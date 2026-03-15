package pl.flezy.itemsblocker.manager;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.flezy.itemsblocker.ItemsBlocker;

import java.util.List;
import java.util.Map;

public class BlockManager {

    public static boolean isBlocked(@Nullable ItemStack item) {
        if (item == null) return false;

        if (isMaterialBlocked(item.getType())) return true;

        if (!item.hasItemMeta()) return false;
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) return false;

        if (itemMeta instanceof EnchantmentStorageMeta enchantmentStorageMeta &&
                areEnchantmentsBlocked(enchantmentStorageMeta.getStoredEnchants()))
            return true;

        if (areEnchantmentsBlocked(itemMeta.getEnchants()))
            return true;

        return itemMeta instanceof PotionMeta potionMeta &&
                arePotionsBlocked(potionMeta.getBasePotionType());
    }

    public static boolean isMaterialBlocked(Material material) {
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

    public static boolean areEnchantmentsBlocked(@NotNull Map<Enchantment,Integer> enchantmentsMap){
        for (Map.Entry<Enchantment,Integer> enchantmentEntry : enchantmentsMap.entrySet()) {
            Enchantment enchantment = enchantmentEntry.getKey();
            Integer level = enchantmentEntry.getValue();
            if (isEnchantmentBlocked(enchantment, level)) {
                return true;
            }
        }
        return false;
    }

    public static boolean arePotionsBlocked(@Nullable PotionType potionType) {
        if (potionType == null) return false;
        List<PotionEffect> potionsList = potionType.getPotionEffects();
        for (PotionEffect potion : potionsList) {
            if (isPotionBlocked(potion)) {
                return true;
            }
        }
        return false;
    }
}
