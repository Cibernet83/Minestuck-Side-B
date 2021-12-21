package com.mraof.minestuck.client.gui;

import com.mraof.minestuck.alchemy.Grist;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IGristSelectable
{
	@SideOnly(Side.CLIENT)
	void select(Grist grist);
	@SideOnly(Side.CLIENT)
	void cancel();
}
