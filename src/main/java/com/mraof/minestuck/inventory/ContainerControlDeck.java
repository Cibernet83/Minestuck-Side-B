package com.mraof.minestuck.inventory;

import com.mraof.minestuck.item.ItemModus;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.util.AlchemyUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerControlDeck extends Container
{
	public InventoryBasic inventory = new InventoryBasic("ModusInventory", false, 1);

	public ContainerControlDeck(EntityPlayer player)
	{
		addSlots(player);
	}

	private void addSlots(EntityPlayer player)
	{
		for (int i = 9; i < 36; i++)
			addSlotToContainer(new Slot(player.inventory, i, 9 + (i % 9) * 18, 63 + ((i - 9) / 9) * 18));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(player.inventory, i, 9 + i * 18, 121));
		addSlotToContainer(new Slot(this.inventory, 0, 81, 32)
		{
			@Override
			public boolean isItemValid(ItemStack stack)
			{
				return stack.getItem() instanceof ItemModus || (stack.getItem().equals(MinestuckItems.captchaCard) && AlchemyUtils.isAppendable(stack));
			}
		});
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber)
	{
		Slot slot = getSlot(slotNumber);
		int slotCount = inventorySlots.size();
		if (slot != null && slot.getHasStack())
		{
			ItemStack stack1 = slot.getStack();
			ItemStack stack2 = stack1.copy();
			if (slotNumber == slotCount - 1)
			{
				if (!mergeItemStack(stack1, 0, slotCount - 1, false))
					return ItemStack.EMPTY;
			}
			else
			{
				if (!getSlot(slotCount - 1).isItemValid(stack1) || !mergeItemStack(stack1, slotCount - 1, slotCount, false))
					return ItemStack.EMPTY;
			}

			if (stack1.isEmpty())
				slot.putStack(ItemStack.EMPTY);
			else slot.onSlotChanged();
			return stack2;
		}
		return ItemStack.EMPTY;
	}

	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		ItemStack stack = this.inventory.removeStackFromSlot(0);
		if (!stack.isEmpty())
			player.dropItem(stack, false);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return ((InventoryPlayer) this.getSlot(0).inventory).player == player;
	}

}