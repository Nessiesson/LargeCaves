package com.cyborgjenn.largecaves.compat.tconstruct;

import com.cyborgjenn.largecaves.block.ModBlocks;
import com.cyborgjenn.largecaves.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;
import slimeknights.tconstruct.library.fluid.FluidMolten;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.HarvestLevels;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

public class TConstruct {

	public static final Modifier MODIFIER_DREAMWALKER = new ModifierDreamWalker();

	public static final ITrait TRAIT_LIVING = new TraitLiving(1);

	public static final Material MATERIAL_CYBERIUM = new Material("cyberium", 0xFF8e2ec1).addTrait(TRAIT_LIVING);

	public static final Fluid FLUID_MOLTEN_CYBERIUM = new FluidMolten("molten_cyberium", 0xFF8e2ec1);

	public static void preInitTinkersConstruct() {
		MinecraftForge.EVENT_BUS.register(new TConstruct());

		FluidRegistry.registerFluid(FLUID_MOLTEN_CYBERIUM);
		FluidRegistry.addBucketForFluid(FLUID_MOLTEN_CYBERIUM);

		MATERIAL_CYBERIUM.setFluid(FLUID_MOLTEN_CYBERIUM);
		MATERIAL_CYBERIUM.setCastable(true);

		TinkerRegistry.addMaterial(MATERIAL_CYBERIUM);

		TinkerRegistry.addMaterialStats(MATERIAL_CYBERIUM,
				new HeadMaterialStats(1463, 11.3F, 14.3F, HarvestLevels.COBALT),
				new HandleMaterialStats(2.6F, 32),
				new ExtraMaterialStats(48));

		//Fluid FLUID = new TinkersFluid("molten_cyberium", new ResourceLocation("tconstruct:blocks/fluids/molten_metal"), new ResourceLocation("tconstruct:blocks/fluids/molten_metal_flow"));
		//Block fluidBlock = new BlockFluidClassic(fluid, Material.LAVA).setRegistryName("molten_cyberium").setUnlocalizedName("molten_cyberium");
		//MODBLOCKS.add(fluidBlock);
	}

	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		TinkerSmeltery.registerOredictMeltingCasting(FLUID_MOLTEN_CYBERIUM, "Cyberium");
		TinkerSmeltery.registerToolpartMeltingCasting(MATERIAL_CYBERIUM);

		MODIFIER_DREAMWALKER.addItem(Item.getItemFromBlock(ModBlocks.Cyberium), 1, 1);

		MATERIAL_CYBERIUM.addItem(ModItems.CyberiumIngot);
		MATERIAL_CYBERIUM.setRepresentativeItem(ModItems.CyberiumIngot);

	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event) {
		//ModelRegisterUtil.registerModifierModel(MODIFIER_DREAMWALKER, new ResourceLocation(Reference.MODID, "models/item/modifiers/dreamwalker"));
		MATERIAL_CYBERIUM.setRenderInfo(new MaterialRenderInfo.MultiColor(0xFF8e2ec1, 0xaa35e0, 0x375bbf)); // MultiColor = Border, Primary, Secondary

	}
}
