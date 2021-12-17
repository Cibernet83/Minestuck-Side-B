package com.mraof.minestuck.util;

import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.advancements.MinestuckCriteriaTriggers;
import com.mraof.minestuck.inventory.captchalouge.ContainerCaptchaDeck;
import com.mraof.minestuck.inventory.captchalouge.ICaptchalogueable;
import com.mraof.minestuck.inventory.captchalouge.ISylladex;
import com.mraof.minestuck.inventory.captchalouge.Modus;
import com.mraof.minestuck.item.ItemBoondollars;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.network.MinestuckChannelHandler;
import com.mraof.minestuck.network.MinestuckPacket;
import com.mraof.minestuck.network.PacketCaptchaDeck;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Random;

public class SylladexUtils
{
	public static Random rand;

	/** TESTING */
	public static void put(ICaptchalogueable object, EntityPlayer player){
		ISylladex sylladex = getSylladex(player);
		sylladex.grow(object);
		if (object.isEmpty())
			return;
		sylladex.put(object, player);
	}

	/** TESTING */
	public static ICaptchalogueable get(int[] slotStack, boolean asCard, EntityPlayer player)
	{
		ISylladex sylladex = getSylladex(player);
		if (asCard)
		{
			ICaptchalogueable card = sylladex.tryGetEmptyCard(slotStack, 0);
			if (card != null)
				return card;
		}
		if (sylladex.canGet(slotStack, 0))
			return sylladex.get(slotStack, 0, asCard);
	}
	
	public static void launchItem(EntityPlayer player, ItemStack item)
	{
		EntityItem entity = new EntityItem(player.world, player.posX, player.posY+1, player.posZ, item);
		entity.motionX = rand.nextDouble() - 0.5;
		entity.motionZ = rand.nextDouble() - 0.5;
		entity.setDefaultPickupDelay();
		player.world.spawnEntity(entity);
	}
	
	public static void useItem(EntityPlayerMP player)
	{
		if(!(player.openContainer instanceof ContainerCaptchaDeck))
			 return;
		ContainerCaptchaDeck container = (ContainerCaptchaDeck) player.openContainer;
		if(container.inventory.getStackInSlot(0).isEmpty())
			return;
		ItemStack item = container.inventory.getStackInSlot(0);
		Modus modus = getSylladex(player);
		
		ResourceLocation type = getType(item);
		if(type != null)
		{
			if(modus == null)
			{
				MinestuckPlayerData.PlayerData data = MinestuckPlayerData.getData(player);
				modus = createInstance(type, Side.SERVER);
				modus.player = player;
				modus.initModus(null, data.givenModus ? 0 : MinestuckConfig.initialModusSize);
				data.givenModus = true;
				setSylladex(player, modus);
				container.inventory.setInventorySlotContents(0, ItemStack.EMPTY);
			}
			else
			{
				Modus oldModus = modus;
				ResourceLocation oldType = getType(oldModus.getClass());
				if(type.equals(oldType))
					return;
				modus = createInstance(type, Side.SERVER);
				modus.player = player;
				if(modus.canSwitchFrom(oldModus))
					modus.initModus(oldModus.getItems(), oldModus.getSize());
				else
				{
					for(ItemStack content : oldModus.getItems())
						if(!content.isEmpty())
							launchItem(player, content);
					modus.initModus(null, oldModus.getSize());
				}
				
				setSylladex(player, modus);
				container.inventory.setInventorySlotContents(0, getItem(oldType));
			}
			
			MinestuckCriteriaTriggers.CHANGE_MODUS.trigger(player, modus);
		}
		else if(item.getItem().equals(MinestuckItems.captchaCard) && !AlchemyUtils.isPunchedCard(item)
				&& modus != null)
		{
			ItemStack content = AlchemyUtils.getDecodedItem(item, true);
			
			System.out.println(content);
			int failed = 0;
			for(int i = 0; i < item.getCount(); i++)
				if(!modus.increaseSize())
					failed++;
			
			if(!content.isEmpty())
				for(int i = 0; i < item.getCount() - failed; i++)
				{
					ItemStack toPut = content.copy();
					if(!modus.putItemStack(toPut))
						insertCardsOrLaunchItem(player, toPut);
					else MinestuckCriteriaTriggers.CAPTCHALOGUE.trigger(player, modus, toPut);
				}
			
			if(failed == 0)
				container.inventory.setInventorySlotContents(0, ItemStack.EMPTY);
			else item.setCount(failed);
		}
		
		if(modus != null)
		{
			MinestuckPacket packet = MinestuckPacket.makePacket(MinestuckPacket.Type.CAPTCHA, PacketCaptchaDeck.DATA, writeToNBT(modus));
			MinestuckChannelHandler.sendToPlayer(packet, player);
		}
	}
	
