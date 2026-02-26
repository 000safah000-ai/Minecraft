/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.moulberry.flashback.editor.ui.ReplayUI
 *  foundry.veil.api.client.render.CameraMatrices
 *  foundry.veil.api.client.render.CullFrustum
 *  foundry.veil.api.client.render.VeilRenderSystem
 *  foundry.veil.api.client.render.framebuffer.AdvancedFbo
 *  foundry.veil.api.compat.SodiumCompat
 *  foundry.veil.ext.RenderTargetExtension
 *  foundry.veil.impl.client.render.perspective.IrisPipelineAccess
 *  foundry.veil.impl.client.render.perspective.LevelPerspectiveCamera
 *  foundry.veil.mixin.perspective.accessor.GameRendererAccessor
 *  foundry.veil.mixin.perspective.accessor.LevelRendererAccessor
 *  net.minecraft.class_1297
 *  net.minecraft.class_1937
 *  net.minecraft.class_239
 *  net.minecraft.class_243
 *  net.minecraft.class_310
 *  net.minecraft.class_3695
 *  net.minecraft.class_4184
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597$class_4598
 *  net.minecraft.class_4604
 *  net.minecraft.class_6854
 *  net.minecraft.class_757
 *  net.minecraft.class_761
 *  net.minecraft.class_9779
 *  org.jetbrains.annotations.ApiStatus$Internal
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Matrix4f
 *  org.joml.Matrix4fStack
 *  org.joml.Matrix4fc
 *  org.joml.Quaternionf
 *  org.joml.Quaternionfc
 *  org.joml.Vector3dc
 *  org.joml.Vector3f
 */
package com.sp.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.moulberry.flashback.editor.ui.ReplayUI;
import foundry.veil.api.client.render.CameraMatrices;
import foundry.veil.api.client.render.CullFrustum;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.framebuffer.AdvancedFbo;
import foundry.veil.api.compat.SodiumCompat;
import foundry.veil.ext.RenderTargetExtension;
import foundry.veil.impl.client.render.perspective.IrisPipelineAccess;
import foundry.veil.impl.client.render.perspective.LevelPerspectiveCamera;
import foundry.veil.mixin.perspective.accessor.GameRendererAccessor;
import foundry.veil.mixin.perspective.accessor.LevelRendererAccessor;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.class_1297;
import net.minecraft.class_1937;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3695;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_4604;
import net.minecraft.class_6854;
import net.minecraft.class_757;
import net.minecraft.class_761;
import net.minecraft.class_9779;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3dc;
import org.joml.Vector3f;

public class PerspectiveRenderer {
    private static final LevelPerspectiveCamera CAMERA = new LevelPerspectiveCamera();
    private static final Matrix4f TRANSFORM = new Matrix4f();
    private static final CameraMatrices BACKUP_CAMERA_MATRICES = new CameraMatrices();
    private static final AtomicInteger ID = new AtomicInteger();
    private static final Matrix4f BACKUP_PROJECTION = new Matrix4f();
    private static final Vector3f BACKUP_LIGHT0_POSITION = new Vector3f();
    private static final Vector3f BACKUP_LIGHT1_POSITION = new Vector3f();
    private static final Matrix4f BACKUP_FLASHBACK_PROJECTION = new Matrix4f();
    private static final Quaternionf BACKUP_FLASHBACK_CAMERA = new Quaternionf();
    private static boolean renderingPerspective = false;

    private PerspectiveRenderer() {
    }

