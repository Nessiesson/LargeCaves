package com.cyborgJenn.largeCaves.util;

import com.cyborgJenn.largeCaves.block.ModBlocks;
import com.cyborgJenn.largeCaves.item.ModItems;

import net.minecraftforge.oredict.OreDictionary;

public class OreDict {

	public static void register() {
		OreDictionary.registerOre("blockCyberium", ModBlocks.Cyberium);
		OreDictionary.registerOre("ingotCyberium", ModItems.CyberiumIngot);
		OreDictionary.registerOre("nuggetCyberium", ModItems.CyberiumNugget);
		OreDictionary.registerOre("dustCyberium", ModItems.CyberiumDust);
		
	}

}
