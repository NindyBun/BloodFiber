package com.nindybun.bloodfiber.items;

import com.mojang.datafixers.util.Pair;
import com.nindybun.bloodfiber.BloodFiber;
import com.nindybun.bloodfiber.dataComponents.ToolRecord;
import com.nindybun.bloodfiber.entities.projectiles.BloodArrow.BloodArrow;
import com.nindybun.bloodfiber.registries.ModComponents;
import com.nindybun.bloodfiber.registries.ModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.IShearable;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.event.EventHooks;

import javax.annotation.Nullable;
import javax.tools.Tool;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
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
        return !stack.get(ModComponents.TOOL_RECORD.get()).equals(ToolRecord.SWORD) || !player.isCreative();
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
        } else if (record.equals(ToolRecord.SHOVEL)) {
            return ItemAbilities.DEFAULT_SHOVEL_ACTIONS.contains(itemAbility);
        } else if (record.equals(ToolRecord.AXE)) {
            return ItemAbilities.DEFAULT_AXE_ACTIONS.contains(itemAbility);
        } else if (record.equals(ToolRecord.HOE)) {
            return ItemAbilities.DEFAULT_HOE_ACTIONS.contains(itemAbility);
        } else if (record.equals(ToolRecord.SHEARS)) {
            return ItemAbilities.DEFAULT_SHEARS_ACTIONS.contains(itemAbility);
        }

        return false;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        ToolRecord record = stack.get(ModComponents.TOOL_RECORD.get());

        if (record.equals(ToolRecord.SHEARS)) {
            return interactShearsOnLivingEntity(stack, player, interactionTarget, usedHand);
        }

        return InteractionResult.PASS;
    }

    private InteractionResult interactShearsOnLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        if (entity instanceof IShearable target) {
            BlockPos pos = entity.blockPosition();
            boolean isClient = entity.level().isClientSide();
            if (target.isShearable(player, stack, entity.level(), pos)) {
                List<ItemStack> drops = target.onSheared(player, stack, entity.level(), pos);
                if (!isClient) {
                    Iterator var9 = drops.iterator();

                    while (var9.hasNext()) {
                        ItemStack drop = (ItemStack) var9.next();
                        target.spawnShearedDrop(entity.level(), pos, drop);
                    }
                }

                entity.gameEvent(GameEvent.SHEAR, player);

                return InteractionResult.sidedSuccess(isClient);
            }
        }
        return InteractionResult.PASS;
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

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        ToolRecord record = stack.get(ModComponents.TOOL_RECORD.get());

        if (record.equals(ToolRecord.SHOVEL)) {
            return useShovelOn(context);
        } else if (record.equals(ToolRecord.AXE)) {
            return useAxeOn(context);
        } else if (record.equals(ToolRecord.HOE)) {
            return useHoeOn(context);
        } else if (record.equals(ToolRecord.SHEARS)) {
            return useShearsOn(context);
        }

        return InteractionResult.PASS;
    }

    private InteractionResult useShearsOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        BlockState blockstate1 = blockstate.getToolModifiedState(context, ItemAbilities.SHEARS_TRIM, false);
        if (blockstate1 != null) {
            Player player = context.getPlayer();
            ItemStack itemstack = context.getItemInHand();
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockpos, itemstack);
            }

            level.setBlockAndUpdate(blockpos, blockstate1);
            level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(context.getPlayer(), blockstate1));

            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return super.useOn(context);
        }
    }

    private InteractionResult useHoeOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState toolModifiedState = level.getBlockState(blockpos).getToolModifiedState(context, ItemAbilities.HOE_TILL, false);
        Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> pair = toolModifiedState == null ? null : Pair.of((ctx) -> {
            return true;
        }, HoeItem.changeIntoState(toolModifiedState));
        if (pair == null) {
            return InteractionResult.PASS;
        } else {
            Predicate<UseOnContext> predicate = (Predicate) pair.getFirst();
            Consumer<UseOnContext> consumer = (Consumer) pair.getSecond();
            if (predicate.test(context)) {
                Player player = context.getPlayer();
                level.playSound(player, blockpos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!level.isClientSide) {
                    consumer.accept(context);
                }

                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    private InteractionResult useAxeOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        Player player = context.getPlayer();
        if (playerHasShieldUseIntent(context)) {
            return InteractionResult.PASS;
        } else {
            Optional<BlockState> optional = this.evaluateNewBlockState(level, blockpos, player, level.getBlockState(blockpos), context);
            if (optional.isEmpty()) {
                return InteractionResult.PASS;
            } else {
                ItemStack itemstack = context.getItemInHand();
                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockpos, itemstack);
                }

                level.setBlock(blockpos, (BlockState)optional.get(), 11);
                level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(player, (BlockState)optional.get()));

                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
    }

    private static boolean playerHasShieldUseIntent(UseOnContext context) {
        Player player = context.getPlayer();
        return context.getHand().equals(InteractionHand.MAIN_HAND) && player.getOffhandItem().is(Items.SHIELD) && !player.isSecondaryUseActive();
    }

    private Optional<BlockState> evaluateNewBlockState(Level level, BlockPos pos, @Nullable Player player, BlockState state, UseOnContext p_40529_) {
        Optional<BlockState> optional = Optional.ofNullable(state.getToolModifiedState(p_40529_, ItemAbilities.AXE_STRIP, false));
        if (optional.isPresent()) {
            level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            return optional;
        } else {
            Optional<BlockState> optional1 = Optional.ofNullable(state.getToolModifiedState(p_40529_, ItemAbilities.AXE_SCRAPE, false));
            if (optional1.isPresent()) {
                level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.levelEvent(player, 3005, pos, 0);
                return optional1;
            } else {
                Optional<BlockState> optional2 = Optional.ofNullable(state.getToolModifiedState(p_40529_, ItemAbilities.AXE_WAX_OFF, false));
                if (optional2.isPresent()) {
                    level.playSound(player, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.levelEvent(player, 3004, pos, 0);
                    return optional2;
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    private InteractionResult useShovelOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        if (context.getClickedFace() == Direction.DOWN) {
            return InteractionResult.PASS;
        } else {
            Player player = context.getPlayer();
            BlockState blockstate1 = blockstate.getToolModifiedState(context, ItemAbilities.SHOVEL_FLATTEN, false);
            BlockState blockstate2 = null;
            if (blockstate1 != null && level.getBlockState(blockpos.above()).isAir()) {
                level.playSound(player, blockpos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
                blockstate2 = blockstate1;
            } else if ((blockstate2 = blockstate.getToolModifiedState(context, ItemAbilities.SHOVEL_DOUSE, false)) != null && !level.isClientSide()) {
                level.levelEvent((Player)null, 1009, blockpos, 0);
            }

            if (blockstate2 != null) {
                if (!level.isClientSide) {
                    level.setBlock(blockpos, blockstate2, 11);
                    level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(player, blockstate2));
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.PASS;
            }
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
