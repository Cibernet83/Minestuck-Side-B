package com.mraof.minestuck.inventory.miniMachines;

import com.mraof.minestuck.inventory.SlotInput;
import com.mraof.minestuck.inventory.SlotOutput;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.tileentity.TileEntityMiniCruxtruder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ContainerMiniCruxtruder extends ContainerMiniSburbMachine
{
	private static final int cruxtruderInputX = 79;
	private static final int cruxtruderInputY = 57;
	private static final int cruxtruderOutputX = 79;
	private static final int cruxtruderOutputY = 19;

	public ContainerMiniCruxtruder(InventoryPlayer inventoryPlayer, TileEntityMiniCruxtruder te)
	{
		super(inventoryPlayer, te);

		addSlotToContainer(new SlotInput(tileEntity, 0, cruxtruderInputX, cruxtruderInputY, MinestuckItems.rawCruxite));
		addSlotToContainer(new SlotOutput(tileEntity, 1, cruxtruderOutputX, cruxtruderOutputY));
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

			if (slotNumber <= 1)
			{
				//if it's a machine slot
				result = mergeItemStack(itemstackOrig, 2, allSlots, false);
			}
			else if (slotNumber > 1)
			{
				//if it's an inventory slot with valid contents
				//Debug.print("item ID of " + itemstackOrig.itemID + ". Expected " + Minestuck.rawCruxite.itemID);
				if (itemstackOrig.getItem() == MinestuckItems.rawCruxite)
				{
					//Debug.print("Transferring...");
					result = mergeItemStack(itemstackOrig, 0, 1, false);
				}
			}

			if (!result)
				return ItemStack.EMPTY;

			if (!itemstackOrig.isEmpty())
				slot.onSlotChanged();
		}

		return itemstack;
	}
}
