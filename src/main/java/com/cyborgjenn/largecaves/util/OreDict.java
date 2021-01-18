package com.cyborgjenn.largecaves.util;

import com.cyborgjenn.largecaves.block.ModBlocks;
import com.cyborgjenn.largecaves.item.ModItems;
import net.minecraftforge.oredict.OreDictionary;

public class OreDict {

	public static void register() {
		OreDictionary.registerOre("blockCyberium", ModBlocks.Cyberium);
		OreDictionary.registerOre("ingotCyberium", ModItems.CyberiumIngot);
		OreDictionary.registerOre("nuggetCyberium", ModItems.CyberiumNugget);
		OreDictionary.registerOre("dustCyberium", ModItems.CyberiumDust);

	}

}
