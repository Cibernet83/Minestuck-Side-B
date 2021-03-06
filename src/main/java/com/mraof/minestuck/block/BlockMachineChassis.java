package com.mraof.minestuck.block;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.client.gui.MinestuckGuiHandler;
import com.mraof.minestuck.tileentity.TileEntityMachineChassis;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockMachineChassis extends MSBlockBase implements ITileEntityProvider
{


	public BlockMachineChassis()
	{
		super("machineChassis", Material.IRON, MapColor.IRON);
		setHarvestLevel("pickaxe", 0);
		setHardness(3.0F);
	}

	@Override
	public boolean isTopSolid(IBlockState state) { return true; }

	@Override
	public boolean isNormalCube(IBlockState state)
	{
		return true;
	}

	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}

	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		checkPowered(worldIn, pos);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		super.onBlockAdded(worldIn, pos, state);
		checkPowered(worldIn, pos);

	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntityMachineChassis te = (TileEntityMachineChassis) worldIn.getTileEntity(pos);
		if (te != null)
			InventoryHelper.dropInventoryItems(worldIn, pos, te);
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (!worldIn.isRemote)
			playerIn.openGui(Minestuck.instance, MinestuckGuiHandler.GuiId.MACHINE_CHASSIS.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side)
	{
		return true;
	}

	private void checkPowered(World worldIn, BlockPos pos)
	{
		if (worldIn.isBlockPowered(pos) && worldIn.getTileEntity(pos) instanceof TileEntityMachineChassis)
		{
			TileEntityMachineChassis te = (TileEntityMachineChassis) worldIn.getTileEntity(pos);
			te.assemble();
		}
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityMachineChassis();
	}
}
