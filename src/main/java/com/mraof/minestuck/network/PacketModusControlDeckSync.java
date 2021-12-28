package com.mraof.minestuck.network;

import com.mraof.minestuck.captchalogue.ModusLayer;
import com.mraof.minestuck.captchalogue.ModusSettings;
import com.mraof.minestuck.captchalogue.sylladex.ISylladex;
import com.mraof.minestuck.captchalogue.sylladex.MultiSylladex;
import com.mraof.minestuck.item.ItemModus;
import com.mraof.minestuck.tileentity.TileEntityModusControlDeck;
import com.mraof.minestuck.util.SylladexUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketModusControlDeckSync extends MinestuckPacket
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
		{
			int modiInLayer = i == 0 ? te.getCartridgeCount() - (layerCount - 1) * TileEntityModusControlDeck.WIDTH : TileEntityModusControlDeck.WIDTH;

			ModusSettings[] modi = new ModusSettings[modiInLayer];
			for (int j = 0; j < modiInLayer; j++)
			{
				ItemStack modusStack = te.getInventory().get((layerCount - i - 1) * TileEntityModusControlDeck.WIDTH + j);
				modi[j] = new ModusSettings(((ItemModus) modusStack.getItem()).getModus(), SylladexUtils.getModusSettings(modusStack));
			}

			modusLayers[i] = new ModusLayer(i + 1 == layerCount ? -1 : lengths[i], modi);
		}

		// Eject previous sylladex
		MultiSylladex sylladex = SylladexUtils.getSylladex(player);
		sylladex.ejectAll(player, false, true);
		int cards = sylladex.getTotalSlots();

		// Make new sylladex
		sylladex = ISylladex.newSylladex(modusLayers);
		sylladex.addCards(cards, player);
		SylladexUtils.setSylladex(player, sylladex);
	}

	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.CLIENT);
	}
}
