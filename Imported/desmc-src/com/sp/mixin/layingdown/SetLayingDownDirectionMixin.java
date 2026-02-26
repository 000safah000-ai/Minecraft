/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1299
 *  net.minecraft.class_1309
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.sp.mixin.layingdown;

import com.sp.block.custom.ChairBlock;
import com.sp.mixininterfaces.LayingDownPlayerEntity;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1309;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={class_1309.class})
public abstract class SetLayingDownDirectionMixin
extends class_1297 {
    public SetLayingDownDirectionMixin(class_1299<?> type, class_1937 world) {
        super(type, world);
    }

    @Inject(method={"getSleepingDirection"}, at={@At(value="RETURN")}, cancellable=true)
    private void setDirection(CallbackInfoReturnable<class_2350> cir) {
        LayingDownPlayerEntity layingDownPlayerEntity;
        class_1309 class_13092 = (class_1309)this;
        if (class_13092 instanceof LayingDownPlayerEntity && (layingDownPlayerEntity = (LayingDownPlayerEntity)class_13092).isLayingDown()) {
            class_2338 layingDownBlockPos = layingDownPlayerEntity.getLayingDownPos().orElse(null);
            cir.setReturnValue(layingDownBlockPos != null ? ChairBlock.getDirection(this.method_37908(), layingDownBlockPos) : null);
        }
    }
}

