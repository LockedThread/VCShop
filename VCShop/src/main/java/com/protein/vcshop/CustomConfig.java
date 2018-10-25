package com.protein.vcshop;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class CustomConfig {

    private File file;
    private YamlConfiguration yamlConfiguration;

    public CustomConfig(Plugin plugin, String name, boolean packaged) {
        this.file = new File(plugin.getDataFolder(), name);
        if ( !file.exists() ) {
            file.getParentFile().mkdirs();
            if ( packaged ) plugin.saveResource(name, false);
        }
        this.yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public YamlConfiguration getYamlConfiguration() {
        return yamlConfiguration;
    }

    public void save() {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
