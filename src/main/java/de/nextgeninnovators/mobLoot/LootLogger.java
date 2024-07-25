package de.nextgeninnovators.mobLoot;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Logger;

public class LootLogger {
    private final Logger logger;

    public LootLogger(Logger logger) {
        this.logger = logger;
    }

    public void logLoot(Player player, EntityType mobType, ItemStack item) {
        logger.info(player.getName() + " picked up " + item.getType() + " from " + mobType);
    }
}
