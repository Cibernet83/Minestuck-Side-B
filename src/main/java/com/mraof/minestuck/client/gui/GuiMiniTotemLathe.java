package com.mraof.minestuck.client.gui;

import com.mraof.minestuck.inventory.miniMachines.ContainerMiniTotemLathe;
import com.mraof.minestuck.tileentity.TileEntityMiniTotemLathe;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiMiniTotemLathe extends GuiMiniSburbMachine
{
	public GuiMiniTotemLathe(InventoryPlayer inventoryPlayer, TileEntityMiniTotemLathe tileEntity)
	{
		super("lathe", new ContainerMiniTotemLathe(inventoryPlayer, tileEntity), tileEntity);

		progressX = 81;
		progressY = 33;
		progressWidth = 44;
		progressHeight = 17;
		goX = 85;
		goY = 53;
	}
}
