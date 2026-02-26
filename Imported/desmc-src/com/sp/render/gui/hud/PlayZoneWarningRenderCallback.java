/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
 *  net.minecraft.class_1109
 *  net.minecraft.class_1113
 *  net.minecraft.class_1144
 *  net.minecraft.class_2960
 *  net.minecraft.class_310
 *  net.minecraft.class_327
 *  net.minecraft.class_332
 *  net.minecraft.class_3414
 *  net.minecraft.class_746
 *  net.minecraft.class_9779
 */
package com.sp.render.gui.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sp.DestroyingMinecraft;
import com.sp.cca.InitializeComponents;
import com.sp.cca.custom.entity.PlayerComponent;
import com.sp.sounds.ModSounds;
import com.sp.util.RenderUtil;
import java.util.Objects;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.class_1109;
import net.minecraft.class_1113;
import net.minecraft.class_1144;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_327;
import net.minecraft.class_332;
import net.minecraft.class_3414;
import net.minecraft.class_746;
import net.minecraft.class_9779;

public class PlayZoneWarningRenderCallback
implements HudRenderCallback {
    private static final class_2960 warningImage = DestroyingMinecraft.idOf("textures/gui/warning.png");
    private static long startTime;
    private static class_1109 countdown;

    public void onHudRender(class_332 drawContext, class_9779 tickCounter) {
        class_310 client = class_310.method_1551();
        class_746 player = client.field_1724;
        class_1144 soundManager = client.method_1483();
        if (player == null) {
            return;
        }
        PlayerComponent component = (PlayerComponent)InitializeComponents.PLAYERS.get((Object)player);
        if (component.isInsideAPlayZone()) {
            startTime = 0L;
            if (soundManager.method_4877((class_1113)countdown)) {
                soundManager.method_4870((class_1113)countdown);
            }
            return;
        }
        if (startTime == 0L) {
            countdown = class_1109.method_24877((class_3414)ModSounds.COUNT_DOWN, (float)1.0f, (float)0.5f);
            soundManager.method_4873((class_1113)countdown);
            startTime = System.currentTimeMillis();
        }
        RenderSystem.enableBlend();
        float alphaFade = Math.min((float)(System.currentTimeMillis() - startTime) / 200.0f, 1.0f);
        float alpha = (float)(Math.sin(RenderSystem.getShaderGameTime() * 8000.0f) * 0.5 + 0.5) * 0.5f + 0.4f;
        drawContext.method_25294(0, 0, drawContext.method_51421(), drawContext.method_51443(), RenderUtil.getArgb((int)(alphaFade * 100.0f), 255, 50, 50));
        RenderSystem.enableBlend();
        drawContext.method_51422(1.0f, 1.0f, 1.0f, alpha - (1.0f - alphaFade));
        drawContext.method_51448().method_22903();
        drawContext.method_51448().method_46416((float)drawContext.method_51421() / 2.0f, (float)drawContext.method_51443() / 2.0f, 0.0f);
        drawContext.method_51448().method_22905(0.5f, 0.5f, 0.5f);
        drawContext.method_25302(warningImage, -128, -256, 0, 0, 256, 255);
        drawContext.method_51448().method_22905(5.0f, 5.0f, 5.0f);
        class_327 class_3272 = client.field_1772;
        Objects.requireNonNull(client.field_1772);
        drawContext.method_25300(class_3272, "Return to the Play Zone", 0, 9 / 2, -1);
        long timeLeft = Math.max(component.getDeathTime() - System.currentTimeMillis(), 0L);
        int seconds = Math.max((int)Math.floor((double)timeLeft / 1000.0), 0);
        long milliSeconds = timeLeft % 1000L;
        Object milliSecondsString = milliSeconds > 100L ? String.valueOf(milliSeconds) : (milliSeconds > 10L ? "0" + milliSeconds : "00" + milliSeconds);
        class_327 class_3273 = client.field_1772;
        String string = "00:0" + seconds + ":" + (String)milliSecondsString;
        Objects.requireNonNull(client.field_1772);
        drawContext.method_25300(class_3273, string, 0, 9 * 2, -1);
        drawContext.method_51448().method_22909();
        drawContext.method_51422(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableBlend();
    }
}

