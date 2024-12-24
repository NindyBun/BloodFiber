package com.nindybun.bloodfiber.data;

import com.nindybun.bloodfiber.BloodFiber;
import com.nindybun.bloodfiber.registries.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

public class BlockModelGen extends BlockModelProvider {
    public BlockModelGen(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BloodFiber.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        registerBasicBlock(ModBlocks.BLOOD_FIBER_ORE);
        registerBasicBlock(ModBlocks.BLOOD_FIBER_ORE_DEEPSLATE);
    }

    private void registerBasicBlock(Supplier<Block> block) {
        String path = ((DeferredHolder<Block, ?>)block).getId().getPath();
        cubeAll(path, ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "block/"+path));
    }
}
