package club.simplounge.pony.frame.command;

import club.simplounge.pony.frame.player.ClubPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.MinecraftServer;
import net.minestom.server.adventure.audience.Audiences;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.ConsoleSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;

public class TempOpCommand extends Command {

	public TempOpCommand() {
		super("tempop");

		setCondition(this::condition);

		setDefaultExecutor(this::usage);

		var player = ArgumentType.Entity("player").onlyPlayers(true).singleEntity(true);
		var removePlayer = ArgumentType.Entity("player").onlyPlayers(true).singleEntity(true)
				.setSuggestionCallback((commandSender, commandContext, suggestion) -> MinecraftServer.getConnectionManager().getOnlinePlayers().forEach(p -> {
					ClubPlayer clubPlayer = (ClubPlayer) p;
					if (clubPlayer.isTempOp()) suggestion.addEntry(new SuggestionEntry(clubPlayer.getUsername()));
				}));

		player.setCallback((commandSender, e) -> commandSender.sendMessage(Component.text("Invalid player '"+e.getInput()+"'!", NamedTextColor.RED)));
		removePlayer.setCallback((commandSender, e) -> commandSender.sendMessage(Component.text("Invalid player '"+e.getInput()+"'!", NamedTextColor.RED)));

		// Add
		addSyntax((commandSender, commandContext) -> {
			ClubPlayer clubPlayer = (ClubPlayer) commandContext.get(player).findFirstPlayer(commandSender);
			if (clubPlayer == null) {
				commandSender.sendMessage(Component.text("Player wasn't found!", NamedTextColor.RED));
				return;
			}
			if (clubPlayer.isTempOp()) {
				commandSender.sendMessage(Component.text("Player is already added as a tempop.", NamedTextColor.RED));
				return;
			}
			clubPlayer.setTempOp(true);
			clubPlayer.getPlayerConnection().sendPacket(MinecraftServer.getCommandManager().createDeclareCommandsPacket(clubPlayer));
			this.sendOpMessage("Added "+clubPlayer.getUsername()+" to the tempop list.");
		}, ArgumentType.Literal("add"), player);

		// Remove
		addSyntax((commandSender, commandContext) -> {
			ClubPlayer clubPlayer = (ClubPlayer) commandContext.get(removePlayer).findFirstPlayer(commandSender);
			if (clubPlayer == null) {
				commandSender.sendMessage(Component.text("Player wasn't found!", NamedTextColor.RED));
				return;
			}
			if (!clubPlayer.isTempOp()) {
				commandSender.sendMessage(Component.text("Player isn't  a tempop.", NamedTextColor.RED));
				return;
			}
			clubPlayer.setTempOp(false);
			clubPlayer.getPlayerConnection().sendPacket(MinecraftServer.getCommandManager().createDeclareCommandsPacket(clubPlayer));
			this.sendOpMessage("Removed "+clubPlayer.getUsername()+" from the tempop list.");
		}, ArgumentType.Literal("remove"), removePlayer);
	}

	private boolean condition(CommandSender sender, String command) {
		return sender instanceof ConsoleSender;
	}

	private void usage(CommandSender sender, CommandContext context) {
		sender.sendMessage("Usage: /tempop add|remove (player)");
	}

	private void sendOpMessage(String message) {
		Audiences.players(player -> {
			ClubPlayer clubPlayer = (ClubPlayer) player;
			return clubPlayer.isTempOp();
		}).sendMessage(Component.text("["+message+"]", NamedTextColor.GRAY, TextDecoration.ITALIC));
		Audiences.console().sendMessage(Component.text("["+message+"]", NamedTextColor.GRAY, TextDecoration.ITALIC));
	}

}
