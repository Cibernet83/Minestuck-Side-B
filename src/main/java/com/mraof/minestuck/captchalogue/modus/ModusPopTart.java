package com.mraof.minestuck.captchalogue.modus;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.captchalogue.captchalogueable.CaptchalogueableItemStack;
import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.captchalogue.sylladex.Sylladex;
import com.mraof.minestuck.captchalogue.sylladex.SylladexList;
import com.mraof.minestuck.client.gui.captchalogue.modus.GuiModusSettings;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.GuiSylladex;
import com.mraof.minestuck.item.ItemFood;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.util.ModusStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

public class ModusPopTart extends Modus
{
	protected ModusPopTart(String name)
	{
		super(name);
	}

	public static ItemFood.FoodItemConsumer getConsumer()
	{
		return (stack, worldIn, player) ->
		{
			ICaptchalogueable storedStack = ModusStorage.getStoredItem(stack);

			if (storedStack != null && !storedStack.isEmpty())
			{
				boolean isHandEmpty = player.getHeldItemMainhand().isEmpty();
				storedStack.fetch(player);
				if (!worldIn.isRemote)
					player.sendStatusMessage(new TextComponentTranslation("status.popTartEat", storedStack.getDisplayName()), true);
				if (isHandEmpty && !player.getHeldItemMainhand().isEmpty())
					return player.getHeldItemMainhand();
			}
			return null;
		};
	}

	@Override
	public <SYLLADEX extends Sylladex> ICaptchalogueable get(SylladexList<SYLLADEX> sylladices, NBTTagCompound settings, int[] slots, int i, boolean asCard)
	{
		ICaptchalogueable item = super.get(sylladices, settings, slots, i, asCard);

		if (asCard)
			return item;

		return new CaptchalogueableItemStack(ModusStorage.storeItem(new ItemStack(MinestuckItems.popTart), item));
	}

	@Override
	public String getName(boolean alone, boolean prefix, boolean plural)
	{
		return super.getName(alone, prefix, plural);
	}

	@Override
	public CardGuiContainer.CardTextureIndex getNewCardTextureIndex(NBTTagCompound settings)
	{
		return new CardGuiContainer.CardTextureIndex(this, GuiSylladex.CARD_TEXTURE, 7);
	}

	@Override
	public GuiModusSettings getSettingsGui(ItemStack modusStack)
	{
		return new GuiModusSettings(modusStack, new ResourceLocation(Minestuck.MODID, "textures/gui/fetch_modus/pop_tart_modus.png"));
	}

	@Override
	public int getPrimaryColor()
	{
		return 0x0E04FB;
	}

	@Override
	public int getTextColor()
	{
		return 0xE5E5E5;
	}
}
