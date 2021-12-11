package com.cibernet.fetchmodiplus.captchalogue;

import com.cibernet.fetchmodiplus.client.gui.captchalogue.BaseModusGuiHandler;
import com.cibernet.fetchmodiplus.items.BaseItem;
import com.cibernet.fetchmodiplus.items.FoodItem;
import com.cibernet.fetchmodiplus.registries.FMPItems;
import com.mraof.minestuck.client.gui.captchalouge.SylladexGuiHandler;
import com.mraof.minestuck.inventory.captchalouge.CaptchaDeckHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class PopTartModus extends BaseModus
{
	
	@Nonnull
	@Override
	public ItemStack getItem(int id, boolean asCard)
	{
		if(id == CaptchaDeckHandler.EMPTY_SYLLADEX)
		{
			for(ItemStack item : list)
				CaptchaDeckHandler.launchAnyItem(player, BaseItem.storeItem(new ItemStack(FMPItems.popTart), item));
			list.clear();
			return ItemStack.EMPTY;
		}
		if(id < 0 || asCard || list.get(id) == null || list.get(id).isEmpty())
			return super.getItem(id, asCard);
		return BaseItem.storeItem(new ItemStack(FMPItems.popTart), super.getItem(id, asCard));
	}
	
	@Override
	protected boolean getSort()
	{
		return false;
	}
	@Override
	@SideOnly(Side.CLIENT)
	public SylladexGuiHandler getGuiHandler()
	{
		if(gui == null)
			gui = new BaseModusGuiHandler(this, 7) {};
		return gui;
	}
	
	public static FoodItem.FoodItemConsumer getConsumer()
	{
		return ((stack, worldIn, player) ->
		{
			ItemStack storedStack = BaseItem.getStoredItem(stack);
			if(storedStack.isEmpty())
				return null;
			
			if(!worldIn.isRemote)
			player.sendStatusMessage(new TextComponentTranslation("status.popTartEat", storedStack.getDisplayName()), true);
			
			if(stack.getCount() <= 1)
				return storedStack;
			else if(!player.addItemStackToInventory(storedStack))
				player.dropItem(storedStack, true);
			return null;
		});
	}
}
