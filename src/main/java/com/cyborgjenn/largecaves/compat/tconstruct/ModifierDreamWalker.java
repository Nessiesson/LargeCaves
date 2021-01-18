package com.cyborgjenn.largecaves.compat.tconstruct;

import com.cyborgjenn.largecaves.entity.NMCRreeperEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class ModifierDreamWalker extends ModifierTrait {

	public ModifierDreamWalker() {
		super("dreamwalker", 0xFF8e2ec1, 3, 36);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		System.out.println("NMCreeper was afterhit");
		if (wasHit) {
			if (target instanceof NMCRreeperEntity) {
				System.out.println("NMCreeper was hit");
				ModifierNBT data = new ModifierNBT(TinkerUtil.getModifierTag(tool, getModifierIdentifier()));
				target.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("levitation"), 20 + (random.nextInt((data.level) * 2) * 20), data.level));
			}
		}
	}
}
