package com.mraof.minestuck.client.renderer.entity;

import com.mraof.minestuck.client.layers.LayerConsortCosmetics;
import com.mraof.minestuck.entity.consort.EntityConsort;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;

public class RenderConsort extends RenderEntityMinestuck<EntityConsort>
{
	public RenderConsort(RenderManager manager, ModelBase par1ModelBase, float par2)
	{
		super(manager, par1ModelBase, par2);
		addLayer(new LayerConsortCosmetics(this));
	}
}
