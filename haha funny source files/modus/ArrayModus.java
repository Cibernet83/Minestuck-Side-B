package com.mraof.minestuck.inventory.captchalouge;

import com.mraof.minestuck.client.gui.captchalogue.SylladexGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ArrayModus extends Modus
{
	@Override
	protected boolean doesAutobalance()
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
