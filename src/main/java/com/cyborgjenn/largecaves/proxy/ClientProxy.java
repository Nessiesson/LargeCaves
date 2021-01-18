package com.cyborgjenn.largecaves.proxy;

import com.cyborgjenn.largecaves.LargeCaves;
import com.cyborgjenn.largecaves.entity.EntityLavaMite;
import com.cyborgjenn.largecaves.entity.NMCRreeperEntity;
import com.cyborgjenn.largecaves.render.RenderLavaMite;
import com.cyborgjenn.largecaves.render.RenderNMCreeper;
import com.cyborgjenn.largecaves.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(NMCRreeperEntity.class, new IRenderFactory<NMCRreeperEntity>() {
			public Render<NMCRreeperEntity> createRenderFor(RenderManager manager) {
				return new RenderNMCreeper(manager);
			}
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityLavaMite.class, new IRenderFactory<EntityLavaMite>() {
			public Render<EntityLavaMite> createRenderFor(RenderManager manager) {
				return new RenderLavaMite(manager);
			}
		});

		LargeCaves.logger.info("Registered Entity Models");
	}

	public static void registerBlockRenderers(Block block, int meta, String id) {
		Item item = Item.getItemFromBlock(block);
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Reference.MODID + ":" + id, "inventory"));
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}

	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Reference.MODID + ":" + id, "inventory"));
	}
}
