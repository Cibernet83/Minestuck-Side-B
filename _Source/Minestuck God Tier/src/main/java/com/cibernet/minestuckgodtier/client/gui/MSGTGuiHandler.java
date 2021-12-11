package com.cibernet.minestuckgodtier.client.gui;

import com.cibernet.minestuckgodtier.inventory.ContainerItemVoid;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class MSGTGuiHandler implements IGuiHandler
{
	public static final int MEDITATE = 0;
	public static final int ITEM_VOID = 1;
	public static final int HOARD_SELECTOR = 2;
	public static final int SASH = 3;

	@Nullable
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch(ID)
		{
			case ITEM_VOID:
				return new ContainerItemVoid(player);

		}
		return null;
	}

	@Nullable
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (ID)
		{
			case MEDITATE:
				return new GuiGodTierMeditation(player);
			case SASH:
				return new GuiManageBadges(player);
			case ITEM_VOID:
				return new GuiItemVoid(player);
			case HOARD_SELECTOR:
				return new GuiGristHoardSelector(player);
		}
		return null;
	}
}
