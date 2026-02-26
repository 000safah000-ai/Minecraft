/*
 * Decompiled with CFR 0.152.
 */
package com.sp.render.postshaders;

import com.sp.DestroyingMinecraft;
import com.sp.render.postshaders.custom.BlackHolePostShader;
import com.sp.render.postshaders.custom.BloomPostShader;
import com.sp.render.postshaders.custom.CracksPostShader;
import com.sp.render.postshaders.custom.NukePostShader;
import com.sp.render.postshaders.custom.PlanetPostShader;
import com.sp.render.postshaders.custom.PostProcessingPostShader;
import com.sp.render.postshaders.custom.ShadowPostShader;
import com.sp.render.postshaders.custom.SupernovaPostShader;

public class PostShaders {
    public static final NukePostShader NUKE = new NukePostShader();
    public static final CracksPostShader CRACKS = new CracksPostShader();
    public static final PlanetPostShader PLANET = new PlanetPostShader();
    public static final SupernovaPostShader SUPERNOVA = new SupernovaPostShader();
    public static final BlackHolePostShader BLACK_HOLE = new BlackHolePostShader();
    public static final BloomPostShader BLOOM = new BloomPostShader();
    public static final PostProcessingPostShader POST = new PostProcessingPostShader();
    public static final ShadowPostShader SHADOW = new ShadowPostShader();

    public static void registerPostShaders() {
        DestroyingMinecraft.LOGGER.info("Registering client post shaders");
    }
}

