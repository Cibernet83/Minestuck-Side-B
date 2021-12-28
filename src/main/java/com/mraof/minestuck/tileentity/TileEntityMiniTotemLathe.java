package com.mraof.minestuck.tileentity;

import com.mraof.minestuck.alchemy.CombinationRegistry;
import com.mraof.minestuck.block.MinestuckBlocks;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.util.AlchemyUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class TileEntityMiniTotemLathe extends TileEntityMiniSburbMachine
{

	@Override
	public int getSizeInventory()
	{
		return 4;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		if (side == EnumFacing.UP)
			return new int[]{2};
		if (side == EnumFacing.DOWN)
			return new int[]{0, 1, 3};
		else return new int[]{0, 1};
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		if(index == 0 || index == 1)
			return !inv.get(3).isEmpty();	//Only remove input cards when an output has been produced
		return true;
	}

	@Override
	public void processContents()
	{
		if (!inv.get(3).isEmpty())
		{
			this.inv.get(3).grow(1);
			decrStackSize(2, 1);
			return;
		}

		ItemStack output;
		if (!inv.get(0).isEmpty() && !inv.get(1).isEmpty())
			if (!inv.get(0).hasTagCompound() || !inv.get(0).getTagCompound().getBoolean("punched") || !inv.get(1).hasTagCompound() || !inv.get(1).getTagCompound().getBoolean("punched"))
				output = new ItemStack(MinestuckBlocks.genericObject);
			else
				output = CombinationRegistry.getCombination(AlchemyUtils.getDecodedItem(inv.get(0)), AlchemyUtils.getDecodedItem(inv.get(1)), CombinationRegistry.Mode.MODE_AND);
		else
		{
			ItemStack input = inv.get(0).isEmpty() ? inv.get(1) : inv.get(0);
			if (!input.hasTagCompound() || !input.getTagCompound().getBoolean("punched"))
				output = new ItemStack(MinestuckBlocks.genericObject);
			else output = AlchemyUtils.getDecodedItem(input);
		}

		ItemStack outputDowel = output.getItem().equals(Item.getItemFromBlock(MinestuckBlocks.genericObject))
								? new ItemStack(MinestuckItems.cruxiteDowel) : AlchemyUtils.createEncodedItem(output, new ItemStack(MinestuckItems.cruxiteDowel));
		outputDowel.setItemDamage(inv.get(2).getItemDamage());

		setInventorySlotContents(3, outputDowel);
		decrStackSize(2, 1);
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return (i == 0 || i == 1) && itemstack.getItem() == MinestuckItems.captchaCard || i == 2 && itemstack.getItem() == MinestuckItems.cruxiteDowel;
	}

	@Override
	public boolean contentsValid()
	{
		if ((!inv.get(0).isEmpty() || !inv.get(1).isEmpty()) && !inv.get(2).isEmpty() && !(inv.get(2).hasTagCompound() && inv.get(2).getTagCompound().hasKey("contentID"))
			&& (inv.get(3).isEmpty() || inv.get(3).getCount() < inv.get(3).getMaxStackSize() && inv.get(3).getItemDamage() == inv.get(2).getItemDamage()))
		{
			if (!inv.get(0).isEmpty() && !inv.get(1).isEmpty())
			{
				if (!inv.get(0).hasTagCompound() || !inv.get(0).getTagCompound().getBoolean("punched") || !inv.get(1).hasTagCompound() || !inv.get(1).getTagCompound().getBoolean("punched"))
					return inv.get(3).isEmpty() || !(inv.get(3).hasTagCompound() && inv.get(3).getTagCompound().hasKey("contentID"));
				else
				{
					ItemStack output = CombinationRegistry.getCombination(AlchemyUtils.getDecodedItem(inv.get(0)), AlchemyUtils.getDecodedItem(inv.get(1)), CombinationRegistry.Mode.MODE_AND);
					return !output.isEmpty() && (inv.get(3).isEmpty() || AlchemyUtils.getDecodedItem(inv.get(3)).isItemEqual(output));
				}
			}
			else
			{
				ItemStack input = inv.get(0).isEmpty() ? inv.get(1) : inv.get(0);
				return (inv.get(3).isEmpty() || (AlchemyUtils.getDecodedItem(inv.get(3)).isItemEqual(AlchemyUtils.getDecodedItem(input))
												 || !(input.hasTagCompound() && input.getTagCompound().getBoolean("punched")) && !(inv.get(3).hasTagCompound() && inv.get(3).getTagCompound().hasKey("contentID"))));
			}
		}
		else return false;
	}

	@Override
	public void markDirty()
	{
		this.progress = 0;
		this.ready = false;
		super.markDirty();
	}

	@Override
	public String getName()
	{
		return "tile.miniTotemLathe.name";
	}
}
