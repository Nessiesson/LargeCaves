package com.cyborgJenn.largeCaves.proxy;

import com.cyborgJenn.largeCaves.LargeCaves;
import com.cyborgJenn.largeCaves.block.ModBlocks;
import com.cyborgJenn.largeCaves.entity.EntityLavaMite;
import com.cyborgJenn.largeCaves.entity.EntityNMCreeper;
import com.cyborgJenn.largeCaves.item.ModItems;
import com.cyborgJenn.largeCaves.render.RenderLavaMite;
import com.cyborgJenn.largeCaves.render.RenderNMCreeper;
import com.cyborgJenn.largeCaves.util.Reference;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy{
	@Override
	public void preInit(FMLPreInitializationEvent event) 
	{
		super.preInit(event);
	}
	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) 
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityNMCreeper.class, new IRenderFactory<EntityNMCreeper>() {
			public Render<EntityNMCreeper> createRenderFor(RenderManager manager) {return new RenderNMCreeper(manager);}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityLavaMite.class, new IRenderFactory<EntityLavaMite>() {
			public Render<EntityLavaMite> createRenderFor(RenderManager manager) {return new RenderLavaMite(manager);}
		});

		LargeCaves.logger.info("Registered Entity Models");
	}
	
	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Reference.MODID + ":" + id, "inventory"));
	}
	
	public static void registerBlockRenderers(Block block, int meta, String id)
	{
		Item item = Item.getItemFromBlock(block);
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Reference.MODID + ":" + id, "inventory"));
	}
}
