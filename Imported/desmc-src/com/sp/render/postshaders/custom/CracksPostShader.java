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
public class CracksPostShader
extends PostShader {
    public static final class_2960 CRACKS_HOLE_POST = DestroyingMinecraft.idOf("cracks");
    public static final class_2960 CRACKS_HOLE_SHADER = DestroyingMinecraft.idOf("cracks/cracks");

    public CracksPostShader() {
        super(CRACKS_HOLE_POST, CRACKS_HOLE_SHADER);
    }
}

