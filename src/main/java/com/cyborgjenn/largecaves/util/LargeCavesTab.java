package com.cyborgjenn.largecaves.util;

import com.cyborgjenn.largecaves.item.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class LargeCavesTab extends CreativeTabs {

	public LargeCavesTab(int id, String label) {
		super(id, label);
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(ModItems.CyberiumSword);
	}

}
