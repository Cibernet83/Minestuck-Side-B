package com.mraof.minestuck.client.gui.captchalogue.modus;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.captchalogue.modus.MinestuckModi;
import com.mraof.minestuck.captchalogue.modus.Modus;
import com.mraof.minestuck.client.gui.MinestuckGuiHandler;
import com.mraof.minestuck.item.MinestuckItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class GuiStackModusSettings extends GuiModusSettings
{
	protected static final int FILO_BUTTON_X = 31, FILO_BUTTON_Y = 117, FIFO_GAP = 3;
	protected static final int FILO_BUTTON_WIDTH = EJECT_BUTTON_WIDTH / 2, FILO_BUTTON_HEIGHT = EJECT_BUTTON_HEIGHT / 2;

	private boolean filo;

	public GuiStackModusSettings(ItemStack modusStack, ResourceLocation settingsGuiTexture, boolean filo)
	{
		super(modusStack, settingsGuiTexture);
		this.filo = filo;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		buttons.add(new ModusGuiButton(settingsGuiTexture, guiX + FILO_BUTTON_X, guiY + FILO_BUTTON_Y, EJECT_BUTTON_WIDTH, GUI_HEIGHT, FILO_BUTTON_WIDTH, FILO_BUTTON_HEIGHT, I18n.format("gui.filo"), modus.getTextColor(), filo)
		{
			@Override
			public void click()
			{
				switchModus(MinestuckModi.stack);
			}
		});
		buttons.add(new ModusGuiButton(settingsGuiTexture, guiX + FILO_BUTTON_X + FILO_BUTTON_WIDTH + FIFO_GAP, guiY + FILO_BUTTON_Y, EJECT_BUTTON_WIDTH, GUI_HEIGHT, FILO_BUTTON_WIDTH, FILO_BUTTON_HEIGHT, I18n.format("gui.fifo"), modus.getTextColor(), !filo)
		{
			@Override
			public void click()
			{
				switchModus(MinestuckModi.queue);
			}
		});
	}

	private void switchModus(Modus modus)
	{
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		modusStack.shrink(1);
		if (!player.getHeldItemMainhand().isEmpty())
			player.dropItem(true);
		ItemStack newModusStack = new ItemStack(MinestuckItems.modi.get(modus));
		newModusStack.setTagCompound(modusStack.getTagCompound());
		player.setHeldItem(EnumHand.MAIN_HAND, newModusStack);

		BlockPos pos = player.getPosition();
		player.openGui(Minestuck.instance, MinestuckGuiHandler.GuiId.FETCH_MODUS.ordinal(), player.getEntityWorld(), pos.getX(), pos.getY(), pos.getZ());
	}
}
