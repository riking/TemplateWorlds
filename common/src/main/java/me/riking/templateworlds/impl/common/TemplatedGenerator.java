package me.riking.templateworlds.impl.common;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public abstract class TemplatedGenerator extends ChunkGenerator {
    public final World templateWorld;

    public TemplatedGenerator(World world) {
        templateWorld = world;
    }
}
