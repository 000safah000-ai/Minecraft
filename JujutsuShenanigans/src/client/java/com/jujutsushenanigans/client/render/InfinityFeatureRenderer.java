package com.jujutsushenanigans.client.render;

import com.jujutsushenanigans.JujutsuShenanigans;
import com.jujutsushenanigans.InfinityState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import ladysnake.satin.api.managed.ManagedCoreShader;
import ladysnake.satin.api.managed.ShaderEffectManager;

@Environment(EnvType.CLIENT)
public class InfinityFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    // Use Satin to load a custom core shader for the entity rendering
    public static final ManagedCoreShader INFINITY_SHADER = ShaderEffectManager.getInstance()
            .manageCoreShader(JujutsuShenanigans.id("infinity_shield"));

    public InfinityFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (entity instanceof InfinityState state && state.isInfinityActive()) {
            matrices.push();
            
            // Scale up to create a 3-block radius barrier
            float scale = 4.0f;
            matrices.scale(scale, scale, scale);
            
            // Adjust position so it stays centered
            matrices.translate(0, -1.5f / scale, 0);

            // We need to tell the vertex consumer to use our shader.
            // We use the entity's base texture just to provide UVs, the shader will override the color.
            RenderLayer baseLayer = RenderLayer.getEntityTranslucent(entity.getSkinTextures().texture());
            RenderLayer layer = INFINITY_SHADER.getRenderLayer(baseLayer);
            
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(layer);
            
            // Render the model
            this.getContextModel().render(matrices, vertexConsumer, light, net.minecraft.client.render.OverlayTexture.DEFAULT_UV, 0xFFFFFFFF);
            
            matrices.pop();
        }
    }
}
