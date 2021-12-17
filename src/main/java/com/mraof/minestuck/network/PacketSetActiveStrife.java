package com.mraof.minestuck.network;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IStrifeData;
import com.mraof.minestuck.event.handler.StrifeEventHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketSetActiveStrife extends MinestuckPacket
{
	boolean isSpecibus;
	int index;

	@Override
	public MinestuckPacket generatePacket(Object... args)
	{
		data.writeInt((int) args[0]);
		data.writeBoolean((boolean) args[1]);
		return this;
	}

	@Override
	public MinestuckPacket consumePacket(ByteBuf data)
	{
		index = data.readInt();
		isSpecibus = data.readBoolean();
		return this;
	}

	@Override
	public void execute(EntityPlayer player)
	{
		if(!player.hasCapability(MinestuckCapabilities.STRIFE_DATA, null))
			return;

		IStrifeData cap = player.getCapability(MinestuckCapabilities.STRIFE_DATA, null);
		if(isSpecibus)
		{
			cap.setSelectedSpecibusIndex(index);
			cap.setArmed(false);

			if(StrifeEventHandler.isStackAssigned(player.getHeldItemMainhand()))
				player.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
			if(StrifeEventHandler.isStackAssigned(player.getHeldItemOffhand()))
				player.setHeldItem(EnumHand.OFF_HAND, ItemStack.EMPTY);
		}
		else cap.setSelectedWeaponIndex(index);

		MinestuckChannelHandler.sendToPlayer(makePacket(Type.UPDATE_STRIFE, player, PacketUpdateStrifeData.UpdateType.INDEXES), player);
	}

	@Override
	public EnumSet<Side> getSenderSide() {
		return EnumSet.of(Side.CLIENT);
	}
}
