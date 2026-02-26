/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  foundry.veil.api.client.render.shader.program.ShaderProgram
 */
package com.sp.destruction.client;

import com.sp.destruction.DestructionEvent;
import com.sp.destruction.DestructionType;
import com.sp.render.postshaders.PostShader;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import java.util.Vector;

public abstract class ClientDestructionEvent
extends DestructionEvent {
    private static final Vector<DestructionEvent> clientInstances = new Vector();
    private final PostShader postShader;

    public ClientDestructionEvent(DestructionType destructionType, PostShader postShader, int duration) {
        super(destructionType, duration, true);
        clientInstances.add(this);
        this.postShader = postShader;
        this.postShader.setUniformCallback(this::setUniforms);
    }

    @Override
    protected void skipKeyframe() {
    }

    public PostShader getPostShader() {
        return this.postShader;
    }

    public abstract void setUniforms(ShaderProgram var1, float var2);

    public static <T extends ClientDestructionEvent> T register(T event) {
        clientInstances.add(event);
        return event;
    }

    public static synchronized Vector<DestructionEvent> getAllClientInstances() {
        return (Vector)clientInstances.clone();
    }
}

