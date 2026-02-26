/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_243
 *  net.minecraft.class_2960
 *  net.minecraft.class_310
 *  net.minecraft.class_3532
 *  net.minecraft.class_4184
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_5617$class_5618
 *  net.minecraft.class_765
 *  net.minecraft.class_776
 *  net.minecraft.class_897
 *  net.minecraft.class_898
 */
package com.sp.entity.client.renderer;

import com.sp.cca.custom.entity.SpinningBlockComponent;
import com.sp.entity.custom.SpinningBlockEntity;
import net.minecraft.class_243;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_5617;
import net.minecraft.class_765;
import net.minecraft.class_776;
import net.minecraft.class_897;
import net.minecraft.class_898;

public class SpinningBlockEntityRenderer
extends class_897<SpinningBlockEntity> {
    private final class_776 blockRenderManager;
    private final class_898 entityRenderDispatcher;

    public SpinningBlockEntityRenderer(class_5617.class_5618 context) {
        super(context);
        this.blockRenderManager = context.method_43337();
        this.entityRenderDispatcher = context.method_32166();
    }

    public class_2960 getTexture(SpinningBlockEntity entity) {
        return null;
    }

    public void render(SpinningBlockEntity entity, float yaw, float tickDelta, class_4587 matrices, class_4597 vertexConsumers, int light) {
        SpinningBlockComponent component = entity.getComponent();
        double d = class_3532.method_16436((double)tickDelta, (double)entity.field_6038, (double)entity.method_23317());
        double e = class_3532.method_16436((double)tickDelta, (double)entity.field_5971, (double)entity.method_23318());
        double f = class_3532.method_16436((double)tickDelta, (double)entity.field_5989, (double)entity.method_23321());
        class_4184 camera = class_310.method_1551().field_1773.method_19418();
        class_243 cameraPos = camera.method_19326();
        matrices.method_22904(-(d - cameraPos.field_1352), -(e - cameraPos.field_1352), -(f - cameraPos.field_1352));
        class_243 entityPos = entity.method_30950(tickDelta);
        matrices.method_22904(entityPos.field_1352 - cameraPos.field_1352, entityPos.field_1351 - cameraPos.field_1352, entityPos.field_1350 - cameraPos.field_1352);
        component.getBlockType().render(this.blockRenderManager, this.entityRenderDispatcher, entity, yaw, tickDelta, matrices, vertexConsumers, class_765.method_23687((int)1, (int)15));
    }
}

