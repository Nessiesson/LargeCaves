package com.cyborgJenn.largeCaves;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cyborgJenn.largeCaves.proxy.CommonProxy;
import com.cyborgJenn.largeCaves.util.LargeCavesTab;
import com.cyborgJenn.largeCaves.util.Reference;
import com.cyborgJenn.largeCaves.world.LargeCaveGen;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
		modid = Reference.MODID, 
		name = Reference.NAME, 
		version = Reference.VERSION,
		useMetadata = true
		)

public class LargeCaves {

	@Mod.Instance(value = Reference.MODID)
	public static LargeCaves instance;
	public static CreativeTabs 		tabLargeCaves = new LargeCavesTab(CreativeTabs.getNextID(), "tabLargeCaves");
	
	@SidedProxy(clientSide = Reference.CLIENTPROXY, serverSide = Reference.SERVERPROXY)
	public static CommonProxy proxy;
	public static final Logger logger = LogManager.getLogger(Reference.NAME);
	public static List<Block> baseBlocks;
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)throws IOException
	{
		proxy.preInit(event);
	}
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)throws IOException
	{
		proxy.init(event);
		proxy.genInit();
	}
	@Mod.EventHandler
	public void imcCallback(FMLInterModComms.IMCEvent event)
	{
		LargeCaves.logger.info("Listening for IMC messages.");
		for (final FMLInterModComms.IMCMessage imcMessage : event.getMessages())
		{
			if (imcMessage.key.equalsIgnoreCase("baseblock"))
			{
				if (imcMessage.isStringMessage())
				{
					Block ablock = Block.getBlockFromName(imcMessage.getStringValue());
					if (ablock != null){
						LargeCaveGen.BASE_BLOCKS.add(ablock);
						LargeCaves.logger.info("The Mod "+imcMessage.getSender()+" has added a block named: "+imcMessage.getStringValue()+" to the list of BaseBlocks to look for.");
					}
				}
				else 
				{
					LargeCaves.logger.warn("LargeCaves recieved an IMC message that was not of the type String, from "+ imcMessage.getSender()+".");
				}
			}
		}
	}
}
