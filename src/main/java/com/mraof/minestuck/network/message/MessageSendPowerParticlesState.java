package com.mraof.minestuck.network.message;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.client.particles.MinestuckParticles;
import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.EnumClass;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class MessageSendPowerParticlesState implements MinestuckMessage
{
	private int entityId;
	private Class badge;
	private MinestuckParticles.PowerParticleState state;

	public MessageSendPowerParticlesState() { }

	public MessageSendPowerParticlesState(Entity entity, Class badge, MinestuckParticles.PowerParticleState state)
	{
		this.entityId = entity.getEntityId();
		this.badge = badge;
		this.state = state;
	}

	public MessageSendPowerParticlesState(Entity entity, MinestuckParticles.PowerParticleState state)
	{
		this(entity, null, state);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(entityId);

		buf.writeBoolean(badge != null);
		if (badge != null)
			ByteBufUtils.writeUTF8String(buf, badge.getName());

		buf.writeBoolean(state != null);
		if (state != null)
		{
			buf.writeBoolean(state.type == MinestuckParticles.ParticleType.AURA);
			if (state.aspect != null)
				buf.writeByte(state.aspect.ordinal());
			else
				buf.writeByte(EnumAspect.values().length + state.clazz.ordinal());
			buf.writeByte(state.count);
		}
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		entityId = buf.readInt();

		if (buf.readBoolean())
			try
			{
				badge = Class.forName(ByteBufUtils.readUTF8String(buf));
			}
			catch (ClassNotFoundException e)
			{
				throw new RuntimeException(e);
			}

		if (buf.readBoolean())
		{
			boolean aura = buf.readBoolean();
			int classpect = buf.readByte();
			int count = buf.readByte();

			if (classpect < EnumAspect.values().length)
				state = new MinestuckParticles.PowerParticleState(
						aura ? MinestuckParticles.ParticleType.AURA : MinestuckParticles.ParticleType.BURST,
						EnumAspect.values()[classpect],
						count
				);
			else
				state = new MinestuckParticles.PowerParticleState(
						aura ? MinestuckParticles.ParticleType.AURA : MinestuckParticles.ParticleType.BURST,
						EnumClass.values()[classpect - EnumAspect.values().length],
						count
				);
		}


	}

	@Override
	public void execute(EntityPlayer player)
	{
		if (badge == null)
			player.world.getEntityByID(entityId).getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(state);
		else if (state == null)
			player.world.getEntityByID(entityId).getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).stopPowerParticles(badge);
		else
			player.world.getEntityByID(entityId).getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).startPowerParticles(badge, state);
	}

	@Override
	public Side toSide()
	{
		return Side.CLIENT;
	}
}
