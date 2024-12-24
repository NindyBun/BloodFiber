package com.nindybun.bloodfiber.registries;

import com.nindybun.bloodfiber.BloodFiber;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BloodFiber.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATIVE_TAB = CREATIVE_MODE_TABS.register("bloodfiber_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + BloodFiber.MODID + ".creativeTab")) //The language key for the title of your CreativeModeTab
            .icon( () -> new ItemStack(ModItems.BLOOD_FIBER.get()))
            .displayItems((parameters, output) -> {
                ModItems.ITEMS.getEntries().forEach(item -> {
                    output.accept(item.get());
                });

            }).build());

    public static void register(IEventBus bus) {
        CREATIVE_MODE_TABS.register(bus);
    }
}
