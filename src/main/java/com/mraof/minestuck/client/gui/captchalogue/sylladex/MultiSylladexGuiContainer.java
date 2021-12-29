package com.mraof.minestuck.client.gui.captchalogue.sylladex;

import com.mraof.minestuck.captchalogue.sylladex.ISylladex;
import com.mraof.minestuck.captchalogue.sylladex.SylladexList;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

@SideOnly(Side.CLIENT)
public class MultiSylladexGuiContainer extends SylladexGuiContainer
{
	protected final SylladexList<? extends ISylladex> sylladices;
	protected final ArrayList<SylladexGuiContainer> containers = new ArrayList<>();

	public <SYLLADEX extends ISylladex> MultiSylladexGuiContainer(SylladexList<SYLLADEX> sylladices, CardGuiContainer.CardTextureIndex[] firstTextureIndices, CardGuiContainer.CardTextureIndex[] lowerTextureIndices)
	{
		this.sylladices = sylladices;

		for (ISylladex sylladex : sylladices)
			containers.add(sylladex.generateSubContainer(firstTextureIndices != null && sylladex == sylladices.getFirstWithSlots() ? firstTextureIndices : lowerTextureIndices));
	}

	public void draw(GuiSylladex gui)
	{
		GlStateManager.translate(x, y, 0);

		if (!containers.isEmpty())
		{
			float width = -containers.get(0).left;
			for (SylladexGuiContainer container : containers)
			{
				container.x = width;
				container.draw(gui);
				width += container.getWidth() + 5;
			}
		}
		recalculateBoundingBox();

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

	protected void recalculateBoundingBox()
	{
		left = right = top = bottom = 0;
		for (SylladexGuiContainer container : containers)
		{
			left = Math.min(left, container.getLeft());
			right = Math.max(right, container.getRight());
			top = Math.min(top, container.getTop());
			bottom = Math.max(bottom, container.getBottom());
		}
	}
}
