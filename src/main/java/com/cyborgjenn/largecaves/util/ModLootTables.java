package com.cyborgjenn.largecaves.util;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableList;

public class ModLootTables {
	public static final ResourceLocation LOOT_LAVAMITE = register("entities/lava_mite");
	public static final ResourceLocation LOOT_NIGHTMARE = register("entities/nightmare_creeper");

	/**
	 * Register a {@link LootTable} with the specified ID.
	 *
	 * @param id The ID of the LootTable without the testmod3: prefix
	 * @return The ID of the LootTable
	 */
	private static ResourceLocation register(String id) {
		return LootTableList.register(new ResourceLocation(Reference.MODID, id));
	}
}
