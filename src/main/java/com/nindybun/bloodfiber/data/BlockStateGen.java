package com.nindybun.bloodfiber.data;

import com.nindybun.bloodfiber.BloodFiber;
import com.nindybun.bloodfiber.registries.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class BlockStateGen extends BlockStateProvider {
    public BlockStateGen(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BloodFiber.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.BLOOD_FIBER_ORE.get(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "block/"+((DeferredHolder<Block, ?>)ModBlocks.BLOOD_FIBER_ORE).getId().getPath())));
        simpleBlock(ModBlocks.BLOOD_FIBER_ORE_DEEPSLATE.get(), new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "block/"+((DeferredHolder<Block, ?>)ModBlocks.BLOOD_FIBER_ORE_DEEPSLATE).getId().getPath())));
    }
}
