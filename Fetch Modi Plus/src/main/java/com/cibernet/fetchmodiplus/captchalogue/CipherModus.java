package com.cibernet.fetchmodiplus.captchalogue;

import com.cibernet.fetchmodiplus.client.gui.captchalogue.CipherGuiHandler;
import com.mraof.minestuck.client.gui.captchalouge.SylladexGuiHandler;

public class CipherModus extends BaseModus
{
	@Override
	protected boolean getSort()
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
