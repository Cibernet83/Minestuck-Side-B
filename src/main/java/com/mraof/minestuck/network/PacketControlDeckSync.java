package com.mraof.minestuck.network;

import com.mraof.minestuck.item.ItemModus;
import com.mraof.minestuck.captchalogue.modus.Modus;
import com.mraof.minestuck.captchalogue.sylladex.ISylladex;
import com.mraof.minestuck.captchalogue.sylladex.MultiSylladex;
import com.mraof.minestuck.tileentity.TileEntityModusControlDeck;
import com.mraof.minestuck.util.SylladexUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketControlDeckSync extends MinestuckPacket
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

		for (int i = 0; i < Math.min(lengths.length, te.lengths.size()); i++)
			te.lengths.set(i, lengths[i]);

		Modus[][] modi = new Modus[te.getLayerCount()][];
		for (int i = 0; i < te.getLayerCount(); i++)
		{
			int modiInLayer = i + 1 == te.getLayerCount() ? te.getCartridgeCount() - (te.getLayerCount() - 1) * TileEntityModusControlDeck.WIDTH : TileEntityModusControlDeck.WIDTH;
			Modus[] layer = new Modus[modiInLayer];
			for (int j = 0; j < modiInLayer; j++)
				layer[j] = ((ItemModus) te.getInventory().get(i * TileEntityModusControlDeck.WIDTH + j).getItem()).getModus();
			modi[te.getLayerCount() - i - 1] = layer;
		}

		MultiSylladex sylladex = SylladexUtils.getSylladex(player);
		sylladex.ejectAll(player, false, true);
		int cards = sylladex.getTotalSlots();

		sylladex = ISylladex.newSylladex(lengths, modi);
		sylladex.addCards(cards, player);
		SylladexUtils.setSylladex(player, sylladex);
	}

	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.CLIENT);
	}
}
