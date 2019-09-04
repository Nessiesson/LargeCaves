package com.cyborgJenn.largeCaves.proxy;

import com.cyborgJenn.largeCaves.block.ModBlocks;
import com.cyborgJenn.largeCaves.compat.ModCompat;
import com.cyborgJenn.largeCaves.item.ModItems;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class RegisterHandler {
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		ModItems.register(event.getRegistry());
		ModBlocks.registerItemBlocks(event.getRegistry());
	}
	@SubscribeEvent
	public static void registerItems(ModelRegistryEvent event)
	{
		ModItems.registerModels();
		ModBlocks.registerModels();
		
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		ModBlocks.registerBlocks(event.getRegistry());
		ModCompat.registerBlocks(event.getRegistry());
	}
}
