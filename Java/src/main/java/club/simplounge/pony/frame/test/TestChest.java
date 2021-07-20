package club.simplounge.pony.frame.test;

import club.simplounge.pony.frame.player.ClubPlayer;
import net.kyori.adventure.text.Component;
import net.minestom.server.command.builder.Command;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.inventory.click.ClickType;

public class TestChest extends Command {

    public TestChest() {
        super("chest");

        setDefaultExecutor((commandSender, commandContext) -> {
            commandSender.sendMessage("Usage: /chest");
        });

        addSyntax((commandSender, commandContext) -> {
            ClubPlayer player = (ClubPlayer) commandSender.asPlayer();


            Inventory inventory = new Inventory(InventoryType.CHEST_1_ROW, "Test Inventory");


            player.openInventory(inventory);

            inventory.addInventoryCondition((player1, slot, clickType, inventoryConditionResult) -> {

                if (slot == 0) {
                    inventory.setTitle(Component.text("Slot 0"));
                    player.getOpenInventory().update();
                    player.sendMessage("This is slot 0 :D");
                    return;
                }
                if (slot == 1) {
                    inventory.setTitle(Component.text("Slot 1"));
                    player.getOpenInventory().update();
                    player.sendMessage("This is slot 1 :D");
                    return;
                }
                if (slot == 2) {
                    inventory.setTitle(Component.text("Slot 2"));
                    player.getOpenInventory().update();
                    player.sendMessage("This is slot 2 :D");
                    return;
                }
                if (slot == 3) {
                    inventory.setTitle(Component.text("Slot 3"));
                    player.getOpenInventory().update();
                    player.sendMessage("This is slot 3 :D");
                }

                if (slot == 4) {
                    inventory.setTitle(Component.text("Slot 4 held"));
                    player.getOpenInventory().update();
                    player.sendMessage("This is a held down slot 4");
                }

                if ( (slot == 4) && (clickType == ClickType.DOUBLE_CLICK)) {
                    inventory.setTitle(Component.text("Slot 4 clicked"));
                    player.getOpenInventory().update();
                    player.sendMessage("THis is slot 4 clicked?");
                }
            });
        });
    }
}
