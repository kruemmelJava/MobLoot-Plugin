package de.nextgeninnovators.mobLoot;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class ListDropsCommand implements CommandExecutor {
    private final DropConfig dropConfig;

    public ListDropsCommand(DropConfig dropConfig) {
        this.dropConfig = dropConfig;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Map<String, List<ItemStack>> allDrops = dropConfig.getAllDrops();
        for (Map.Entry<String, List<ItemStack>> entry : allDrops.entrySet()) {
            String mobType = entry.getKey();
            List<ItemStack> items = entry.getValue();
            sender.sendMessage("Drops for " + mobType + ":");
            for (ItemStack item : items) {
                sender.sendMessage(" - " + item.getType().toString());
            }
        }
        return true;
    }
}
