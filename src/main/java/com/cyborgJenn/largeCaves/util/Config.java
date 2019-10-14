package com.cyborgJenn.largeCaves.util;

import java.io.File;
import java.util.ArrayList;

import com.cyborgJenn.largeCaves.LargeCaves;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class Config {

	// Debug
	public static boolean enableDebugging;
	public static boolean enableCaveDebugging;
	// Entity
	public static double  NMCreeperHealth;
	public static int     NMCreeperBoostTime;
	public static int     NMFuseTime;
	public static int     NMBlastRadius;
	public static boolean NMPotionEffect;
	public static boolean shouldHideInBlock;
	public static boolean doRespectMobGriefing;
	// Caves
	public static int 	 largeNodeMultiplier;
	public static int 	 largeNodeFrequency;
	public static int 	 nodeMultiplier;
	public static int 	 nodeFrequency;
	public static double caveTunnelSizeVar1;
	public static double caveTunnelSizeVar2;
	public static double caveVar3;
	public static int[]  dimsToGenerateCaves;
	public static int    caveGenType;
	// Mini Dungeon
	public static double  miniDSpiCree;
	public static int 	  miniDWitch;
	
	public static void init(File file) {
		Configuration config = new Configuration(file);
		try
		{
			config.load();
			configlargeCaveOptions(config);
			configMiniDungeonOptions(config);
			configEntityTraits(config);
			configDebugOptions(config);
		}
		catch (Exception e)
		{
			LargeCaves.logger.fatal("LargeCaves has had a problem loading its configuration file", new Object[0],e);
		}
		finally
		{
			if (config.hasChanged()) {
				config.save();
			}
		}
		
	}
	private static void configlargeCaveOptions(Configuration config)
	{
		String caveSettings = "Cave Settings";
		config.addCustomCategoryComment(caveSettings, "Setting for Controlling the size and frequency of Caves");
		// Version 1
		largeNodeMultiplier = config.get(caveSettings, "Large Node Multiplier", 16, "MC is 5 by default. A larger number here gives largeNodes more girth").getInt();
		largeNodeFrequency 	= config.get(caveSettings, "Large Node Frequency", 2, "MC is 4 by default. A lower number here makes them more frequent.").getInt();
		nodeMultiplier 		= config.get(caveSettings, "Node Multiplier", 9, "MC is 3 by default. A larger number here gives Nodes more girth").getInt();
		nodeFrequency 		= config.get(caveSettings, "Node Frequency", 8, "MC is 10 by default. A lower number here makes them more frequent.").getInt();
		caveTunnelSizeVar1 	= config.getFloat("Tunnel_Var1", caveSettings, 4F, 0.5F, 100F, "MC is 0.5 by default. A larger number here means more variation in cave tunnel size.");
		caveTunnelSizeVar2 	= config.getFloat("Tunnel_Var2", caveSettings, 8F, 0.5F, 100F, "MC is 0.5 by default. A larger number here means more variation in cave tunnel size.");
		// Version 2
		
		
		// Common
		dimsToGenerateCaves = config.get(caveSettings, "cave", new int[]{0}, "Dimensions to generate caves in. DimID(int) on a new line.").getIntList();
		caveGenType= config.get(caveSettings, "Cave Generator Type", 1, "1 = Standard, 2 = New").getInt();
	}
	private static void configDebugOptions(Configuration config){
		String debug = "Dev Debug Options";
		config.addCustomCategoryComment(debug, "Use these settings to Enable Dev Debug Options");
		enableDebugging 		= config.get(debug, "enable Debugging", false).getBoolean();
		caveVar3 			= config.get(debug, "CaveVar3", 2, "Do not Change this").getDouble();
		enableCaveDebugging = config.get(debug, "CaveGenDebug", 2, "Enable Cave Generator Debugg").getBoolean();
	}
	private static void configMiniDungeonOptions(Configuration config) {
		String miniD = "Mini-Dungeon Options";
		config.addCustomCategoryComment(miniD, "Changes Rarity of Creeper and Spider Mini-Dungeons. (0.0 to 1.0 % chance per chunk)");
		miniDSpiCree = config.get(miniD, "Spider-Creeper",0.05).getDouble();
		miniDWitch   = config.get(miniD, "Witch", 50).getInt();
	}
	private static void configEntityTraits(Configuration config){
        String entityTraits = "Entity Traits";
        NMFuseTime = config.get(entityTraits, "NightmareCreeper FuseTime", 20, "Fuse Time for the Nightmare Creeper").getInt();
        NMBlastRadius = config.get(entityTraits, "NightmareCreeper Blast Radius", 5,"Blast Radius for the Nightmare Creeper, this # is doubled if charged.").getInt();
        NMPotionEffect = config.getBoolean("NightmareCreeper Potion effect", entityTraits, true, "Whether or not the Nightmare Creeper causes potions effect to players caught in the blast radius.");
        NMCreeperBoostTime = config.get(entityTraits, "NightmareCreeper BoostTime", 30, "Time in ticks that this creeper gets a speedboost after being hit by indirect source (arrow, etc).").getInt();
        NMCreeperHealth = config.get(entityTraits,"NightmareCreeper Health", 60.0D , "This Creepers BaseHealth").getDouble();
        shouldHideInBlock = config.getBoolean("LavaMite Should Hide in Netherrack", entityTraits, false, "Whether or not the LavaMite will retreat to netherrack.<< Not Currently Used >>");
        doRespectMobGriefing = config.getBoolean("LavaMiteBlock Respect Griefing Rules", entityTraits, true, "If the Mob Griefing Rules should be followed by the LavaMite Blocks");
	}
}
