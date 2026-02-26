/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  net.minecraft.class_1937
 *  net.minecraft.class_2246
 *  net.minecraft.class_2338
 *  net.minecraft.class_2338$class_2339
 *  net.minecraft.class_2374
 *  net.minecraft.class_238
 *  net.minecraft.class_243
 *  net.minecraft.class_5575
 */
package com.sp.world.spinningblockexplosion.custom;

import com.sp.entity.custom.SpinningBlockEntity;
import com.sp.world.ModGameRules;
import com.sp.world.spinningblockexplosion.SpinningBlockExplosion;
import java.util.List;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2374;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_5575;

public class PointSBE
extends SpinningBlockExplosion {
    private final int radius;
    private final float density;

    public PointSBE(int radius, float density, class_243 position) {
        super(position);
        this.radius = radius;
        this.density = density;
    }

    @Override
    public void explode() {
        if (!this.explode) {
            return;
        }
        class_2338.class_2339 mutable = new class_2338.class_2339();
        for (int x = -this.radius; x < this.radius; ++x) {
            for (int y = -this.radius; y < this.radius; ++y) {
                for (int z = -this.radius; z < this.radius; ++z) {
                    if (!mutable.method_10102(this.position.field_1352 + (double)x, this.position.field_1351 + (double)y, this.position.field_1350 + (double)z).method_19769((class_2374)this.position, (double)this.radius) || !this.world.method_8320((class_2338)mutable).method_51367()) continue;
                    if (this.random.method_43057() < this.density) {
                        SpinningBlockEntity spinningBlockEntity = SpinningBlockEntity.spawnFromBlock((class_1937)this.world, (class_2338)mutable, this.world.method_8320((class_2338)mutable));
                        spinningBlockEntity.getComponent().setLifeTime(this.random.method_39332(60, 120));
                        this.world.method_8649((class_1297)spinningBlockEntity);
                        spinningBlockEntity.method_18799(mutable.method_46558().method_1020(this.position).method_1029());
                        spinningBlockEntity.method_45319(new class_243(0.0, 1.0, 0.0));
                        spinningBlockEntity.field_6007 = true;
                        spinningBlockEntity.field_6037 = true;
                    }
                    if (!this.world.method_8450().method_8355(ModGameRules.ALLOW_EXPLOSIONS)) continue;
                    this.world.method_8501((class_2338)mutable, class_2246.field_10124.method_9564());
                }
            }
        }
        int affectedRadius = Math.max(this.radius * 3, 10);
        List nearbyEntitiesList = this.world.method_18023(class_5575.method_31795(class_1309.class), new class_238(this.position.method_1023(100.0, 100.0, 100.0), this.position.method_1031(100.0, 100.0, 100.0)), class_1309::method_5805);
        for (class_1309 entity : nearbyEntitiesList) {
            double distanceFromCenter = Math.sqrt(entity.method_5707(this.position)) / (double)affectedRadius;
            if (distanceFromCenter > 1.3) continue;
            class_243 knockbackVelocity = entity.field_22467.method_1020(this.position).method_1031(0.0, 0.75, 0.0).method_1029();
            entity.method_45319(knockbackVelocity.method_1021((1.3 - distanceFromCenter) * 2.0));
            entity.method_5643(this.world.method_48963().method_48807(null), (float)(1.3 - distanceFromCenter) * (float)this.radius);
        }
        this.explode = false;
        SpinningBlockExplosion.removeExplosion(this);
    }
}

