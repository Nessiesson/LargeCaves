package com.cyborgJenn.largeCaves.compat;

import java.util.function.Function;

import javax.annotation.Nullable;

import org.apache.logging.log4j.Level;

import com.cyborgJenn.largeCaves.LargeCaves;
import com.cyborgJenn.largeCaves.block.ModBlocks;

import mcjty.theoneprobe.api.IBlockDisplayOverride;
import mcjty.theoneprobe.api.IEntityDisplayOverride;
import mcjty.theoneprobe.api.IProbeConfig;
import mcjty.theoneprobe.api.IProbeConfigProvider;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeHitEntityData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.api.TextStyleClass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class TheOneProbe {

	private static boolean registered;
	
	public static void register() {
		if (registered)
            return;
        registered = true;
        FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "com.cyborgJenn.largeCaves.compat.TheOneProbe$GetTheOneProbe");
		
	}
	public static class GetTheOneProbe implements Function<ITheOneProbe, Void> {
		public static TextStyleClass textStyle;
        public static ITheOneProbe probe;

        
        @Nullable
        @Override
        public Void apply(ITheOneProbe theOneProbe) {
            probe = theOneProbe;
            LargeCaves.logger.log(Level.INFO, "Enabled support for The One Probe");
            
            probe.registerProvider(new IProbeInfoProvider() {
                @Override
                public String getID() {
                    return "largecaves:default";
                }

                @Override
                public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
                    if (blockState.getBlock() instanceof TOPInfoProvider) {
                        TOPInfoProvider provider = (TOPInfoProvider) blockState.getBlock();
                        provider.addProbeInfo(mode, probeInfo, player, world, blockState, data);
                    }

                }
            });
            probe.registerBlockDisplayOverride(new IBlockDisplayOverride() {
				
				@Override
				public boolean overrideStandardInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world,
						IBlockState blockState, IProbeHitData data) {
					if (blockState.equals(ModBlocks.LAVAMITE.getDefaultState())){
						
						return true;
					}
					return false;
				}
				
			});
            probe.registerProbeConfigProvider(new IProbeConfigProvider(){

				@Override
				public void getProbeConfig(IProbeConfig config, EntityPlayer player, World world, Entity entity,
						IProbeHitEntityData data) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void getProbeConfig(IProbeConfig config, EntityPlayer player, World world,
						IBlockState blockState, IProbeHitData data) {
					// TODO Auto-generated method stub
					
				}
            	
            });
            return null;
        }
    }
}
