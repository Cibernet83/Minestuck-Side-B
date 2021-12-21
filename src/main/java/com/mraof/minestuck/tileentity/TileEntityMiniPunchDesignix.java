package com.mraof.minestuck.tileentity;

import com.mraof.minestuck.alchemy.CombinationRegistry;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.util.AlchemyUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class TileEntityMiniPunchDesignix extends TileEntityMiniSburbMachine
{
	@Override
	public int getSizeInventory()
	{
		return 3;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return i == 0 || i == 1 && itemstack.getItem() == MinestuckItems.captchaCard;
	}

	@Override
	public void markDirty()
	{
		this.progress = 0;
		this.ready = false;
		super.markDirty();
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		if (side == EnumFacing.UP)
			return new int[]{1};
		if (side == EnumFacing.DOWN)
			return new int[]{0, 2};
		else return new int[]{0};
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		if(index == 0)
			return !inv.get(2).isEmpty();	//Same but for the punch designix
		return true;
	}

	@Override
	public void processContents()
	{
		//Create a new card, using CombinationRegistry
		if (!inv.get(2).isEmpty())
		{
			decrStackSize(1, 1);
			if (!(inv.get(0).hasTagCompound() && inv.get(0).getTagCompound().hasKey("contentID")))
				decrStackSize(0, 1);
			this.inv.get(2).grow(1);
			return;
		}

		ItemStack outputItem = AlchemyUtils.getDecodedItemDesignix(inv.get(0));

		if (inv.get(1).hasTagCompound() && inv.get(1).getTagCompound().getBoolean("punched"))
			outputItem = CombinationRegistry.getCombination(outputItem, AlchemyUtils.getDecodedItem(inv.get(1)), CombinationRegistry.Mode.MODE_OR);
		if (outputItem.getItem().isDamageable())
			outputItem.setItemDamage(0);

		//Create card
		outputItem = AlchemyUtils.createCard(outputItem, true);

		setInventorySlotContents(2, outputItem);
		if (!(inv.get(0).hasTagCompound() && inv.get(0).getTagCompound().hasKey("contentID")))
			decrStackSize(0, 1);
		decrStackSize(1, 1);
	}

	@Override
	public boolean contentsValid()
	{
		if (!this.inv.get(0).isEmpty() && !inv.get(1).isEmpty())
		{
			ItemStack output = AlchemyUtils.getDecodedItemDesignix(inv.get(0));
			if (inv.get(1).hasTagCompound() && inv.get(1).getTagCompound().getBoolean("punched"))
			{
				output = CombinationRegistry.getCombination(output,
															AlchemyUtils.getDecodedItem(inv.get(1)), CombinationRegistry.Mode.MODE_OR);
			}
			if (output.isEmpty())
				return false;
			if (output.getItem().isDamageable())
				output.setItemDamage(0);
			output = AlchemyUtils.createCard(output, true);
			return (inv.get(2).isEmpty() || inv.get(2).getCount() < 16 && ItemStack.areItemStackTagsEqual(inv.get(2), output));
		}
		else
		{
			return false;
		}
	}

	@Override
	public String getName()
	{
		return "tile.miniPunchDesignix.name";
	}
}
