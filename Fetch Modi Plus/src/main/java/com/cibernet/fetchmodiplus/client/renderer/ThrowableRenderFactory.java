package com.cibernet.fetchmodiplus.client.renderer;

import com.cibernet.fetchmodiplus.entities.CruxiteSlimeEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class ThrowableRenderFactory<T extends EntityThrowable> implements IRenderFactory<T>
{
	protected ModelBase modelBase;
	protected float shadowSize;
	protected Item item;
	
	public ThrowableRenderFactory(Item item)
	{
		this.item = item;
	}
	
	public Render<? super T> createRenderFor(RenderManager manager)
	{
		return new RenderSnowball<>(manager, item, Minecraft.getMinecraft().getRenderItem());
	}
}
