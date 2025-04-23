package pl.flezy.itemsblocker;

import co.aikar.commands.PaperCommandManager;
import com.google.common.collect.ImmutableList;
import org.bukkit.Registry;
import org.bukkit.event.Listener;
import pl.flezy.itemsblocker.commands.BlockCommand;
import pl.flezy.itemsblocker.config.ConfigurationFactory;
import pl.flezy.itemsblocker.config.DataConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import pl.flezy.itemsblocker.listeners.BlockListener;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class ItemsBlocker extends JavaPlugin {
    private final File dataConfigurationFile = new File(this.getDataFolder(), "data.yml");

    private DataConfiguration dataConfiguration;

    private static ItemsBlocker instance;

    @Override
    public void onEnable() {
        instance = this;

        this.dataConfiguration = ConfigurationFactory.createDataConfiguration(this.dataConfigurationFile);

        registerCommands();
        registerListeners(List.of(
                new BlockListener()
        ));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerCommands() {
        PaperCommandManager manager = new PaperCommandManager(this);

        manager.enableUnstableAPI("help");

        Set<String> enchantments = Registry.ENCHANTMENT.stream()
                .map(enchantment -> enchantment.getKey().getKey())
                .collect(Collectors.toSet());

        Set<String> potionEffects = Registry.POTION_EFFECT_TYPE.stream()
                .map(potionEffect -> potionEffect.getKey().getKey())
                .collect(Collectors.toSet());

        manager.getCommandCompletions().registerCompletion("enchantments", c -> enchantments);
        manager.getCommandCompletions().registerCompletion("potionEffects", c -> potionEffects);

        manager.registerCommand(new BlockCommand());
    }

    private void registerListeners(List<Listener> listeners) {
        listeners.forEach(listener -> getServer().getPluginManager().registerEvents(listener,this));
    }

    public static ItemsBlocker instance() {
        return instance;
    }

    public DataConfiguration dataConfiguration() {
        return dataConfiguration;
    }

    public void reloadConfiguration() {
        this.dataConfiguration.load();
    }
}
