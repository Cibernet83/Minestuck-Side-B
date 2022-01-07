package com.mraof.minestuck.network.message;

import com.mraof.minestuck.item.ItemStrifeCard;
import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.strife.KindAbstratus;
import com.mraof.minestuck.strife.StrifePortfolioHandler;
import com.mraof.minestuck.strife.StrifeSpecibus;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

public class MessageStrifeAssignRequest implements MinestuckMessage
{
	private EnumHand hand;
	private KindAbstratus newType;

	private MessageStrifeAssignRequest() { }

	public MessageStrifeAssignRequest(EnumHand hand, KindAbstratus newType)
	{
		this.hand = hand;
		this.newType = newType;
	}

	public MessageStrifeAssignRequest(EnumHand hand)
	{
		this(hand, null);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(hand.ordinal());
		if (newType != null)
			ByteBufUtils.writeRegistryEntry(buf, newType);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		hand = EnumHand.values()[buf.readInt()];
		if(buf.readableBytes() > 0)
			newType = ByteBufUtils.readRegistryEntry(buf, KindAbstratus.REGISTRY);
	}

	@Override
	public void execute(EntityPlayer player)
	{
		ItemStack heldItem = player.getHeldItem(hand);
		if(newType != null && heldItem.getItem() instanceof ItemStrifeCard && !(heldItem.hasTagCompound() && heldItem.getTagCompound().hasKey("StrifeSpecibus"))) // No hacking for arbitrary cards lol
			ItemStrifeCard.injectStrifeSpecibus(new StrifeSpecibus(newType), heldItem);
		StrifePortfolioHandler.assignStrife(player, hand);
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}
}
