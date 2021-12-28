package com.mraof.minestuck.captchalogue.sylladex;

import com.mraof.minestuck.captchalogue.ModusLayer;
import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;
import java.util.List;

public class UpperSylladex extends MultiSylladex
{
	private final LinkedList<MultiSylladex> sylladices = new LinkedList<>(); // Keep these as LL instead of array because we have to add/remove cards :/

	UpperSylladex(ModusLayer[] modusLayers, int index)
	{
		//super(new ModusLayer(lengths[index] & 0xff, modi[index]));
		super(modusLayers[index]);
		for (int i = 0; i < this.modi.getLength(); i++)
			sylladices.add(index + 2 == modusLayers.length ? new BottomSylladex(modusLayers[index + 1]) : new UpperSylladex(modusLayers, index + 1));
	}

	UpperSylladex(NBTTagCompound nbt)
	{
		super(new ModusLayer(nbt.getCompoundTag("Modus")));

		NBTTagList sylladicesTag = nbt.getTagList("Sylladices", 10);
		for (NBTBase sylladexTagBase : sylladicesTag)
			this.sylladices.add(ISylladex.readFromNBT((NBTTagCompound) sylladexTagBase));
	}

	@Override
	public ICaptchalogueable tryGetEmptyCard(int[] slots, int index)
	{
		return sylladices.get(slots[index]).tryGetEmptyCard(slots, index + 1);
	}

	@Override
	public void addCard(ICaptchalogueable object, EntityPlayer player)
	{
		int leastSlots = 257;
		MultiSylladex leastSlotsSylladex = null;
		for (MultiSylladex sylladex : sylladices)
		{
			int slots = sylladex.getTotalSlots();
			if (slots < leastSlots)
			{
				leastSlots = slots;
				leastSlotsSylladex = sylladex;
			}
		}
		if (leastSlots == 256)
			object.eject(player);
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
	protected void getModusLayers(List<ModusLayer> modusLayers)
	{
		modusLayers.add(modi);
		sylladices.get(0).getModusLayers(modusLayers);
	}

	@Override
	protected LinkedList<MultiSylladex> getSylladices()
	{
		return sylladices;
	}

	@Override
	public NBTTagCompound writeToNBT()
	{
		NBTTagCompound nbt = new NBTTagCompound();

		nbt.setTag("Modus", modi.writeToNBT());

		NBTTagList sylladicesTag = new NBTTagList();
		for (MultiSylladex sylladex : sylladices)
			sylladicesTag.appendTag(sylladex.writeToNBT());
		nbt.setTag("Sylladices", sylladicesTag);

		return nbt;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getName(boolean plural)
	{
		return I18n.format("modus.nameCombob", modi.getName(plural), sylladices.getFirst().getName(true));
	}
}