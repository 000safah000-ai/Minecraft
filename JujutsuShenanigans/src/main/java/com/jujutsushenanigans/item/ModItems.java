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
    // Item registrations
    // ═══════════════════════════════════════════════

    public static final Item SIX_EYES = registerItem("six_eyes",
            new com.jujutsushenanigans.item.custom.SixEyesItem(new Item.Settings().maxCount(1)));

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
