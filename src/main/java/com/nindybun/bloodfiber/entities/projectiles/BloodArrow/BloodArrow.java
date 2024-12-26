package com.nindybun.bloodfiber.entities.projectiles.BloodArrow;

import com.nindybun.bloodfiber.registries.ModEntities;
import com.nindybun.bloodfiber.registries.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class BloodArrow extends AbstractArrow {

    public BloodArrow(EntityType<? extends BloodArrow> entityType, Level level) {
        super(entityType, level);
    }

    public BloodArrow(Level level, LivingEntity owner, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(ModEntities.BLOOD_ARROW.get(), owner, level, pickupItemStack, firedFromWeapon);
    }

    public BloodArrow(Level level, double x, double y, double z, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(ModEntities.BLOOD_ARROW.get(), x, y, z, level, pickupItemStack, firedFromWeapon);
    }

    @Override
    protected boolean tryPickup(Player player) {
        return false;
    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide && !this.inGround) {
            this.level().addParticle(ParticleTypes.CRIMSON_SPORE, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        }
    }

    protected void doPostHurtEffects(LivingEntity living) {
        super.doPostHurtEffects(living);
        //MobEffectInstance mobeffectinstance = new MobEffectInstance(MobEffects.GLOWING, this.duration, 0);
        //living.addEffect(mobeffectinstance, this.getEffectSource());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        //if (compound.contains("Duration")) {
        //    this.duration = compound.getInt("Duration");
        //}

    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        //compound.putInt("Duration", this.duration);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.BLOOD_ARROW_ITEM.get());
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity p_entity) {
        return new ClientboundAddEntityPacket(this, p_entity);
    }
}
