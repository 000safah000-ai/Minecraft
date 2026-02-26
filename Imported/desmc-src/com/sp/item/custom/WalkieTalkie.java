/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1268
 *  net.minecraft.class_1271
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  net.minecraft.class_1657
 *  net.minecraft.class_1792
 *  net.minecraft.class_1792$class_1793
 *  net.minecraft.class_1799
 *  net.minecraft.class_1839
 *  net.minecraft.class_1937
 *  net.minecraft.class_3419
 */
package com.sp.item.custom;

import com.sp.component.ModDataComponentTypes;
import com.sp.sounds.ModSounds;
import net.minecraft.class_1268;
import net.minecraft.class_1271;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1839;
import net.minecraft.class_1937;
import net.minecraft.class_3419;

public class WalkieTalkie
extends class_1792 {
    public WalkieTalkie(class_1792.class_1793 settings) {
        super(settings);
    }

    public class_1271<class_1799> method_7836(class_1937 world, class_1657 user, class_1268 hand) {
        class_1799 itemStack = user.method_5998(hand);
        user.method_6019(hand);
        itemStack.method_57379(ModDataComponentTypes.WALKIE_TALKIE_ON, (Object)true);
        world.method_43129(user, (class_1297)user, ModSounds.WALKIE_TALKIE, class_3419.field_15248, 0.4f, 1.0f);
        return class_1271.method_22428((Object)itemStack);
    }

    public void method_7840(class_1799 stack, class_1937 world, class_1309 user, int remainingUseTicks) {
        stack.method_57379(ModDataComponentTypes.WALKIE_TALKIE_ON, (Object)false);
    }

    public class_1839 method_7853(class_1799 stack) {
        return class_1839.field_39058;
    }
}

