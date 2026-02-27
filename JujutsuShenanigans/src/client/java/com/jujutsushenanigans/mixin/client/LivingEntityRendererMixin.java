package com.jujutsushenanigans.mixin.client;

import com.jujutsushenanigans.JujutsuShenanigans;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity> {

    @Inject(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"))
    private void onRenderHead(T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        if (livingEntity.hasStatusEffect(JujutsuShenanigans.SIX_EYES_EFFECT)) {
            var effectInstance = livingEntity.getStatusEffect(JujutsuShenanigans.SIX_EYES_EFFECT);
            if (effectInstance != null) {
                int duration = effectInstance.getDuration();
                if (duration <= 100) {
                    float progress = 1.0f - (duration / 100.0f);
                    progress = progress * progress * (3.0f - 2.0f * progress);
                    
                    // Translate down so the knees touch the ground
                    matrixStack.translate(0.0, -0.4 * progress, 0.0);
                }
            }
        }
    }
}
