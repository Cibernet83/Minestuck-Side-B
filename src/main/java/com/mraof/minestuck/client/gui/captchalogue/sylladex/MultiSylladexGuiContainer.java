package com.mraof.minestuck.client.gui.captchalogue.sylladex;

import com.mraof.minestuck.captchalogue.sylladex.Sylladex;
import com.mraof.minestuck.captchalogue.sylladex.MultiSylladex;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

@SideOnly(Side.CLIENT)
public abstract class MultiSylladexGuiContainer extends SylladexGuiContainer
{
	private final MultiSylladex sylladex;
	private final ArrayList<SylladexGuiContainer> containers = new ArrayList<>();

	protected MultiSylladexGuiContainer(MultiSylladex<? extends Sylladex> sylladex, CardGuiContainer.CardTextureIndex[] firstTextureIndices, CardGuiContainer.CardTextureIndex[] lowerTextureIndices)
	{
		this.sylladex = sylladex;

		for (Sylladex subsylladex : sylladex)
			containers.add(subsylladex.generateSubContainer(firstTextureIndices != null && subsylladex == sylladex.getFirst() ? firstTextureIndices : lowerTextureIndices));
	}

	protected void constrainBounds()
	{
		width = height = 0;
		float xOffset = 0, yOffset = 0;

		for (SylladexGuiContainer container : containers)
		{
			if (container.isEmpty()) continue;
			xOffset = Math.min(xOffset, container.x);
			yOffset = Math.min(yOffset, container.y);
			width = Math.max(width, container.x + container.width);
			height = Math.max(height, container.y + container.height);
		}
		width -= xOffset;
		height -= yOffset;
		for (SylladexGuiContainer container : containers)
		{
			if (container.isEmpty()) continue;
			container.x -= xOffset;
			container.y -= yOffset;
		}
	}

	@Override
	public void draw(GuiSylladex gui, float mouseX, float mouseY, float partialTicks, boolean fetchable)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, 0);

		for (int i = 0; i < containers.size(); i++)
			if (!containers.get(i).isEmpty())
				containers.get(i).draw(gui, mouseX - x, mouseY - y, partialTicks, fetchable && sylladex.canGet(i));

		GlStateManager.popMatrix();
	}

	@Override
	public void drawPeek(int[] slots, int index, GuiSylladex gui, float mouseX, float mouseY, float partialTicks, boolean fetchable)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, 0);

		containers.get(slots[index]).drawPeek(slots, index + 1, gui, mouseX, mouseY, partialTicks, fetchable && sylladex.canGet(slots[index]));

		GlStateManager.popMatrix();
	}

	@Override
	public ArrayList<Integer> hit(float x, float y)
	{
		x -= this.x;
		y -= this.y;

		if (x < 0 || x > width || y < 0 || y > height)
			return null;

		for (int i = containers.size() - 1; i >= 0; i--)
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
	public boolean isHitting(int[] slots, int index, float x, float y)
	{
		x -= this.x;
		y -= this.y;

		if (x < 0 || x > width || y < 0 || y > height)
			return false;

		return containers.get(slots[index]).isHitting(slots, index + 1, x, y);
	}

	@Override
	public boolean isEmpty()
	{
		for (SylladexGuiContainer container : containers)
			if (!container.isEmpty())
				return false;
		return true;
	}

	@Override
	protected float getMaxVertexDistance(float angle)
	{
		float dist = -Float.MAX_VALUE;
		for (SylladexGuiContainer container : containers)
			dist = Math.max(dist, container.y * MathHelper.cos(angle) - container.x * MathHelper.sin(angle) + container.getMaxVertexDistance(angle));
		return dist;
	}

	@Override
	protected float getMinVertexDistance(float angle)
	{
		float dist = Float.MAX_VALUE;
		for (SylladexGuiContainer container : containers)
			dist = Math.min(dist, container.y * MathHelper.cos(angle) - container.x * MathHelper.sin(angle) + container.getMinVertexDistance(angle));
		return dist;
	}

	protected MultiSylladex getSylladex()
	{
		return sylladex;
	}

	protected ArrayList<SylladexGuiContainer> getContainers()
	{
		return containers;
	}
}
