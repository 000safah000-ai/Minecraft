package com.jujutsushenanigans.client.render;

import com.jujutsushenanigans.JujutsuShenanigans;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class SixEyesFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    public SixEyesFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        // Only render glowing eyes when the player has the Six Eyes effect
        // Uses the player's own skin texture with the "eyes" render layer (fullbright emissive glow, no custom PNG needed)
        if (entity.hasStatusEffect(JujutsuShenanigans.SIX_EYES_EFFECT)) {
            // Use the player's skin as the glow texture — the eyes RenderLayer makes it emissive like Enderman/Spider eyes
            RenderLayer eyesLayer = RenderLayer.getEyes(entity.getSkinTextures().texture());
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(eyesLayer);

            // Only render the head with the glow effect
            PlayerEntityModel<AbstractClientPlayerEntity> model = this.getContextModel();
            // Hide all parts except head
            model.body.visible = false;
            model.leftArm.visible = false;
            model.rightArm.visible = false;
            model.leftLeg.visible = false;
            model.rightLeg.visible = false;
            model.hat.visible = false;

            // Render head-only glow with blue tint (ARGB: 0xFF4488FF)
            model.render(matrices, vertexConsumer, 15728640, OverlayTexture.DEFAULT_UV, 0xFF4488FF);

            // Restore visibility
            model.body.visible = true;
            model.leftArm.visible = true;
            model.rightArm.visible = true;
            model.leftLeg.visible = true;
            model.rightLeg.visible = true;
            model.hat.visible = true;
        }
    }
}
