package com.mraof.minestuck.network;

import com.mraof.minestuck.tileentity.TileEntityMachineChasis;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class MachineChassisPacket extends MinestuckPacket
{
    public BlockPos pos;

    @Override
    public MinestuckPacket generatePacket(Object... dat)
    {
        TileEntity te = (TileEntity)dat[0];
        this.data.writeInt(te.getPos().getX());
        this.data.writeInt(te.getPos().getY());
        this.data.writeInt(te.getPos().getZ());
        return this;
    }

    @Override
    public MinestuckPacket consumePacket(ByteBuf data)
    {
        this.pos = new BlockPos(data.readInt(), data.readInt(), data.readInt());
        return this;
    }

    @Override
    public void execute(EntityPlayer player)
    {
        if (player.getEntityWorld().isBlockLoaded(this.pos))
        {
            TileEntityMachineChasis te = (TileEntityMachineChasis) player.getEntityWorld().getTileEntity(pos);
            if(te != null)
                te.assemble();
        }
    }

    @Override
    public EnumSet<Side> getSenderSide() {
        return null;
    }
}
