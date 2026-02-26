package com.jujutsushenanigans.entity;

import com.jujutsushenanigans.JujutsuShenanigans;

/**
 * Entity type registration hub.
 * <p>
 * Pattern: Static fields with eager initialization.
 * Call {@link #init()} from the main initializer to trigger class loading.
 * <p>
 * Entity types are registered via:
 * <pre>
 * public static final EntityType&lt;MyEntity&gt; MY_ENTITY =
 *     Registry.register(Registries.ENTITY_TYPE, JujutsuShenanigans.id("my_entity"),
 *         EntityType.Builder.create(MyEntity::new, SpawnGroup.MISC)
 *             .dimensions(0.6f, 1.8f)
 *             .build());
 * </pre>
 */
public class ModEntities {

    // ═══════════════════════════════════════════════
    // Entity type registrations will go here.
    // Example:
    //
    // public static final EntityType<CursedSpiritEntity> CURSED_SPIRIT =
    //     Registry.register(Registries.ENTITY_TYPE,
    //         JujutsuShenanigans.id("cursed_spirit"),
    //         EntityType.Builder.create(CursedSpiritEntity::new, SpawnGroup.MONSTER)
    //             .dimensions(0.6f, 1.8f)
    //             .build());
    // ═══════════════════════════════════════════════

    /**
     * Called from {@link JujutsuShenanigans#onInitialize()} to force
     * static field initialization.
     */
    public static void init() {
        JujutsuShenanigans.LOGGER.debug("Registering entities...");
    }
}
