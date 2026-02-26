/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_310
 *  net.minecraft.class_636
 *  net.minecraft.class_746
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 */
package com.sp.mixin.layingdown;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.sp.mixininterfaces.LayingDownPlayerEntity;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_310;
import net.minecraft.class_636;
import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={class_636.class})
public class PreventBlockBreakingMixin {
    @Shadow
    @Final
    private class_310 field_3712;

    @WrapMethod(method={"updateBlockBreakingProgress"})
    private boolean preventBlockBreaking(class_2338 pos, class_2350 direction, Operation<Boolean> original) {
        LayingDownPlayerEntity layingDownPlayerEntity;
        class_746 class_7462 = this.field_3712.field_1724;
        boolean bl = !(class_7462 instanceof LayingDownPlayerEntity) || !(layingDownPlayerEntity = (LayingDownPlayerEntity)class_7462).isLayingDown();
        return bl && (Boolean)original.call(new Object[]{pos, direction}) != false;
    }

    @WrapMethod(method={"attackBlock"})
    private boolean preventBlockBreaking2(class_2338 pos, class_2350 direction, Operation<Boolean> original) {
        LayingDownPlayerEntity layingDownPlayerEntity;
        class_746 class_7462 = this.field_3712.field_1724;
        boolean bl = !(class_7462 instanceof LayingDownPlayerEntity) || !(layingDownPlayerEntity = (LayingDownPlayerEntity)class_7462).isLayingDown();
        return bl && (Boolean)original.call(new Object[]{pos, direction}) != false;
    }
}

