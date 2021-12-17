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

	public ModusGuiContainer(ISylladex sylladex)
	{
		this.sylladex = sylladex;
	}

	public ArrayList<ModusGuiContainer> generateSubContainers()
	{
		containers.clear();
		containers.addAll(sylladex.generateSubContainers());

		width = height = 0;
		for (int i = 0; i < containers.size(); i++)
		{
			ModusGuiContainer container = containers.get(i);
			container.setX(width);
			container.setY(0);
			width += container.width + 5;
		}
		width -= 5;

		return containers;
	}

	public void draw(SylladexGuiHandler gui)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, 0F);
		for (ModusGuiContainer container : containers)
			container.draw(gui);
		GlStateManager.popMatrix();
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
