package com.mraof.minestuck.network;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IStrifeData;
import com.mraof.minestuck.event.handler.StrifeEventHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;

import java.util.function.Consumer;

public abstract class MessageUpdateStrifeToClientBase extends MessageEntityEncoderBase
{
	private EntityLivingBase decodingEntity;

	public MessageUpdateStrifeToClientBase() { }

	public MessageUpdateStrifeToClientBase(EntityLivingBase entity)
	{
		super(entity);
	}

	@Override
	public Consumer<ByteBuf> getEncoder(EntityLivingBase entity)
	{
		return getEncoder(entity.getCapability(MinestuckCapabilities.STRIFE_DATA, null));
	}

	public abstract Consumer<ByteBuf> getEncoder(IStrifeData cap);

	@Override
	public void execute(EntityPlayer player)
	{
		super.execute(player);

		IStrifeData cap = decodingEntity.getCapability(MinestuckCapabilities.STRIFE_DATA, null);
		if (cap.isArmed())
		{
			EnumHand hand = StrifeEventHandler.isStackAssigned(decodingEntity.getHeldItemOffhand()) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
			if (StrifeEventHandler.isStackAssigned(decodingEntity.getHeldItem(hand)) &&
						cap.getPortfolio().length <= 0 || cap.getSelectedSpecibusIndex() < 0 || cap.getSelectedWeaponIndex() < 0
						|| cap.getPortfolio()[cap.getSelectedSpecibusIndex()] == null || cap.getPortfolio()[cap.getSelectedSpecibusIndex()].getContents().isEmpty()
						|| cap.getSelectedWeaponIndex() >= cap.getPortfolio()[cap.getSelectedSpecibusIndex()].getContents().size()
						|| !ItemStack.areItemStacksEqual(cap.getPortfolio()[cap.getSelectedSpecibusIndex()].getContents().get(cap.getSelectedWeaponIndex()), decodingEntity.getHeldItem(hand)))
			{
				decodingEntity.setHeldItem(hand, ItemStack.EMPTY);
				cap.setArmed(false);
			}
		}
	}

	@Override
	public Consumer<ByteBuf> getDecoder(EntityLivingBase entity)
	{
		decodingEntity = entity;
		return getDecoder(entity.getCapability(MinestuckCapabilities.STRIFE_DATA, null));
	}

	public abstract Consumer<ByteBuf> getDecoder(IStrifeData cap);

	@Override
	public Side toSide()
	{
		return Side.CLIENT;
	}
}
