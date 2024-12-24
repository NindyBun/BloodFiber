package com.nindybun.bloodfiber.registries;

import com.nindybun.bloodfiber.BloodFiber;
import net.minecraft.core.registries.BuiltInRegistries;
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

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
