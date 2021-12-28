package com.mraof.minestuck.captchalogue.modus;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.captchalogue.sylladex.ISylladex;
import com.mraof.minestuck.client.gui.captchalogue.modus.GuiModusSettings;
import com.mraof.minestuck.client.gui.captchalogue.modus.GuiStackModusSettings;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.GuiSylladex;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;

public class ModusStack extends Modus
{
	public ModusStack(String name)
	{
		super(name);
	}

	@Override
	public <SYLLADEX extends ISylladex> ICaptchalogueable get(LinkedList<SYLLADEX> sylladices, NBTTagCompound settings, int[] slots, int i, boolean asCard)
	{
		SYLLADEX sylladex = sylladices.removeFirst();
		sylladices.addLast(sylladex);
		slots[i] = sylladices.size() - 1;
		return sylladex.get(slots, i + 1, asCard);
	}

	@Override
	public <SYLLADEX extends ISylladex> boolean canGet(LinkedList<SYLLADEX> sylladices, NBTTagCompound settings, int[] slots, int i)
	{
		return slots[i] == 0 && sylladices.getFirst().canGet(slots, i + 1);
	}

	@Override
	public <SYLLADEX extends ISylladex> void put(LinkedList<SYLLADEX> sylladices, NBTTagCompound settings, ICaptchalogueable object, EntityPlayer player)
	{
		SYLLADEX mostFreeSlotsSylladex = getSylladexWithMostFreeSlots(sylladices, settings, player);
		sylladices.remove(mostFreeSlotsSylladex);
		sylladices.addFirst(mostFreeSlotsSylladex);
		mostFreeSlotsSylladex.put(object, player);
	}

	@Override
	public <SYLLADEX extends ISylladex> void grow(LinkedList<SYLLADEX> sylladices, NBTTagCompound settings, ICaptchalogueable other)
	{
		sylladices.getFirst().grow(other);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public CardGuiContainer.CardTextureIndex getNewCardTextureIndex(NBTTagCompound settings)
	{
		return new CardGuiContainer.CardTextureIndex(GuiSylladex.CARD_TEXTURE, 53);
	}

	@SideOnly(Side.CLIENT)
	public GuiModusSettings getSettingsGui(ItemStack modusStack)
	{
		return new GuiStackModusSettings(modusStack, new ResourceLocation(Minestuck.MODID, "textures/gui/fetch_modus/stack_modus.png"), true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getPrimaryColor()
	{
		return 0xFF067C;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getDarkerColor()
	{
		return 0x9A2547;
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