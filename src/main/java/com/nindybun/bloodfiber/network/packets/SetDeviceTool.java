package com.nindybun.bloodfiber.network.packets;

import com.nindybun.bloodfiber.BloodFiber;
import com.nindybun.bloodfiber.dataComponents.ToolRecord;
import com.nindybun.bloodfiber.events.server.AttributeEvents;
import com.nindybun.bloodfiber.items.BloodFiberDevice;
import com.nindybun.bloodfiber.registries.ModComponents;
import com.nindybun.bloodfiber.tools.Helpers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.util.AttributeUtil;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
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

            ToolRecord record = stack.get(ModComponents.TOOL_RECORD.get());

            if (stack.has(DataComponents.TOOL)) {
                stack.remove(DataComponents.TOOL);
            }

            if (record.equals(ToolRecord.BLANK)) {
                //AttributeEvents.addOrPersistModifier(AttributeEvents.AttributeModifiers.MAX_HEALTH, 1, player, Attributes.MAX_HEALTH);
                //AttributeEvents.addOrPersistModifier(AttributeEvents.AttributeModifiers.ARMOR, 1, player, Attributes.ARMOR);
                //AttributeEvents.addOrPersistModifier(AttributeEvents.AttributeModifiers.ARMOR_TOUGHNESS, 1, player, Attributes.ARMOR_TOUGHNESS);
                //AttributeEvents.addOrPersistModifier(AttributeEvents.AttributeModifiers.MOVEMENT_SPEED, 0.02, player, Attributes.MOVEMENT_SPEED);
            } else if (record.equals(ToolRecord.SWORD)) {
                stack.set(DataComponents.TOOL, AttributeEvents.createToolProperties(AttributeEvents.Property.SWORD));
            } else if (record.equals(ToolRecord.PICKAXE)) {
                stack.set(DataComponents.TOOL, AttributeEvents.createToolProperties(AttributeEvents.Property.PICKAXE, Tiers.IRON, BlockTags.MINEABLE_WITH_PICKAXE));
            } else if (record.equals(ToolRecord.SHOVEL)) {
                stack.set(DataComponents.TOOL, AttributeEvents.createToolProperties(AttributeEvents.Property.SHOVEL, Tiers.IRON, BlockTags.MINEABLE_WITH_SHOVEL));
            } else if (record.equals(ToolRecord.AXE)) {
                stack.set(DataComponents.TOOL, AttributeEvents.createToolProperties(AttributeEvents.Property.AXE, Tiers.IRON, BlockTags.MINEABLE_WITH_AXE));
            } else if (record.equals(ToolRecord.HOE)) {
                stack.set(DataComponents.TOOL, AttributeEvents.createToolProperties(AttributeEvents.Property.HOE, Tiers.IRON, BlockTags.MINEABLE_WITH_HOE));
            } else if (record.equals(ToolRecord.SHEARS)) {
                stack.set(DataComponents.TOOL, AttributeEvents.createToolProperties(AttributeEvents.Property.SHEARS));
            }

            player.level()
                    .playSound(
                            null, player.getX(), player.getY(), player.getZ(), SoundEvents.COBWEB_HIT, SoundSource.PLAYERS, 1.0F, 0.35F / (player.level().getRandom().nextFloat() * 0.4F + 1.2F) + 1 * 0.5F
                    );
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
