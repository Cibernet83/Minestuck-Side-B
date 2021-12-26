package com.mraof.minestuck.network;

import com.mraof.minestuck.tileentity.TileEntityMachineChassis;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public class PacketMachineChassis extends MinestuckPacket
{
    public BlockPos pos;

    @Override
    public void generatePacket(Object... dat)
    {
        TileEntity te = (TileEntity)dat[0];
        this.data.writeInt(te.getPos().getX());
        this.data.writeInt(te.getPos().getY());
        this.data.writeInt(te.getPos().getZ());

    }

    @Override
    public void consumePacket(ByteBuf data)
    {
        this.pos = new BlockPos(data.readInt(), data.readInt(), data.readInt());

    }

    @Override
    public void execute(EntityPlayer player)
    {
        if (player.getEntityWorld().isBlockLoaded(this.pos))
        {
            TileEntityMachineChassis te = (TileEntityMachineChassis) player.getEntityWorld().getTileEntity(pos);
            if(te != null)
                te.assemble();
        }
    }

    @Override
    public EnumSet<Side> getSenderSide() {
        return null;
    }
}
