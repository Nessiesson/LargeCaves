package com.cyborgJenn.largeCaves.event.entity;

import com.cyborgJenn.largeCaves.entity.EntityNMCreeper;
import com.cyborgJenn.largeCaves.item.ModItems;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityAttackHandler {

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void getEntityAttacked(LivingAttackEvent event){
		
		if (event.getEntity() instanceof EntityNMCreeper){
			
			Iterable<ItemStack> equipment = event.getSource().getTrueSource().getEquipmentAndArmor();
			
			for (ItemStack stack : equipment){
				
				if (ItemStack.areItemsEqual(stack, new ItemStack(ModItems.CyberiumSword))){
					System.out.println("NMCreeper hit with Cyberium sword");
				}
			}
		}	
	}
}
