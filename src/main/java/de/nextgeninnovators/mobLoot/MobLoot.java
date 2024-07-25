package de.nextgeninnovators.mobLoot;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MobLoot extends JavaPlugin {
    private DropConfig dropConfig;
    private GUIListener guiListener;
    private DamageListener damageListener;
    private LootLogger lootLogger;

    @Override
    public void onEnable() {
        this.dropConfig = new DropConfig(this);
        this.guiListener = new GUIListener(dropConfig, this);
        this.lootLogger = new LootLogger(getLogger());
        this.damageListener = new DamageListener(dropConfig, 15); // Hier können Sie den Radius anpassen

        dropConfig.load();

        Bukkit.getLogger().info("MobLoot Plugin Enabled");
        getServer().getPluginManager().registerEvents(new MobListener(dropConfig, lootLogger), this);
        getServer().getPluginManager().registerEvents(guiListener, this);
        getServer().getPluginManager().registerEvents(damageListener, this);
        getCommand("setdrops").setExecutor(new SetDropsCommand(this));
        getCommand("healthbar").setExecutor(new HealthBarCommand(dropConfig, damageListener));
        getCommand("listdrops").setExecutor(new ListDropsCommand(dropConfig)); // Neuer Befehl registrieren
        getCommand("specialevent").setExecutor(new SpecialEventCommand(dropConfig)); // Neuer Befehl registrieren
    }

    @Override
    public void onDisable() {
        dropConfig.save();
        Bukkit.getLogger().info("MobLoot Plugin Disabled");
    }

    public GUIListener getGUIListener() {
        return guiListener;
    }
}
