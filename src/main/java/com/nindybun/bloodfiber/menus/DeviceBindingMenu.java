package com.nindybun.bloodfiber.menus;

import com.nindybun.bloodfiber.BloodFiber;
import com.nindybun.bloodfiber.containers.DeviceCraftingContainer;
import com.nindybun.bloodfiber.items.BloodFiberDevice;
import com.nindybun.bloodfiber.registries.ModMenus;
import com.nindybun.bloodfiber.slots.DeviceSlot;
import com.nindybun.bloodfiber.tools.Helpers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class DeviceBindingMenu extends AbstractContainerMenu {
    private final Player player;
    private final Inventory inventory;
    private final ItemStack device;
    private final DeviceCraftingContainer container = new DeviceCraftingContainer(this, 9);

    public DeviceBindingMenu(int containerId, Inventory playerInventory, FriendlyByteBuf buf) {
        super(ModMenus.DEVICE_BINDING_MENU.get(), containerId);
        this.player = playerInventory.player;
        this.inventory = playerInventory;
        this.device = Helpers.getItem(BloodFiberDevice.class, player);

        //Generates Device slots
        this.addSlot(new DeviceSlot(this.container, 0, 80, 24));
        this.addSlot(new DeviceSlot(this.container, 1, 101, 29));
        this.addSlot(new DeviceSlot(this.container, 2, 106, 50));
        this.addSlot(new DeviceSlot(this.container, 3, 101, 71));
        this.addSlot(new DeviceSlot(this.container, 4, 80, 76));
        this.addSlot(new DeviceSlot(this.container, 5, 59, 71));
        this.addSlot(new DeviceSlot(this.container, 6, 54, 50));
        this.addSlot(new DeviceSlot(this.container, 7, 59, 29));
        this.addSlot(new DeviceSlot(this.container, 8, 80, 50));

        //Generates Player slots
        for (int l = 0; l < 3; l++) {
            for (int j1 = 0; j1 < 9; j1++) {
                this.addSlot(new Slot(playerInventory, j1 + (l + 1) * 9, 8 + j1 * 18, 140 + l * 18));
            }
        }

        for (int i1 = 0; i1 < 9; i1++) {
            this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 198));
        }
        
    }

    public DeviceBindingMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, null);
    }


    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.isAlive()
                && Helpers.isHoldingItem(BloodFiberDevice.class, player)
                && Helpers.getItem(BloodFiberDevice.class, player).equals(this.device);
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        if (player instanceof ServerPlayer) {
            for (ItemStack stack : this.container.asInputs()) {
                if (player.isAlive() && !((ServerPlayer) player).hasDisconnected()) {
                    player.getInventory().placeItemBackInInventory(stack);
                } else {
                    player.drop(stack, false);
                }
            }
        }
    }

}
