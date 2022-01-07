package com.mraof.minestuck.block;

import com.mraof.minestuck.item.MinestuckTabs;
import com.mraof.minestuck.tileentity.TileEntityMachine;
import com.mraof.minestuck.tileentity.TileEntityMiniSburbMachine;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class MSFacingBase extends MSBlockContainer
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public MSFacingBase(String name)
	{
		super(name, Material.ROCK);
		setHardness(3.0F);
		setHarvestLevel("pickaxe", 0);
		setDefaultState(getDefaultState().withProperty(FACING, EnumFacing.SOUTH));
		this.setCreativeTab(MinestuckTabs.minestuck);
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FACING);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntityMachine te = (TileEntityMachine) worldIn.getTileEntity(pos);
		if (te != null) InventoryHelper.dropInventoryItems(worldIn, pos, te);

		super.breakBlock(worldIn, pos, state);
	}	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FACING).getHorizontalIndex() * 4;
	}

	public abstract boolean renderCheckItem(EntityPlayerSP player, ItemStack stack, RenderGlobal render, RayTraceResult rayTraceResult, float partialTicks, EnumFacing placedFacing);	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta / 4));
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		return new ArrayList<ItemStack>();
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}



	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	// Inform the game that this object has a comparator value.
	@Override
	public boolean hasComparatorInputOverride(IBlockState state) { return true; }

	// Will provide a redstone signal through a comparator with the output level corresponding to how many items can be alchemized with the player's current grist cache.
	// If no item can be alchemized, it will provide no signal to the comparator.
	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
	{
		TileEntityMiniSburbMachine te = (TileEntityMiniSburbMachine) world.getTileEntity(pos);
		if (te != null)
			return te.comparatorValue();
		return 0;
	}

	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side)
	{
		return side != null;
	}



	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getStateFromMeta(meta).withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(Item.getItemFromBlock(this), 1);
	}



	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return BlockFaceShape.UNDEFINED;
	}
}
