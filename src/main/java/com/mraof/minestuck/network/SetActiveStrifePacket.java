package com.mraof.minestuck.network;

import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.capabilities.MSUCapabilities;
import com.mraof.minestuck.capabilities.strife.IStrifeData;
import com.mraof.minestuck.event.handlers.StrifeEventHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class SetActiveStrifePacket extends MSUPacket
{
	boolean isSpecibus;
	int index;

	@Override
	public MSUPacket generatePacket(Object... args)
	{
		data.writeInt((int) args[0]);
		data.writeBoolean((boolean) args[1]);
		return this;
	}

	@Override
	public MSUPacket consumePacket(ByteBuf data)
	{
		index = data.readInt();
		isSpecibus = data.readBoolean();
		return this;
	}

	@Override
	public void execute(EntityPlayer player)
	{
		if(!MinestuckConfig.combatOverhaul || !player.hasCapability(MSUCapabilities.STRIFE_DATA, null))
			return;

		IStrifeData cap = player.getCapability(MSUCapabilities.STRIFE_DATA, null);
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

		MSUChannelHandler.sendToPlayer(makePacket(Type.UPDATE_STRIFE, player, UpdateStrifeDataPacket.UpdateType.INDEXES), player);
	}

	@Override
	public EnumSet<Side> getSenderSide() {
		return EnumSet.of(Side.CLIENT);
	}
}
