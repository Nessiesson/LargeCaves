package com.cyborgjenn.largecaves.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class BlockCyberium extends BlockBase {

	public BlockCyberium(String name, Material material) {
		super(material, name);
		this.setResistance(2000.0F);
		this.setHardness(50.0F);
		this.setHarvestLevel("pickaxe", 3);
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.DARK_PURPLE + "Caution - this is not a Noxy approved texture.");
	}
}
