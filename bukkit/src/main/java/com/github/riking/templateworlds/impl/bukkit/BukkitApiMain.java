package com.github.riking.templateworlds.impl.bukkit;

import java.util.Collection;


import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import com.github.riking.templateworlds.impl.common.BaseApiMain;
import com.github.riking.templateworlds.impl.common.TemplatedGenerator;

public class BukkitApiMain extends BaseApiMain {

    public BukkitApiMain() {
    }

    @Override
    public ChunkGenerator getTemplatedGenerator(String world, String template) {
        World temp = Bukkit.getWorld(template);
        Validate.notNull(temp, "The right-hand side of the chunk generator string for TemplateWorlds must be a valid world to use as a template");
        return new TemplateChunkGenerator(temp);
    }

    @Override
    public World createWorld(String name, World template) {
        Validate.notNull(template, "Template world cannot be null");

        World w = new WorldCreator(name).copy(template).generateStructures(false).generator(new TemplateChunkGenerator(template)).createWorld();
        w.setAutoSave(false);
        return w;
    }

    @Override
    public World createWorld(String name, World template, Collection<BlockPopulator> extraPopulators) {
        Validate.notNull(template, "Template world cannot be null");
        Validate.allElementsOfType(extraPopulators, BlockPopulator.class, "All extra populators must be BlockPopulators");

        World w = new WorldCreator(name).copy(template).generateStructures(false).generator(new TemplateChunkGenerator(template, extraPopulators)).createWorld();
        w.setAutoSave(false);
        return w;
    }

    @Override
    public void changeTemplate(World templated, World newTemplate) {
        validateTemplatedWorld(templated);
        TemplatedGenerator gen = (TemplatedGenerator) templated.getGenerator();
        BlockPopulator pop1 = templated.getPopulators().get(0);

        gen.templateWorld = newTemplate;
        if (pop1 instanceof TemplateBlockPopulator) {
            ((TemplateBlockPopulator) pop1).templateWorld = newTemplate;
        }
    }
}
