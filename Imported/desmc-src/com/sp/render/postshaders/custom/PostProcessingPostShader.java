/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  foundry.veil.api.client.render.post.PostPipeline$Context
 *  foundry.veil.api.client.render.shader.program.ShaderProgram
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_1297
 *  net.minecraft.class_1675
 *  net.minecraft.class_1937
 *  net.minecraft.class_238
 *  net.minecraft.class_243
 *  net.minecraft.class_2960
 *  net.minecraft.class_310
 *  net.minecraft.class_3959
 *  net.minecraft.class_3959$class_242
 *  net.minecraft.class_3959$class_3960
 *  net.minecraft.class_3965
 *  net.minecraft.class_3966
 */
package com.sp.render.postshaders.custom;

import com.sp.DestroyingMinecraft;
import com.sp.cca.InitializeComponents;
import com.sp.cca.custom.entity.PlayerComponent;
import com.sp.config.DestroyingMinecraftConfig;
import com.sp.render.BlackScreenManager;
import com.sp.render.postshaders.PostShader;
import com.sp.util.BetterUniforms;
import com.sp.util.MathUtil;
import foundry.veil.api.client.render.post.PostPipeline;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_1675;
import net.minecraft.class_1937;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_3966;

@Environment(value=EnvType.CLIENT)
public class PostProcessingPostShader
extends PostShader {
    public static final class_2960 POST = DestroyingMinecraft.idOf("post");
    public static final class_2960 SHADER = DestroyingMinecraft.idOf("post/post");
    public static final class_2960[] BLUR_IDENTIFIERS = new class_2960[]{DestroyingMinecraft.idOf("bloom/blur/horizontal"), DestroyingMinecraft.idOf("bloom/blur/vertical")};
    float smoothDepth;

    public PostProcessingPostShader() {
        super(POST, SHADER);
    }

    @Override
    public void setUniforms(PostPipeline.Context context, float tickDelta, class_310 client, class_1937 clientWorld) {
        super.setUniforms(context, tickDelta, client, clientWorld);
        for (class_2960 identifier : BLUR_IDENTIFIERS) {
            ShaderProgram shaderProgram = context.getShader(identifier);
            if (shaderProgram == null) continue;
            BetterUniforms.setFloat(shaderProgram, "blurStrength", DestroyingMinecraftConfig.blurStrength);
            BetterUniforms.setFloat(shaderProgram, "xLimit", DestroyingMinecraftConfig.enableDepthOfField ? 1.0f : -0.1f);
        }
    }

    @Override
    public void setUniformsForShader(ShaderProgram shaderProgram, float tickDelta, class_310 client, class_1937 clientWorld) {
        if (client.field_1724 == null) {
            return;
        }
        PlayerComponent component = (PlayerComponent)InitializeComponents.PLAYERS.get((Object)client.field_1724);
        float farPlane = 100.0f;
        class_243 vec3d = client.field_1724.method_5836(tickDelta);
        class_243 vec3d2 = client.field_1724.method_5828(tickDelta).method_1029();
        class_243 vec3d3 = vec3d.method_1031(vec3d2.field_1352 * (double)farPlane, vec3d2.field_1351 * (double)farPlane, vec3d2.field_1350 * (double)farPlane);
        class_3965 hitResult = client.field_1724.method_37908().method_17742(new class_3959(vec3d, vec3d3, class_3959.class_3960.field_17559, class_3959.class_242.field_1347, (class_1297)client.field_1724));
        class_238 box = client.field_1724.method_5829().method_18804(vec3d2).method_1014((double)farPlane);
        class_3966 entityHitResult = class_1675.method_18075((class_1297)client.field_1724, (class_243)vec3d, (class_243)vec3d3, (class_238)box, entity -> true, (double)farPlane);
        class_243 closestDistance = entityHitResult == null ? hitResult.method_17784() : (entityHitResult.method_17784().method_1033() > hitResult.method_17784().method_1033() ? hitResult.method_17784() : entityHitResult.method_17784());
        float depth = (float)client.method_1560().method_33571().method_1022(closestDistance) / farPlane;
        this.smoothDepth = MathUtil.Lerp(this.smoothDepth, depth, DestroyingMinecraftConfig.autoFocusTime, class_310.method_1551().method_60646().method_60636());
        BetterUniforms.setFloat(shaderProgram, "centerDepth", this.smoothDepth);
        BetterUniforms.setInt(shaderProgram, "enabledDepthOfField", DestroyingMinecraftConfig.enableDepthOfField ? 1 : 0);
        BetterUniforms.setInt(shaderProgram, "enabledBlackScreen", BlackScreenManager.isBlackScreen() || component.isInWaitingRoom() ? 1 : 0);
        BetterUniforms.setFloat(shaderProgram, "glitchTime", Math.min((float)component.getGlitchTime() / 100.0f, 1.0f));
    }
}

