package com.mraof.minestuck.tileentity;

import com.mraof.minestuck.item.ItemModus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;

public class TileEntityModusControlDeck extends TileEntity
{
	public static final int WIDTH = 2;

	protected NonNullList<ItemStack> inventory = NonNullList.withSize(6, ItemStack.EMPTY);
	public ArrayList<Integer> lengths = new ArrayList<>();

	public boolean handleInsert(EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		int index = getCartridgeCount()-1;

		if(stack.isEmpty())
		{
			if(index >= 0)
			{
				ItemStack modus = inventory.get(index);
				inventory.set(index, ItemStack.EMPTY);

				if (player.getHeldItemMainhand().isEmpty())
					player.setHeldItem(EnumHand.MAIN_HAND, modus);
				else if (!player.inventory.addItemStackToInventory(modus))
					InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY() + 0.4, pos.getZ(), modus);
				else
					player.inventoryContainer.detectAndSendChanges();

				while (lengths.size() + 1 > MathHelper.ceil((float) getCartridgeCount() / (float) WIDTH) && !lengths.isEmpty())
					lengths.remove(lengths.size() - 1);

				return true;
			}
		}
		else if(canInsertStack(stack) && index+1 < inventory.size())
		{
			ItemStack modus = stack.copy();
			modus.setCount(1);
			stack.shrink(1);
			inventory.set(index+1, modus);

			while (lengths.size() + 1 < MathHelper.ceil((float) getCartridgeCount() / (float) WIDTH))
				lengths.add(4);

			return true;
		}

		return false;
	}

	public int getCartridgeCount()
	{
		int result = 0;
		for(ItemStack stack : inventory)
			if(canInsertStack(stack))
				result++;
		return result;
	}

	public boolean canInsertStack(ItemStack stack)
	{
		return !stack.isEmpty() && stack.getItem() instanceof ItemModus;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		inventory.clear();
		lengths.clear();

		ItemStackHelper.loadAllItems(compound.getCompoundTag("Inventory"), inventory);

		for (byte len : compound.getByteArray("Lengths"))
			lengths.add(len & 0xff);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound.setTag("Inventory", ItemStackHelper.saveAllItems(new NBTTagCompound(), inventory));

		byte[] lens = new byte[lengths.size()];
		for (int i = 0; i < lengths.size(); i++)
			lens[i] = lengths.get(i).byteValue();
		compound.setByteArray("Lengths", lens);

		return super.writeToNBT(compound);
	}

	public NonNullList<ItemStack> getInventory()
	{
		return inventory;
	}


	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tagCompound = this.getUpdateTag();
		return new SPacketUpdateTileEntity(this.pos, 2, tagCompound);
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		this.readFromNBT(tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		this.handleUpdateTag(pkt.getNbtCompound());
	}
}
