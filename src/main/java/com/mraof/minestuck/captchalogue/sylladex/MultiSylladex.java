package com.mraof.minestuck.captchalogue.sylladex;

import com.mraof.minestuck.captchalogue.ModusLayer;
import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.GuiSylladex;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.SylladexGuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public abstract class MultiSylladex implements ISylladex
{
	protected final ModusLayer modi;
	private final ArrayList<SylladexGuiContainer> guiContainers = new ArrayList<>();
	public boolean autoBalanceNewCards = false;

	@SideOnly(Side.CLIENT)
	private GuiSylladex gui;

	protected MultiSylladex(ModusLayer modi)
	{
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
	public void put(ICaptchalogueable object, EntityPlayer player)
	{
		modi.put(getSylladices(), object, player);
	}

	@Override
	public void grow(ICaptchalogueable object)
	{
		modi.grow(getSylladices(), object);
	}

	@Override
	public void eject(EntityPlayer player)
	{
		modi.eject(getSylladices(), player);
	}

	@Override
	public boolean tryEjectCard(EntityPlayer player)
	{
		for (ISylladex sylladex : getSylladices())
			if (sylladex.tryEjectCard(player))
				return true;
		return false;
	}

	public void addCards(int cards, EntityPlayer player)
	{
		for (int i = 0; i < cards; i++)
			addCard(null, player);
	}

	public ModusLayer[] getModusLayers()
	{
		ArrayList<ModusLayer> modi = new ArrayList<>();
		getModusLayers(modi);
		return modi.toArray(new ModusLayer[0]);
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
	public ArrayList<SylladexGuiContainer> generateSubContainers(CardGuiContainer.CardTextureIndex[] textureIndices)
	{
		guiContainers.clear();

		CardGuiContainer.CardTextureIndex[] lowerTextureIndices = modi.getTextureIndices();
		for (ISylladex sylladex : getSylladices())
			guiContainers.add(modi.getGuiContainer(sylladex == getSylladices().get(0) && textureIndices != null ? textureIndices : lowerTextureIndices, sylladex));

		return guiContainers;
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

	public abstract void addCard(ICaptchalogueable object, EntityPlayer player);
	protected abstract void getModusLayers(List<ModusLayer> modusLayers);
	protected abstract SylladexList<? extends ISylladex> getSylladices();
	public abstract NBTTagCompound writeToNBT();
	@SideOnly(Side.CLIENT)
	public abstract String getName(boolean plural);
}
