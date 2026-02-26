package com.jujutsushenanigans.client.render;

import com.jujutsushenanigans.JujutsuShenanigans;
import com.jujutsushenanigans.item.ModItems;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class SixEyesFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    private static final Identifier SIX_EYES_TEXTURE = JujutsuShenanigans.id("textures/entity/six_eyes_glow.png");

    public SixEyesFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        // Check if player has the item in inventory
        boolean hasSixEyes = false;
        for (ItemStack stack : entity.getInventory().main) {
            if (stack.isOf(ModItems.SIX_EYES)) {
                hasSixEyes = true;
                break;
            }
        }
        if (!hasSixEyes) {
            for (ItemStack stack : entity.getInventory().offHand) {
                if (stack.isOf(ModItems.SIX_EYES)) {
                    hasSixEyes = true;
                    break;
                }
            }
        }

        if (hasSixEyes) {
            // Render glowing eyes
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEyes(SIX_EYES_TEXTURE));
            this.getContextModel().render(matrices, vertexConsumer, 15728640, OverlayTexture.DEFAULT_UV);
        }
    }
}
