package com.nindybun.bloodfiber.registries;

import com.nindybun.bloodfiber.BloodFiber;
import com.nindybun.bloodfiber.dataComponents.ToolRecord;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class ModComponents {
    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, BloodFiber.MODID);

    public static final Supplier<DataComponentType<ToolRecord>> TOOL_RECORD = COMPONENTS.registerComponentType("tool_record", builder ->
       builder.persistent(ToolRecord.CODEC).networkSynchronized(ToolRecord.STREAM_CODEC)
    );

    public static void register(IEventBus bus) {
        COMPONENTS.register(bus);
    }
}
