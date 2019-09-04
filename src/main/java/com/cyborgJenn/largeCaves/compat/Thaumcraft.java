package com.cyborgJenn.largeCaves.compat;

import com.cyborgJenn.largeCaves.LargeCaves;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.AspectRegistryEvent;

@Optional.Interface(iface="thaumcraft.api.aspects.AspectRegistryEvent",modid="thaumcraft")
@Mod.EventBusSubscriber
public class Thaumcraft {
	
	@Optional.Method(modid="thaumcraft")
	@SubscribeEvent()
	public static void registerAspects(AspectRegistryEvent event) { 
		event.register.registerObjectTag("nuggetCyberium", (new AspectList()).add(Aspect.METAL, 1).add(Aspect.ENERGY, 1).add(Aspect.DARKNESS, 1));
		event.register.registerObjectTag("blockCyberium", (new AspectList()).add(Aspect.METAL, 54).add(Aspect.ENERGY, 54).add(Aspect.DARKNESS, 54));
		LargeCaves.logger.info("Registered Thaumcraft aspects");
	}
}
