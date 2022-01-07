package com.mraof.minestuck.network.message;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.entity.ai.EntityAIMindflayerTarget;
import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.network.MinestuckNetwork;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

public class MessageMindflayerMovementInputRequest implements MinestuckMessage
{
	private float moveStrafe, moveForward;
	private boolean jump, sneak;
	private int currentItem;

	public MessageMindflayerMovementInputRequest() { }

	public MessageMindflayerMovementInputRequest(float moveStrafe, float moveForward, boolean jump, boolean sneak, int currentItem)
	{
		this.moveStrafe = moveStrafe;
		this.moveForward = moveForward;
		this.jump = jump;
		this.sneak = sneak;
		this.currentItem = currentItem;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeFloat(moveStrafe);
		buf.writeFloat(moveForward);
		buf.writeBoolean(jump);
		buf.writeBoolean(sneak);
		buf.writeInt(currentItem);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		moveStrafe = buf.readFloat();
		moveForward = buf.readFloat();
		jump = buf.readBoolean();
		sneak = buf.readBoolean();
		currentItem = buf.readInt();

	}

	@Override
	public void execute(EntityPlayer player)
	{
		EntityLivingBase target = player.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).getMindflayerEntity();

		if(target == null)
			return;

		if (target instanceof EntityCreature)
			for (EntityAITasks.EntityAITaskEntry entry : ((EntityCreature)target).tasks.taskEntries)
				if (entry.action instanceof EntityAIMindflayerTarget)
					((EntityAIMindflayerTarget) entry.action).setMove(moveStrafe, moveForward);
		else
		{
			IBadgeEffects badgeEffects = target.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null);
			badgeEffects.setMovement(moveStrafe, moveForward, jump, sneak);

			if(target instanceof EntityPlayer && ((EntityPlayer)target).inventory.currentItem != currentItem)
			{
				((EntityPlayer)target).inventory.currentItem = currentItem;
				MinestuckNetwork.sendTo(new MessageCurrentItem(currentItem), (EntityPlayer)target);
			}
		}
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}
}
