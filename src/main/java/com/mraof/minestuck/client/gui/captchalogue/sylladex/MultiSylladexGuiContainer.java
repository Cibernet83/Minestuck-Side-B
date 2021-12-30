package com.mraof.minestuck.client.gui.captchalogue.sylladex;

import com.mraof.minestuck.captchalogue.sylladex.ISylladex;
import com.mraof.minestuck.captchalogue.sylladex.SylladexList;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

@SideOnly(Side.CLIENT)
public class MultiSylladexGuiContainer extends SylladexGuiContainer
{
	protected final SylladexList<? extends ISylladex> sylladices;
	protected final ArrayList<SylladexGuiContainer> containers = new ArrayList<>();

	public <SYLLADEX extends ISylladex> MultiSylladexGuiContainer(SylladexList<SYLLADEX> sylladices, int[] slots, int index, CardGuiContainer.CardTextureIndex[] firstTextureIndices, CardGuiContainer.CardTextureIndex[] lowerTextureIndices)
	{
		this.sylladices = sylladices;

		int i = 0;
		for (ISylladex sylladex : sylladices)
		{
			slots[index] = i++;
			containers.add(sylladex.generateSubContainer(slots, index + 1, firstTextureIndices != null && sylladex == sylladices.getFirstWithSlots() ? firstTextureIndices : lowerTextureIndices));
		}
	}

	@Override
	public void update(int depth, float partialTicks)
	{
		if (!containers.isEmpty())
		{
			float theta = (-depth + 1) * (float)Math.PI / 3f;
			if (theta < 0)
				theta += (float)Math.PI;
			float moveX = MathHelper.cos(theta);
			float moveY = MathHelper.sin(theta);
			float slope = moveY / moveX;

			float distanceX = 0;
			float distanceY = 0;
			for (SylladexGuiContainer container : containers)
			{
				if (container.isEmpty())
					continue;
				container.update(depth + 1, partialTicks);
				container.x = distanceX;
				if (slope < 0) container.x -= container.width;
				container.y = distanceY;

				float containerSlope = container.height / container.width;
				float relaxedMetric = (Math.abs(slope) < containerSlope ? container.width : container.height) * 0.9f;
				distanceX += moveX * relaxedMetric;
				distanceY += moveY * relaxedMetric;
			}

			// Constrain bounding box
			width = height = 0;
			if (slope < 0) // Origin is at top-left, backwards
			{
				for (SylladexGuiContainer container : containers)
				{
					width = Math.min(width, container.x);
					height = Math.max(height, container.y + container.height);
				}
				width = -width;
				for (SylladexGuiContainer container : containers) // Fix origin
					container.x += width;
			}
			else // Origin is at top-right, how it should be
			{
				for (SylladexGuiContainer container : containers)
				{
					width = Math.max(width, container.x + container.width);
					height = Math.max(height, container.y + container.height);
				}
			}
		}
	}

	@Override
	public void draw(GuiSylladex gui, float mouseX, float mouseY, float partialTicks)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, 0);

		for (SylladexGuiContainer container : containers)
			container.draw(gui, mouseX - x, mouseY - y, partialTicks);

		GlStateManager.popMatrix();
	}

	@Override
	public void drawPeek(int[] slots, int index, GuiSylladex gui, float mouseX, float mouseY, float partialTicks)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, 0);

		containers.get(slots[index]).drawPeek(slots, index + 1, gui, mouseX, mouseY, partialTicks);

		GlStateManager.popMatrix();
	}

	@Override
	public ArrayList<Integer> hit(float x, float y)
	{
		x -= this.x;
		y -= this.y;

		if (x < 0 || x > width || y < 0 || y > height)
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

	@Override
	public boolean isEmpty()
	{
		for (SylladexGuiContainer container : containers)
			if (!container.isEmpty())
				return false;
		return true;
	}
}
