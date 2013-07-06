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
    }

    /**
     * Get the ApiMain class, which is used for all operations.
     * <p>
     * Please don't try to instanceof the ApiMain, you shouldn't need to.
     *
     * @return the ApiMain
     */
    public ApiMain getApi() {
        return api;
    }
}
