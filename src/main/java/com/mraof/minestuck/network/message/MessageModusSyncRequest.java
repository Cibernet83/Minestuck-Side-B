package com.mraof.minestuck.network.message;

import com.mraof.minestuck.captchalogue.ModusLayer;
import com.mraof.minestuck.captchalogue.ModusSettings;
import com.mraof.minestuck.item.ItemModus;
import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.util.SylladexUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;

public class MessageModusSyncRequest implements MinestuckMessage
{
	public MessageModusSyncRequest() { }

	@Override
	public void fromBytes(ByteBuf buf) { }

	@Override
	public void toBytes(ByteBuf buf) { }

	@Override
	public void execute(EntityPlayer player)
	{
		ItemStack stack = player.getHeldItemMainhand();
		if (!(stack.getItem() instanceof ItemModus))
			return;
		ItemModus item = (ItemModus)stack.getItem();
		SylladexUtils.changeModi(player, new ModusLayer(-1, new ModusSettings(item.getModus(), SylladexUtils.getModusSettings(stack))));
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}
}
