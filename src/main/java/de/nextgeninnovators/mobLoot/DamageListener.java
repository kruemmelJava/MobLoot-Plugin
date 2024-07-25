package de.nextgeninnovators.mobLoot;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;

public class DamageListener implements Listener {
    private final DropConfig dropConfig;
    private final Map<LivingEntity, BossBar> bossBars = new HashMap<>();
    private final int displayRadius;

    public DamageListener(DropConfig dropConfig, int displayRadius) {
        this.dropConfig = dropConfig;
        this.displayRadius = displayRadius;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!dropConfig.isShowHealthBars()) return;

        if (event.getEntity() instanceof LivingEntity && !(event.getEntity() instanceof Player)) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            updateBossBar(entity, entity.getHealth() - event.getFinalDamage());
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        BossBar bossBar = bossBars.remove(entity);
        if (bossBar != null) {
            bossBar.removeAll();
        }
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (!dropConfig.isShowHealthBars()) return;

        if (event.getEntity() instanceof LivingEntity && !(event.getEntity() instanceof Player)) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            updateBossBar(entity, entity.getHealth());
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!dropConfig.isShowHealthBars()) return;

        Player player = event.getPlayer();
        player.getWorld().getLivingEntities().forEach(entity -> {
            if (entity.getLocation().distance(player.getLocation()) <= displayRadius && !(entity instanceof Player)) {
                updateBossBar(entity, entity.getHealth());
            } else {
                BossBar bossBar = bossBars.remove(entity);
                if (bossBar != null) {
                    bossBar.removePlayer(player);
                }
            }
        });
    }

    private void updateBossBar(LivingEntity entity, double health) {
        double maxHealth = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        double currentHealth = Math.max(0, health);

        BossBar bossBar = bossBars.get(entity);
        if (bossBar == null) {
            bossBar = Bukkit.createBossBar(entity.getName(), BarColor.RED, BarStyle.SOLID);
            bossBars.put(entity, bossBar);
        }

        bossBar.setProgress(currentHealth / maxHealth);
        bossBar.setTitle(entity.getName() + " - " + (int) currentHealth + "/" + (int) maxHealth);

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getLocation().distance(entity.getLocation()) <= displayRadius) {
                bossBar.addPlayer(player);
            } else {
                bossBar.removePlayer(player);
            }
        }

        if (currentHealth <= 0) {
            bossBars.remove(entity);
            bossBar.removeAll();
        }
    }

    public void clearAllBossBars() {
        bossBars.values().forEach(BossBar::removeAll);
        bossBars.clear();
    }
}
