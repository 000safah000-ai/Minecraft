package com.jujutsushenanigans.sound;

import com.jujutsushenanigans.JujutsuShenanigans;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

/**
 * Sound event registration hub.
 * <p>
 * All sound files go in: {@code assets/jujutsushenanigans/sounds/}
 * Sound definitions go in: {@code assets/jujutsushenanigans/sounds.json}
 * <p>
 * Fail-Safe Audio Policy (from instructions.md §2.5):
 * - Sound files are OPTIONAL.
 * - Code must check existence before playback.
 * - Missing files cause silent no-ops, never crashes.
 */
public class ModSounds {

    // ═══════════════════════════════════════════════
    // Sound event registrations will go here.
    // Example:
    //
    // public static final SoundEvent DOMAIN_EXPANSION = registerSoundEvent("domain_expansion");
    // public static final SoundEvent CURSED_ENERGY_CHARGE = registerSoundEvent("cursed_energy_charge");
    // public static final SoundEvent HOLLOW_PURPLE = registerSoundEvent("hollow_purple");
    // ═══════════════════════════════════════════════

    // ── Registration Helper ──

    /**
     * Registers a sound event. The sound file path is derived from the name:
     * {@code assets/jujutsushenanigans/sounds/<name>.ogg}
     */
    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = JujutsuShenanigans.id(name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    /**
     * Called from {@link JujutsuShenanigans#onInitialize()} to force
     * static field initialization.
     */
    public static void init() {
        JujutsuShenanigans.LOGGER.debug("Registering sounds...");
    }
}
