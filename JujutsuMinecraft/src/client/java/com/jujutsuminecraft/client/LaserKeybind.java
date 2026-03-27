package com.jujutsuminecraft.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class LaserKeybind {
    public static KeyBinding laserKey;
    public static boolean isLaserActive = false;

    public static void register() {
        laserKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.jujutsuminecraft.laser",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            "category.jujutsuminecraft"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (laserKey.wasPressed()) {
                isLaserActive = !isLaserActive;
                // TODO: Send packet to server for multiplayer sync
            }
        });
    }
}