package com.github.riking.templateworlds.impl.common;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ResetWorldTask extends BukkitRunnable {
    public static final long TICK_NANOS = 50000000;
    // one tick is 50000000 nanos
    // one milli is 1000000 nanos, which may be system granularity
    public static final long DEFAULT_ALLOCATION = 7500000; // 7.5ms (5ms = 1/10 tick)

    private final World target;
    private final Iterator<Vector> iter;
    private final Runnable callback;
    private final Plugin plugin;
    private final long allocation;

    public ResetWorldTask(World target, ChunkAreaIterator iter, Plugin plugin, Runnable callback) {
        this.target = target;
        this.iter = iter;
        this.plugin = plugin;
        this.callback = callback;
        this.allocation = DEFAULT_ALLOCATION;
    }

    public ResetWorldTask(World target, ChunkAreaIterator iter, Plugin plugin, Runnable callback, double timeFrac) {
        this.target = target;
        this.iter = iter;
        this.plugin = plugin;
        this.callback = callback;
        this.allocation = (long) (TICK_NANOS * timeFrac);
    }

    @Override
    public void run() {
        long stopTime = System.nanoTime() + allocation;
        do {
            if (iter.hasNext()) {
                Vector next = iter.next();
                target.regenerateChunk(next.getBlockX(), next.getBlockZ()); // actually chunk coords, but whatever
            } else {
                // Stop
                this.cancel();
                Bukkit.getScheduler().runTask(plugin, callback);
                return;
            }
        } while (stopTime > System.nanoTime());
    }
}
