package com.example.autofakeplayer;

import com.mojang.authlib.GameProfile;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

public class AutoFakePlayerMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("autofakeplayer");

    private Config config;
    private int tickCounter = 0;
    private int nextActionTick = 600;
    private Random random = new Random();
    private boolean isLooking = false;
    private int lookTicksRemaining = 0;
    private float targetYaw = 0;
    private float targetPitch = 0;
    private float startYaw = 0;
    private float startPitch = 0;
    
    // Recovery
    private int offlineTicks = 0;

    // Startup Delay
    private boolean serverStarted = false;
    private int delayTicks = 0;
    private static final int SPAWN_DELAY_TICKS = 600; // 30 seconds (20 ticks/sec)

    @Override
    public void onInitialize() {
        LOGGER.info("AutoFakePlayer Mod Initializing on server side!");

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            loadConfig(server.getRunDirectory().toPath().resolve("config").resolve("autofakeplayer.properties"));
            serverStarted = true;
            LOGGER.info("AutoFakePlayer successfully loaded config. Waiting 30 seconds before spawning player...");
        });

        ServerTickEvents.START_SERVER_TICK.register(server -> {
            if (!serverStarted || config == null || !config.enabled) return;

            if (delayTicks < SPAWN_DELAY_TICKS) {
                delayTicks++;
                if (delayTicks == SPAWN_DELAY_TICKS) {
                    LOGGER.info("Startup delay complete (30s). Spawning fake player...");
                    ensureFakePlayer(server);
                }
                return;
            }

            // Ensure the player is alive/present every ~1 minute if it gets disconnected
            ServerPlayerEntity player = server.getPlayerManager().getPlayer(config.playerName);
            if (player == null) {
                offlineTicks++;
                if (offlineTicks > 1200) { // 1 minute (20 ticks * 60)
                    LOGGER.info("Fake player disconnected, recreating...");
                    ensureFakePlayer(server);
                    offlineTicks = 0;
                }
                return;
            } else {
                offlineTicks = 0;
            }

            if (!(player instanceof FakePlayer)) {
                // Not a fake player, shouldn't interfere but warn?
                return;
            }
            
            // Prevent taking damage
            player.getAbilities().invulnerable = true;

            // Handle smooth look transition
            if (isLooking && lookTicksRemaining > 0) {
                lookTicksRemaining--;
                float progress = 1.0f - ((float)lookTicksRemaining / 20.0f);
                float curYaw = startYaw + (targetYaw - startYaw) * progress;
                float curPitch = startPitch + (targetPitch - startPitch) * progress;
                player.setYaw(curYaw);
                player.setPitch(curPitch);
                if (lookTicksRemaining == 0) isLooking = false;
            }

            tickCounter++;
            if (tickCounter >= nextActionTick) {
                tickCounter = 0;
                nextActionTick = config.minInterval + random.nextInt(Math.max(1, config.maxInterval - config.minInterval));
                
                performRandomAction((FakePlayer) player, server);
            }
        });
    }

    private void ensureFakePlayer(MinecraftServer server) {
        if (server.getPlayerManager().getPlayer(config.playerName) != null) {
            return; // Player already exists
        }

        ServerWorld world = server.getOverworld();
        GameProfile profile = new GameProfile(UUID.nameUUIDFromBytes(("OfflinePlayer:" + config.playerName).getBytes()), config.playerName);

        FakePlayer player = FakePlayer.get(world, profile);
        
        // Setup initial state
        player.setPosition(config.spawnX, config.spawnY, config.spawnZ);
        player.setYaw(config.spawnYaw);
        player.setPitch(config.spawnPitch);
        player.changeGameMode(GameMode.CREATIVE);
        player.getAbilities().allowFlying = true;
        player.getAbilities().invulnerable = true;
        player.sendAbilitiesUpdate();
        
        LOGGER.info("FakePlayer {} created at X:{} Y:{} Z:{}", config.playerName, config.spawnX, config.spawnY, config.spawnZ);
    }

    private void performRandomAction(FakePlayer player, MinecraftServer server) {
        int action = random.nextInt(4);
        
        if (action == 0 && config.enableMovement) {
            // Movement Action
            double dx = (random.nextDouble() - 0.5) * 6.0;
            double dz = (random.nextDouble() - 0.5) * 6.0;
            player.setPosition(player.getX() + dx, player.getY(), player.getZ() + dz);
        } else if (action == 1 && config.enableJump) {
            // Jump Action
            player.setOnGround(true);
            player.addVelocity(0, 0.42, 0); // basic jump strength
            player.velocityModified = true;
        } else if (action == 2 && config.enableLook) {
            // Look Action
            isLooking = true;
            lookTicksRemaining = 20;
            startYaw = player.getYaw();
            startPitch = player.getPitch();
            targetYaw = startYaw + (random.nextFloat() - 0.5f) * 180f;
            targetPitch = (random.nextFloat() - 0.5f) * 90f;
        } else if (action == 3 && config.enableSwing) {
            // Swing Action
            player.swingHand(Hand.MAIN_HAND);
        }
    }

    private void loadConfig(Path path) {
        config = new Config();
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Properties props = new Properties();
                props.setProperty("enabled", "true");
                props.setProperty("playerName", "ServerKeepAlive");
                props.setProperty("spawn.x", "0.0");
                props.setProperty("spawn.y", "200.0");
                props.setProperty("spawn.z", "0.0");
                props.setProperty("gamemode", "creative");
                props.setProperty("minInterval", "600");
                props.setProperty("maxInterval", "1200");
                props.setProperty("enableMovement", "true");
                props.setProperty("enableJump", "true");
                props.setProperty("enableLook", "true");
                props.setProperty("enableSwing", "true");

                try (OutputStream os = Files.newOutputStream(path)) {
                    props.store(os, "AutoFakePlayer Configuration");
                }
            } else {
                Properties props = new Properties();
                try (InputStream is = Files.newInputStream(path)) {
                    props.load(is);
                }
                config.enabled = Boolean.parseBoolean(props.getProperty("enabled", "true"));
                config.playerName = props.getProperty("playerName", "ServerKeepAlive");
                config.spawnX = Double.parseDouble(props.getProperty("spawn.x", "0.0"));
                config.spawnY = Double.parseDouble(props.getProperty("spawn.y", "200.0"));
                config.spawnZ = Double.parseDouble(props.getProperty("spawn.z", "0.0"));
                config.minInterval = Integer.parseInt(props.getProperty("minInterval", "600"));
                config.maxInterval = Integer.parseInt(props.getProperty("maxInterval", "1200"));
                config.enableMovement = Boolean.parseBoolean(props.getProperty("enableMovement", "true"));
                config.enableJump = Boolean.parseBoolean(props.getProperty("enableJump", "true"));
                config.enableLook = Boolean.parseBoolean(props.getProperty("enableLook", "true"));
                config.enableSwing = Boolean.parseBoolean(props.getProperty("enableSwing", "true"));
            }
        } catch (Exception e) {
            LOGGER.error("Failed to load / create config, using defaults.", e);
        }
    }

    private static class Config {
        public boolean enabled = true;
        public String playerName = "ServerKeepAlive";
        public double spawnX = 0;
        public double spawnY = 200;
        public double spawnZ = 0;
        public float spawnYaw = 0;
        public float spawnPitch = 0;
        public int minInterval = 600;
        public int maxInterval = 1200;
        public boolean enableMovement = true;
        public boolean enableJump = true;
        public boolean enableLook = true;
        public boolean enableSwing = true;
    }
}