	public static void captchalougeItem(EntityPlayerMP player)
	{
		ItemStack stack = player.getHeldItemMainhand();
		Modus modus = getSylladex(player);
		
		if(stack.getItem() == MinestuckItems.boondollars)
		{
			MinestuckPlayerData.addBoondollars(player, ItemBoondollars.getCount(stack));
			stack.setCount(0);
			return;
		}
		
		if(modus != null && !stack.isEmpty())
		{
			boolean card1 = false, card2 = true;
			if(stack.getItem() == MinestuckItems.captchaCard && AlchemyUtils.hasDecodedItem(stack)
					&& !AlchemyUtils.isPunchedCard(stack))
			{
				ItemStack newStack = AlchemyUtils.getDecodedItem(stack, true);
				if(!newStack.isEmpty())
				{
					card1 = true;
					stack = newStack;
					card2 = modus.increaseSize();
				}
			}
			if(modus.putItemStack(stack))
			{
				MinestuckCriteriaTriggers.CAPTCHALOGUE.trigger(player, modus, stack);
				if(!card2)
					launchItem(player, new ItemStack(MinestuckItems.captchaCard, 1));
				
				stack = player.getHeldItemMainhand();
				if(card1 && stack.getCount() > 1)
					stack.shrink(1);
				else player.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
			}
			else if(card1 && card2)
			{
				launchItem(player, stack);
				stack = player.getHeldItemMainhand();
				if(stack.getCount() == 1)
					player.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
				else stack.shrink(1);
			}
			MinestuckPacket packet = MinestuckPacket.makePacket(MinestuckPacket.Type.CAPTCHA, PacketCaptchaDeck.DATA, writeToNBT(modus));
			MinestuckChannelHandler.sendToPlayer(packet, player);
		}
		
	}
	
	public static void captchalougeInventoryItem (EntityPlayerMP player, int slotIndex) {
		ItemStack stack;
		Modus modus = getSylladex(player);
		System.out.println("Raw Slot: " + slotIndex);
		//This statement is so that the server knows whether the item is in the hotbar or not because apparently THE "openContainer" CANT EDIT THE HOTBAR SLOTS.
		if(player.openContainer.equals(player.inventoryContainer) && player.inventory.isHotbar(slotIndex)) {
			int hotbarIndex = slotIndex;
			
			stack = player.inventory.mainInventory.get(hotbarIndex);

			if(stack.getItem() == MinestuckItems.boondollars)
			{
				MinestuckPlayerData.addBoondollars(player, ItemBoondollars.getCount(stack));
				stack.setCount(0);
				return;
			}

			if(modus != null && !stack.isEmpty())
			{
				boolean card1 = false, card2 = true;
				if(stack.getItem() == MinestuckItems.captchaCard && AlchemyUtils.hasDecodedItem(stack)
						&& !AlchemyUtils.isPunchedCard(stack))
				{
					ItemStack newStack = AlchemyUtils.getDecodedItem(stack, true);
					if(!newStack.isEmpty())
					{
						card1 = true;
						stack = newStack;
						card2 = modus.increaseSize();
					}
				}
				if(modus.putItemStack(stack))
				{
					MinestuckCriteriaTriggers.CAPTCHALOGUE.trigger(player, modus, stack);
					if(!card2)
						launchItem(player, new ItemStack(MinestuckItems.captchaCard, 1));
					stack = player.inventory.mainInventory.get(hotbarIndex);
					if(card1 && stack.getCount() > 1)
						stack.shrink(1);
					else {
						player.inventory.setInventorySlotContents(hotbarIndex, ItemStack.EMPTY);
					}
				}
				else if(card1 && card2)
				{
					launchItem(player, stack);
					stack = player.inventory.mainInventory.get(hotbarIndex);
					if(stack.getCount() == 1) {
						player.inventory.setInventorySlotContents(hotbarIndex, ItemStack.EMPTY);
					} else stack.shrink(1);
				}
				MinestuckPacket packet = MinestuckPacket.makePacket(MinestuckPacket.Type.CAPTCHA, PacketCaptchaDeck.DATA, writeToNBT(modus));
				MinestuckChannelHandler.sendToPlayer(packet, player);
			}
		}
		else {
			Slot slot = player.openContainer.getSlot(slotIndex);
			stack = slot.getStack();

			if(stack.getItem() == MinestuckItems.boondollars)
			{
				MinestuckPlayerData.addBoondollars(player, ItemBoondollars.getCount(stack));
				stack.setCount(0);
				return;
			}

			if(modus != null && !stack.isEmpty())
			{
				boolean card1 = false, card2 = true;
				if(stack.getItem() == MinestuckItems.captchaCard && AlchemyUtils.hasDecodedItem(stack)
						&& !AlchemyUtils.isPunchedCard(stack))
				{
					ItemStack newStack = AlchemyUtils.getDecodedItem(stack, true);
					if(!newStack.isEmpty())
					{
						card1 = true;
						stack = newStack;
						card2 = modus.increaseSize();
					}
				}
				if(modus.putItemStack(stack))
				{
					MinestuckCriteriaTriggers.CAPTCHALOGUE.trigger(player, modus, stack);
					if(!card2)
						launchItem(player, new ItemStack(MinestuckItems.captchaCard, 1));
					stack = slot.getStack();
					if(card1 && stack.getCount() > 1)
						stack.shrink(1);
					else {
						slot.putStack(ItemStack.EMPTY);
					}
				}
				else if(card1 && card2)
				{
					launchItem(player, stack);
					stack = slot.getStack();
					if(stack.getCount() == 1) {
						slot.putStack(ItemStack.EMPTY);
					} else stack.shrink(1);
				}
				MinestuckPacket packet = MinestuckPacket.makePacket(MinestuckPacket.Type.CAPTCHA, PacketCaptchaDeck.DATA, writeToNBT(modus));
				MinestuckChannelHandler.sendToPlayer(packet, player);
			}
		}
	}

