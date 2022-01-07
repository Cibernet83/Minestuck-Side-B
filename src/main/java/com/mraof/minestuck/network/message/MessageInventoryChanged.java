package com.mraof.minestuck.network.message;

import com.mraof.minestuck.client.gui.playerStats.GuiInventoryEditmode;
import com.mraof.minestuck.inventory.ContainerEditmode;
import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;

public class MessageInventoryChanged implements MinestuckMessage
{
	private ArrayList<ItemStack> inventory;
	private boolean less, more;

	private MessageInventoryChanged() { }

	public MessageInventoryChanged(ArrayList<ItemStack> inventory, boolean less, boolean more)
	{
		this.inventory = inventory;
		this.less = less;
		this.more = more;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeBoolean(less);
		buf.writeBoolean(more);
		for(ItemStack stack : inventory)
			ByteBufUtils.writeItemStack(buf, stack);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		less = buf.readBoolean();
		more = buf.readBoolean();
		inventory = new ArrayList<>();
		while(buf.readableBytes() > 0)
			inventory.add(ByteBufUtils.readItemStack(buf));
	}

	@Override
	public void execute(EntityPlayer player)
	{
		if(player.openContainer instanceof ContainerEditmode)
		{
			for(int i = 0; i < inventory.size(); i++)
			{
				((ContainerEditmode)player.openContainer).inventoryItemStacks.set(i, inventory.get(i) == null ? null : inventory.get(i).copy());
				((ContainerEditmode)player.openContainer).inventory.setInventorySlotContents(i, inventory.get(i));
			}
			if(FMLClientHandler.instance().getClient().currentScreen instanceof GuiInventoryEditmode)
			{
				((GuiInventoryEditmode)FMLClientHandler.instance().getClient().currentScreen).less = less;
				((GuiInventoryEditmode)FMLClientHandler.instance().getClient().currentScreen).more = more;
			}
		}
	}

	@Override
	public Side toSide()
	{
		return Side.CLIENT;
	}
}
