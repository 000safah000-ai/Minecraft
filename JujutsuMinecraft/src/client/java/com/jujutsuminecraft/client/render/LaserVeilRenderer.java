package com.jujutsuminecraft.client.render;

import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import net.minecraft.util.Identifier;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import org.joml.Matrix4f;
import net.minecraft.client.MinecraftClient;

public class LaserVeilRenderer {
    public static final Identifier LASER_SHADER = new Identifier("jujutsuminecraft", "laser_beam");

    public static void register() {
        // Shaders are automatically loaded by Veil if placed in assets/modid/shaders
    }
    
    public static void renderLaserCore(float time, float distance, MatrixStack matrixStack) {
        // Complete GLSL rendering wrapper logic
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.depthMask(false);
        
        ShaderProgram shader = VeilRenderSystem.setShader(LASER_SHADER);
        if (shader != null) {
            shader.setFloat("time", time);
            shader.setVector("coreColor", 0.5f, 0.0f, 1.0f); // Purple
            shader.setVector("outlineColor", 0.0f, 0.5f, 1.0f); // Blue
            shader.setVector("effectColor", 0.8f, 0.2f, 1.0f);
        }
        
        // Draw the laser quad based on distance
        matrixStack.push();
        
        // Match player rotation
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            float yaw = client.player.getYaw(client.getTickDelta());
            float pitch = client.player.getPitch(client.getTickDelta());
            
            // Adjust matrix so Z+ points forward corresponding to where the player looks
            matrixStack.multiply(net.minecraft.util.math.RotationAxis.POSITIVE_Y.rotationDegrees(-yaw + 180));
            matrixStack.multiply(net.minecraft.util.math.RotationAxis.POSITIVE_X.rotationDegrees(-pitch));
        }

        Matrix4f posMat = matrixStack.peek().getPositionMatrix();
        
        ShaderProgram currentShader = VeilRenderSystem.getShader();
        if (currentShader != null) {
            // Provide projection and view matrices explicitly for the custom geometry
            try {
                currentShader.setMatrix("ProjMat", RenderSystem.getProjectionMatrix());
                currentShader.setMatrix("ModelViewMat", posMat);
            } catch (Exception e) {
                // Ignore if uniforms don't exactly match or aren't generated
            }
        }
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        
        float width = 1.5f; // Beam width
        
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        
        // Top quad
        buffer.vertex(posMat, -width, width, 0).texture(0f, 0f).next();
        buffer.vertex(posMat, width, width, 0).texture(0f, 1f).next();
        buffer.vertex(posMat, width, width, distance).texture(1f, 1f).next();
        buffer.vertex(posMat, -width, width, distance).texture(1f, 0f).next();

        // Bottom quad
        buffer.vertex(posMat, -width, -width, distance).texture(1f, 0f).next();
        buffer.vertex(posMat, width, -width, distance).texture(1f, 1f).next();
        buffer.vertex(posMat, width, -width, 0).texture(0f, 1f).next();
        buffer.vertex(posMat, -width, -width, 0).texture(0f, 0f).next();
        
        // Side quad 1
        buffer.vertex(posMat, -width, -width, 0).texture(0f, 0f).next();
        buffer.vertex(posMat, -width, -width, distance).texture(1f, 0f).next();
        buffer.vertex(posMat, -width, width, distance).texture(1f, 1f).next();
        buffer.vertex(posMat, -width, width, 0).texture(0f, 1f).next();

        // Side quad 2
        buffer.vertex(posMat, width, width, 0).texture(0f, 1f).next();
        buffer.vertex(posMat, width, width, distance).texture(1f, 1f).next();
        buffer.vertex(posMat, width, -width, distance).texture(1f, 0f).next();
        buffer.vertex(posMat, width, -width, 0).texture(0f, 0f).next();

        tessellator.draw();
        
        matrixStack.pop();
        
        RenderSystem.enableCull();
        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
    }
}