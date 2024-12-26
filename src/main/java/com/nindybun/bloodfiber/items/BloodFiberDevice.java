package com.nindybun.bloodfiber.items;

import com.nindybun.bloodfiber.BloodFiber;
import com.nindybun.bloodfiber.dataComponents.ToolRecord;
import com.nindybun.bloodfiber.entities.projectiles.BloodArrow.BloodArrow;
import com.nindybun.bloodfiber.registries.ModComponents;
import com.nindybun.bloodfiber.registries.ModItems;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.event.EventHooks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class BloodFiberDevice extends BowItem {

    public BloodFiberDevice() {
        super(new Properties().stacksTo(1)
                .component(ModComponents.TOOL_RECORD.get(), ToolRecord.BLANK)
        );
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        ItemStack stack = player.getMainHandItem();
        return (!(stack.getItem() instanceof BloodFiberDevice) || stack.get(ModComponents.TOOL_RECORD.get()) != ToolRecord.SWORD) && !player.isCreative();
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return !(stack.getItem() instanceof BloodFiberDevice) || stack.get(ModComponents.TOOL_RECORD.get()) != ToolRecord.BOW;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        if (!(stack.getItem() instanceof BloodFiberDevice)) {
            return false;
        }

        ToolRecord record = stack.get(ModComponents.TOOL_RECORD.get());

        if (record.equals(ToolRecord.SWORD)) {
            return ItemAbilities.DEFAULT_SWORD_ACTIONS.contains(itemAbility);
        } else if (record.equals(ToolRecord.PICKAXE)) {
            return ItemAbilities.DEFAULT_PICKAXE_ACTIONS.contains(itemAbility);
        } else if (record.equals(ToolRecord.AXE)) {
            return ItemAbilities.DEFAULT_AXE_ACTIONS.contains(itemAbility);
        }

        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        ToolRecord currentRecord = itemstack.get(ModComponents.TOOL_RECORD.get());
        if (!currentRecord.equals(ToolRecord.BOW)) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    private ItemStack getProjectiles(Player player) {
        Predicate<ItemStack> predicate = this.getAllSupportedProjectiles();
        ItemStack itemstack = ProjectileWeaponItem.getHeldProjectile(player, predicate);
        if (!itemstack.isEmpty()) {
            return CommonHooks.getProjectile(player, this.getDefaultInstance(), itemstack);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player) {
            ItemStack itemstack = this.getProjectiles(player).isEmpty() ? ModItems.BLOOD_ARROW_ITEM.get().getDefaultInstance() : this.getProjectiles(player);
            int i = this.getUseDuration(stack, entityLiving) - timeLeft;
            i = EventHooks.onArrowLoose(stack, level, player, i, !itemstack.isEmpty());
            if (i < 0) {
                return;
            }

            float f = getPowerForTime(i);
            if (!((double)f < 0.1)) {
                if (!player.isCreative()) {
                    useAmmo(player, itemstack);
                }
                if (level instanceof ServerLevel) {
                    ServerLevel serverlevel = (ServerLevel)level;
                    this.shoot(serverlevel, player, stack, itemstack, f * 3.0F, 1.0F, f == 1.0F);
                }
                level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                player.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    private void useAmmo(LivingEntity shooter, ItemStack ammo) {
        if (!(ammo.getItem() instanceof BloodArrowItem)) {
            if (shooter.level() instanceof ServerLevel) {
                ammo.shrink(1);
                if (ammo.isEmpty() && shooter instanceof Player) {
                    Player player = (Player)shooter;
                    player.getInventory().removeItem(ammo);
                }
            }
        }
    }

    private void shoot(ServerLevel level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, float velocity, float inaccuracy, boolean isCrit) {
        Projectile projectile = this.createProjectile(level, shooter, weapon, ammo, isCrit);
        projectile.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), 0.0F, velocity, inaccuracy);
        level.addFreshEntity(projectile);
    }

    public static float getToolMode(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity p_entity, int seed) {
        return stack.get(ModComponents.TOOL_RECORD.get()).id();
    }

    public static float getPulling(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity p_entity, int seed) {
        return p_entity != null && p_entity.isUsingItem() && p_entity.getUseItem() == stack ? 1.0f : 0f;
    }

    public static float getPull(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity p_entity, int seed) {
        if (p_entity == null) {
            return 0.0F;
        } else {
            return p_entity.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration(p_entity) - p_entity.getUseItemRemainingTicks()) / 20.0F;
        }
    }
}
