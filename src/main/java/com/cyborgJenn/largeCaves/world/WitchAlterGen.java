package com.cyborgJenn.largeCaves.world;

import java.util.Random;

import com.cyborgJenn.largeCaves.LargeCaves;
import com.cyborgJenn.largeCaves.util.Config;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.storage.loot.LootTableList;

public class WitchAlterGen extends WorldGenerator {

	protected Block[] GetValidSpawnBlocks()
	{
		return new Block[] { Blocks.STONE, Blocks.GRASS, Blocks.DIRT};
	}

	public boolean LocationIsValidSpawn(World world, BlockPos pos)
	{
		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();
		
		int distanceToAir = 0;
		IBlockState checkID = world.getBlockState(pos);

		while (checkID != Blocks.AIR.getDefaultState())
		{
			distanceToAir++;
			checkID = world.getBlockState(new BlockPos(i, j + distanceToAir, k));
		}

		if (distanceToAir > 0)
		{
			return false;
		}
		j += distanceToAir - 1;

		IBlockState blockID = world.getBlockState(new BlockPos(i, j, k));
		IBlockState blockIDAbove = world.getBlockState(new BlockPos(i, j + 1, k));
		IBlockState blockIDBelow = world.getBlockState(new BlockPos(i, j - 1, k));
		for (Block x : GetValidSpawnBlocks())
		{
			if (blockIDAbove != Blocks.AIR.getDefaultState())
			{
				return false;
			}
			if (blockID == x)
			{
				return true;
			} else if (blockID == Blocks.SNOW.getDefaultState() && blockIDBelow == x)
			{
				return true;
			}
		}
		return false;
	}
	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		int posX = pos.getX();
		int posY = pos.getY();
		int posZ = pos.getZ();
		// check that each corner is one of the valid spawn blocks
		if (!LocationIsValidSpawn(world, pos) || !LocationIsValidSpawn(world, new BlockPos(posX + 9, posY, posZ)) || !LocationIsValidSpawn(world, new BlockPos(posX + 9, posY, posZ + 9)) || !LocationIsValidSpawn(world, new BlockPos(posX, posY, posZ + 9)))
		{
			return false;
		}
		createWitchAltar(world, pos);
		return true;
	}
	private void createWitchAltar(World world, BlockPos pos){
		int posX = pos.getX();
		int posY = pos.getY();
		int posZ = pos.getZ();
		IBlockState NETHERBRICK = Blocks.NETHER_BRICK.getDefaultState();
		IBlockState AIR = Blocks.AIR.getDefaultState();
		IBlockState OBSIDIAN = Blocks.OBSIDIAN.getDefaultState();
		
		if (Config.enableDebugging){
			LargeCaves.logger.info("Generated Altar at : " + posX + " " + posY + " " + posZ);
		}
		posY = posY - 1;
		// Base Layer
		world.setBlockState(new BlockPos(posX + 3, posY, posZ), NETHERBRICK);
		world.setBlockState(new BlockPos(posX + 4, posY, posZ), Blocks.NETHER_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
		world.setBlockState(new BlockPos(posX + 5, posY, posZ), NETHERBRICK);
		world.setBlockState(new BlockPos(posX + 2, posY, posZ + 1), NETHERBRICK);
		world.setBlockState(new BlockPos(posX + 3, posY, posZ + 1), Blocks.NETHER_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
		world.setBlockState(new BlockPos(posX + 4, posY, posZ + 1), AIR);
		world.setBlockState(new BlockPos(posX + 5, posY, posZ + 1), Blocks.NETHER_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
		world.setBlockState(new BlockPos(posX + 6, posY, posZ + 1), NETHERBRICK);
		world.setBlockState(new BlockPos(posX + 1, posY, posZ + 2), NETHERBRICK);
		world.setBlockState(new BlockPos(posX + 2, posY, posZ + 2), Blocks.NETHER_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH));
		world.setBlockState(new BlockPos(posX + 3, posY, posZ + 2), AIR);
		world.setBlockState(new BlockPos(posX + 4, posY, posZ + 2), AIR);
		world.setBlockState(new BlockPos(posX + 5, posY, posZ + 2), AIR);
		world.setBlockState(new BlockPos(posX + 6, posY, posZ + 2), Blocks.NETHER_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST));
		world.setBlockState(new BlockPos(posX + 7, posY, posZ + 2), NETHERBRICK);
		world.setBlockState(new BlockPos(posX + 1, posY, posZ + 3), Blocks.NETHER_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH));
		world.setBlockState(new BlockPos(posX + 2, posY, posZ + 3), AIR);
		world.setBlockState(new BlockPos(posX + 3, posY, posZ + 3), AIR);
		world.setBlockState(new BlockPos(posX + 4, posY, posZ + 3), Blocks.CAULDRON.getDefaultState());
		world.setBlockState(new BlockPos(posX + 5, posY, posZ + 3), AIR);
		world.setBlockState(new BlockPos(posX + 6, posY, posZ + 3), AIR);
		world.setBlockState(new BlockPos(posX + 7, posY, posZ + 3), Blocks.NETHER_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST));
		world.setBlockState(new BlockPos(posX + 1, posY, posZ + 4), NETHERBRICK);
		world.setBlockState(new BlockPos(posX + 2, posY, posZ + 4), Blocks.NETHER_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH));
		world.setBlockState(new BlockPos(posX + 3, posY, posZ + 4), AIR);
		world.setBlockState(new BlockPos(posX + 4, posY, posZ + 4), AIR);
		world.setBlockState(new BlockPos(posX + 5, posY, posZ + 4), AIR);
		world.setBlockState(new BlockPos(posX + 6, posY, posZ + 4), Blocks.NETHER_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST));
		world.setBlockState(new BlockPos(posX + 7, posY, posZ + 4), NETHERBRICK);
		world.setBlockState(new BlockPos(posX + 2, posY, posZ + 5), NETHERBRICK);
		world.setBlockState(new BlockPos(posX + 3, posY, posZ + 5), Blocks.NETHER_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST));
		world.setBlockState(new BlockPos(posX + 4, posY, posZ + 5), AIR);
		world.setBlockState(new BlockPos(posX + 5, posY, posZ + 5), Blocks.NETHER_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST));
		world.setBlockState(new BlockPos(posX + 6, posY, posZ + 5), NETHERBRICK);
		world.setBlockState(new BlockPos(posX + 3, posY, posZ + 6), NETHERBRICK);
		world.setBlockState(new BlockPos(posX + 4, posY, posZ + 6), Blocks.NETHER_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST));
		world.setBlockState(new BlockPos(posX + 5, posY, posZ + 6), NETHERBRICK);

		// Layer 1 Down
		world.setBlockState(new BlockPos(posX + 4, posY - 1, posZ + 1), OBSIDIAN);
		world.setBlockState(new BlockPos(posX + 3, posY - 1, posZ + 2), OBSIDIAN);
		world.setBlockState(new BlockPos(posX + 4, posY - 1, posZ + 2), OBSIDIAN);
		world.setBlockState(new BlockPos(posX + 5, posY - 1, posZ + 2), OBSIDIAN);
		world.setBlockState(new BlockPos(posX + 2, posY - 1, posZ + 3), OBSIDIAN);
		world.setBlockState(new BlockPos(posX + 3, posY - 1, posZ + 3), OBSIDIAN);
		placeSpawner(world, new BlockPos(posX + 4, posY - 1, posZ + 3));
		world.setBlockState(new BlockPos(posX + 5, posY - 1, posZ + 3), OBSIDIAN);
		world.setBlockState(new BlockPos(posX + 6, posY - 1, posZ + 3), OBSIDIAN);
		world.setBlockState(new BlockPos(posX + 3, posY - 1, posZ + 4), OBSIDIAN);
		world.setBlockState(new BlockPos(posX + 4, posY - 1, posZ + 4), OBSIDIAN);
		world.setBlockState(new BlockPos(posX + 5, posY - 1, posZ + 4), OBSIDIAN);
		world.setBlockState(new BlockPos(posX + 4, posY - 1, posZ + 5), OBSIDIAN);

		// Layer 2 Down
		world.setBlockState(new BlockPos(posX + 3, posY - 2, posZ + 2), OBSIDIAN);
		world.setBlockState(new BlockPos(posX + 4, posY - 2, posZ + 2), OBSIDIAN);
		world.setBlockState(new BlockPos(posX + 5, posY - 2, posZ + 2), OBSIDIAN);
		world.setBlockState(new BlockPos(posX + 3, posY - 2, posZ + 3), OBSIDIAN);
		placeChest(world, new BlockPos(posX + 4, posY - 2, posZ + 3));
		world.setBlockState(new BlockPos(posX + 5, posY - 2, posZ + 3), OBSIDIAN);
		world.setBlockState(new BlockPos(posX + 3, posY - 2, posZ + 4), OBSIDIAN);
		world.setBlockState(new BlockPos(posX + 4, posY - 2, posZ + 4), OBSIDIAN);
		world.setBlockState(new BlockPos(posX + 5, posY - 2, posZ + 4), OBSIDIAN);

		// Layer 3 down
		world.setBlockState(new BlockPos(posX + 4, posY - 3, posZ + 3), OBSIDIAN);

		// Layer 1 up and 2 up
		world.setBlockState(new BlockPos(posX + 2, posY + 1, posZ + 1), NETHERBRICK);
		world.setBlockState(new BlockPos(posX + 6, posY + 1, posZ + 1), NETHERBRICK);
		world.setBlockState(new BlockPos(posX + 2, posY + 1, posZ + 5), NETHERBRICK);
		world.setBlockState(new BlockPos(posX + 6, posY + 1, posZ + 5), NETHERBRICK);

		world.setBlockState(new BlockPos(posX + 2, posY + 2, posZ + 1), NETHERBRICK);
		world.setBlockState(new BlockPos(posX + 6, posY + 2, posZ + 1), NETHERBRICK);
		world.setBlockState(new BlockPos(posX + 2, posY + 2, posZ + 5), NETHERBRICK);
		world.setBlockState(new BlockPos(posX + 6, posY + 2, posZ + 5), NETHERBRICK);
		// Layer 3 up
		world.setBlockState(new BlockPos(posX + 2, posY + 3, posZ + 1), Blocks.STONEBRICK.getDefaultState());
		world.setBlockState(new BlockPos(posX + 6, posY + 3, posZ + 1), Blocks.STONEBRICK.getDefaultState());
		world.setBlockState(new BlockPos(posX + 2, posY + 3, posZ + 5), Blocks.STONEBRICK.getDefaultState());
		world.setBlockState(new BlockPos(posX + 6, posY + 3, posZ + 5), Blocks.STONEBRICK.getDefaultState());

		EntityWitch entitywitch = new EntityWitch(world);
		entitywitch.setPosition(posX + 3, posY + 1, posZ + 3);
		world.spawnEntity(entitywitch);

	}
	private void placeSpawner(World world, BlockPos pos) {
		world.setBlockState(pos, Blocks.MOB_SPAWNER.getDefaultState());
		TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)world.getTileEntity(pos);

		if (tileentitymobspawner != null)
		{
			tileentitymobspawner.getSpawnerBaseLogic().setEntityId(EntityList.getKey(EntityWitch.class));
			NBTTagCompound nbt = new NBTTagCompound();
			tileentitymobspawner.writeToNBT(nbt);
			nbt.setShort("MinSpawnDelay",(short)50);
			nbt.setShort("MaxSpawnDelay",(short)200);
			nbt.setShort("MaxNearbyEntities",(short)12);
			tileentitymobspawner.readFromNBT(nbt);

		}
		else
		{
			LargeCaves.logger.error("Failed to fetch mob spawner entity at (" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + ")");
		}

	}

	private void placeChest(World world, BlockPos pos){
		Random rand = new Random();

		world.setBlockState(pos, Blocks.CHEST.getDefaultState());
		TileEntityChest tileentitychest = (TileEntityChest)world.getTileEntity(pos);

		if (tileentitychest != null)
		{
			((TileEntityChest)tileentitychest).setLootTable(LootTableList.CHESTS_SIMPLE_DUNGEON, rand.nextLong());
		}
	}
}
