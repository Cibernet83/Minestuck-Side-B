package com.mraof.minestuck.tileentity;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.recipes.MachineChasisRecipes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;

import javax.annotation.Nullable;

public class TileEntityMachineChassis extends TileEntity implements IInventory, ITickable
{

	public boolean assembling = false;
	private NonNullList<ItemStack> inventory = NonNullList.withSize(5, ItemStack.EMPTY);
	private String customName;

	@Override
	public int getSizeInventory()
	{
		return inventory.size();
	}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack stack : inventory)
			if (!stack.isEmpty())
				return false;
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return inventory.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return ItemStackHelper.getAndSplit(inventory, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(inventory, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		ItemStack stackInSlot = inventory.get(index);
		inventory.set(index, stack);

		if (stack.getCount() > getInventoryStackLimit())
			stack.setCount(getInventoryStackLimit());
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.world.getTileEntity(this.pos) == this && player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return inventory.get(index).isEmpty();
	}

	@Override
	public int getField(int id)
	{
		return assembling ? 1 : 0;
	}

	@Override
	public void setField(int id, int value)
	{
		assembling = value == 1;
	}

	@Override
	public int getFieldCount()
	{
		return 1;
	}

	@Override
	public void clear()
	{
		inventory.clear();
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		inventory = NonNullList.withSize(5, ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, inventory);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		ItemStackHelper.saveAllItems(compound, inventory);
		return super.writeToNBT(compound);
	}

	@Nullable
	@Override
	public ITextComponent getDisplayName()
	{
		return new TextComponentString(this.getName());
	}

	@Override
	public String getName()
	{
		return this.hasCustomName() ? this.customName : I18n.translateToLocal("container.machineChassis");
	}

	@Override
	public boolean hasCustomName()
	{
		return this.customName != null && !this.customName.isEmpty();
	}

	public String getGuiID() {return Minestuck.MODID + ":machine_chasis";}

	public void setCustomName(String name) {this.customName = name;}

	@Override
	public void update()
	{
		if (assembling)
			assemble();
	}

	public void assemble()
	{
		if (canAssemble())
		{
			MachineChasisRecipes.Output output = MachineChasisRecipes.getOutput(invToArray());

			clear();
			world.destroyBlock(pos, false);

			if (output.isStack())
			{
				if (!world.isRemote)
				{
					EntityItem item = new EntityItem(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, output.getStack());
					item.setDefaultPickupDelay();
					world.spawnEntity(item);
				}
			}
			else world.setBlockState(pos, output.getBlockState());
		}
	}

	public boolean canAssemble()
	{
		return MachineChasisRecipes.recipeExists(invToArray());
	}

	public ItemStack[] invToArray()
	{
		return new ItemStack[]
					   {
							   inventory.get(0),
							   inventory.get(1),
							   inventory.get(2),
							   inventory.get(3),
							   inventory.get(4),
					   };

	}
}
