package com.nindybun.bloodfiber.data;

import com.nindybun.bloodfiber.BloodFiber;
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

        add(ModItems.BLOOD_FIBER.get(), "Blood Fiber");
        add(ModItems.BLOOD_FIBER_WOVEN.get(), "Woven Blood Fiber");
        add(ModBlocks.BLOOD_FIBER_ORE.get(), "Blood Fiber Ore");
        add(ModBlocks.BLOOD_FIBER_ORE_DEEPSLATE.get(), "Deepslate Blood Fiber Ore");
    }
}
