package com.mraof.minestuck.inventory.captchalouge;

import com.mraof.minestuck.client.gui.captchalogue.SylladexGuiHandler;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.util.MinestuckPlayerData;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WeightModus extends Modus
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
			gui = new BaseModusGuiHandler(this, 1) {};
		return gui;
	}
	
	public int getFloatStones()
	{
		int result = 0;
		for(ItemStack stack : list)
			if(stack.getItem().equals(MinestuckItems.floatStone))
				result ++;
		return result;
	}
	
	public double getItemCap()
	{
		return (Math.min(Math.max(0, (MinestuckPlayerData.getData(player).echeladder.getRung()-4)*4), 20))+20;
	}
}
