package com.jujutsushenanigans.item;

import com.jujutsushenanigans.JujutsuShenanigans;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

/**
 * Item registration hub.
 * <p>
 * Pattern: Static fields with eager initialization.
 * Call {@link #init()} from the main initializer to trigger class loading.
 */
public class ModItems {

    // ═══════════════════════════════════════════════
    // Example item registration (uncomment when needed):
    //
    // public static final Item CURSED_FINGER = registerItem("cursed_finger",
    //     new Item(new Item.Settings().maxCount(1)));
    // ═══════════════════════════════════════════════

    // ── Registration Helper ──

    /**
     * Registers an item under this mod's namespace.
     */
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM,
                JujutsuShenanigans.id(name), item);
    }

    /**
     * Called from {@link JujutsuShenanigans#onInitialize()} to force
     * static field initialization.
     */
    public static void init() {
        JujutsuShenanigans.LOGGER.debug("Registering items...");
    }
}
