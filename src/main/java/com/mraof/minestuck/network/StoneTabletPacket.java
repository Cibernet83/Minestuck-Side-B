package com.mraof.minestuck.network;

import com.mraof.minestuck.item.MinestuckItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class StoneTabletPacket extends MinestuckPacket
{
	public String text;

	@Override
	public MinestuckPacket generatePacket(Object... dat)
	{
		if(dat.length > 0)
			data.writeBytes(((String) dat[0]).getBytes());
		return this;
	}

	@Override
	public MinestuckPacket consumePacket(ByteBuf data)
	{
		int size = data.readableBytes();
		byte[] destBytes = new byte[size];
		for(int i = 0; i < size; i++)
			destBytes[i] = data.readByte();
		text = new String(destBytes);
		return this;
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
			nbt.setString("text",text);
		else if(nbt.hasKey("text"))
			nbt.removeTag("text");
		stack.setTagCompound(nbt.hasNoTags() ? null : nbt);
	}

	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.CLIENT);
	}
}
