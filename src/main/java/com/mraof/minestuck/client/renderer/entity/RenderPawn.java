package com.mraof.minestuck.client.renderer.entity;

import com.mraof.minestuck.entity.carapacian.EntityPawn;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderPawn extends RenderBiped<EntityPawn>
{

	public RenderPawn(RenderManager manager, ModelBiped modelBiped, float shadowSize)
	{
		super(manager, modelBiped, shadowSize);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityPawn pawn)
	{
		return pawn.getTextureResource();
	}
}