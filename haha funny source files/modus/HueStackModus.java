package com.mraof.minestuck.inventory.captchalouge;

import com.mraof.minestuck.client.gui.captchalogue.HueStackGuiHandler;
import com.mraof.minestuck.client.gui.captchalogue.SylladexGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HueStackModus extends HueModus
{
	@Override
	@SideOnly(Side.CLIENT)
	public SylladexGuiHandler getGuiHandler()
	{
		if(gui == null)
			gui = new HueStackGuiHandler(this);
		return gui;
	}
}
