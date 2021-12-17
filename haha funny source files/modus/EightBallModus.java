package com.mraof.minestuck.inventory.captchalouge;

import com.mraof.minestuck.util.ModusStorage;
import com.mraof.minestuck.client.gui.captchalogue.SylladexGuiHandler;
import com.mraof.minestuck.item.MinestuckItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class EightBallModus extends Modus
{
	@Override
	protected boolean doesAutobalance()
	{
		return false;
	}
	
	@Nonnull
	@Override
	public ItemStack getItem(int id, boolean asCard)
	{
		if(id == SylladexUtils.EMPTY_SYLLADEX)
		{
			for(ItemStack item : list)
				SylladexUtils.launchItem(player, ModusStorage.storeItem(new ItemStack(getEightBallItem()), item));
			list.clear();
			return ItemStack.EMPTY;
		}
		if(id < 0 || asCard || list.get(id) == null || list.get(id).isEmpty())
			return super.getItem(id, asCard);
		return ModusStorage.storeItem(new ItemStack(getEightBallItem()), super.getItem(id, asCard));
	}

	protected Item getEightBallItem()
	{
		return MinestuckItems.eightBall;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public SylladexGuiHandler getGuiHandler()
	{
		if(gui == null)
			gui = new BaseModusGuiHandler(this, 15) {};
		return gui;
	}
}
