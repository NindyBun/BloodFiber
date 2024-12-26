package com.nindybun.bloodfiber.screens;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.nindybun.bloodfiber.BloodFiber;
import com.nindybun.bloodfiber.dataComponents.ToolRecord;
import com.nindybun.bloodfiber.events.client.KeyEvents;
import com.nindybun.bloodfiber.items.BloodFiberDevice;
import com.nindybun.bloodfiber.network.packets.SetDeviceTool;
import com.nindybun.bloodfiber.registries.ModComponents;
import com.nindybun.bloodfiber.registries.ModItems;
import com.nindybun.bloodfiber.tools.Helpers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.navigation.FocusNavigationEvent;
import net.minecraft.client.gui.navigation.ScreenDirection;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Matrix4fStack;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import static com.nindybun.bloodfiber.events.client.KeyEvents.device_key;


@EventBusSubscriber(value = Dist.CLIENT, modid = BloodFiber.MODID)
public class DeviceRadialMenu extends Screen {
    private int selected = -1;
    private List<ItemStack> tools = new ArrayList<>();
    private final ItemStack stack;
    private final Player player;

    public DeviceRadialMenu(ItemStack stack, Player player) {
        super(Component.literal("Title"));
        ItemStack s = new ItemStack(ModItems.BLOOD_FIBER_DEVICE.get());
        tools.add(s);
        s = new ItemStack(ModItems.BLOOD_FIBER_DEVICE.get());
        s.set(ModComponents.TOOL_RECORD.get(), ToolRecord.BOW);
        tools.add(s);
        s = new ItemStack(ModItems.BLOOD_FIBER_DEVICE.get());
        s.set(ModComponents.TOOL_RECORD.get(), ToolRecord.SWORD);
        tools.add(s);
        s = new ItemStack(ModItems.BLOOD_FIBER_DEVICE.get());
        s.set(ModComponents.TOOL_RECORD.get(), ToolRecord.PICKAXE);
        tools.add(s);
        s = new ItemStack(ModItems.BLOOD_FIBER_DEVICE.get());
        s.set(ModComponents.TOOL_RECORD.get(), ToolRecord.SHOVEL);
        tools.add(s);
        s = new ItemStack(ModItems.BLOOD_FIBER_DEVICE.get());
        s.set(ModComponents.TOOL_RECORD.get(), ToolRecord.AXE);
        tools.add(s);
        s = new ItemStack(ModItems.BLOOD_FIBER_DEVICE.get());
        s.set(ModComponents.TOOL_RECORD.get(), ToolRecord.HOE);
        tools.add(s);
        this.stack = stack;
        this.player = player;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        int numberOfSlices = this.tools.size();
        if (numberOfSlices == 0)
            return;

        float radiusIn = 20;
        float radiusOut = radiusIn * 2;
        int x = width / 2;
        int y = height / 2;

        PoseStack poseStack = pGuiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(0, 0, 0);
        drawBackground(poseStack, numberOfSlices, pMouseX, pMouseY, x, y, radiusIn, radiusOut);
        poseStack.popPose();

        poseStack.pushPose();
        drawItem(pGuiGraphics, numberOfSlices, x, y, radiusIn, radiusOut);
        poseStack.popPose();

        poseStack.pushPose();
        drawToolTip(pGuiGraphics, numberOfSlices, x, y, radiusIn, radiusOut);
        poseStack.popPose();
    }

    public void drawToolTip(GuiGraphics guiGraphics, int sections, int x, int y, float radiusIn, float radiusOut){
        for (int j = 0; j < sections; j++) {
            float start = (((j - 0.5f) / (float) sections) + 0.25f) * 360;
            float end = (((j + 0.5f) / (float) sections) + 0.25f) * 360;
            float itemRadius = (radiusIn+radiusOut)/2;
            float middle = (float) Math.toRadians(start+end)/2;
            float midX = x - itemRadius * (float) Math.cos(middle);
            float midY = y - itemRadius * (float) Math.sin(middle);
            if (selected == j)
                guiGraphics.renderTooltip(Minecraft.getInstance().font, tools.get(j), (int)midX, (int)midY);
        }
    }

    public void drawItem(GuiGraphics graphics, int sections, int x, int y, float radiusIn, float radiusOut){
        //PoseStack poseStack = graphics.pose();
        //Matrix4fStack matrix4fStack = RenderSystem.getModelViewStack();
        //matrix4fStack.pushMatrix();
        //matrix4fStack.mul(poseStack.last().pose());
        //matrix4fStack.translate(-8, -8, 0);
        //RenderSystem.applyModelViewMatrix();

        //poseStack.translate(0, 0, 200);
        for (int j = 0; j < sections; j++) {
            float start = (((j - 0.5f) / (float) sections) + 0.25f) * 360;
            float end = (((j + 0.5f) / (float) sections) + 0.25f) * 360;
            float itemRadius = (radiusIn+radiusOut)/2;
            float middle = (float) Math.toRadians(start+end)/2;
            float midX = x - itemRadius * (float) Math.cos(middle) - 8;
            float midY = y - itemRadius * (float) Math.sin(middle) - 8;
            ItemStack stack = this.tools.get(j);
            graphics.renderItem(stack, (int)midX, (int)midY);
            graphics.renderItemDecorations(this.font, stack, (int)midX, (int)midY, "");
            //this.itemRenderer.renderGuiItemDecorations(this.font, stack, (int)midX, (int)midY, "");
        }
        //matrix4fStack.popMatrix();
        //RenderSystem.applyModelViewMatrix();
    }

