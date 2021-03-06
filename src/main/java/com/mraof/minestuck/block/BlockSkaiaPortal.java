package com.mraof.minestuck.block;

import com.mraof.minestuck.item.MinestuckTabs;
import com.mraof.minestuck.tileentity.TileEntitySkaiaPortal;
import com.mraof.minestuck.util.IRegistryObject;
import com.mraof.minestuck.world.MinestuckDimensionHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockSkaiaPortal extends BlockContainer implements IRegistryBlock
{
	protected static final AxisAlignedBB SKAIA_PORTAL_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1 / 16D, 1.0D);
	private final String regName;

	public BlockSkaiaPortal(Material material)
	{
		super(material);

		setUnlocalizedName("skaiaPortal");
		this.setCreativeTab(MinestuckTabs.minestuck);
		regName = IRegistryObject.unlocToReg("skaiaPortal");
		MinestuckBlocks.blocks.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		TileEntitySkaiaPortal tileEntity = (TileEntitySkaiaPortal) this.createNewTileEntity(world);
		tileEntity.destination.dim = MinestuckDimensionHandler.skaiaDimensionId == world.provider.getDimension() ? 0 : MinestuckDimensionHandler.skaiaDimensionId;
		return tileEntity;
	}

	public TileEntity createNewTileEntity(World var1)
	{
		return new TileEntitySkaiaPortal();
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return SKAIA_PORTAL_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		return !side.getAxis().isHorizontal() && super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_)
	{
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
	{
		if (entity.getRidingEntity() == null && entity.getPassengers().isEmpty() && !world.isRemote && entity.timeUntilPortal == 0)
		{
			TileEntitySkaiaPortal portal = (TileEntitySkaiaPortal) world.getTileEntity(pos);
			portal.teleportEntity(entity);
		}
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return ItemStack.EMPTY;
	}

	//	/**
	//	 * Called upon block activation (right click on the block.)
	//	 */
	//	@Override
	//	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	//	{
	//		if (worldIn.isRemote)
	//		{
	//			return true;
	//		}
	//		int newDimension = ((TileEntityGatePortal) worldIn.getTileEntity(pos)).destination.dim + 1;
	//		if(worldIn.provider.getDimensionId() != newDimension && DimensionManager.isDimensionRegistered(newDimension))
	//		{
	//			this.destinationDimension = newDimension;
	//			((TileEntityGatePortal) worldIn.getTileEntity(pos)).destination.dim = newDimension;
	//		}
	//
	//		return true;
	//	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.INVISIBLE;
	}

	public void setDestinationDimension(World world, int x, int y, int z, int destinationDimension)
	{
		((TileEntitySkaiaPortal) world.getTileEntity(new BlockPos(x, y, z))).destination.dim = destinationDimension;
	}

	@Override
	public void register(IForgeRegistry<Block> registry)
	{
		setRegistryName(regName);
		registry.register(this);
	}
}
