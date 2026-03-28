package com.jujutsuminecraft;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class JujutsuMinecraftPreLaunch implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        // Disable Veil's ImGui loading unconditionally before any mod initialization occurs
        // This prevents native library loading crashes on architecture/environments like 
        // ARM64 / ZalithLauncher.
        System.setProperty("veil.disableImgui", "true");
        System.out.println("[JujutsuMinecraft] Set veil.disableImgui to true in PreLaunch");
    }
}
