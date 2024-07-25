package de.nextgeninnovators.mobLoot;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DropConfig {
    private final MobLoot plugin;
    private final File configFile;
    private FileConfiguration config;
    private final Random random = new Random();
    private boolean specialEventActive = false; // Flag für spezielle Events

    public DropConfig(MobLoot plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "drops.yml");
    }

    public void load() {
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource("drops.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(configFile);
        config.addDefault("showHealthBars", true);
        config.options().copyDefaults(true);
        save();
    }

    public void save() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isShowHealthBars() {
        return config.getBoolean("showHealthBars");
    }

    public void setShowHealthBars(boolean show) {
        config.set("showHealthBars", show);
        save();
    }

    public void addLoot(String mobType, ItemStack item) {
        addLoot(mobType, item, 1.0); // Standardchance ist 1.0
    }

    public void addLoot(String mobType, ItemStack item, double chance) {
        List<Map<String, Object>> drops = getDrops(mobType);
        Map<String, Object> drop = new HashMap<>();
        drop.put("item", item);
        drop.put("chance", chance);
        drops.add(drop);
        config.set("drops." + mobType, drops);
        save();
    }

    public void overwriteLoot(String mobType, List<ItemStack> items, List<Double> chances) {
        List<Map<String, Object>> drops = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            Map<String, Object> drop = new HashMap<>();
            drop.put("item", items.get(i));
            drop.put("chance", chances.get(i));
            drops.add(drop);
        }
        config.set("drops." + mobType, drops);
        save();
    }

    public void clearLoot(String mobType) {
        config.set("drops." + mobType, null);
        save();
    }

    public boolean hasCustomLoot(String mobType) {
        List<Map<String, Object>> drops = getDrops(mobType);
        return drops != null && !drops.isEmpty();
    }

    public ItemStack getRandomLoot(String mobType) {
        List<Map<String, Object>> drops = getDrops(mobType);
        if (drops != null && !drops.isEmpty()) {
            for (Map<String, Object> drop : drops) {
                ItemStack item = (ItemStack) drop.get("item");
                double chance = (double) drop.get("chance");
                if (random.nextDouble() < chance) {
                    return item;
                }
            }
        }
        return null;
    }

    public void setLootRate(String mobType, double rate) {
        config.set("rates." + mobType, rate);
        save();
    }

    public double getLootRate(String mobType) {
        return config.getDouble("rates." + mobType, 1.0); // Standardrate ist 1.0
    }

    public boolean isSpecialEventActive() {
        return specialEventActive;
    }

    public void setSpecialEventActive(boolean active) {
        this.specialEventActive = active;
    }

    public void addSpecialEventLoot(String mobType, ItemStack item, double chance) {
        List<Map<String, Object>> specialDrops = getSpecialDrops(mobType);
        Map<String, Object> drop = new HashMap<>();
        drop.put("item", item);
        drop.put("chance", chance);
        specialDrops.add(drop);
        config.set("specialDrops." + mobType, specialDrops);
        save();
    }

    public ItemStack getSpecialEventLoot(String mobType) {
        List<Map<String, Object>> specialDrops = getSpecialDrops(mobType);
        if (specialDrops != null && !specialDrops.isEmpty()) {
            for (Map<String, Object> drop : specialDrops) {
                ItemStack item = (ItemStack) drop.get("item");
                double chance = (double) drop.get("chance");
                if (random.nextDouble() < chance) {
                    return item;
                }
            }
        }
        return null;
    }

    public Map<String, List<ItemStack>> getAllDrops() {
        Map<String, List<ItemStack>> allDrops = new HashMap<>();
        if (config.contains("drops")) {
            for (String mobType : config.getConfigurationSection("drops").getKeys(false)) {
                List<Map<String, Object>> drops = getDrops(mobType);
                List<ItemStack> items = new ArrayList<>();
                if (drops != null) {
                    for (Map<String, Object> drop : drops) {
                        items.add((ItemStack) drop.get("item"));
                    }
                }
                allDrops.put(mobType, items);
            }
        }
        return allDrops;
    }

    private List<Map<String, Object>> getDrops(String mobType) {
        List<?> rawList = config.getList("drops." + mobType);
        if (rawList == null) {
            return new ArrayList<>();
        }
        List<Map<String, Object>> drops = new ArrayList<>();
        for (Object obj : rawList) {
            if (obj instanceof Map) {
                drops.add((Map<String, Object>) obj);
            }
        }
        return drops;
    }

    private List<Map<String, Object>> getSpecialDrops(String mobType) {
        List<?> rawList = config.getList("specialDrops." + mobType);
        if (rawList == null) {
            return new ArrayList<>();
        }
        List<Map<String, Object>> specialDrops = new ArrayList<>();
        for (Object obj : rawList) {
            if (obj instanceof Map) {
                specialDrops.add((Map<String, Object>) obj);
            }
        }
        return specialDrops;
    }
}
