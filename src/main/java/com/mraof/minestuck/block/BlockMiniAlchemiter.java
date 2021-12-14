package com.mraof.minestuck.block;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.client.gui.GuiHandler;
import com.mraof.minestuck.tileentity.TileEntityMiniAlchemiter;
import com.mraof.minestuck.tileentity.TileEntitySburbMachine;
import com.mraof.minestuck.util.IdentifierHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockMiniAlchemiter extends BlockSburbMachine
{
	private static final AxisAlignedBB ALCHMITER_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1/2D, 1.0D);
	private static final AxisAlignedBB[] ALCHEMITER_POLE_AABB = {new AxisAlignedBB(0.0D, 2/16D, 0.0D, 4.5/16D, 1.0D, 1/8D), new AxisAlignedBB(7/8D, 2/16D, 0.0D, 1.0D, 1.0D, 4.5/16D), new AxisAlignedBB(11.5/16D, 2/16D, 7/8D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 2/16D, 11.5/16D, 1/8D, 1.0D, 1.0D)};

	public BlockMiniAlchemiter()
	{
		 super("miniAlchemiter");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileEntityMiniAlchemiter();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (!(tileEntity instanceof TileEntitySburbMachine) || playerIn.isSneaking())
			return false;

		if(!worldIn.isRemote)
		{
			((TileEntitySburbMachine) tileEntity).owner = IdentifierHandler.encode(playerIn);
			playerIn.openGui(Minestuck.instance, GuiHandler.GuiId.MACHINE.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return ALCHMITER_AABB;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_)
	{
		super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, p_185477_7_);
		AxisAlignedBB bb = ALCHEMITER_POLE_AABB[getActualState(state, worldIn, pos).getValue(FACING).getHorizontalIndex()].offset(pos);
		if(entityBox.intersects(bb))
			collidingBoxes.add(bb);
	}
}
