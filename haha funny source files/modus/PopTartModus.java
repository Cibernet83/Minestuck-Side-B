package com.mraof.minestuck.inventory.captchalouge;

import com.mraof.minestuck.util.ModusStorage;
import com.mraof.minestuck.item.ItemFood;
import com.mraof.minestuck.client.gui.captchalogue.SylladexGuiHandler;
import com.mraof.minestuck.item.MinestuckItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class PopTartModus extends Modus
{
	
	@Nonnull
	@Override
	public ItemStack getItem(int id, boolean asCard)
	{
		if(id == SylladexUtils.EMPTY_SYLLADEX)
		{
			for(ItemStack item : list)
				SylladexUtils.launchItem(player, ModusStorage.storeItem(new ItemStack(MinestuckItems.popTart), item));
			list.clear();
			return ItemStack.EMPTY;
		}
		if(id < 0 || asCard || list.get(id) == null || list.get(id).isEmpty())
			return super.getItem(id, asCard);
		return ModusStorage.storeItem(new ItemStack(MinestuckItems.popTart), super.getItem(id, asCard));
	}
	
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
			gui = new BaseModusGuiHandler(this, 7) {};
		return gui;
	}
	
	public static ItemFood.FoodItemConsumer getConsumer()
	{
		return ((stack, worldIn, player) ->
		{
			ItemStack storedStack = ModusStorage.getStoredItem(stack);
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
