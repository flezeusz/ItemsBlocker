package pl.flezy.itemsblocker;

import co.aikar.commands.PaperCommandManager;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;
import pl.flezy.itemsblocker.commands.BlockCommand;
import pl.flezy.itemsblocker.config.ConfigurationFactory;
import pl.flezy.itemsblocker.config.Data;
import org.bukkit.plugin.java.JavaPlugin;
import pl.flezy.itemsblocker.listeners.BlockListener;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public final class ItemsBlocker extends JavaPlugin {
    private final File dataConfigurationFile = new File(this.getDataFolder(), "data.yml");

    private static ItemsBlocker instance;
    private Data data;

    @Override
    public void onEnable() {
        instance = this;
        this.data = ConfigurationFactory.createDataConfiguration(this.dataConfigurationFile);

        registerCommands();
        registerListeners(List.of(
                new BlockListener()
        ));
    }

    private void registerCommands() {
        PaperCommandManager manager = new PaperCommandManager(this);

        manager.enableUnstableAPI("help");

        manager.getCommandCompletions().registerCompletion("enchantments", c ->
                Registry.ENCHANTMENT
                        .stream()
                        .map(Enchantment::getKey)
                        .map(NamespacedKey::getKey)
                        .collect(Collectors.toSet()));

        manager.getCommandCompletions().registerCompletion("potionEffects", c ->
                Registry.POTION_EFFECT_TYPE.stream()
                        .map(PotionEffectType::getKey)
                        .map(NamespacedKey::getKey)
                        .collect(Collectors.toSet()));

        manager.getCommandCompletions().registerCompletion("enchantmentsRemove", c ->
                data.blockedEnchants
                        .keySet().stream()
                        .map(Enchantment::getKey)
                        .map(NamespacedKey::getKey)
                        .collect(Collectors.toSet()));

        manager.getCommandCompletions().registerCompletion("potionEffectsRemove", c ->
                data.blockedPotions
                        .keySet().stream()
                        .map(PotionEffectType::getKey)
                        .map(NamespacedKey::getKey)
                        .collect(Collectors.toSet()));

        manager.getCommandCompletions().registerCompletion("itemsRemove", c ->
                data.blockedMaterials
                        .stream()
                        .map(Material::name)
                        .collect(Collectors.toSet()));

        manager.registerCommand(new BlockCommand());
    }

    private void registerListeners(List<Listener> listeners) {
        listeners.forEach(listener -> getServer().getPluginManager().registerEvents(listener,this));
    }

    public static ItemsBlocker getInstance() {
        return instance;
    }

    public Data getData() {
        return data;
    }

    public void reloadConfiguration() {
        this.data.load();
    }
}
