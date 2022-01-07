package com.mraof.minestuck.network.message;

import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.block.MinestuckBlocks;
import com.mraof.minestuck.editmode.ServerEditHandler;
import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.network.skaianet.SburbConnection;
import com.mraof.minestuck.network.skaianet.SburbHandler;
import com.mraof.minestuck.network.skaianet.SkaianetHandler;
import com.mraof.minestuck.tileentity.TileEntityComputer;
import com.mraof.minestuck.util.AlchemyUtils;
import com.mraof.minestuck.util.IdentifierHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.management.UserListOpsEntry;
import net.minecraftforge.fml.relauncher.Side;

public class MessageClientEditRequest implements MinestuckMessage
{
	private int username = -1;
	private int target;

	public MessageClientEditRequest() { }

	public MessageClientEditRequest(TileEntityComputer computer, int program)
	{
		username = computer.ownerId;
		target = computer.getData(program).getInteger("connectedClient");
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		if (buf.readableBytes() == 0)
			return;

		username = buf.readInt();
		target = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		if (username == -1)
			return;

		buf.writeInt(username);
		buf.writeInt(target);
	}

	@Override
	public void execute(EntityPlayer player)
	{
		UserListOpsEntry opsEntry = player == null ? null : player.getServer().getPlayerList().getOppedPlayers().getEntry(player.getGameProfile());
		if (!MinestuckConfig.giveItems)
		{
			if (username == -1)
				ServerEditHandler.onPlayerExit(player);
			else if (!MinestuckConfig.privateComputers || IdentifierHandler.encode(player).getId() == this.username || opsEntry != null && opsEntry.getPermissionLevel() >= 2)
				ServerEditHandler.newServerEditor((EntityPlayerMP) player, IdentifierHandler.getById(username), IdentifierHandler.getById(target));
			return;
		}

		EntityPlayerMP playerMP = IdentifierHandler.getById(target).getPlayer();

		if (playerMP != null && (!MinestuckConfig.privateComputers || IdentifierHandler.getById(username).appliesTo(player)) || opsEntry != null && opsEntry.getPermissionLevel() >= 2)
		{
			SburbConnection c = SkaianetHandler.getClientConnection(IdentifierHandler.getById(target));
			if (c == null || c.getServerIdentifier().getId() != username || !(c.isMain() || SkaianetHandler.giveItems(IdentifierHandler.getById(target))))
				return;
			for (int i = 0; i < 5; i++)
				if (i == 4)
				{
					if (c.enteredGame())
						continue;
					ItemStack card = AlchemyUtils.createCard(SburbHandler.getEntryItem(c.getClientIdentifier()), true);
					if (!playerMP.inventory.hasItemStack(card))
						c.givenItems()[i] = playerMP.inventory.addItemStackToInventory(card) || c.givenItems()[i];
				}
				else
				{
					Block block;
					switch (i)
					{
						case 0:
							block = MinestuckBlocks.miniCruxtruder;
							break;
						case 1:
							block = MinestuckBlocks.miniPunchDesignix;
							break;
						case 2:
							block = MinestuckBlocks.miniTotemLathe;
							break;
						default:
							block = MinestuckBlocks.miniAlchemiter;
							break;
					}
					ItemStack machine = new ItemStack(block, 1);
					if (i == 1 && !c.enteredGame())
						continue;
					if (!playerMP.inventory.hasItemStack(machine))
						c.givenItems()[i] = playerMP.inventory.addItemStackToInventory(machine) || c.givenItems()[i];
				}
			player.getServer().getPlayerList().syncPlayerInventory(playerMP);
		}
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}

}
