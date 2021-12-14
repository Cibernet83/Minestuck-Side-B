package com.mraof.minestuck.block;

import com.mraof.minestuck.tileentity.TileEntityGristHopper;
import com.mraof.minestuck.util.IdentifierHandler;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockGristHopper extends MSBlockBase implements ITileEntityProvider
{

    public BlockGristHopper()
    {
        super("gristHopper", Material.IRON, MapColor.EMERALD);
        setHarvestLevel("pickaxe", 0);
        setHardness(3.0F);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        TileEntityGristHopper te = (TileEntityGristHopper)worldIn.getTileEntity(pos);

        if(te != null && placer instanceof EntityPlayer)
            te.owner = IdentifierHandler.encode((EntityPlayer) placer);

        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }
    @Override
    public boolean isTopSolid(IBlockState state) { return true; }

    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityGristHopper();
    }
}
