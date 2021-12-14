package com.mraof.minestuck.inventory.miniMachines;

import com.mraof.minestuck.inventory.SlotInput;
import com.mraof.minestuck.inventory.SlotOutput;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.tileentity.TileEntityMiniPunchDesignix;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ContainerMiniPunchDesignix extends ContainerMiniSburbMachine
{
	private static final int designixInputX = 44;
	private static final int designixInputY = 26;
	private static final int designixCardsX = 44;
	private static final int designixCardsY = 50;
	private static final int designixOutputX = 116;
	private static final int designixOutputY = 37;

	public ContainerMiniPunchDesignix(InventoryPlayer inventoryPlayer, TileEntityMiniPunchDesignix te)
	{
		super(inventoryPlayer, te);

		addSlotToContainer(new Slot(tileEntity, 0, designixInputX, designixInputY));
		addSlotToContainer(new SlotInput(tileEntity, 1, designixCardsX, designixCardsY, MinestuckItems.captchaCard));
		addSlotToContainer(new SlotOutput(tileEntity, 2, designixOutputX, designixOutputY));
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

			if (slotNumber <= 2)
			{
				//if it's a machine slot
				result = mergeItemStack(itemstackOrig,3,allSlots,false);
			}
			else if (slotNumber > 2)
			{
				//if it's an inventory slot with valid contents
				if (itemstackOrig.getItem() == MinestuckItems.captchaCard && (itemstackOrig.getTagCompound() == null
						|| !itemstackOrig.getTagCompound().hasKey("contentID") || itemstackOrig.getTagCompound().getBoolean("punched")))
					result = mergeItemStack(itemstackOrig,1,2,false);
				else result = mergeItemStack(itemstackOrig,0,1,false);
			}

			if (!result)
				return ItemStack.EMPTY;

			if(!itemstackOrig.isEmpty())
				slot.onSlotChanged();
		}

		return itemstack;
	}
}
