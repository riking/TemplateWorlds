package me.riking.templateworlds.impl.craftbukkit;

import java.util.List;
import java.util.Random;

import net.minecraft.server.v1_6_R1.Chunk;
import net.minecraft.server.v1_6_R1.Entity;
import net.minecraft.server.v1_6_R1.EntityHuman;
import net.minecraft.server.v1_6_R1.EntityTypes;
import net.minecraft.server.v1_6_R1.NBTTagCompound;
import net.minecraft.server.v1_6_R1.TileEntity;
import net.minecraft.server.v1_6_R1.WorldServer;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_6_R1.CraftChunk;
import org.bukkit.craftbukkit.v1_6_R1.CraftWorld;
import org.bukkit.generator.BlockPopulator;

public class OBCTemplateBlockPopulator extends BlockPopulator {
    private final World templateWorld;

    public OBCTemplateBlockPopulator(World world) {
        templateWorld = world;
    }

    @Override
    public void populate(World world, Random random, org.bukkit.Chunk tar) {
        CraftChunk targetChunk = (CraftChunk) tar;
        CraftChunk sourceChunk = (CraftChunk) templateWorld.getChunkAt(targetChunk.getX(), targetChunk.getZ());
        WorldServer targetWorld = ((CraftWorld) world).getHandle();

        Chunk source = sourceChunk.getHandle();
        Chunk target = targetChunk.getHandle();

        // Copy ChunkSections (blocks, data, light)
        target.a(source.i());

        // Copy Entities
        for (List<?> l : source.entitySlices) {
            for (Object o : l) {
                if (!(o instanceof Entity)) {
                    continue;
                }
                if (o instanceof EntityHuman) {
                    continue;
                }
                Entity orig = (Entity) o;
                NBTTagCompound tag = new NBTTagCompound();
                // Save entity to NBT
                orig.e(tag);
                tag.setString("id", EntityTypes.b(orig));
                // Reload the entity in target world, add to target chunk
                target.a(EntityTypes.a(tag, targetWorld));
            }
        }

        // Copy Tile Entities
        for (Object o : source.tileEntities.values()) {
            if (!(o instanceof TileEntity)) {
                continue;
            }
            TileEntity orig = (TileEntity) o;
            NBTTagCompound tag = new NBTTagCompound();
            // Save tile entity to NBT
            orig.b(tag);
            // Reload the tile entity, add to target chunk
            target.a(TileEntity.c(tag));
        }

        // Add the entities to the world
        target.addEntities();

        // Request cleanup
        sourceChunk = null;
        templateWorld.unloadChunkRequest(targetChunk.getX(), targetChunk.getZ());
    }
}
