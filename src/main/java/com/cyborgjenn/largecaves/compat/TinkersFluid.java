package com.cyborgjenn.largecaves.compat;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class TinkersFluid extends Fluid {


	public TinkersFluid(String fluidName, ResourceLocation still, ResourceLocation flowing) {
		super(fluidName, still, flowing);

		this.setDensity(2000);
		this.setViscosity(10000);
		this.setTemperature(1000);
	}

	@Override
	public int getColor() {
		return 0xFF8e2ec1;

	}
}
