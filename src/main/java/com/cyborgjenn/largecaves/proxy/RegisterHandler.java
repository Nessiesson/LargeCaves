package com.cyborgjenn.largecaves.proxy;

import com.cyborgjenn.largecaves.block.ModBlocks;
import com.cyborgjenn.largecaves.compat.ModCompat;
import com.cyborgjenn.largecaves.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class RegisterHandler {

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		ModItems.register(event.getRegistry());
		ModBlocks.registerItemBlocks(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerItems(ModelRegistryEvent event) {
		ModItems.registerModels();
		ModBlocks.registerModels();

	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		ModBlocks.registerBlocks(event.getRegistry());
		ModCompat.registerBlocks(event.getRegistry());
	}
}
