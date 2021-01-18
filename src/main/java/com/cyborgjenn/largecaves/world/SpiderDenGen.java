package com.cyborgjenn.largecaves.world;

import com.cyborgjenn.largecaves.LargeCaves;
import com.cyborgjenn.largecaves.util.Config;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

import java.util.Random;

public class SpiderDenGen extends WorldGenerator {

	private static int DISTANCE_OUTER_SQ;

	/*
	 * (non-Javadoc)
	 * @see net.minecraft.world.gen.feature.WorldGenerator#generate(net.minecraft.world.World, java.util.Random, int, int, int)
	 * There be some sweet math here. (pirate tongue) Thanks and credit goes to to CovertJaguar and his GeodeGen code.
	 */
	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {

		if (Config.enableDebugging) {
			LargeCaves.logger.info("Generated SpoiderDen at : " + pos.getX() + " " + pos.getY() + " " + pos.getZ());
		}
		int denSize = (4 + rand.nextInt(3));
		DISTANCE_OUTER_SQ = denSize * denSize;
		for (int i = -denSize; i < denSize; i++) {
			for (int j = -denSize; j < denSize; j++) {
				for (int k = -denSize; k < denSize; k++) {
					int distSq = i * i + j * j + k * k;
					if (distSq <= DISTANCE_OUTER_SQ)
						fillDen(world, rand, pos.getX() + i, pos.getY() + j, pos.getZ() + k);
				}
			}
		}
		placeSpawner(world, rand, pos);
		return true;
	}

	private void placeSpawner(World world, Random rand, BlockPos pos) {
		world.setBlockState(pos, Blocks.MOB_SPAWNER.getDefaultState());
		TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner) world.getTileEntity(pos);

		if (tileentitymobspawner != null) {
			tileentitymobspawner.getSpawnerBaseLogic().setEntityId(EntityList.getKey(EntityCaveSpider.class));
			NBTTagCompound nbt = new NBTTagCompound();
			tileentitymobspawner.writeToNBT(nbt);
			nbt.setShort("MinSpawnDelay", (short) 50);
			nbt.setShort("MaxSpawnDelay", (short) 150);
			nbt.setShort("MaxNearbyEntities", (short) 8);
			tileentitymobspawner.readFromNBT(nbt);
		} else {
			LargeCaves.logger.warn("Failed to fetch Spider spawner entity at (" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + ")");
		}
		BlockPos newPos = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
		world.setBlockState(new BlockPos(newPos), Blocks.CHEST.getDefaultState());
		TileEntityChest tileentitychest = (TileEntityChest) world.getTileEntity(newPos);

		if (tileentitychest != null) {
			((TileEntityChest) tileentitychest).setLootTable(LootTableList.CHESTS_SIMPLE_DUNGEON, rand.nextLong());
		}
	}

	public void fillDen(World world, Random rand, int X, int Y, int Z) {

		if (rand.nextInt(3) == 0) {
			world.setBlockState(new BlockPos(X, Y, Z), Blocks.WEB.getDefaultState());
		} else {
			world.setBlockState(new BlockPos(X, Y, Z), Blocks.AIR.getDefaultState());
		}
	}
}

