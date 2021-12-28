package com.mraof.minestuck.client.renderer.entity;

import com.mraof.minestuck.client.layers.LayerConsortCosmetics;
import com.mraof.minestuck.client.model.ModelConsort;
import com.mraof.minestuck.entity.consort.EntityConsort;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;

public class RenderConsort extends RenderEntityMinestuck<EntityConsort>
{
	public RenderConsort(RenderManager manager, ModelConsort model, float shadowSize)
	{
		super(manager, model, shadowSize);
		//addLayer(new LayerCustomHead(model.head));
		addLayer(new LayerConsortCosmetics(this, model.head));
	}
}
