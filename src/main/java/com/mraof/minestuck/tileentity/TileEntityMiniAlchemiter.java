package com.mraof.minestuck.tileentity;

import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.alchemy.*;
import com.mraof.minestuck.block.MinestuckBlocks;
import com.mraof.minestuck.event.AlchemizeItemEvent;
import com.mraof.minestuck.event.AlchemizeItemMinichemiterEvent;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.tracker.MinestuckPlayerTracker;
import com.mraof.minestuck.util.AlchemyUtils;
import com.mraof.minestuck.util.MinestuckPlayerData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;

public class TileEntityMiniAlchemiter extends TileEntityMiniSburbMachine
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
		tagCompound.setString("gristType", selectedGrist.getRegistryName().toString());
		if (owner != null)
			owner.saveToNBT(tagCompound, "owner");
		return tagCompound;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return i == 0 && itemstack.getItem() == MinestuckItems.cruxiteDowel;
	}

	@Override
	public boolean allowOverrideStop()
	{
		return true;
	}

	@Override
	public void markDirty()
	{
		super.markDirty();
	}

	@Override
	public int comparatorValue()
	{
		if (getStackInSlot(0) != null && owner != null)
		{
			ItemStack newItem = AlchemyUtils.getDecodedItem(getStackInSlot(0));
			if (newItem.isEmpty())
				if (!getStackInSlot(0).hasTagCompound() || !getStackInSlot(0).getTagCompound().hasKey("contentID"))
					newItem = new ItemStack(MinestuckBlocks.genericObject);
				else return 0;
			if (!getStackInSlot(1).isEmpty() && (getStackInSlot(1).getItem() != newItem.getItem() || getStackInSlot(1).getItemDamage() != newItem.getItemDamage() || getStackInSlot(1).getMaxStackSize() <= getStackInSlot(1).getCount()))
			{
				return 0;
			}
			GristSet cost = GristRegistry.getGristConversion(newItem);
			if (newItem.getItem() == MinestuckItems.captchaCard)
				cost = new GristSet(selectedGrist, MinestuckConfig.cardCost);
			if (cost != null && newItem.isItemDamaged())
			{
				float multiplier = 1 - newItem.getItem().getDamage(newItem) / ((float) newItem.getMaxDamage());
				for (GristAmount amount : cost.getArray())
				{
					cost.setGrist(amount.getType(), (int) Math.ceil(amount.getAmount() * multiplier));
				}
			}
			// We need to run the check 16 times. Don't want to hammer the game with too many of these, so the comparators are only told to update every 20 ticks.
			// Additionally, we need to check if the item in the slot is empty. Otherwise, it will attempt to check the cost for air, which cannot be alchemized anyway.
			if (cost != null && !getStackInSlot(0).isEmpty())
			{
				GristSet scale_cost;
				for (int lvl = 1; lvl <= 17; lvl++)
				{
					// We went through fifteen item cost checks and could still afford it. No sense in checking more than this.
					if (lvl == 17)
					{
						return 15;
					}
					// We need to make a copy to preserve the original grist amounts and avoid scaling values that have already been scaled. Keeps scaling linear as opposed to exponential.
					scale_cost = cost.copy().scaleGrist(lvl);
					if (!GristHelper.canAfford(MinestuckPlayerData.getGristSet(owner), scale_cost))
					{
						return lvl - 1;
					}
				}
				return 0;
			}
		}
		return 0;
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
		ItemStack newItem = AlchemyUtils.getDecodedItem(this.inv.get(0));

		if (newItem.isEmpty())
			newItem = new ItemStack(MinestuckBlocks.genericObject);

		GristSet cost = GristRegistry.getGristConversion(newItem);

		AlchemizeItemEvent alchemizeItemEvent = new AlchemizeItemMinichemiterEvent(world, inv.get(0), newItem, this);
		if (MinecraftForge.EVENT_BUS.post(alchemizeItemEvent))
			return;
		newItem = alchemizeItemEvent.getResultItem();

		if (inv.get(1).isEmpty())
		{
			setInventorySlotContents(1, newItem);
		} else
		{
			this.inv.get(1).grow(1);
		}

		EntityPlayerMP player = owner.getPlayer();
		if (player != null)
			AlchemyUtils.giveAlchemyExperience(newItem, player);

		if (newItem.getItem() == MinestuckItems.captchaCard)
			cost = new GristSet(selectedGrist, MinestuckConfig.cardCost);
		if (newItem.isItemDamaged())
		{
			float multiplier = 1 - newItem.getItem().getDamage(newItem) / ((float) newItem.getMaxDamage());
			for (GristAmount amount : cost.getArray())
			{
				cost.setGrist(amount.getType(), (int) Math.ceil(amount.getAmount() * multiplier));
			}
		}
		GristHelper.decrease(owner, cost);
		MinestuckPlayerTracker.updateGristCache(owner);
	}

	@Override
	public void update()
	{
		if (this.ticks_since_update == 20)
		{
			world.updateComparatorOutputLevel(this.getPos(), this.blockType);
			this.ticks_since_update = 0;
		}
		else
		{
			this.ticks_since_update++;
		}
		super.update();
	}

	@Override
	public boolean contentsValid()
	{
		if (!world.isBlockPowered(this.getPos()) && !this.inv.get(0).isEmpty() && this.owner != null)
		{
			//Check owner's cache: Do they have everything they need?
			ItemStack newItem = AlchemyUtils.getDecodedItem(this.inv.get(0));
			if (newItem.isEmpty())
				if (!inv.get(0).hasTagCompound() || !inv.get(0).getTagCompound().hasKey("contentID"))
					newItem = new ItemStack(MinestuckBlocks.genericObject);
				else return false;
				if (!inv.get(1).isEmpty() && (inv.get(1).getItem() != newItem.getItem() || inv.get(1).getItemDamage() != newItem.getItemDamage() || inv.get(1).getMaxStackSize() <= inv.get(1).getCount()))
				{
					return false;
				}
				GristSet cost = GristRegistry.getGristConversion(newItem);
				if (newItem.getItem() == MinestuckItems.captchaCard)
					cost = new GristSet(selectedGrist, MinestuckConfig.cardCost);
				if (cost != null && newItem.isItemDamaged())
				{
					float multiplier = 1 - newItem.getItem().getDamage(newItem) / ((float) newItem.getMaxDamage());
					for (GristAmount amount : cost.getArray())
					{
						cost.setGrist(amount.getType(), (int) Math.ceil(amount.getAmount() * multiplier));
					}
				}
				return GristHelper.canAfford(MinestuckPlayerData.getGristSet(this.owner), cost);
		}
		else
			{
				return false;
			}
	}

	@Override
	public String getName()
	{
		return "tile.miniAlchemiter.name";
	}
}
