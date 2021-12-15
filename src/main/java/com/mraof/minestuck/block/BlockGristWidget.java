package com.mraof.minestuck.block;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.client.gui.MSGuiHandler;
import com.mraof.minestuck.tileentity.TileEntityGristWidget;
import com.mraof.minestuck.tileentity.TileEntityMachine;
import com.mraof.minestuck.util.IdentifierHandler;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGristWidget extends MSBlockContainer
{
	protected static final AxisAlignedBB[] GRIST_WIDGET_AABB = {new AxisAlignedBB(2 / 16D, 0.0D, 5 / 16D, 14 / 16D, 2.1 / 16D, 12 / 16D), new AxisAlignedBB(4 / 16D, 0.0D, 2 / 16D, 11 / 16D, 2.1 / 16D, 14 / 16D), new AxisAlignedBB(2 / 16D, 0.0D, 4 / 16D, 14 / 16D, 2.1 / 16D, 11 / 16D), new AxisAlignedBB(5 / 16D, 0.0D, 2 / 16D, 12 / 16D, 2.1 / 16D, 14 / 16D)};

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool HAS_ITEM = PropertyBool.create("has_item");

	protected BlockGristWidget()
	{
		super("gristWidget", Material.ROCK);
		setHardness(3.0F);
		setHarvestLevel("pickaxe", 0);
		setDefaultState(getDefaultState().withProperty(FACING, EnumFacing.SOUTH));
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FACING, HAS_ITEM);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FACING).getHorizontalIndex() * 4;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta / 4));
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
									EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		TileEntity tileEntity = worldIn.getTileEntity(pos);

		if (!(tileEntity instanceof TileEntityGristWidget) || playerIn.isSneaking())
			return false;

		if (!worldIn.isRemote)
		{
			((TileEntityGristWidget) tileEntity).owner = IdentifierHandler.encode(playerIn);
			playerIn.openGui(Minestuck.instance, MSGuiHandler.GuiId.MACHINE.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntityMachine te = (TileEntityMachine) worldIn.getTileEntity(pos);
		if (te != null) InventoryHelper.dropInventoryItems(worldIn, pos, te);
		super.breakBlock(worldIn, pos, state);
	}


	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getStateFromMeta(meta).withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityGristWidget();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		EnumFacing rotation = state.getValue(FACING);
		return GRIST_WIDGET_AABB[rotation.getHorizontalIndex() % 4];
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		TileEntity te = worldIn.getTileEntity(pos);
		if (!(te instanceof TileEntityMachine))
			return state;
		return state.withProperty(HAS_ITEM, !((TileEntityMachine) te).getStackInSlot(0).isEmpty());
	}

	public static void updateItem(boolean b, World world, BlockPos pos)
	{
		IBlockState oldState = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, oldState, oldState.withProperty(HAS_ITEM, b), 3);
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return BlockFaceShape.UNDEFINED;
	}
}