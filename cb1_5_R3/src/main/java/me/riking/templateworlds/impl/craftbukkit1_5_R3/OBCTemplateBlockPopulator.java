package me.riking.templateworlds.impl.craftbukkit1_5_R3;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

import net.minecraft.server.v1_5_R3.Chunk;
import net.minecraft.server.v1_5_R3.ChunkSection;
import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityHuman;
import net.minecraft.server.v1_5_R3.EntityTypes;
import net.minecraft.server.v1_5_R3.NBTTagCompound;
import net.minecraft.server.v1_5_R3.NibbleArray;
import net.minecraft.server.v1_5_R3.TileEntity;
import net.minecraft.server.v1_5_R3.WorldServer;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_5_R3.CraftChunk;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.generator.BlockPopulator;

public class OBCTemplateBlockPopulator extends BlockPopulator {
    public World templateWorld;
    private static Field extendedIdArray;
    private static Field skyLightArray;

    static {
        try {
            extendedIdArray = ChunkSection.class.getDeclaredField("extBlockIds");
            extendedIdArray.setAccessible(true);
            skyLightArray = ChunkSection.class.getDeclaredField("skyLight");
            skyLightArray.setAccessible(true);
        } catch (Exception e) {
        }
    }

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
        {
            ChunkSection[] sourceSections = source.i();
            ChunkSection[] targetSections = target.i();
            for (int i = 0; i < sourceSections.length; i++) {
                ChunkSection sSec = sourceSections[i];
                if (sSec == null) {
                    targetSections[i] = null;
                } else {
                    ChunkSection tSec = targetSections[i];
                    if (tSec == null) {
                        tSec = new ChunkSection(i, target.world.worldProvider.f); // from Chunk constructor
                    }
                    // Straight NibbleArray clones
                    tSec.setIdArray(sSec.getIdArray().clone());
                    tSec.setDataArray(new NibbleArray(sSec.getDataArray().a.clone(), 4)); // NibbleArray.rawData
                    tSec.setEmittedLightArray(new NibbleArray(sSec.getEmittedLightArray().a.clone(), 4));

                    // Could be null
                    NibbleArray nar = sSec.getExtendedIdArray();
                    if (nar != null) {
                        tSec.setExtendedIdArray(new NibbleArray(nar.a.clone(), 4));
                    } else {
                        if (tSec.getExtendedIdArray() != null) {
                            try {
                                extendedIdArray.set(tSec, null);
                            } catch (Exception e) {
                                tSec.setExtendedIdArray(new NibbleArray(4096, 4));
                            }
                        }
                    }
                    nar = sSec.getSkyLightArray();
                    if (nar != null) {
                        tSec.setSkyLightArray(new NibbleArray(nar.a.clone(), 4));
                    } else {
                        if (tSec.getSkyLightArray() != null) {
                            try {
                                skyLightArray.set(tSec, null);
                            } catch (Exception e) {
                                tSec.setSkyLightArray(new NibbleArray(4096, 4));
                            }
                        }
                    }
                    tSec.recalcBlockCounts();
                    targetSections[i] = tSec;
                }
            }
            target.a(targetSections); // setChunkSections()
        }

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
                orig.e(tag); // Entity.saveToNBT(NBTTagCompound)
                tag.setString("id", EntityTypes.b(orig)); // EntityTypes.getIdString(Entity)
                // Reload the entity in target world, add to target chunk
                target.a(EntityTypes.a(tag, targetWorld)); // Chunk.addEntity(Entity), EntityTypes.loadEntityFromNBT(NBTTagCompound, World)
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
            orig.b(tag); // TileEntity.saveToNBT(NBTTagCompound)
            // Reload the tile entity, add to target chunk
            target.a(TileEntity.c(tag)); // Chunk.addTileEntity(TileEntity), TileEntity.createFromNBT(NBTTagCompound)
        }

        // Add the entities to the world
        target.addEntities();

        // Request unload
        sourceChunk = null;
        source = null;
        templateWorld.unloadChunkRequest(targetChunk.getX(), targetChunk.getZ());
    }
}
