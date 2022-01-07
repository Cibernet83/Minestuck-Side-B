package com.mraof.minestuck.network.message;

import com.mraof.minestuck.client.gui.playerStats.GuiEcheladder;
import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.util.Echeladder;
import com.mraof.minestuck.util.IdentifierHandler;
import com.mraof.minestuck.util.MinestuckPlayerData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;

public class MessageEcheladder implements MinestuckMessage
{
	private int rung;
	private float progress;
	private boolean jumpToRung;

	public MessageEcheladder() { }

	public MessageEcheladder(int rung, float progress, boolean jumpToRung)
	{
		this.rung = rung;
		this.progress = progress;
		this.jumpToRung = jumpToRung;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		rung = buf.readInt();
		progress = buf.readFloat();
		jumpToRung = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(rung);
		buf.writeFloat(progress);
		buf.writeBoolean(jumpToRung);
	}

	@Override
	public void execute(EntityPlayer player)
	{
		if (MinestuckPlayerData.clientData.echeladder == null)
			MinestuckPlayerData.clientData.echeladder = new Echeladder(IdentifierHandler.encode(player));

		int prevRung = MinestuckPlayerData.clientData.echeladder.getRung();
		MinestuckPlayerData.clientData.echeladder.setProgress(rung, progress);

		if (jumpToRung)
			GuiEcheladder.animatedRung = GuiEcheladder.lastRung = rung;
		else
			for (prevRung++; prevRung <= rung; prevRung++)
			{
				String s = I18n.canTranslate("echeladder.rung" + prevRung) ? I18n.translateToLocal("echeladder.rung" + prevRung) : String.valueOf(prevRung + 1);
				player.sendMessage(new TextComponentString("You reached rung " + s + '!'));
			}
	}

	@Override
	public Side toSide()
	{
		return Side.CLIENT;
	}
}