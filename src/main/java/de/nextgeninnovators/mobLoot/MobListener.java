package de.nextgeninnovators.mobLoot;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class MobListener implements Listener {
    private final DropConfig dropConfig;
    private final LootLogger lootLogger;
    private final Random random = new Random();

    public MobListener(DropConfig dropConfig, LootLogger lootLogger) {
        this.dropConfig = dropConfig;
        this.lootLogger = lootLogger;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() == null) {
            return;
        }

        String mobType = event.getEntityType().toString();
        if (dropConfig.hasCustomLoot(mobType)) {
            ItemStack drop = dropConfig.getRandomLoot(mobType);
            if (drop != null) {
                event.getDrops().clear();
                event.getDrops().add(drop);
                lootLogger.logLoot(event.getEntity().getKiller(), event.getEntityType(), drop);
            }
        }

        // Überprüfen Sie auf spezielle Events
        if (dropConfig.isSpecialEventActive()) {
            ItemStack specialDrop = dropConfig.getSpecialEventLoot(mobType);
            if (specialDrop != null) {
                event.getDrops().add(specialDrop);
                lootLogger.logLoot(event.getEntity().getKiller(), event.getEntityType(), specialDrop);
            }
        }
    }
}
