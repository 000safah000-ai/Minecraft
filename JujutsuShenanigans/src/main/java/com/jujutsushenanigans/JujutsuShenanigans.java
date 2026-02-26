package com.jujutsushenanigans;

import com.jujutsushenanigans.block.ModBlocks;
import com.jujutsushenanigans.entity.ModEntities;
import com.jujutsushenanigans.item.ModItemGroups;
import com.jujutsushenanigans.item.ModItems;
import com.jujutsushenanigans.networking.ModPackets;
import com.jujutsushenanigans.sound.ModSounds;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Jujutsu Shenanigans — Main Server/Common Initializer.
 * <p>
 * Registers all common-side content: blocks, items, entities,
 * sounds, networking, commands, and game rules.
 * <p>
 * Architecture inspired by the desmc reference mod but built
 * from scratch with a focus on AAA-quality visual effects using
 * Veil as the primary rendering engine.
 */
public class JujutsuShenanigans implements ModInitializer {

    public static final String MOD_ID = "jujutsushenanigans";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    /**
     * Creates a namespaced {@link Identifier} for this mod.
     *
     * @param path the resource path
     * @return an Identifier with this mod's namespace
     */
    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Jujutsu Shenanigans...");

        // ── Registration Order Matters ──
        // Blocks before Items (block items reference blocks)
        // Items before ItemGroups (groups reference items)
        // Entities after core registries
        // Sounds can be registered at any point
        // Networking last (may reference registered content)

        ModBlocks.init();
        ModItems.init();
        ModItemGroups.init();
        ModEntities.init();
        ModSounds.init();
        ModPackets.registerServerPackets();

        LOGGER.info("Jujutsu Shenanigans initialized successfully.");
    }
}
