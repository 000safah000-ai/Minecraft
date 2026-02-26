/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.class_310
 *  net.minecraft.class_3756
 *  net.minecraft.class_4184
 *  net.minecraft.class_5819
 */
package com.sp.render.camerashake;

import com.sp.render.camerashake.AbstractCameraShakeInstance;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_310;
import net.minecraft.class_3756;
import net.minecraft.class_4184;
import net.minecraft.class_5819;

@Environment(value=EnvType.CLIENT)
public class CameraShakeManager {
    private static final List<AbstractCameraShakeInstance> INSTANCES = new ArrayList<AbstractCameraShakeInstance>();
    private static final class_3756 noiseSampler = new class_3756(class_5819.method_43047());
    private static float noiseY;
    private static float amplitude;
    private static float shakeSpeed;
    private static float totalTrauma;
    private static float totalRoll;

    public static void updateCamera(class_4184 camera) {
        float frameDelta = class_310.method_1551().method_60646().method_60636();
        if (noiseY >= 1000.0f) {
            noiseY = 0.0f;
        }
        double pitchOffset = (double)(amplitude * totalTrauma) * noiseSampler.method_33658(3.0, (double)(noiseY += shakeSpeed * frameDelta), 0.0);
        double yawOffset = (double)(amplitude * totalTrauma) * noiseSampler.method_33658(25.0, (double)noiseY, 0.0);
        totalRoll = (float)((double)(amplitude * totalTrauma) * noiseSampler.method_33658(75.0, (double)noiseY, 0.0));
        camera.method_19325((float)((double)camera.method_19330() + yawOffset), (float)((double)camera.method_19329() + pitchOffset));
    }

    public static void instancesTicks() {
        float tempTrauma = 0.0f;
        INSTANCES.removeIf(AbstractCameraShakeInstance::isFinished);
        for (AbstractCameraShakeInstance instance : INSTANCES) {
            if (tempTrauma >= 5.0f) {
                tempTrauma = 5.0f;
                break;
            }
            instance.tick();
            tempTrauma += instance.getTrauma();
        }
        totalTrauma = tempTrauma * tempTrauma;
    }

    public static float getTotalRoll() {
        return totalRoll;
    }

    public static void addCameraShake(AbstractCameraShakeInstance cameraShakeInstance) {
        INSTANCES.add(cameraShakeInstance);
    }

    static {
        amplitude = 4.0f;
        shakeSpeed = 0.5f;
    }
}

