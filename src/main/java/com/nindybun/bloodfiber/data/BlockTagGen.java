package com.nindybun.bloodfiber.data;

import com.nindybun.bloodfiber.BloodFiber;
import com.nindybun.bloodfiber.registries.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BlockTagGen extends BlockTagsProvider {
    public BlockTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, BloodFiber.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.BLOOD_FIBER_ORE.get())
                .add(ModBlocks.BLOOD_FIBER_ORE_DEEPSLATE.get());
        tag(BlockTags.INCORRECT_FOR_WOODEN_TOOL)
                .add(ModBlocks.BLOOD_FIBER_ORE.get())
                .add(ModBlocks.BLOOD_FIBER_ORE_DEEPSLATE.get());
        tag(BlockTags.INCORRECT_FOR_STONE_TOOL)
                .add(ModBlocks.BLOOD_FIBER_ORE.get())
                .add(ModBlocks.BLOOD_FIBER_ORE_DEEPSLATE.get());


    }
}
