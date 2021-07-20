package club.simplounge.pony.frame;

import club.simplounge.pony.frame.command.GamemodeCommand;
import club.simplounge.pony.frame.command.StopCommand;
import club.simplounge.pony.frame.command.TempOpCommand;
import club.simplounge.pony.frame.player.ClubPlayer;
import club.simplounge.pony.frame.test.TestChest;
import club.simplounge.pony.frame.test.TestCommand;
import club.simplounge.pony.frame.world.chunk.generator.TestGenerator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import net.minestom.server.MinecraftServer;
import net.minestom.server.chat.ColoredText;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.extras.lan.OpenToLAN;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.MinestomBasicChunkLoader;
import net.minestom.server.network.PlayerProvider;
import net.minestom.server.network.packet.server.play.*;
import net.minestom.server.network.player.PlayerConnection;
import net.minestom.server.storage.StorageManager;
import net.minestom.server.storage.systems.FileStorageSystem;
import net.minestom.server.utils.Position;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Server {

	public static void main(String[] args) {
		Server server = new Server("0.0.0.0", 19870);
		server.start();
	}

	public static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
	public static InstanceContainer INSTANCE_CONTAINER;

	private final String host;
	private final int port;

	public Server(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void start() {
		// Initialization
		MinecraftServer minecraftServer = MinecraftServer.init();
		InstanceManager instanceManager = MinecraftServer.getInstanceManager();
		StorageManager storageManager = MinecraftServer.getStorageManager();
		storageManager.defineDefaultStorageSystem(FileStorageSystem::new);

		INSTANCE_CONTAINER = instanceManager.createInstanceContainer(storageManager.getLocation("test_chunks"));
		INSTANCE_CONTAINER.setChunkGenerator(new TestGenerator());
		INSTANCE_CONTAINER.enableAutoChunkLoad(true);

		MinecraftServer.getSchedulerManager().buildShutdownTask(() -> {
			//instanceContainer.saveChunksToStorage();
			LOGGER.info("Saved!");
		});

		// Add an event callback to specify the spawning instance (and the spawn position)
		GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();

		globalEventHandler.addEventCallback(PlayerLoginEvent.class, event -> {
			final Player player = event.getPlayer();
			event.setSpawningInstance(INSTANCE_CONTAINER);
			player.setRespawnPoint(new Position(0, 42, 0));
			player.showTitle(Title.title(Component.empty(), Component.text(player.getUsername(), NamedTextColor.YELLOW), Title.Times.of(Duration.ZERO, Duration.ofDays(1), Duration.ZERO)));
		});

		globalEventHandler.addEventCallback(PlayerSpawnEvent.class, event -> {
			ClubPlayer clubPlayer = (ClubPlayer) event.getPlayer();
			MinecraftServer.getConnectionManager().getOnlinePlayers().forEach(player -> {
				ClubPlayer targetPlayer = (ClubPlayer) player;
				targetPlayer.addTabPlayer(clubPlayer);
				clubPlayer.addTabPlayer(targetPlayer);
			});
		});

		/*Set<Class> ignoreClasses = new HashSet<>(Arrays.asList(ChatMessagePacket.class, TimeUpdatePacket.class,
				KeepAlivePacket.class, UpdateLightPacket.class, ChunkDataPacket.class, UnloadChunkPacket.class,
				EntityHeadLookPacket.class, PlayerInfoPacket.class, UpdateViewPositionPacket.class, EntityMetaDataPacket.class));
		MinecraftServer.getConnectionManager().onPacketSend((collection, packetController, serverPacket) -> {
			for (Player player : collection) {
				if (player.getUsername().equalsIgnoreCase("cp1987")) {
					if (ignoreClasses.contains(serverPacket.getClass())) return;
					player.sendMessage(Component.text("Got packet "+serverPacket.getClass().getSimpleName(), NamedTextColor.BLUE));
					if (serverPacket instanceof EntityTeleportPacket) {
						EntityTeleportPacket packet = (EntityTeleportPacket) serverPacket;
						player.sendMessage(Component.text("Teleport packet: to id "+packet.entityId+", my id "+player.getEntityId()));
					} else if (serverPacket instanceof PlayerPositionAndLookPacket) {
						PlayerPositionAndLookPacket packet = (PlayerPositionAndLookPacket) serverPacket;
						player.sendMessage(Component.text("Position packet: to id "+packet.teleportId+", my id "+player.getEntityId()));
					}
				}
			}
		});*/

		MinecraftServer.getConnectionManager().setPlayerProvider(ClubPlayer::new);

		MojangAuth.init();
		OpenToLAN.open();

		// Commands
		this.registerCommands();

		// Start the server on port 25565
		minecraftServer.start(this.host, this.port);
	}

	private void registerCommands() {
		this.registerCommand(new GamemodeCommand());
		this.registerCommand(new TempOpCommand());
		this.registerCommand(new TestCommand());
		this.registerCommand(new StopCommand());
		this.registerCommand(new TestChest());
	}

	private void registerCommand(Command command) {
		MinecraftServer.getCommandManager().register(command);
	}
}
