package com.cyborgjenn.largecaves.event.world;

import com.cyborgjenn.largecaves.LargeCaves;
import com.cyborgjenn.largecaves.item.ModItems;
import com.cyborgjenn.largecaves.proxy.CommonProxy;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Method;
import java.util.List;

public class CyborgEventHandler {
	private static Method getEntityIdMethod;

	static {
		try {
			getEntityIdMethod = ReflectionHelper.findMethod(MobSpawnerBaseLogic.class, "getEntityId", "func_190895_g");
		} catch (Exception e) {
			LargeCaves.logger.error("LastHoorah: Could not find method: getEntityId/func_190895_g");
		}

	}

	private final int spawnCount = 4;
	private final int spawnRange = 3;
	private BlockPos blockPos;
	private Biome biome;

	private static ResourceLocation getEntityName(MobSpawnerBaseLogic logic) {
		if (getEntityIdMethod != null) {
			try {
				return (ResourceLocation) getEntityIdMethod.invoke(logic, new Object[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void getPopulateEvent(PopulateChunkEvent.Pre event) {
		World world = event.getWorld();
		List<Block> STONETYPES = Lists.newArrayList(
				Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.STONE).getBlock(),
				Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE).getBlock(),
				Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE).getBlock(),
				Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE).getBlock());

		for (int i = 0; i < 16; ++i) {
			for (int j = 0; j < 16; ++j) {
				for (int k = 255; k > 53; --k) {

					blockPos = new BlockPos(event.getChunkX() * 16 + i, k, event.getChunkZ() * 16 + j);
					biome = world.getBiome(blockPos);
					IBlockState top = biome.topBlock;
					IBlockState filler = biome.fillerBlock;

					if (biome.equals(Biomes.DESERT) || biome.equals(Biomes.DESERT_HILLS) || biome.equals(Biomes.MUTATED_DESERT)) {
						filler = Blocks.SANDSTONE.getDefaultState();
					}

					if (STONETYPES.contains(world.getBlockState(blockPos).getBlock()) &&
							world.getBlockState(blockPos.up()) == Blocks.AIR.getDefaultState() &&
							world.getBlockState(blockPos.up()) != Blocks.WATER.getDefaultState()) {
						if (biome.equals(Biomes.DESERT) || biome.equals(Biomes.DESERT_HILLS) || biome.equals(Biomes.MUTATED_DESERT)) {
							world.setBlockState(blockPos, top);
							world.setBlockState(blockPos.down(), top);
							world.setBlockState(blockPos.down(2), filler);
						} else {
							world.setBlockState(blockPos, top);
							world.setBlockState(blockPos.down(), filler);
						}
						break;
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void getBlockPlaceEvent(BlockEvent.EntityPlaceEvent event) {
		if (event.getEntity() instanceof EntityPlayer) {
			if (event.getPlacedBlock().getBlock().equals(Block.getBlockFromName("rustic:apiary")) ||
					event.getPlacedBlock().getBlock().equals(Block.getBlockFromName("harvestcraft:apiary"))) {
				event.getWorld().playSound((EntityPlayer) null, event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), CommonProxy.BEEZ, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void getBlockBreakEvent(BlockEvent.BreakEvent event) {
		World world = event.getWorld();
		Block block = event.getState().getBlock();

		if (event.getState().getBlock() instanceof BlockMobSpawner) {
			if (!event.getPlayer().world.isRemote) {

				TileEntity tile = event.getPlayer().world.getTileEntity(event.getPos());
				if (tile instanceof TileEntityMobSpawner) {
					TileEntityMobSpawner spawner = (TileEntityMobSpawner) tile;
					MobSpawnerBaseLogic logic = spawner.getSpawnerBaseLogic();
					ResourceLocation entityName = getEntityName(logic);

					for (int i = 0; i < this.spawnCount; ++i) {
						Entity toSpawn = new EntityList().createEntityByIDFromName(entityName, world);
						if (toSpawn != null) {
							toSpawn.setLocationAndAngles(
									(double) event.getPos().getX() + (world.rand.nextDouble() - world.rand.nextDouble()) * (double) this.spawnRange + 0.5D,
									(double) event.getPos().getY() + 0.5D,
									(double) event.getPos().getZ() + (world.rand.nextDouble() - world.rand.nextDouble()) * (double) this.spawnRange + 0.5D,
									world.rand.nextFloat() * 360.0F, 0.0F);
							world.spawnEntity(toSpawn);
						}
					}
				}
			}
		}

		if (block.equals(Block.getBlockFromName("rustic:beehive")) ||
				block.equals(Block.getBlockFromName("harvestcraft:beehive")) ||
				block.equals(Block.getBlockFromName("rustic:apiary")) ||
				block.equals(Block.getBlockFromName("harvestcraft:apiary"))) {
			if (event.getPlayer() != null) {
				world.playSound((EntityPlayer) null, event.getPlayer().posX, event.getPlayer().posY, event.getPlayer().posZ, CommonProxy.BEEZ, SoundCategory.BLOCKS, 1.0f, 1.0f);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void getLootTables(LootTableLoadEvent event) {
		if (event.getName().equals(LootTableList.CHESTS_SIMPLE_DUNGEON)) {
			final LootPool pool2 = event.getTable().getPool("pool2");

			if (pool2 != null) {

				// pool2.addEntry(new LootEntryItem(ITEM, WEIGHT, QUALITY, FUNCTIONS, CONDITIONS, NAME));
				pool2.addEntry(new LootEntryItem(ModItems.CyberiumNugget, 6, 0, new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(1, 2))}, new LootCondition[0], "loottable:dust_cyberium"));
			}
		}

	}
}
