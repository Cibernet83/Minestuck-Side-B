package com.mraof.minestuck.inventory.captchalouge;

import com.mraof.minestuck.client.gui.captchalogue.OuijaGuiHandler;
import com.mraof.minestuck.client.gui.captchalogue.SylladexGuiHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Random;

public class OuijaModus extends Modus
{
	final Random rand = new Random();
	
	@Nonnull
	@Override
	public ItemStack getItem(int id, boolean asCard)
	{
		if(asCard || id < 0)
			return super.getItem(id, asCard);
		
		return list.size() >= 0 ? super.getItem(rand.nextInt(list.size()), asCard) : ItemStack.EMPTY;
	}
	
	@Override
	public boolean putItemStack(ItemStack stack)
	{
		System.out.println(stack);
		
		if(!stack.isEmpty())
			return super.putItemStack(stack);
		
		SylladexUtils.getItem((EntityPlayerMP)player, 0, false);
		return true;
	}
	
	@Override
	protected boolean doesAutobalance()
	{
		return true;
	}
	
	@Override
	public SylladexGuiHandler getGuiHandler()
	{
		if(gui == null)
			gui = new OuijaGuiHandler(this);
		return gui;
	}
}
