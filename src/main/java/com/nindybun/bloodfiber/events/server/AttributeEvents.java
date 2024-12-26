package com.nindybun.bloodfiber.events.server;

import com.nindybun.bloodfiber.BloodFiber;
import com.nindybun.bloodfiber.dataComponents.ToolRecord;
import com.nindybun.bloodfiber.items.BloodFiberDevice;
import com.nindybun.bloodfiber.registries.ModComponents;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

import javax.annotation.Nullable;
import java.util.List;

import static net.neoforged.neoforge.common.util.AttributeUtil.BASE_ATTACK_DAMAGE_ID;
import static net.neoforged.neoforge.common.util.AttributeUtil.BASE_ATTACK_SPEED_ID;

@EventBusSubscriber(modid = BloodFiber.MODID)
public class AttributeEvents {
    @SubscribeEvent
    public static void itemAttributeModifiersEvent(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        if (!(stack.getItem() instanceof BloodFiberDevice)) return;
        event.clearModifiers();
        ToolRecord record = stack.get(ModComponents.TOOL_RECORD.get());

        if (record.equals(ToolRecord.SWORD)) {
            AttributeEvents.addModifier(event, Tiers.IRON, 3f, -2.4f);
        } else if (record.equals(ToolRecord.PICKAXE)) {
            AttributeEvents.addModifier(event, Tiers.IRON, 1f, -2.8f);
        } else if (record.equals(ToolRecord.SHOVEL)) {
            AttributeEvents.addModifier(event, Tiers.IRON, 1.5f, -3.0f);
        } else if (record.equals(ToolRecord.AXE)) {
            AttributeEvents.addModifier(event, Tiers.IRON, 6f, -3.1f);
        } else if (record.equals(ToolRecord.HOE)) {
            AttributeEvents.addModifier(event, Tiers.IRON, -2f, -1f);
        }
    }

    private static void addModifier(ItemAttributeModifierEvent event, Tier tier, float attackDamage, float atackSpeed) {
        event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, (double)(attackDamage + tier.getAttackDamageBonus()), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, (double)atackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
    }

    public static enum Property {
        SWORD("sword"),
        PICKAXE("pickaxe"),
        AXE("axe"),
        SHOVEL("shovel"),
        HOE("hoe")
        ;

        Property(String name) {}
    }

    public static Tool createToolProperties(Property property) {
        if (property.equals(Property.SWORD)) {
            return new Tool(List.of(Tool.Rule.minesAndDrops(List.of(Blocks.COBWEB), 15.0F), Tool.Rule.overrideSpeed(BlockTags.SWORD_EFFICIENT, 1.5F)), 1.0F, 2);
        }
        return null;
    }

    public static Tool createToolProperties(Property property, Tier tier, TagKey<Block> blockTags) {
        return new Tool(List.of(Tool.Rule.deniesDrops(tier.getIncorrectBlocksForDrops()), Tool.Rule.minesAndDrops(blockTags, tier.getSpeed())), 1.0F, 1);
    }
}
