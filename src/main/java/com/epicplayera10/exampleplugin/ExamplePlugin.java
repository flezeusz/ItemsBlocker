package com.epicplayera10.exampleplugin;

import co.aikar.commands.PaperCommandManager;
import com.epicplayera10.exampleplugin.commands.ExampleCommand;
import com.epicplayera10.exampleplugin.config.ConfigurationFactory;
import com.epicplayera10.exampleplugin.config.DataConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class ExamplePlugin extends JavaPlugin {
    private final File dataConfigurationFile = new File(this.getDataFolder(), "data.yml");

    private DataConfiguration dataConfiguration;

    private static ExamplePlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        this.dataConfiguration = ConfigurationFactory.createDataConfiguration(this.dataConfigurationFile);

        registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerCommands() {
        PaperCommandManager manager = new PaperCommandManager(this);

        manager.enableUnstableAPI("help");

        manager.registerCommand(new ExampleCommand());
    }

    public static ExamplePlugin instance() {
        return instance;
    }

    public DataConfiguration dataConfiguration() {
        return dataConfiguration;
    }

    public void reloadConfiguration() {
        this.dataConfiguration.load();
    }
}
