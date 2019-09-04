package com.cyborgJenn.largeCaves.event.player;

import org.lwjgl.opengl.GL11;

import com.cyborgJenn.largeCaves.LargeCaves;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlayerTickHandler {

	public boolean resetRender;
	
	public PlayerTickHandler() {
		this.resetRender = false;
	}
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event) {


	}
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void renderSpecials(final RenderPlayerEvent.Specials.Post event) {
		if (event.getEntity() instanceof EntityPlayer){
			if (event.getEntity().getName().equals("NovaLumen")) {

			}
		}
	}
	@SubscribeEvent(priority = EventPriority.HIGHEST)
    public void entityColorRender(final RenderLivingEvent.Pre event) {
        final String s = TextFormatting.getTextWithoutFormattingCodes(event.getEntity().getCommandSenderEntity().getName());
        if (event.getEntity().getName().equals("JennyLeeP") && !(event.getEntity() instanceof EntityPlayer)) {
            GL11.glColor4f(0.9686f, 0.7059f, 0.8392f, 1.0f);
            this.resetRender = true;
        }
	}
}
