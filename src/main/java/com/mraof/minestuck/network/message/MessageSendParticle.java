package com.mraof.minestuck.network.message;

import com.mraof.minestuck.client.particles.MinestuckParticles;
import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

public class MessageSendParticle implements MinestuckMessage
{
	private MinestuckParticles.ParticleType type;
	private double x;
	private double y;
	private double z;
	private int color;
	private int count;

	public MessageSendParticle() { }

	public MessageSendParticle(MinestuckParticles.ParticleType type, double x, double y, double z, int color, int count)
	{
		this.type = type;
		this.x = x;
		this.y = y;
		this.z = z;
		this.color = color;
		this.count = count;
	}

	public MessageSendParticle(MinestuckParticles.ParticleType type, Entity entity, int color, int count)
	{
		this(type, entity.posX, entity.posY, entity.posZ, color, count);
	}

	public MessageSendParticle(MinestuckParticles.ParticleType type, BlockPos pos, int color, int count)
	{
		this(type, pos.getX(), pos.getY(), pos.getZ(), color, count);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(type.ordinal());
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
		buf.writeInt(color);
		buf.writeInt(count);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		type = MinestuckParticles.ParticleType.values()[buf.readInt()];
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
		color = buf.readInt();
		count = buf.readInt();
	}

	@Override
	public void execute(EntityPlayer player)
	{
		switch (type)
		{
			case AURA:
				MinestuckParticles.spawnAuraParticles(player.world, x, y, z, color, count);
				break;
			case BURST:
				MinestuckParticles.spawnBurstParticles(player.world, x, y, z, color, count);
				break;
		}
	}

	@Override
	public Side toSide() {
		return Side.CLIENT;
	}

}
