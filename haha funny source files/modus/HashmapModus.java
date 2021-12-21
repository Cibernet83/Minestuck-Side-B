package com.mraof.minestuck.inventory.captchalouge;

import com.mraof.minestuck.client.gui.captchalogue.HashchatGuiHandler;
import com.mraof.minestuck.client.gui.captchalogue.SylladexGuiHandler;
import com.mraof.minestuck.network.MinestuckChannelHandler;
import com.mraof.minestuck.network.MinestuckPacket;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HashmapModus extends ChatModus
{
	public HashtableModus.ModusHashFunction hashFunc = new HashtableModus.Vowel1Const2Hash();

	@Override
	@SideOnly(Side.CLIENT)
	public SylladexGuiHandler getGuiHandler()
	{
		if(gui == null)
			gui = new HashchatGuiHandler(this);
		return gui;
	}

	@Override
	public boolean putItemStack(ItemStack item)
	{
		if(item.isEmpty())
			return false;

		int cardIndex = hashFunc.hash(item.getDisplayName()) % getSize();
		while (list.size() <= cardIndex) list.add(ItemStack.EMPTY);

		if(!list.get(cardIndex).isEmpty())
		{
			ItemStack otherItem = list.get(cardIndex);
			if(otherItem.getItem() == item.getItem() && otherItem.getItemDamage() == item.getItemDamage() && ItemStack.areItemStackTagsEqual(otherItem, item)
			   && otherItem.getCount() + item.getCount() <= otherItem.getMaxStackSize())
			{
				otherItem.grow(item.getCount());
				return true;
			} else SylladexUtils.insertCardsOrLaunchItem(player, list.get(cardIndex));
		}

		list.set(cardIndex, item);

		return true;
	}

	@Override
	public void handleChatSend(String msg)
	{
		NonNullList<ItemStack> items = getItems();

		String asCardCheck = msg;
		for(ItemStack stack : items)
			asCardCheck = asCardCheck.toLowerCase().replace(stack.getDisplayName().toLowerCase(), "");
		boolean asCard = asCardCheck.contains("card");

		boolean ejected = false;
		String noWhitespace = msg.replaceAll("[^a-zA-Z]+", "");
		int lastStartIndex = -1;
		for (int i = 0; i <= noWhitespace.length(); i++)
		{
			if (i < noWhitespace.length() && Character.isUpperCase(noWhitespace.charAt(i)))
			{
				if (lastStartIndex == -1)
				{
					lastStartIndex = i;
				}
			}
			else
			{
				if (lastStartIndex != -1)
				{
					if (i >= lastStartIndex + 3)
					{
						String hashText = noWhitespace.substring(lastStartIndex, i);
						lastStartIndex = -1;
						int cardIndex = hashFunc.hash(hashText) % getSize();
						while (list.size() <= cardIndex) list.add(ItemStack.EMPTY);
						if (!list.get(cardIndex).isEmpty())
						{
							SylladexUtils.getItem((EntityPlayerMP) player, cardIndex, asCard);
							ejected = true;
						}
					}
					else
					{
						lastStartIndex = -1;
					}
				}
			}
		}

		if(ejected)
			MinestuckChannelHandler.sendToPlayer(MinestuckPacket.makePacket(MinestuckPacket.Type.UPDATE_MODUS, SylladexUtils.writeToNBT(this)), player);
	}

	@Override
	public void handleChatReceived(String msg) { }
}
