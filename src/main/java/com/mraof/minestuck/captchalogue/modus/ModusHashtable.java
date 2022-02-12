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

public class ModusHashtable extends Modus
{
	// TODO: make this use the hashstack captchaloguable
	public ModusHashFunction hashFunction = new Vowel1Const2Hash(); // TODO: add settings

	public ModusHashtable(String name)
	{
		super(name);
	}

	@Override
	protected <SYLLADEX extends Sylladex> SYLLADEX getSylladexToPutInto(SylladexList<SYLLADEX> sylladices, NBTTagCompound settings, ICaptchalogueable object)
	{
		int hash = hash(object.getDisplayName(), hashFunction);
		SYLLADEX sylladex = sylladices.get(sylladices.transformIndexToWithSlots(hash % sylladices.sizeWithSlots()));
		if (sylladex.getFreeSlots() == 0)
			sylladex.eject();
		return sylladex;
	}

	@Override
	public <SYLLADEX extends Sylladex> void grow(SylladexList<SYLLADEX> sylladices, NBTTagCompound settings, ICaptchalogueable other)
	{
		int hash = hash(other.getDisplayName(), hashFunction);
		SYLLADEX sylladex = sylladices.get(sylladices.transformIndexToWithSlots(hash % sylladices.sizeWithSlots()));
		sylladex.grow(other);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public CardGuiContainer.CardTextureIndex getNewCardTextureIndex(NBTTagCompound settings)
	{
		return new CardGuiContainer.CardTextureIndex(this, GuiSylladex.CARD_TEXTURE, 55);
	}

	@SideOnly(Side.CLIENT)
	public GuiModusSettings getSettingsGui(ItemStack modusStack)
	{
		return new GuiStackModusSettings(modusStack, new ResourceLocation(Minestuck.MODID, "textures/gui/fetch_modus/hashtable_modus.png"), false);
	}

	public static int hash(String text, ModusHashFunction function)
	{
		text = text.toLowerCase().replaceAll("[^a-z]+", "");
		int hash = 0;
		for (int i = 0; i < text.length(); i++)
			hash += function.hashLetter(text.charAt(i) - 97);
		return hash;
	}

	public interface ModusHashFunction
	{
		int hashLetter(int letterIndex);
	}

	public static final class Vowel1Const2Hash implements ModusHashFunction
	{
		//												   a  b  c  d  e  f  g  h  i  j  k  l  m  n  o  p  q  r  s  t  u  v  w  x  y  z
		private static final int[] hashValues = new int[] {1, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2};
		@Override
		public int hashLetter(int letterIndex)
		{
			return hashValues[letterIndex];
		}
	}
}
