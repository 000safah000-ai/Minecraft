/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  foundry.veil.Veil
 *  foundry.veil.api.client.registry.RenderTypeShardRegistry
 *  foundry.veil.api.client.render.VeilLevelPerspectiveRenderer
 *  foundry.veil.api.client.render.VeilRenderSystem
 *  foundry.veil.api.client.render.dynamicbuffer.DynamicBufferType
 *  foundry.veil.api.client.render.framebuffer.AdvancedFbo
 *  foundry.veil.api.client.render.post.PostProcessingManager
 *  foundry.veil.api.client.render.rendertype.VeilRenderType
 *  foundry.veil.impl.client.render.dynamicbuffer.DynamicBufferShard
 *  foundry.veil.platform.VeilEventPlatform
 *  net.fabricmc.api.ClientModInitializer
 *  net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
 *  net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
 *  net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
 *  net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
 *  net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener
 *  net.fabricmc.fabric.api.resource.ResourceManagerHelper
 *  net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
 *  net.minecraft.class_1921
 *  net.minecraft.class_1921$class_4687
 *  net.minecraft.class_1937
 *  net.minecraft.class_276
 *  net.minecraft.class_2960
 *  net.minecraft.class_310
 *  net.minecraft.class_3264
 *  net.minecraft.class_3300
 *  net.minecraft.class_4597
 *  net.minecraft.class_4668
 *  net.minecraft.class_5601
 *  net.minecraft.class_5616
 *  net.minecraft.class_638
 */
package com.sp;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sp.DestroyingMinecraft;
import com.sp.block.entity.ModBlockEntities;
import com.sp.block.entity.client.PhysicsDoorBlockRenderer;
import com.sp.cca.InitializeComponents;
import com.sp.cca.custom.entity.PlayerComponent;
import com.sp.cca.custom.world.WorldDestructionEventsComponent;
import com.sp.config.DestroyingMinecraftConfig;
import com.sp.destruction.DestructionEvent;
import com.sp.destruction.client.ClientDestructionEvent;
import com.sp.destruction.client.ClientDestructionEvents;
import com.sp.entity.ModEntities;
import com.sp.entity.client.model.StarPiercerModel;
import com.sp.entity.client.renderer.BlockPhysicsEntityRenderer;
import com.sp.entity.client.renderer.MeteorEntityRenderer;
import com.sp.entity.client.renderer.SpinningBlockEntityRenderer;
import com.sp.entity.client.renderer.StarPiercerEntityRenderer;
import com.sp.item.ModModelPredicates;
import com.sp.mixin.PostProcessingManagerAccessor;
import com.sp.networking.InitializePackets;
import com.sp.networking.callbacks.ClientConnectionEvents;
import com.sp.render.BlackScreenManager;
import com.sp.render.BlockInstanceRenderer;
import com.sp.render.SelectionHandler;
import com.sp.render.ShaderType;
import com.sp.render.ShadowMapRenderer;
import com.sp.render.TranslucentRenderer;
import com.sp.render.camerashake.CameraShakeManager;
import com.sp.render.gui.HSVColorTextureManager;
import com.sp.render.gui.hud.DestructionTitleRenderCallback;
import com.sp.render.gui.hud.PlayZoneWarningRenderCallback;
import com.sp.render.postshaders.PostShader;
import com.sp.render.postshaders.PostShaders;
import com.sp.util.RenderUtil;
import com.sp.util.tickinstances.client.ClientTickInstances;
import com.sp.world.destructionevent.custom.BlackHoleDestruction;
import com.sp.world.playzone.PlayZone;
import com.sp.world.playzone.PlayZoneManager;
import foundry.veil.Veil;
import foundry.veil.api.client.registry.RenderTypeShardRegistry;
import foundry.veil.api.client.render.VeilLevelPerspectiveRenderer;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.dynamicbuffer.DynamicBufferType;
import foundry.veil.api.client.render.framebuffer.AdvancedFbo;
import foundry.veil.api.client.render.post.PostProcessingManager;
import foundry.veil.api.client.render.rendertype.VeilRenderType;
import foundry.veil.impl.client.render.dynamicbuffer.DynamicBufferShard;
import foundry.veil.platform.VeilEventPlatform;
import java.util.HashSet;
import java.util.Set;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.class_1921;
import net.minecraft.class_1937;
import net.minecraft.class_276;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3264;
import net.minecraft.class_3300;
import net.minecraft.class_4597;
import net.minecraft.class_4668;
import net.minecraft.class_5601;
import net.minecraft.class_5616;
import net.minecraft.class_638;

