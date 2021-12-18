package com.mraof.minestuck.inventory.captchalouge;

import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.util.AlchemyUtils;
import com.mraof.minestuck.util.SylladexUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class CaptchalogueableItemStack implements ICaptchalogueable
{
	private ItemStack stack; // Should be final but readFromNBT

	public CaptchalogueableItemStack(ItemStack stack)
	{
		this.stack = stack;
	}

	@Override
	public void grow(ICaptchalogueable other)
	{
		if (!isCompatibleWith(other))
			return;
		SylladexUtils.moveItemStack((ItemStack)other.getObject(), stack);
	}

	@Override
	public boolean isEmpty()
	{
		return stack.isEmpty();
	}

	@Override
	public boolean isCompatibleWith(ICaptchalogueable other)
	{
		if (!(other.getObject() instanceof ItemStack))
			return false;
		ItemStack otherStack = (ItemStack)other.getObject();
		return SylladexUtils.areItemStacksCompatible(stack, otherStack);
	}

	@Override
	public Object getObject()
	{
		return stack;
	}

	@Override
	public void eject(ISylladex fromSylladex, EntityPlayer player)
	{
		if(fromSylladex != null && stack.getItem().equals(MinestuckItems.captchaCard) && !AlchemyUtils.containsItem(stack))
			while (!stack.isEmpty())
			{
				fromSylladex.addCard(new CaptchalogueableItemStack(AlchemyUtils.getDecodedItem(stack)));
				stack.shrink(1);
			}
		if(!stack.isEmpty())
			SylladexUtils.launchItem(player, stack);
	}

	@Override
	public ItemStack captchalogueIntoCardItem()
	{
		return AlchemyUtils.createCard(stack, false);
	}

	@Override
	public NBTTagCompound writeToNBT()
	{
		return AlchemyUtils.encodeTo(stack, null, true);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		stack = AlchemyUtils.decodeFrom(nbt);
	}
}
