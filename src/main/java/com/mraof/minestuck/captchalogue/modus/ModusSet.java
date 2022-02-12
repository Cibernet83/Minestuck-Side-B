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

import java.util.ArrayList;

public class ModusSet extends Modus
{
	public ModusSet(String name)
	{
		super(name);
	}

	@Override
	protected <SYLLADEX extends Sylladex> SYLLADEX getSylladexToPutInto(SylladexList<SYLLADEX> sylladices, NBTTagCompound settings, ICaptchalogueable object)
	{
		for (SYLLADEX sylladex : sylladices)
		{
			ArrayList<Integer> slots = sylladex.hitLooselyCompatibleObject(object);
			if (slots != null)
			{
				sylladex.eject(slots.stream().mapToInt(Integer::intValue).toArray(), 0);
				return sylladex;
			}
		}

		return super.getSylladexToPutInto(sylladices, settings, object);
	}

	@Override
	public boolean doesRestrictCards()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public CardGuiContainer.CardTextureIndex getNewCardTextureIndex(NBTTagCompound settings)
	{
		return new CardGuiContainer.CardTextureIndex(this, GuiSylladex.CARD_TEXTURE, 58);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiModusSettings getSettingsGui(ItemStack modusStack)
	{
		return new GuiStackModusSettings(modusStack, new ResourceLocation(Minestuck.MODID, "textures/gui/fetch_modus/set_modus.png"), false);
	}
}
