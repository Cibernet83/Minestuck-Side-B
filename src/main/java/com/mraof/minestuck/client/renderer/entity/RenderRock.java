package com.mraof.minestuck.client.renderer.entity;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.client.model.ModelRock;
import com.mraof.minestuck.entity.EntityRock;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderRock extends RenderLivingBase<EntityRock>
{
	private static final ModelRock MODEL = new ModelRock();
	private static final ResourceLocation TEXTURE = new ResourceLocation(Minestuck.MODID, "textures/entity/rock.png");

	public RenderRock(RenderManager renderManagerIn)
	{
		super(renderManagerIn, MODEL, 1f);
	}

	@Override
	public void doRender(EntityRock entity, double x, double y, double z, float entityYaw, float partialTicks)
	{

		if (!entity.isDead)
			super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected void applyRotations(EntityRock entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
	}

	@Override
	protected boolean canRenderName(EntityRock entity)
	{
		return entity.hasCustomName() && super.canRenderName(entity);
	}

	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(EntityRock entity)
	{
		return TEXTURE;
	}
}
