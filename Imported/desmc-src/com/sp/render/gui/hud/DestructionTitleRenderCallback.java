/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
 *  net.minecraft.class_1109
 *  net.minecraft.class_1113
 *  net.minecraft.class_2561
 *  net.minecraft.class_2960
 *  net.minecraft.class_310
 *  net.minecraft.class_332
 *  net.minecraft.class_3414
 *  net.minecraft.class_5250
 *  net.minecraft.class_5348
 *  net.minecraft.class_9779
 */
package com.sp.render.gui.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sp.DestroyingMinecraft;
import com.sp.destruction.DestructionType;
import com.sp.sounds.ModSounds;
import com.sp.sounds.instances.FadingSoundInstance;
import com.sp.util.keyframes.Keyframe;
import com.sp.util.keyframes.KeyframeAnimation;
import com.sp.util.timer.MsTimer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.class_1109;
import net.minecraft.class_1113;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_3414;
import net.minecraft.class_5250;
import net.minecraft.class_5348;
import net.minecraft.class_9779;

public class DestructionTitleRenderCallback
implements HudRenderCallback {
    private static final DestructionTitle ORBITAL_LASER = new DestructionTitle(DestroyingMinecraft.idOf("orbital_laser.png"), 1024, 134);
    private static final DestructionTitle PLANET = new DestructionTitle(DestroyingMinecraft.idOf("planet.png"), 1003, 256);
    private static final DestructionTitle SUPERNOVA = new DestructionTitle(DestroyingMinecraft.idOf("supernova.png"), 1024, 168);
    private static final DestructionTitle BLACK_HOLE = new DestructionTitle(DestroyingMinecraft.idOf("black_hole.png"), 1024, 171);
    public static DestructionTitleAnimation ORBITAL_LASER_ANIMATION;
    public static DestructionTitleAnimation PLANET_ANIMATION;
    public static DestructionTitleAnimation SUPERNOVA_ANIMATION;
    public static DestructionTitleAnimation BLACK_HOLE_ANIMATION;
    private static final MsTimer timer;
    private static DestructionTitleAnimation currentDestructionTitle;
    private static boolean renderTitle;
    private static boolean initAnimations;

    public static void setDestructionTitle(DestructionType type) {
        if (renderTitle) {
            return;
        }
        switch (type) {
            case ORBITAL_LASER: {
                DestructionTitleAnimation destructionTitleAnimation = ORBITAL_LASER_ANIMATION;
                break;
            }
            case PLANET: {
                DestructionTitleAnimation destructionTitleAnimation = PLANET_ANIMATION;
                break;
            }
            case SUPERNOVA: {
                DestructionTitleAnimation destructionTitleAnimation = SUPERNOVA_ANIMATION;
                break;
            }
            case BLACK_HOLE: {
                DestructionTitleAnimation destructionTitleAnimation = BLACK_HOLE_ANIMATION;
                break;
            }
            default: {
                DestructionTitleAnimation destructionTitleAnimation = currentDestructionTitle = null;
            }
        }
        if (currentDestructionTitle == null) {
            return;
        }
        timer.start();
        renderTitle = true;
    }

    public void onHudRender(class_332 drawContext, class_9779 renderTickCounter) {
        if (!initAnimations) {
            this.initializeAnimations(drawContext);
            initAnimations = true;
        }
        if (!renderTitle || currentDestructionTitle == null) {
            return;
        }
        RenderSystem.enableBlend();
        float time = (float)timer.getTime() / (float)currentDestructionTitle.duration();
        currentDestructionTitle.keyframeAnimation().updateKeyframeAnimation(time);
        if (!class_310.method_1551().method_1493()) {
            timer.resume();
        } else {
            timer.pause();
        }
        RenderSystem.disableBlend();
        if ((double)time >= 1.0) {
            timer.stop();
            currentDestructionTitle.keyframeAnimation().resetAnimation();
            renderTitle = false;
            currentDestructionTitle = null;
        }
    }

    private void initializeAnimations(class_332 drawContext) {
        class_310 client = class_310.method_1551();
        ORBITAL_LASER_ANIMATION = new DestructionTitleAnimation(new KeyframeAnimation.KeyframeAnimationBuilder(200, new Keyframe(0.0, () -> client.method_1483().method_4873((class_1113)class_1109.method_4757((class_3414)ModSounds.ORBITAL_LASER_INITIALIZE, (float)1.0f, (float)1.0f)), (globalTime, localTime) -> {
            class_5250 text = class_2561.method_43470((String)("Initializing . . . ".substring(0, (int)Math.min(localTime * 25.0, 18.0)) + "|"));
            drawContext.method_51448().method_22903();
            drawContext.method_51448().method_22905(2.0f, 2.0f, 2.0f);
            drawContext.method_51448().method_22904((double)drawContext.method_51421() / 4.0 - (double)client.field_1772.method_27525((class_5348)text) / 2.0, (double)drawContext.method_51443() * 0.05, 0.0);
            drawContext.method_51439(client.field_1772, (class_2561)text, 0, 0, -1, false);
            drawContext.method_51448().method_22909();
        }), new Keyframe((double)0.35f, (globalTime, localTime) -> this.renderText(drawContext, localTime))).build(), ORBITAL_LASER);
        PLANET_ANIMATION = new DestructionTitleAnimation(new KeyframeAnimation.KeyframeAnimationBuilder(200, new Keyframe(0.0, () -> client.method_1483().method_4873((class_1113)class_1109.method_4757((class_3414)ModSounds.PLANET_INITIALIZE, (float)1.0f, (float)1.0f)), (globalTime, localTime) -> {
            class_5250 text = class_2561.method_43470((String)("Initializing . . . ".substring(0, (int)Math.min(localTime * 25.0, 18.0)) + "|"));
            drawContext.method_51448().method_22903();
            drawContext.method_51448().method_22905(2.0f, 2.0f, 2.0f);
            drawContext.method_51448().method_22904((double)drawContext.method_51421() / 4.0 - (double)client.field_1772.method_27525((class_5348)text) / 2.0, (double)drawContext.method_51443() * 0.05, 0.0);
            drawContext.method_51439(client.field_1772, (class_2561)text, 0, 0, -1, false);
            drawContext.method_51448().method_22909();
        }), new Keyframe((double)0.31f, (globalTime, localTime) -> this.renderText(drawContext, localTime))).build(), PLANET);
        SUPERNOVA_ANIMATION = new DestructionTitleAnimation(new KeyframeAnimation.KeyframeAnimationBuilder(200, new Keyframe(0.0, () -> client.method_1483().method_4873((class_1113)class_1109.method_4757((class_3414)ModSounds.SUPERNOVA_INITIALIZE, (float)1.0f, (float)1.0f)), (globalTime, localTime) -> {
            class_5250 text = class_2561.method_43470((String)("Initializing . . . ".substring(0, (int)Math.min(localTime * 25.0, 18.0)) + "|"));
            drawContext.method_51448().method_22903();
            drawContext.method_51448().method_22905(2.0f, 2.0f, 2.0f);
            drawContext.method_51448().method_22904((double)drawContext.method_51421() / 4.0 - (double)client.field_1772.method_27525((class_5348)text) / 2.0, (double)drawContext.method_51443() * 0.05, 0.0);
            drawContext.method_51439(client.field_1772, (class_2561)text, 0, 0, -1, false);
            drawContext.method_51448().method_22909();
        }), new Keyframe((double)0.47f, (globalTime, localTime) -> this.renderText(drawContext, localTime))).build(), SUPERNOVA);
        BLACK_HOLE_ANIMATION = new DestructionTitleAnimation(new KeyframeAnimation.KeyframeAnimationBuilder(500, new Keyframe(0.0, () -> client.method_1483().method_4873((class_1113)FadingSoundInstance.ambient(ModSounds.BLACK_HOLE_INITIALIZE, 20, false, 0, 1.0f, 1.0f)), (globalTime, localTime) -> this.renderText(drawContext, localTime))).build(), BLACK_HOLE);
    }

    private void renderText(class_332 drawContext, double localTime) {
        float alpha = (float)(-Math.pow(2.0 * localTime - 1.0, 6.0)) + 1.0f;
        float height = 0.19f;
        float width = (float)currentDestructionTitle.title().width() * height / (float)currentDestructionTitle.title().height();
        drawContext.method_51448().method_22903();
        drawContext.method_51448().method_22905(width, height, 0.0f);
        drawContext.method_51448().method_22904((double)drawContext.method_51421() / (double)(2.0f * width) - 127.5, (double)drawContext.method_51443() * 0.2, 0.0);
        drawContext.method_51422(1.0f, 1.0f, 1.0f, alpha);
        drawContext.method_25302(currentDestructionTitle.title().texture(), 0, 0, 0, 0, 256, 255);
        drawContext.method_51448().method_22909();
        drawContext.method_51422(1.0f, 1.0f, 1.0f, 1.0f);
    }

    static {
        timer = new MsTimer();
    }

    public record DestructionTitleAnimation(KeyframeAnimation keyframeAnimation, DestructionTitle title, long duration) {
        public DestructionTitleAnimation(KeyframeAnimation keyframeAnimation, DestructionTitle title) {
            this(keyframeAnimation, title, 10000L);
        }
    }

    public record DestructionTitle(class_2960 texture, int width, int height) {
        public DestructionTitle(class_2960 texture, int width, int height) {
            this.texture = texture.method_45138("textures/gui/");
            this.width = width;
            this.height = height;
        }
    }
}

