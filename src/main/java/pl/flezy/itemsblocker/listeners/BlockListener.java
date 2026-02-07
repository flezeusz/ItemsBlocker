package pl.flezy.itemsblocker.listeners;

import io.papermc.paper.event.block.BlockPreDispenseEvent;
import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import org.bukkit.entity.Item;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import pl.flezy.itemsblocker.manager.BlockManager;
import pl.flezy.itemsblocker.manager.SmithingManager;

import java.util.List;

public class BlockListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if (event.getPlayer().hasPermission("itemsblocker.bypass")) return;

        ItemStack item = event.getItem();
        if (BlockManager.isBlocked(item)) {
            item.setAmount(0);
        }
    }

    @EventHandler
    public void onSlotChange(PlayerInventorySlotChangeEvent event) {
        if (event.getPlayer().hasPermission("itemsblocker.bypass")) return;

        ItemStack item = event.getNewItemStack();
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
    public void onItemHeld(PlayerItemHeldEvent event) {
        if (event.getPlayer().hasPermission("itemsblocker.bypass")) return;

        PlayerInventory inventory = event.getPlayer().getInventory();

        ItemStack newItem = inventory.getItem(event.getNewSlot());
        if (BlockManager.isBlocked(newItem)) {
            newItem.setAmount(0);
        }

        ItemStack oldItem = inventory.getItem(event.getPreviousSlot());
        if (BlockManager.isBlocked(oldItem)) {
            oldItem.setAmount(0);
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
    public void onPreDispense(BlockPreDispenseEvent event) {
        ItemStack item = event.getItemStack();
        if (BlockManager.isBlocked(item)) {
            event.setCancelled(true);
            item.setAmount(0);
        }
    }

    @EventHandler
    public void onBlockDrop(BlockDropItemEvent event) {
        for (Item item : event.getItems()) {
            ItemStack itemStack = item.getItemStack();
            if (BlockManager.isBlocked(itemStack)) {
                item.remove();
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if (event.getWhoClicked().hasPermission("itemsblocker.bypass")) return;

        ItemStack item = event.getCurrentItem();
        if (BlockManager.isBlocked(item)) {
            item.setAmount(0);
        }
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
    public void onEntityPickup(EntityPickupItemEvent event){
        if (event.getEntity().hasPermission("itemsblocker.bypass")) return;

        ItemStack item = event.getItem().getItemStack();
        if (BlockManager.isBlocked(item)) {
            event.setCancelled(true);
            event.getItem().remove();
        }
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent event){
        if (event.getPlayer().hasPermission("itemsblocker.bypass")) return;

        Item item = event.getItemDrop();
        ItemStack itemStack = item.getItemStack();
        if (BlockManager.isBlocked(itemStack)) {
            itemStack.setAmount(0);
            item.remove();
        }
    }

    @EventHandler
    public void onEntityDrop(EntityDropItemEvent event){
        Item item = event.getItemDrop();
        ItemStack itemStack = item.getItemStack();
        if (BlockManager.isBlocked(itemStack)) {
            item.remove();
        }
    }

    @EventHandler
    public void onEnchant(EnchantItemEvent event){
        if (BlockManager.areEnchantmentsBlocked(event.getEnchantsToAdd())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onSmith(SmithItemEvent event) {
        if (SmithingManager.isNetheriteSmithingBlocked()) {
            ItemStack item = event.getInventory().getResult();
            if (item != null && SmithingManager.isNetheriteItem(item)) {
                event.setResult(Event.Result.DENY);
            }
        }
    }
}
