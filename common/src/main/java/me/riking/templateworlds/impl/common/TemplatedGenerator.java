package me.riking.templateworlds.impl.common;

import java.util.List;

import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public abstract class TemplatedGenerator extends ChunkGenerator {
    public final World templateWorld;

    public TemplatedGenerator(World world) {
        templateWorld = world;
    }

    /**
     * This method must be implemented.
     */
    @Override
    public abstract List<BlockPopulator> getDefaultPopulators(World target);
}
