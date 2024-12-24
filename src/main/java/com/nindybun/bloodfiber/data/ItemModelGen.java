package com.nindybun.bloodfiber.data;

import com.nindybun.bloodfiber.BloodFiber;
import com.nindybun.bloodfiber.registries.ModBlocks;
import com.nindybun.bloodfiber.registries.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

public class ItemModelGen extends ItemModelProvider {
    public ItemModelGen(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BloodFiber.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        registerBasicBlock(ModBlocks.BLOOD_FIBER_ORE);
        registerBasicBlock(ModBlocks.BLOOD_FIBER_ORE_DEEPSLATE);
        registerBasicItem(ModItems.BLOOD_FIBER);
        registerBasicItem(ModItems.BLOOD_FIBER_WOVEN);

    }

    private void registerBasicItem(Supplier<Item> item) {
        String path = ((DeferredHolder<Item, ?>)item).getId().getPath();
        singleTexture(path, mcLoc("item/handheld"), "layer0", ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "item/"+path));
    }

    private void registerBasicBlock(Supplier<Block> block) {
        String path = ((DeferredHolder<Block, ?>)block).getId().getPath();
        getBuilder(path).parent(new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "block/"+path)));
    }
}