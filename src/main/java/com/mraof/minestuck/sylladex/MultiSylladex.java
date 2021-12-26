package com.mraof.minestuck.sylladex;

import com.mraof.minestuck.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.client.gui.captchalogue.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.ModusGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.SylladexGuiHandler;
import com.mraof.minestuck.modus.Modus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

public abstract class MultiSylladex implements ISylladex
{
	protected final ArrayList<Modus> modi = new ArrayList<>();
	protected final ArrayList<ModusGuiContainer> guiContainers = new ArrayList<>();
	public boolean autoBalanceNewCards = false;

	@SideOnly(Side.CLIENT)
	private SylladexGuiHandler gui;

	@Override
	public boolean canGet(int[] slots, int i)
	{
		for (Modus modus : modi)
			if (modus.canGet(getSylladices(), slots, i))
				return true;
		return false;
	}

	@Override
	public ICaptchalogueable peek(int[] slots, int i)
	{
		return getSylladices().get(slots[i]).peek(slots, i + 1);
	}

	@Override
	public void put(ICaptchalogueable object, EntityPlayer player)
	{
		modi.get(0).put(getSylladices(), object, player);
	}

	@Override
	public void grow(ICaptchalogueable object)
	{
		modi.get(0).grow(getSylladices(), object);
	}

	@Override
	public void eject(EntityPlayer player)
	{
		modi.get(0).eject(getSylladices(), player);
	}

	public void addCards(int cards, EntityPlayer player)
	{
		for (int i = 0; i < cards; i++)
			addCard(null, player);
	}

	public Modus[][] getModi()
	{
		ArrayList<Modus[]> modi = new ArrayList<>();
		getModi(modi);
		return modi.toArray(new Modus[0][]);
	}

	protected void getModi(ArrayList<Modus[]> modi)
	{
		modi.add(this.modi.toArray(new Modus[0]));
	}

	public int[] getLengths()
	{
		ArrayList<Integer> lengths = new ArrayList<>();
		getLengths(lengths);
		return lengths.stream().mapToInt(Integer::intValue).toArray();
	}

	protected void getLengths(ArrayList<Integer> lengths)
	{
		lengths.add(getSylladices().size());
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
	public NBTTagCompound writeToNBT()
	{
		NBTTagCompound nbt = new NBTTagCompound();

		NBTTagList modusTypes = new NBTTagList();
		for (Modus modus : modi)
			modusTypes.appendTag(new NBTTagString(modus.getRegistryName().toString()));
		nbt.setTag("modusTypes", modusTypes);

		NBTTagList sylladices = new NBTTagList();
		for (ISylladex sylladex : getSylladices())
			sylladices.appendTag(sylladex.writeToNBT());
		nbt.setTag("sylladices", sylladices);

		return nbt;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ArrayList<ModusGuiContainer> generateSubContainers(ArrayList<CardGuiContainer.CardTextureIndex[]> textureIndices)
	{
		guiContainers.clear();

		CardGuiContainer.CardTextureIndex[] thisIndices = new CardGuiContainer.CardTextureIndex[modi.size()];
		for (int i = 0; i < thisIndices.length; i++)
			thisIndices[i] = modi.get(i).getCardTextureIndex();
		textureIndices.add(thisIndices);

		for (ISylladex sylladex : getSylladices())
			guiContainers.add(modi.get(0).getGuiContainer(textureIndices, sylladex));
		return guiContainers;
	}

	@SideOnly(Side.CLIENT)
	public String getNameForLayer(boolean plural)
	{
		LinkedHashSet<Modus> distinctModi = new LinkedHashSet<>(modi);

		if (distinctModi.size() == 1)
			return distinctModi.iterator().next().getName(true, false, plural);
		else
		{
			StringBuilder nameBuilder = new StringBuilder();
			Iterator<Modus> iterator = distinctModi.iterator();
			for (int i = 0; iterator.hasNext(); i++)
				nameBuilder.append(iterator.next().getName(false, i == 0, plural && i == distinctModi.size() - 1));
			return nameBuilder.toString();
		}
	}

	@SideOnly(Side.CLIENT)
	public String getName()
	{
		return getName(false);
	}

	@SideOnly(Side.CLIENT)
	public SylladexGuiHandler getGuiHandler()
	{
		if (gui == null)
			gui = new SylladexGuiHandler(this);
		return gui;
	}

	public abstract void addCard(ICaptchalogueable object, EntityPlayer player);
	public abstract LinkedList<? extends ISylladex> getSylladices();
	public abstract String getName(boolean plural);
}
