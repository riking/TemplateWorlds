package com.github.riking.templateworlds.impl.common;

import java.util.Random;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

/**
 * Returns nothing but empty chunks.
 */
public class VoidGenerator extends ChunkGenerator {

    public VoidGenerator() {
    }

    @Override
    public short[][] generateExtBlockSections(World contextWorld, Random worldRandom, int cx, int cz, BiomeGrid biomes) {
        return new short[0][];
    }
}
