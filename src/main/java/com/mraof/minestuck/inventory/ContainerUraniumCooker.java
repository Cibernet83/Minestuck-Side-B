package com.mraof.minestuck.inventory;

import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.tileentity.TileEntityUraniumCooker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ContainerUraniumCooker extends Container
{

	private static final int uraniumInputX = 38;
	private static final int uraniumInputY = 51;
	private static final int itemInputX = 38;
	private static final int itemInputY = 22;
	private static final int itemOutputX = 117;
	private static final int itemOutputY = 35;

	public TileEntityUraniumCooker tileEntity;
	private int progress;

	public ContainerUraniumCooker(InventoryPlayer inventoryPlayer, TileEntityUraniumCooker te)
	{
		tileEntity = te;

		addSlotToContainer(new SlotInput(tileEntity, 0, uraniumInputX, uraniumInputY, MinestuckItems.rawUranium));
		addSlotToContainer(new Slot(tileEntity, 1, itemInputX, itemInputY));
		addSlotToContainer(new SlotOutput(tileEntity, 2, itemOutputX, itemOutputY));

		bindPlayerInventory(inventoryPlayer);
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
		if (this.progress != tileEntity.progress && tileEntity.progress != 0)
			for (IContainerListener listener : listeners)
				listener.sendWindowProperty(this, 0, tileEntity.progress);    //The server should update and send the progress bar to the client because client and server ticks aren't synchronized
		this.progress = tileEntity.progress;
	}

	@Nonnull
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(slotNumber);
		int allSlots = this.inventorySlots.size();

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstackOrig = slot.getStack();
			itemstack = itemstackOrig.copy();
			boolean result = false;

			if (slotNumber == 0)    //Shift-clicking from the uranium input
			{
				result = mergeItemStack(itemstackOrig, 3, allSlots, false);    //Send into the inventory
			}
			else if (slotNumber == 1)    //Shift-clicking from the item input
			{
				result = mergeItemStack(itemstackOrig, 3, allSlots, false);    //Send into the inventory

			}
			else if (slotNumber == 2)    //Shift-clicking from the output slot
			{
				if (itemstackOrig.getItem() == MinestuckItems.rawUranium)
					result = mergeItemStack(itemstackOrig, 0, 1, false);    //Send the uranium back to the uranium input
				else
					result = mergeItemStack(itemstackOrig, 3, allSlots, false);    //Send the non-uranium to the inventory

			}
			else    //Shift-clicking from the inventory
			{
				if (itemstackOrig.getItem() == MinestuckItems.rawUranium)
				{
					result = mergeItemStack(itemstackOrig, 0, 1, false);    //Send the uranium to the uranium input
				}
				else
				{
					result = mergeItemStack(itemstackOrig, 1, 2, false);    //Send the non-uranium to the other input
				}

			}

			if (!result)
				return ItemStack.EMPTY;

			if (!itemstackOrig.isEmpty())
				slot.onSlotChanged();
		}

		return itemstack;
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		if (par1 == 0)
		{
			tileEntity.progress = par2;
			return;
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return tileEntity.isUsableByPlayer(player);
	}
}