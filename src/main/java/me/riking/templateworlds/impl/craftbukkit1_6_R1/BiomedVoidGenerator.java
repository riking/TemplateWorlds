package me.riking.templateworlds.impl.craftbukkit1_6_R1;

import java.util.Random;

import me.riking.templateworlds.impl.common.TemplatedGenerator;

import org.bukkit.ChunkSnapshot;
import org.bukkit.World;

public class BiomedVoidGenerator extends TemplatedGenerator {
    public BiomedVoidGenerator(World world) {
        super(world);
    }

    public short[][] generateExtBlockSections(World world, Random random, int x, int z, BiomeGrid biomes) {
        ChunkSnapshot templateChunk = templateWorld.getChunkAt(x, z).getChunkSnapshot(true, true, false);
        for (int px = 0; px < 16; px++) {
            for (int pz = 0; pz < 16; pz++) {
                biomes.setBiome(px, pz, templateChunk.getBiome(px, pz));
            }
        }
        short[][] ret = new short[256 / 16][];
        for (int i = 0; i < 16; ret[i++] = new short[4096]);
        return ret;
    }
}
