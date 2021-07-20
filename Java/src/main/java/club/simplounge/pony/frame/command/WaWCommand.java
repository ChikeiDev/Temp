package club.simplounge.pony.frame.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;

public class WaWCommand extends Command {

	public WaWCommand() {
		super("waw");

		setDefaultExecutor((commandSender, commandContext) -> commandSender.sendMessage(Component.text("WaW better plox: /waw (1-100)")));

		var numberArgument = ArgumentType.Integer("waw-factor");
		addSyntax((commandSender, commandContext) -> {
			int wawFactor = commandContext.get(numberArgument);
			commandSender.sendMessage(Component.text("You WaWed the WaW factor to "+wawFactor, NamedTextColor.GOLD));
		}, numberArgument);

	}
}
