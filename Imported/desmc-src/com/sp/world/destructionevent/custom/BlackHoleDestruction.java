/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  foundry.veil.api.client.util.Easing
 *  net.minecraft.class_1657
 *  net.minecraft.class_1937
 *  net.minecraft.class_1944
 *  net.minecraft.class_2338
 *  net.minecraft.class_2338$class_2339
 *  net.minecraft.class_2350
 *  net.minecraft.class_2374
 *  net.minecraft.class_238
 *  net.minecraft.class_2382
 *  net.minecraft.class_239$class_240
 *  net.minecraft.class_243
 *  net.minecraft.class_3726
 *  net.minecraft.class_3756
 *  net.minecraft.class_3959
 *  net.minecraft.class_3959$class_242
 *  net.minecraft.class_3959$class_3960
 *  net.minecraft.class_3965
 *  net.minecraft.class_4184
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597
 *  net.minecraft.class_4604
 *  net.minecraft.class_5819
 */
package com.sp.world.destructionevent.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sp.entity.custom.BlockPhysicsEntity;
import com.sp.sounds.ModSounds;
import com.sp.util.MathUtil;
import com.sp.util.RenderUtil;
import foundry.veil.api.client.util.Easing;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.class_1944;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2374;
import net.minecraft.class_238;
import net.minecraft.class_2382;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_3726;
import net.minecraft.class_3756;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_4604;
import net.minecraft.class_5819;

public class BlackHoleDestruction {
    private static class_238 selection;
    private static List<class_2338> surfaceBlocks;
    private static final List<BlockPhysicsEntity> resetEntityMap;
    private static boolean startDestruction;
    private static int breakOffCooldown;
    private static int aggressiveCooldown;
    private static final class_5819 random;
    private static final class_3756 noiseSampler;

    public static void tick(class_1937 world) {
        if (!startDestruction || surfaceBlocks.isEmpty()) {
            return;
        }
        if (breakOffCooldown <= 0) {
            if (aggressiveCooldown <= 0) {
                for (class_1657 player : world.method_18456()) {
                    if (player.method_7337() || player.method_7325()) continue;
                    class_2338 standingBlockPos = player.method_24828() ? player.method_24515().method_10074() : world.method_17742(new class_3959(player.method_19538(), player.method_19538().method_1031(0.0, -50.0, 0.0), class_3959.class_3960.field_17558, class_3959.class_242.field_1348, class_3726.method_16194())).method_17777();
                    BlackHoleDestruction.collectAndBreakOff(world, standingBlockPos);
                }
                aggressiveCooldown = random.method_39332(100, 200);
            } else {
                BlackHoleDestruction.collectAndBreakOff(world, MathUtil.randomValueInList(surfaceBlocks));
            }
            breakOffCooldown = random.method_39332(5, 8);
        } else {
            --breakOffCooldown;
            --aggressiveCooldown;
        }
    }

    private static void collectAndBreakOff(class_1937 world, class_2338 startingBlockPos) {
        List<class_2338> breakOffList = switch (random.method_39332(1, 2)) {
            case 2 -> BlackHoleDestruction.getIsland(world, startingBlockPos);
            default -> BlackHoleDestruction.getNoodle(world, startingBlockPos);
        };
        if (!breakOffList.isEmpty()) {
            BlackHoleDestruction.breakOff(world, breakOffList);
        }
    }

    private static List<class_2338> getNoodle(class_1937 world, class_2338 startPos) {
        ArrayList<class_2338> breakOffList = new ArrayList<class_2338>();
        class_2338.class_2339 mutable = startPos.method_25503();
        breakOffList.add((class_2338)mutable);
        int size = random.method_39332(3, 10);
        for (int i = 0; i < size; ++i) {
            class_2338 pos;
            class_2350 randDir = MathUtil.randomValueInList(class_2350.values());
            if (randDir == class_2350.field_11036 || !world.method_8320(pos = mutable.method_10093(randDir)).method_51367()) continue;
            breakOffList.add(pos);
            surfaceBlocks.remove(pos);
            mutable.method_10101((class_2382)pos);
        }
        return breakOffList;
    }

