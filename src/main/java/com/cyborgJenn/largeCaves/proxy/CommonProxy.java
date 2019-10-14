package com.cyborgJenn.largeCaves.proxy;

import com.cyborgJenn.largeCaves.LargeCaves;
import com.cyborgJenn.largeCaves.block.ModBlocks;
import com.cyborgJenn.largeCaves.compat.ModCompat;
import com.cyborgJenn.largeCaves.entity.EntityLavaMite;
import com.cyborgJenn.largeCaves.entity.EntityNMCreeper;
import com.cyborgJenn.largeCaves.event.entity.EntityAttackHandler;
import com.cyborgJenn.largeCaves.event.player.PlayerTickHandler;
import com.cyborgJenn.largeCaves.event.terrain.CyborgTerrainEventHandler;
import com.cyborgJenn.largeCaves.event.world.CyborgEventHandler;
import com.cyborgJenn.largeCaves.item.ModItems;
import com.cyborgJenn.largeCaves.util.Config;
import com.cyborgJenn.largeCaves.util.OreDict;
import com.cyborgJenn.largeCaves.util.Reference;
import com.cyborgJenn.largeCaves.world.CaveGenType2;
import com.cyborgJenn.largeCaves.world.LargeCaveGen;
import com.cyborgJenn.largeCaves.world.WorldGenerator;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class CommonProxy {

	public static MapGenCaves caveGen;
	public static SoundEvent BEEZ;
	static ResourceLocation location;
	public void preInit(FMLPreInitializationEvent event)
	{
		event.getModMetadata().version = Reference.VERSION;
		Config.init(event.getSuggestedConfigurationFile());
		ModCompat.preInitCompat();
		if (Config.caveGenType == 1){
			caveGen = new LargeCaveGen(); 
		}else if (Config.caveGenType == 2){
			caveGen = new CaveGenType2();
		}
		MinecraftForge.TERRAIN_GEN_BUS.register(new CyborgTerrainEventHandler()); 
		MinecraftForge.EVENT_BUS.register(new CyborgEventHandler());
		MinecraftForge.EVENT_BUS.register(new PlayerTickHandler());
		LargeCaves.logger.info("Registered Event Handler");
		
	}
	public void init(FMLInitializationEvent event){ 
		ModCompat.initCompat();
		OreDict.register();
		MinecraftForge.EVENT_BUS.register(new EntityAttackHandler());
	}
	
	public static void genInit() {
		GameRegistry.registerWorldGenerator(new WorldGenerator(), 0);
		LargeCaves.logger.info("Registered WorldGenerator");
	}
	@SubscribeEvent
	public static void registerMobs(RegistryEvent.Register<EntityEntry> event)
	{
		EntityEntry creeper = EntityEntryBuilder.create()
				.entity(EntityNMCreeper.class)
				.id(new ResourceLocation(Reference.MODID, "NightmareCreeper"), 0)
				.name("nightmarecreeper")
				.egg(0x262425, 0x780510)
				.tracker(60, 3, false)
				.spawn(EnumCreatureType.MONSTER, 35, 1, 1, BiomeDictionary.getBiomes(BiomeDictionary.Type.NETHER))
				.build();
		event.getRegistry().register(creeper);

		EntityEntry lavamite = EntityEntryBuilder.create()
				.entity(EntityLavaMite.class)
				.id(new ResourceLocation(Reference.MODID, "LavaMite"), 1)
				.name("lavamite")
				.egg(0x780510, 0xD16B04)
				.tracker(60, 3, false)
				.spawn(EnumCreatureType.MONSTER, 85, 4, 8, BiomeDictionary.getBiomes(BiomeDictionary.Type.NETHER))
				.build();
		event.getRegistry().register(lavamite);
		LargeCaves.logger.info("Registered Entities");
	}
	
	@SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> event) 
	{	
		location = new ResourceLocation(Reference.MODID, "beez");
		BEEZ = new SoundEvent(location);
		BEEZ.setRegistryName(location);
		event.getRegistry().register(BEEZ);
		
		LargeCaves.logger.info("Common - Registered Sounds");
	}
	public void registerItemRenderer(Item item, int meta, String id) {}
}

