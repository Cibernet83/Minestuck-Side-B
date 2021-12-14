package com.mraof.minestuck.client.gui;

import com.mraof.minestuck.inventory.miniMachines.ContainerMiniPunchDesignix;
import com.mraof.minestuck.tileentity.TileEntityMiniPunchDesignix;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiMiniPunchDesignix extends GuiMiniSburbMachine
{
	public GuiMiniPunchDesignix(InventoryPlayer inventoryPlayer, TileEntityMiniPunchDesignix tileEntity)
	{
		super("designix", new ContainerMiniPunchDesignix(inventoryPlayer, tileEntity), tileEntity);

		progressX = 63;
		progressY = 38;
		progressWidth = 43;
		progressHeight = 17;
		goX = 66;
		goY = 55;
	}
}
