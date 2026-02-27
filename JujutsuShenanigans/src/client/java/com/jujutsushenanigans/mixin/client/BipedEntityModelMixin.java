package com.jujutsushenanigans.mixin.client;

import com.jujutsushenanigans.JujutsuShenanigans;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin<T extends LivingEntity> {
    @Shadow public ModelPart head;
    @Shadow public ModelPart body;
    @Shadow public ModelPart rightArm;
    @Shadow public ModelPart leftArm;
    @Shadow public ModelPart rightLeg;
    @Shadow public ModelPart leftLeg;

    @Inject(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
    private void onSetAngles(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        if (livingEntity.hasStatusEffect(JujutsuShenanigans.SIX_EYES_EFFECT)) {
            var effectInstance = livingEntity.getStatusEffect(JujutsuShenanigans.SIX_EYES_EFFECT);
            if (effectInstance != null) {
                int duration = effectInstance.getDuration();
            // 10 seconds = 200 ticks. Collapse starts at 100 ticks remaining.
            if (duration <= 100) {
                float progress = 1.0f - (duration / 100.0f); // 0.0 to 1.0
                
                // Smooth easing
                progress = progress * progress * (3.0f - 2.0f * progress);
                
                // Collapse animation
                // Bend knees
                this.rightLeg.pitch = progress * 1.5f;
                this.leftLeg.pitch = progress * 1.5f;
                this.rightLeg.pivotY = 12.0f + progress * 8.0f;
                this.leftLeg.pivotY = 12.0f + progress * 8.0f;
                this.rightLeg.pivotZ = progress * 5.0f;
                this.leftLeg.pivotZ = progress * 5.0f;
                
                // Lower body
                this.body.pivotY = progress * 8.0f;
                this.body.pitch = progress * 0.5f;
                
                // Lower arms
                this.rightArm.pivotY = 2.0f + progress * 8.0f;
                this.leftArm.pivotY = 2.0f + progress * 8.0f;
                this.rightArm.pitch = progress * 0.2f;
                this.leftArm.pitch = progress * 0.2f;
                
                // Lower head
                this.head.pivotY = progress * 8.0f;
                this.head.pitch += progress * 0.5f;
            }
            }
        }
    }
}
