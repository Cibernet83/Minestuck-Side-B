package com.mraof.minestuck.network.message;

import com.mraof.minestuck.captchalogue.ModusLayer;
import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.tileentity.TileEntityModusControlDeck;
import com.mraof.minestuck.util.SylladexUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

public class MessageModusControlDeckSyncRequest implements MinestuckMessage
{
	private BlockPos pos;
	private int[] lengths;

	public MessageModusControlDeckSyncRequest() { }

	public MessageModusControlDeckSyncRequest(TileEntityModusControlDeck deck, int[] lengths)
	{
		this.pos = deck.getPos();
		this.lengths = lengths;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		lengths = new int[buf.readByte()];
		for (int i = 0; i < lengths.length; i++)
			lengths[i] = (buf.readByte() & 0xff) + 1;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		buf.writeByte(lengths.length);
		for (int length : lengths)
			buf.writeByte(length - 1);
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

		SylladexUtils.changeModi(player, modusLayers);
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}
}
