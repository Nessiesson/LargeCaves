package com.cyborgJenn.largeCaves.compat;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class TinkersFluid extends Fluid {

	
	public TinkersFluid(String fluidName, ResourceLocation still, ResourceLocation flowing) {
		super(fluidName, still, flowing);
		
		this.setDensity(2000);
		this.setViscosity(10000);
		this.setTemperature(1000);
	}
	
	@Override
	public int getColor(){
		return 0xFF8e2ec1;

	}
}
