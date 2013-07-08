package com.github.riking.templateworlds.impl.craftbukkit1_5_R3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;


import org.bukkit.ChunkSnapshot;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import com.github.riking.templateworlds.impl.common.TemplatedGenerator;

public class BiomedVoidGenerator extends TemplatedGenerator {
    private List<BlockPopulator> blockPopulators;

    public BiomedVoidGenerator(World world) {
        super(world);
        blockPopulators = new ArrayList<BlockPopulator>(1);
        blockPopulators.add(new OBCTemplateBlockPopulator(world));
    }

    public BiomedVoidGenerator(World world, Collection<BlockPopulator> extraPops) {
        this(world);
        blockPopulators.addAll(extraPops);
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World target) {
        return blockPopulators;
    }

    public short[][] generateExtBlockSections(World world, Random random, int x, int z, BiomeGrid biomes) {
        ChunkSnapshot templateChunk = templateWorld.getChunkAt(x, z).getChunkSnapshot(true, true, false);
        for (int px = 0; px < 16; px++) {
            for (int pz = 0; pz < 16; pz++) {
                biomes.setBiome(px, pz, templateChunk.getBiome(px, pz));
            }
        }
        short[][] ret = new short[256 / 16][];
        for (int i = 0; i < 16; ret[i++] = new short[4096])
            ;
        return ret;
    }
}
