package com.mraof.minestuck.inventory.captchalouge;

import com.mraof.minestuck.client.gui.captchalogue.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.ModusGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.SylladexGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.LinkedList;

public class ModusStack extends Modus
{
	public ModusStack(String name)
	{
		super(name);
	}

	@Override
	public ICaptchalogueable get(LinkedList<ISylladex> sylladices, int[] slots, int i, boolean asCard)
	{
		ISylladex sylladex = sylladices.removeFirst();
		sylladices.addLast(sylladex);
		slots[i] = sylladices.size() - 1;
		return sylladex.get(slots, i + 1, asCard);
	}

	@Override
	public boolean canGet(LinkedList<ISylladex> sylladices, int[] slots, int i)
	{
		return slots[i] == 0 && sylladices.getFirst().canGet(slots, i + 1);
	}

	@Override
	public void put(LinkedList<ISylladex> sylladices, ISylladex mostFreeSlotsSylladex, ICaptchalogueable object, EntityPlayer player)
	{
		sylladices.remove(mostFreeSlotsSylladex);
		sylladices.addFirst(mostFreeSlotsSylladex);
		mostFreeSlotsSylladex.put(object, player);
	}

	@Override
	public void grow(LinkedList<ISylladex> sylladices, ICaptchalogueable other)
	{
		sylladices.getFirst().grow(other);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModusGuiContainer getGuiContainer(ArrayList<CardGuiContainer.CardTextureIndex[]> textureIndices, ISylladex sylladex)
	{
		return new ModusGuiContainer(textureIndices, sylladex);
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