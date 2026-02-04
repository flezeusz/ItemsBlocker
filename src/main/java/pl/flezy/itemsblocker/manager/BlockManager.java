package pl.flezy.itemsblocker.manager;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import pl.flezy.itemsblocker.ItemsBlocker;

import java.util.List;
import java.util.Map;

public class BlockManager {
    public static boolean isBlocked(ItemStack item){
        if (item == null) return false;
        Material itemType = item.getType();
        if (ItemsBlocker.getInstance().getData().blockedMaterials.contains(itemType)) return true;

        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) return false;

        if (itemMeta instanceof EnchantmentStorageMeta enchantmentStorageMeta &&
                checkEnchant(enchantmentStorageMeta.getStoredEnchants()))
            return true;

        if (checkEnchant(itemMeta.getEnchants()))
            return true;

        return itemMeta instanceof PotionMeta potionMeta &&
                checkPotion(potionMeta.getBasePotionType().getPotionEffects());
    }

    public static boolean checkEnchant(Map<Enchantment,Integer> enchants){
        for (Map.Entry<Enchantment,Integer> enchant : enchants.entrySet()) {
            if (ItemsBlocker.getInstance().getData().blockedEnchants.containsKey(enchant.getKey()) &&
                    ItemsBlocker.getInstance().getData().blockedEnchants.get(enchant.getKey()) <= enchant.getValue())
                return true;
        }
        return false;
    }

    public static boolean checkPotion(List<PotionEffect> potions){
        if (potions == null || potions.isEmpty()) return false;
        for (PotionEffect potion : potions) {
            if (ItemsBlocker.getInstance().getData().blockedPotions.containsKey(potion.getType()) &&
                    ItemsBlocker.getInstance().getData().blockedPotions.get(potion.getType()) - 1 <= potion.getAmplifier()) {
                return true;
            }
        }
        return false;
    }
}
