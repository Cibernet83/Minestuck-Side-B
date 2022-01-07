package com.mraof.minestuck.network.message;

import com.mraof.minestuck.captchalogue.modus.Modus;
import com.mraof.minestuck.item.ItemModus;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;

public class MessageSwitchModusRequest implements MinestuckMessage
{
	public MessageSwitchModusRequest() { }

	@Override
	public void toBytes(ByteBuf buf) { }

	@Override
	public void fromBytes(ByteBuf buf) { }

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
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}
}