	public static void getItem(EntityPlayerMP player, int index, boolean asCard)
	{
		Modus modus = getSylladex(player);
		if(modus == null)
			return;
		ItemStack stack = modus.getItem(index, asCard);
		if(!stack.isEmpty())
		{
			ItemStack otherStack = player.getHeldItemMainhand();
			if(otherStack.isEmpty())
				player.setHeldItem(EnumHand.MAIN_HAND, stack);
			else if(canMergeItemStacks(stack, otherStack))
			{
				otherStack.grow(stack.getCount());
				stack.setCount(0);
			}
			else
			{
				boolean placed = false;
				for(int i = 0; i < player.inventory.mainInventory.size(); i++)
				{
					otherStack = player.inventory.mainInventory.get(i);
					if(otherStack.isEmpty())
						player.inventory.mainInventory.set(i, stack.copy());
					else if(canMergeItemStacks(stack, otherStack))
						otherStack.grow(stack.getCount());
					else continue;
					
					stack.setCount(0);
					placed = true;
					player.inventory.markDirty();
					player.inventoryContainer.detectAndSendChanges();
					break;
				}
				if(!placed)
					launchItem(player, stack);
			}
		}
		MinestuckPacket packet = MinestuckPacket.makePacket(MinestuckPacket.Type.CAPTCHA, PacketCaptchaDeck.DATA, writeToNBT(modus));
		MinestuckChannelHandler.sendToPlayer(packet, player);
	}
	
	public static void dropSylladex(EntityPlayer player)
	{
		Modus modus = getSylladex(player);
		
		if(modus == null)
			return;
		
		NonNullList<ItemStack> stacks = modus.getItems();
		int size = modus.getSize();
		int cardsToKeep = MinestuckConfig.sylladexDropMode == 2 ? 0 : MinestuckConfig.initialModusSize;
		
		if(!MinestuckConfig.dropItemsInCards || MinestuckConfig.sylladexDropMode == 0)
		{
			for(ItemStack stack : stacks)
				if(!stack.isEmpty())
					player.dropItem(stack, true, false);
		} else
			for(ItemStack stack : stacks)
				if(!stack.isEmpty())
					if(size > cardsToKeep)
					{
						ItemStack card = AlchemyUtils.createCard(stack, false);
						player.dropItem(card, true, false);
						size--;
					} else player.dropItem(stack, true, false);
		
		int stackLimit = MinestuckItems.captchaCard.getItemStackLimit(new ItemStack(MinestuckItems.captchaCard));
		if(MinestuckConfig.sylladexDropMode >= 1)
			for(; size > cardsToKeep; size = Math.max(size - stackLimit, cardsToKeep))
				player.dropItem(new ItemStack(MinestuckItems.captchaCard, Math.min(stackLimit, size - cardsToKeep)), true, false);
		
		if(MinestuckConfig.sylladexDropMode == 2)
		{
			player.dropItem(getItem(getType(modus.getClass())), true, false);
			setSylladex(player, null);
		} else modus.initModus(null, size);
		
		MinestuckPacket packet = MinestuckPacket.makePacket(MinestuckPacket.Type.CAPTCHA, PacketCaptchaDeck.DATA, writeToNBT(getSylladex(player)));
		MinestuckChannelHandler.sendToPlayer(packet, player);
	}
	
	public static ISylladex getSylladex(EntityPlayer player)
	{
		return MinestuckPlayerData.getData(player).sylladex;
	}
	
	public static void setSylladex(EntityPlayer player, ISylladex sylladex)
	{
		MinestuckPlayerData.getData(player).sylladex = sylladex;
		if(sylladex != null)
			MinestuckPlayerData.getData(player).givenModus = true;
	}
}