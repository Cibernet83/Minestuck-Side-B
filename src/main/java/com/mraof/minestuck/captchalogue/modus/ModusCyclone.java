package com.mraof.minestuck.captchalogue.modus;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.captchalogue.sylladex.MultiSylladex;
import com.mraof.minestuck.captchalogue.sylladex.Sylladex;
import com.mraof.minestuck.captchalogue.sylladex.SylladexList;
import com.mraof.minestuck.client.gui.captchalogue.modus.GuiModusSettings;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.GuiSylladex;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.MultiSylladexGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.MultiSylladexGuiContainerCyclone;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModusCyclone extends Modus
{
	public static final float CYCLE_SPEED = 0.01f;

	public ModusCyclone(String name)
	{
		super(name);
	}

	@Override
	public <SYLLADEX extends Sylladex> boolean canGet(SylladexList<SYLLADEX> sylladices, NBTTagCompound settings, int i)
	{
		return sylladices.sizeWithSlots() - 1 - (int)(sylladices.get(i).getPlayer().ticksExisted * CYCLE_SPEED) % sylladices.sizeWithSlots() == sylladices.transformIndexToWithSlots(i);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public MultiSylladexGuiContainer getGuiContainer(MultiSylladex<? extends Sylladex> sylladex, CardGuiContainer.CardTextureIndex[] firstTextureIndices, CardGuiContainer.CardTextureIndex[] lowerTextureIndices)
	{
		return new MultiSylladexGuiContainerCyclone(sylladex, firstTextureIndices, lowerTextureIndices);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public CardGuiContainer.CardTextureIndex getNewCardTextureIndex(NBTTagCompound settings)
	{
		return new CardGuiContainer.CardTextureIndex(this, GuiSylladex.CARD_TEXTURE, 27);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiModusSettings getSettingsGui(ItemStack modusStack)
	{
		return new GuiModusSettings(modusStack, new ResourceLocation(Minestuck.MODID, "textures/gui/fetch_modus/cyclone_modus.png"));
	}
}
