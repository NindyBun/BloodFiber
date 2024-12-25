package com.nindybun.bloodfiber.registries;

import com.nindybun.bloodfiber.BloodFiber;
import com.nindybun.bloodfiber.items.BloodFiberDevice;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, BloodFiber.MODID);

    public static final Supplier<Item> BLOOD_FIBER = ITEMS.register("blood_fiber",
            () -> new Item(new Item.Properties().stacksTo(64))
    );

    public static final Supplier<Item> BLOOD_FIBER_WOVEN = ITEMS.register("blood_fiber_woven",
            () -> new Item(new Item.Properties().stacksTo(64))
    );

    public static final Supplier<Item> BLOCK_FIBER_ORE_ITEM = ITEMS.register("blood_fiber_ore",
            () -> new BlockItem(ModBlocks.BLOOD_FIBER_ORE.get(), new Item.Properties().stacksTo(64))
    );

    public static final Supplier<Item> BLOCK_FIBER_ORE_DEEPSLATE_ITEM = ITEMS.register("blood_fiber_ore_deepslate",
            () -> new BlockItem(ModBlocks.BLOOD_FIBER_ORE_DEEPSLATE.get(), new Item.Properties().stacksTo(64))
    );

    public static final Supplier<ArmorItem> BLOOD_FIBER_BOOTS = ITEMS.register("blood_fiber_boots",
            () -> new ArmorItem(ModArmorMaterials.BLOOD_FIBER_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(5))));

    public static final Supplier<ArmorItem> BLOOD_FIBER_LEGINGS = ITEMS.register("blood_fiber_leggings",
            () -> new ArmorItem(ModArmorMaterials.BLOOD_FIBER_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(5))));

    public static final Supplier<ArmorItem> BLOOD_FIBER_CHESTPIECE = ITEMS.register("blood_fiber_chestpiece",
            () -> new ArmorItem(ModArmorMaterials.BLOOD_FIBER_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(5))));

    public static final Supplier<ArmorItem> BLOOD_FIBER_HEADPIECE = ITEMS.register("blood_fiber_headpiece",
            () -> new ArmorItem(ModArmorMaterials.BLOOD_FIBER_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(5))));

    public static final Supplier<BloodFiberDevice> BLOOD_FIBER_DEVICE = ITEMS.register("blood_fiber_device",
            BloodFiberDevice::new);

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
