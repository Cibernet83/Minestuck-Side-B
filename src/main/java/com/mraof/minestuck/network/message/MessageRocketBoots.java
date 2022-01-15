package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;

public class MessageRocketBoots implements MinestuckMessage
{
	public MessageRocketBoots() { }

	@Override
	public void fromBytes(ByteBuf buf) { }

	@Override
	public void toBytes(ByteBuf buf) { }

	@Override
	public void execute(EntityPlayer player)
	{
		if (player.getRNG().nextFloat() < 0.1f)
			player.getItemStackFromSlot(EntityEquipmentSlot.FEET).damageItem(1, player);
		player.fallDistance *= 0.8f;
		((WorldServer) player.world).spawnParticle(EnumParticleTypes.FLAME, player.posX, player.posY, player.posZ, 1, 0.2D, 0.1D, 0.2D, 0.0D);
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}
}