    private static List<class_2338> getPerlinNoise(class_1937 world, class_2338 startPos) {
        int range;
        ArrayList<class_2338> breakOffList = new ArrayList<class_2338>();
        for (int i = range = random.method_39332(3, 8); i > 0; --i) {
            for (int x = -range; x <= range; ++x) {
                for (int z = -range; z <= range; ++z) {
                    class_2338 selectedPos = startPos.method_10069(x, 0, z);
                    class_243 pos = selectedPos.method_46558().method_1021(0.2);
                    double noise = (noiseSampler.method_33658(pos.field_1352, pos.field_1351, pos.field_1350) * 2.0 - 1.0) * 5.0;
                    if (!(noise > 0.4) || !world.method_8320(selectedPos).method_51367()) continue;
                    breakOffList.add(selectedPos);
                    surfaceBlocks.remove(selectedPos);
                }
            }
        }
        return breakOffList;
    }

    private static List<class_2338> getIsland(class_1937 world, class_2338 startPos) {
        int radius;
        ArrayList<class_2338> breakOffList = new ArrayList<class_2338>();
        for (int i = radius = random.method_39332(1, 3); i > 0; --i) {
            class_2338 centerBlockPos = startPos.method_10069(0, -(radius - i), 0);
            for (int x = -radius; x <= radius; ++x) {
                for (int z = -radius; z <= radius; ++z) {
                    class_2338 selectedPos = centerBlockPos.method_10069(x, 0, z);
                    class_243 centerPos = centerBlockPos.method_46558();
                    class_243 centerDirection = centerPos.method_1020(selectedPos.method_46558()).method_1029();
                    class_243 adjustedSelectedPos = selectedPos.method_46558().method_1020(centerDirection.method_1021((double)Easing.EASE_OUT_QUINT.ease((float)(radius - i) / (float)radius)));
                    boolean isInRange = adjustedSelectedPos.method_24802((class_2374)centerPos, (double)i);
                    if (!isInRange || !world.method_8320(selectedPos).method_51367()) continue;
                    breakOffList.add(centerBlockPos.method_10069(x, 0, z));
                    surfaceBlocks.remove(selectedPos);
                }
            }
        }
        return breakOffList;
    }

    public static void renderSelectionDebug(class_4587 matrices, class_4597 vertexConsumers, class_4184 camera, class_4604 frustum) {
        if (selection == null) {
            return;
        }
        matrices.method_22903();
        class_243 cameraPos = camera.method_19326();
        matrices.method_22904(-cameraPos.field_1352, -cameraPos.field_1351, -cameraPos.field_1350);
        int alpha = (int)((Math.sin(RenderSystem.getShaderGameTime() * 2000.0f) * 0.5 + 0.5) * 100.0) + 50;
        RenderUtil.drawBox(matrices, vertexConsumers, selection.method_1005(), new class_243(1.0, 2.0, 1.0), 0, 0, 255, 150, false, false);
        ArrayList<class_2338> listCopy = new ArrayList<class_2338>(surfaceBlocks);
        for (class_2338 surfaceBlock : listCopy) {
            class_243 pos;
            boolean isVisible;
            if (surfaceBlock == null || !(isVisible = frustum.method_23093(class_238.method_30048((class_243)(pos = surfaceBlock.method_46558()), (double)1.0, (double)1.0, (double)1.0)))) continue;
            RenderUtil.drawBox(matrices, vertexConsumers, pos, 1.0, 20, 200, 20, alpha, false, false);
        }
        matrices.method_22909();
    }

