package pl.flezy.itemsblocker.listeners;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import pl.flezy.itemsblocker.ItemsBlocker;

import java.util.List;
import java.util.Map;

public class BlockListener implements Listener {
    private final ItemsBlocker plugin = ItemsBlocker.instance();

    @EventHandler
    private void onCraft(PrepareItemCraftEvent event){
        ItemStack item = event.getInventory().getResult();
        if (checkItem(item))
            event.getInventory().setResult(null);
    }

    @EventHandler
    private void onBrew(BrewEvent event){
        List<ItemStack> items = event.getResults();
        for(ItemStack item : items)
            if(checkItem(item))
                event.setCancelled(true);
    }

    @EventHandler
    private void onTrade(VillagerAcquireTradeEvent event){
        ItemStack item = event.getRecipe().getResult();
        if (checkItem(item))
            event.setCancelled(true);
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent event){
        if (event.getWhoClicked() instanceof Player player && player.hasPermission("itemsblocker.bypass")) return;

        ItemStack item = event.getCurrentItem();
        if (checkItem(item))
            item.setAmount(0);
    }

    @EventHandler
    private void onSwapItems(PlayerSwapHandItemsEvent event){
        if (event.getPlayer().hasPermission("itemsblocker.bypass")) return;

        ItemStack item = event.getOffHandItem();
        if (checkItem(item))
            item.setAmount(0);
    }

    @EventHandler
    private void onEntityPickUp(EntityPickupItemEvent event){
        if (event.getEntity() instanceof Player player && player.hasPermission("itemsblocker.bypass")) return;

        ItemStack item = event.getItem().getItemStack();
        if (checkItem(item))
            event.getItem().remove();
    }

    @EventHandler
    private void onEnchant(EnchantItemEvent event){
        if (checkEnchant(event.getEnchantsToAdd()))
            event.setCancelled(true);
    }

    private boolean checkItem(ItemStack item){
        if (item == null) return false;
        Material itemType = item.getType();
        if (plugin.dataConfiguration().blockedMaterials.contains(itemType))
            return true;

        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) return false;

        if (itemMeta instanceof EnchantmentStorageMeta est && checkEnchant(est.getStoredEnchants()))
            return true;

        if (checkEnchant(itemMeta.getEnchants()))
            return true;

        if (itemMeta instanceof PotionMeta pm && checkPotion(pm.getBasePotionType().getPotionEffects()))
            return true;

        return false;
    }

    private boolean checkEnchant(Map<Enchantment,Integer> enchants){
        for (Map.Entry<Enchantment,Integer> enchant : enchants.entrySet()){
            if (plugin.dataConfiguration().blockedEnchants.containsKey(enchant.getKey()) &&
                    plugin.dataConfiguration().blockedEnchants.get(enchant.getKey()) <= enchant.getValue())
                return true;
        }
        return false;
    }

    private boolean checkPotion(List<PotionEffect> potions){
        for (PotionEffect potion : potions){
            if (plugin.dataConfiguration().blockedPotions.containsKey(potion.getType()) &&
                    plugin.dataConfiguration().blockedPotions.get(potion.getType())-1 <= potion.getAmplifier()) {
                return true;
            }
        }
        return false;
    }

    @EventHandler
    private void onSmith(SmithItemEvent event) {
        if (event.getInventory().getResult() != null) {
            Material result = event.getInventory().getResult().getType();
            if (plugin.dataConfiguration().netherite) {
                if (result == Material.NETHERITE_AXE ||
                        result == Material.NETHERITE_HOE ||
                        result == Material.NETHERITE_PICKAXE ||
                        result == Material.NETHERITE_SHOVEL ||
                        result == Material.NETHERITE_HELMET ||
                        result == Material.NETHERITE_CHESTPLATE ||
                        result == Material.NETHERITE_LEGGINGS ||
                        result == Material.NETHERITE_BOOTS ||
                        result == Material.NETHERITE_SWORD) {
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }
}
