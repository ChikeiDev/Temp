package club.simplounge.pony.frame.player;

import club.simplounge.pony.frame.Server;
import club.simplounge.pony.frame.utils.ReflectionUtils;
import net.minestom.server.MinecraftServer;
import net.minestom.server.advancements.AdvancementTab;
import net.minestom.server.collision.CollisionUtils;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Player;
import net.minestom.server.event.entity.EntityPotionRemoveEvent;
import net.minestom.server.event.entity.EntityTickEvent;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.block.CustomBlock;
import net.minestom.server.network.packet.client.ClientPlayPacket;
import net.minestom.server.network.packet.server.play.DestroyEntitiesPacket;
import net.minestom.server.network.packet.server.play.EntityHeadLookPacket;
import net.minestom.server.network.packet.server.play.PlayerInfoPacket;
import net.minestom.server.network.player.PlayerConnection;
import net.minestom.server.potion.TimedPotion;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.Vector;
import net.minestom.server.utils.chunk.ChunkUtils;
import net.minestom.server.utils.player.PlayerUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

public class ClubPlayer extends Player {

	private Set<UUID> tabPlayers = new HashSet<>();

	private Set<Entity> viewableEntities;
	private Queue<ClientPlayPacket> packets;

	private boolean tempOperator = false;

	private final Object syncObject = new Object();

	public ClubPlayer(@NotNull UUID uuid, @NotNull String username, @NotNull PlayerConnection playerConnection) {
		super(uuid, username, playerConnection);

		this.viewableEntities = (Set<Entity>) ReflectionUtils.getPrivateField(this, "viewableEntities", Player.class);
		this.packets = (Queue<ClientPlayPacket>) ReflectionUtils.getPrivateField(this, "packets", Player.class);
	}

	/*
	 * New methods.
	 */

	public void setTempOp(boolean value) {
		this.tempOperator = value;
	}

	public boolean isTempOp() {
		return this.tempOperator;
	}

	public void addTabPlayer(ClubPlayer player) {
		if (player == this) return;

		synchronized (this.syncObject) {
			if (this.tabPlayers.contains(player.getUuid())) return;
			this.tabPlayers.add(player.getUuid());
		}

		PlayerInfoPacket playerInfoPacket = new PlayerInfoPacket(net.minestom.server.network.packet.server.play.PlayerInfoPacket.Action.ADD_PLAYER);
		PlayerInfoPacket.AddPlayer addPlayer = new PlayerInfoPacket.AddPlayer(player.getUuid(), player.getUsername(), player.getGameMode(), player.getLatency());
		addPlayer.displayName = player.getDisplayName();
		if (player.getSkin() != null) {
			String textures = player.getSkin().getTextures();
			String signature = player.getSkin().getSignature();
			PlayerInfoPacket.AddPlayer.Property prop = new PlayerInfoPacket.AddPlayer.Property("textures", textures, signature);
			addPlayer.properties.add(prop);
		}
		playerInfoPacket.playerInfos.add(addPlayer);
		getPlayerConnection().sendPacket(playerInfoPacket);
	}

	public void removeTabPlayer(ClubPlayer player) {
		if (player == this) return;

		synchronized (this.syncObject) {
			if (!this.tabPlayers.contains(player.getUuid())) return;
			this.tabPlayers.remove(player.getUuid());
		}

		PlayerInfoPacket playerInfoPacket = new PlayerInfoPacket(net.minestom.server.network.packet.server.play.PlayerInfoPacket.Action.REMOVE_PLAYER);
		PlayerInfoPacket.RemovePlayer removePlayer = new PlayerInfoPacket.RemovePlayer(player.getUuid());
		playerInfoPacket.playerInfos.add(removePlayer);
		getPlayerConnection().sendPacket(playerInfoPacket);
	}

	/*
	 * Override methods.
	 */

