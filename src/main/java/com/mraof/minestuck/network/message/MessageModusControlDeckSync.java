package com.mraof.minestuck.network.message;

import com.mraof.minestuck.captchalogue.ModusLayer;
import com.mraof.minestuck.captchalogue.sylladex.ISylladex;
import com.mraof.minestuck.captchalogue.sylladex.MultiSylladex;
import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.tileentity.TileEntityModusControlDeck;
import com.mraof.minestuck.util.SylladexUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class MessageModusControlDeckSync extends MinestuckMessage
{
	private BlockPos pos;
	private int[] lengths;

	@Override
	public void generatePacket(Object... args)
	{
		BlockPos pos = (BlockPos) args[0];
		int[] lengths = (int[]) args[1];
		data.writeInt(pos.getX());
		data.writeInt(pos.getY());
		data.writeInt(pos.getZ());
		data.writeByte(lengths.length);
		for (int length : lengths)
			data.writeByte(length - 1);
	}

	@Override
	public void consumePacket(ByteBuf data)
	{
		pos = new BlockPos(data.readInt(), data.readInt(), data.readInt());
		lengths = new int[data.readByte()];
		for (int i = 0; i < lengths.length; i++)
			lengths[i] = (data.readByte() & 0xff) + 1;
	}

	@Override
	public void execute(EntityPlayer player)
	{
		if (!(player.world.getTileEntity(pos) instanceof TileEntityModusControlDeck))
			return;
		TileEntityModusControlDeck te = (TileEntityModusControlDeck) player.world.getTileEntity(pos);
		if (te.getCartridgeCount() == 0)
			return;

		// Get modus layers
		int layerCount = te.getLayerCount();
		ModusLayer[] modusLayers = new ModusLayer[layerCount];
		for (int i = 0; i < layerCount; i++)
			modusLayers[i] = te.getLayer(i, i + 1 == layerCount ? -1 : lengths[i]);

		// Eject previous sylladex
		MultiSylladex sylladex = SylladexUtils.getSylladex(player);
		sylladex.ejectAll(false, true);
		int cards = sylladex.getTotalSlots();

		// Make new sylladex
		sylladex = ISylladex.newSylladex(player, modusLayers);
		sylladex.addCards(cards);
		SylladexUtils.setSylladex(player, sylladex);
	}

	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.CLIENT);
	}
}
