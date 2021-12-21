package com.mraof.minestuck.inventory.captchalouge;

import com.mraof.minestuck.client.gui.captchalogue.OnionGuiHandler;
import com.mraof.minestuck.client.gui.captchalogue.SylladexGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class OnionModus extends Modus
{
	//TODO
	
	@Override
	protected boolean doesAutobalance()
	{
		return true;
	}
	@Override
	@SideOnly(Side.CLIENT)
	public SylladexGuiHandler getGuiHandler()
	{
		if(gui == null)
			gui = new OnionGuiHandler(this);
		return gui;
	}
}
