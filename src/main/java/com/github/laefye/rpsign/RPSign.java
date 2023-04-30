package com.github.laefye.rpsign;

import com.github.laefye.rpsign.api.ServiceManager;
import com.github.laefye.rpsign.events.PlayerEvents;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class RPSign extends JavaPlugin {
    private final ServiceManager serviceManager = new ServiceManager();
    private final SignManager signManager = new SignManager(this);
    private final LangConfig langConfig = new LangConfig(this);

    @Override
    public void onEnable() {
        createFile();
        getServer().getPluginManager().registerEvents(new PlayerEvents(this), this);
    }

    private void createFile() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        if (!new File(getDataFolder(), "signs").exists()) {
            new File(getDataFolder(), "signs").mkdir();
        }
        langConfig.loadConfig();
    }

    public ServiceManager getServiceManager() {
        return serviceManager;
    }

    public SignManager getSignManager() {
        return signManager;
    }

    @Override
    public void onDisable() {
    }

    public LangConfig getLangConfig() {
        return langConfig;
    }
}
