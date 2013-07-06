package me.riking.templateworlds.impl.common;

import me.riking.templateworlds.api.ApiMain;

import org.apache.commons.lang.Validate;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

public abstract class BaseApiMain implements ApiMain {

    @Override
    public boolean isTemplatedWorld(World world) {
        return world != null && (world.getGenerator() instanceof TemplatedGenerator);
    }

    protected void validateTemplatedWorld(World world) {
        Validate.isTrue(isTemplatedWorld(world), "You must provide a templated world");
    }

    @Override
    public void resetArea(World templated, int chunkMinX, int chunkMinZ, int chunkMaxX, int chunkMaxZ) {
        validateTemplatedWorld(templated);
        Validate.isTrue(chunkMinX <= chunkMaxX);
        Validate.isTrue(chunkMinZ <= chunkMaxZ);

        for (int cx = chunkMinX; cx <= chunkMaxX; cx++) {
            for (int cz = chunkMinZ; cz <= chunkMaxZ; cz++) {
                templated.regenerateChunk(cx, cz);
            }
        }
    }

    @Override
    public void resetArea(World templated, Location a, Location b) {
        validateTemplatedWorld(templated);

        Chunk ca = a.getChunk();
        Chunk cb = b.getChunk();
        int cax = ca.getX();
        int caz = ca.getZ();
        int cbx = cb.getX();
        int cbz = cb.getZ();
        int lowX  =  (cax < cbx) ? cax : cbx;
        int highX = !(cax < cbx) ? cax : cbx;
        int lowZ  =  (caz < cbz) ? caz : cbz;
        int highZ = !(caz < cbz) ? caz : cbz;

        for (int cx = lowX; cx <= highX; cx++) {
            for (int cz = lowZ; cz <= highZ; cz++) {
                templated.regenerateChunk(cx, cz);
            }
        }
    }
}
