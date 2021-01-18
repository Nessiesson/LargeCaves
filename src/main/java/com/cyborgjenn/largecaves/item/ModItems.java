package com.cyborgjenn.largecaves.item;

import com.cyborgjenn.largecaves.LargeCaves;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;

public class ModItems {

	public static final ArrayList<ItemBase> ITEMS = new ArrayList<ItemBase>();

	public static ToolMaterial CYBERIUM = EnumHelper.addToolMaterial("CYBERIUM", 3, 2561, 16.0F, 5.5F, 35);

	public static final CyberiumSword CyberiumSword = new CyberiumSword(CYBERIUM, "sword_cyberium");
	public static final CyberiumPick CyberiumPick = new CyberiumPick(CYBERIUM, "pick_cyberium");
	//public static final CyberiumShield CyberiumShield = new CyberiumShield("shield_cyberium");
	public static Item CyberiumNugget = new ItemBase("nugget_cyberium");
	public static Item CyberiumIngot = new ItemBase("ingot_cyberium");
	public static Item BunnyDebuger = new BunnyDebuger();
	public static Item CyberiumDust = new ItemBase("dust_cyberium");
	public static Item ObsidianRod = new ItemBase("rod_obsidian");

	public static void register(IForgeRegistry<Item> registry) {
		registry.registerAll(CyberiumNugget, CyberiumIngot, CyberiumDust, CyberiumSword, CyberiumPick, ObsidianRod);
		LargeCaves.logger.info("Registered Items");
	}

	public static void registerModels() {
		for (ItemBase item : ITEMS) {
			item.registerItemModels();
		}
		CyberiumSword.registerItemModel();
		CyberiumPick.registerItemModel();
		//CyberiumShield.registerItemModel();
		LargeCaves.logger.info("Client - Registered ItemModels");
	}
}
