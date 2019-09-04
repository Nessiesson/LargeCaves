package com.cyborgJenn.largeCaves.render;

import com.cyborgJenn.largeCaves.entity.EntityLavaMite;
import com.cyborgJenn.largeCaves.util.Reference;

import net.minecraft.client.model.ModelSilverfish;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderLavaMite extends RenderLiving<EntityLavaMite> {

	private static final ResourceLocation silverfishTextures = new ResourceLocation(Reference.TEXTURE + "textures/mobs/lavaMite.png");

	public RenderLavaMite(RenderManager renderManagerIn)
	{
		super(renderManagerIn,new ModelSilverfish(), 0.3F);
	}
	protected float getDeathMaxRotation(EntityLavaMite par1EntitySilverfish)
	{
		return 180.0F;
	}
	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(EntityLavaMite par1EntitySilverfish)
	{
		return silverfishTextures;
	}
}
