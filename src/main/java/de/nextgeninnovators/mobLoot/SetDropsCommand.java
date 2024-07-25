package de.nextgeninnovators.mobLoot;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetDropsCommand implements CommandExecutor {
    private final MobLoot plugin;

    public SetDropsCommand(MobLoot plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            plugin.getGUIListener().openMobSelectionGUI(player);
            return true;
        }
        sender.sendMessage("This command can only be used by a player.");
        return false;
    }
}
