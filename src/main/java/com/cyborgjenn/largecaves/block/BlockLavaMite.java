package com.cyborgjenn.largecaves.block;

import com.cyborgjenn.largecaves.compat.TOPInfoProvider;
import com.cyborgjenn.largecaves.entity.EntityLavaMite;
import mcjty.theoneprobe.Tools;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.api.TextStyleClass;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockLavaMite extends BlockBase implements TOPInfoProvider {

	public BlockLavaMite(String name) {
		super(Material.CLAY, name);
		this.setHardness(0.4F);
		this.setResistance(1F);
		this.setHarvestLevel("pickaxe", 0);
	}

	public static boolean canContainLavaMite(IBlockState blockState) {
		Block block = blockState.getBlock();
		return blockState == Blocks.NETHERRACK.getDefaultState();
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	public int quantityDropped(Random random) {
		return 0;
	}

	protected ItemStack getSilkTouchDrop(IBlockState state) {
		return new ItemStack(Blocks.NETHERRACK);
	}

	/**
	 * Spawns this Block's drops into the World as EntityItems.
	 */
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
		if (!worldIn.isRemote && worldIn.getGameRules().getBoolean("doTileDrops")) {
			EntityLavaMite entitylavamite = new EntityLavaMite(worldIn);
			entitylavamite.setLocationAndAngles((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, 0.0F, 0.0F);
			worldIn.spawnEntity(entitylavamite);
			entitylavamite.spawnExplosionParticle();
		}
	}

	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(this, 1, state.getBlock().getMetaFromState(state));
	}

	public MapColor getMapColor(int color) {
		return MapColor.NETHERRACK;
	}

	@Override
	public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
		if (mode != ProbeMode.DEBUG) {

			ItemStack netherrack = new ItemStack(Blocks.NETHERRACK);
			probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER).spacing(3))
					.item(netherrack)
					.vertical()
					.itemLabel(netherrack)
					.text(TextStyleClass.MODNAME + Tools.getModName(((ItemBlock) netherrack.getItem()).getBlock()));
		}
	}
}
