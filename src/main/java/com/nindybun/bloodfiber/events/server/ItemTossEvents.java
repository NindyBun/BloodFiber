package com.nindybun.bloodfiber.events.server;

import com.nindybun.bloodfiber.BloodFiber;
import com.nindybun.bloodfiber.dataComponents.ToolRecord;
import com.nindybun.bloodfiber.items.BloodFiberDevice;
import com.nindybun.bloodfiber.registries.ModComponents;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;

@EventBusSubscriber(modid = BloodFiber.MODID)
public class ItemTossEvents {

    @SubscribeEvent
    public static void itemTossEvent(ItemTossEvent event) {
        if (event.getEntity().getItem().getItem() instanceof BloodFiberDevice) {
            ItemStack stack = event.getEntity().getItem();
            stack.set(ModComponents.TOOL_RECORD.get(), ToolRecord.BLANK);
        }
    }
}
