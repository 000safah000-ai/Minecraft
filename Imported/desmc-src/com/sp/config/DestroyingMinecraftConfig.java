/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  eu.midnightdust.lib.config.MidnightConfig
 *  eu.midnightdust.lib.config.MidnightConfig$Entry
 */
package com.sp.config;

import com.sp.render.ShaderType;
import eu.midnightdust.lib.config.MidnightConfig;

public class DestroyingMinecraftConfig
extends MidnightConfig {
    public static final String SHADERS = "shaders";
    @MidnightConfig.Entry(category="shaders")
    public static ShaderType shaderType = ShaderType.CRACKS;
    @MidnightConfig.Entry(category="shaders")
    public static boolean enableDepthOfField = false;
    @MidnightConfig.Entry(category="shaders", isSlider=true, min=0.10000000149011612, max=3.0, precision=10)
    public static float blurStrength = 1.0f;
    @MidnightConfig.Entry(category="shaders", isSlider=true, min=0.10000000149011612, max=0.8999999761581421, precision=10)
    public static float autoFocusTime = 0.8f;
}

