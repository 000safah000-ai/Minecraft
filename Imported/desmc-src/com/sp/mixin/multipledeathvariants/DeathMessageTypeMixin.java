/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  net.minecraft.class_3542
 *  net.minecraft.class_8112
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Mutable
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.gen.Invoker
 */
package com.sp.mixin.multipledeathvariants;

import com.mojang.serialization.Codec;
import com.sp.entity.ModDamageSources;
import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.class_3542;
import net.minecraft.class_8112;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;

@Unique
@Mixin(value={class_8112.class})
public class DeathMessageTypeMixin {
    @Shadow
    @Final
    @Mutable
    private static class_8112[] field_42366;
    private static final class_8112 PLAY_ZONE;
    private static final class_8112 CRACKS;
    @Shadow
    @Final
    public static Codec<class_8112> field_42364;

    @Unique
    private static class_8112[] getDeathMessageTypes() {
        return field_42366;
    }

    @Invoker(value="<init>")
    public static class_8112 invokeInit(String par1, int par2, String par3) {
        throw new AssertionError();
    }

    private static class_8112 addVariant(String id) {
        ArrayList<class_8112> variants = new ArrayList<class_8112>(Arrays.asList(field_42366));
        class_8112 messageType = DeathMessageTypeMixin.invokeInit(id, variants.get(variants.size() - 1).ordinal() + 1, id);
        variants.add(messageType);
        class_8112[] newList = variants.toArray(field_42366);
        field_42366 = newList;
        return messageType;
    }

    static {
        PLAY_ZONE = ModDamageSources.PLAY_ZONE_TYPE = DeathMessageTypeMixin.addVariant("play_zone");
        CRACKS = ModDamageSources.CRACKS_TYPE = DeathMessageTypeMixin.addVariant("cracks");
        field_42364 = class_3542.method_28140(DeathMessageTypeMixin::getDeathMessageTypes);
    }
}

