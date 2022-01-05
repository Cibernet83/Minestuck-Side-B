package com.mraof.minestuck.network;

import com.mraof.minestuck.captchalogue.modus.Modus;
import com.mraof.minestuck.client.gui.MinestuckGuiHandler;
import com.mraof.minestuck.item.ItemModus;
import com.mraof.minestuck.item.MinestuckItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketSwitchModus extends MinestuckPacket
{
	@Override
	public void generatePacket(Object... args) { }

	@Override
	public void consumePacket(ByteBuf data) { }

	@Override
	public void execute(EntityPlayer player)
	{
		ItemStack modusStack = player.getHeldItemMainhand();
		if (!(modusStack.getItem() instanceof ItemModus))
			return;
		Modus modus = ((ItemModus)modusStack.getItem()).getModus();
		if (modus.getAlternate() == null)
			return;

		modusStack.shrink(1);
		if (!player.getHeldItemMainhand().isEmpty())
			player.dropItem(true);
		ItemStack newModusStack = new ItemStack(MinestuckItems.modi.get(modus.getAlternate()));
		newModusStack.setTagCompound(modusStack.getTagCompound());
		player.setHeldItem(EnumHand.MAIN_HAND, newModusStack);

		MinestuckChannelHandler.sendToPlayer(makePacket(Type.OPEN_GUI, MinestuckGuiHandler.GuiId.FETCH_MODUS), player);
	}

	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.SERVER);
	}
}
