package com.cyborgJenn.largeCaves.world;

import java.util.List;
import java.util.Random;

import com.cyborgJenn.largeCaves.LargeCaves;
import com.cyborgJenn.largeCaves.block.ModBlocks;
import com.cyborgJenn.largeCaves.util.Config;
import com.cyborgJenn.largeCaves.util.Utils;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class WorldGenerator implements net.minecraftforge.fml.common.IWorldGenerator {
	private final SpiderDenGen spiderDen = new SpiderDenGen();
	private final CreeperCondoGen creeperCondo = new CreeperCondoGen();
	private final WitchAlterGen witchAlter = new WitchAlterGen();
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) 
	{
		switch(world.provider.getDimension()){
		case -1:
			generateNether(world, random, chunkX * 16, chunkZ * 16);
			break;
		case 0:
			generateSurface(world, random, chunkX * 16, chunkZ * 16);
			break;
		case 1:
			generateEnd(world, random, chunkX * 16, chunkZ * 16);
			break;
		}
	}

	private void generateEnd(World world, Random random, int posX, int posZ)
	{

	}

	private void generateSurface(World world, Random random, int posX, int posZ) 
	{
		if (Config.miniDSpiCree < 0.0001) return;
		if (world.getWorldInfo().isMapFeaturesEnabled() && world.getWorldType() != WorldType.FLAT)
		{
			if (!world.isRemote){	
				if (Config.miniDSpiCree > random.nextDouble()){	
					int type = random.nextInt(2);
					switch (type) {
					case 0:
						generateSpiderDen(world, random, posX, posZ);
						break;
					case 1:
						generateCreeperCondo(world, random, posX, posZ);
						break;
					}
				}
				if (random.nextInt(Config.miniDWitch) <= 5){
					//TODO generateWitchAltar(world, random, posX, posZ);
				}
			}
		}
	}

	private void generateNether(World world, Random random, int posX, int posZ) 
	{
		addOreSpawn(ModBlocks.LAVAMITE.getDefaultState(), world, random, posX, posZ, 16, 16, 10, 30, 10, 127, BlockMatcher.forBlock(Blocks.NETHERRACK));
	}

	private void generateCreeperCondo(World world, Random rand, int posX, int posZ)
	{
		int groundY = Utils.findGroundBlockforDungeon(posX, posZ, world);
		int randomY = Utils.getRandomY(groundY);
		creeperCondo.generate(world, rand, new BlockPos(posX, randomY, posZ));
	}

	private void generateWitchAltar(World world, Random rand, int posX, int posZ)
	{
		//BlockPos worldTop  = world.getTopSolidOrLiquidBlock(new BlockPos(posX, 0, posZ));
		//witchAlter.generate(world, rand, new BlockPos(posX, worldTop.getY(), posZ));
	}

	private void generateSpiderDen(World world, Random rand, int posX, int posZ) 
	{
		int groundY = Utils.findGroundBlockforDungeon(posX, posZ, world);
		int randomY = Utils.getRandomY(groundY);
		spiderDen.generate(world, rand, new BlockPos(posX, randomY, posZ));
	}
	
	/**
	 * 
	 * @param block
	 * @param world
	 * @param random
	 * @param chunkXPos
	 * @param chunkZPos
	 * @param maxX
	 * @param maxZ
	 * @param maxVeinSize
	 * @param chance
	 * @param minY
	 * @param maxY
	 * @param blockMatcher
	 */
	private void addOreSpawn(IBlockState block, World world, Random random, int chunkXPos, int chunkZPos, int maxX, int maxZ, int maxVeinSize, int chance, int minY, int maxY, BlockMatcher blockMatcher){
		int diffMinMaxY = maxY - minY;
		for(int x = 0; x < chance; x++)
		{
			int posX = chunkXPos + random.nextInt(maxX);
			int posY = minY + random.nextInt(diffMinMaxY);
			int posZ = chunkZPos + random.nextInt(maxZ);
			(new WorldGenMinable(block, maxVeinSize, blockMatcher)).generate(world, random, new BlockPos(posX, posY, posZ));
		}
	}
}
