package com.mraof.minestuck.network.message;

import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.potions.PotionMouseSensitivityAdjusterBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.relauncher.Side;

public class MessageMouseSensitivityPotion implements MinestuckMessage
{
	private PotionMouseSensitivityAdjusterBase potion;

	private MessageMouseSensitivityPotion() { }

	public MessageMouseSensitivityPotion(PotionMouseSensitivityAdjusterBase potion)
	{
		this.potion = potion;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(potion == null ? -1 : Potion.getIdFromPotion(potion));
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		int id = buf.readInt();
		potion = id < 0 ? null : (PotionMouseSensitivityAdjusterBase) Potion.getPotionById(id);
	}

	@Override
	public void execute(EntityPlayer player)
	{
		if (potion == null)
			PotionMouseSensitivityAdjusterBase.resetMouseSensitivity(player);
		else
			potion.setMouseSensitivity(player);
	}

	@Override
	public Side toSide()
	{
		return Side.CLIENT;
	}
}
