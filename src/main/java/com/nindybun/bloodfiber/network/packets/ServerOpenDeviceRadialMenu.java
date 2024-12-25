package com.nindybun.bloodfiber.network.packets;

import com.nindybun.bloodfiber.BloodFiber;
import com.nindybun.bloodfiber.items.BloodFiberDevice;
import com.nindybun.bloodfiber.tools.Helpers;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerOpenDeviceRadialMenu {
    public static final ServerOpenDeviceRadialMenu INSTANCE = new ServerOpenDeviceRadialMenu();

    public static ServerOpenDeviceRadialMenu get() {
        return INSTANCE;
    }

    public void handle(ServerOpenDeviceRadialMenu.Data data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (!Helpers.isHoldingItem(BloodFiberDevice.class, player)) return;
            ItemStack stack = Helpers.getItem(BloodFiberDevice.class, player);
            PacketDistributor.sendToPlayer((ServerPlayer) player, new ClientOpenDeviceRadialMenu.Data(stack));
        });
    }

    public record Data(int dummy) implements CustomPacketPayload {
        public static final Type<ServerOpenDeviceRadialMenu.Data> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "open_device_radial_menu-server"));

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }

        public static final StreamCodec<RegistryFriendlyByteBuf, ServerOpenDeviceRadialMenu.Data> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.INT, ServerOpenDeviceRadialMenu.Data::dummy,
                ServerOpenDeviceRadialMenu.Data::new
        );
    }
}
