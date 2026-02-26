/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  net.minecraft.class_1011
 *  net.minecraft.class_2561
 *  net.minecraft.class_310
 *  net.minecraft.class_327
 *  net.minecraft.class_332
 *  net.minecraft.class_339
 *  net.minecraft.class_3532
 *  net.minecraft.class_5253$class_5254
 *  net.minecraft.class_6382
 *  org.joml.Vector3f
 */
package com.sp.render.gui.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sp.render.gui.HSVColorTextureManager;
import java.util.Objects;
import net.minecraft.class_1011;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_327;
import net.minecraft.class_332;
import net.minecraft.class_339;
import net.minecraft.class_3532;
import net.minecraft.class_5253;
import net.minecraft.class_6382;
import org.joml.Vector3f;

public class HSVColorWidget
extends class_339 {
    private final float scale;
    private boolean pickHue;
    private float hue;
    private float saturation;
    private float value;
    private double colorPickerX;
    private double colorPickerY;
    private final Vector3f outputColor;
    private final SetColor setColorFunction;

    public HSVColorWidget(int x, int y, float scale, float currentRed, float currentGreen, float currentBlue, SetColor setColorFunction) {
        super(x, y, (int)(281.0f * scale), (int)(255.0f * scale), (class_2561)class_2561.method_43470((String)""));
        this.scale = scale;
        this.outputColor = new Vector3f(currentRed, currentGreen, currentBlue);
        this.setColorFunction = setColorFunction;
        this.setInitialValues();
        this.updateTextures();
    }

    private void setInitialValues() {
        float max = Math.max(Math.max(this.outputColor.x, this.outputColor.y), this.outputColor.z);
        float min = Math.min(Math.min(this.outputColor.x, this.outputColor.y), this.outputColor.z);
        float delta = max - min;
        if (min != max) {
            if (max == this.outputColor.x) {
                this.hue = 60.0f * ((this.outputColor.y - this.outputColor.z) / delta % 6.0f) / 360.0f;
            } else if (max == this.outputColor.y) {
                this.hue = 60.0f * ((this.outputColor.z - this.outputColor.x) / delta + 2.0f) / 360.0f;
            } else if (max == this.outputColor.z) {
                this.hue = 60.0f * ((this.outputColor.x - this.outputColor.y) / delta + 4.0f) / 360.0f;
            }
        } else {
            this.hue = 0.0f;
        }
        this.saturation = max == 0.0f ? 0.0f : delta / max;
        this.value = max;
    }

    protected void method_48579(class_332 context, int mouseX, int mouseY, float delta) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        context.method_51448().method_22903();
        context.method_51448().method_46416((float)this.method_46426(), (float)this.method_46427(), 0.0f);
        context.method_51448().method_22905(this.scale, this.scale, this.scale);
        context.method_25302(HSVColorTextureManager.getHsvTextureIdentifier(), 0, 0, 255, 255, 255, 255);
        context.method_25302(HSVColorTextureManager.getHueTextureIdentifier(), 260, 0, 20, 255, 20, 255);
        class_327 textRenderer = class_310.method_1551().field_1772;
        int width = textRenderer.method_1727("o");
        int xPos = (int)(this.saturation * 255.0f);
        int yPos = (int)((1.0 - (double)this.value) * 255.0);
        int n = xPos - width / 2;
        Objects.requireNonNull(textRenderer);
        context.method_51433(textRenderer, "o", n, yPos - 9 / 2, -1, true);
        context.method_25294(258, (int)((1.0 - (double)this.hue) * 255.0 - 2.0), 282, (int)((1.0 - (double)this.hue) * 255.0 + 2.0), -1);
        context.method_51448().method_22909();
    }

    protected boolean method_25361(double mouseX, double mouseY) {
        double mouseXPos = mouseX - (double)this.method_46426();
        if (mouseXPos > (double)(255.0f * this.scale) && mouseXPos < (double)(260.0f * this.scale)) {
            return false;
        }
        return super.method_25361(mouseX, mouseY);
    }

    public void method_25348(double mouseX, double mouseY) {
        double mouseXPos = mouseX - (double)this.method_46426();
        double mouseYPos = mouseY - (double)this.method_46427();
        boolean bl = this.pickHue = mouseXPos >= (double)(260.0f * this.scale);
        if (!this.pickHue) {
            this.colorPickerX = Math.clamp(mouseXPos / (double)this.scale, 0.0, 255.0);
            this.colorPickerY = Math.clamp(mouseYPos / (double)this.scale, 0.0, 255.0);
        }
        this.updateColors((float)(mouseYPos / (double)this.scale));
        super.method_25348(mouseX, mouseY);
    }

    protected void method_25349(double mouseX, double mouseY, double deltaX, double deltaY) {
        double mouseXPos = (mouseX - (double)this.method_46426()) / (double)this.scale;
        double mouseYPos = (mouseY - (double)this.method_46427()) / (double)this.scale;
        if (!this.pickHue) {
            this.colorPickerX = Math.clamp(mouseXPos, 0.0, 255.0);
            this.colorPickerY = Math.clamp(mouseYPos, 0.0, 255.0);
        }
        this.updateColors((float)mouseYPos);
        super.method_25349(mouseX, mouseY, deltaX, deltaY);
    }

    private void updateColors(float mouseYPos) {
        if (this.pickHue) {
            this.hue = Math.clamp((255.0f - mouseYPos) / 255.0f, 0.0f, 0.999f);
            this.updateTextures();
        } else {
            this.value = (float)Math.clamp((255.0 - this.colorPickerY) / 255.0, 0.0, 1.0);
            this.saturation = (float)Math.clamp(this.colorPickerX / 254.0, 0.0, 1.0);
        }
        int color = class_3532.method_60599((float)this.hue, (float)this.saturation, (float)this.value, (int)255);
        this.outputColor.x = (float)class_5253.class_5254.method_27765((int)color) / 255.0f;
        this.outputColor.y = (float)class_5253.class_5254.method_27766((int)color) / 255.0f;
        this.outputColor.z = (float)class_5253.class_5254.method_27767((int)color) / 255.0f;
        this.setColorFunction.setColor(this.outputColor.x, this.outputColor.y, this.outputColor.z);
    }

    private void updateTextures() {
        class_1011 hsvImage = HSVColorTextureManager.getHsvImage();
        class_1011 hueImage = HSVColorTextureManager.getHueImage();
        if (hsvImage == null || hueImage == null) {
            return;
        }
        for (int x = 0; x < 255; ++x) {
            for (int y = 0; y < 255; ++y) {
                float value = (float)(255 - y) / 255.0f;
                float saturation = (float)x / 254.0f;
                int color = class_3532.method_60599((float)this.hue, (float)saturation, (float)value, (int)255);
                int red = class_5253.class_5254.method_27765((int)color);
                int blue = class_5253.class_5254.method_27767((int)color);
                int green = class_5253.class_5254.method_27766((int)color);
                hsvImage.method_4305(x, y, 0xFF000000 | blue << 16 | green << 8 | red);
                if (x >= 20) continue;
                color = class_3532.method_60599((float)(value - 0.01f), (float)1.0f, (float)1.0f, (int)0);
                red = class_5253.class_5254.method_27765((int)color);
                blue = class_5253.class_5254.method_27767((int)color);
                green = class_5253.class_5254.method_27766((int)color);
                hueImage.method_4305(x, y, 0xFF000000 | blue << 16 | green << 8 | red);
            }
        }
        HSVColorTextureManager.upload();
    }

    protected void method_47399(class_6382 builder) {
    }

    public static interface SetColor {
        public void setColor(float var1, float var2, float var3);
    }
}

