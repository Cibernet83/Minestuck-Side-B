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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;

public class TileEntityMiniCruxtruder extends TileEntitySburbMachine
{
	@Override
	public int getSizeInventory()
	{
		return 2;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
		tagCompound.setInteger("color", color);
		return tagCompound;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return i == 0 && itemstack.getItem() == MinestuckItems.rawCruxite;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		if (side == EnumFacing.DOWN)
			return new int[]{1};
		else return new int[]{0};
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
	public boolean contentsValid()
	{
		ItemStack stack1 = this.inv.get(1);
		return (!world.isBlockPowered(this.getPos()) && !this.inv.get(0).isEmpty() && (stack1.isEmpty() || stack1.getCount() < stack1.getMaxStackSize() && stack1.getItemDamage() == this.color + 1));
	}

	@Override
	public boolean isAutomatic()
	{
		return getMachineType() == BlockSburbMachine.MachineType.CRUXTRUDER;
	}

	@Override
	public void markDirty()
	{
		super.markDirty();
	}

	public BlockSburbMachine.MachineType getMachineType()
	{
		return BlockSburbMachine.MachineType.CRUXTRUDER;
	}
}
