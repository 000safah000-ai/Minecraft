package com.jujutsushenanigans.mixin.client;

import com.jujutsushenanigans.item.custom.SixEyesItem;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin<T extends LivingEntity> {

    @Shadow public ModelPart rightLeg;
    @Shadow public ModelPart leftLeg;
    @Shadow public ModelPart body;
    @Shadow public ModelPart head;
    @Shadow public ModelPart rightArm;
    @Shadow public ModelPart leftArm;

    @Inject(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
    private void onSetAngles(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        if (livingEntity instanceof PlayerEntity player && player instanceof SixEyesItem.SixEyesState state) {
            if (state.isSixEyesActive()) {
                int timer = state.getSixEyesTimer();
                // Last 5 seconds (100 ticks)
                if (timer > 200) {
                    // Calculate progress from 0.0 to 1.0
                    float progress = (timer - 200) / 100.0f;
                    
                    // Smooth easing
                    progress = progress * progress * (3.0f - 2.0f * progress);

                    // Kneeling pose
                    // Legs bend backwards
                    this.rightLeg.pitch = lerp(this.rightLeg.pitch, 1.5f, progress);
                    this.leftLeg.pitch = lerp(this.leftLeg.pitch, 1.5f, progress);
                    
                    // Body leans forward
                    this.body.pitch = lerp(this.body.pitch, 0.5f, progress);
                    
                    // Head looks down
                    this.head.pitch = lerp(this.head.pitch, 0.8f, progress);
                    
                    // Arms hang down loosely
                    this.rightArm.pitch = lerp(this.rightArm.pitch, 0.1f, progress);
                    this.leftArm.pitch = lerp(this.leftArm.pitch, 0.1f, progress);
                    this.rightArm.roll = lerp(this.rightArm.roll, 0.0f, progress);
                    this.leftArm.roll = lerp(this.leftArm.roll, 0.0f, progress);
                }
            }
        }
    }

    private float lerp(float start, float end, float delta) {
        return start + delta * (end - start);
    }
}