	@Override
	public void remove() {
		if (isRemoved()) return;

		callEvent(PlayerDisconnectEvent.class, new PlayerDisconnectEvent(this));

		super.remove();
		this.packets.clear();
		if (getOpenInventory() != null) getOpenInventory().removeViewer(this);

		// Boss bars cache
		{
			Set<net.minestom.server.bossbar.BossBar> bossBars = net.minestom.server.bossbar.BossBar.getBossBars(this);
			if (bossBars != null) {
				for (net.minestom.server.bossbar.BossBar bossBar : bossBars) bossBar.removeViewer(this);
			}
		}

		MinecraftServer.getBossBarManager().removeAllBossBars(this);

		// Advancement tabs cache
		{
			Set<AdvancementTab> advancementTabs = AdvancementTab.getTabs(this);
			if (advancementTabs != null) {
				for (AdvancementTab advancementTab : advancementTabs) advancementTab.removeViewer(this);
			}
		}

		// Clear all viewable entities
		this.viewableEntities.forEach(entity -> entity.removeViewer(this));
		// Clear all tab players.
		MinecraftServer.getConnectionManager().getOnlinePlayers().forEach(player -> ((ClubPlayer) player).removeTabPlayer(this));
		// Clear all viewable chunks
		this.viewableChunks.forEach(chunk -> {
			if (chunk.isLoaded())
				chunk.removeViewer(this);
		});
		resetTargetBlock();
		playerConnection.disconnect();
	}

	@Override
	protected boolean addViewer0(@NotNull Player player) {
		if (player == this) return false;
		log("1, Adding "+player.getUsername());

		if (this.tabPlayers.contains(player.getUuid())) return this.addClubViewer_LivingEntity((ClubPlayer) player);
		else this.tabPlayers.add(player.getUuid());

		log("2, Adding "+player.getUsername());

		PlayerConnection viewerConnection = player.getPlayerConnection();
		viewerConnection.sendPacket(getAddPlayerToList());
		return this.addClubViewer_LivingEntity((ClubPlayer) player);
	}

	@Override
	protected boolean removeViewer0(@NotNull Player player) {
		if (player == this || !this.removeClubViewer_Entity((ClubPlayer) player)) return false;

		log("1, Removing "+player.getUsername());

		if (!player.isRemoved() && !isRemoved()) return true;
		log("2, Removing "+player.getUsername());
		if (!this.tabPlayers.contains(player.getUuid())) return true;
		else this.tabPlayers.remove(player.getUuid());
		log("3, Removing "+player.getUsername());

		PlayerConnection viewerConnection = player.getPlayerConnection();
		viewerConnection.sendPacket(getRemovePlayerToList());

		// Team
		if (this.getTeam() != null && this.getTeam().getMembers().size() == 1) {// If team only contains "this" player
			viewerConnection.sendPacket(this.getTeam().createTeamDestructionPacket());
		}
		return true;
	}

	/*
	 * ADD / REMOVE PLAYER
	 * Create own methods to override the parents, as we can't call the parent method.
	 */

	private boolean removeClubViewer_Entity(@NotNull ClubPlayer player) {
		if (!this.viewers.remove(player)) {
			return false;
		} else {
			DestroyEntitiesPacket destroyEntitiesPacket = new DestroyEntitiesPacket();
			destroyEntitiesPacket.entityIds = new int[]{this.getEntityId()};
			player.getPlayerConnection().sendPacket(destroyEntitiesPacket);
			player.viewableEntities.remove(this);
			return true;
		}
	}

	private boolean addClubViewer_LivingEntity(@NotNull ClubPlayer player) {
		if (!addClubViewer_Entity(player)) return false;

		final PlayerConnection playerConnection = player.getPlayerConnection();
		playerConnection.sendPacket(getEquipmentsPacket());
		playerConnection.sendPacket(getPropertiesPacket());

		if (getTeam() != null) playerConnection.sendPacket(getTeam().createTeamsCreationPacket());
		return true;
	}

	private boolean addClubViewer_Entity(@NotNull ClubPlayer player) {
		if (!this.viewers.add(player)) return false;
		player.viewableEntities.add(this);

		PlayerConnection playerConnection = player.getPlayerConnection();
		playerConnection.sendPacket(getEntityType().getSpawnType().getSpawnPacket(this));
		if (hasVelocity()) playerConnection.sendPacket(getVelocityPacket());
		playerConnection.sendPacket(getMetadataPacket());

		// Passenger
		if (hasPassenger()) playerConnection.sendPacket(getPassengersPacket());

		{ // Head position
			EntityHeadLookPacket entityHeadLookPacket = new EntityHeadLookPacket();
			entityHeadLookPacket.entityId = getEntityId();
			entityHeadLookPacket.yaw = position.getYaw();
			playerConnection.sendPacket(entityHeadLookPacket);
		}

		return true;
	}

	/*
	 * Debug
	 */

	private void log(String message) {
		Server.LOGGER.info("["+getUsername()+"] "+message);
	}
}
