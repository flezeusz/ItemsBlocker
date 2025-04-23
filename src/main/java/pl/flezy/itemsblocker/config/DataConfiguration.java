package pl.flezy.itemsblocker.config;

import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DataConfiguration extends OkaeriConfig {
    public Set<Material> blockedMaterials = new HashSet<>();

    public Map<Enchantment,Integer> blockedEnchants = new HashMap<>();

    public Map<PotionEffectType,Integer> blockedPotions = new HashMap<>();

    public boolean netherite = true;

}
