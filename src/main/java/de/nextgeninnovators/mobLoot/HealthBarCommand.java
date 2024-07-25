package de.nextgeninnovators.mobLoot;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealthBarCommand implements CommandExecutor {
    private final DropConfig dropConfig;
    private final DamageListener damageListener;

    public HealthBarCommand(DropConfig dropConfig, DamageListener damageListener) {
        this.dropConfig = dropConfig;
        this.damageListener = damageListener;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage("Usage: /healthbar <on|off>");
            return true;
        }

        boolean showHealthBars = args[0].equalsIgnoreCase("on");
        dropConfig.setShowHealthBars(showHealthBars);
        sender.sendMessage("Health bars are now " + (showHealthBars ? "enabled" : "disabled") + ".");

        if (!showHealthBars) {
            damageListener.clearAllBossBars();
        }
        return true;
    }
}
