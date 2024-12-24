package com.nindybun.bloodfiber.data;

import com.nindybun.bloodfiber.registries.ModBlocks;
import com.nindybun.bloodfiber.registries.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.List;

public class BlockLootGen extends VanillaBlockLoot {

    public BlockLootGen(HolderLookup.Provider registries) {
        super(registries);
    }

    @Override
    protected void generate() {
        add(ModBlocks.BLOOD_FIBER_ORE.get(), createOreDrop(ModBlocks.BLOOD_FIBER_ORE.get(), ModItems.BLOOD_FIBER.get()));
        add(ModBlocks.BLOOD_FIBER_ORE_DEEPSLATE.get(), createOreDrop(ModBlocks.BLOOD_FIBER_ORE_DEEPSLATE.get(), ModItems.BLOOD_FIBER.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> knownBlocks = new ArrayList<>();
        knownBlocks.addAll(ModBlocks.BLOCKS.getEntries().stream().map(DeferredHolder::get).toList());
        return knownBlocks;
    }
}
