package com.mraof.minestuck.captchalogue.sylladex;

import com.mraof.minestuck.client.gui.captchalogue.sylladex.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.SylladexGuiContainer;
import com.mraof.minestuck.captchalogue.captchalogueable.CaptchalogueableItemStack;
import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.util.AlchemyUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class CardSylladex implements ISylladex
{
	private final BottomSylladex owner;
	private ICaptchalogueable object;
	private final ArrayList<SylladexGuiContainer> containers = new ArrayList<>();
	private boolean markedForDeletion = false;

	public CardSylladex(BottomSylladex owner, ICaptchalogueable object)
	{
		this.owner = owner;
		this.object = object;
	}

	public CardSylladex(BottomSylladex owner, NBTTagCompound nbt)
	{
		this(owner, (ICaptchalogueable) null);
		readFromNBT(nbt);
	}

	@Override
	public ICaptchalogueable get(int[] slots, int index, boolean asCard)
	{
		checkSlots(slots, index);
		ICaptchalogueable object = asCard ?
										   new CaptchalogueableItemStack(AlchemyUtils.setCardModi(this.object.captchalogueIntoCardItem(), owner.modi)) : // Shouldn't ever ask for empty cards
										   this.object == null ? new CaptchalogueableItemStack(ItemStack.EMPTY) : this.object;
		this.object = null;
		if (asCard)
			markedForDeletion = true;
		return object;
	}

	@Override
	public boolean canGet(int[] slots, int index)
	{
		checkSlots(slots, index);
		return true; // Leave this true instead of !isEmpty because there may be gaps in places that make objects irretrievable
	}

	@Override
	public ICaptchalogueable peek(int[] slots, int index)
	{
		checkSlots(slots, index);
		return object;
	}

	@Override
	public ICaptchalogueable tryGetEmptyCard(int[] slots, int index)
	{
		checkSlots(slots, index);
		if (object == null)
			markedForDeletion = true;
		return object == null ? new CaptchalogueableItemStack(AlchemyUtils.setCardModi(new ItemStack(MinestuckItems.captchaCard), owner.modi)) : null;
	}

	@Override
	public void put(ICaptchalogueable object, EntityPlayer player)
	{
		if (this.object != null || markedForDeletion)
			throw new IllegalStateException("Attempted to put an item into a full card");
		this.object = object;
	}

	@Override
	public void grow(ICaptchalogueable other)
	{
		if (object != null)
			this.object.grow(other);
	}

	@Override
	public void eject(EntityPlayer player)
	{
		get(null, 0, false).eject(owner, player);
	}

	@Override
	public void ejectAll(EntityPlayer player, boolean asCards, boolean onlyFull)
	{
		if (!onlyFull || object != null)
			get(null, -1, asCards).eject(null, player);
	}

	@Override
	public int getFreeSlots()
	{
		return object == null && !markedForDeletion ? 1 : 0;
	}

	@Override
	public int getTotalSlots()
	{
		return markedForDeletion ? 0 : 1;
	}

	private void checkSlots(int[] slots, int i)
	{
		if (markedForDeletion)
			throw new NullPointerException("Attempted to interact from a card marked for deletion");
		if (slots != null && i >= slots.length)
			throw new IndexOutOfBoundsException("Attempted to fetch a numbered slot from a card");
	}

	@Override
	public NBTTagCompound writeToNBT()
	{
		return ICaptchalogueable.writeToNBT(object);
	}

	public void readFromNBT(NBTTagCompound nbt)
	{
		object = ICaptchalogueable.readFromNBT(nbt);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ArrayList<SylladexGuiContainer> generateSubContainers(ArrayList<CardGuiContainer.CardTextureIndex[]> textureIndices)
	{
		containers.clear();
		containers.add(new CardGuiContainer(textureIndices.get(Math.min(owner.getSylladices().indexOf(this), textureIndices.size() - 1)), object));
		return containers;
	}
}
