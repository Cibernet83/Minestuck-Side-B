package com.mraof.minestuck.client.gui.captchalogue;

import com.mraof.minestuck.inventory.captchalouge.ISylladex;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

@SideOnly(Side.CLIENT)
public class ModusGuiContainer
{
	protected final ISylladex sylladex;
	protected final ArrayList<ModusGuiContainer> containers = new ArrayList<>();

	protected float x, y, width, height;

	public ModusGuiContainer(ArrayList<CardGuiContainer.CardTextureIndex[]> textureIndices, ISylladex sylladex)
	{
		this.sylladex = sylladex;
		generateSubContainers(textureIndices);
	}

	public ModusGuiContainer(ISylladex sylladex)
	{
		this(new ArrayList<>(), sylladex);
	}

	protected void generateSubContainers(ArrayList<CardGuiContainer.CardTextureIndex[]> textureIndices)
	{
		containers.clear();
		containers.addAll(sylladex.generateSubContainers(textureIndices));

		width = height = 0;
		for (int i = 0; i < containers.size(); i++)
		{
			ModusGuiContainer container = containers.get(i);
			container.setX(width);
			container.setY(0);
			width += container.width + 5;
			height = Math.max(height, container.height);
		}
		width -= 5;
	}

	public void draw(SylladexGuiHandler gui)
	{
		GlStateManager.translate(x, y, 0);
		for (ModusGuiContainer container : containers)
			container.draw(gui);
		GlStateManager.translate(-x, -y, 0);
	}

	public ArrayList<Integer> hit(float x, float y)
	{
		if (x < this.x || x > this.x + this.width || y < this.y || y > this.y + this.height)
			return null;

		x += this.x;
		y += this.y;

		for (int i = 0; i < containers.size(); i++)
		{
			ArrayList<Integer> hitSlots = containers.get(i).hit(x, y);
			if (hitSlots != null)
			{
				hitSlots.add(0, i);
				return hitSlots;
			}
		}
		return null;
	}

	public float getX()
	{
		return x;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getY()
	{
		return y;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public float getWidth()
	{
		return width;
	}

	public float getHeight()
	{
		return height;
	}
}
