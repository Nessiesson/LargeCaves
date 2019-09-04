package com.cyborgJenn.largeCaves.event.terrain;

import java.util.List;

import com.cyborgJenn.largeCaves.LargeCaves;
import com.cyborgJenn.largeCaves.proxy.CommonProxy;
import com.cyborgJenn.largeCaves.util.Config;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.ChunkGeneratorEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CyborgTerrainEventHandler 
{
	public static int dimension;

	/*
	 * Gets the MinecraftForge WorldEvent.
	 */
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void getWorldEvent(WorldEvent event)
	{
		dimension = event.getWorld().provider.getDimension();
	}

	/*
	 * Gets the MinecraftForge MapGen event type
	 */
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void getMapGenEvent(InitMapGenEvent event)
	{
		for (int i = 0;i < Config.dimsToGenerateCaves.length; i++){
			if (dimension == Config.dimsToGenerateCaves[i]){
				switch(event.getType()){
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
