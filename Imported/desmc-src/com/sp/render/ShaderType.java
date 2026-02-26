/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  net.minecraft.class_3542
 *  org.jetbrains.annotations.Nullable
 */
package com.sp.render;

import com.mojang.serialization.Codec;
import com.sp.destruction.DestructionType;
import com.sp.destruction.client.ClientDestructionEvents;
import com.sp.render.postshaders.PostShader;
import com.sp.render.postshaders.PostShaders;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.class_3542;
import org.jetbrains.annotations.Nullable;

public enum ShaderType implements class_3542
{
    NONE("none", false, false, false, false, null),
    NUKE("nuke", ClientDestructionEvents.NUKE_CLIENT.getPostShader()),
    CRACKS("orbital_laser", ClientDestructionEvents.CRACKS_CLIENT.getPostShader()),
    PLANET("planet", ClientDestructionEvents.PLANET_CLIENT.getPostShader()),
    SUPERNOVA("supernova", null),
    BLACK_HOLE("black_hole", true, false, true, true, ClientDestructionEvents.BLACK_HOLE_CLIENT.getPostShader());

    public static final Codec<ShaderType> CODEC;
    final String id;
    final List<PostShader> enabledShaders;

    private ShaderType(String id, PostShader ... postShaders) {
        this(id, true, true, true, true, postShaders);
    }

    private ShaderType(String id, boolean enableShadows, boolean enableSky, @Nullable boolean enableBloom, boolean enablePost, PostShader ... postShaders) {
        this.id = id;
        this.enabledShaders = new ArrayList<PostShader>();
        if (enableSky) {
            this.enabledShaders.add(ClientDestructionEvents.SUPERNOVA_CLIENT.getPostShader());
        }
        if (enableShadows) {
            this.enabledShaders.add(PostShaders.SHADOW);
        }
        if (postShaders != null) {
            this.enabledShaders.addAll(Arrays.stream(postShaders).toList());
        }
        if (enableBloom) {
            this.enabledShaders.add(PostShaders.BLOOM);
        }
        if (enablePost) {
            this.enabledShaders.add(PostShaders.POST);
        }
    }

    public List<PostShader> getEnabledShaders() {
        return this.enabledShaders;
    }

    public String method_15434() {
        return this.id;
    }

    public static ShaderType getFromString(String shader) {
        for (ShaderType type : ShaderType.values()) {
            if (!shader.equals(type.id)) continue;
            return type;
        }
        return NONE;
    }

    public static ShaderType getFromDestructionType(DestructionType type) {
        return switch (type) {
            default -> throw new MatchException(null, null);
            case DestructionType.NUKE -> NUKE;
            case DestructionType.ORBITAL_LASER -> CRACKS;
            case DestructionType.PLANET -> PLANET;
            case DestructionType.SUPERNOVA -> SUPERNOVA;
            case DestructionType.BLACK_HOLE -> BLACK_HOLE;
        };
    }

    static {
        CODEC = class_3542.method_28140(ShaderType::values);
    }
}

