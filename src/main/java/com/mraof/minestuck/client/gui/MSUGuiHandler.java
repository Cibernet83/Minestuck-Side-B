package com.mraof.minestuck.client.gui;

import static com.mraof.minestuck.util.MSUUtils.*;

import com.mraof.minestuck.inventory.ContainerAutoCaptcha;
import com.mraof.minestuck.inventory.ContainerMachineChasis;
import com.mraof.minestuck.tileentity.TileEntityAutoCaptcha;
import com.mraof.minestuck.tileentity.TileEntityBoondollarRegister;
import com.mraof.minestuck.tileentity.TileEntityMachineChasis;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class MSUGuiHandler implements IGuiHandler
{
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity te = world.getTileEntity(new BlockPos(x,y,z));
        switch(ID)
        {
            case MACHINE_CHASIS_GUI:
                return new ContainerMachineChasis(player.inventory, (TileEntityMachineChasis) te);
            case AUTO_CAPTCHA_GUI:
                return new ContainerAutoCaptcha(player.inventory, (TileEntityAutoCaptcha) te);

        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity te = world.getTileEntity(new BlockPos(x,y,z));
        switch(ID)
        {
            case MACHINE_CHASIS_GUI:
                return new GuiMachineChasis(player.inventory, (TileEntityMachineChasis) te);
            case AUTO_CAPTCHA_GUI:
                return new GuiAutoCaptcha(player.inventory, (TileEntityAutoCaptcha) te);
            case PORKHOLLOW_ATM_GUI:
                return new GuiPorkhollowAtm(player);
            case BOONDOLLAR_REGISTER_GUI:
                return new GuiBoondollarRegister(player, (TileEntityBoondollarRegister) te);
            case STRIFE_CARD_GUI:
                return new GuiStrifeCard(player);

        }
        return null;
    }
}
