package pl.flezy.itemsblocker.manager;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.Nullable;
import pl.flezy.itemsblocker.ItemsBlocker;

import java.util.List;
import java.util.Map;

public class BlockManager {

    public static boolean isBlocked(ItemStack item){
        if (item == null) return false;

        if (isMaterialBlocked(item.getType())) return true;

        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) return false;

        if (itemMeta instanceof EnchantmentStorageMeta enchantmentStorageMeta &&
                areEnchantmentsBlocked(enchantmentStorageMeta.getStoredEnchants()))
            return true;

        if (areEnchantmentsBlocked(itemMeta.getEnchants()))
            return true;

        return itemMeta instanceof PotionMeta potionMeta &&
                checkPotion(potionMeta.getBasePotionType().getPotionEffects());
    }

    public static boolean isMaterialBlocked(Material material){
        return ItemsBlocker.getInstance().getData().blockedMaterials.contains(material);
    }

    public static boolean isEnchantmentBlocked(Enchantment enchantment, Integer level) {
        Integer blockedLevel = ItemsBlocker.getInstance().getData().blockedEnchantments.get(enchantment);
        return blockedLevel != null && blockedLevel <= level;
    }

    public static boolean isPotionBlocked(PotionEffect potionEffect) {
        int amplifier = potionEffect.getAmplifier();
        Integer blockedAmplifier = ItemsBlocker.getInstance().getData().blockedPotions.get(potionEffect.getType());
        return blockedAmplifier != null && blockedAmplifier <= amplifier;
    }

    public static boolean areEnchantmentsBlocked(Map<Enchantment,Integer> enchantmentsMap){
        for (Map.Entry<Enchantment,Integer> enchantmentEntry : enchantmentsMap.entrySet()) {
            Enchantment enchantment = enchantmentEntry.getKey();
            Integer level = enchantmentEntry.getValue();
            if (isEnchantmentBlocked(enchantment, level)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkPotion(@Nullable List<PotionEffect> potionsList){
        if (potionsList == null || potionsList.isEmpty()) return false;
        for (PotionEffect potion : potionsList) {
            if (isPotionBlocked(potion)) {
                return true;
            }
        }
        return false;
    }
}
