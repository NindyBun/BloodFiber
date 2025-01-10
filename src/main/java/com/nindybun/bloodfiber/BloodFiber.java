package com.nindybun.bloodfiber;

import com.nindybun.bloodfiber.data.Generator;
import com.nindybun.bloodfiber.entities.projectiles.BloodArrow.BloodArrowRenderer;
import com.nindybun.bloodfiber.items.BloodFiberDevice;
import com.nindybun.bloodfiber.network.PacketHandler;
import com.nindybun.bloodfiber.registries.*;
import com.nindybun.bloodfiber.screens.DeviceBindingScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.Cursor3D;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(BloodFiber.MODID)
public class BloodFiber
{
    public static final String MODID = "bloodfiber";
    public static final Logger LOGGER = LogUtils.getLogger();


    public BloodFiber(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(this::commonSetup);
        ModComponents.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModMenus.register(modEventBus);
        ModEntities.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        modEventBus.addListener(Generator::gatherData);
        NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(PacketHandler::register);

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientEvents{

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(ModMenus.DEVICE_BINDING_MENU.get(), DeviceBindingScreen::new);
        }

        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(ModEntities.BLOOD_ARROW.get(), BloodArrowRenderer::new);

            event.enqueueWork(() -> {
                ItemProperties.register(ModItems.BLOOD_FIBER_DEVICE.get(), ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "tool"), BloodFiberDevice::getToolMode);
                ItemProperties.register(ModItems.BLOOD_FIBER_DEVICE.get(), ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "pulling"), BloodFiberDevice::getPulling);
                ItemProperties.register(ModItems.BLOOD_FIBER_DEVICE.get(), ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "pull"), BloodFiberDevice::getPull);
            });
        }
    }


}
