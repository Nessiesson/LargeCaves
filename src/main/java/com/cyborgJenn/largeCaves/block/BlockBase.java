package com.cyborgJenn.largeCaves.block;

import com.cyborgJenn.largeCaves.LargeCaves;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block {

	protected String name;
	
	public BlockBase(Material material, String name) {
		super(material);
		this.name = name;
		setTranslationKey(name);
		//setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(LargeCaves.tabLargeCaves);
	}
	
	public void registerItemBlockModel(Item itemblock){
		LargeCaves.proxy.registerItemRenderer(itemblock, 0, name);
	}
	
	public Item createItemBlock() {
		ItemBlock itemBlock = new ItemBlock(this);
		itemBlock.setRegistryName(getRegistryName());
		return itemBlock;
	}
	
	@Override
	public BlockBase setCreativeTab(CreativeTabs tab){
		super.setCreativeTab(tab);
		
		return this;
	}
}
