package com.cyborgJenn.largeCaves.render;

import com.cyborgJenn.largeCaves.entity.EntityNMCreeper;
import com.cyborgJenn.largeCaves.model.ModelNMCreeper;
import com.cyborgJenn.largeCaves.util.Reference;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderNMCreeper extends RenderLiving<EntityNMCreeper>{

	private static final ResourceLocation armoredCreeperTextures = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
    private static final ResourceLocation creeperTextures = new ResourceLocation(Reference.TEXTURE + "textures/mobs/NMCreeper.png");
    
    public RenderNMCreeper(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelNMCreeper(), 0.5F);
        this.addLayer(new LayerCreeperCharge(this));
    }

	protected void preRenderCallback(EntityNMCreeper entitylivingbaseIn, float partialTickTime) {
		float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);
        float f1 = 1.0F + MathHelper.sin(f * 100.0F) * f * 0.01F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        f = f * f;
        f = f * f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        GlStateManager.scale(f2, f3, f2);
	}
	/**
     * Gets an RGBA int color multiplier to apply.
     */
    protected int getColorMultiplier(EntityNMCreeper entitylivingbaseIn, float lightBrightness, float partialTickTime)
    {
        float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);

        if ((int)(f * 10.0F) % 2 == 0)
        {
            return 0;
        }
        else
        {
            int i = (int)(f * 0.2F * 255.0F);
            i = MathHelper.clamp(i, 0, 255);
            return i << 24 | 822083583;
        }
    }
	@Override
	protected ResourceLocation getEntityTexture(EntityNMCreeper entity) {
		return creeperTextures;
	}


}
