package com.mraof.minestuck.network.message;

import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.editmode.ServerEditHandler;
import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.network.skaianet.SkaianetHandler;
import com.mraof.minestuck.util.IdentifierHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.management.UserListOpsEntry;
import net.minecraftforge.fml.relauncher.Side;

public class MessageSburbCloseRequest implements MinestuckMessage
{
	private int player;
	private int otherPlayer;
	private boolean isClient;

	private MessageSburbCloseRequest() { }

	public MessageSburbCloseRequest(int player, int otherPlayer, boolean isClient)
	{
		this.player = player;
		this.otherPlayer = otherPlayer;
		this.isClient = isClient;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(player);
		buf.writeInt(otherPlayer);
		buf.writeBoolean(isClient);
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		player = buf.readInt();
		otherPlayer = buf.readInt();
		isClient = buf.readBoolean();
	}
	
	@Override
	public void execute(EntityPlayer player)
	{
		UserListOpsEntry opsEntry = player.getServer().getPlayerList().getOppedPlayers().getEntry(player.getGameProfile());
		if((!MinestuckConfig.privateComputers || IdentifierHandler.encode(player).getId() == this.player || opsEntry != null && opsEntry.getPermissionLevel() >= 2) && ServerEditHandler.getData(player) == null)
			SkaianetHandler.closeConnection(IdentifierHandler.getById(this.player), otherPlayer != -1 ? IdentifierHandler.getById(this.otherPlayer) : null, isClient);
	}
	
	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}
	
}