package com.jujutsushenanigans;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JujutsuPreLaunch implements PreLaunchEntrypoint {
    private static final Logger LOGGER = LoggerFactory.getLogger("jujutsushenanigans-prelaunch");

    @Override
    public void onPreLaunch() {
        LOGGER.info("Applying ARM/Mali GPU compatibility properties...");
        
        // Disable Veil ImGui (ARM compatibility) - just in case the user forgets
        System.setProperty("veil.imgui.disable", "true");
        
        // Force OpenGL ES / disable Zink Vulkan for compatibility on Mali-G52
        System.setProperty("libgl.glEs", "true");
        System.setProperty("libgl.alwaysSoftware", "false");
        
        // Veil specific fallbacks for OpenGL
        System.setProperty("veil.backend", "opengl");
        System.setProperty("veil.renderer", "opengl");
    }
}
