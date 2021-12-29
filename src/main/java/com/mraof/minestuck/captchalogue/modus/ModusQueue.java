package com.mraof.minestuck.captchalogue.modus;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.captchalogue.sylladex.ISylladex;
import com.mraof.minestuck.captchalogue.sylladex.SylladexList;
import com.mraof.minestuck.client.gui.captchalogue.modus.GuiModusSettings;
import com.mraof.minestuck.client.gui.captchalogue.modus.GuiStackModusSettings;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.GuiSylladex;
import com.mraof.minestuck.util.MinestuckUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModusQueue extends Modus
{
	public ModusQueue(String name)
	{
		super(name);
	}

	@Override
	public <SYLLADEX extends ISylladex> ICaptchalogueable get(SylladexList<SYLLADEX> sylladices, NBTTagCompound settings, int[] slots, int i, boolean asCard)
	{
		SYLLADEX sylladex = sylladices.removeLastWithSlots();
		sylladices.addFirst(sylladex);
		slots[i] = 0;
		return sylladex.get(slots, i + 1, asCard);
	}

	@Override
	public <SYLLADEX extends ISylladex> boolean canGet(SylladexList<SYLLADEX> sylladices, NBTTagCompound settings, int[] slots, int i)
	{
		SYLLADEX last = sylladices.getLastWithSlots();
		return slots[i] == sylladices.indexOf(last) && last.canGet(slots, i + 1);
	}

	@Override
	public <SYLLADEX extends ISylladex> void put(SylladexList<SYLLADEX> sylladices, NBTTagCompound settings, ICaptchalogueable object)
	{
		getSylladexToPutInto(sylladices, settings).put(object);
	}

	@Override
	protected <SYLLADEX extends ISylladex> SYLLADEX getSylladexToPutInto(SylladexList<SYLLADEX> sylladices, NBTTagCompound settings)
	{
		SYLLADEX freeSylladex = getMostFreeSlotsSylladex(sylladices, settings);

		if (freeSylladex == null)
		{
			int oldSize = sylladices.size();
			freeSylladex = sylladices.getLastWithSlots();
			sylladices.remove(freeSylladex);
			sylladices.addFirst(freeSylladex);
			freeSylladex.eject();
			int newSize = sylladices.size();
			sylladices.remove(freeSylladex);
			sylladices.add(newSize - oldSize, freeSylladex);
		}

		return freeSylladex;
	}

	@Override
	protected <SYLLADEX extends ISylladex> SYLLADEX getMostFreeSlotsSylladex(SylladexList<SYLLADEX> sylladices, NBTTagCompound settings)
	{
		int mostFreeSlots = 0;
		SYLLADEX freeSylladex = null;
		for (SYLLADEX sylladex : MinestuckUtils.reverse(sylladices))
		{
			int slots = sylladex.getFreeSlots();
			if (slots > mostFreeSlots)
			{
				mostFreeSlots = slots;
				freeSylladex = sylladex;
			}
		}
		return freeSylladex;
	}

	@Override
	public <SYLLADEX extends ISylladex> void grow(SylladexList<SYLLADEX> sylladices, NBTTagCompound settings, ICaptchalogueable other)
	{
		SYLLADEX first = sylladices.getFirstWithObject();
		if (first != null)
			first.grow(other);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public CardGuiContainer.CardTextureIndex getNewCardTextureIndex(NBTTagCompound settings)
	{
		return new CardGuiContainer.CardTextureIndex(this, GuiSylladex.CARD_TEXTURE, 54);
	}

	@SideOnly(Side.CLIENT)
	public GuiModusSettings getSettingsGui(ItemStack modusStack)
	{
		return new GuiStackModusSettings(modusStack, new ResourceLocation(Minestuck.MODID, "textures/gui/fetch_modus/queue_modus.png"), false);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getPrimaryColor()
	{
		return 0xFF6000;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getDarkerColor()
	{
		return 0xCF560C;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getLighterColor()
	{
		return 0xFF8135;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getTextColor()
	{
		return 0x00F5A1;
	}
}