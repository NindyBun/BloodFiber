package com.nindybun.bloodfiber.items;

import com.nindybun.bloodfiber.BloodFiber;
import com.nindybun.bloodfiber.dataComponents.ToolRecord;
import com.nindybun.bloodfiber.registries.ModComponents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;

import javax.annotation.Nullable;
import java.util.List;

public class BloodFiberDevice extends BowItem {

    public BloodFiberDevice() {
        super(new Item.Properties().stacksTo(1)
                .component(ModComponents.TOOL_RECORD.get(), ToolRecord.BLANK)
        );
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity, InteractionHand hand) {
        return super.onEntitySwing(stack, entity, hand);
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
