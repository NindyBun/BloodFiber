package com.nindybun.bloodfiber.items;

import com.nindybun.bloodfiber.entities.projectiles.BloodArrow.BloodArrow;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class BloodArrowItem extends ArrowItem {
    public BloodArrowItem() {
        super(new Item.Properties().stacksTo(64));
    }

    public AbstractArrow createArrow(Level level, ItemStack ammo, LivingEntity shooter, @Nullable ItemStack weapon) {
        return new BloodArrow(level, shooter, ammo.copyWithCount(1), weapon);
    }

    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        BloodArrow arrow = new BloodArrow(level, pos.x(), pos.y(), pos.z(), stack.copyWithCount(1), (ItemStack)null);
        arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
        return arrow;
    }

}
