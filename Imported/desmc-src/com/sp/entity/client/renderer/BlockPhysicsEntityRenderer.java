/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1920
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_2374
 *  net.minecraft.class_2382
 *  net.minecraft.class_243
 *  net.minecraft.class_2680
 *  net.minecraft.class_2960
 *  net.minecraft.class_310
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_4608
 *  net.minecraft.class_4696
 *  net.minecraft.class_5617$class_5618
 *  net.minecraft.class_5819
 *  net.minecraft.class_776
 *  net.minecraft.class_897
 *  org.joml.Vector3f
 */
package com.sp.entity.client.renderer;

import com.sp.DestroyingMinecraftClient;
import com.sp.cca.InitializeComponents;
import com.sp.cca.custom.entity.PhysicsBlockComponent;
import com.sp.collision.BlockOBB;
import com.sp.entity.custom.BlockPhysicsEntity;
import com.sp.util.MathUtil;
import com.sp.util.RenderUtil;
import java.util.List;
import net.minecraft.class_1297;
import net.minecraft.class_1920;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2374;
import net.minecraft.class_2382;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_4696;
import net.minecraft.class_5617;
import net.minecraft.class_5819;
import net.minecraft.class_776;
import net.minecraft.class_897;
import org.joml.Vector3f;

public class BlockPhysicsEntityRenderer
extends class_897<BlockPhysicsEntity> {
    private final class_776 blockModelRenderer;

    public BlockPhysicsEntityRenderer(class_5617.class_5618 ctx) {
        super(ctx);
        this.blockModelRenderer = ctx.method_43337();
    }

    public class_2960 getTexture(BlockPhysicsEntity entity) {
        return null;
    }

    public void render(BlockPhysicsEntity entity, float yaw, float tickDelta, class_4587 matrices, class_4597 vertexConsumers, int light) {
        if (DestroyingMinecraftClient.shouldRenderDebug) {
            this.renderDebug(entity, matrices, vertexConsumers);
        }
        PhysicsBlockComponent component = (PhysicsBlockComponent)InitializeComponents.PHYSICS_BLOCK.get((Object)entity);
        class_1937 world = entity.method_37908();
        if (world == null) {
            return;
        }
        for (BlockPhysicsEntity.BlockData blockData : entity.getBlocks()) {
            matrices.method_22903();
            matrices.method_22907(component.getRotation());
            matrices.method_22904(-0.5, -0.5, -0.5);
            matrices.method_22904(blockData.offset.method_10216(), blockData.offset.method_10214(), blockData.offset.method_10215());
            class_2680 blockState = blockData.blockState;
            class_2338 entityPos = entity.method_24515();
            class_243 offsetPos = blockData.offset;
            Vector3f rotatedPos = component.getRotation().transform(offsetPos.method_46409());
            this.blockModelRenderer.method_3350().method_3374((class_1920)world, this.blockModelRenderer.method_3349(blockState), blockState, entityPos.method_10081((class_2382)class_2338.method_49638((class_2374)MathUtil.toVec3d(rotatedPos))), matrices, vertexConsumers.getBuffer(class_4696.method_29359((class_2680)blockState)), false, class_5819.method_43047(), 1L, class_4608.field_21444);
            matrices.method_22909();
        }
        super.method_3936((class_1297)entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    private void renderDebug(BlockPhysicsEntity entity, class_4587 matrices, class_4597 vertexConsumers) {
        List<class_243> aabbCorners = BlockOBB.getAABBCorners(class_310.method_1551().field_1724.method_5829());
        for (int i = 0; i < aabbCorners.size(); ++i) {
            class_243 class_2432 = aabbCorners.get(i);
        }
        for (BlockPhysicsEntity.BlockData block : entity.getBlocks()) {
            BlockOBB obb = new BlockOBB(entity.component.getRotation(), block);
            for (class_243 globalCorner : obb.getGlobalCorners(entity)) {
                boolean collides = obb.collidesWith(class_310.method_1551().field_1724.method_5829(), entity);
                RenderUtil.drawEntityBox(matrices, vertexConsumers, globalCorner.method_1031(0.5, 0.5, 0.5), 0.2, entity, collides ? 255 : 0, collides ? 0 : 255, 0, 255);
            }
        }
    }
}

