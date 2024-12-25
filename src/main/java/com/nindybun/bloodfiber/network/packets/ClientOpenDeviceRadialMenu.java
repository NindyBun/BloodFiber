package com.nindybun.bloodfiber.network.packets;

import com.nindybun.bloodfiber.BloodFiber;
import com.nindybun.bloodfiber.items.BloodFiberDevice;
import com.nindybun.bloodfiber.screens.DeviceRadialMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientOpenDeviceRadialMenu {
    public static final ClientOpenDeviceRadialMenu INSTANCE = new ClientOpenDeviceRadialMenu();

    public static ClientOpenDeviceRadialMenu get() {
        return INSTANCE;
    }

    public void handle(Data data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Minecraft.getInstance().setScreen(new DeviceRadialMenu(data.stack()));
        });
    }

    public record Data(ItemStack stack) implements CustomPacketPayload {
        public static final Type<ClientOpenDeviceRadialMenu.Data> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "open_device_radial_menu-client"));

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }

        public static final StreamCodec<RegistryFriendlyByteBuf, ClientOpenDeviceRadialMenu.Data> STREAM_CODEC = StreamCodec.composite(
                ItemStack.STREAM_CODEC, ClientOpenDeviceRadialMenu.Data::stack,
                ClientOpenDeviceRadialMenu.Data::new
        );
    }
}
