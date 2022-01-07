package com.mraof.minestuck.network.message;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.caps.GodTierData;
import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import java.util.UUID;

public class MessageAddSkillXp implements MinestuckMessage
{
	private GodTierData.SkillType skillType;
	private UUID playerUUID;
	private int amount;

	public MessageAddSkillXp() { }

	public MessageAddSkillXp(GodTierData.SkillType skillType, EntityPlayer player)
	{
		this(skillType, player, 1);
	}

	public MessageAddSkillXp(GodTierData.SkillType skillType, EntityPlayer player, int amount)
	{
		this.skillType = skillType;
		this.playerUUID = player.getUniqueID();
		this.amount = amount;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		skillType = GodTierData.SkillType.values()[buf.readInt()];
		playerUUID = UUID.fromString(ByteBufUtils.readUTF8String(buf));
		amount = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(skillType.ordinal());
		ByteBufUtils.writeUTF8String(buf, playerUUID.toString());
		buf.writeInt(amount);
	}

	@Override
	public void execute(EntityPlayer player)
	{
		if (player.getUniqueID().equals(playerUUID))
		{
			int actualAmount = player.isCreative() ? amount : Math.min(player.experienceLevel, amount);
			player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).increaseXp(skillType, actualAmount);
			if (!player.isCreative())
				player.experienceLevel -= actualAmount;
		}
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}
}
