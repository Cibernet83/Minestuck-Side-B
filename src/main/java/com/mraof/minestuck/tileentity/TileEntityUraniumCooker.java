package com.mraof.minestuck.tileentity;

import com.mraof.minestuck.item.MinestuckItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class TileEntityUraniumCooker extends TileEntityMachine
{
	private static HashMap<Item, ItemStack> radiations = new HashMap();
	private short fuel = 0;
	private short maxFuel = 128;

	//This is called mostly from AlchemyRecipeHandler
	public static void setRadiation(Item in, ItemStack out)
	{
		radiations.put(in, out);
	}

	public static void removeRadiation(Item in)
	{
		radiations.remove(in);
	}

	public static Map getRadiations()
	{
		return radiations;
	}

	@Override
	public boolean allowOverrideStop()
	{
		return true;
	}

	@Override
	public void readFromNBT(NBTTagCompound in)
	{
		super.readFromNBT(in);
		fuel = in.getShort("fuel");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound in)
	{
		super.writeToNBT(in);
		in.setShort("fuel", fuel);
		return in;
	}

	public void update()
	{
		processContents();
	}

	@Override
	public boolean isAutomatic()
	{
		return true;
	}

	@Override
	public boolean contentsValid()
	{
		if (world.isBlockPowered(this.getPos()))
		{
			return false;
		}

		ItemStack inputA = this.inv.get(0);
		ItemStack inputB = this.inv.get(1);
		ItemStack output = irradiate(inputB);
		return (inputA.getItem() == MinestuckItems.rawUranium && !inputB.isEmpty());
	}

	@Override
	public void processContents()
	{
		ItemStack item = inv.get(1);
		if (getFuel() <= getMaxFuel() - 32 && inv.get(0).getItem() == MinestuckItems.rawUranium)
		{    //Refill fuel
			fuel += 32;
			this.decrStackSize(0, 1);
		}
		if (canIrradiate())
		{
			ItemStack output = irradiate(this.getStackInSlot(1));
			if (inv.get(2).isEmpty() && fuel > 0)
			{
				this.setInventorySlotContents(2, output);
			}
			else
			{
				this.getStackInSlot(2).grow(output.getCount());
			}
			if (this.getStackInSlot(1).getItem() == Items.MUSHROOM_STEW)
			{
				this.setInventorySlotContents(1, new ItemStack(Items.BOWL));
			}
			else
			{
				this.decrStackSize(1, 1);
			}
			fuel--;
		}
	}

	private boolean canIrradiate()
	{
		ItemStack output = irradiate(inv.get(1));
		if (fuel > 0 && !inv.get(1).isEmpty() && !output.isEmpty())
		{
			ItemStack out = inv.get(2);
			if (out.isEmpty())
			{
				return true;
			}
			else return out.getMaxStackSize() >= output.getCount() + out.getCount() && out.isItemEqual(output);
		}
		return false;
	}

	private ItemStack irradiate(ItemStack input)
	{
		if (radiations.containsKey(input.getItem()))
		{
			input = radiations.get(input.getItem());
		}
		else
		{
			input = FurnaceRecipes.instance().getSmeltingResult(input);
		}

		return input.copy();
	}

	public short getFuel()
	{
		return fuel;
	}

	public void setFuel(short fuel)
	{
		this.fuel = fuel;
	}

	public short getMaxFuel()
	{
		return maxFuel;
	}

	public void setMaxFuel(short maxFuel)
	{
		this.maxFuel = maxFuel;
	}

	@Override
	public int getSizeInventory()
	{
		return 3;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return i != 0 || itemstack.getItem() == MinestuckItems.rawUranium;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		if (side == EnumFacing.UP)
			return new int[]{1};
		if (side == EnumFacing.DOWN)
			return new int[]{2};
		else return new int[]{0};
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		return true;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		return true;
	}

	@Override
	public String getName()
	{
		return "tile.uranium_cooker.name";
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
	{
		return oldState.getBlock() != newSate.getBlock();
	}
}
