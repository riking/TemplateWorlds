package me.riking.templateworlds.impl.craftbukkit1_6_R1;

import java.util.Collection;
import java.util.List;

import me.riking.templateworlds.impl.common.BaseApiMain;

import org.apache.commons.lang.Validate;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.craftbukkit.v1_6_R1.util.Versioning;
import org.bukkit.generator.BlockPopulator;

public class CBApiMain extends BaseApiMain {
    public CBApiMain() {
        // CB Version check
        Versioning.getBukkitVersion();
    }

    @Override
    public World createWorld(String name, World template) {
        Validate.notNull(template, "Template world cannot be null");

        World w = new WorldCreator(name).copy(template).generateStructures(false).generator(new BiomedVoidGenerator(template)).createWorld();
        List<BlockPopulator> pops = w.getPopulators();
        pops.clear();
        pops.add(new OBCTemplateBlockPopulator(template));
        w.setAutoSave(false);
        return w;
    }

    @Override
    public World createWorld(String name, World template, Collection<BlockPopulator> extraPopulators) {
        Validate.notNull(template, "Template world cannot be null");
        Validate.allElementsOfType(extraPopulators, BlockPopulator.class, "All extra populators must be BlockPopulators");

        World w = new WorldCreator(name).copy(template).generateStructures(false).generator(new BiomedVoidGenerator(template)).createWorld();
        List<BlockPopulator> pops = w.getPopulators();
        pops.clear();
        pops.add(new OBCTemplateBlockPopulator(template));
        pops.addAll(extraPopulators);
        w.setAutoSave(false);
        return w;
    }
}
