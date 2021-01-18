package com.cyborgjenn.largecaves.item;

import com.cyborgjenn.largecaves.LargeCaves;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class CyberiumPick extends ItemPickaxe {

	private final String name;

	public CyberiumPick(ToolMaterial material, String name) {
		super(material);
		this.name = name;
		this.setRegistryName(name);
		this.setTranslationKey(name);
		//this.setUnlocalizedName(name);
		this.setCreativeTab(LargeCaves.tabLargeCaves);
		this.setHarvestLevel("pickaxe", 4);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.DARK_PURPLE + "I wonder what this thing does?");
	}

	public void registerItemModel() {
		LargeCaves.proxy.registerItemRenderer(this, 0, name);
	}

	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return repair.getItem() == ModItems.CyberiumIngot ? true : super.getIsRepairable(toRepair, repair);
	}
}
