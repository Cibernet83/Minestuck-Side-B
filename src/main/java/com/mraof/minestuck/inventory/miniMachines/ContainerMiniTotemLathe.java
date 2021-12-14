package com.mraof.minestuck.inventory.miniMachines;

import com.mraof.minestuck.inventory.SlotInput;
import com.mraof.minestuck.inventory.SlotOutput;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.tileentity.TileEntityMiniTotemLathe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ContainerMiniTotemLathe extends ContainerMiniSburbMachine
{
	private static final int latheCard1X = 26;
	private static final int latheCard1Y = 25;
	private static final int latheCard2X = 26;
	private static final int latheCard2Y = 43;
	private static final int latheDowelX = 62;
	private static final int latheDowelY = 34;
	private static final int latheOutputX = 134;
	private static final int latheOutputY = 34;

	public ContainerMiniTotemLathe(InventoryPlayer inventoryPlayer, TileEntityMiniTotemLathe te)
	{
		super(inventoryPlayer, te);
		addSlotToContainer(new SlotInput(tileEntity, 0, latheCard1X, latheCard1Y, MinestuckItems.captchaCard));
		addSlotToContainer(new SlotInput(tileEntity, 1, latheCard2X, latheCard2Y, MinestuckItems.captchaCard));
		addSlotToContainer(new SlotInput(tileEntity, 2, latheDowelX, latheDowelY, MinestuckItems.cruxiteDowel));
		addSlotToContainer(new SlotOutput(tileEntity, 3, latheOutputX, latheOutputY));
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

			if (slotNumber <= 3)
			{
				//if it's a machine slot
				result = mergeItemStack(itemstackOrig,4, allSlots,false);
			}
			else if (slotNumber > 3)
			{
				//if it's an inventory slot with valid contents
				if (itemstackOrig.getItem() == MinestuckItems.captchaCard)
					result = mergeItemStack(itemstackOrig,0,2,false);
				else if (itemstackOrig.getItem() == MinestuckItems.cruxiteDowel)
					result = mergeItemStack(itemstackOrig,2,3,false);
			}

			if (!result)
				return ItemStack.EMPTY;

			if(!itemstackOrig.isEmpty())
				slot.onSlotChanged();
		}

		return itemstack;
	}
}
