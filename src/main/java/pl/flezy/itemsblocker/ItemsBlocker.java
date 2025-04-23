package pl.flezy.itemsblocker;

import co.aikar.commands.PaperCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import pl.flezy.itemsblocker.commands.BlockCommand;
import pl.flezy.itemsblocker.config.ConfigurationFactory;
import pl.flezy.itemsblocker.config.DataConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import pl.flezy.itemsblocker.listeners.BlockListener;

import java.io.File;
import java.net.http.WebSocket;
import java.util.List;

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
