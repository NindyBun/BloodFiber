package com.nindybun.bloodfiber.events.client;


import com.nindybun.bloodfiber.BloodFiber;
import com.nindybun.bloodfiber.items.BloodFiberDevice;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.neoforge.client.event.RenderHandEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = BloodFiber.MODID)
public class ClientEvents {

    @SubscribeEvent
    public static void renderHands(RenderHandEvent event) {
        InteractionHand hand = event.getHand();
        Player player = Minecraft.getInstance().player;

        if (hand == InteractionHand.OFF_HAND && player.isUsingItem()
                && player.getUseItem().getItem() instanceof BloodFiberDevice
                && player.getUsedItemHand() == InteractionHand.MAIN_HAND) {
            event.setCanceled(true);
        } else if (hand == InteractionHand.MAIN_HAND && player.isUsingItem()
                && player.getUseItem().getItem() instanceof BloodFiberDevice
                && player.getUsedItemHand() == InteractionHand.OFF_HAND) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onComputeFovModifierEvent(ComputeFovModifierEvent event) {
        Player player = event.getPlayer();
        ItemStack stack = player.getUseItem();
        if (player.isUsingItem() && stack.getItem() instanceof BloodFiberDevice) {
            float f = 1f;
            int i = player.getTicksUsingItem();
            float f1 = (float)i / 20.0F;
            if (f1 > 1.0F) {
                f1 = 1.0F;
            } else {
                f1 *= f1;
            }
            f *= 1.0F - f1 * 0.15F;
            event.setNewFovModifier(f);
        }
    }

}
