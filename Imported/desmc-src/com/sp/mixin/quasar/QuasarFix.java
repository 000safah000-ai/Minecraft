/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  foundry.veil.api.client.render.rendertype.VeilRenderType
 *  foundry.veil.api.client.render.vertex.VeilVertexFormat
 *  net.minecraft.class_156
 *  net.minecraft.class_1921
 *  net.minecraft.class_1921$class_4688
 *  net.minecraft.class_293
 *  net.minecraft.class_293$class_5596
 *  net.minecraft.class_2960
 *  net.minecraft.class_4668
 *  net.minecraft.class_4668$class_4683
 *  net.minecraft.class_4668$class_5939
 *  net.minecraft.class_4668$class_5942
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 */
package com.sp.mixin.quasar;

import foundry.veil.api.client.render.rendertype.VeilRenderType;
import foundry.veil.api.client.render.vertex.VeilVertexFormat;
import java.util.function.BiFunction;
import net.minecraft.class_156;
import net.minecraft.class_1921;
import net.minecraft.class_293;
import net.minecraft.class_2960;
import net.minecraft.class_4668;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={VeilRenderType.class})
public class QuasarFix {
    @Shadow
    @Final
    private static class_4668.class_5942 PARTICLE;
    @Shadow
    @Final
    private static class_4668.class_5942 PARTICLE_ADDITIVE;
    @Shadow
    private static final BiFunction<class_2960, Boolean, class_1921> QUASAR_PARTICLE;

    static {
        QUASAR_PARTICLE = class_156.method_34865((texture, additive) -> {
            class_1921.class_4688 state = class_1921.class_4688.method_23598().method_34578(additive != false ? PARTICLE_ADDITIVE : PARTICLE).method_34577((class_4668.class_5939)new class_4668.class_4683(texture, false, false)).method_23615(additive != false ? class_4668.field_21366 : class_4668.field_21370).method_23608(class_4668.field_21383).method_23616(class_4668.field_21349).method_23617(false);
            return class_1921.method_24049((String)"veil:quasar_particle", (class_293)VeilVertexFormat.QUASAR_PARTICLE, (class_293.class_5596)class_293.class_5596.field_27382, (int)786432, (boolean)false, (additive == false ? 1 : 0) != 0, (class_1921.class_4688)state);
        });
    }
}

