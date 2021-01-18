package com.cyborgjenn.largecaves.world;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

import java.util.List;

public class CaveGenType2 extends MapGenCaves {
	private static final IBlockState DEFAULT_STATE = Blocks.AIR.getDefaultState();
	public static List<Block> BASE_BLOCKS = Lists.newArrayList(Blocks.STONE, Blocks.DIRT, Blocks.GRASS, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.MYCELIUM, Blocks.SNOW_LAYER);
	private NoiseGeneratorOctaves mainPerlinNoise;
	private NoiseGeneratorPerlin surfaceNoise;

	/**
	 * Primary method for calculating cave areas.
	 */
	@Override
	protected void recursiveGenerate(World worldIn, int chunkX, int chunkZ, int originalX, int originalZ, ChunkPrimer chunkPrimerIn) {
		double blockPosX = (double) (chunkX * 16 + this.rand.nextInt(16)); // Gets random BlockPosX in given ChunkX.
		double blockPosY = (double) this.rand.nextInt(this.rand.nextInt(120) + 8); //Gets random y height between SomeHeight(random,max 120y) and y8.
		double blockPosZ = (double) (chunkZ * 16 + this.rand.nextInt(16)); // Gets random BlockPosZ in given ChunkZ.

		// Big Rooms , low in the world , infrequent.

		// Medium variable sized rooms, more common than big rooms.

		// Tunnels / tendrils 

	}

	@Override
	protected void addTunnel(long seed, int originalX, int originalZ, ChunkPrimer primer, double p_180702_6_, double p_180702_8_, double p_180702_10_, float p_180702_12_, float p_180702_13_, float p_180702_14_, int p_180702_15_, int p_180702_16_, double p_180702_17_) {

	}

	@Override
	protected void addRoom(long seed, int originalX, int originalZ, ChunkPrimer primer, double blockPosX, double blockPosY, double blockPosZ) {

	}

	private void addLargeRoom(long seed, int originalX, int originalZ, ChunkPrimer primer) {

	}

	/**
	 * Does the actual work of removing blocks
	 */
	@Override
	protected void digBlock(ChunkPrimer data, int currentX, int currentY, int currentZ, int chunkX, int chunkZ, boolean foundTop, IBlockState state, IBlockState stateUp) {
		net.minecraft.world.biome.Biome biome = world.getBiome(new BlockPos(currentX + chunkX * 16, 0, currentZ + chunkZ * 16));
		IBlockState top = biome.topBlock;
		IBlockState filler = biome.fillerBlock;

		if (this.canReplaceBlock(state, stateUp) || state.getBlock() == top.getBlock() || state.getBlock() == filler.getBlock()) {
			if (currentY - 1 < 10) {
				data.setBlockState(currentX, currentY, currentZ, BLK_LAVA);
			} else {
				data.setBlockState(currentX, currentY, currentZ, BLK_AIR);

				if (foundTop && data.getBlockState(currentX, currentY - 1, currentZ).getBlock() == filler.getBlock()) {
					data.setBlockState(currentX, currentY - 1, currentZ, top.getBlock().getDefaultState());
				}
			}
		}
	}

	/**
	 * Trying to prevent caves from digging through the surface in Ocean biomes.
	 */
	@Override
	protected boolean isOceanBlock(ChunkPrimer data, int currentX, int currentY, int currentZ, int chunkX, int chunkZ) {
		net.minecraft.block.Block block = data.getBlockState(currentX, currentY, currentZ).getBlock();
		return block == Blocks.FLOWING_WATER || block == Blocks.WATER;
	}

	/**
	 * Attempt to tame caves in certain biomes.
	 * return = true if biome is an exception biome.
	 *
	 * @param biome
	 * @return
	 */
	private boolean isExceptionBiome(net.minecraft.world.biome.Biome biome) // Why are they an exception?
	{
		return false;
	}

	private boolean isTopBlock(ChunkPrimer data, int currentX, int currentY, int currentZ, int chunkX, int chunkZ) {
		net.minecraft.world.biome.Biome biome = world.getBiome(new BlockPos(currentX + chunkX * 16, 0, currentZ + chunkZ * 16));
		IBlockState state = data.getBlockState(currentX, currentY, currentZ);
		// Vanilla == return (isExceptionBiome(biome) ? state.getBlock() == Blocks.GRASS : state.getBlock() == biome.topBlock);
		// Mine ==
		return (isExceptionBiome(biome) ? state.getBlock().getDefaultState() == Blocks.GRASS.getDefaultState() : state.getBlock().getDefaultState() == biome.topBlock);
	}

	/**
	 * Whether or not the block in world can be replaced by a cave.
	 *
	 * @param biome
	 * @param state
	 * @param stateUP
	 * @return
	 */
	protected boolean canReplaceBlock(Biome biome, IBlockState state, IBlockState stateUP) {
		return true;
	}
}
