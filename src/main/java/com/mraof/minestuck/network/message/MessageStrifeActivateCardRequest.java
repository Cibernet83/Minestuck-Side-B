package com.mraof.minestuck.network.message;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IStrifeData;
import com.mraof.minestuck.event.handler.StrifeEventHandler;
import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.network.MinestuckNetwork;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;

public class MessageStrifeActivateCardRequest implements MinestuckMessage
{
	private int index;
	private boolean isSpecibus;

	public MessageStrifeActivateCardRequest() { }

	public MessageStrifeActivateCardRequest(int index, boolean isSpecibus)
	{
		this.index = index;
		this.isSpecibus = isSpecibus;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		index = buf.readInt();
		isSpecibus = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(index);
		buf.writeBoolean(isSpecibus);
	}

	@Override
	public void execute(EntityPlayer player)
	{
		if (!player.hasCapability(MinestuckCapabilities.STRIFE_DATA, null))
			return;

		IStrifeData cap = player.getCapability(MinestuckCapabilities.STRIFE_DATA, null);
		if (isSpecibus)
		{
			cap.setSelectedSpecibusIndex(index);
			cap.setArmed(false);

			if (StrifeEventHandler.isStackAssigned(player.getHeldItemMainhand()))
				player.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
			if (StrifeEventHandler.isStackAssigned(player.getHeldItemOffhand()))
				player.setHeldItem(EnumHand.OFF_HAND, ItemStack.EMPTY);
		}
		else cap.setSelectedWeaponIndex(index);

		MinestuckNetwork.sendTo(new MessageStrifeIndices(player), player);
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}
}