    public static AdvancedFbo render(AdvancedFbo framebuffer, Matrix4fc modelView, Matrix4fc projection, Vector3dc cameraPosition, Quaternionfc cameraOrientation, float renderDistance, class_9779 deltaTracker, boolean drawLights) {
        return PerspectiveRenderer.render(framebuffer, class_310.method_1551().field_1719, modelView, projection, cameraPosition, cameraOrientation, renderDistance, deltaTracker, drawLights);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static AdvancedFbo render(AdvancedFbo framebuffer, @Nullable class_1297 cameraEntity, Matrix4fc modelView, Matrix4fc projection, Vector3dc cameraPosition, Quaternionfc cameraOrientation, float renderDistance, class_9779 deltaTracker, boolean drawLights) {
        Object backupTaskLists;
        Object backupRenderLists;
        if (renderingPerspective) {
            return framebuffer;
        }
        class_4597.class_4598 bufferSource = class_310.method_1551().method_22940().method_23000();
        bufferSource.method_22993();
        class_310 minecraft = class_310.method_1551();
        class_757 gameRenderer = minecraft.field_1773;
        class_761 levelRenderer = minecraft.field_1769;
        LevelRendererAccessor levelRendererAccessor = (LevelRendererAccessor)levelRenderer;
        GameRendererAccessor accessor = (GameRendererAccessor)gameRenderer;
        RenderTargetExtension renderTargetExtension = (RenderTargetExtension)minecraft.method_1522();
        class_4587 poseStack = new class_4587();
        CAMERA.setup(cameraPosition, cameraEntity, minecraft.field_1687, cameraOrientation, renderDistance);
        poseStack.method_34425(TRANSFORM.set(modelView));
        poseStack.method_22907(CAMERA.method_23767());
        float backupRenderDistance = gameRenderer.method_3193();
        accessor.setRenderDistance(renderDistance * 16.0f);
        float backupFogStart = RenderSystem.getShaderFogStart();
        float backupFogEnd = RenderSystem.getShaderFogEnd();
        class_6854 backupFogShape = RenderSystem.getShaderFogShape();
        BACKUP_FLASHBACK_PROJECTION.set((Matrix4fc)ReplayUI.lastProjectionMatrix);
        BACKUP_FLASHBACK_CAMERA.set((Quaternionfc)ReplayUI.lastViewQuaternion);
        Object backupPipeline = IrisPipelineAccess.getPipeline((class_761)levelRenderer);
        if (SodiumCompat.isLoaded()) {
            backupRenderLists = SodiumCompat.INSTANCE.getSortedRenderLists();
            backupTaskLists = SodiumCompat.INSTANCE.getTaskLists();
            ID.getAndIncrement();
        } else {
            backupRenderLists = null;
            backupTaskLists = null;
        }
        BACKUP_PROJECTION.set((Matrix4fc)RenderSystem.getProjectionMatrix());
        gameRenderer.method_22709(TRANSFORM.set(projection));
        BACKUP_LIGHT0_POSITION.set(VeilRenderSystem.getLight0Direction());
        BACKUP_LIGHT1_POSITION.set(VeilRenderSystem.getLight1Direction());
        Matrix4fStack matrix4fstack = RenderSystem.getModelViewStack();
        matrix4fstack.pushMatrix();
        matrix4fstack.identity();
        RenderSystem.applyModelViewMatrix();
        class_239 backupHitResult = minecraft.field_1765;
        class_1297 backupCrosshairPickEntity = minecraft.field_1692;
        renderingPerspective = true;
        AdvancedFbo drawFbo = VeilRenderSystem.renderer().getDynamicBufferManger().getDynamicFbo(framebuffer);
        drawFbo.bind(true);
        renderTargetExtension.veil$setWrapper(drawFbo);
        class_4604 backupFrustum = levelRendererAccessor.getCullingFrustum();
        CameraMatrices matrices = VeilRenderSystem.renderer().getCameraMatrices();
        matrices.backup(BACKUP_CAMERA_MATRICES);
        try {
            levelRenderer.method_32133(new class_243(cameraPosition.x(), cameraPosition.y(), cameraPosition.z()), poseStack.method_23760().method_23761(), TRANSFORM);
            levelRenderer.method_22710(deltaTracker, false, (class_4184)CAMERA, gameRenderer, gameRenderer.method_22974(), poseStack.method_23760().method_23761(), TRANSFORM);
            bufferSource.method_22993();
            levelRenderer.method_3254();
            if (drawLights) {
                class_3695 profiler = class_310.method_1551().method_16011();
                if (VeilRenderSystem.drawLights((class_3695)profiler, (CullFrustum)VeilRenderSystem.getCullingFrustum())) {
                    VeilRenderSystem.compositeLights((class_3695)profiler);
                } else {
                    AdvancedFbo.unbind();
                }
            }
        }
        finally {
            matrices.restore(BACKUP_CAMERA_MATRICES);
            levelRendererAccessor.setCullingFrustum(backupFrustum);
            renderTargetExtension.veil$setWrapper(null);
            AdvancedFbo.unbind();
            renderingPerspective = false;
            minecraft.field_1692 = backupCrosshairPickEntity;
            minecraft.field_1765 = backupHitResult;
            matrix4fstack.popMatrix();
            RenderSystem.applyModelViewMatrix();
            RenderSystem.setShaderLights((Vector3f)BACKUP_LIGHT0_POSITION, (Vector3f)BACKUP_LIGHT1_POSITION);
            gameRenderer.method_22709(BACKUP_PROJECTION);
            IrisPipelineAccess.setPipeline((class_761)levelRenderer, (Object)backupPipeline);
            if (SodiumCompat.isLoaded()) {
                SodiumCompat.INSTANCE.setSortedRenderLists(backupRenderLists);
                SodiumCompat.INSTANCE.setTaskList(backupTaskLists);
            }
            RenderSystem.setShaderFogStart((float)backupFogStart);
            RenderSystem.setShaderFogEnd((float)backupFogEnd);
            RenderSystem.setShaderFogShape((class_6854)backupFogShape);
            ReplayUI.lastProjectionMatrix.set((Matrix4fc)BACKUP_FLASHBACK_PROJECTION);
            ReplayUI.lastViewQuaternion.set((Quaternionfc)BACKUP_FLASHBACK_CAMERA);
            accessor.setRenderDistance(backupRenderDistance);
            class_4184 mainCamera = gameRenderer.method_19418();
            minecraft.method_31975().method_3549((class_1937)minecraft.field_1687, mainCamera, minecraft.field_1765);
            minecraft.method_1561().method_3941((class_1937)minecraft.field_1687, mainCamera, minecraft.field_1692);
        }
        return drawFbo;
    }

    public static boolean isRenderingPerspective() {
        return renderingPerspective;
    }

    @ApiStatus.Internal
    public static int getID() {
        return ID.get();
    }
}

