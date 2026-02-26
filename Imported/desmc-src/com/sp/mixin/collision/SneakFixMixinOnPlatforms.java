/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1657
 *  net.minecraft.class_238
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.sp.mixin.collision;

import com.sp.entity.ModEntities;
import com.sp.entity.custom.BlockPhysicsEntity;
import java.util.List;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_238;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={class_1657.class})
public class SneakFixMixinOnPlatforms {
    @Inject(method={"isSpaceAroundPlayerEmpty"}, at={@At(value="HEAD")}, cancellable=true)
    private void isSpaceAroundPlayerEmpty(double offsetX, double offsetZ, float f, CallbackInfoReturnable<Boolean> cir) {
        List blockPhysicsEntities;
        class_1657 player = (class_1657)this;
        class_238 box = player.method_5829();
        class_238 newBox = new class_238(box.field_1323 + offsetX, box.field_1322 - (double)f - (double)1.0E-5f, box.field_1321 + offsetZ, box.field_1320 + offsetX, box.field_1322, box.field_1324 + offsetZ);
        boolean doesNotCollide = player.method_37908().method_8587((class_1297)player, newBox);
        if (!doesNotCollide) {
            cir.setReturnValue((Object)false);
        }
        if (!(blockPhysicsEntities = player.method_37908().method_18023(ModEntities.BLOCK_PHYSICS_ENTITY, newBox, entity1 -> true)).isEmpty()) {
            for (BlockPhysicsEntity blockPhysicsEntity : blockPhysicsEntities) {
                if (!blockPhysicsEntity.collides(newBox)) continue;
                cir.setReturnValue((Object)false);
            }
        }
    }
}

