package com.mraof.minestuck.inventory.miniMachines;

import com.mraof.minestuck.inventory.SlotInput;
import com.mraof.minestuck.inventory.SlotOutput;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.tileentity.TileEntityMiniAlchemiter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ContainerMiniAlchemiter extends ContainerMiniSburbMachine
{
	private static final int alchemiterInputX = 27;
	private static final int alchemiterInputY = 20;
	private static final int alchemiterOutputX = 135;
	private static final int alchemiterOutputY = 20;

	public ContainerMiniAlchemiter(InventoryPlayer inventoryPlayer, TileEntityMiniAlchemiter te)
	{
		super(inventoryPlayer, te);

		addSlotToContainer(new SlotInput(tileEntity, 0, alchemiterInputX, alchemiterInputY, MinestuckItems.cruxiteDowel));
		addSlotToContainer(new SlotOutput(tileEntity, 1, alchemiterOutputX, alchemiterOutputY));
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
				if (itemstackOrig.getItem() == MinestuckItems.cruxiteDowel)
					result = mergeItemStack(itemstackOrig, 0, 1, false);
			}

			if (!result)
				return ItemStack.EMPTY;

			if (!itemstackOrig.isEmpty())
				slot.onSlotChanged();
		}

		return itemstack;
	}
}
