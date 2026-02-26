/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_3532
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.sp.mixin.layingdown;

import com.sp.mixininterfaces.LayingDownPlayerEntity;
import net.minecraft.class_1297;
import net.minecraft.class_3532;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_1297.class})
public abstract class ClampPlayerYawMixin {
    @Shadow
    public float field_5982;

    @Shadow
    public abstract float method_36454();

    @Shadow
    public abstract void method_36456(float var1);

    @Shadow
    public abstract void method_5847(float var1);

    @Shadow
    public abstract void method_36457(float var1);

    @Shadow
    public abstract float method_36455();

    @Inject(method={"changeLookDirection"}, at={@At(value="TAIL")})
    private void clampLookDir(double cursorDeltaX, double cursorDeltaY, CallbackInfo ci) {
        LayingDownPlayerEntity layingDownPlayerEntity;
        class_1297 class_12972 = (class_1297)this;
        if (class_12972 instanceof LayingDownPlayerEntity && (layingDownPlayerEntity = (LayingDownPlayerEntity)class_12972).isLayingDown()) {
            this.clampPassengerYaw();
        }
    }

    @Unique
    protected void clampPassengerYaw() {
        float f = class_3532.method_15393((float)(this.method_36454() - 180.0f));
        float g = class_3532.method_15363((float)f, (float)-90.0f, (float)90.0f);
        this.field_5982 += g - f;
        this.method_36456(this.method_36454() + g - f);
        this.method_5847(this.method_36454());
        this.method_36457(class_3532.method_15363((float)this.method_36455(), (float)-10.0f, (float)90.0f));
    }
}

