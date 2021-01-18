package com.cyborgjenn.largecaves.world;

import com.cyborgjenn.largecaves.LargeCaves;
import com.cyborgjenn.largecaves.entity.NMCRreeperEntity;
import com.cyborgjenn.largecaves.util.Config;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

import java.util.Random;

public class CreeperCondoGen extends WorldGenerator {
	Random rand = new Random();

	protected Block[] GetValidSpawnBlocks() {
		return new Block[]{Blocks.STONE, Blocks.GRASS, Blocks.DIRT};
	}

	public boolean LocationIsValidSpawn(World world, int i, int j, int k) {
		BlockPos pos = new BlockPos(i, j - 1, k);
		Block block = world.getBlockState(pos).getBlock();
		return block != Blocks.AIR.getDefaultState().getBlock();
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		// check that each corner is one of the valid spawn blocks
		int X = pos.getX();
		int Y = (pos.getY() + 6);
		int Z = pos.getZ();
		if (!LocationIsValidSpawn(world, X, Y, Z) || !LocationIsValidSpawn(world, X + 7, Y, Z) || !LocationIsValidSpawn(world, X + 7, Y, Z + 7) || !LocationIsValidSpawn(world, X, Y, Z + 7)) {
			return false;
		}

		createCreeperCondo(world, pos);
		return true;
	}

	private void createCreeperCondo(World world, BlockPos pos) {

		IBlockState OBSIDIAN = Blocks.OBSIDIAN.getDefaultState();
		IBlockState AIR = Blocks.AIR.getDefaultState();
		IBlockState MONSTER = Blocks.MONSTER_EGG.getDefaultState().withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.STONEBRICK);
		// clear area first
		for (int i = 0; i <= 6; i++) {
			for (int j = 0; j <= 6; j++) {
				for (int k = 0; k <= 8; k++) {
					world.setBlockState(new BlockPos(pos.getX() + i, pos.getY() + k, pos.getZ() + j), AIR);
				}
			}
		}
		int X = pos.getX();
		int Y = pos.getY();
		int Z = pos.getZ();
		// Base Layer
		world.setBlockState(pos, getRandomBrick());
		world.setBlockState(new BlockPos(X + 1, Y, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 2, Y, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 3, Y, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 4, Y, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 5, Y, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y, Z), getRandomBrick());

