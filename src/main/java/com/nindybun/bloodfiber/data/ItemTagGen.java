package com.nindybun.bloodfiber.data;

import com.nindybun.bloodfiber.BloodFiber;
import com.nindybun.bloodfiber.registries.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ItemTagGen extends ItemTagsProvider {

    public ItemTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, BloodFiber.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.ARROWS)
                .add(ModItems.BLOOD_ARROW_ITEM.get());
        tag(ItemTags.SWORDS)
                .add(ModItems.BLOOD_FIBER_DEVICE.get());
        tag(ItemTags.SHOVELS)
                .add(ModItems.BLOOD_FIBER_DEVICE.get());
        tag(ItemTags.PICKAXES)
                .add(ModItems.BLOOD_FIBER_DEVICE.get());
        tag(ItemTags.CLUSTER_MAX_HARVESTABLES)
                .add(ModItems.BLOOD_FIBER_DEVICE.get());
        tag(ItemTags.AXES)
                .add(ModItems.BLOOD_FIBER_DEVICE.get());
        tag(ItemTags.HOES)
                .add(ModItems.BLOOD_FIBER_DEVICE.get());


        tag(Tags.Items.MELEE_WEAPON_TOOLS)
                .add(ModItems.BLOOD_FIBER_DEVICE.get());
        tag(Tags.Items.TOOLS_BOW)
                .add(ModItems.BLOOD_FIBER_DEVICE.get());
        tag(Tags.Items.MINING_TOOL_TOOLS)
                .add(ModItems.BLOOD_FIBER_DEVICE.get());
    }
}
