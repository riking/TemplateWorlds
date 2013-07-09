package com.github.riking.templateworlds.plugin;


import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.riking.templateworlds.api.ApiMain;

public class TemplateWorlds extends JavaPlugin {
    private ApiMain api;
    private static TemplateWorlds instance;

    @Override
    public void onEnable() {
        instance = this;
        try {
            api = new com.github.riking.templateworlds.impl.craftbukkit1_6_R2.CBApiMain();
            getLogger().info("TemplateWorlds loaded with 1.6.1 CraftBukkit provider!");
        } catch (Throwable t) {
            try {
                api = new com.github.riking.templateworlds.impl.craftbukkit1_5_R3.CBApiMain();
                getLogger().info("TemplateWorlds loaded with 1.5.2 CraftBukkit provider!");
            } catch (Throwable t2) {
                try {
                    api = new com.github.riking.templateworlds.impl.bukkit.BukkitApiMain();
                    getLogger().info("TemplateWorlds loaded with Bukkit-only loader!");
                } catch (Throwable t3) {
                    getLogger().severe("Unable to load TemplateWorlds, even with Bukkit-only. Was there a breaking API change?");
                }
            }
        }
        api.onEnable(this);
        getServer().getServicesManager().register(ApiMain.class, api, this, ServicePriority.Low);
    }

    @Override
    public void onDisable() {
        api.onDisable(this);
        instance = null;
    }

    public static TemplateWorlds getInstance() {
        return instance;
    }

    public ApiMain getApi() {
        return api;
    }

    public static ApiMain getApiStatic() {
        return instance.api;
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String world, String name) {
        return api.getTemplatedGenerator(world, name);
    }
}
