package com.github.laefye.rpsign;

import com.github.laefye.kublik.text.Text;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class LangConfig {

    private final RPSign plugin;
    private final File configFile;
    private YamlConfiguration config;

    public LangConfig(RPSign plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "lang.yml");
    }

    public void loadConfig() {
        if (!configFile.exists()) {
            plugin.saveResource("lang.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public String getString(String path) {
        if (!config.contains(path)) {
            return path;
        }
        var str = config.getString(path);
        return Text.format(str == null ? "" : str);
    }
}

