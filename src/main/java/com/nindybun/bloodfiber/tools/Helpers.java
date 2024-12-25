package com.nindybun.bloodfiber.tools;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Helpers {
    public static <T> boolean isHoldingItem(Class<T> T, Player player) {
        ItemStack stack = T.isInstance(player.getMainHandItem().getItem()) ? player.getMainHandItem() : player.getOffhandItem();
        return !stack.isEmpty() && T.isInstance(stack.getItem());
    }

    public static <T> ItemStack getItem(Class<T> T, Player player) {
        if (Helpers.isHoldingItem(T, player)) {
            return T.isInstance(player.getMainHandItem().getItem()) ? player.getMainHandItem() : player.getOffhandItem();
        }
        return ItemStack.EMPTY;
    }
}
