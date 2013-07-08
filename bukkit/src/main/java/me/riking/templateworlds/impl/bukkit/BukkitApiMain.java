package me.riking.templateworlds.impl.bukkit;

import java.util.Collection;
import java.util.List;

import me.riking.templateworlds.impl.common.BaseApiMain;

import org.apache.commons.lang.Validate;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.BlockPopulator;

public class BukkitApiMain extends BaseApiMain {

    public BukkitApiMain() {
    }

    @Override
    public World createWorld(String name, World template) {
        Validate.notNull(template, "Template world cannot be null");

        World w = new WorldCreator(name).copy(template).generateStructures(false).generator(new TemplateChunkGenerator(template)).createWorld();
        List<BlockPopulator> pops = w.getPopulators();
        pops.clear();
        pops.add(new TemplateBlockPopulator(template));
        w.setAutoSave(false);
        return w;
    }

    @Override
    public World createWorld(String name, World template, Collection<BlockPopulator> extraPopulators) {
        Validate.notNull(template, "Template world cannot be null");
        Validate.allElementsOfType(extraPopulators, BlockPopulator.class, "All extra populators must be BlockPopulators");

        World w = new WorldCreator(name).copy(template).generateStructures(false).generator(new TemplateChunkGenerator(template)).createWorld();
        List<BlockPopulator> pops = w.getPopulators();
        pops.clear();
        pops.add(new TemplateBlockPopulator(template));
        pops.addAll(extraPopulators);
        w.setAutoSave(false);
        return w;
    }
}
