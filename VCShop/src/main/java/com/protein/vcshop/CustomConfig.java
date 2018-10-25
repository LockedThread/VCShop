package com.protein.vcshop;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Collectors;

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

    public HashMap<String, Integer> readMap(String token) {
        return yamlConfiguration
                .getConfigurationSection(token)
                .getKeys(false)
                .stream()
                .collect(Collectors.toMap(key -> key, key -> yamlConfiguration.getInt(token + "." + key), (a, b) -> b, HashMap::new));
    }

    public void writeMap(String token, HashMap<String, Integer> map) {
        yamlConfiguration.set(token, null);
        map.keySet().forEach(k -> yamlConfiguration.set(token + "." + k, map.get(k)));
    }
}
