/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  foundry.veil.api.client.render.VeilLevelPerspectiveRenderer
 *  foundry.veil.api.client.render.VeilRenderSystem
 *  foundry.veil.api.client.render.framebuffer.AdvancedFbo
 *  foundry.veil.api.client.render.shader.program.ShaderProgram
 *  net.minecraft.class_243
 *  net.minecraft.class_310
 *  net.minecraft.class_4184
 *  net.minecraft.class_4587
 *  net.minecraft.class_7833
 *  net.minecraft.class_9779
 *  org.joml.Matrix4f
 *  org.joml.Matrix4fc
 *  org.joml.Quaternionf
 *  org.joml.Quaternionfc
 *  org.joml.Vector3d
 *  org.joml.Vector3dc
 *  org.lwjgl.opengl.GL11
 */
package com.sp.render;

import com.sp.DestroyingMinecraft;
import com.sp.config.DestroyingMinecraftConfig;
import com.sp.util.BetterUniforms;
import foundry.veil.api.client.render.VeilLevelPerspectiveRenderer;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.framebuffer.AdvancedFbo;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import java.util.Optional;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_7833;
import net.minecraft.class_9779;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.lwjgl.opengl.GL11;

public class ShadowMapRenderer {
    private static class_4184 currentCamera;
    private static class_310 client;

    public static void renderShadowMap(class_4184 camera) {
        currentCamera = camera;
        client = class_310.method_1551();
        class_243 cameraPos = camera.method_19326();
        class_4587 shadowModelView = ShadowMapRenderer.createShadowModelView(cameraPos.field_1352, cameraPos.field_1351, cameraPos.field_1350, true);
        Matrix4f shadowProjMat = ShadowMapRenderer.createProjMat();
        AdvancedFbo shadowMap = VeilRenderSystem.renderer().getFramebufferManager().getFramebuffer(DestroyingMinecraft.idOf("shadowmap"));
        if (shadowMap != null) {
            GL11.glCullFace((int)1028);
            VeilLevelPerspectiveRenderer.render((AdvancedFbo)shadowMap, (Matrix4fc)shadowModelView.method_23760().method_23761(), (Matrix4fc)shadowProjMat, (Vector3dc)new Vector3d(cameraPos.field_1352, cameraPos.field_1351, cameraPos.field_1350), (Quaternionfc)new Quaternionf(), (float)20.0f, (class_9779)client.method_60646(), (boolean)false);
            GL11.glCullFace((int)1029);
        }
    }

    public static class_4587 createShadowModelView(double CameraX, double CameraY, double CameraZ, boolean doInterval) {
        class_4587 shadowModelView = new class_4587();
        shadowModelView.method_23760().method_23762().identity();
        shadowModelView.method_23760().method_23761().identity();
        shadowModelView.method_23760().method_23761().translate(0.0f, 0.0f, -100.0f);
        ShadowMapRenderer.rotateShadowModelView(shadowModelView.method_23760().method_23761());
        if (doInterval) {
            float offsetX = (float)CameraX % 2.0f;
            float offsetY = (float)CameraY % 2.0f;
            float offsetZ = (float)CameraZ % 2.0f;
            float halfIntervalSize = 1.0f;
            shadowModelView.method_23760().method_23761().translate(offsetX -= halfIntervalSize, offsetY -= halfIntervalSize, offsetZ -= halfIntervalSize);
        }
        return shadowModelView;
    }

    public static void rotateShadowModelView(Matrix4f shadowModelView) {
        switch (DestroyingMinecraftConfig.shaderType) {
            case BLACK_HOLE: {
                shadowModelView.rotate((Quaternionfc)class_7833.field_40714.rotationDegrees(20.0f));
                shadowModelView.rotate((Quaternionfc)class_7833.field_40716.rotationDegrees(180.0f));
                break;
            }
            case SUPERNOVA: {
                shadowModelView.rotate((Quaternionfc)class_7833.field_40716.rotationDegrees(-90.0f));
                shadowModelView.rotate((Quaternionfc)class_7833.field_40718.rotationDegrees(-387.6012f));
                break;
            }
            case PLANET: {
                shadowModelView.rotate((Quaternionfc)class_7833.field_40714.rotationDegrees(25.0f));
                shadowModelView.rotate((Quaternionfc)class_7833.field_40716.rotationDegrees(20.0f));
                break;
            }
            default: {
                shadowModelView.rotate((Quaternionfc)class_7833.field_40714.rotationDegrees(20.0f));
                shadowModelView.rotate((Quaternionfc)class_7833.field_40716.rotationDegrees(-75.0f));
            }
        }
    }

    public static Matrix4f createProjMat() {
        return ShadowMapRenderer.orthographicMatrix(160.0f, 0.01f, 256.0f);
    }

    public static Matrix4f orthographicMatrix(float halfPlaneLength, float nearPlane, float farPlane) {
        return new Matrix4f(1.0f / halfPlaneLength, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f / halfPlaneLength, 0.0f, 0.0f, 0.0f, 0.0f, 2.0f / (nearPlane - farPlane), 0.0f, 0.0f, 0.0f, -(farPlane + nearPlane) / (farPlane - nearPlane), 1.0f);
    }

    public static void setShadowUniforms(ShaderProgram access) {
        if (currentCamera == null) {
            return;
        }
        Matrix4f viewMat = ShadowMapRenderer.createShadowModelView(ShadowMapRenderer.currentCamera.method_19326().field_1352, ShadowMapRenderer.currentCamera.method_19326().field_1351, ShadowMapRenderer.currentCamera.method_19326().field_1350, true).method_23760().method_23761();
        BetterUniforms.setMatrix(access, "shadowViewMatrix", viewMat);
        BetterUniforms.setMatrix(access, "IShadowViewMatrix", viewMat.invert());
        BetterUniforms.setMatrix(access, "shadowProjMat", ShadowMapRenderer.createProjMat());
    }

    public static Optional<Matrix4f> getShadowViewMat() {
        if (currentCamera != null) {
            Matrix4f matrix4f = new Matrix4f();
            ShadowMapRenderer.rotateShadowModelView(matrix4f);
            return Optional.of(matrix4f);
        }
        return Optional.empty();
    }
}

