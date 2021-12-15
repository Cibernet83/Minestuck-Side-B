package com.mraof.minestuck.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketRocketBoots extends MinestuckPacket
{
	@Override
	public MinestuckPacket generatePacket(Object... args) {
		return this;
	}

	@Override
	public MinestuckPacket consumePacket(ByteBuf data) {
		return this;
	}

	@Override
	public void execute(EntityPlayer player)
	{
		if(player.getRNG().nextFloat() < 0.1f)
			player.getItemStackFromSlot(EntityEquipmentSlot.FEET).damageItem(1, player);
		player.fallDistance *= 0.8f;
		((WorldServer)player.world).spawnParticle(EnumParticleTypes.FLAME, player.posX, player.posY, player.posZ, 1, 0.2D, 0.1D, 0.2D, 0.0D);
	}

	@Override
	public EnumSet<Side> getSenderSide() {
		return EnumSet.of(Side.CLIENT);
	}
}
