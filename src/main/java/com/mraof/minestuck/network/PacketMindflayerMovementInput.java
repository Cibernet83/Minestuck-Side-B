package com.mraof.minestuck.network;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.entity.ai.EntityAIMindflayerTarget;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketMindflayerMovementInput extends MinestuckPacket
{
	private float moveForward, moveStrafe;
	private boolean jump, sneak;
	private int currentItem;

	@Override
	public MinestuckPacket generatePacket(Object... args)
	{
		data.writeFloat((float) args[0]);
		data.writeFloat((float) args[1]);
		data.writeBoolean((boolean) args[2]);
		data.writeBoolean((boolean) args[3]);
		data.writeInt((int) args[4]);
		return this;
	}

	@Override
	public MinestuckPacket consumePacket(ByteBuf data)
	{
		moveStrafe = data.readFloat();
		moveForward = data.readFloat();
		jump = data.readBoolean();
		sneak = data.readBoolean();
		currentItem = data.readInt();
		return this;
	}

	@Override
	public void execute(EntityPlayer player)
	{
		EntityLivingBase target = player.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).getMindflayerEntity();

		if(target == null)
			return;

		if (target instanceof EntityCreature)
		{
			for (EntityAITasks.EntityAITaskEntry entry : ((EntityCreature) target).tasks.taskEntries)
				if (entry.action instanceof EntityAIMindflayerTarget)
					((EntityAIMindflayerTarget) entry.action).setMove(moveStrafe, moveForward);
		}
		else
		{
			IBadgeEffects badgeEffects = target.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null);
			badgeEffects.setMovement(moveStrafe, moveForward, jump, sneak);

			if(target instanceof EntityPlayer && ((EntityPlayer) target).inventory.currentItem != currentItem)
			{
				((EntityPlayer) target).inventory.currentItem = currentItem;
				MinestuckChannelHandler.sendToPlayer(makePacket(Type.SET_CURRENT_ITEM, currentItem), (EntityPlayer) target);
			}
		}
	}

	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.CLIENT);
	}
}
