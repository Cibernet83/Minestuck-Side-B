package com.mraof.minestuck.inventory.captchalouge;

import com.mraof.minestuck.client.gui.captchalogue.CipherGuiHandler;
import com.mraof.minestuck.client.gui.captchalogue.SylladexGuiHandler;

public class CipherModus extends Modus
{
	@Override
	protected boolean doesAutobalance()
	{
		return false;
	}
	
	@Override
	public SylladexGuiHandler getGuiHandler()
	{
		if(gui == null)
			gui = new CipherGuiHandler(this);
		return gui;
	}
}
