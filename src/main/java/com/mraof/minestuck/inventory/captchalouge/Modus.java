package com.mraof.minestuck.inventory.captchalouge;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.client.gui.captchalogue.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.ModusGuiContainer;
import com.mraof.minestuck.util.IRegistryObject;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.ArrayList;
import java.util.LinkedList;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public abstract class Modus extends IForgeRegistryEntry.Impl<Modus> implements IRegistryObject<Modus>
{
	public static ForgeRegistry<Modus> REGISTRY;

	@SideOnly(Side.CLIENT)
	protected CardGuiContainer.CardTextureIndex cardTextureIndex;

	private final String name, regName;

	protected Modus(String name)
	{
		this.name = name;
		this.regName = IRegistryObject.unlocToReg(name);
		MinestuckModi.modi.add(this);
	}

	/**
	 * Fetch an object from the slots[i]th sylladex and perform some rearranging of sylladices if required. Modus#canGet
	 * has already been confirmed by this point. Make sure to update slots[i] if rearranging is needed.
	 */
	public ICaptchalogueable get(LinkedList<ISylladex> sylladices, int[] slots, int i, boolean asCard)
	{
		return sylladices.get(slots[i]).get(slots, i + 1, asCard);
	}

	/**
	 * Return whether a card is valid to be retrieved from.
	 */
	public boolean canGet(LinkedList<ISylladex> sylladices, int[] slots, int i)
	{
		return sylladices.get(slots[i]).canGet(slots, i + 1);
	}

	/**
	 * Put an object into a default card and perform some rearranging of sylladices if required. Modus#grow has already
	 * been called by this point.
	 * @param mostFreeSlotsSylladex The sylladex in sylladices that has the most free slots
	 */
	public void put(LinkedList<ISylladex> sylladices, ISylladex mostFreeSlotsSylladex, ICaptchalogueable object, EntityPlayer player)
	{
		mostFreeSlotsSylladex.put(object, player);
	}

	public void put(LinkedList<ISylladex> sylladices, ICaptchalogueable object, EntityPlayer player)
	{
		int mostFreeSlots = 0;
		ISylladex mostFreeSlotsSylladex = null;
		for (ISylladex sylladex : sylladices)
		{
			int slots = sylladex.getFreeSlots();
			if (slots > mostFreeSlots)
			{
				mostFreeSlots = slots;
				mostFreeSlotsSylladex = sylladex;
			}
		}
		if (mostFreeSlots == 0)
		{
			mostFreeSlotsSylladex = sylladices.getLast();
			mostFreeSlotsSylladex.eject(player);
		}

		put(sylladices, mostFreeSlotsSylladex, object, player);
	}

	/** Try to fill a default card with as much of other as possible */
	public void grow(LinkedList<ISylladex> sylladices, ICaptchalogueable other)
	{
		for (int i = 0; i < sylladices.size() && !other.isEmpty(); i++)
			sylladices.get(i).grow(other);
	}

	/** Eject the contents of a default card */
	public void eject(LinkedList<ISylladex> sylladices, EntityPlayer player)
	{
		sylladices.getLast().eject(player);
	}

	/**
	 * Return whether this modus should perform autobalancing after each operation. // TODO: modus autobalance
	 */
	protected boolean doesAutobalance()
	{
		return false;
	}

	public String getUnlocalizedName()
	{
		return name;
	}

	@Override
	public void register(IForgeRegistry<Modus> registry)
	{
		setRegistryName(regName);
		registry.register(this);
	}

	/**
	 * Get a new ModusGuiContainer or a subtype with funky animations or whatever.
	 */
	@SideOnly(Side.CLIENT)
	public ModusGuiContainer getGuiContainer(ArrayList<CardGuiContainer.CardTextureIndex[]> textureIndices, ISylladex sylladex)
	{
		return new ModusGuiContainer(textureIndices, sylladex);
	}

	/**
	 * Get the index of the card texture that should be used by this modus.
	 */
	@SideOnly(Side.CLIENT)
	public abstract CardGuiContainer.CardTextureIndex getCardTextureIndex();

	@SubscribeEvent
	public static void onNewRegistry(RegistryEvent.NewRegistry event)
	{
		REGISTRY = (ForgeRegistry<Modus>) new RegistryBuilder<Modus>()
												  .setName(new ResourceLocation(Minestuck.MODID, "modus"))
												  .setType(Modus.class)
												  .create();
	}
}
