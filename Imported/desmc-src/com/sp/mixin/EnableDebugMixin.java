/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2561
 *  net.minecraft.class_309
 *  net.minecraft.class_310
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.sp.mixin;

import com.sp.DestroyingMinecraftClient;
import net.minecraft.class_2561;
import net.minecraft.class_309;
import net.minecraft.class_310;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={class_309.class})
public abstract class EnableDebugMixin {
    @Shadow
    @Final
    private class_310 field_1678;

    @Shadow
    protected abstract void method_37272(class_2561 var1);

    @Inject(method={"processF3"}, at={@At(value="HEAD")}, cancellable=true)
    private void onHandleDebugKeys(int keyCode, CallbackInfoReturnable<Boolean> cir) {
        if (this.field_1678.field_1724 != null && (this.field_1678.field_1724.method_7337() || this.field_1678.field_1724.method_7325())) {
            if (keyCode == 70) {
                DestroyingMinecraftClient.shouldRenderDebug = !DestroyingMinecraftClient.shouldRenderDebug;
                this.method_37272((class_2561)class_2561.method_43470((String)("Destroying Minecraft Debug Mode: " + (DestroyingMinecraftClient.shouldRenderDebug ? "Enabled" : "Disabled"))));
                cir.setReturnValue((Object)true);
            }
        } else {
            DestroyingMinecraftClient.shouldRenderDebug = false;
        }
    }
}

