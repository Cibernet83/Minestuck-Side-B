package com.mraof.minestuck.network.message;

import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.event.WeaponAssignedEvent;
import com.mraof.minestuck.event.handler.StrifeEventHandler;
import com.mraof.minestuck.item.IPropertyWeapon;
import com.mraof.minestuck.item.properties.WeaponProperty;
import com.mraof.minestuck.network.MinestuckMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;

import java.util.List;

public class MessageLeftClickEmpty implements MinestuckMessage
{
	public MessageLeftClickEmpty() { }

	@Override
	public void fromBytes(ByteBuf buf) { }

	@Override
	public void toBytes(ByteBuf buf) { }

	@Override
	public void execute(EntityPlayer player)
	{
		ItemStack stack = player.getHeldItemMainhand();
		boolean checkAssigned = MinestuckConfig.restrictedStrife;

		if (checkAssigned)
		{
			WeaponAssignedEvent event = new WeaponAssignedEvent(player, stack, StrifeEventHandler.isStackAssigned(stack));
			MinecraftForge.EVENT_BUS.post(event);
			checkAssigned = !event.getCheckResult();
		}

		if (stack.getItem() instanceof IPropertyWeapon && !checkAssigned)
		{
			List<WeaponProperty> propertyList = ((IPropertyWeapon) stack.getItem()).getProperties(stack);
			for (WeaponProperty p : propertyList)
				p.onEmptyHit(stack, player);
		}
	}

	@Override
	public Side toSide()
	{
		return Side.SERVER;
	}
}
