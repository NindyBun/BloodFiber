package com.nindybun.bloodfiber.slots;

import com.nindybun.bloodfiber.items.BloodFiberDevice;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class DeviceSlot extends Slot {
    public DeviceSlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        if (stack.getItem() instanceof BloodFiberDevice) {
            return false;
        }
        return true;
    }
}
