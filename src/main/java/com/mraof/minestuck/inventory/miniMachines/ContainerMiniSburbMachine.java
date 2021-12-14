package com.mraof.minestuck.inventory.miniMachines;

import com.mraof.minestuck.tileentity.TileEntityMiniSburbMachine;
import com.mraof.minestuck.util.IdentifierHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;

public abstract class ContainerMiniSburbMachine extends Container
{
	public TileEntityMiniSburbMachine tileEntity;
	private boolean operator = true;
	private int progress;
	
	public ContainerMiniSburbMachine(InventoryPlayer inventoryPlayer, TileEntityMiniSburbMachine te)
	{
		tileEntity = te;
		te.owner = IdentifierHandler.encode(inventoryPlayer.player);
		
		bindPlayerInventory(inventoryPlayer);
	}
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return tileEntity.isUsableByPlayer(player);
	}
	
	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer)
	{
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
						8 + j * 18, 84 + i * 18));
		
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
	}

	
	@Override
	public void detectAndSendChanges()
	{
		if(this.progress != tileEntity.progress && tileEntity.progress != 0)
			for(IContainerListener listener : listeners)
				listener.sendWindowProperty(this, 0, tileEntity.progress);	//The server should update and send the progress bar to the client because client and server ticks aren't synchronized
		this.progress = tileEntity.progress;
	}
	@Override
	public void updateProgressBar(int par1, int par2) 
	{
		if(par1 == 0)
		{
			tileEntity.progress = par2;
			return;
		}
	}
}