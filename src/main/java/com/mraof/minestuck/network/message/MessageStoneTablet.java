package com.mraof.minestuck.network.message;

import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class MessageStoneTablet implements MinestuckMessage
{
	private String text;

	private MessageStoneTablet() { }

	public MessageStoneTablet(String text) { }

	@Override
	public void toBytes(ByteBuf buf)
	{
		if (text != null)
			ByteBufUtils.writeUTF8String(buf, text);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		if (buf.readableBytes() > 0)
			text = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void execute(EntityPlayer player)
	{
		ItemStack stack = player.getHeldItemMainhand();
		ItemStack tablet = new ItemStack(MinestuckItems.stoneTablet);

		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound nbt = stack.getTagCompound();

		if(!stack.isItemEqual(tablet))
			if(!(stack = player.getHeldItemOffhand()).isItemEqual(tablet))
				return;

		if(!text.trim().isEmpty())
			nbt.setString("text", text);
		else if(nbt.hasKey("text"))
			nbt.removeTag("text");
		stack.setTagCompound(nbt.hasNoTags() ? null : nbt);
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}
}
