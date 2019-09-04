package com.cyborgJenn.largeCaves.util;

import java.util.List;
import java.util.Random;

import com.cyborgJenn.largeCaves.LargeCaves;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public class Utils {
	/**
	 * 
	 * @param posX
	 * @param posZ
	 * @param world
	 * @return
	 */
	public static int findGroundBlockforDungeon(int posX, int posZ, World world)
	{	
		Biome biome = world.getBiome(new BlockPos(posX, 0, posZ));
		List<Block> ground = Lists.newArrayList(Blocks.STONE,Blocks.GRASS, Blocks.DIRT, Blocks.MYCELIUM, Blocks.SAND, Blocks.GRAVEL, biome.topBlock.getBlock());
		for (int i = 255; i >= 25;--i) {
			Block block = world.getBlockState(new BlockPos(posX, i, posZ)).getBlock();
			if (ground.contains(block)) {
				return i - 10;
			}
		}
		return 0;
	}
	public static int findGroundBlockforCaves(int x, int z, ChunkPrimer primer) {
		
		int lvl = primer.findGroundBlockIdx(x, z);
		return lvl;
	}
	
	/**
	 *  Gets a random for y level between ground(from) and  bedrock.
	 * @param from (y level) usually ground
	 * @return
	 */
	public static int getRandomY(int from) 
	{  
		int bedrock = 8;
		if (from < bedrock) {
			return from + new Random().nextInt(Math.abs(bedrock - from));
		}
		return from - new Random().nextInt(Math.abs(bedrock - from));
	}
	private boolean isReplaceable(World world, int x, int y, int z) 
	{
		return true;
	}
}
