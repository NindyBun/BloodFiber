package com.nindybun.bloodfiber.events.server;

import com.nindybun.bloodfiber.BloodFiber;
import com.nindybun.bloodfiber.dataComponents.ToolRecord;
import com.nindybun.bloodfiber.items.BloodFiberDevice;
import com.nindybun.bloodfiber.registries.ModComponents;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

import java.util.Collection;

@EventBusSubscriber(modid = BloodFiber.MODID)
public class LivingDropsEvents {

    @SubscribeEvent
    public static void livingDropsEvent(LivingDropsEvent event) {
        Collection<ItemEntity> collection = event.getDrops();
        for (ItemEntity itemEntity : collection) {
            ItemStack stack = itemEntity.getItem();
            if (stack.getItem() instanceof BloodFiberDevice) {
                stack.set(ModComponents.TOOL_RECORD.get(), ToolRecord.BLANK);
            }
        }
    }
}
