package com.cyborgjenn.largecaves.event.entity;

import com.cyborgjenn.largecaves.entity.NMCRreeperEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityAttackHandler {

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void getEntityAttacked(LivingAttackEvent event) {

		if (event.getEntity() instanceof NMCRreeperEntity) {

//			Iterable<ItemStack> equipment = event.getSource().getTrueSource().getEquipmentAndArmor();
//			
//			for (ItemStack stack : equipment){
//				
//				if (ItemStack.areItemsEqual(stack, new ItemStack(ModItems.CyberiumSword))){
//					System.out.println("NMCreeper hit with Cyberium sword");
//				}
//			}
		}
	}
}
