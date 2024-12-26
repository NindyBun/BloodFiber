package com.nindybun.bloodfiber.data;

import com.nindybun.bloodfiber.BloodFiber;
import com.nindybun.bloodfiber.dataComponents.ToolRecord;
import com.nindybun.bloodfiber.registries.ModBlocks;
import com.nindybun.bloodfiber.registries.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class LangGen extends LanguageProvider {
    public LangGen(PackOutput output, String locale) {
        super(output, BloodFiber.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add("itemGroup."+BloodFiber.MODID+".creativeTab", "Blood Fiber");
        add("key.categories."+BloodFiber.MODID, "Blood Fiber");
        add("key."+BloodFiber.MODID+".device_radial_menu_key", "Open Tools");

        add(ModItems.BLOOD_FIBER.get(), "Blood Fiber");
        add(ModItems.BLOOD_FIBER_WOVEN.get(), "Woven Blood Fiber");
        add(ModBlocks.BLOOD_FIBER_ORE.get(), "Blood Fiber Ore");
        add(ModBlocks.BLOOD_FIBER_ORE_DEEPSLATE.get(), "Deepslate Blood Fiber Ore");

        add(ModItems.BLOOD_FIBER_BOOTS.get(), "Blood Woven Boots");
        add(ModItems.BLOOD_FIBER_LEGINGS.get(), "Blood Woven Leggings");
        add(ModItems.BLOOD_FIBER_CHESTPIECE.get(), "Blood Woven Chestpiece");
        add(ModItems.BLOOD_FIBER_HEADPIECE.get(), "Blood Woven Headpiece");

        add(ModItems.BLOOD_FIBER_DEVICE.get(), "Blood Fiber Device");
        add(ToolRecord.BLANK.display_name(), "Blood Fiber Device");
        add(ToolRecord.BOW.display_name(), "Blood Fiber Device (BOW)");
        add(ToolRecord.SWORD.display_name(), "Blood Fiber Device (SWORD)");
        add(ToolRecord.PICKAXE.display_name(), "Blood Fiber Device (PICKAXE)");
        add(ToolRecord.SHOVEL.display_name(), "Blood Fiber Device (SHOVEL)");
        add(ToolRecord.AXE.display_name(), "Blood Fiber Device (AXE)");
        add(ToolRecord.HOE.display_name(), "Blood Fiber Device (HOE)");


        add(ModItems.BLOOD_ARROW_ITEM.get(), "Blood Arrow");
    }
}
