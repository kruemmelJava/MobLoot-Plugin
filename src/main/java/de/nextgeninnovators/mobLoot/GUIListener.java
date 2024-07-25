package de.nextgeninnovators.mobLoot;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GUIListener implements Listener {
    private final DropConfig dropConfig;
    private final MobLoot plugin;

    public GUIListener(DropConfig dropConfig, MobLoot plugin) {
        this.dropConfig = dropConfig;
        this.plugin = plugin;
    }

    public void openMobSelectionGUI(Player player) {
        Inventory mobSelectionInventory = Bukkit.createInventory(player, 54, "Mob Drops");

        for (EntityType entityType : EntityType.values()) {
            if (entityType.isAlive()) {
                ItemStack itemStack = new ItemStack(Material.ZOMBIE_HEAD); // Beispielhaft, ändern Sie es entsprechend
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(entityType.name());
                itemStack.setItemMeta(itemMeta);
                mobSelectionInventory.addItem(itemStack);
            }
        }

        player.openInventory(mobSelectionInventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase("Mob Drops")) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) {
                return;
            }

            ItemStack clickedItem = event.getCurrentItem();
            String selectedMobType = clickedItem.getItemMeta().getDisplayName();

            Inventory selectItemInventory = Bukkit.createInventory(null, 54, "Select Item");

            for (Material material : Material.values()) {
                if (material.isItem()) {
                    ItemStack itemStack = new ItemStack(material);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName(material.name());
                    itemStack.setItemMeta(itemMeta);
                    selectItemInventory.addItem(itemStack);
                }
            }

            event.getWhoClicked().openInventory(selectItemInventory);
            plugin.getServer().getPluginManager().registerEvents(new Listener() {
                @EventHandler
                public void onItemSelect(InventoryClickEvent selectEvent) {
                    if (selectEvent.getView().getTitle().equalsIgnoreCase("Select Item")) {
                        selectEvent.setCancelled(true);
                        if (selectEvent.getCurrentItem() == null || selectEvent.getCurrentItem().getType() == Material.AIR) {
                            return;
                        }

                        ItemStack selectedItem = selectEvent.getCurrentItem();
                        dropConfig.addLoot(selectedMobType, selectedItem, 1.0);
                        selectEvent.getWhoClicked().closeInventory();
                        Inventory confirmationInventory = Bukkit.createInventory(null, 54, "Item added!");
                        ItemStack confirmationItem = new ItemStack(Material.GREEN_WOOL);
                        ItemMeta confirmationMeta = confirmationItem.getItemMeta();
                        confirmationMeta.setDisplayName("Item successfully added as drop!");
                        confirmationItem.setItemMeta(confirmationMeta);
                        confirmationInventory.addItem(confirmationItem);
                        selectEvent.getWhoClicked().openInventory(confirmationInventory);
                    }
                }
            }, plugin);
        }
    }
}
