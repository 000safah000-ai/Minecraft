/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  foundry.veil.api.client.render.VeilRenderSystem
 *  foundry.veil.api.client.render.shader.program.ShaderProgram
 *  net.minecraft.class_1297
 *  net.minecraft.class_1921
 *  net.minecraft.class_2960
 *  net.minecraft.class_310
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_4604
 *  net.minecraft.class_4608
 *  net.minecraft.class_5617$class_5618
 *  net.minecraft.class_897
 *  org.joml.Matrix3f
 *  org.joml.Matrix3fc
 *  org.joml.Matrix4f
 *  org.joml.Matrix4fc
 *  org.joml.Quaternionf
 *  org.joml.Vector3f
 */
package com.sp.entity.client.renderer;

import com.sp.DestroyingMinecraft;
import com.sp.entity.client.model.StarPiercerModel;
import com.sp.entity.custom.StarPiercerEntity;
import com.sp.render.CustomRenderLayersAndVertexFormats;
import com.sp.render.ShadowMapRenderer;
import com.sp.util.BetterUniforms;
import com.sp.util.timer.MsTimer;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import java.util.Optional;
import net.minecraft.class_1297;
import net.minecraft.class_1921;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_4604;
import net.minecraft.class_4608;
import net.minecraft.class_5617;
import net.minecraft.class_897;
import org.joml.Matrix3f;
import org.joml.Matrix3fc;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class StarPiercerEntityRenderer
extends class_897<StarPiercerEntity> {
    private static final class_2960 TEXTURE = DestroyingMinecraft.idOf("textures/entity/starpiercer_texture.png");
    private static final class_2960 LIGHTS_TEXTURE = DestroyingMinecraft.idOf("textures/entity/starpiercer_lights_texture.png");
    private static final class_2960 STAR_PIERCER_SHADER = DestroyingMinecraft.idOf("star_piercer/star_piercer");
    private static final MsTimer bloomTimer = new MsTimer();
    private final StarPiercerModel model;
    private float speed = 0.0f;

    public StarPiercerEntityRenderer(class_5617.class_5618 ctx) {
        super(ctx);
        this.model = new StarPiercerModel(ctx.method_32167(StarPiercerModel.STAR_PIERCER_MODEL_LAYER));
    }

    public void render(StarPiercerEntity entity, float yaw, float tickDelta, class_4587 matrices, class_4597 vertexConsumers, int light) {
        ShaderProgram shader;
        matrices.method_22903();
        matrices.method_22907(new Quaternionf().rotateLocalZ((float)Math.toRadians(180.0)));
        Optional<Matrix4f> sunMat = ShadowMapRenderer.getShadowViewMat();
        if (sunMat.isPresent()) {
            Matrix3f matrix3f = new Matrix3f((Matrix4fc)sunMat.get().invert());
            Vector3f angles = new Vector3f(0.0f, 0.0f, 1.0f).mul((Matrix3fc)matrix3f).normalize();
            float pitch = (float)Math.atan2(angles.y, angles.x);
            float yaw2 = (float)Math.atan2(angles.z, angles.x);
            this.model.body.field_3654 = -pitch;
            this.model.starpiercer.field_3675 = yaw2 + (float)Math.toRadians(90.0);
        }
        if ((shader = VeilRenderSystem.setShader((class_2960)STAR_PIERCER_SHADER)) == null) {
            return;
        }
        float lastFrameDuration = class_310.method_1551().method_60646().method_60636();
        float bloomTime = 0.0f;
        if (!class_310.method_1551().method_1493()) {
            bloomTimer.resume();
            if (entity.isStartingUp()) {
                bloomTimer.start();
                bloomTime = (float)(bloomTimer.getTime() - 34000L) / 10000.0f;
                this.speed += 2.02222E-4f * lastFrameDuration;
                this.speed = Math.min(this.speed, 2.0f);
                this.model.barrel.field_3674 += this.speed * lastFrameDuration;
            } else if (entity.isPoweringDown()) {
                bloomTime = 1.0f - (float)(bloomTimer.getTime() - 60000L) / 20000.0f;
                this.speed -= 5.2222E-5f * lastFrameDuration;
                this.speed = Math.max(this.speed, 0.0f);
                this.model.barrel.field_3674 += this.speed * lastFrameDuration;
            } else {
                this.speed = 0.0f;
                bloomTimer.stop();
            }
        } else {
            bloomTimer.pause();
        }
        BetterUniforms.setFloat(shader, "bloomTime", Math.clamp(bloomTime, 0.0f, 1.0f));
        class_1921 renderLayer = CustomRenderLayersAndVertexFormats.ENTITY_BLOOM.apply(TEXTURE, LIGHTS_TEXTURE);
        this.model.method_60879(matrices, vertexConsumers.getBuffer(renderLayer), light, class_4608.field_21444);
        super.method_3936((class_1297)entity, yaw, tickDelta, matrices, vertexConsumers, light);
        matrices.method_22909();
    }

    public boolean shouldRender(StarPiercerEntity entity, class_4604 frustum, double x, double y, double z) {
        return true;
    }

    public class_2960 getTexture(StarPiercerEntity entity) {
        return TEXTURE;
    }
}

