package me.riking.templateworlds.api;

import me.riking.templateworlds.impl.bukkit.BukkitApiMain;
import me.riking.templateworlds.impl.craftbukkit.CBApiMain;

import org.bukkit.plugin.java.JavaPlugin;

public class TemplateWorlds extends JavaPlugin {
    private ApiMain api;

    @Override
    public void onLoad() {
    }

    @Override
    public void onEnable() {
        try {
            api = new CBApiMain(this);
        } catch (Throwable t) {
            api = new BukkitApiMain(this);
        }
        api.onEnable();
    }

    @Override
    public void onDisable() {
        api.onDisable();
    }

    public ApiMain getApi() {
        return api;
    }
}
