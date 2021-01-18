package com.cyborgjenn.largecaves.block;

import com.cyborgjenn.largecaves.LargeCaves;
import com.cyborgjenn.largecaves.compat.ModCompat;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {

	public static final BlockLavaMite LAVAMITE = new BlockLavaMite("BlockLavaMite");
	public static Material CYBERIUM = new Material(MapColor.BLUE_STAINED_HARDENED_CLAY);
	public static final BlockCyberium Cyberium = new BlockCyberium("block_cyberium", CYBERIUM);


	public static void registerBlocks(IForgeRegistry<Block> registry) {
		registry.register(Cyberium);
		registry.register(LAVAMITE);

		LargeCaves.logger.info("Registered Blocks");
	}

	public static void registerItemBlocks(IForgeRegistry<Item> registry) {
		registry.register(LAVAMITE.createItemBlock());
		registry.register(Cyberium.createItemBlock());
		ModCompat.registerItemBlocks(registry);
		LargeCaves.logger.info("Registered ItemBlocks");

	}

	public static void registerModels() {
		LAVAMITE.registerItemBlockModel(Item.getItemFromBlock(LAVAMITE));
		Cyberium.registerItemBlockModel(Item.getItemFromBlock(Cyberium));
		ModCompat.registerBlockModels();
		LargeCaves.logger.info("Registered Block Models");
	}
}
