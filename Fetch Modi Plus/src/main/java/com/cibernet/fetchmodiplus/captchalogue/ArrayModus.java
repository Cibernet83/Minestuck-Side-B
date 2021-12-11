package com.cibernet.fetchmodiplus.captchalogue;

import com.cibernet.fetchmodiplus.client.gui.captchalogue.BaseModusGuiHandler;
import com.mraof.minestuck.client.gui.captchalouge.SylladexGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ArrayModus extends BaseModus
{
	@Override
	protected boolean getSort()
	{
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public SylladexGuiHandler getGuiHandler()
	{
		if(gui == null)
			gui = new BaseModusGuiHandler(this, 42) {};
		return gui;
	}
}
