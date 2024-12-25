package com.nindybun.bloodfiber.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record ToolRecord(String mode, float id) {
    public static final ToolRecord BLANK = new ToolRecord("blank", 0f);
    public static final ToolRecord BOW = new ToolRecord("bow", 0.1f);
    public static final ToolRecord SWORD = new ToolRecord("sword", 0.2f);
    public static final ToolRecord PICKAXE = new ToolRecord("pickaxe", 0.3f);
    public static final ToolRecord SHOVEL = new ToolRecord("shovel", 0.4f);
    public static final ToolRecord AXE = new ToolRecord("axe", 0.5f);
    public static final ToolRecord HOE = new ToolRecord("hoe", 0.6f);

    public static final Codec<ToolRecord> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("mode").forGetter(ToolRecord::mode),
                    Codec.FLOAT.fieldOf("id").forGetter(ToolRecord::id)
            ).apply(instance, ToolRecord::new)
    );

    public static final StreamCodec<ByteBuf, ToolRecord> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, ToolRecord::mode,
            ByteBufCodecs.FLOAT, ToolRecord::id,
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
            default -> BLANK;
        };
    }

    public static ToolRecord increment(ToolRecord record) {
        return ToolRecord.getRecord((record.id()+0.1f) % 0.7f);
    }
}
