/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  net.minecraft.class_2378
 *  net.minecraft.class_2960
 *  net.minecraft.class_7923
 *  net.minecraft.class_9331
 *  net.minecraft.class_9331$class_9332
 */
package com.sp.component;

import com.mojang.serialization.Codec;
import com.sp.DestroyingMinecraft;
import java.util.function.UnaryOperator;
import net.minecraft.class_2378;
import net.minecraft.class_2960;
import net.minecraft.class_7923;
import net.minecraft.class_9331;

public class ModDataComponentTypes {
    public static final class_9331<Boolean> WALKIE_TALKIE_ON = ModDataComponentTypes.register("walkie_talkie_on", builder -> builder.method_57881((Codec)Codec.BOOL));

    private static <T> class_9331<T> register(String name, UnaryOperator<class_9331.class_9332<T>> builderOperator) {
        return (class_9331)class_2378.method_10230((class_2378)class_7923.field_49658, (class_2960)DestroyingMinecraft.idOf(name), (Object)((class_9331.class_9332)builderOperator.apply(class_9331.method_57873())).method_57880());
    }

    public static void registerDataComponentTypes() {
    }
}

