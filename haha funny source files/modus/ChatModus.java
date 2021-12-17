package com.mraof.minestuck.inventory.captchalouge;

import com.mraof.minestuck.client.gui.captchalogue.ChatGuiHandler;
import com.mraof.minestuck.network.MinestuckChannelHandler;
import com.mraof.minestuck.network.MinestuckPacket;
import com.mraof.minestuck.util.MinestuckSounds;
import com.mraof.minestuck.client.gui.captchalogue.SylladexGuiHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ChatModus extends Modus
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
			gui = new ChatGuiHandler(this);
		return gui;
	}

	public void ejectByChat(int index, boolean asCard)
	{
		if(side.isServer())
		{
			if(index == SylladexUtils.EMPTY_SYLLADEX)
			{
				getItem(SylladexUtils.EMPTY_SYLLADEX, false);
				return;
			}
			
			if(index >= 0 && index < list.size())
				SylladexUtils.launchItem(player, getItem(index, asCard));
		}
		else
		{
			getItem(index, false);
			gui.updateContent();
		}
	}

	public void handleChatSend(String msg)
	{
		if(msg.toLowerCase().contains("@everyone"))
		{
			player.world.playSound(null, player.getPosition(), MinestuckSounds.chatModusPing, SoundCategory.PLAYERS, 0.8f, 1);
			ejectByChat(SylladexUtils.EMPTY_SYLLADEX, false);
		}
		if(msg.toLowerCase().contains("@card"))
		{
			player.world.playSound(null, player.getPosition(), MinestuckSounds.chatModusPing, SoundCategory.PLAYERS, 0.8f, 1);
			ejectByChat(SylladexUtils.EMPTY_CARD, false);
		}
	}

	public void handleChatReceived(String msg)
	{
		NonNullList<ItemStack> items = getItems();

		String asCardCheck = msg;
		for(ItemStack stack : items)
			asCardCheck = asCardCheck.toLowerCase().replace(stack.getDisplayName().toLowerCase(), "");
		boolean asCard = asCardCheck.contains("card");

		boolean playPing = false;
		for(ItemStack stack : items)
		{
			if(stack.isEmpty())
				continue;

			if(msg.toLowerCase().contains(stack.getDisplayName().toLowerCase()))
			{
				playPing = true;
				ejectByChat(list.indexOf(stack), asCard);
			}
		}

		if(playPing)
		{
			player.world.playSound(null, player.getPosition(), MinestuckSounds.chatModusPing, SoundCategory.PLAYERS, 0.8f, 1);
			MinestuckChannelHandler.sendToPlayer(MinestuckPacket.makePacket(MinestuckPacket.Type.UPDATE_MODUS, SylladexUtils.writeToNBT(this)), player);
		}
	}
}
