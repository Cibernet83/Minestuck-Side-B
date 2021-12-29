package com.mraof.minestuck.captchalogue.sylladex;

import com.mraof.minestuck.captchalogue.ModusLayer;
import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.GuiSylladex;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.MultiSylladexGuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public abstract class MultiSylladex implements ISylladex
{
	public final EntityPlayer player;
	protected final ModusLayer modi;
	public boolean autoBalanceNewCards = false;

	@SideOnly(Side.CLIENT)
	private GuiSylladex gui;

	protected MultiSylladex(EntityPlayer player, ModusLayer modi)
	{
		this.player = player;
		this.modi = modi;
	}

	@Override
	public ICaptchalogueable get(int[] slots, int index, boolean asCard)
	{
		return modi.get(getSylladices(), slots, index, asCard);
	}

	@Override
	public boolean canGet(int[] slots, int index)
	{
		return modi.canGet(getSylladices(), slots, index);
	}

	@Override
	public ICaptchalogueable peek(int[] slots, int index)
	{
		return getSylladices().get(slots[index]).peek(slots, index + 1);
	}

	@Override
	public void put(ICaptchalogueable object)
	{
		modi.put(getSylladices(), object);
	}

	@Override
	public void grow(ICaptchalogueable object)
	{
		modi.grow(getSylladices(), object);
	}

	@Override
	public void eject()
	{
		modi.eject(getSylladices());
	}

	@Override
	public boolean tryEjectCard()
	{
		for (ISylladex sylladex : getSylladices())
			if (sylladex.tryEjectCard())
				return true;
		return false;
	}

	public void addCards(int cards)
	{
		for (int i = 0; i < cards; i++)
			addCard(null);
	}

	public ModusLayer[] getModusLayers()
	{
		ArrayList<ModusLayer> modi = new ArrayList<>();
		getModusLayers(modi);
		return modi.toArray(new ModusLayer[0]);
	}

	public boolean isFirstVisibleCard(int[] slots, int index, int startIndex)
	{
		// Remember, get(slots[index]) is known to have slots
		if (index >= startIndex)
			for (int i = 0; i < slots[index]; i++)
				if(getSylladices().get(i).getTotalSlots() > 0)
					return false;
		return index + 1 == slots.length || ((MultiSylladex)getSylladices().get(slots[index])).isFirstVisibleCard(slots, index + 1, startIndex);
	}

	@Override
	public int getFreeSlots()
	{
		int slots = 0;
		for (ISylladex sylladex : getSylladices())
			slots += sylladex.getFreeSlots();
		return slots;
	}

	@Override
	public int getTotalSlots()
	{
		int slots = 0;
		for (ISylladex sylladex : getSylladices())
			slots += sylladex.getTotalSlots();
		return slots;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public MultiSylladexGuiContainer generateSubContainer(CardGuiContainer.CardTextureIndex[] textureIndices)
	{
		return modi.getGuiContainer(getSylladices(), textureIndices);
	}

	@SideOnly(Side.CLIENT)
	public String getName()
	{
		return getName(false);
	}

	@SideOnly(Side.CLIENT)
	public GuiSylladex getGuiHandler()
	{
		if (gui == null)
			gui = new GuiSylladex(this);
		return gui;
	}

	public abstract void addCard(ICaptchalogueable object);
	protected abstract void getModusLayers(List<ModusLayer> modusLayers);
	protected abstract SylladexList<? extends ISylladex> getSylladices();
	public abstract BottomSylladex getFirstBottomSylladex();
	@SideOnly(Side.CLIENT)
	public abstract String getName(boolean plural);
}
