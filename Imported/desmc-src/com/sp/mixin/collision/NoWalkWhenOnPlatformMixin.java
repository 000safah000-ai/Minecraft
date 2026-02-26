/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1309
 *  net.minecraft.class_572
 *  org.spongepowered.asm.mixin.Mixin
 */
package com.sp.mixin.collision;

import net.minecraft.class_1309;
import net.minecraft.class_572;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value={class_572.class})
public class NoWalkWhenOnPlatformMixin<T extends class_1309> {
}

