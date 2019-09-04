package com.cyborgJenn.largeCaves.item;

import java.util.List;

import javax.annotation.Nullable;

import com.cyborgJenn.largeCaves.LargeCaves;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CyberiumSword extends ItemSword {
	
	private Item.ToolMaterial material;
	private float attackDamage;
	private String name;
	
	public CyberiumSword(Item.ToolMaterial material, String name) {
		super(material);
		this.material = material;
		this.name = name;
		this.setRegistryName(name);
		this.setTranslationKey(name);
		//this.setUnlocalizedName(name);
		this.setCreativeTab(LargeCaves.tabLargeCaves);
	}
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
		tooltip.add(TextFormatting.DARK_PURPLE + "This sword appears to vibrate with some sort of energy");
    }
	public void registerItemModel() {
		LargeCaves.proxy.registerItemRenderer(this, 0, name);
	}
	/**
     * Return whether this item is repairable in an anvil.
     *  
     * @param toRepair the {@code ItemStack} being repaired
     * @param repair the {@code ItemStack} being used to perform the repair
     */
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return repair.getItem() == ModItems.CyberiumIngot ? true : super.getIsRepairable(toRepair, repair);
    }
}
