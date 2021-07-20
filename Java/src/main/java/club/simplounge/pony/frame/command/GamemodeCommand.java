package club.simplounge.pony.frame.command;

import club.simplounge.pony.frame.player.ClubPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentEnum;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
import net.minestom.server.entity.GameMode;
import net.minestom.server.utils.entity.EntityFinder;
import org.jetbrains.annotations.Nullable;

public class GamemodeCommand extends FrameworkCommand {

	public GamemodeCommand() {
		super("gamemode", "gm", "gms", "gmc", "gma", "gmsp");

		setSupportedSender(SupportedSender.PLAYER);
		setRequireOP(true);

		// Default executor.
		setDefaultExecutor((commandSender, commandContext) -> {
			String alias = commandContext.getCommandName();
			ClubPlayer player = getPlayerFromSender(commandSender);

			if (alias.equalsIgnoreCase("gms")) setPlayerGameMode(player, GameMode.SURVIVAL, null);
			else if (alias.equalsIgnoreCase("gmc")) setPlayerGameMode(player, GameMode.CREATIVE, null);
			else if (alias.equalsIgnoreCase("gma")) setPlayerGameMode(player, GameMode.ADVENTURE, null);
			else if (alias.equalsIgnoreCase("gmsp")) setPlayerGameMode(player, GameMode.SPECTATOR, null);
			else sendWrongUsage(commandSender, "/gamemode [player] (mode)");
		});

		var targetArg = ArgumentType.Entity("player").onlyPlayers(true).singleEntity(true);
		var modeArg = ArgumentType.Enum("mode", GameMode.class).setFormat(ArgumentEnum.Format.LOWER_CASED);
		targetArg.setCallback(this::targetCallback);
		modeArg.setCallback(this::modeCallback);

		addSyntax(this::changeOnSelf, modeArg);
		addSyntax(this::changeOnOther, targetArg, modeArg);
	}

	/*
	 * Handle syntax.
	 */

	private void changeOnSelf(CommandSender commandSender, CommandContext commandContext) {
		ClubPlayer player = getPlayerFromSender(commandSender);
		GameMode mode = commandContext.get("mode");
		this.setPlayerGameMode(player, mode, null);
	}

	private void changeOnOther(CommandSender commandSender, CommandContext commandContext) {
		ClubPlayer player = getPlayerFromSender(commandSender);
		ClubPlayer target = (ClubPlayer) ((EntityFinder) commandContext.get("player")).findFirstPlayer(null, null);
		GameMode mode = commandContext.get("mode");
		this.setPlayerGameMode(target, mode, player);
	}

	/*
	 * Handle argument callback.
	 */

	private void targetCallback(CommandSender commandSender, ArgumentSyntaxException exception) {
		commandSender.sendMessage(Component.text("'" + exception.getInput() + "' isn't a valid player name.", NamedTextColor.RED));
	}

	private void modeCallback(CommandSender commandSender, ArgumentSyntaxException exception) {
		commandSender.sendMessage(Component.text("'" + exception.getInput() + "' isn't a valid gamemode.", NamedTextColor.RED));
	}

	/*
	 * Gamemode
	 */

	private void setPlayerGameMode(ClubPlayer target, GameMode mode, @Nullable ClubPlayer sender) {
		target.setGameMode(mode);
		if (sender == null) sendOpMessage(target.getUsername() + " changed their own gamemode to "+mode.name().toLowerCase());
		else sendOpMessage(sender.getUsername() + " changed the gamemode of " + target.getUsername() + " to "+mode.name().toLowerCase());
	}
}
