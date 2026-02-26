/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  foundry.veil.api.client.render.VeilLevelPerspectiveRenderer
 *  net.minecraft.class_1297
 *  net.minecraft.class_243
 *  net.minecraft.class_2960
 *  net.minecraft.class_310
 *  net.minecraft.class_4184
 *  net.minecraft.class_4587
 *  net.minecraft.class_4587$class_4665
 *  net.minecraft.class_4588
 *  net.minecraft.class_4597
 *  net.minecraft.class_5617$class_5618
 *  net.minecraft.class_897
 *  org.joml.Quaternionf
 *  org.joml.Vector3f
 *  org.joml.Vector3fc
 */
package com.sp.entity.client.renderer;

import com.sp.entity.custom.MeteorEntity;
import com.sp.mixininterfaces.BufferBuilderPosition;
import com.sp.render.CustomRenderLayersAndVertexFormats;
import foundry.veil.api.client.render.VeilLevelPerspectiveRenderer;
import net.minecraft.class_1297;
import net.minecraft.class_243;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_5617;
import net.minecraft.class_897;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class MeteorEntityRenderer
extends class_897<MeteorEntity> {
    public MeteorEntityRenderer(class_5617.class_5618 context) {
        super(context);
    }

    public class_2960 getTexture(MeteorEntity entity) {
        return null;
    }

    public void render(MeteorEntity entity, float yaw, float tickDelta, class_4587 matrices, class_4597 vertexConsumers, int light) {
        if (VeilLevelPerspectiveRenderer.isRenderingPerspective()) {
            return;
        }
        class_4588 bufferBuilder = vertexConsumers.getBuffer(CustomRenderLayersAndVertexFormats.METEOR);
        float pMinX = 0.0f;
        float pMinY = 0.0f;
        float pMinZ = 0.0f;
        float pMaxX = 1.0f;
        float pMaxY = 1.0f;
        float pMaxZ = 1.0f;
        float scale = 18.0f;
        matrices.method_22903();
        class_4184 camera = class_310.method_1551().field_1773.method_19418();
        matrices.method_22907(new Quaternionf().rotateTo((Vector3fc)new Vector3f(0.0f, 0.0f, 1.0f), (Vector3fc)camera.method_19326().method_46409().sub((Vector3fc)entity.method_19538().method_46409()).normalize()));
        matrices.method_46416(0.0f, 0.5f, 3.0f);
        matrices.method_22905(scale, scale, scale);
        matrices.method_46416(-0.5f, -0.5f, -1.0f);
        class_4587.class_4665 entry = matrices.method_23760();
        class_243 entityPos = entity.method_30950(tickDelta).method_1031(0.0, 0.5, 0.0);
        float id = entity.method_5628();
        bufferBuilder.method_56824(entry, pMinX, pMaxY, pMaxZ).method_22915(1.0f, 1.0f, 1.0f, Math.min((float)entity.field_6012 / 30.0f, 1.0f));
        this.putPosition(bufferBuilder, entityPos, id);
        bufferBuilder.method_56824(entry, pMinX, pMinY, pMaxZ).method_22915(1.0f, 1.0f, 1.0f, Math.min((float)entity.field_6012 / 30.0f, 1.0f));
        this.putPosition(bufferBuilder, entityPos, id);
        bufferBuilder.method_56824(entry, pMaxX, pMinY, pMaxZ).method_22915(1.0f, 1.0f, 1.0f, Math.min((float)entity.field_6012 / 30.0f, 1.0f));
        this.putPosition(bufferBuilder, entityPos, id);
        bufferBuilder.method_56824(entry, pMaxX, pMaxY, pMaxZ).method_22915(1.0f, 1.0f, 1.0f, Math.min((float)entity.field_6012 / 30.0f, 1.0f));
        this.putPosition(bufferBuilder, entityPos, id);
        matrices.method_22909();
        super.method_3936((class_1297)entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    private void putPosition(class_4588 bufferBuilder, class_243 entityPos, float value) {
        if (bufferBuilder instanceof BufferBuilderPosition) {
            BufferBuilderPosition builderPosition = (BufferBuilderPosition)bufferBuilder;
            builderPosition.setPosition((float)entityPos.field_1352, (float)entityPos.field_1351, (float)entityPos.field_1350, value);
        }
    }
}

