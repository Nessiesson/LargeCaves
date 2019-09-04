package com.cyborgJenn.largeCaves.compat;

import java.util.ArrayList;
import java.util.logging.Level;

import com.cyborgJenn.largeCaves.LargeCaves;
import com.cyborgJenn.largeCaves.compat.tconstruct.TConstruct;
import com.cyborgJenn.largeCaves.event.world.CyborgEventHandler;
import com.cyborgJenn.largeCaves.util.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class ModCompat {

	private static boolean isThaumcraftLoaded = false;
	private static boolean isTheOneProbeLoaded = false;
	private static boolean isTinkersLoaded = false;
	private static ArrayList<Block> MODBLOCKS = new ArrayList();

	public static void checkLoadedMods() {
		if (Loader.isModLoaded("thaumcraft")) {
			LargeCaves.logger.info("Found Mod - Thaumcraft, Initializing compat.");
			setThaumcraftLoaded(true);
		}
		if (Loader.isModLoaded("theoneprobe")) {
			setTheOneProbeLoaded(true);
		}
		if (Loader.isModLoaded("tconstruct")) {
			LargeCaves.logger.info("Found Mod - Tinkers Construct, Initializing compat.");
			setTinkersLoaded(true);
		}
	}
	public static void preInitCompat() {
		checkLoadedMods();
		if(isTheOneProbeLoaded){TheOneProbe.register();};
		if(isTinkersLoaded){ TConstruct.preInitTinkersConstruct();};
		if(isThaumcraftLoaded){};
	}
	public static void initCompat() {
		
	}
	public static void postInitCompat() {

	}
	/**
	 * Register Compat Blocks
	 * @param registry
	 */

	public static void registerBlocks(IForgeRegistry<Block> registry) {
		if (!MODBLOCKS.isEmpty()){
			for (Block block:MODBLOCKS){
				registry.register(block);
			}
		}
	}
	/**
	 * Register Compat ItemBlocks
	 * @param registry
	 */
	public static void registerItemBlocks(IForgeRegistry<Item> registry) {
		if (!MODBLOCKS.isEmpty()){
			for (Block block:MODBLOCKS){
				registry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
			}
		}

	}
	/**
	 * Client Side Only, Registers block models automagically
	 */
	@SideOnly(Side.CLIENT)
	public static void registerBlockModels() {
		if (!MODBLOCKS.isEmpty()){
			for (Block block:MODBLOCKS){
				Item item = Item.getItemFromBlock(block);
				ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Reference.MODID + ":" + block.getRegistryName(), "inventory"));
			}
		}

	}
	/*
	 * Getters and Setters for Loaded Mods
	 */
	public boolean isTinkersLoaded() {
		return isTinkersLoaded;
	}
	public static void setTinkersLoaded(boolean isTinkersLoaded) {
		ModCompat.isTinkersLoaded = isTinkersLoaded;
	}
	public boolean isThaumcraftLoaded() {
		return isThaumcraftLoaded;
	}
	private static void setThaumcraftLoaded(boolean isThaumcraftLoaded) {
		ModCompat.isThaumcraftLoaded = isThaumcraftLoaded;
	}
	public boolean isTheOneProbeLoaded() {
		return isTheOneProbeLoaded;
	}
	private static void setTheOneProbeLoaded(boolean isTheOneProbeLoaded) {
		ModCompat.isTheOneProbeLoaded = isTheOneProbeLoaded;
	}
}
