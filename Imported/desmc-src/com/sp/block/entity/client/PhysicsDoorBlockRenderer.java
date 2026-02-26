/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_5614$class_5615
 *  net.minecraft.class_827
 */
package com.sp.block.entity.client;

import com.sp.block.entity.custom.PhysicsDoorBlockEntity;
import com.sp.util.RenderUtil;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_5614;
import net.minecraft.class_827;

public class PhysicsDoorBlockRenderer
implements class_827<PhysicsDoorBlockEntity> {
    public PhysicsDoorBlockRenderer(class_5614.class_5615 context) {
    }

    public void render(PhysicsDoorBlockEntity entity, float tickDelta, class_4587 matrices, class_4597 vertexConsumers, int light, int overlay) {
        if (entity.shouldShowSelection()) {
            boolean bl = entity.isSettingSelection();
            int[] colors = new int[]{bl ? 255 : 100, bl ? 100 : 255, 100, 100};
            matrices.method_46416((float)(-entity.method_11016().method_10263()), (float)(-entity.method_11016().method_10264()), (float)(-entity.method_11016().method_10260()));
            RenderUtil.drawBlocksFromCorners(matrices, vertexConsumers, null, entity.getCorner1(), entity.getCorner2(), colors[0], colors[1], colors[2], colors[3], true, false);
        }
    }
}

