package com.mraof.minestuck.tileentity;

import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.alchemy.*;
import com.mraof.minestuck.block.BlockSburbMachine;
import com.mraof.minestuck.block.MinestuckBlocks;
import com.mraof.minestuck.event.AlchemizeItemEvent;
import com.mraof.minestuck.event.AlchemizeItemMinichemiterEvent;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.tracker.MinestuckPlayerTracker;
import com.mraof.minestuck.util.MinestuckPlayerData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;

public class TileEntityMiniPunchDesignix extends TileEntitySburbMachine
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

	public BlockSburbMachine.MachineType getMachineType()
	{
		return BlockSburbMachine.MachineType.PUNCH_DESIGNIX;
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
		if(getMachineType() == BlockSburbMachine.MachineType.TOTEM_LATHE && (index == 0 || index == 1))
			return !inv.get(3).isEmpty();	//Only remove input cards when an output has been produced
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

		ItemStack outputItem = AlchemyRecipes.getDecodedItemDesignix(inv.get(0));

		if (inv.get(1).hasTagCompound() && inv.get(1).getTagCompound().getBoolean("punched"))
			outputItem = CombinationRegistry.getCombination(outputItem, AlchemyRecipes.getDecodedItem(inv.get(1)), CombinationRegistry.Mode.MODE_OR);
		if (outputItem.getItem().isDamageable())
			outputItem.setItemDamage(0);

		//Create card
		outputItem = AlchemyRecipes.createCard(outputItem, true);

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
			ItemStack output = AlchemyRecipes.getDecodedItemDesignix(inv.get(0));
			if (inv.get(1).hasTagCompound() && inv.get(1).getTagCompound().getBoolean("punched"))
			{
				output = CombinationRegistry.getCombination(output,
															AlchemyRecipes.getDecodedItem(inv.get(1)), CombinationRegistry.Mode.MODE_OR);
			}
			if (output.isEmpty())
				return false;
			if (output.getItem().isDamageable())
				output.setItemDamage(0);
			output = AlchemyRecipes.createCard(output, true);
			return (inv.get(2).isEmpty() || inv.get(2).getCount() < 16 && ItemStack.areItemStackTagsEqual(inv.get(2), output));
		}
		else
		{
			return false;
		}
	}
}
