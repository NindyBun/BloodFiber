package com.nindybun.bloodfiber.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nindybun.bloodfiber.BloodFiber;
import com.nindybun.bloodfiber.registries.ModItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record ToolRecord(String mode, float id, String display_name) {
    public static final ToolRecord BLANK = new ToolRecord("blank", 0f, "item." + BloodFiber.MODID+".blank");
    public static final ToolRecord BOW = new ToolRecord("bow", 0.1f, "item." + BloodFiber.MODID + ".bow");
    public static final ToolRecord SWORD = new ToolRecord("sword", 0.2f, "item." + BloodFiber.MODID+".sword");
    public static final ToolRecord PICKAXE = new ToolRecord("pickaxe", 0.3f, "item." + BloodFiber.MODID+".pickaxe");
    public static final ToolRecord SHOVEL = new ToolRecord("shovel", 0.4f, "item." + BloodFiber.MODID+".shovel");
    public static final ToolRecord AXE = new ToolRecord("axe", 0.5f, "item." + BloodFiber.MODID+".axe");
    public static final ToolRecord HOE = new ToolRecord("hoe", 0.6f, "item." + BloodFiber.MODID+".hoe");
    public static final ToolRecord SHEARS = new ToolRecord("shears", 0.7f, "item." + BloodFiber.MODID+".shears");

    public static final Codec<ToolRecord> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("mode").forGetter(ToolRecord::mode),
                    Codec.FLOAT.fieldOf("id").forGetter(ToolRecord::id),
                    Codec.STRING.fieldOf("display_name").forGetter(ToolRecord::display_name)
            ).apply(instance, ToolRecord::new)
    );

    public static final StreamCodec<ByteBuf, ToolRecord> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, ToolRecord::mode,
            ByteBufCodecs.FLOAT, ToolRecord::id,
            ByteBufCodecs.STRING_UTF8, ToolRecord::display_name,
            ToolRecord::new
    );

    public static ToolRecord getRecord(float id) {
        return switch ((int) (id*10f)) {
            case 1 -> BOW;
            case 2 -> SWORD;
            case 3 -> PICKAXE;
            case 4 -> SHOVEL;
            case 5 -> AXE;
            case 6 -> HOE;
            case 7 -> SHEARS;
            default -> BLANK;
        };
    }

    public static ToolRecord increment(ToolRecord record) {
        return ToolRecord.getRecord((record.id()+0.1f) % 0.8f);
    }
}
