package com.nindybun.bloodfiber.network.packets;

import com.nindybun.bloodfiber.BloodFiber;
import com.nindybun.bloodfiber.dataComponents.ToolRecord;
import com.nindybun.bloodfiber.items.BloodFiberDevice;
import com.nindybun.bloodfiber.registries.ModComponents;
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

public class SetDeviceTool {
    public static final SetDeviceTool INSTANCE = new SetDeviceTool();

    public static SetDeviceTool get() {
        return INSTANCE;
    }

    public void handle(SetDeviceTool.Data data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (!Helpers.isHoldingItem(BloodFiberDevice.class, player)) return;
            ItemStack stack = Helpers.getItem(BloodFiberDevice.class, player);
            stack.set(ModComponents.TOOL_RECORD.get(), data.record);
        });
    }

    public record Data(ToolRecord record) implements CustomPacketPayload {
        public static final Type<SetDeviceTool.Data> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "set_device_tool"));

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }

        public static final StreamCodec<RegistryFriendlyByteBuf, SetDeviceTool.Data> STREAM_CODEC = StreamCodec.composite(
                ToolRecord.STREAM_CODEC, Data::record,
                SetDeviceTool.Data::new
        );
    }
}
