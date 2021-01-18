package com.cyborgjenn.largecaves.block;

import com.cyborgjenn.largecaves.LargeCaves;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class BlockBase extends Block {
	protected String name;

	public BlockBase(Material material, String name) {
		super(material);
		this.name = name;
		setTranslationKey(name);
		setRegistryName(name);
		setCreativeTab(LargeCaves.tabLargeCaves);
	}

	public void registerItemBlockModel(Item itemblock) {
		LargeCaves.proxy.registerItemRenderer(itemblock, 0, name);
	}

	public Item createItemBlock() {
		BlockItem itemBlock = new BlockItem(this);
		itemBlock.setRegistryName(getRegistryName());
		return itemBlock;
	}
}
