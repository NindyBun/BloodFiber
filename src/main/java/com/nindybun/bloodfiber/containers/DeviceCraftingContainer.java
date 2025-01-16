package com.nindybun.bloodfiber.containers;

import com.nindybun.bloodfiber.BloodFiber;
import com.nindybun.bloodfiber.items.BloodFiberDevice;
import com.nindybun.bloodfiber.menus.DeviceBindingMenu;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DeviceCraftingContainer implements StackedContentsCompatible, Container {

    private final NonNullList<ItemStack> ITEMS;
    private final DeviceBindingMenu MENU;
    private final int SIZE;

    public DeviceCraftingContainer(DeviceBindingMenu menu, int size) {
        this(menu, size, NonNullList.withSize(size, ItemStack.EMPTY));
    }

    public DeviceCraftingContainer(DeviceBindingMenu menu, int size, NonNullList<ItemStack> items) {
        this.ITEMS = items;
        this.SIZE = size;
        this.MENU = menu;
    }

    @Override
    public int getContainerSize() {
        return this.ITEMS.size();
    }

    public boolean isEmpty() {
        Iterator var1 = this.ITEMS.iterator();

        ItemStack itemstack;
        do {
            if (!var1.hasNext()) {
                return true;
            }

            itemstack = (ItemStack)var1.next();
        } while(itemstack.isEmpty());

        return false;
    }

    public ItemStack getItem(int slot) {
        return slot >= this.getContainerSize() ? ItemStack.EMPTY : this.ITEMS.get(slot);
    }

    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(this.ITEMS, slot);
    }

    public ItemStack removeItem(int slot, int amount) {
        ItemStack itemstack = ContainerHelper.removeItem(this.ITEMS, slot, amount);
        if (!itemstack.isEmpty()) {
            this.MENU.slotsChanged(this);
        }

        return itemstack;
    }

    public void setItem(int slot, ItemStack stack) {
        this.ITEMS.set(slot, stack);
        this.MENU.slotsChanged(this);
    }

    public void setChanged() {
    }

    public boolean stillValid(Player player) {
        return true;
    }

    public void clearContent() {
        this.ITEMS.clear();
    }

    public int getSize() {
        return this.SIZE;
    }

    public List<ItemStack> getItems() {
        return List.copyOf(this.ITEMS);
    }

    public void fillStackedContents(StackedContents contents) {
        Iterator var2 = this.ITEMS.iterator();

        while(var2.hasNext()) {
            ItemStack itemstack = (ItemStack)var2.next();
            contents.accountSimpleStack(itemstack);
        }

    }

    public List<ItemStack> asInputs() {
        List<ItemStack> list = new ArrayList<>();
        for (ItemStack stack : this.ITEMS) {
            if (!stack.isEmpty()) {
                list.add(stack);
            }
        }
        return list;
    }

}
