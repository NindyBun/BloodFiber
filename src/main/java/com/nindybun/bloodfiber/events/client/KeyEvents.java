package com.nindybun.bloodfiber.events.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.nindybun.bloodfiber.BloodFiber;
import com.nindybun.bloodfiber.items.BloodFiberDevice;
import com.nindybun.bloodfiber.network.packets.ServerOpenDeviceRadialMenu;
import com.nindybun.bloodfiber.registries.ModComponents;
import com.nindybun.bloodfiber.screens.DeviceRadialMenu;
import com.nindybun.bloodfiber.tools.Helpers;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(value = Dist.CLIENT, modid = BloodFiber.MODID)
public class KeyEvents {
    public static final String KEY_CATEGORY = "key.categories."+BloodFiber.MODID;
    public static KeyMapping device_key = new KeyMapping("key."+BloodFiber.MODID+".device_radial_menu_key", GLFW.GLFW_KEY_LEFT_ALT, KEY_CATEGORY);
    private static boolean device_keyWasDown = false;

    @SubscribeEvent
    public static void onClientTick(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_LEVEL) return;
        Player player = Minecraft.getInstance().player;
        boolean isHoldingDevice = false;

        if (Minecraft.getInstance().screen == null) {
            if (Helpers.isHoldingItem(BloodFiberDevice.class, player)) {
                isHoldingDevice = true;
            }

            boolean device_keyIsDown = device_key.isDown();
            if (device_keyIsDown && !device_keyWasDown) {
                while (device_key.consumeClick() && isHoldingDevice) {
                    if (Minecraft.getInstance().screen == null) {
                        Minecraft.getInstance().setScreen(new DeviceRadialMenu(Helpers.getItem(BloodFiberDevice.class, player), player));
                        //PacketDistributor.sendToServer(new ServerOpenDeviceRadialMenu.Data(0));
                    }
                }
            }
            device_keyWasDown = device_keyIsDown;
        } else {
            device_keyWasDown = true;
        }
    }

    public static void wipeOpen() {
        while (device_key.consumeClick()){}
    }

    public static boolean isKeyDown(KeyMapping keyMapping) {
        if (keyMapping.isUnbound())
            return false;

        boolean isDown = switch (keyMapping.getKey().getType()) {
            case KEYSYM -> InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), keyMapping.getKey().getValue());
            case MOUSE -> GLFW.glfwGetMouseButton(Minecraft.getInstance().getWindow().getWindow(), keyMapping.getKey().getValue()) == GLFW.GLFW_PRESS;
            default -> false;
        };
        return isDown && keyMapping.getKeyConflictContext().isActive() && keyMapping.getKeyModifier().isActive(keyMapping.getKeyConflictContext());
    }

    @EventBusSubscriber(value = Dist.CLIENT, modid = BloodFiber.MODID, bus = EventBusSubscriber.Bus.MOD)
    public static class KeyBus {
        @SubscribeEvent
        public static void register(RegisterKeyMappingsEvent event) {
            event.register(device_key);
        }
    }
}
