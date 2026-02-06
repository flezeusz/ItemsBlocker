package pl.flezy.itemsblocker.manager;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import pl.flezy.itemsblocker.ItemsBlocker;

import java.util.List;

public class SmithingManager {
    public static boolean isNetheriteSmithingBlocked() {
        return ItemsBlocker.getInstance().getData().blockNetherite;
    }

    public static boolean isNetheriteItem(@NotNull ItemStack item) {
        return List.of(Material.NETHERITE_AXE,Material.NETHERITE_HOE,Material.NETHERITE_PICKAXE, Material.NETHERITE_SHOVEL,
                Material.NETHERITE_HELMET, Material.NETHERITE_CHESTPLATE, Material.NETHERITE_LEGGINGS,
                Material.NETHERITE_BOOTS, Material.NETHERITE_SWORD).contains(item.getType());
    }
}
