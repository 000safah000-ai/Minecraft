/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  com.llamalad7.mixinextras.sugar.Local
 *  net.minecraft.class_1282
 *  net.minecraft.class_1283
 *  net.minecraft.class_1309
 *  net.minecraft.class_2561
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 */
package com.sp.mixin.multipledeathvariants;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.sp.entity.ModDamageSources;
import net.minecraft.class_1282;
import net.minecraft.class_1283;
import net.minecraft.class_1309;
import net.minecraft.class_2561;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value={class_1283.class})
public class DamageTrackerMixin {
    @Shadow
    @Final
    private class_1309 field_5877;

    @WrapOperation(method={"getDeathMessage"}, at={@At(value="INVOKE", target="Lnet/minecraft/entity/damage/DamageSource;getDeathMessage(Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/text/Text;")})
    private class_2561 isPlayZoneDeath(class_1282 instance, class_1309 killed, Operation<class_2561> original, @Local class_1282 damageSource) {
        if (instance.method_48792().comp_1246() == ModDamageSources.PLAY_ZONE_TYPE) {
            int randomInt = killed.method_59922().method_39332(1, 7);
            return class_2561.method_43469((String)("death.attack." + damageSource.method_5525() + randomInt), (Object[])new Object[]{this.field_5877.method_5476()});
        }
        if (instance.method_48792().comp_1246() == ModDamageSources.CRACKS_TYPE) {
            int randomInt = killed.method_59922().method_39332(1, 4);
            return class_2561.method_43469((String)("death.attack." + damageSource.method_5525() + randomInt), (Object[])new Object[]{this.field_5877.method_5476()});
        }
        return (class_2561)original.call(new Object[]{instance, killed});
    }
}

