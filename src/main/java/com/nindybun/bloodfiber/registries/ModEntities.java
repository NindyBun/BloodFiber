package com.nindybun.bloodfiber.registries;

import com.nindybun.bloodfiber.BloodFiber;
import com.nindybun.bloodfiber.entities.projectiles.BloodArrow.BloodArrow;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, BloodFiber.MODID);

    public static final Supplier<EntityType<BloodArrow>> BLOOD_ARROW = ENTITY.register("blood_fiber_arrow",
            () -> EntityType.Builder.<BloodArrow>of(BloodArrow::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).eyeHeight(0.13F).clientTrackingRange(4).updateInterval(20).build("blood_fiber_arrow"));

    public static void register(IEventBus bus) {
        ENTITY.register(bus);
    }
}
