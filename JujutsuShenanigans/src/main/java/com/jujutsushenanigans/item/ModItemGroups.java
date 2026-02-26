package com.jujutsushenanigans.item;

import com.jujutsushenanigans.JujutsuShenanigans;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

/**
 * Creative tab (ItemGroup) registration.
 * <p>
 * Creates a dedicated creative tab for all Jujutsu Shenanigans content.
 * Add items to the tab via {@code entries.add(ModItems.YOUR_ITEM)}.
 */
public class ModItemGroups {

    public static final ItemGroup JUJUTSU_GROUP = Registry.register(
            Registries.ITEM_GROUP,
            JujutsuShenanigans.id("jujutsu_group"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(Items.NETHER_STAR)) // Placeholder icon
                    .displayName(Text.translatable("itemGroup.jujutsushenanigans.jujutsu_group"))
                    .entries((displayContext, entries) -> {
                        // ── Add all mod items here ──
                        // entries.add(ModItems.CURSED_FINGER);
                        // entries.add(ModBlocks.CURSED_STONE);
                    })
                    .build()
    );

    /**
     * Called from {@link JujutsuShenanigans#onInitialize()} to force
     * static field initialization.
     */
    public static void init() {
        JujutsuShenanigans.LOGGER.debug("Registering item groups...");
    }
}
