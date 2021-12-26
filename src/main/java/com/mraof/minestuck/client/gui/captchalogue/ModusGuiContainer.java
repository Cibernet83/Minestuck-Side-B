package com.mraof.minestuck.client.gui.captchalogue;

import com.mraof.minestuck.sylladex.ISylladex;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

@SideOnly(Side.CLIENT)
public class ModusGuiContainer
{
	protected final ISylladex sylladex;
	protected final ArrayList<ModusGuiContainer> containers = new ArrayList<>();

	protected float x, y;
	// Relative to x, y
	protected float left, right, top, bottom;

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

		right = -containers.get(0).left;
		for (int i = 0; i < containers.size(); i++)
		{
			ModusGuiContainer container = containers.get(i);
			container.setX(right);
			container.setY(0);
			right += container.getWidth() + 5;
		}

		resetBoundingBox();
	}

	protected void resetBoundingBox()
	{
		left = right = top = bottom = 0;
		for (ModusGuiContainer container : containers)
		{
			left = Math.min(left, container.getLeft());
			right = Math.max(right, container.getRight());
			top = Math.min(top, container.getTop());
			bottom = Math.max(bottom, container.getBottom());
		}
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
		x -= this.x;
		y -= this.y;

		if (x < left || x > right || y < top || y > bottom)
			return null;

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

	public float getLeft()
	{
		return x - left;
	}

	public float getRight()
	{
		return x + right;
	}

	public float getTop()
	{
		return y - top;
	}

	public float getBottom()
	{
		return y + bottom;
	}

	public float getWidth()
	{
		return left + right;
	}

	public float getHeight()
	{
		return top + bottom;
	}
}
