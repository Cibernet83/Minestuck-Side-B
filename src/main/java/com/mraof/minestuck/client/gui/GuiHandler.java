package com.mraof.minestuck.client.gui;

import com.mraof.minestuck.inventory.ContainerConsortMerchant;
import com.mraof.minestuck.inventory.ContainerGristWidget;
import com.mraof.minestuck.inventory.miniMachines.*;
import com.mraof.minestuck.inventory.ContainerUraniumCooker;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.tileentity.*;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler 
{
	public enum GuiId
	{
		MACHINE,
		COMPUTER,
		TRANSPORTALIZER,
		COLOR,
		MERCHANT,
		ALCHEMITER,
		STONE_TABLET,
	}
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
		if(id == GuiId.MACHINE.ordinal())
		{
			if(tileEntity instanceof TileEntityMiniAlchemiter)
				return new ContainerMiniAlchemiter(player.inventory, (TileEntityMiniAlchemiter) tileEntity);
			if(tileEntity instanceof TileEntityMiniCruxtruder)
				return new ContainerMiniCruxtruder(player.inventory, (TileEntityMiniCruxtruder) tileEntity);
			if(tileEntity instanceof TileEntityMiniTotemLathe)
				return new ContainerMiniTotemLathe(player.inventory, (TileEntityMiniTotemLathe) tileEntity);
			if(tileEntity instanceof TileEntityMiniPunchDesignix)
				return new ContainerMiniPunchDesignix(player.inventory, (TileEntityMiniPunchDesignix) tileEntity);
			else if(tileEntity instanceof TileEntityGristWidget)
				return new ContainerGristWidget(player.inventory, (TileEntityGristWidget) tileEntity);
			else if(tileEntity instanceof TileEntityUraniumCooker)
				return new ContainerUraniumCooker(player.inventory, (TileEntityUraniumCooker) tileEntity);
		} else if(id == GuiId.MERCHANT.ordinal())
			return new ContainerConsortMerchant(player);
		return null;
	}

	//returns an instance of the Gui you made earlier
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
		if(id == GuiId.MACHINE.ordinal())
			if(tileEntity instanceof TileEntityMiniAlchemiter)
				return new GuiMiniAlchemiter(player.inventory, (TileEntityMiniAlchemiter) tileEntity);
			if(tileEntity instanceof TileEntityMiniCruxtruder)
				return new GuiMiniCruxtruder(player.inventory, (TileEntityMiniCruxtruder) tileEntity);
		if(tileEntity instanceof TileEntityMiniTotemLathe)
			return new GuiMiniTotemLathe(player.inventory, (TileEntityMiniTotemLathe) tileEntity);
		if(tileEntity instanceof TileEntityMiniPunchDesignix)
			return new GuiMiniPunchDesignix(player.inventory, (TileEntityMiniPunchDesignix) tileEntity);
			else if(tileEntity instanceof TileEntityGristWidget)
				return new GuiGristWidget(player.inventory, (TileEntityGristWidget) tileEntity);
			else if(tileEntity instanceof TileEntityUraniumCooker)
				return new GuiUraniumCooker(player.inventory, (TileEntityUraniumCooker) tileEntity);
			
		if(tileEntity instanceof TileEntityComputer && id == GuiId.COMPUTER.ordinal())
			return new GuiComputer(Minecraft.getMinecraft(),(TileEntityComputer) tileEntity);
		
		if(id == GuiId.TRANSPORTALIZER.ordinal() && tileEntity instanceof TileEntityTransportalizer)
			return new GuiTransportalizer(Minecraft.getMinecraft(), (TileEntityTransportalizer) tileEntity);
		
		if(id == GuiId.COLOR.ordinal())
			return new GuiColorSelector(false);
		
		if(id == GuiId.MERCHANT.ordinal())
			return new GuiConsortShop(player);
		
		if(tileEntity instanceof TileEntityAlchemiter && id == GuiId.ALCHEMITER.ordinal())
			return new GuiAlchemiter((TileEntityAlchemiter) tileEntity);

		if(id == GuiId.STONE_TABLET.ordinal())
		{
			EnumHand hand = EnumHand.OFF_HAND;
			ItemStack stack = player.getHeldItemMainhand();
			ItemStack tablet = new ItemStack(MinestuckItems.stoneTablet);
			String text = "";
			if(!stack.isItemEqual(tablet))
			{
				hand = EnumHand.MAIN_HAND;
				if(!(stack = player.getHeldItemOffhand()).isItemEqual(tablet))
					return null;
			}

			if(stack.hasTagCompound())
			{
				text = stack.getTagCompound().getString("text");
			}

			boolean canEdit = player.getHeldItem(hand).isItemEqual(new ItemStack(MinestuckItems.carvingTool));
			return new GuiStoneTablet(player, player.getHeldItemMainhand(), text, canEdit);
		}

		return null;
		
	}
	
}
