/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  foundry.veil.api.client.render.VeilRenderBridge
 *  net.minecraft.class_156
 *  net.minecraft.class_1921
 *  net.minecraft.class_1921$class_4688
 *  net.minecraft.class_290
 *  net.minecraft.class_293
 *  net.minecraft.class_293$class_5596
 *  net.minecraft.class_296
 *  net.minecraft.class_296$class_297
 *  net.minecraft.class_296$class_298
 *  net.minecraft.class_2960
 *  net.minecraft.class_4668
 *  net.minecraft.class_4668$class_5939
 *  net.minecraft.class_4668$class_5940
 *  net.minecraft.class_4668$class_5942
 */
package com.sp.render;

import com.sp.DestroyingMinecraft;
import foundry.veil.api.client.render.VeilRenderBridge;
import java.util.function.BiFunction;
import net.minecraft.class_156;
import net.minecraft.class_1921;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_296;
import net.minecraft.class_2960;
import net.minecraft.class_4668;

public class CustomRenderLayersAndVertexFormats {
    public static final class_296 MATERIAL = class_296.method_60845((int)7, (int)0, (class_296.class_297)class_296.class_297.field_1623, (class_296.class_298)class_296.class_298.field_20782, (int)1);
    public static final class_296 ENTITY_POSITION = class_296.method_60845((int)8, (int)0, (class_296.class_297)class_296.class_297.field_1623, (class_296.class_298)class_296.class_298.field_20782, (int)4);
    public static final class_293 POSITION_POSITION = class_293.method_60833().method_60842("Position", class_296.field_52107).method_60842("Color", class_296.field_52108).method_60842("Entity_Position", ENTITY_POSITION).method_60840();
    public static final class_293 POSITION_TEXTURE_NORMAL = class_293.method_60833().method_60842("Position", class_296.field_52107).method_60842("UV0", class_296.field_52109).method_60842("Normal", class_296.field_52113).method_60840();
    public static final class_293 BLOCK = class_293.method_60833().method_60842("Position", class_296.field_52107).method_60842("Color", class_296.field_52108).method_60842("UV0", class_296.field_52109).method_60842("UV1", class_296.field_52111).method_60842("UV2", class_296.field_52112).method_60842("Normal", class_296.field_52113).method_60842("Material", MATERIAL).method_60841(1).method_60840();
    private static final class_4668.class_5942 VOID_BLOCK_SHADER = VeilRenderBridge.shaderState((class_2960)DestroyingMinecraft.idOf("void/void"));
    private static final class_4668.class_5942 METEOR_SHADER = VeilRenderBridge.shaderState((class_2960)DestroyingMinecraft.idOf("meteor/meteor"));
    private static final class_4668.class_5942 ENTITY_BLOOM_SHADER = VeilRenderBridge.shaderState((class_2960)DestroyingMinecraft.idOf("star_piercer/star_piercer"));
    public static final class_1921 VOID_BLOCK = class_1921.method_24049((String)"void_block", (class_293)class_290.field_1576, (class_293.class_5596)class_293.class_5596.field_27382, (int)256, (boolean)false, (boolean)false, (class_1921.class_4688)class_1921.class_4688.method_23598().method_34578(VOID_BLOCK_SHADER).method_23617(false));
    public static final class_1921 METEOR = class_1921.method_24049((String)"meteor", (class_293)POSITION_POSITION, (class_293.class_5596)class_293.class_5596.field_27382, (int)256, (boolean)false, (boolean)false, (class_1921.class_4688)class_1921.class_4688.method_23598().method_34578(METEOR_SHADER).method_23617(false));
    public static final BiFunction<class_2960, class_2960, class_1921> ENTITY_BLOOM = class_156.method_34865((texture, bloomTexture) -> class_1921.method_24049((String)"entity_bloom", (class_293)class_290.field_1580, (class_293.class_5596)class_293.class_5596.field_27382, (int)1536, (boolean)true, (boolean)false, (class_1921.class_4688)class_1921.class_4688.method_23598().method_34577((class_4668.class_5939)class_4668.class_5940.method_34560().method_34563(texture, false, true).method_34563(bloomTexture, false, false).method_34563(bloomTexture, false, false).method_34563(bloomTexture, false, false).method_34562()).method_23615(class_4668.field_21364).method_23608(class_4668.field_21383).method_23611(class_4668.field_21385).method_34578(ENTITY_BLOOM_SHADER).method_23617(true)));
}

