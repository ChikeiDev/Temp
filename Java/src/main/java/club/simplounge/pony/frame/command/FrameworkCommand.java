package club.simplounge.pony.frame.command;

import club.simplounge.pony.frame.player.ClubPlayer;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.adventure.audience.Audiences;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FrameworkCommand extends Command {

	public static final String PERMISSION_DENY_MESSAGE = "This command requires the power of a super cool person... and it looks like you aren't one.";

	@Setter private boolean requireOP = false;
	@Setter private SupportedSender supportedSender;

	public FrameworkCommand(@NotNull String name, @Nullable String... aliases) {
		super(name, aliases);

		this.buildDefault();
	}

	public FrameworkCommand(@NotNull String name) {
		super(name);

		this.buildDefault();
	}

	private void buildDefault() {

		// Build default condition.
		setCondition((commandSender, s) -> {
			// If the command supports both or console and the sender is console then allow the command.
			if ((this.supportedSender == SupportedSender.BOTH || this.supportedSender == SupportedSender.CONSOLE) && commandSender.isConsole()) return true;
			// If the command supports only console and the sender isn't console then disallow the command.
			if (this.supportedSender == SupportedSender.CONSOLE && !commandSender.isConsole()) {
				if (s != null) commandSender.sendMessage(Component.text("This command can only be used in the console!", NamedTextColor.RED));
				return false;
			}
			// If the command supports only player and the sender isn't a player then disallow the command.
			if (this.supportedSender == SupportedSender.PLAYER && !commandSender.isPlayer()) {
				if (s != null) commandSender.sendMessage(Component.text("This command can only be used by players!", NamedTextColor.RED));
				return false;
			}

			// It *should* be a player, so check if the command requires OP.
			if (!this.requireOP) return true;
			else {
				if (commandSender.isPlayer()) {
					ClubPlayer clubPlayer = (ClubPlayer) commandSender.asPlayer();
					if (!clubPlayer.isTempOp()) {
						if (s != null) commandSender.sendMessage(Component.text(PERMISSION_DENY_MESSAGE, NamedTextColor.RED));
						return false;
					}
					return true;
				}
			}

			// Fallback to deny command.
			return false;
		});
	}

	public void sendOpMessage(String message) {
		Audiences.players(player -> {
			ClubPlayer clubPlayer = (ClubPlayer) player;
			return clubPlayer.isTempOp();
		}).sendMessage(Component.text("["+message+"]", NamedTextColor.GRAY, TextDecoration.ITALIC));
		Audiences.console().sendMessage(Component.text("["+message+"]", NamedTextColor.GRAY, TextDecoration.ITALIC));
	}

	public void sendWrongUsage(CommandSender sender, String usage) {
		sender.sendMessage(Component.text("Usage: "+usage, NamedTextColor.RED));
	}

	public ClubPlayer getPlayerFromSender(CommandSender sender) {
		return (ClubPlayer) sender.asPlayer();
	}

	public static enum SupportedSender {
		PLAYER,
		CONSOLE,
		BOTH
	}
}
