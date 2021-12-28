package com.mraof.minestuck.captchalogue.modus;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.captchalogue.sylladex.ISylladex;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.modus.GuiModusSettings;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.SylladexGuiContainer;
import com.mraof.minestuck.util.IRegistryObject;
import com.mraof.minestuck.util.MinestuckUtils;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
	private CardGuiContainer.CardTextureIndex cardTextureIndex;

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
	public <SYLLADEX extends ISylladex> ICaptchalogueable get(LinkedList<SYLLADEX> sylladices, NBTTagCompound settings, int[] slots, int i, boolean asCard)
	{
		return sylladices.get(slots[i]).get(slots, i + 1, asCard);
	}

	/**
	 * Return whether a card is valid to be retrieved from.
	 */
	public <SYLLADEX extends ISylladex> boolean canGet(LinkedList<SYLLADEX> sylladices, NBTTagCompound settings, int[] slots, int i)
	{
		return sylladices.get(slots[i]).canGet(slots, i + 1);
	}

	/**
	 * Put an object into a default card and perform some rearranging of sylladices if required. Modus#grow has already
	 * been called by this point.
	 */
	public <SYLLADEX extends ISylladex> void put(LinkedList<SYLLADEX> sylladices, NBTTagCompound settings, ICaptchalogueable object, EntityPlayer player)
	{
		getSylladexWithMostFreeSlots(sylladices, settings, player).put(object, player);
	}

	protected <SYLLADEX extends ISylladex> SYLLADEX getSylladexWithMostFreeSlots(LinkedList<SYLLADEX> sylladices, NBTTagCompound settings, EntityPlayer player)
	{
		int mostFreeSlots = 0;
		SYLLADEX mostFreeSlotsSylladex = null;
		for (SYLLADEX sylladex : sylladices)
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

		return mostFreeSlotsSylladex;
	}

	/** Try to fill a default card with as much of other as possible */
	public <SYLLADEX extends ISylladex> void grow(LinkedList<SYLLADEX> sylladices, NBTTagCompound settings, ICaptchalogueable other)
	{
		for (int i = 0; i < sylladices.size() && !other.isEmpty(); i++)
			sylladices.get(i).grow(other);
	}

	/** Eject the contents of a default card */
	public <SYLLADEX extends ISylladex> void eject(LinkedList<SYLLADEX> sylladices, NBTTagCompound settings, EntityPlayer player)
	{
		sylladices.getLast().eject(player);
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
	public SylladexGuiContainer getGuiContainer(ArrayList<CardGuiContainer.CardTextureIndex[]> textureIndices, ISylladex sylladex, NBTTagCompound settings)
	{
		return new SylladexGuiContainer(textureIndices, sylladex);
	}

	@SideOnly(Side.CLIENT)
	public String getName(boolean alone, boolean prefix, boolean plural)
	{
		if (alone)
			if (plural)
				return getIfKeyExists("modus." + name + ".alone.plural", I18n.format("modus.pluralize", getName()));
			else
				return I18n.format("modus." + name + ".alone.singular");
		else if (prefix)
			return getIfKeyExists("modus." + name + ".prefix", getName());
		else
			if (plural)
				return getIfKeyExists("modus." + name + ".suffix.plural", getName(true).toLowerCase());
			else
				return getIfKeyExists("modus." + name + ".suffix.singular", getName().toLowerCase());
	}

	@SideOnly(Side.CLIENT)
	public String getName(boolean plural)
	{
		return getName(true, false, plural);
	}

	@SideOnly(Side.CLIENT)
	public String getName()
	{
		return getName(false);
	}

	@SideOnly(Side.CLIENT)
	private static String getIfKeyExists(String key, String defaultString)
	{
		return I18n.hasKey(key) ? I18n.format(key) : defaultString;
	}

	@SideOnly(Side.CLIENT)
	public CardGuiContainer.CardTextureIndex getCardTextureIndex(NBTTagCompound settings)
	{
		if (cardTextureIndex == null)
			cardTextureIndex = getNewCardTextureIndex(settings);
		return cardTextureIndex;
	}

	@SideOnly(Side.CLIENT)
	public int getDarkerColor()
	{
		return MinestuckUtils.multiply(getPrimaryColor(), 0.85f);
	}

	@SideOnly(Side.CLIENT)
	public int getLighterColor()
	{
		return MinestuckUtils.add(getPrimaryColor(), 20);
	}

	@SubscribeEvent
	public static void onNewRegistry(RegistryEvent.NewRegistry event)
	{
		REGISTRY = (ForgeRegistry<Modus>) new RegistryBuilder<Modus>()
												  .setName(new ResourceLocation(Minestuck.MODID, "modus"))
												  .setType(Modus.class)
												  .create();
	}

	/**
	 * Get the index of the card texture that should be used by this modus.
	 */
	@SideOnly(Side.CLIENT)
	public abstract CardGuiContainer.CardTextureIndex getNewCardTextureIndex(NBTTagCompound settings);
	@SideOnly(Side.CLIENT)
	public abstract GuiModusSettings getSettingsGui(ItemStack modusStack);
	@SideOnly(Side.CLIENT)
	public abstract int getPrimaryColor();
	@SideOnly(Side.CLIENT)
	public abstract int getTextColor();
}
