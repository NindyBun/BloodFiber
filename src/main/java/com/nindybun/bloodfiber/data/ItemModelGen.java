package com.nindybun.bloodfiber.data;

import com.nindybun.bloodfiber.BloodFiber;
import com.nindybun.bloodfiber.registries.ModBlocks;
import com.nindybun.bloodfiber.registries.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
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

        registerBasicItem(ModItems.BLOOD_FIBER_BOOTS);
        registerBasicItem(ModItems.BLOOD_FIBER_LEGINGS);
        registerBasicItem(ModItems.BLOOD_FIBER_CHESTPIECE);
        registerBasicItem(ModItems.BLOOD_FIBER_HEADPIECE);

        String path = "blood_fiber";
        String device_path = ((DeferredHolder<?, ?>)ModItems.BLOOD_FIBER_DEVICE).getId().getPath();
        String bow_path = path+"_bow";
        withExistingParent(device_path, mcLoc("item/handheld")).texture("layer0", ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "item/"+device_path+"-1"))
                .override().predicate(ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID,"tool"), 0.1f)
                    .model(withExistingParent(bow_path, mcLoc("item/generated")).texture("layer0", ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "item/"+bow_path)).transforms()
                            .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
                                .rotation(-80f, 260f, -40f)
                                .translation(-1f, -2f, 2.5f)
                                .scale(0.9f, 0.9f, 0.9f)
                                .end()
                            .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND)
                                .rotation(-80f, -280f, 40f)
                                .translation(-1f, -2f, 2.5f)
                                .scale(0.9f, 0.9f, 0.9f)
                                .end()
                            .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
                                .rotation(0f, -90f, 25f)
                                .translation(1.13f, 3.2f, 1.13f)
                                .scale(0.68f, 0.68f, 0.68f)
                                .end()
                            .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND)
                                .rotation(0f, 90f, -25f)
                                .translation(1.13f, 3.2f, 1.13f)
                                .scale(0.68f, 0.68f, 0.68f)
                                .end()
                            .end()
                    ).end()
                .override()
                    .predicate(ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID,"tool"), 0.1f)
                    .predicate(ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "pulling"), 1f)
                    .model(withExistingParent(bow_path+"_pulling_0", ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "item/"+bow_path))
                            .texture("layer0", ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "item/"+bow_path+"_pulling_0"))
                    ).end()
                .override()
                    .predicate(ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID,"tool"), 0.1f)
                    .predicate(ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "pulling"), 1f)
                    .predicate(ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "pull"), 0.65f)
                    .model(withExistingParent(bow_path+"_pulling_1", ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "item/"+bow_path))
                            .texture("layer0", ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "item/"+bow_path+"_pulling_1"))
                    ).end()
                .override()
                    .predicate(ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID,"tool"), 0.1f)
                    .predicate(ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "pulling"), 1f)
                    .predicate(ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "pull"), 0.9f)
                    .model(withExistingParent(bow_path+"_pulling_2", ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "item/"+bow_path))
                            .texture("layer0", ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "item/"+bow_path+"_pulling_2"))
                    ).end()
                .override().predicate(ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "tool"), 0.2f)
                    .model(withExistingParent(path+"_sword", mcLoc("item/handheld"))
                            .texture("layer0", ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "item/"+path+"_sword"))
                    ).end()
                .override().predicate(ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "tool"), 0.3f)
                    .model(withExistingParent(path+"_pickaxe", mcLoc("item/handheld"))
                            .texture("layer0", ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "item/"+path+"_pickaxe"))
                    ).end()
                .override().predicate(ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "tool"), 0.4f)
                    .model(withExistingParent(path+"_shovel", mcLoc("item/handheld"))
                            .texture("layer0", ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "item/"+path+"_shovel"))
                    ).end()
                .override().predicate(ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "tool"), 0.5f)
                    .model(withExistingParent(path+"_axe", mcLoc("item/handheld"))
                            .texture("layer0", ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "item/"+path+"_axe"))
                    ).end()
                .override().predicate(ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "tool"), 0.6f)
                    .model(withExistingParent(path+"_hoe", mcLoc("item/handheld"))
                            .texture("layer0", ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "item/"+path+"_hoe"))
                    ).end()
        ;

    }

    private void registerBasicItem(Supplier<?> item) {
        String path = ((DeferredHolder<?, ?>)item).getId().getPath();
        singleTexture(path, mcLoc("item/handheld"), "layer0", ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "item/"+path));
    }

    private void registerBasicBlock(Supplier<?> block) {
        String path = ((DeferredHolder<?, ?>)block).getId().getPath();
        getBuilder(path).parent(new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "block/"+path)));
    }
}
