package com.mraof.minestuck.client.gui.captchalogue.sylladex;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

@SideOnly(Side.CLIENT)
public abstract class SylladexGuiContainer
{
	protected float x, y;
	protected float left, right, top, bottom; // Relative to x, y

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

	public abstract void update(int depth, float partialTicks);
	public abstract void draw(GuiSylladex gui, float mouseX, float mouseY, float partialTicks);
	public abstract ArrayList<Integer> hit(float x, float y);
	public abstract CardGuiContainer peek(int[] slots, int index);
}
