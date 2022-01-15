package com.mraof.minestuck.network.message;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class MessageBadgeEffectsAll implements MinestuckMessage
{
	private int entityId;
	private NBTTagCompound nbt;

	public MessageBadgeEffectsAll() { }

	public MessageBadgeEffectsAll(EntityLivingBase entity)
	{
		entityId = entity.getEntityId();
		nbt = entity.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).writeToNBT();
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		entityId = buf.readInt();
		nbt = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(entityId);
		ByteBufUtils.writeTag(buf, nbt);
	}

	@Override
	public void execute(EntityPlayer player)
	{
		player.world.getEntityByID(entityId).getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).readFromNBT(nbt);
	}

	@Override
	public Side toSide()
	{
		return Side.CLIENT;
	}
}
