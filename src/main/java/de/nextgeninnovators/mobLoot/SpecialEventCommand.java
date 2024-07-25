package de.nextgeninnovators.mobLoot;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SpecialEventCommand implements CommandExecutor {
    private final DropConfig dropConfig;

    public SpecialEventCommand(DropConfig dropConfig) {
        this.dropConfig = dropConfig;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /specialevent <start|stop|addloot>");
            return false;
        }

        if (args[0].equalsIgnoreCase("start")) {
            dropConfig.setSpecialEventActive(true);
            sender.sendMessage("Special event started!");
        } else if (args[0].equalsIgnoreCase("stop")) {
            dropConfig.setSpecialEventActive(false);
            sender.sendMessage("Special event stopped!");
        } else if (args[0].equalsIgnoreCase("addloot")) {
            if (args.length < 4) {
                sender.sendMessage("Usage: /specialevent addloot <mobType> <item> <chance>");
                return false;
            }

            String mobType = args[1].toUpperCase();
            Material material = Material.getMaterial(args[2].toUpperCase());
            double chance;

            try {
                chance = Double.parseDouble(args[3]);
            } catch (NumberFormatException e) {
                sender.sendMessage("Invalid chance value. It must be a number.");
                return false;
            }

            if (material == null) {
                sender.sendMessage("Invalid item type.");
                return false;
            }

            ItemStack itemStack = new ItemStack(material);
            dropConfig.addSpecialEventLoot(mobType, itemStack, chance);
            sender.sendMessage("Added special event loot for " + mobType + ": " + material.name() + " with a chance of " + chance);
        } else {
            sender.sendMessage("Usage: /specialevent <start|stop|addloot>");
        }

        return true;
    }
}