public class DestroyingMinecraftClient
implements ClientModInitializer {
    private static final class_2960 BLOOM_FRAMEBUFFER = DestroyingMinecraft.idOf("bloom/bloom_start");
    private static final class_2960 TRANSLUCENT_FRAMEBUFFER = DestroyingMinecraft.idOf("translucent");
    public static boolean shouldRenderDebug = false;
    public BlockInstanceRenderer blockInstanceRenderer;
    private static ShaderType prevShaderType;
    private static final Set<class_2960> removedPipelines;
    private static boolean enabledDynamicBuffers;
    public static int destructionDistance;

    public void onInitializeClient() {
        HudRenderCallback.EVENT.register((Object)new DestructionTitleRenderCallback());
        HudRenderCallback.EVENT.register((Object)new PlayZoneWarningRenderCallback());
        InitializePackets.registerClientNetworking();
        ClientTickInstances.registerAllClientTickInstances();
        ModModelPredicates.registerModelPredicates();
        ClientDestructionEvents.registerClientEvents();
        PostShaders.registerPostShaders();
        EntityRendererRegistry.register(ModEntities.SPINNING_BLOCK, SpinningBlockEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.BLOCK_PHYSICS_ENTITY, BlockPhysicsEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.METEOR_ENTITY, MeteorEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer((class_5601)StarPiercerModel.STAR_PIERCER_MODEL_LAYER, StarPiercerModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.STAR_PIERCER_ENTITY, StarPiercerEntityRenderer::new);
        class_5616.method_32144(ModBlockEntities.PHYSICS_DOOR_BE, PhysicsDoorBlockRenderer::new);
        RenderTypeShardRegistry.addGenericShard(renderType -> "translucent_target".equals(DestroyingMinecraftClient.getOutputName(renderType)), (class_4668[])new class_4668[]{new DynamicBufferShard("translucent", DestroyingMinecraftClient::getTranslucentBuffer)});
        VeilEventPlatform.INSTANCE.onVeilRenderLevelStage((stage, levelRenderer, bufferSource, matrixStack, frustumMatrix, projectionMatrix, renderTick, deltaTracker, camera, frustum) -> {
            PlayerComponent component;
            if (!enabledDynamicBuffers) {
                this.enableDynamicBuffers();
                enabledDynamicBuffers = true;
            }
            class_310 client = class_310.method_1551();
            class_638 clientWorld = client.field_1687;
            RenderSystem.disableDepthTest();
            if (client.field_1724 != null) {
                component = (PlayerComponent)InitializeComponents.PLAYERS.get((Object)client.field_1724);
                if (BlackScreenManager.isBlackScreen() || component.isInWaitingRoom()) {
                    client.field_1690.field_1842 = true;
                }
            }
            if (clientWorld == null || VeilLevelPerspectiveRenderer.isRenderingPerspective()) {
                return;
            }
            switch (stage) {
                case AFTER_LEVEL: {
                    if (camera != null) {
                        ShadowMapRenderer.renderShadowMap(camera);
                    }
                    if (prevShaderType == DestroyingMinecraftConfig.shaderType) break;
                    this.updatePostShader();
                    prevShaderType = DestroyingMinecraftConfig.shaderType;
                    break;
                }
                case AFTER_BLOCK_ENTITIES: {
                    if (this.blockInstanceRenderer == null) {
                        this.blockInstanceRenderer = new BlockInstanceRenderer();
                    }
                    if (DestroyingMinecraftConfig.shaderType == ShaderType.BLACK_HOLE) {
                        this.blockInstanceRenderer.render();
                    }
                    TranslucentRenderer.bind(256);
                    if (client.field_1724 == null) {
                        return;
                    }
                    component = (PlayerComponent)InitializeComponents.PLAYERS.get((Object)client.field_1724);
                    if (shouldRenderDebug) {
                        BlackHoleDestruction.renderSelectionDebug(matrixStack.toPoseStack(), (class_4597)bufferSource, camera, frustum);
                    }
                    if (shouldRenderDebug || !component.isInsideAPlayZone()) {
                        for (PlayZone playZone : PlayZoneManager.getActivePlayZones()) {
                            boolean inside = playZone.isPositionInsideZone(client.field_1724.field_22467);
                            int[] colors = new int[]{inside ? 50 : 255, inside ? 255 : 50, 50, 100};
                            RenderUtil.drawBox(matrixStack.toPoseStack(), (class_4597)bufferSource, playZone.getBoundingBox().method_997(camera.method_19326().method_22882()), colors[0], colors[1], colors[2], colors[3], true, playZone.isPositionInsideZone(camera.method_19326()));
                        }
                    }
                    SelectionHandler.renderSelection(matrixStack.toPoseStack(), (class_4597)bufferSource, deltaTracker, camera);
                    break;
                }
                case AFTER_WEATHER: {
                    TranslucentRenderer.unbind();
                }
            }
        });
        VeilEventPlatform.INSTANCE.preVeilPostProcessing((name, pipeline, context) -> {
            class_310 client = class_310.method_1551();
            class_638 clientWorld = client.field_1687;
            float tickDelta = client.method_60646().method_60637(true);
            if (clientWorld != null) {
                ShaderType type = DestroyingMinecraftConfig.shaderType;
                for (PostShader postShader : type.getEnabledShaders()) {
                    if (!postShader.getPost().equals((Object)name)) continue;
                    postShader.setUniforms(context, tickDelta, client, (class_1937)clientWorld);
                }
            }
        });
        ClientConnectionEvents.DISCONNECT.register(client -> {
            this.blockInstanceRenderer.free();
            this.blockInstanceRenderer = null;
            BlackHoleDestruction.clear();
            if (!client.method_1496()) {
                PlayZoneManager.clearAllPlayZones();
            }
            for (DestructionEvent event : ClientDestructionEvent.getAllClientInstances()) {
                event.resetAnimations();
            }
            if (client.field_1687 != null) {
                WorldDestructionEventsComponent component = (WorldDestructionEventsComponent)InitializeComponents.EVENTS.get((Object)client.field_1687);
                if (component.getCurrentDestructionEvent() != null) {
                    component.getCurrentDestructionEvent().resetEvent();
                }
                component.setAndStartCurrentDestructionEvent(null, -1L);
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> CameraShakeManager.instancesTicks());
        ClientTickEvents.END_WORLD_TICK.register(clientWorld -> SelectionHandler.tickClientWorld((class_1937)clientWorld));
        ResourceManagerHelper.get((class_3264)class_3264.field_14188).registerReloadListener((IdentifiableResourceReloadListener)new SimpleSynchronousResourceReloadListener(this){

            public class_2960 getFabricId() {
                return DestroyingMinecraft.idOf("after_resources");
            }

            public void method_14491(class_3300 manager) {
                HSVColorTextureManager.init();
            }
        });
    }

    private void updatePostShader() {
        PostProcessingManager postProcessingManager = VeilRenderSystem.renderer().getPostProcessingManager();
        removedPipelines.clear();
        ((PostProcessingManagerAccessor)postProcessingManager).getActuallyActivePipelines().forEach(profileEntry -> removedPipelines.add(profileEntry.getPipeline()));
        for (class_2960 id : removedPipelines) {
            postProcessingManager.remove(id);
        }
        for (PostShader enabledPosts : DestroyingMinecraftConfig.shaderType.getEnabledShaders()) {
            if (postProcessingManager.isActive(enabledPosts.getPost())) continue;
            postProcessingManager.add(enabledPosts.getPost());
        }
    }

    public static class_276 getTranslucentBuffer() {
        AdvancedFbo translucentBuffer = VeilRenderSystem.renderer().getFramebufferManager().getFramebuffer(TRANSLUCENT_FRAMEBUFFER);
        if (translucentBuffer != null && !VeilLevelPerspectiveRenderer.isRenderingPerspective()) {
            return translucentBuffer.toRenderTarget();
        }
        return class_310.method_1551().method_1522();
    }

    private static String getOutputName(class_1921.class_4687 renderType) {
        return VeilRenderType.getName((class_4668)VeilRenderType.getShards((class_1921)renderType).outputState());
    }

    private void enableDynamicBuffers() {
        class_2960 bufferId = Veil.veilPath((String)"forced");
        VeilRenderSystem.renderer().enableBuffers(bufferId, DynamicBufferType.values());
    }

    static {
        removedPipelines = new HashSet<class_2960>(1);
        enabledDynamicBuffers = false;
        destructionDistance = Integer.MAX_VALUE;
    }
}

