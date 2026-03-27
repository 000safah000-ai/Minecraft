package com.jujutsuminecraft.client.render;

import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.minecraft.util.Identifier;

public class LaserSatinPostProcess {
    public static ManagedShaderEffect LASER_VIGNETTE_BLUR;

    public static void init() {
        LASER_VIGNETTE_BLUR = ShaderEffectManager.getInstance().manage(
            new Identifier("jujutsuminecraft", "shaders/post/laser_vignette_blur.json")
        );
    }
}