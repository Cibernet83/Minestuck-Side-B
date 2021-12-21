package com.mraof.minestuck.inventory.captchalouge;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.client.gui.captchalogue.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.ModusGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.SylladexGuiHandler;
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
	private CardGuiContainer.CardTextureIndex cardTextureIndex;

	private final String name, regName;

	protected Modus(String name)
	{
		this.name = name;
		this.regName = IRegistryObject.unlocToReg(name);
		MinestuckModi.modi.add(this);
	}

	public abstract ICaptchalogueable get(LinkedList<ISylladex> sylladices, int[] slots, int i, boolean asCard);
	public abstract boolean canGet(LinkedList<ISylladex> sylladicies, int[] slots, int i);

	public abstract boolean put(LinkedList<ISylladex> sylladices, ICaptchalogueable object, EntityPlayer player);

	/** Try to fill a default card with as much of other as possible */
	public abstract void grow(LinkedList<ISylladex> sylladices, ICaptchalogueable other);

	/** Eject the contents of a default card */
	public abstract void eject(LinkedList<ISylladex> sylladices, EntityPlayer player);

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

	@SideOnly(Side.CLIENT)
	public abstract ModusGuiContainer getGuiContainer(ArrayList<CardGuiContainer.CardTextureIndex[]> textureIndices, ISylladex sylladex);

	@SideOnly(Side.CLIENT)
	public CardGuiContainer.CardTextureIndex getCardTextureIndex()
	{
		if (cardTextureIndex == null)
			cardTextureIndex = new CardGuiContainer.CardTextureIndex(SylladexGuiHandler.CARD_TEXTURE, 53);
		return cardTextureIndex;
	}

	@SubscribeEvent
	public static void onNewRegistry(RegistryEvent.NewRegistry event)
	{
		REGISTRY = (ForgeRegistry<Modus>) new RegistryBuilder<Modus>()
												  .setName(new ResourceLocation(Minestuck.MODID, "modus"))
												  .setType(Modus.class)
												  .create();
	}
}
