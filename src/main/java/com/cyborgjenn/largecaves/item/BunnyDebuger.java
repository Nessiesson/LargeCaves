package com.cyborgjenn.largecaves.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class BunnyDebuger extends Item {

	public BunnyDebuger() {
		this.setRegistryName("bunny_debuger");
		this.setTranslationKey("bunny_debuger");
		//this.setUnlocalizedName("bunny_debuger");
		this.setCreativeTab(CreativeTabs.TOOLS);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (!worldIn.isRemote) {
			Biome biome = worldIn.getBiome(playerIn.getPosition());
			Block topblock = biome.topBlock.getBlock();
			Block filler = biome.fillerBlock.getBlock();
			int chunkX = worldIn.getChunk(playerIn.getPosition()).x;
			int chunkZ = worldIn.getChunk(playerIn.getPosition()).z;
			playerIn.sendMessage(new TextComponentString("Biome: " + biome.getBiomeName()));
			playerIn.sendMessage(new TextComponentString("TopBlock: " + topblock));
			playerIn.sendMessage(new TextComponentString("Filler: " + filler));
			playerIn.sendMessage(new TextComponentString("Filler: " + filler));
			playerIn.sendMessage(new TextComponentString("Chunk - X:" + chunkX + " Z:" + chunkZ));
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
}
