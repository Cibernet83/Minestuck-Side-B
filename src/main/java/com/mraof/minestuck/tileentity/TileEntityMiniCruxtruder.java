package com.mraof.minestuck.tileentity;

import com.mraof.minestuck.item.MinestuckItems;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileEntityMiniCruxtruder extends TileEntityMiniSburbMachine
{
	@Override
	public int getSizeInventory()
	{
		return 2;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return i == 0 && itemstack.getItem() == MinestuckItems.rawCruxite;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
		tagCompound.setInteger("color", color);
		return tagCompound;
	}

	@Override
	public boolean contentsValid()
	{
		ItemStack stack1 = this.inv.get(1);
		return (!world.isBlockPowered(this.getPos()) && !this.inv.get(0).isEmpty() && (stack1.isEmpty() || stack1.getCount() < stack1.getMaxStackSize() && stack1.getItemDamage() == this.color + 1));
	}

	@Override
	public void processContents()
	{
		// Process the Raw Cruxite

		if (this.inv.get(1).isEmpty())
			setInventorySlotContents(1, new ItemStack(MinestuckItems.cruxiteDowel, 1, color + 1));
		else this.inv.get(1).grow(1);
		decrStackSize(0, 1);

		this.progress++;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		if (side == EnumFacing.DOWN)
			return new int[]{1};
		else return new int[]{0};
	}

	@Override
	public boolean isAutomatic()
	{
		return true;
	}

	@Override
	public void markDirty()
	{
		super.markDirty();
	}

	@Override
	public String getName()
	{
		return "tile.miniCruxtruder.name";
	}
}
