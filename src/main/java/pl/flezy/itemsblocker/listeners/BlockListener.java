package pl.flezy.itemsblocker.listeners;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import pl.flezy.itemsblocker.ItemsBlocker;
import pl.flezy.itemsblocker.manager.BlockManager;

import java.util.List;

public class BlockListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        ItemStack item = event.getItem();
        if (BlockManager.isBlocked(item)) {
            item.setAmount(0);
        }
    }

    @EventHandler
    public void onCraft(PrepareItemCraftEvent event){
        ItemStack item = event.getInventory().getResult();
        if (BlockManager.isBlocked(item)) {
            event.getInventory().setResult(null);
        }
    }

    @EventHandler
    public void onBrew(BrewEvent event){
        List<ItemStack> items = event.getResults();
        for (ItemStack item : items) {
            if (BlockManager.isBlocked(item)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onTrade(VillagerAcquireTradeEvent event){
        ItemStack item = event.getRecipe().getResult();
        if (BlockManager.isBlocked(item)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if (event.getWhoClicked().hasPermission("itemsblocker.bypass")) return;

        ItemStack item = event.getCurrentItem();
        if (BlockManager.isBlocked(item)) item.setAmount(0);
    }

    @EventHandler
    public void onSwapItems(PlayerSwapHandItemsEvent event){
        if (event.getPlayer().hasPermission("itemsblocker.bypass")) return;

        ItemStack offHandItem = event.getOffHandItem();
        if (BlockManager.isBlocked(offHandItem)) {
            offHandItem.setAmount(0);
        }

        ItemStack mainHandItem = event.getMainHandItem();
        if (BlockManager.isBlocked(mainHandItem)) {
            mainHandItem.setAmount(0);
        }
    }

    @EventHandler
    public void onEntityPickUp(EntityPickupItemEvent event){
        if (event.getEntity().hasPermission("itemsblocker.bypass")) return;

        ItemStack item = event.getItem().getItemStack();
        if (BlockManager.isBlocked(item)) {
            event.getItem().remove();
        }
    }

    @EventHandler
    public void onEnchant(EnchantItemEvent event){
        if (BlockManager.checkEnchant(event.getEnchantsToAdd())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onSmith(SmithItemEvent event) {
        if (event.getInventory().getResult() != null) {
            Material result = event.getInventory().getResult().getType();
            if (ItemsBlocker.getInstance().getData().blockNetherite &&
            List.of(Material.NETHERITE_AXE,Material.NETHERITE_HOE,Material.NETHERITE_PICKAXE, Material.NETHERITE_SHOVEL,
                    Material.NETHERITE_HELMET, Material.NETHERITE_CHESTPLATE, Material.NETHERITE_LEGGINGS,
                    Material.NETHERITE_BOOTS, Material.NETHERITE_SWORD).contains(result)) {
                event.setResult(Event.Result.DENY);
            }
        }
    }
}
