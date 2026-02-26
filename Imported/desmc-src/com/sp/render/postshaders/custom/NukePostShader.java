/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_2960
 */
package com.sp.render.postshaders.custom;

import com.sp.DestroyingMinecraft;
import com.sp.render.postshaders.PostShader;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;

@Environment(value=EnvType.CLIENT)
public class NukePostShader
extends PostShader {
    public static final class_2960 NUKE_POST = DestroyingMinecraft.idOf("nuke");
    public static final class_2960 NUKE_SHADER = DestroyingMinecraft.idOf("nuke/nuke");

    public NukePostShader() {
        super(NUKE_POST, NUKE_SHADER);
    }
}

