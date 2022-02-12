package com.mraof.minestuck.captchalogue.sylladex;

import com.mraof.minestuck.captchalogue.captchalogueable.CaptchalogueableItemStack;
import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.CardGuiContainer;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.util.AlchemyUtils;
import com.mraof.minestuck.util.SylladexUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CardSylladex extends Sylladex
{
	private final EntityPlayer player;
	private final BottomSylladex owner;
	private ICaptchalogueable object;
	private boolean markedForDeletion = false;

	public CardSylladex(BottomSylladex owner, NBTTagCompound nbt)
	{
		this(owner, (ICaptchalogueable) null);
		readFromNBT(nbt);
	}

	public CardSylladex(BottomSylladex owner, ICaptchalogueable object)
	{
		this.player = owner.getPlayer();
		this.owner = owner;
		this.object = object;
	}

	public void readFromNBT(NBTTagCompound nbt)
	{
		object = ICaptchalogueable.readFromNBT(nbt);
	}

	@Override
	public ICaptchalogueable get(int[] slots, int index, boolean asCard)
	{
		checkSlots(slots, index);
		ICaptchalogueable object = asCard ?
										   new CaptchalogueableItemStack(AlchemyUtils.setCardModi(this.object.captchalogueIntoCardItem(), SylladexUtils.getTextureModi(slots, player))) : // Shouldn't ever ask for empty cards
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
	public boolean canGet(int index)
	{
		return true;
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
		{
			markedForDeletion = true;
			return new CaptchalogueableItemStack(AlchemyUtils.setCardModi(new ItemStack(MinestuckItems.captchaCard), SylladexUtils.getTextureModi(slots, player)));
		}
		else
			return null;
	}

	@Override
	public void put(ICaptchalogueable object)
	{
		if (this.object != null || markedForDeletion)
			throw new IllegalStateException("Attempted to put an object " + object.getName() + " into a card with " + this.object.getName());
		this.object = object;
	}

	@Override
	public void grow(ICaptchalogueable other)
	{
		if (object != null)
			this.object.grow(other);
	}

	@Override
	public void eject()
	{
		get(null, 0, false).eject(owner, owner.getSylladices().indexOf(this) + 1, player);
		this.object = null;
	}

	@Override
	public void ejectAll(boolean asCards, boolean onlyFull)
	{
		if (!onlyFull || object != null)
			get(null, -1, asCards).eject(player);
		this.object = null;
	}

	@Override
	public boolean tryEjectCard()
	{
		if (object != null && object.tryPopCard(owner, owner.getSylladices().indexOf(this) + 1, player))
		{
			object = null;
			return true;
		}
		return false;
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

	@Override
	public EntityPlayer getPlayer()
	{
		return player;
	}

	private void checkSlots(int[] slots, int i)
	{
		if (markedForDeletion)
			throw new NullPointerException("Attempted to interact from a card marked for deletion");
		if (slots != null && i > slots.length)
			throw new IndexOutOfBoundsException("Attempted to fetch a numbered slot from a card");
	}

	@Override
	public NBTTagCompound writeToNBT()
	{
		return ICaptchalogueable.writeToNBT(object);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public CardGuiContainer generateSubContainer(CardGuiContainer.CardTextureIndex[] textureIndices)
	{
		return new CardGuiContainer(textureIndices, object);
	}
}