		world.setBlockState(new BlockPos(X, Y, Z + 1), getRandomBrick());
		world.setBlockState(new BlockPos(X + 1, Y, Z + 1), getRandomBrick());
		world.setBlockState(new BlockPos(X + 2, Y, Z + 1), getRandomBrick());
		world.setBlockState(new BlockPos(X + 3, Y, Z + 1), getRandomBrick());
		world.setBlockState(new BlockPos(X + 4, Y, Z + 1), getRandomBrick());
		world.setBlockState(new BlockPos(X + 5, Y, Z + 1), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y, Z + 1), getRandomBrick());

		world.setBlockState(new BlockPos(X, Y, Z + 2), getRandomBrick());
		world.setBlockState(new BlockPos(X + 1, Y, Z + 2), getRandomBrick());
		world.setBlockState(new BlockPos(X + 2, Y, Z + 2), OBSIDIAN);
		world.setBlockState(new BlockPos(X + 3, Y, Z + 2), OBSIDIAN);
		world.setBlockState(new BlockPos(X + 4, Y, Z + 2), OBSIDIAN);
		world.setBlockState(new BlockPos(X + 5, Y, Z + 2), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y, Z + 2), getRandomBrick());

		world.setBlockState(new BlockPos(X, Y, Z + 3), getRandomBrick());
		world.setBlockState(new BlockPos(X + 1, Y, Z + 3), getRandomBrick());
		world.setBlockState(new BlockPos(X + 2, Y, Z + 3), OBSIDIAN);
		world.setBlockState(new BlockPos(X + 3, Y, Z + 3), Blocks.SOUL_SAND.getDefaultState());
		world.setBlockState(new BlockPos(X + 4, Y, Z + 3), OBSIDIAN);
		world.setBlockState(new BlockPos(X + 5, Y, Z + 3), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y, Z + 3), getRandomBrick());

		world.setBlockState(new BlockPos(X, Y, Z + 4), getRandomBrick());
		world.setBlockState(new BlockPos(X + 1, Y, Z + 4), getRandomBrick());
		world.setBlockState(new BlockPos(X + 2, Y, Z + 4), OBSIDIAN);
		world.setBlockState(new BlockPos(X + 3, Y, Z + 4), OBSIDIAN);
		world.setBlockState(new BlockPos(X + 4, Y, Z + 4), OBSIDIAN);
		world.setBlockState(new BlockPos(X + 5, Y, Z + 4), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y, Z + 4), getRandomBrick());
		world.setBlockState(new BlockPos(X, Y, Z + 5), getRandomBrick());
		world.setBlockState(new BlockPos(X + 1, Y, Z + 5), getRandomBrick());
		world.setBlockState(new BlockPos(X + 2, Y, Z + 5), getRandomBrick());
		world.setBlockState(new BlockPos(X + 3, Y, Z + 5), getRandomBrick());
		world.setBlockState(new BlockPos(X + 4, Y, Z + 5), getRandomBrick());
		world.setBlockState(new BlockPos(X + 5, Y, Z + 5), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y, Z + 5), getRandomBrick());
		world.setBlockState(new BlockPos(X, Y, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 1, Y, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 2, Y, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 3, Y, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 4, Y, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 5, Y, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y, Z + 6), getRandomBrick());

		//layer 1 up
		world.setBlockState(new BlockPos(X, Y + 1, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 1, Y + 1, Z), AIR);
		world.setBlockState(new BlockPos(X + 2, Y + 1, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 3, Y + 1, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 4, Y + 1, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 5, Y + 1, Z), AIR);
		world.setBlockState(new BlockPos(X + 6, Y + 1, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X, Y + 1, Z + 1), AIR);
		world.setBlockState(new BlockPos(X + 1, Y + 1, Z + 1), AIR);
		world.setBlockState(new BlockPos(X + 2, Y + 1, Z + 1), AIR);
		world.setBlockState(new BlockPos(X + 3, Y + 1, Z + 1), AIR);
		world.setBlockState(new BlockPos(X + 4, Y + 1, Z + 1), AIR);
		world.setBlockState(new BlockPos(X + 5, Y + 1, Z + 1), AIR);
		world.setBlockState(new BlockPos(X + 6, Y + 1, Z + 1), AIR);
		world.setBlockState(new BlockPos(X, Y + 1, Z + 2), getRandomBrick());
		world.setBlockState(new BlockPos(X + 1, Y + 1, Z + 2), AIR);
		world.setBlockState(new BlockPos(X + 2, Y + 1, Z + 2), AIR);
		world.setBlockState(new BlockPos(X + 3, Y + 1, Z + 2), AIR);
		world.setBlockState(new BlockPos(X + 4, Y + 1, Z + 2), AIR);
		world.setBlockState(new BlockPos(X + 5, Y + 1, Z + 2), AIR);
		world.setBlockState(new BlockPos(X + 6, Y + 1, Z + 2), getRandomBrick());
		world.setBlockState(new BlockPos(X, Y + 1, Z + 3), getRandomBrick());
		world.setBlockState(new BlockPos(X + 1, Y + 1, Z + 3), AIR);
		world.setBlockState(new BlockPos(X + 2, Y + 1, Z + 3), AIR);
		world.setBlockState(new BlockPos(X + 3, Y + 1, Z + 3), AIR);
		world.setBlockState(new BlockPos(X + 4, Y + 1, Z + 3), AIR);
		world.setBlockState(new BlockPos(X + 5, Y + 1, Z + 3), AIR);
		world.setBlockState(new BlockPos(X + 6, Y + 1, Z + 3), getRandomBrick());
		world.setBlockState(new BlockPos(X, Y + 1, Z + 4), getRandomBrick());
		world.setBlockState(new BlockPos(X + 1, Y + 1, Z + 4), AIR);
		world.setBlockState(new BlockPos(X + 2, Y + 1, Z + 4), AIR);
		world.setBlockState(new BlockPos(X + 3, Y + 1, Z + 4), AIR);
		world.setBlockState(new BlockPos(X + 4, Y + 1, Z + 4), AIR);
		world.setBlockState(new BlockPos(X + 5, Y + 1, Z + 4), AIR);
		world.setBlockState(new BlockPos(X + 6, Y + 1, Z + 4), getRandomBrick());
		world.setBlockState(new BlockPos(X, Y + 1, Z + 5), AIR);
		world.setBlockState(new BlockPos(X + 1, Y + 1, Z + 5), AIR);
		world.setBlockState(new BlockPos(X + 2, Y + 1, Z + 5), AIR);
		world.setBlockState(new BlockPos(X + 3, Y + 1, Z + 5), AIR);
		world.setBlockState(new BlockPos(X + 4, Y + 1, Z + 5), AIR);
		world.setBlockState(new BlockPos(X + 5, Y + 1, Z + 5), AIR);
		world.setBlockState(new BlockPos(X + 6, Y + 1, Z + 5), AIR);
		world.setBlockState(new BlockPos(X, Y + 1, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 1, Y + 1, Z + 6), AIR);
		world.setBlockState(new BlockPos(X + 2, Y + 1, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 3, Y + 1, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 4, Y + 1, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 5, Y + 1, Z + 6), AIR);
		world.setBlockState(new BlockPos(X + 6, Y + 1, Z + 6), getRandomBrick());
		// Layer 2 up
		world.setBlockState(new BlockPos(X, Y + 2, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y + 2, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X, Y + 2, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y + 2, Z + 6), getRandomBrick());
		// Layer 3 up
		world.setBlockState(new BlockPos(X, Y + 3, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y + 3, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X, Y + 3, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y + 3, Z + 6), getRandomBrick());
		// Layer 4 up
		world.setBlockState(new BlockPos(X, Y + 4, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 1, Y + 4, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X, Y + 4, Z + 1), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y + 4, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 5, Y + 4, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y + 4, Z + 1), getRandomBrick());
		world.setBlockState(new BlockPos(X, Y + 4, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X, Y + 4, Z + 5), getRandomBrick());
		world.setBlockState(new BlockPos(X + 1, Y + 4, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y + 4, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y + 4, Z + 5), getRandomBrick());
		world.setBlockState(new BlockPos(X + 5, Y + 4, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 3, Y + 4, Z + 3), OBSIDIAN);
		// Layer 5 up Spawner layer
		world.setBlockState(new BlockPos(X, Y + 5, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 1, Y + 5, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 2, Y + 5, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 3, Y + 5, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 4, Y + 5, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 5, Y + 5, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y + 5, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X, Y + 5, Z + 1), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y + 5, Z + 1), getRandomBrick());
		world.setBlockState(new BlockPos(X, Y + 5, Z + 2), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y + 5, Z + 2), getRandomBrick());
		world.setBlockState(new BlockPos(X, Y + 5, Z + 3), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y + 5, Z + 3), getRandomBrick());
		world.setBlockState(new BlockPos(X, Y + 5, Z + 4), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y + 5, Z + 4), getRandomBrick());
		world.setBlockState(new BlockPos(X, Y + 5, Z + 5), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y + 5, Z + 5), getRandomBrick());
		world.setBlockState(new BlockPos(X, Y + 5, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 1, Y + 5, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 2, Y + 5, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 3, Y + 5, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 4, Y + 5, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 5, Y + 5, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y + 5, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 3, Y + 5, Z + 1), getRandomBrick());
		world.setBlockState(new BlockPos(X + 3, Y + 5, Z + 2), MONSTER);
		world.setBlockState(new BlockPos(X + 1, Y + 5, Z + 3), getRandomBrick());
		world.setBlockState(new BlockPos(X + 2, Y + 5, Z + 3), MONSTER);
		world.setBlockState(new BlockPos(X + 4, Y + 5, Z + 3), MONSTER);
		world.setBlockState(new BlockPos(X + 5, Y + 5, Z + 3), getRandomBrick());
		world.setBlockState(new BlockPos(X + 3, Y + 5, Z + 4), MONSTER);
		world.setBlockState(new BlockPos(X + 3, Y + 5, Z + 5), getRandomBrick());
		placeSpawner(world, new BlockPos(X + 3, Y + 5, Z + 3));

		//Layer 6 up
		world.setBlockState(new BlockPos(X, Y + 6, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 1, Y + 6, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 2, Y + 6, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 3, Y + 6, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 4, Y + 6, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 5, Y + 6, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y + 6, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X, Y + 6, Z + 1), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y + 6, Z + 1), getRandomBrick());
		world.setBlockState(new BlockPos(X, Y + 6, Z + 2), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y + 6, Z + 2), getRandomBrick());
		world.setBlockState(new BlockPos(X, Y + 6, Z + 3), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y + 6, Z + 3), getRandomBrick());
		world.setBlockState(new BlockPos(X, Y + 6, Z + 4), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y + 6, Z + 4), getRandomBrick());
		world.setBlockState(new BlockPos(X, Y + 6, Z + 5), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y + 6, Z + 5), getRandomBrick());
		world.setBlockState(new BlockPos(X, Y + 6, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 1, Y + 6, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 2, Y + 6, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 3, Y + 6, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 4, Y + 6, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 5, Y + 6, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y + 6, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 3, Y + 6, Z + 3), OBSIDIAN);
		// Layer 7 up
		world.setBlockState(new BlockPos(X, Y + 7, Z), getRandomBrick());
		for (int i = 1; i <= 6; i++) {
			world.setBlockState(new BlockPos(X + i, Y + 7, Z), getRandomBrick());
		}
		for (int i = 1; i <= 6; i++) {
			world.setBlockState(new BlockPos(X, Y + 7, Z + i), getRandomBrick());
		}
		for (int i = 1; i <= 6; i++) {
			world.setBlockState(new BlockPos(X + 6, Y + 7, Z + i), getRandomBrick());
		}
		for (int i = 1; i <= 6; i++) {
			world.setBlockState(new BlockPos(X + i, Y + 7, Z + 6), getRandomBrick());
		}
		// layer 8 up
		world.setBlockState(new BlockPos(X, Y + 8, Z), getRandomBrick());

		world.setBlockState(new BlockPos(X + 1, Y + 8, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 2, Y + 8, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 3, Y + 8, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 4, Y + 8, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 5, Y + 8, Z), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y + 8, Z), getRandomBrick());

		world.setBlockState(new BlockPos(X, Y + 8, Z + 1), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y + 8, Z + 1), getRandomBrick());
		world.setBlockState(new BlockPos(X, Y + 8, Z + 2), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y + 8, Z + 2), getRandomBrick());
		world.setBlockState(new BlockPos(X, Y + 8, Z + 3), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y + 8, Z + 3), getRandomBrick());
		world.setBlockState(new BlockPos(X, Y + 8, Z + 4), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y + 8, Z + 4), getRandomBrick());
		world.setBlockState(new BlockPos(X, Y + 8, Z + 5), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y + 8, Z + 5), getRandomBrick());
		world.setBlockState(new BlockPos(X, Y + 8, Z + 6), getRandomBrick());

		world.setBlockState(new BlockPos(X + 1, Y + 8, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 2, Y + 8, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 3, Y + 8, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 4, Y + 8, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 5, Y + 8, Z + 6), getRandomBrick());
		world.setBlockState(new BlockPos(X + 6, Y + 8, Z + 6), getRandomBrick());
		// layer 9 up - Roof
		for (int i = 1; i <= 5; i++) {
			for (int j = 1; j <= 5; j++) {
				world.setBlockState(new BlockPos(X + i, Y + 9, Z + j), getRandomBrick());
			}
		}
		// Layer 1 Down - Chest layer
		world.setBlockState(new BlockPos(X + 2, Y - 1, Z + 2), OBSIDIAN);
		world.setBlockState(new BlockPos(X + 3, Y - 1, Z + 2), OBSIDIAN);
		world.setBlockState(new BlockPos(X + 4, Y - 1, Z + 2), OBSIDIAN);
		world.setBlockState(new BlockPos(X + 2, Y - 1, Z + 3), OBSIDIAN);
		placeChest(world, new BlockPos(X + 3, Y - 1, Z + 3));
		world.setBlockState(new BlockPos(X + 4, Y - 1, Z + 3), OBSIDIAN);
		world.setBlockState(new BlockPos(X + 2, Y - 1, Z + 4), OBSIDIAN);
		world.setBlockState(new BlockPos(X + 3, Y - 1, Z + 4), OBSIDIAN);
		world.setBlockState(new BlockPos(X + 4, Y - 1, Z + 4), OBSIDIAN);
		//Layer 2 Down
		world.setBlockState(new BlockPos(X + 2, Y - 2, Z + 2), OBSIDIAN);
		world.setBlockState(new BlockPos(X + 3, Y - 2, Z + 2), OBSIDIAN);
		world.setBlockState(new BlockPos(X + 4, Y - 2, Z + 2), OBSIDIAN);
		world.setBlockState(new BlockPos(X + 2, Y - 2, Z + 3), OBSIDIAN);
		world.setBlockState(new BlockPos(X + 3, Y - 2, Z + 3), OBSIDIAN);
		world.setBlockState(new BlockPos(X + 4, Y - 2, Z + 3), OBSIDIAN);
		world.setBlockState(new BlockPos(X + 2, Y - 2, Z + 4), OBSIDIAN);
		world.setBlockState(new BlockPos(X + 3, Y - 2, Z + 4), OBSIDIAN);
		world.setBlockState(new BlockPos(X + 4, Y - 2, Z + 4), OBSIDIAN);
		if (Config.enableDebugging) {
			LargeCaves.logger.info("Generated CreeperCondo at : " + X + " " + Y + " " + Z);
		}
	}

	private void placeSpawner(World world, BlockPos pos) {
		world.setBlockState(pos, Blocks.MOB_SPAWNER.getDefaultState());
		TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner) world.getTileEntity(pos);
		//compound.setBoolean("PersistenceRequired", this.persistenceRequired);

		if (tileentitymobspawner != null) {

			tileentitymobspawner.getSpawnerBaseLogic().setEntityId(EntityList.getKey(NMCRreeperEntity.class));
			NBTTagCompound nbt = new NBTTagCompound();
			tileentitymobspawner.writeToNBT(nbt);
			nbt.setShort("MinSpawnDelay", (short) 75);
			nbt.setShort("MaxSpawnDelay", (short) 150);
			nbt.setShort("MaxNearbyEntities", (short) 3);
			tileentitymobspawner.readFromNBT(nbt);
		} else {
			LargeCaves.logger.warn("Failed to fetch NightmareCreeper spawner entity at (" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + ")");
		}
	}

	private void placeChest(World world, BlockPos pos) {

		world.setBlockState(pos, Blocks.CHEST.getDefaultState());
		TileEntityChest tileentitychest = (TileEntityChest) world.getTileEntity(pos);

		if (tileentitychest != null) {
			((TileEntityChest) tileentitychest).setLootTable(LootTableList.CHESTS_SIMPLE_DUNGEON, rand.nextLong());
		}
	}

	private IBlockState getRandomBrick() {
		IBlockState STONEBRICK = Blocks.STONEBRICK.getDefaultState();
		IBlockState MOSSBRICK = Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.MOSSY);
		IBlockState CRACKEDBRICK = Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CRACKED);
		int brickNum = rand.nextInt(3);
		if (rand.nextInt(3) < 1) {
			switch (brickNum) {
				case 0:
					return STONEBRICK;
				case 1:
					return MOSSBRICK;
				case 2:
					return CRACKEDBRICK;
			}
		}
		return STONEBRICK;
	}
}
