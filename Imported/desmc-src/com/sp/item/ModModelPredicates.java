/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1792
 *  net.minecraft.class_2960
 *  net.minecraft.class_5272
 */
package com.sp.item;

import com.sp.DestroyingMinecraft;
import com.sp.component.ModDataComponentTypes;
import com.sp.item.ModItems;
import net.minecraft.class_1792;
import net.minecraft.class_2960;
import net.minecraft.class_5272;

public class ModModelPredicates {
    public static void registerModelPredicates() {
        class_5272.method_27879((class_1792)ModItems.WALKIE_TALKIE_ITEM, (class_2960)DestroyingMinecraft.idOf("on"), (stack, world, entity, seed) -> Boolean.TRUE.equals(stack.method_57824(ModDataComponentTypes.WALKIE_TALKIE_ON)) ? 1.0f : 0.0f);
    }
}

