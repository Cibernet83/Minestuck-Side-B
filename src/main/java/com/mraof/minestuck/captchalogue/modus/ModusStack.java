package com.mraof.minestuck.captchalogue.modus;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.captchalogue.sylladex.Sylladex;
import com.mraof.minestuck.captchalogue.sylladex.SylladexList;
import com.mraof.minestuck.client.gui.captchalogue.modus.GuiModusSettings;
import com.mraof.minestuck.client.gui.captchalogue.modus.GuiStackModusSettings;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.GuiSylladex;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModusStack extends Modus
{
	public ModusStack(String name)
	{
		super(name);
	}

	@Override
	public <SYLLADEX extends Sylladex> ICaptchalogueable get(SylladexList<SYLLADEX> sylladices, NBTTagCompound settings, int[] slots, int i, boolean asCard)
	{
		SYLLADEX sylladex = sylladices.removeFirstWithSlots();
		sylladices.addLast(sylladex);
		slots[i] = sylladices.size() - 1;
		return sylladex.get(slots, i + 1, asCard);
	}

	@Override
	public <SYLLADEX extends Sylladex> boolean canGet(SylladexList<SYLLADEX> sylladices, NBTTagCompound settings, int i)
	{
		return i == sylladices.indexOf(sylladices.getFirstWithSlots());
	}

	@Override
	public <SYLLADEX extends Sylladex> void put(SylladexList<SYLLADEX> sylladices, NBTTagCompound settings, ICaptchalogueable object)
	{
		SYLLADEX sylladex = getSylladexToPutInto(sylladices, settings, object);
		sylladices.remove(sylladex);
		sylladices.addFirst(sylladex);
		sylladex.put(object);
	}

	@Override
	protected <SYLLADEX extends Sylladex> SYLLADEX getSylladexToPutInto(SylladexList<SYLLADEX> sylladices, NBTTagCompound settings, ICaptchalogueable object)
	{
		SYLLADEX freeSylladex = getMostFreeSlotsSylladex(sylladices, settings);

		if (freeSylladex == null)
		{
			freeSylladex = sylladices.getLastWithSlots();
			freeSylladex.eject();
		}

		return freeSylladex;
	}

	@Override
	public <SYLLADEX extends Sylladex> void grow(SylladexList<SYLLADEX> sylladices, NBTTagCompound settings, ICaptchalogueable other)
	{
		SYLLADEX first = sylladices.getFirstWithSlots();
		if (first != null)
			first.grow(other);
	}

	@Override
	public Modus getAlternate()
	{
		return MinestuckModi.queue;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public CardGuiContainer.CardTextureIndex getNewCardTextureIndex(NBTTagCompound settings)
	{
		return new CardGuiContainer.CardTextureIndex(this, GuiSylladex.CARD_TEXTURE, 53);
	}

	@SideOnly(Side.CLIENT)
	public GuiModusSettings getSettingsGui(ItemStack modusStack)
	{
		return new GuiStackModusSettings(modusStack, new ResourceLocation(Minestuck.MODID, "textures/gui/fetch_modus/stack_modus.png"), true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getDarkerColor()
	{
		return 0x9A2547;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getPrimaryColor()
	{
		return 0xFF067C;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getLighterColor()
	{
		return 0xFF5D99;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getTextColor()
	{
		return 0xB7FFFD;
	}
}