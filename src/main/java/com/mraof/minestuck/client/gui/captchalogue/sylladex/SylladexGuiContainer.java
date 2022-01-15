package com.mraof.minestuck.client.gui.captchalogue.sylladex;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

@SideOnly(Side.CLIENT)
public abstract class SylladexGuiContainer
{
	protected float x, y, width, height;

	public abstract void update(int depth, float partialTicks);
	public abstract void draw(GuiSylladex gui, float mouseX, float mouseY, float partialTicks);
	public abstract void drawPeek(int[] slots, int index, GuiSylladex gui, float mouseX, float mouseY, float partialTicks);
	public abstract ArrayList<Integer> hit(float x, float y);
	public abstract boolean isHitting(int[] slots, int index, float x, float y);
	public abstract boolean isEmpty();
}
