package com.mraof.minestuck.tileentity;

import com.mraof.minestuck.alchemy.Grist;
import com.mraof.minestuck.alchemy.MinestuckGrist;
import com.mraof.minestuck.util.IdentifierHandler;
import com.mraof.minestuck.util.IdentifierHandler.PlayerIdentifier;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class TileEntityMiniSburbMachine extends TileEntityMachine
{
	public PlayerIdentifier owner;
	public Grist selectedGrist = MinestuckGrist.build;
	public int color = -1;
	protected int ticks_since_update = 0;

	@Override
	public boolean allowOverrideStop()
	{
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);

		if (tagCompound.hasKey("gristType"))
		{
			this.selectedGrist = Grist.getTypeFromString(tagCompound.getString("gristType"));
			if (this.selectedGrist == null)
			{
				this.selectedGrist = MinestuckGrist.build;
			}
		}

		if (tagCompound.hasKey("color"))
			this.color = tagCompound.getInteger("color");

		if (IdentifierHandler.hasIdentifier(tagCompound, "owner"))
			owner = IdentifierHandler.load(tagCompound, "owner");
	}

	// We're going to want to trigger a block update every 20 ticks to have comparators pull data from the Alchemeter.
	@Override
	public void update()
	{
		if (world.isRemote)
			return;
		super.update();
	}

	@Override
	public boolean isAutomatic()
	{
		return false;
	}

	public int comparatorValue()
	{
		return 0;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		return isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		return true;
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return oldState.getBlock() != newState.getBlock();
	}
}
