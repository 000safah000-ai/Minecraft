/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_243
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_4608
 *  net.minecraft.class_776
 *  net.minecraft.class_7833
 *  net.minecraft.class_898
 *  org.joml.Quaternionf
 *  org.joml.Quaternionfc
 */
package com.sp.entity.client.renderer;

import com.sp.cca.custom.entity.SpinningBlockComponent;
import com.sp.entity.custom.SpinningBlockEntity;
import net.minecraft.class_243;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_776;
import net.minecraft.class_7833;
import net.minecraft.class_898;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;

public enum BlockType {
    SINGLE(BlockType::renderSingle);

    private final Render render;

    private BlockType(Render render) {
        this.render = render;
    }

    public void render(class_776 blockRenderManager, class_898 entityRenderDispatcher, SpinningBlockEntity entity, float yaw, float tickDelta, class_4587 matrices, class_4597 vertexConsumers, int light) {
        this.render.render(blockRenderManager, entityRenderDispatcher, entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    private static void renderSingle(class_776 blockRenderManager, class_898 entityRenderDispatcher, SpinningBlockEntity entity, float yaw, float tickDelta, class_4587 matrices, class_4597 vertexConsumers, int light) {
        SpinningBlockComponent component = entity.getComponent();
        float scale = component.getScale();
        float halfScale = scale / 2.0f;
        matrices.method_22907(new Quaternionf().set((Quaternionfc)class_7833.field_40714.rotationDegrees(component.getPitch(tickDelta))));
        matrices.method_22907(new Quaternionf().set((Quaternionfc)class_7833.field_40716.rotationDegrees(component.getYaw(tickDelta))));
        matrices.method_46416(-halfScale, -halfScale, -halfScale);
        matrices.method_22905(scale, scale, scale);
        blockRenderManager.method_3353(component.getBlockState(), matrices, vertexConsumers, light, class_4608.field_21444);
        matrices.method_22905(1.0f / scale, 1.0f / scale, 1.0f / scale);
        matrices.method_46416(halfScale, halfScale, halfScale);
    }

    private static class_243 startRenderingAnimal(class_898 entityRenderDispatcher, SpinningBlockEntity entity, float tickDelta, class_4587 matrices) {
        SpinningBlockComponent component = entity.getComponent();
        class_243 cameraPos = entityRenderDispatcher.field_4686.method_19326();
        class_243 pos = entity.method_19538().method_1020(cameraPos);
        matrices.method_22904(pos.field_1352, pos.field_1351, pos.field_1350);
        matrices.method_22904(0.0, 0.5, 0.0);
        matrices.method_22907(new Quaternionf().set((Quaternionfc)class_7833.field_40714.rotationDegrees(component.getPitch(tickDelta))));
        matrices.method_22907(new Quaternionf().set((Quaternionfc)class_7833.field_40716.rotationDegrees(component.getYaw(tickDelta))));
        matrices.method_22904(0.0, -0.5, 0.0);
        matrices.method_22904(-pos.field_1352, -pos.field_1351, -pos.field_1350);
        return cameraPos;
    }

    private static void renderDouble(class_776 blockRenderManager, class_898 entityRenderDispatcher, SpinningBlockEntity entity, float yaw, float tickDelta, class_4587 matrices, class_4597 vertexConsumers, int light) {
        SpinningBlockComponent component = entity.getComponent();
        float scale = component.getScale();
        float halfScale = scale / 2.0f;
        matrices.method_22907(new Quaternionf().set((Quaternionfc)class_7833.field_40714.rotationDegrees(component.getPitch(tickDelta))));
        matrices.method_22907(new Quaternionf().set((Quaternionfc)class_7833.field_40716.rotationDegrees(component.getYaw(tickDelta))));
        matrices.method_46416(-halfScale, -halfScale, -halfScale);
        matrices.method_22905(scale, scale, scale);
        blockRenderManager.method_3353(component.getBlockState(), matrices, vertexConsumers, light, class_4608.field_21444);
        matrices.method_22905(1.0f / scale, 1.0f / scale, 1.0f / scale);
        matrices.method_46416(halfScale, halfScale, halfScale);
        matrices.method_46416(scale, 0.0f, 0.0f);
        matrices.method_46416(-halfScale, -halfScale, -halfScale);
        matrices.method_22905(scale, scale, scale);
        blockRenderManager.method_3353(component.getBlockState(), matrices, vertexConsumers, light, class_4608.field_21444);
        matrices.method_22905(1.0f / scale, 1.0f / scale, 1.0f / scale);
        matrices.method_46416(halfScale, halfScale, halfScale);
        matrices.method_46416(-scale, 0.0f, 0.0f);
    }

    private static void renderTriple(class_776 blockRenderManager, class_898 entityRenderDispatcher, SpinningBlockEntity entity, float yaw, float tickDelta, class_4587 matrices, class_4597 vertexConsumers, int light) {
        SpinningBlockComponent component = entity.getComponent();
        float scale = component.getScale();
        float halfScale = scale / 2.0f;
        matrices.method_22907(new Quaternionf().set((Quaternionfc)class_7833.field_40714.rotationDegrees(component.getPitch(tickDelta))));
        matrices.method_22907(new Quaternionf().set((Quaternionfc)class_7833.field_40716.rotationDegrees(component.getYaw(tickDelta))));
        matrices.method_46416(-halfScale, -halfScale, -halfScale);
        matrices.method_22905(scale, scale, scale);
        blockRenderManager.method_3353(component.getBlockState(), matrices, vertexConsumers, light, class_4608.field_21444);
        matrices.method_22905(1.0f / scale, 1.0f / scale, 1.0f / scale);
        matrices.method_46416(halfScale, halfScale, halfScale);
        matrices.method_46416(scale, 0.0f, 0.0f);
        matrices.method_46416(-halfScale, -halfScale, -halfScale);
        matrices.method_22905(scale, scale, scale);
        blockRenderManager.method_3353(component.getBlockState(), matrices, vertexConsumers, light, class_4608.field_21444);
        matrices.method_22905(1.0f / scale, 1.0f / scale, 1.0f / scale);
        matrices.method_46416(halfScale, halfScale, halfScale);
        matrices.method_46416(0.0f, 0.0f, scale);
        matrices.method_46416(-halfScale, -halfScale, -halfScale);
        matrices.method_22905(scale, scale, scale);
        blockRenderManager.method_3353(component.getBlockState(), matrices, vertexConsumers, light, class_4608.field_21444);
        matrices.method_22905(1.0f / scale, 1.0f / scale, 1.0f / scale);
        matrices.method_46416(halfScale, halfScale, halfScale);
        matrices.method_46416(-scale, 0.0f, -scale);
    }

    private static interface Render {
        public void render(class_776 var1, class_898 var2, SpinningBlockEntity var3, float var4, float var5, class_4587 var6, class_4597 var7, int var8);
    }
}

