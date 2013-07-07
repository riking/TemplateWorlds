package me.riking.templateworlds.impl.common;

import java.util.Iterator;

import me.riking.templateworlds.api.TemplateWorlds;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ResetWorldTask extends BukkitRunnable {
    // one tick is 50000000 nanos
    // one milli is 1000000 nanos, which may be system granularity
    public final static long ALLOCATION_NANOS = 2500000; // 5ms (1/10 tick)

    private final World target;
    private final Iterator<Vector> iter;
    private final Runnable callback;

    public ResetWorldTask(World target, ChunkAreaIterator iter, Runnable callback) {
        this.target = target;
        this.iter = iter;
        this.callback = callback;
    }

    @Override
    public void run() {
        long stopTime = System.nanoTime() + ALLOCATION_NANOS;
        do {
            if (iter.hasNext()) {
                Vector next = iter.next();
                target.regenerateChunk(next.getBlockX(), next.getBlockZ()); // actually chunk coords, but whatever
            } else {
                // Stop
                this.cancel();
                Bukkit.getScheduler().runTask(TemplateWorlds.getInstance(), callback);
                return;
            }
        } while (stopTime > System.nanoTime());
    }
}
