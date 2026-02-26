/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2350
 *  net.minecraft.class_638
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.sp.mixin;

import net.minecraft.class_2350;
import net.minecraft.class_638;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={class_638.class})
public class NoDirectionalBrightnessMixin {
    @Inject(method={"getBrightness"}, at={@At(value="RETURN")}, cancellable=true)
    private void setReturn(class_2350 direction, boolean shaded, CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue((Object)Float.valueOf(1.0f));
    }
}

