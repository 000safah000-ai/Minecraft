package com.jujutsushenanigans.block;

import com.jujutsushenanigans.JujutsuShenanigans;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

/**
 * Block registration hub.
 * <p>
 * Pattern: Static fields with eager initialization.
 * Call {@link #init()} from the main initializer to trigger class loading.
 * <p>
 * Each block automatically gets a corresponding {@link BlockItem}
 * registered via {@link #registerBlock(String, Block)}.
 */
public class ModBlocks {

    // ═══════════════════════════════════════════════
    // Example block registration (uncomment when needed):
    //
    // public static final Block CURSED_STONE = registerBlock("cursed_stone",
    //     new Block(AbstractBlock.Settings.create()
    //         .strength(3.0f, 6.0f)
    //         .requiresTool()));
    // ═══════════════════════════════════════════════

    // ── Registration Helpers ──

    /**
     * Registers a block and its corresponding block item.
     */
    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK,
                JujutsuShenanigans.id(name), block);
    }

    /**
     * Registers a BlockItem for the given block.
     */
    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM,
                JujutsuShenanigans.id(name),
                new BlockItem(block, new Item.Settings()));
    }

    /**
     * Called from {@link JujutsuShenanigans#onInitialize()} to force
     * static field initialization and trigger all registrations.
     */
    public static void init() {
        JujutsuShenanigans.LOGGER.debug("Registering blocks...");
    }
}
