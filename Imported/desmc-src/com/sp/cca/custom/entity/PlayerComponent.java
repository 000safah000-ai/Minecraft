/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1113
 *  net.minecraft.class_1657
 *  net.minecraft.class_1934
 *  net.minecraft.class_2394
 *  net.minecraft.class_2398
 *  net.minecraft.class_243
 *  net.minecraft.class_2487
 *  net.minecraft.class_310
 *  net.minecraft.class_3222
 *  net.minecraft.class_7225$class_7874
 *  org.joml.Vector2d
 *  org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent
 *  org.ladysnake.cca.api.v3.component.tick.ClientTickingComponent
 *  org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent
 */
package com.sp.cca.custom.entity;

import com.sp.cca.InitializeComponents;
import com.sp.cca.custom.world.WorldDestructionEventsComponent;
import com.sp.destruction.server.ServerDestructionEvents;
import com.sp.destruction.server.custom.LaserDestructionServer;
import com.sp.entity.ModDamageSources;
import com.sp.networking.ServerPacketManager;
import com.sp.sounds.ModSounds;
import com.sp.sounds.instances.FadingSoundInstance;
import com.sp.util.Noise;
import com.sp.world.playzone.PlayZoneManager;
import net.minecraft.class_1113;
import net.minecraft.class_1657;
import net.minecraft.class_1934;
import net.minecraft.class_2394;
import net.minecraft.class_2398;
import net.minecraft.class_243;
import net.minecraft.class_2487;
import net.minecraft.class_310;
import net.minecraft.class_3222;
import net.minecraft.class_7225;
import org.joml.Vector2d;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ClientTickingComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class PlayerComponent
implements AutoSyncedComponent,
ClientTickingComponent,
ServerTickingComponent {
    private final class_1657 player;
    private class_1934 prevGameMode;
    private boolean insideAPlayZone;
    private long deathTime;
    private boolean isInHole;
    private boolean spawnedEvaporateParticles;
    private boolean isInWaitingRoom;
    private int timeInWaitingRoom;
    private boolean shouldGlitch;
    private int glitchTime;
    private FadingSoundInstance glitchSoundInstance;

    public PlayerComponent(class_1657 player) {
        this.player = player;
        this.insideAPlayZone = true;
        this.isInWaitingRoom = false;
    }

    public boolean isInsideAPlayZone() {
        return this.insideAPlayZone;
    }

    public long getDeathTime() {
        return this.deathTime;
    }

    public boolean isInHole() {
        return this.isInHole;
    }

    public boolean isInWaitingRoom() {
        return this.isInWaitingRoom;
    }

    public void setInWaitingRoom(boolean inWaitingRoom) {
        this.isInWaitingRoom = inWaitingRoom;
    }

    public int getGlitchTime() {
        return this.glitchTime;
    }

    public void readFromNbt(class_2487 nbtCompound, class_7225.class_7874 wrapperLookup) {
        this.isInHole = nbtCompound.method_10577("isInHole");
        this.shouldGlitch = nbtCompound.method_10577("shouldGlitch");
    }

    public void writeToNbt(class_2487 nbtCompound, class_7225.class_7874 wrapperLookup) {
        nbtCompound.method_10556("isInHole", this.isInHole);
        nbtCompound.method_10556("shouldGlitch", this.shouldGlitch);
    }

    public void sync() {
        InitializeComponents.PLAYERS.sync((Object)this.player);
    }

    public void resetPlayer() {
        if (this.prevGameMode != null) {
            ((class_3222)this.player).method_7336(this.prevGameMode);
            this.prevGameMode = null;
        }
        this.sync();
    }

    public void clientTick() {
        if (!this.player.method_24828() || this.player.method_56992() || this.player.method_7325()) {
            this.isInHole = false;
        }
        if (this.isInWaitingRoom) {
            ++this.timeInWaitingRoom;
            if (this.timeInWaitingRoom >= 60) {
                this.setInWaitingRoom(false);
                class_310.method_1551().field_1690.field_1842 = false;
                this.timeInWaitingRoom = 0;
            }
        }
        if (this.player == class_310.method_1551().field_1724) {
            this.updateInAPlayZone();
            if (this.shouldGlitch) {
                if (this.glitchSoundInstance == null || this.glitchSoundInstance.method_4793()) {
                    this.glitchSoundInstance = FadingSoundInstance.ambient(ModSounds.GLITCH, 50, true, 0, 1.0f, 1.0f);
                    class_310.method_1551().method_1483().method_4873((class_1113)this.glitchSoundInstance);
                }
                this.glitchTime = Math.min(this.glitchTime + 1, 100);
            } else {
                if (this.glitchSoundInstance != null) {
                    this.glitchSoundInstance.fadeOut();
                    if (this.glitchSoundInstance.method_4793()) {
                        this.glitchSoundInstance = null;
                    }
                }
                this.glitchTime = Math.max(this.glitchTime - 1, 0);
            }
        }
        if (this.isInHole && !this.spawnedEvaporateParticles) {
            this.player.method_5783(ModSounds.LAVA_DEATH, 1.0f, 1.0f);
            for (int i = 0; i < 100; ++i) {
                double d = this.player.method_59922().method_43059() * 0.2;
                double f = this.player.method_59922().method_43059() * 0.2;
                this.player.method_37908().method_8406((class_2394)class_2398.field_11203, this.player.method_23322(1.0), this.player.method_23319(), this.player.method_23325(1.0), d, 0.0, f);
            }
            this.spawnedEvaporateParticles = true;
        } else if (!this.isInHole) {
            this.spawnedEvaporateParticles = false;
        }
    }

    public void serverTick() {
        if (!this.player.method_24828() || this.player.method_56992() || this.player.method_7325()) {
            this.isInHole = false;
        }
        this.updateInAPlayZone();
        if (ServerDestructionEvents.CRACKS_SERVER.isActive()) {
            this.updateInHole(LaserDestructionServer.laserLength, LaserDestructionServer.crackingTime);
        }
        if (this.isInHole && this.player.method_5805()) {
            this.sync();
            this.player.method_5643(ModDamageSources.of(this.player.method_37908(), ModDamageSources.CRACKS_DAMAGE_TYPE), Float.MAX_VALUE);
        }
        this.updateGlitchTimer();
    }

    private void updateGlitchTimer() {
        WorldDestructionEventsComponent component = (WorldDestructionEventsComponent)InitializeComponents.EVENTS.get((Object)this.player.method_37908());
        class_243 destructionPos = component.getDestructionEventPosition();
        if (component.getCurrentDestructionEvent() == null || !component.getCurrentDestructionEvent().equals(ServerDestructionEvents.BLACK_HOLE_SERVER) || destructionPos.field_1350 - this.player.method_19538().field_1350 < 290.0 && this.player.method_19538().field_1351 - destructionPos.field_1351 < 180.0) {
            if (this.shouldGlitch) {
                this.shouldGlitch = false;
                this.sync();
            }
            this.glitchTime = 0;
        } else {
            if (!this.shouldGlitch) {
                this.shouldGlitch = true;
                this.sync();
            }
            ++this.glitchTime;
            if (this.glitchTime == 100) {
                ServerPacketManager.sendWaitingRoomPacket(this.player, true);
            }
        }
    }

    private void updateInAPlayZone() {
        boolean insidePlayZone;
        if ((this.player.method_56992() || this.player.method_7325()) && !this.isInWaitingRoom) {
            insidePlayZone = true;
            this.deathTime = 0L;
        } else {
            insidePlayZone = PlayZoneManager.isInsideAPlayZone(this.player.method_19538());
            if (!insidePlayZone && this.deathTime == 0L) {
                this.deathTime = System.currentTimeMillis() + 7000L;
            } else if (insidePlayZone) {
                this.deathTime = 0L;
            }
        }
        if (!insidePlayZone && this.deathTime - System.currentTimeMillis() <= 0L) {
            this.player.method_5643(ModDamageSources.of(this.player.method_37908(), ModDamageSources.PLAY_ZONE_DAMAGE_TYPE), Float.MAX_VALUE);
        }
        this.insideAPlayZone = insidePlayZone;
    }

    private void updateInHole(float laserLength, float cracksTime) {
        if (!this.player.method_24828() || this.player.method_56992() || this.player.method_7325()) {
            this.isInHole = false;
            return;
        }
        float time = 0.0f;
        if ((double)laserLength >= 1.0) {
            time = cracksTime * 50.0f + 5.0f;
        }
        class_243 playerPos = this.player.method_19538();
        class_243 position = ((WorldDestructionEventsComponent)InitializeComponents.EVENTS.get((Object)this.player.method_37908())).getDestructionEventPosition();
        float holeSize = (float)(1.0 - Vector2d.distance((double)position.field_1352, (double)position.field_1350, (double)playerPos.field_1352, (double)playerPos.field_1350) / (double)time);
        float noise = Noise.getCrackNoise(new class_243(playerPos.field_1352, 4.0, playerPos.field_1350));
        this.isInHole = noise < holeSize;
    }
}

