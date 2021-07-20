package club.simplounge.pony.frame.command;

import club.simplounge.pony.frame.Server;
import club.simplounge.pony.frame.player.ClubPlayer;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.ConsoleSender;
import net.minestom.server.command.builder.Command;

public class StopCommand extends Command {

	public StopCommand() {
		super("stop");

		setCondition((commandSender, s) -> {
			if (commandSender instanceof ConsoleSender) return true;
			if (commandSender.isConsole()) return true;
			if (commandSender.isPlayer()) {
				ClubPlayer player = (ClubPlayer) commandSender.asPlayer();
				return player.isTempOp();
			}
			return false;
		});

		setDefaultExecutor((commandSender, commandContext) -> {
			Server.LOGGER.info("Saving...");
			Server.INSTANCE_CONTAINER.saveChunksToStorage(() -> {
				Server.LOGGER.info("Saved!");
				MinecraftServer.stopCleanly();
			});
		});
	}
}
