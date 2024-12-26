package com.nindybun.bloodfiber.entities.projectiles.BloodArrow;

import com.nindybun.bloodfiber.BloodFiber;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BloodArrowRenderer extends ArrowRenderer<BloodArrow> {
    public BloodArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(BloodArrow bloodArrow) {
        return ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "textures/entity/projectiles/blood_fiber_arrow.png");
    }
}
