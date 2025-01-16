package com.nindybun.bloodfiber.registries;

import com.nindybun.bloodfiber.BloodFiber;
import com.nindybun.bloodfiber.menus.DeviceBindingMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, BloodFiber.MODID);


    public static final Supplier<MenuType<DeviceBindingMenu>> DEVICE_BINDING_MENU = MENUS.register("device_menu",
            () -> IMenuTypeExtension.create(DeviceBindingMenu::new));

    public static void register(IEventBus bus) {
        MENUS.register(bus);
    }
}
