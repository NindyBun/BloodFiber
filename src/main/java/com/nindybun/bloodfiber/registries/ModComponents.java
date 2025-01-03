package com.nindybun.bloodfiber.registries;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.nindybun.bloodfiber.BloodFiber;
import com.nindybun.bloodfiber.dataComponents.ToolRecord;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;


public class ModComponents {
    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, BloodFiber.MODID);

    public static final Supplier<DataComponentType<ToolRecord>> TOOL_RECORD = COMPONENTS.registerComponentType("tool_record", builder ->
       builder.persistent(ToolRecord.CODEC).networkSynchronized(ToolRecord.STREAM_CODEC)
    );

    public static final Supplier<DataComponentType<String>> PLAYER_ID = COMPONENTS.registerComponentType("player_id", builder ->
            builder.persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8)
    );

    public static void register(IEventBus bus) {
        COMPONENTS.register(bus);
    }
}
