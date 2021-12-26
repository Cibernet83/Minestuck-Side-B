package com.mraof.minestuck.captchalogue.sylladex;

import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.captchalogue.modus.Modus;
import com.mraof.minestuck.util.SylladexUtils;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class UpperSylladex extends MultiSylladex
{
	private final LinkedList<MultiSylladex> sylladices = new LinkedList<>(); // Keep these as LL instead of array because we have to add/remove cards :/

	public UpperSylladex(Modus[] modi)
	{
		Collections.addAll(this.modi, modi);
	}

	public UpperSylladex(int[] lengths, Modus[][] modi, int index)
	{
		this(modi[index]);
		int length = lengths[index] & 0xff;
		for (int i = 0; i < length; i++)
			this.sylladices.add(index == lengths.length - 1 ? new BottomSylladex(modi[index + 1]) : new UpperSylladex(lengths, modi, index + 1));
	}

	public UpperSylladex(NBTTagCompound nbt)
	{
		readFromNBT(nbt);
	}

	@Override
	public ICaptchalogueable get(int[] slots, int i, boolean asCard)
	{
		for (Modus modus : modi)
			if (modus.canGet(sylladices, slots, i))
				return modus.get(sylladices, slots, i, asCard);
		return null;
	}

	@Override
	public ICaptchalogueable tryGetEmptyCard(int[] slots, int i)
	{
		return sylladices.get(slots[i]).tryGetEmptyCard(slots, i + 1);
	}

	@Override
	public void addCard(ICaptchalogueable object, EntityPlayer player)
	{
		int leastSlots = 0;
		MultiSylladex leastSlotsSylladex = null;
		for (MultiSylladex sylladex : sylladices)
		{
			int slots = sylladex.getTotalSlots();
			if (slots < leastSlots || leastSlotsSylladex == null)
			{
				leastSlots = slots;
				leastSlotsSylladex = sylladex;
			}
		}
		if (leastSlots == 256)
			SylladexUtils.launchItem(player, (ItemStack) object.getObject());
		else
			leastSlotsSylladex.addCard(object, player);
	}

	@Override
	public void ejectAll(EntityPlayer player, boolean asCards, boolean onlyFull)
	{
		for (ISylladex sylladex : sylladices)
			sylladex.ejectAll(player, asCards, onlyFull);
	}

	@Override
	protected void getModi(ArrayList<Modus[]> modi)
	{
		super.getModi(modi);
		sylladices.get(0).getModi(modi);
	}

	@Override
	protected void getLengths(ArrayList<Integer> lengths)
	{
		super.getLengths(lengths);
		sylladices.get(0).getLengths(lengths);
	}

	@Override
	public LinkedList<MultiSylladex> getSylladices()
	{
		return sylladices;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getName(boolean plural)
	{
		return I18n.format("modus.nameCombob", getNameForLayer(plural), sylladices.getFirst().getName(true));
	}

	public void readFromNBT(NBTTagCompound nbt)
	{
		modi.clear();
		sylladices.clear();

		NBTTagList modusTypes = nbt.getTagList("modusTypes", 8);
		for (NBTBase modusType : modusTypes)
			modi.add(Modus.REGISTRY.getValue(new ResourceLocation(((NBTTagString)modusType).getString())));

		NBTTagList sylladices = nbt.getTagList("sylladices", 10);
		for (NBTBase sylladexTagBase : sylladices)
			this.sylladices.add(ISylladex.readFromNBT((NBTTagCompound) sylladexTagBase));
	}
}
