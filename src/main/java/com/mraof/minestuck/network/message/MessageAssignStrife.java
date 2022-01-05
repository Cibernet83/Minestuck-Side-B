package com.mraof.minestuck.network.message;

import com.mraof.minestuck.item.ItemStrifeCard;
import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.strife.StrifePortfolioHandler;
import com.mraof.minestuck.strife.StrifeSpecibus;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class MessageAssignStrife extends MinestuckMessage
{
	EnumHand hand;
	StrifeSpecibus specibus;

	ItemStack stack;
	int slot;

	@Override
	public void generatePacket(Object... args)
	{
		data.writeBoolean(args[0] instanceof ItemStack);

		if(args[0] instanceof ItemStack)
		{
			ByteBufUtils.writeItemStack(data, (ItemStack) args[0]);
			data.writeInt((Integer) args[1]);

		}

		data.writeInt(((EnumHand)args[0]).ordinal());
		data.writeBoolean(args.length > 1);
		if(args.length > 1)
			ByteBufUtils.writeTag(data, ((StrifeSpecibus)args[1]).writeToNBT(new NBTTagCompound()));


	}

	@Override
	public void consumePacket(ByteBuf data)
	{
		if(data.readBoolean())
		{
			stack = ByteBufUtils.readItemStack(data);
			slot = data.readInt();

		}

		hand = EnumHand.values()[data.readInt()];
		if(data.readBoolean())
			specibus = new StrifeSpecibus(ByteBufUtils.readTag(data));


	}

	@Override
	public void execute(EntityPlayer player)
	{
		if(stack != null)
		{
			StrifePortfolioHandler.addWeapontoSlot(player, stack, slot);
			return;
		}

		if(specibus != null)
			ItemStrifeCard.injectStrifeSpecibus(specibus, player.getHeldItem(hand));
		StrifePortfolioHandler.assignStrife(player, hand);
	}

	@Override
	public EnumSet<Side> getSenderSide()
	{
		return EnumSet.of(Side.CLIENT);
	}
}
