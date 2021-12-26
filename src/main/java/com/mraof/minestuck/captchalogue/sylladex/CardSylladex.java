package com.mraof.minestuck.captchalogue.sylladex;

import com.mraof.minestuck.client.gui.captchalogue.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.ModusGuiContainer;
import com.mraof.minestuck.captchalogue.captchalogueable.CaptchalogueableItemStack;
import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.util.AlchemyUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

public class CardSylladex implements ISylladex
{
	private final BottomSylladex owner;
	private ICaptchalogueable object;
	private final ArrayList<ModusGuiContainer> containers = new ArrayList<>();
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
	public ICaptchalogueable get(int[] slots, int i, boolean asCard)
	{
		checkSlots(slots, i);
		ICaptchalogueable object = asCard ?
										   new CaptchalogueableItemStack(AlchemyUtils.setCardModi(this.object.captchalogueIntoCardItem(), owner.modi)) : // Shouldn't ever ask for empty cards
										   this.object == null ? new CaptchalogueableItemStack(ItemStack.EMPTY) : this.object;
		this.object = null;
		if (asCard)
			markedForDeletion = true;
		return object;
	}

	@Override
	public boolean canGet(int[] slots, int i)
	{
		checkSlots(slots, i);
		return true;
	}

	@Override
	public ICaptchalogueable peek(int[] slots, int i)
	{
		checkSlots(slots, i);
		return object;
	}

	@Override
	public ICaptchalogueable tryGetEmptyCard(int[] slots, int i)
	{
		checkSlots(slots, i);
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
		get(null, 0, false).eject(owner, owner.getSylladices().indexOf(this), player);
	}

	@Override
	public void ejectAll(EntityPlayer player, boolean asCards, boolean onlyFull)
	{
		if (!onlyFull || object != null)
			get(null, -1, asCards).eject(null, -1, player);
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
		if (object != null)
		{
			NBTTagCompound nbt = object.writeToNBT();
			nbt.setString("class", object.getClass().getName());
			return nbt;
		}
		else
		{
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("class", "null");
			return nbt;
		}
	}

	public void readFromNBT(NBTTagCompound nbt)
	{
		String className = nbt.getString("class");
		if (!className.equals("null"))
		{
			try
			{
				object = (ICaptchalogueable) Class.forName(className).newInstance();
			}
			catch (ClassNotFoundException | InstantiationException | IllegalAccessException e)
			{
				throw new RuntimeException(e);
			}
			object.readFromNBT(nbt);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ArrayList<ModusGuiContainer> generateSubContainers(ArrayList<CardGuiContainer.CardTextureIndex[]> textureIndices)
	{
		containers.clear();
		containers.add(new CardGuiContainer(textureIndices.get(Math.min(owner.getSylladices().indexOf(this), textureIndices.size() - 1)), object));
		return containers;
	}
}
