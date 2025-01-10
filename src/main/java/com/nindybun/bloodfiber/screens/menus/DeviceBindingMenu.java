package com.nindybun.bloodfiber.screens.menus;

import com.nindybun.bloodfiber.items.BloodFiberDevice;
import com.nindybun.bloodfiber.registries.ModMenus;
import com.nindybun.bloodfiber.tools.Helpers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class DeviceBindingMenu extends AbstractContainerMenu {
    private final Player player;
    private final Inventory inventory;
    private final ItemStack device;

    public DeviceBindingMenu(int containerId, Inventory playerInventory, FriendlyByteBuf buf) {
        super(ModMenus.DEVICE_BINDING_MENU.get(), containerId);
        this.player = playerInventory.player;
        this.inventory = playerInventory;
        this.device = Helpers.getItem(BloodFiberDevice.class, player);
    }

    public DeviceBindingMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, null);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.isAlive();
    }
}