    public static int selectSurfaceBlocks(class_2338 pos, class_1937 world) {
        surfaceBlocks = new ArrayList<class_2338>();
        selection = class_238.method_54784((class_2338)pos, (class_2338)pos).method_1009(100.0, 0.0, 100.0);
        class_2338.method_29715((class_238)selection.method_35578(BlackHoleDestruction.selection.field_1325 - 0.5).method_35577(BlackHoleDestruction.selection.field_1320 - 0.5).method_35579(BlackHoleDestruction.selection.field_1324 - 0.5)).forEachOrdered(blockPos -> {
            class_2338.class_2339 standInBlockPos = blockPos.method_25503();
            class_2338.class_2339 standInBlockPos2 = blockPos.method_25503();
            Object surfaceBlock = null;
            do {
                if (!world.method_8320(standInBlockPos.method_10084()).method_51367()) {
                    class_3965 blockHitResultUp = world.method_17742(new class_3959(standInBlockPos.method_61082().method_1031(0.0, 1.1, 0.0), standInBlockPos.method_61082().method_1031(0.0, 20.0, 0.0), class_3959.class_3960.field_17558, class_3959.class_242.field_1348, class_3726.method_16194()));
                    if (blockHitResultUp.method_17783() != class_239.class_240.field_1332) break;
                    surfaceBlock = blockHitResultUp.method_17777();
                    standInBlockPos.method_10101((class_2382)blockHitResultUp.method_17777());
                    continue;
                }
                surfaceBlock = standInBlockPos.method_10084();
                standInBlockPos.method_10101((class_2382)standInBlockPos.method_10084());
            } while (standInBlockPos.method_10264() <= 300);
            if (surfaceBlock == null) {
                class_3965 blockHitResultDown;
                surfaceBlock = !world.method_8320(blockPos).method_51367() ? ((blockHitResultDown = world.method_17742(new class_3959(blockPos.method_61082(), blockPos.method_61082().method_1031(0.0, -50.0, 0.0), class_3959.class_3960.field_17558, class_3959.class_242.field_1348, class_3726.method_16194()))).method_17783() == class_239.class_240.field_1332 ? blockHitResultDown.method_17777() : standInBlockPos2) : standInBlockPos2;
            }
            surfaceBlocks.add((class_2338)surfaceBlock);
            for (int i = 1; i <= 11; ++i) {
                class_2338 belowPos = surfaceBlock.method_10074();
                if (!world.method_8320(belowPos).method_51367()) {
                    class_3965 blockHitResultDown = world.method_17742(new class_3959(belowPos.method_61082(), belowPos.method_46558().method_1031(0.0, -50.0, 0.0), class_3959.class_3960.field_17558, class_3959.class_242.field_1348, class_3726.method_16194()));
                    if (blockHitResultDown.method_17783() != class_239.class_240.field_1332) break;
                    belowPos = blockHitResultDown.method_17777();
                }
                for (class_2350 direction : class_2350.values()) {
                    class_2338 lightTestPos = belowPos.method_10093(direction);
                    if (world.method_8314(class_1944.field_9284, lightTestPos) < 10) continue;
                    surfaceBlocks.add(belowPos);
                    break;
                }
                surfaceBlock = belowPos;
            }
        });
        return surfaceBlocks.size();
    }

    public static void reset() {
        if (!resetEntityMap.isEmpty()) {
            resetEntityMap.forEach(blockPhysicsEntity -> {
                if (blockPhysicsEntity != null && !blockPhysicsEntity.method_31481()) {
                    blockPhysicsEntity.method_31472();
                }
            });
            resetEntityMap.clear();
        }
    }

    private static void breakOff(class_1937 world, List<class_2338> blocks) {
        BlockPhysicsEntity entity = BlockPhysicsEntity.ofBlocks(world, blocks);
        int shouldBeMeteor = random.method_39332(1, 5);
        if (shouldBeMeteor > 1) {
            entity.method_18800(0.0, 0.06, -0.2);
            entity.component.setRotationSpeed(MathUtil.nextBetween(0.1f, 0.8f), MathUtil.nextBetween(0.1f, 0.8f), MathUtil.nextBetween(0.1f, 0.8f));
            entity.component.sync();
            entity.field_6007 = true;
            entity.field_6037 = true;
            entity.method_5783(ModSounds.BREAK_OFF, 8.0f, MathUtil.nextBetween(0.5f, 1.5f));
        } else {
            class_243 inComingDirection = new class_243(-0.5, -0.5, 0.1).method_1029();
            entity.method_33574(entity.method_19538().method_1019(inComingDirection.method_22882().method_1021(100.0)));
            entity.method_18799(inComingDirection.method_1021(3.0));
            entity.component.setMeteorLike(true);
            entity.component.setRotationSpeed(MathUtil.nextBetween(0.8f, 1.5f), MathUtil.nextBetween(0.8f, 1.5f), MathUtil.nextBetween(0.8f, 1.5f));
            entity.component.sync();
        }
        resetEntityMap.add(entity);
    }

    public static void setStartDestruction(boolean bl) {
        startDestruction = bl;
    }

    public static void clear() {
        selection = null;
        surfaceBlocks.clear();
    }

    static {
        surfaceBlocks = new ArrayList<class_2338>();
        resetEntityMap = new ArrayList<BlockPhysicsEntity>();
        random = class_5819.method_43047();
        noiseSampler = new class_3756(random);
    }
}

