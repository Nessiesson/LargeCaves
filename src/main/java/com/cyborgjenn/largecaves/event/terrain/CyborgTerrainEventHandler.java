package com.cyborgjenn.largecaves.event.terrain;

import com.cyborgjenn.largecaves.proxy.CommonProxy;
import com.cyborgjenn.largecaves.util.Config;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CyborgTerrainEventHandler {
	public static int dimension;

	/*
	 * Gets the MinecraftForge WorldEvent.
	 */
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void getWorldEvent(WorldEvent event) {
		dimension = event.getWorld().provider.getDimension();
	}

	/*
	 * Gets the MinecraftForge MapGen event type
	 */
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void getMapGenEvent(InitMapGenEvent event) {
		for (int i = 0; i < Config.dimsToGenerateCaves.length; i++) {
			if (dimension == Config.dimsToGenerateCaves[i]) {
				switch (event.getType()) {
					case CAVE:
						event.setNewGen(CommonProxy.caveGen);
						break;
					default:
						break;
				}
			}
		}
	}
}
