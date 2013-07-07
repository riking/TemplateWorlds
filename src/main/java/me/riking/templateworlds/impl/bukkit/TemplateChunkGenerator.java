package me.riking.templateworlds.impl.bukkit;

import java.util.Random;

import me.riking.templateworlds.impl.common.TemplatedGenerator;

import org.bukkit.ChunkSnapshot;
import org.bukkit.World;

public class TemplateChunkGenerator extends TemplatedGenerator {
    public TemplateChunkGenerator(World world) {
        super(world);
    }

    @Override
    public short[][] generateExtBlockSections(World contextWorld, Random worldRandom, int cx, int cz, BiomeGrid biomes) {
        ChunkSnapshot templateChunk = templateWorld.getChunkAt(cx, cz).getChunkSnapshot(true, true, false);

        // Load block IDs
        short[][] result = new short[256 / 16][];
        for (int y = 0; y < 256; y += 16) {
            short[] locres = new short[4096];
            for (int py = y; py < y + 16; py++) {
                for (int px = 0; px < 16; px++) {
                    for (int pz = 0; pz < 16; pz++) {
                        locres[((py & 0xF) << 8) | (pz << 4) | px] = (short) templateChunk.getBlockTypeId(px, py, pz);
                    }
                }
            }
            result[y >> 4] = locres;
        }

        // Biomes
        loadBiomesIntoGrid(biomes, templateChunk);

        return result;
    }

    public void loadBiomesIntoGrid(BiomeGrid biomes, ChunkSnapshot template) {
        for (int px = 0; px < 16; px++) {
            for (int pz = 0; pz < 16; pz++) {
                biomes.setBiome(px, pz, template.getBiome(px, pz));
            }
        }
    }
}