    public void drawBackground(PoseStack poseStack, int sections, int mouseX, int mouseY, int x, int y, float radiusIn, float radiusOut){
        //RenderSystem.enableBlend();
        //RenderSystem.defaultBlendFunc();
        //RenderSystem.setShader(GameRenderer::getPositionColorShader);
        //RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        //Tesselator tesselator = Tesselator.getInstance();
        //tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        VertexConsumer builder = bufferSource.getBuffer(RenderType.GUI);

        for (int j = 0; j < sections; j++){
            float s0 = (((0 - 0.5f) / (float) sections) + 0.25f) * 360;
            double angle = Math.toDegrees(Math.atan2(y-mouseY, x-mouseX)); //Angle the mouse makes with the screen's equator
            double distance = Math.sqrt(Math.pow(x-mouseX, 2) + Math.pow(y-mouseY, 2)); //Distance of the mouse from the center of the screen
            if (angle < s0) {
                angle += 360;
            }
            float start = (((j - 0.5f) / (float) sections) + 0.25f) * 360;
            float end = (((j + 0.5f) / (float) sections) + 0.25f) * 360;
            if ((distance >= radiusOut && distance < radiusIn) || distance < radiusIn || distance >= radiusOut)
                selected = -1;
            if (angle >= start && angle < end && distance >= radiusIn && distance < radiusOut) {
                selected = j;
                break;
            }
        }

        for (int j = 0; j < sections; j++){
            float start = (((j - 0.5f) / (float) sections) + 0.25f) * 360;
            float end = (((j + 0.5f) / (float) sections) + 0.25f) * 360;

            if (this.selected == j)
                drawPieArc(builder, x, y, 0, radiusIn, radiusOut, start, end, 255, 255, 255, 64);
            else
                drawPieArc(builder, x, y, 0, radiusIn, radiusOut, start, end, 0, 0, 0, 64);

            //if (this.savedName.equals(this.tools.get(j).getName()))
            //    drawPieArc(buffer, x, y, 0, radiusIn, radiusOut, start, end, 0, 255, 0, 64);

        }

        //tesselator.clear();
        //RenderSystem.disableBlend();
    }

    public void drawPieArc(VertexConsumer buffer, float x, float y, float z, float radiusIn, float radiusOut, float startAngle, float endAngle, int r, int g, int b, int a){
        float angle = endAngle - startAngle;
        int sections = (int)Math.max(1, Math.nextUp(angle / 5.0F));

        startAngle = (float) Math.toRadians(startAngle);
        endAngle = (float) Math.toRadians(endAngle);
        angle = endAngle - startAngle;

        for (int i = 0; i < sections; i++)
        {
            float angle1 = startAngle + (i / (float) sections) * angle;
            float angle2 = startAngle + ((i + 1) / (float) sections) * angle;

            //subtracting goes top clockwise
            //addition goes bottom clockwise
            float pos1InX = x - radiusIn * (float) Math.cos(angle1);
            float pos1InY = y - radiusIn * (float) Math.sin(angle1);
            float pos1OutX = x - radiusOut * (float) Math.cos(angle1);
            float pos1OutY = y - radiusOut * (float) Math.sin(angle1);
            float pos2OutX = x - radiusOut * (float) Math.cos(angle2);
            float pos2OutY = y - radiusOut * (float) Math.sin(angle2);
            float pos2InX = x - radiusIn * (float) Math.cos(angle2);
            float pos2InY = y - radiusIn * (float) Math.sin(angle2);

            buffer.addVertex(pos1OutX, pos1OutY, z).setColor(r, g, b, a);
            buffer.addVertex(pos1InX, pos1InY, z).setColor(r, g, b, a);
            buffer.addVertex(pos2InX, pos2InY, z).setColor(r, g, b, a);
            buffer.addVertex(pos2OutX, pos2OutY, z).setColor(r, g, b, a);
        }
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        this.processClick();
        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }

    private void processClick() {
        if (selected != -1) {
            PacketDistributor.sendToServer(new SetDeviceTool.Data(this.tools.get(this.selected).get(ModComponents.TOOL_RECORD.get())));
        }
        stack.getAttributeModifiers();
        onClose();
    }

    @SubscribeEvent
    public static  void overlayEvent(RenderGuiLayerEvent.Pre event) {
        if (Minecraft.getInstance().screen instanceof DeviceRadialMenu) {
            if (event.getName().equals(VanillaGuiLayers.CROSSHAIR)) {
                event.setCanceled(true);
            }
        }
    }

    @Override
    public void removed() {
        super.removed();
        KeyEvents.wipeOpen();
    }

    @Override
    public void tick() {
        super.tick();
        if (!KeyEvents.isKeyDown(device_key)){
            Minecraft.getInstance().setScreen(null);
            KeyEvents.wipeOpen();
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
