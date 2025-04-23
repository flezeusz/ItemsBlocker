package com.epicplayera10.exampleplugin.config;

import com.epicplayera10.exampleplugin.ExamplePlugin;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.serdes.commons.SerdesCommons;
import eu.okaeri.configs.validator.okaeri.OkaeriValidator;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;

import java.io.File;

public class ConfigurationFactory {

    private ConfigurationFactory(){
    }

    public static DataConfiguration createDataConfiguration(File dataConfigurationFile) {
        return ConfigManager.create(DataConfiguration.class, (it) -> {
            it.withConfigurer(new OkaeriValidator(new YamlBukkitConfigurer()));
            it.withSerdesPack(registry -> {
                registry.register(new SerdesCommons());
                registry.register(new SerdesBukkit());
            });

            it.withBindFile(dataConfigurationFile);
            it.withLogger(ExamplePlugin.instance().getLogger());
            it.saveDefaults();
            it.load(true);
        });
    }
}
