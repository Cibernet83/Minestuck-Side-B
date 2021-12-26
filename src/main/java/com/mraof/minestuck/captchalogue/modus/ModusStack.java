package com.mraof.minestuck.captchalogue.modus;

import com.mraof.minestuck.client.gui.captchalogue.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.SylladexGuiHandler;
import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.captchalogue.sylladex.ISylladex;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;

public class ModusStack extends Modus
{
	public ModusStack(String name)
	{
		super(name);
	}

	@Override
	public <SYLLADEX extends ISylladex> ICaptchalogueable get(LinkedList<SYLLADEX> sylladices, int[] slots, int i, boolean asCard)
	{
		SYLLADEX sylladex = sylladices.removeFirst();
		sylladices.addLast(sylladex);
		slots[i] = sylladices.size() - 1;
		return sylladex.get(slots, i + 1, asCard);
	}

	@Override
	public <SYLLADEX extends ISylladex> boolean canGet(LinkedList<SYLLADEX> sylladices, int[] slots, int i)
	{
		return slots[i] == 0 && sylladices.getFirst().canGet(slots, i + 1);
	}

	@Override
	public <SYLLADEX extends ISylladex> void put(LinkedList<SYLLADEX> sylladices, ICaptchalogueable object, EntityPlayer player)
	{
		SYLLADEX mostFreeSlotsSylladex = getSylladexWithMostFreeSlots(sylladices, player);
		sylladices.remove(mostFreeSlotsSylladex);
		sylladices.addFirst(mostFreeSlotsSylladex);
		mostFreeSlotsSylladex.put(object, player);
	}

	@Override
	public <SYLLADEX extends ISylladex> void grow(LinkedList<SYLLADEX> sylladices, ICaptchalogueable other)
	{
		sylladices.getFirst().grow(other);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public CardGuiContainer.CardTextureIndex getCardTextureIndex()
	{
		if (cardTextureIndex == null)
			cardTextureIndex = new CardGuiContainer.CardTextureIndex(SylladexGuiHandler.CARD_TEXTURE, 53);
		return cardTextureIndex;
	}
}