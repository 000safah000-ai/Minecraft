/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  foundry.veil.api.client.render.CameraMatrices
 *  foundry.veil.api.client.render.VeilRenderSystem
 *  org.joml.Matrix4f
 *  org.joml.Matrix4fc
 *  org.joml.Vector3f
 *  org.joml.Vector3fc
 */
package com.sp.render;

import foundry.veil.api.client.render.CameraMatrices;
import foundry.veil.api.client.render.VeilRenderSystem;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class PrevUniforms {
    private static Matrix4f prevProjMat;
    private static Matrix4f prevModelViewMat;
    private static Vector3f prevCameraPos;
    private static boolean init;

    public static void update() {
        CameraMatrices matrices = VeilRenderSystem.renderer().getCameraMatrices();
        prevProjMat = new Matrix4f((Matrix4fc)matrices.getProjectionMatrix());
        prevModelViewMat = new Matrix4f((Matrix4fc)matrices.getViewMatrix());
        prevCameraPos = new Vector3f((Vector3fc)matrices.getCameraPosition());
        init = true;
    }

    public static boolean isInitialized() {
        return init;
    }

    public static Matrix4f getPrevProjMat() {
        return prevProjMat;
    }

    public static Matrix4f getPrevModelViewMat() {
        return prevModelViewMat;
    }

    public static Vector3f getPrevCameraPos() {
        return prevCameraPos;
    }

    static {
        init = false;
    }
}

