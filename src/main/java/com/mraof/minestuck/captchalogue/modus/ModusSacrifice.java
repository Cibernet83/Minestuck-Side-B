package com.mraof.minestuck.captchalogue.modus;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.captchalogue.sylladex.Sylladex;
import com.mraof.minestuck.captchalogue.sylladex.SylladexList;
import com.mraof.minestuck.client.gui.captchalogue.modus.GuiModusSettings;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.GuiSylladex;
import com.mraof.minestuck.damage.SacrificeModusDamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModusSacrifice extends Modus
{
	public ModusSacrifice(String name)
	{
		super(name);
	}

	@Override
	public <SYLLADEX extends Sylladex> ICaptchalogueable get(SylladexList<SYLLADEX> sylladices, NBTTagCompound settings, int[] slots, int i, boolean asCard)
	{
		ICaptchalogueable rtn = super.get(sylladices, settings, slots, i, asCard);
		if (!rtn.isEmpty())
			sylladices.getPlayer().attackEntityFrom(new SacrificeModusDamageSource(), 1);
		return rtn;
	}

	@Override
	public <SYLLADEX extends Sylladex> void put(SylladexList<SYLLADEX> sylladices, NBTTagCompound settings, ICaptchalogueable object)
	{
		super.put(sylladices, settings, object);
		sylladices.getPlayer().heal(0.5f);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public CardGuiContainer.CardTextureIndex getNewCardTextureIndex(NBTTagCompound settings)
	{
		return new CardGuiContainer.CardTextureIndex(this, GuiSylladex.CARD_TEXTURE, 13);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiModusSettings getSettingsGui(ItemStack modusStack)
	{
		return new GuiModusSettings(modusStack, new ResourceLocation(Minestuck.MODID, "textures/gui/fetch_modus/sacrifice_modus.png"));
	}
}
