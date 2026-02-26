/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_3532
 *  net.minecraft.class_638
 *  net.minecraft.class_703
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Redirect
 */
package com.sp.mixin.gravity;

import com.sp.cca.InitializeComponents;
import com.sp.cca.custom.world.WorldDestructionEventsComponent;
import net.minecraft.class_3532;
import net.minecraft.class_638;
import net.minecraft.class_703;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={class_703.class})
public class NoGravityParticleMixin {
    @Shadow
    protected float field_3844;
    @Shadow
    @Final
    protected class_638 field_3851;

    @Redirect(method={"tick"}, at=@At(value="FIELD", target="Lnet/minecraft/client/particle/Particle;gravityStrength:F", opcode=180))
    private float reduceGravity(class_703 instance) {
        WorldDestructionEventsComponent component = (WorldDestructionEventsComponent)InitializeComponents.EVENTS.get((Object)this.field_3851);
        return (float)class_3532.method_16436((double)component.getGravityLerp(), (double)this.field_3844, (double)0.0);
    }
}

