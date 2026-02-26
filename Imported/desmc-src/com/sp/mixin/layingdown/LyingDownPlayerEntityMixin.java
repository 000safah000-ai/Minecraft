/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1299
 *  net.minecraft.class_1309
 *  net.minecraft.class_1657
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_243
 *  net.minecraft.class_2680
 *  net.minecraft.class_2769
 *  net.minecraft.class_2940
 *  net.minecraft.class_2941
 *  net.minecraft.class_2943
 *  net.minecraft.class_2945
 *  net.minecraft.class_2945$class_9222
 *  net.minecraft.class_4050
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.Redirect
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.sp.mixin.layingdown;

import com.sp.block.custom.ChairBlock;
import com.sp.mixininterfaces.LayingDownPlayerEntity;
import java.util.Optional;
import net.minecraft.class_1299;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_2769;
import net.minecraft.class_2940;
import net.minecraft.class_2941;
import net.minecraft.class_2943;
import net.minecraft.class_2945;
import net.minecraft.class_4050;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_1657.class})
public abstract class LyingDownPlayerEntityMixin
extends class_1309
implements LayingDownPlayerEntity {
    @Unique
    private static final class_2940<Boolean> LAYING_DOWN = class_2945.method_12791(class_1657.class, (class_2941)class_2943.field_13323);
    @Unique
    private static final class_2940<Optional<class_2338>> LAYING_DOWN_POS = class_2945.method_12791(class_1657.class, (class_2941)class_2943.field_13315);

    protected LyingDownPlayerEntityMixin(class_1299<? extends class_1309> entityType, class_1937 world) {
        super(entityType, world);
    }

    @Shadow
    protected abstract boolean method_21824();

    @Override
    public boolean isLayingDown() {
        return (Boolean)this.field_6011.method_12789(LAYING_DOWN) != false && this.getLayingDownPos().isPresent();
    }

    @Override
    public void setLayingDown(boolean layingDown) {
        this.field_6011.method_12778(LAYING_DOWN, (Object)layingDown);
    }

    @Override
    public void setLayingDownPos(class_2338 layingDownPos) {
        this.field_6011.method_12778(LAYING_DOWN_POS, layingDownPos != null ? Optional.of(layingDownPos) : Optional.empty());
    }

    @Override
    public Optional<class_2338> getLayingDownPos() {
        return (Optional)this.field_6011.method_12789(LAYING_DOWN_POS);
    }

    @Inject(method={"initDataTracker"}, at={@At(value="TAIL")})
    private void addLayingDown(class_2945.class_9222 builder, CallbackInfo ci) {
        builder.method_56912(LAYING_DOWN, (Object)false);
        builder.method_56912(LAYING_DOWN_POS, Optional.empty());
    }

    @Inject(method={"tick"}, at={@At(value="INVOKE", target="Lnet/minecraft/entity/player/PlayerEntity;updatePose()V")})
    private void stopLayingDown(CallbackInfo ci) {
        class_2350 layingDownDirection;
        if (!this.method_37908().field_9236 && this.isLayingDown() && this.method_21824()) {
            this.getUp();
        }
        if (this.isLayingDown() && (layingDownDirection = this.getLayingDownDirection()) != null) {
            layingDownDirection = layingDownDirection.method_10153();
            class_243 offset = new class_243((double)layingDownDirection.method_10148(), (double)layingDownDirection.method_10164(), (double)layingDownDirection.method_10165()).method_1021(0.25).method_1031(0.0, 0.1, 0.0);
            this.method_33574(this.getLayingDownPos().get().method_46558().method_1019(offset));
            this.method_18800(0.0, 0.0, 0.0);
        }
    }

    @Redirect(method={"updatePose"}, at=@At(value="INVOKE", target="Lnet/minecraft/entity/player/PlayerEntity;isSleeping()Z"))
    private boolean test(class_1657 instance) {
        boolean isSleeping = instance.method_6113();
        if (instance instanceof LayingDownPlayerEntity) {
            LayingDownPlayerEntity layingDownPLayerEntity = (LayingDownPlayerEntity)instance;
            return isSleeping || layingDownPLayerEntity.isLayingDown();
        }
        return isSleeping;
    }

    @Override
    public void getUp() {
        this.method_18380(class_4050.field_18076);
        class_2680 blockState = this.method_37908().method_8320(this.getLayingDownPos().get());
        if (blockState.method_26204() instanceof ChairBlock) {
            this.method_37908().method_8501(this.getLayingDownPos().get(), (class_2680)blockState.method_11657((class_2769)ChairBlock.OCCUPIED, (Comparable)Boolean.valueOf(false)));
        }
        this.setLayingDown(false);
        this.setLayingDownPos(null);
    }

    @Override
    public class_2350 getLayingDownDirection() {
        class_2338 layingDownPos = this.getLayingDownPos().orElse(null);
        return layingDownPos != null ? ChairBlock.getDirection(this.method_37908(), layingDownPos) : null;
    }
}

