package com.nindybun.bloodfiber.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.nindybun.bloodfiber.BloodFiber;
import com.nindybun.bloodfiber.screens.menus.DeviceBindingMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class DeviceBindingScreen extends AbstractContainerScreen<DeviceBindingMenu> {
    private final ResourceLocation GUI = ResourceLocation.fromNamespaceAndPath(BloodFiber.MODID, "textures/gui/blood_fiber_device_gui.png");
    private final int GUI_WIDTH = 384;
    private final int GUI_HEIGHT = 256;
    private final Component DESCRIPTION_TITLE = Component.translatable("menu.title."+BloodFiber.MODID+".device_description");
    private final Component MODIFIER_TITLE = Component.translatable("menu.title."+BloodFiber.MODID+".device_modifier");


    public DeviceBindingScreen(DeviceBindingMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageHeight = 222;
        this.imageWidth = 176;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        int relX = (this.width-this.imageWidth)/2;
        int relY = (this.height-this.imageHeight)/2;
        guiGraphics.drawString(this.font, this.DESCRIPTION_TITLE, relX + 8 - 143, relY + 6, 16250871, false);
        guiGraphics.drawString(this.font, this.MODIFIER_TITLE, relX + 8 + 177, relY + 6, 16250871, false);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        //The GUI uses the same size as the generic_54 container gui
        int relX = (this.width-this.imageWidth)/2;
        int relY = (this.height-this.imageHeight)/2;
        //main body
        guiGraphics.blit(this.GUI, relX, relY, 0, 0, 0, 176, 222, this.GUI_WIDTH, this.GUI_HEIGHT);
        //left body
        guiGraphics.blit(this.GUI, relX + 177, relY, 0, 177, 0, 143, 222, this.GUI_WIDTH, this.GUI_HEIGHT);
        //right body
        guiGraphics.blit(this.GUI, relX - 143, relY, 0, 177, 0, 143, 222, this.GUI_WIDTH, this.GUI_HEIGHT);
    }

}
