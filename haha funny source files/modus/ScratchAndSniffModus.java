package com.mraof.minestuck.inventory.captchalouge;

import com.mraof.minestuck.client.gui.captchalogue.ScratchAndSniffGuiHandler;
import com.mraof.minestuck.client.gui.captchalogue.SylladexGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ScratchAndSniffModus extends Modus
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
			gui = new ScratchAndSniffGuiHandler(this);
		return gui;
	}
}
