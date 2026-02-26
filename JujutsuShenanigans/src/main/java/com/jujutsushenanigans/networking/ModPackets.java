package com.jujutsushenanigans.networking;

import com.jujutsushenanigans.JujutsuShenanigans;

/**
 * Networking registration hub.
 * <p>
 * Architecture (learned from desmc reference):
 * - Each packet is a Java {@code record} implementing {@code CustomPayload}
 * - Each record has a static {@code ID} and {@code CODEC}
 * - Server packets (S2C) are registered in {@link #registerClientPackets()}
 * - Client packets (C2S) are registered in {@link #registerServerPackets()}
 * <p>
 * Usage pattern:
 * <pre>
 * // Define payload
 * public record MyPayload(int value) implements CustomPayload {
 *     public static final CustomPayload.Id&lt;MyPayload&gt; ID =
 *         new CustomPayload.Id&lt;&gt;(JujutsuShenanigans.id("my_payload"));
 *     public static final PacketCodec&lt;PacketByteBuf, MyPayload&gt; CODEC =
 *         PacketCodec.tuple(PacketCodecs.INTEGER, MyPayload::value, MyPayload::new);
 *
 *     &#64;Override
 *     public Id&lt;? extends CustomPayload&gt; getId() { return ID; }
 * }
 *
 * // Register S2C in registerServerPackets():
 * PayloadTypeRegistry.playS2C().register(MyPayload.ID, MyPayload.CODEC);
 *
 * // Handle on client in registerClientPackets():
 * ClientPlayNetworking.registerGlobalReceiver(MyPayload.ID, (payload, context) -&gt; { ... });
 * </pre>
 */
public class ModPackets {

    /**
     * Registers server-side packet handlers (C2S receivers)
     * and S2C payload types.
     * Called from {@link JujutsuShenanigans#onInitialize()}.
     */
    public static void registerServerPackets() {
        JujutsuShenanigans.LOGGER.debug("Registering server packets...");

        // ── Register C2S packet types ──
        // PayloadTypeRegistry.playC2S().register(MyC2SPayload.ID, MyC2SPayload.CODEC);
        // ServerPlayNetworking.registerGlobalReceiver(MyC2SPayload.ID, (payload, context) -> { ... });

        // ── Register S2C packet types (type registration is common-side) ──
        // PayloadTypeRegistry.playS2C().register(MyS2CPayload.ID, MyS2CPayload.CODEC);
    }

    /**
     * Registers client-side packet handlers (S2C receivers).
     * Called from {@link com.jujutsushenanigans.client.JujutsuShenanigansClient#onInitializeClient()}.
     */
    public static void registerClientPackets() {
        JujutsuShenanigans.LOGGER.debug("Registering client packets...");

        // ── Register S2C receivers ──
        // ClientPlayNetworking.registerGlobalReceiver(MyS2CPayload.ID, (payload, context) -> { ... });
    }
}
