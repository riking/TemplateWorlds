package com.github.riking.templateworlds.api;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;

public interface ApiMain {
    /**
     * Please do not call this method.
     */
    public void onEnable(Plugin templateWorlds);

    /**
     * Please do not call this method.
     */
    public void onDisable(Plugin templateWorlds);

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
     * Get a templated ChunkGenerator for the given world.
     * <p>
     * This allows people to create templated worlds from the command line
     * with Multiverse or from bukkit.yml.
     *
     * @param world the world this is being created for
     * @param template a string that should be the template world
     * @return a ChunkGenerator that you can give to a WorldCreator to make a
     *         templated world
     */
    public ChunkGenerator getTemplatedGenerator(String world, String template);

    /**
     * Get a ChunkGenerator that will only create empty (all air / void)
     * chunks. This is useful when loading the world to use as the template,
     * as it is possible for the templated world to cause new chunks to be
     * generated on the template when that is not desirable.
     *
     * @return a ChunkGenerator that will only create empty chunks
     */
    public ChunkGenerator getVoidGenerator();

    /**
     * Check whether the given World was created by this plugin.
     *
     * @param world a world that may or may not be templated
     * @return if the world was templated
     */
    public boolean isTemplatedWorld(World world);

    /**
     * Reset all chunks in the given area to the chunks in the template world
     * over time. The provided Runnable will be executed once the reset is
     * complete.
     *
     * @param templated A templated world
     * @param chunkMinX low chunk-coord X to regenerate
     * @param chunkMinZ low chunk-coord Z to regenerate
     * @param chunkMaxX high chunk-coord X to regenerate
     * @param chunkMaxZ high chunk-coord Z to regenerate
     * @param callback A Runnable to execute once the resetting is completed.
     *            It will be executed on the server main thread. May be null.
     * @throws IllegalArgumentException if max < min
     * @throws IllegalArgumentException if the provided world is not a
     *             templated world
     */
    public void resetAreaGradually(World templated, int chunkMinX, int chunkMinZ, int chunkMaxX, int chunkMaxZ, Runnable callback);

    /**
     * Reset all chunks in the area bounded in X and Z by the two locations to
     * the chunks in the template world over time. The provided Runnable will
     * be executed once the reset is complete.
     *
     * @param templated A templated world
     * @param a one corner of the bounding box
     * @param b opposite corner of the bounding box
     * @param callback A Runnable to execute once the resetting is completed.
     *            It will be executed on the server main thread. May be null.
     * @throws IllegalArgumentException if the provided world is not a
     *             templated world
     */
    public void resetAreaGradually(World templated, Location a, Location b, Runnable callback);

    /**
     * Reset all chunks in the given area to the chunks in the template world
     * over time. The provided Runnable will be executed once the reset is
     * complete.
     *
     * @param templated A templated world
     * @param chunkMinX low chunk-coord X to regenerate
     * @param chunkMinZ low chunk-coord Z to regenerate
     * @param chunkMaxX high chunk-coord X to regenerate
     * @param chunkMaxZ high chunk-coord Z to regenerate
     * @param callback A Runnable to execute once the resetting is completed.
     *            It will be executed on the server main thread. May be null.
     * @param timeAllotment What FRACTION of 1/20th of a second (one tick) to
     *            spend doing the reset. If the allotment is less than the
     *            time it takes to reset a chunk, only 1 chunk will be reset
     *            per tick. Be warned that values in excess of 100 billion may
     *            also exhibit this behavior, due to overflow of the long
     *            type.
     * @throws IllegalArgumentException if max < min
     * @throws IllegalArgumentException if the provided world is not a
     *             templated world
     */
    public void resetAreaGradually(World templated, int chunkMinX, int chunkMinZ, int chunkMaxX, int chunkMaxZ, Runnable callback, double timeAllotment);

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

    /**
     * Change the template for the given templated world.
     * <p>
     * After changing the template, you must reset the area for the changes to
     * take effect.
     *
     * @param templated A templated world
     * @param newTemplate The world to use as the new template
     * @throws IllegalArgumentException if the provided world is not a
     *             templated world
     */
    public void changeTemplate(World templated, World newTemplate);
}
