package me.riking.templateworlds.api;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

public interface ApiMain {
    /**
     * Please do not call this method.
     */
    public void onEnable();

    /**
     * Please do not call this method.
     */
    public void onDisable();

    /**
     * Create a new templated world with the given name and template world
     *
     * @param name name of the world folder for the templated world
     * @param template the world to use as the template
     * @return a World whose templated chunks must now be regenerated
     */
    public World createWorld(String name, World template);

    /**
     * Create a templated world with the given name, template world, and extra
     * BlockPopulators from your plugin. (This will typically create a
     * different world each time, assuming the Populator uses the Random
     * argument.)
     *
     * @param name name of the world folder for the templated world
     * @param template the world to use as the template
     * @return a WorldCreator ready to create a World
     */
    public World createWorld(String name, World template, Collection<BlockPopulator> extraPopulators);

    /**
     * Check whether the given World was created by this plugin.
     *
     * @param world a world that may or may not be templated
     * @return if the world was templated
     */
    public boolean isTemplatedWorld(World world);

    /**
     * Reset all chunks in the given area to the chunks in the template world.
     *
     * @param templated A templated world
     * @param chunkMinX low chunk-coord X to regenerate
     * @param chunkMinZ low chunk-coord Z to regenerate
     * @param chunkMaxX high chunk-coord X to regenerate
     * @param chunkMaxZ high chunk-coord Z to regenerate
     * @throws IllegalArgumentException if max < min
     * @throws IllegalArgumentException if the provided world is not a
     *             templated world
     */
    public void resetArea(World templated, int chunkMinX, int chunkMinZ, int chunkMaxX, int chunkMaxZ);

    /**
     * Reset all chunks in the area bounded in X and Z by the two locations to
     * the chunks in the template world.
     *
     * @param templated A templated world
     * @param a one corner of the bounding box
     * @param b opposite corner of the bounding box
     * @throws IllegalArgumentException if the provided world is not a
     *             templated world
     */
    public void resetArea(World templated, Location a, Location b);
}
