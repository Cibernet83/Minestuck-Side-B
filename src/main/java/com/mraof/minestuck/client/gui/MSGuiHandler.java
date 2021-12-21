package com.mraof.minestuck.client.gui;

import com.mraof.minestuck.inventory.*;
import com.mraof.minestuck.inventory.miniMachines.ContainerMiniAlchemiter;
import com.mraof.minestuck.inventory.miniMachines.ContainerMiniCruxtruder;
import com.mraof.minestuck.inventory.miniMachines.ContainerMiniPunchDesignix;
import com.mraof.minestuck.inventory.miniMachines.ContainerMiniTotemLathe;
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

public class MSGuiHandler implements IGuiHandler
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
		MACHINE_CHASSIS,
		AUTO_CAPTCHA,
		PORKHOLLOW_ATM,
		BOONDOLLAR_REGISTER,
		STRIFE_CARD,
		MODUS_CONTROL_DECK,
	}
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));

		switch(GuiId.values()[id])
		{
			case MACHINE:
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
				break;
			case MERCHANT:
				return new ContainerConsortMerchant(player);
			case MACHINE_CHASSIS:
				return new ContainerMachineChasis(player.inventory, (TileEntityMachineChasis) tileEntity);
			case AUTO_CAPTCHA:
				return new ContainerAutoCaptcha(player.inventory, (TileEntityAutoCaptcha) tileEntity);
		}
		return null;
	}

	//returns an instance of the Gui you made earlier
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));

		switch(GuiId.values()[id])
		{
			case MACHINE:
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
				break;
			case COMPUTER:
				return new GuiComputer(Minecraft.getMinecraft(),(TileEntityComputer) tileEntity);
			case TRANSPORTALIZER:
				return new GuiTransportalizer(Minecraft.getMinecraft(), (TileEntityTransportalizer) tileEntity);
			case COLOR:
				return new GuiColorSelector(false);
			case MERCHANT:
				return new GuiConsortShop(player);
			case ALCHEMITER:
				return new GuiAlchemiter((TileEntityAlchemiter) tileEntity);
			case STONE_TABLET:
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


			case MACHINE_CHASSIS:
				return new GuiMachineChasis(player.inventory, (TileEntityMachineChasis) tileEntity);
			case AUTO_CAPTCHA:
				return new GuiAutoCaptcha(player.inventory, (TileEntityAutoCaptcha) tileEntity);
			case PORKHOLLOW_ATM:
				return new GuiCeramicPorkhollow(player);
			case BOONDOLLAR_REGISTER:
				return new GuiBoondollarRegister(player, (TileEntityBoondollarRegister) tileEntity);
			case STRIFE_CARD:
				return new GuiStrifeCard(player);
			case MODUS_CONTROL_DECK:
				return new GuiModusControlDeck((TileEntityModusControlDeck) tileEntity);

		}


		return null;
		
	}
	
}
