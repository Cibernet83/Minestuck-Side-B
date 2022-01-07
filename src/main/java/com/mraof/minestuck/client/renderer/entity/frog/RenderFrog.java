package com.mraof.minestuck.client.renderer.entity.frog;

import com.mraof.minestuck.client.layers.LayerConsortCosmetics;
import com.mraof.minestuck.client.model.ModelFrog;
import com.mraof.minestuck.entity.EntityFrog;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderFrog extends RenderLivingBase<EntityFrog>
{
	public RenderFrog(RenderManager manager, ModelFrog model, float par2)
	{
		super(manager, model, par2);
		this.addLayer(new LayerFrogSkin(this));
		this.addLayer(new LayerFrogEyes(this));
		this.addLayer(new LayerFrogBelly(this));
		addLayer(new LayerConsortCosmetics(this, model.head));
		//addLayer(new LayerCustomHead(model.head));

	}

	@Override
	protected void preRenderCallback(EntityFrog frog, float partialTickTime)
	{
		float scale = frog.getFrogSize();
		GlStateManager.scale(scale, scale, scale);
	}

	protected boolean canRenderName(EntityFrog entity)
	{
		return super.canRenderName(entity) && (entity.getAlwaysRenderNameTagForRender() || entity.hasCustomName() && entity == this.renderManager.pointedEntity);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityFrog entity)
	{
		return entity.getTextureResource();
	}

}
