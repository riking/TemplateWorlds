package me.riking.templateworlds.api;

import me.riking.templateworlds.impl.bukkit.BukkitApiMain;
import me.riking.templateworlds.impl.craftbukkit.CBApiMain;

import org.bukkit.plugin.java.JavaPlugin;

public class TemplateWorlds extends JavaPlugin {
    private ApiMain api;
    private static TemplateWorlds instance;

    /**
     * Get the instance of the plugin.
     * @return the plugin instance, or null if disabled
     */
    public static TemplateWorlds getInstance() {
        return instance;
    }

    /**
     * Get the ApiMain class, which is used for all operations.
     *
     * @return the ApiMain
     */
    public ApiMain getApi() {
        return api;
    }

    /**
     * Get the ApiMain class, which is used for all operations.
     *
     * @return the ApiMain
     */
    public static ApiMain getApiStatic() {
        return instance.api;
    }

    @Override
    public void onLoad() {
    }

    @Override
    public void onEnable() {
        instance = this;
        try {
            api = new CBApiMain(this);
            getLogger().info("TemplateWorlds loaded with CraftBukkit loader!");
        } catch (Throwable t) {
            api = new BukkitApiMain(this);
            getLogger().info("TemplateWorlds loaded with Bukkit-only loader!");
        }
        api.onEnable();
    }

    @Override
    public void onDisable() {
        api.onDisable();
        instance = null;
    }
}
