package com.mraof.minestuck.client.gui;

import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.alchemy.Grist;
import com.mraof.minestuck.alchemy.GristAmount;
import com.mraof.minestuck.alchemy.GristRegistry;
import com.mraof.minestuck.alchemy.GristSet;
import com.mraof.minestuck.block.MinestuckBlocks;
import com.mraof.minestuck.client.util.GuiUtil;
import com.mraof.minestuck.inventory.miniMachines.ContainerMiniAlchemiter;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.network.message.MessageMachineGristRequest;
import com.mraof.minestuck.tileentity.TileEntityMiniAlchemiter;
import com.mraof.minestuck.util.AlchemyUtils;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiMiniAlchemiter extends GuiMiniSburbMachine implements IGristSelectable
{
	public GuiMiniAlchemiter(InventoryPlayer inventoryPlayer, TileEntityMiniAlchemiter tileEntity) {
		super("alchemiter", new ContainerMiniAlchemiter(inventoryPlayer, tileEntity), tileEntity);

		progressX = 54;
		progressY = 23;
		progressWidth = 71;
		progressHeight = 10;
		goX = 72;
		goY = 31;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		fontRenderer.drawString(I18n.format("gui.alchemiter.name"), 8, 6, 4210752);
		//draws "Inventory" or your regional equivalent
		fontRenderer.drawString(I18n.format("container.inventory"), 8, ySize - 96 + 2, 4210752);
		if (!te.getStackInSlot(0).isEmpty())
		{
			//Render grist requirements
			ItemStack stack = AlchemyUtils.getDecodedItem(te.getStackInSlot(0));
			if (!(te.getStackInSlot(0).hasTagCompound() && te.getStackInSlot(0).getTagCompound().hasKey("contentID")))
				stack = new ItemStack(MinestuckBlocks.genericObject);

			GristSet set = GristRegistry.getGristConversion(stack);
			boolean useSelectedType = stack.getItem() == MinestuckItems.captchaCard;
			if (useSelectedType)
				set = new GristSet(te.selectedGrist, MinestuckConfig.clientCardCost);
			if (set != null && stack.isItemDamaged())
			{
				float multiplier = 1 - stack.getItem().getDamage(stack) / ((float) stack.getMaxDamage());
				for (GristAmount amount : set.getArray())
					set.setGrist(amount.getType(), (int) Math.ceil(amount.getAmount() * multiplier));
			}

			GuiUtil.drawGristBoard(set, useSelectedType ? GuiUtil.GristboardMode.ALCHEMITER_SELECT : GuiUtil.GristboardMode.ALCHEMITER, 9, 45, fontRenderer);

			List<String> tooltip = GuiUtil.getGristboardTooltip(set, mouseX - this.guiLeft, mouseY - this.guiTop, 9, 45, fontRenderer);
			if (tooltip != null)
				this.drawHoveringText(tooltip, mouseX - this.guiLeft, mouseY - this.guiTop, fontRenderer);

		}
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) throws IOException
	{
		super.mouseClicked(par1, par2, par3);
		if (par3 == 1)
		{
			if (goButton != null && goButton.mousePressed(this.mc, par1, par2))
			{
				goButton.playPressSound(this.mc.getSoundHandler());
				this.actionPerformed(goButton);
			}
		}
		else if (par3 == 0 && mc.player.inventory.getItemStack().isEmpty()
				&& te.getStackInSlot(0) != null && AlchemyUtils.getDecodedItem(te.getStackInSlot(0)).getItem() == MinestuckItems.captchaCard
				&& par1 >= guiLeft + 9 && par1 < guiLeft + 167 && par2 >= guiTop + 45 && par2 < guiTop + 70)
		{
			mc.currentScreen = new GuiGristSelector(this);
			mc.currentScreen.setWorldAndResolution(mc, width, height);
		}
	}

	@Override
	public void select(Grist grist)
	{
		te.selectedGrist = grist;

		mc.currentScreen = this;
		MinestuckNetwork.sendToServer(new MessageMachineGristRequest(grist));
	}

	@Override
	public void cancel()
	{
		mc.currentScreen = this;
	}
}
