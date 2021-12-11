package com.cibernet.fetchmodiplus.captchalogue;

import com.cibernet.fetchmodiplus.client.gui.captchalogue.BaseModusGuiHandler;
import com.cibernet.fetchmodiplus.items.BaseItem;
import com.cibernet.fetchmodiplus.registries.FMPItems;
import com.mraof.minestuck.client.gui.captchalouge.SylladexGuiHandler;
import com.mraof.minestuck.inventory.captchalouge.CaptchaDeckHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class EightBallModus extends BaseModus
{
	@Override
	protected boolean getSort()
	{
		return false;
	}
	
	@Nonnull
	@Override
	public ItemStack getItem(int id, boolean asCard)
	{
		if(id == CaptchaDeckHandler.EMPTY_SYLLADEX)
		{
			for(ItemStack item : list)
				CaptchaDeckHandler.launchAnyItem(player, BaseItem.storeItem(new ItemStack(getEightBallItem()), item));
			list.clear();
			return ItemStack.EMPTY;
		}
		if(id < 0 || asCard || list.get(id) == null || list.get(id).isEmpty())
			return super.getItem(id, asCard);
		return BaseItem.storeItem(new ItemStack(getEightBallItem()), super.getItem(id, asCard));
	}

	protected Item getEightBallItem()
	{
		return FMPItems.eightBall;
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
