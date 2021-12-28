package com.mraof.minestuck.util;

import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.advancements.MinestuckCriteriaTriggers;
import com.mraof.minestuck.captchalogue.captchalogueable.CaptchalogueableItemStack;
import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.captchalogue.sylladex.ISylladex;
import com.mraof.minestuck.captchalogue.sylladex.MultiSylladex;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.network.MinestuckChannelHandler;
import com.mraof.minestuck.network.MinestuckPacket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Random;

public class SylladexUtils
{
	public static Random rand;
	
	public static void launchItem(EntityPlayer player, ItemStack item)
	{
		EntityItem entity = new EntityItem(player.world, player.posX, player.posY+1, player.posZ, item);
		entity.motionX = rand.nextDouble() - 0.5;
		entity.motionZ = rand.nextDouble() - 0.5;
		entity.setDefaultPickupDelay();
		player.world.spawnEntity(entity);
	}

	public static void captchalogue(ICaptchalogueable object, EntityPlayerMP player)
	{
		if (object.isEmpty())
			return;
		MultiSylladex sylladex = getSylladex(player);
		if (sylladex == null)
			return;

		sylladex.grow(object);
		if (!object.isEmpty())
			sylladex.put(object, player);

		MinestuckCriteriaTriggers.CAPTCHALOGUE.trigger(player, sylladex, object);

		MinestuckPacket packet = MinestuckPacket.makePacket(MinestuckPacket.Type.SYLLADEX_DATA, sylladex.writeToNBT());
		MinestuckChannelHandler.sendToPlayer(packet, player);
	}
	
	public static void captchalouge(int slotIndex, EntityPlayerMP player) {
		// This statement is so that the server knows whether the item is in the hotbar or not because apparently THE "openContainer" CANT EDIT THE HOTBAR SLOTS.
		if(player.openContainer == player.inventoryContainer && InventoryPlayer.isHotbar(slotIndex)) {
			int hotbarIndex = slotIndex;
			ItemStack stack = player.inventory.mainInventory.get(hotbarIndex);
			captchalogue(new CaptchalogueableItemStack(stack), player);
			player.inventory.setInventorySlotContents(hotbarIndex, ItemStack.EMPTY);
		}
		else
		{
			Slot slot = player.openContainer.getSlot(slotIndex);
			ItemStack stack = slot.getStack();
			captchalogue(new CaptchalogueableItemStack(stack), player);
			slot.putStack(ItemStack.EMPTY);
		}
	}

	public static void fetch(EntityPlayerMP player, int[] slotStack, boolean asCard)
	{
		MultiSylladex sylladex = getSylladex(player);
		if(sylladex == null)
			return;

		boolean fetched = false;
		if (asCard)
		{
			ICaptchalogueable card = sylladex.tryGetEmptyCard(slotStack, 0);
			if (card != null)
			{
				card.fetch(player);
				fetched = true;
			}
		}
		if (!fetched)
			if (sylladex.canGet(slotStack, 0))
			{
				sylladex.get(slotStack, 0, asCard).fetch(player);
				fetched = true;
			}

		if (fetched)
		{
			MinestuckPacket packet = MinestuckPacket.makePacket(MinestuckPacket.Type.SYLLADEX_DATA, sylladex.writeToNBT());
			MinestuckChannelHandler.sendToPlayer(packet, player);
		}
	}

	public static boolean areItemStacksCompatible(ItemStack stackA, ItemStack stackB)
	{
		return stackA.getItem() == stackB.getItem() &&
					   (!stackA.getHasSubtypes() || stackA.getMetadata() == stackB.getMetadata()) &&
					   ItemStack.areItemStackTagsEqual(stackA, stackB) &&
					   stackA.isStackable();
	}

	public static boolean moveItemStack(ItemStack stack, ItemStack into)
	{
		if (!areItemStacksCompatible(stack, into))
			return false;

		int amount = stack.getCount();
		if (into.getCount() + amount > into.getMaxStackSize())
			amount = into.getMaxStackSize() - into.getCount();
		if (amount == 0)
			return false;

		into.grow(amount);
		stack.shrink(amount);
		return true;
	}
	
	public static void dropSylladexOnDeath(EntityPlayer player)
	{
		MultiSylladex sylladex = getSylladex(player);
		if(sylladex == null)
			return;

		boolean asCards = MinestuckConfig.dropItemsInCards && MinestuckConfig.sylladexDropMode != 0;
		sylladex.ejectAll(player, asCards, true);

		int emptyCardsLeft = sylladex.getTotalSlots();
		int emptyCardsToKeep = MinestuckConfig.sylladexDropMode == 2 ? 0 : MinestuckConfig.initialModusSize;
		int emptyCardStackSize = new ItemStack(MinestuckItems.captchaCard).getMaxStackSize();
		while (emptyCardsLeft > emptyCardsToKeep)
		{
			int toDrop = Math.max(emptyCardsLeft - emptyCardStackSize, emptyCardsToKeep);
			emptyCardsLeft -= emptyCardStackSize;
			launchItem(player, new ItemStack(MinestuckItems.captchaCard, toDrop)); // TODO: Drop these and the other items softly, with the rest of the death loot
		}

		setSylladex(player, ISylladex.newSylladex(sylladex.getModusLayers()));
	}
	
	public static MultiSylladex getSylladex(EntityPlayer player)
	{
		return MinestuckPlayerData.getData(player).sylladex;
	}
	
	public static void setSylladex(EntityPlayer player, MultiSylladex sylladex)
	{
		MinestuckPlayerData.getData(player).sylladex = sylladex;
		if(sylladex != null)
			MinestuckPlayerData.getData(player).givenModus = true;

		MinestuckPacket packet = MinestuckPacket.makePacket(MinestuckPacket.Type.SYLLADEX_DATA, getSylladex(player).writeToNBT());
		MinestuckChannelHandler.sendToPlayer(packet, player);
	}

	public static NBTTagCompound getModusSettings(ItemStack stack)
	{
		NBTTagCompound stackTag = stack.getTagCompound();
		if (stackTag == null)
			stack.setTagCompound(stackTag = new NBTTagCompound());
		if (!stackTag.hasKey("ModusSettings"))
			stackTag.setTag("ModusSettings", new NBTTagCompound());
		return stackTag.getCompoundTag("ModusSettings");
	}
}