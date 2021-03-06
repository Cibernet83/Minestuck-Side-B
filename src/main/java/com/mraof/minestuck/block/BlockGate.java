package com.mraof.minestuck.block;

import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.item.block.MSItemBlock;
import com.mraof.minestuck.tileentity.TileEntityGate;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockGate extends MSBlockBase
{

	protected static final AxisAlignedBB GATE_AABB = new AxisAlignedBB(0.0D, 0.45D, 0.0D, 1.0D, 0.55D, 1.0D);
	public static PropertyBool isMainComponent = PropertyBool.create("main_component");

	public BlockGate(String name)
	{
		super(name, Material.PORTAL);
		setDefaultState(getDefaultState().withProperty(isMainComponent, false));
		setLightLevel(0.75F);
		setBlockUnbreakable();
		setResistance(25.0F);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(isMainComponent, meta == 0);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(isMainComponent) ? 0 : 1;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.INVISIBLE;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return GATE_AABB;
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

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		if (!this.isValid(pos, worldIn, state))
		{
			BlockPos mainPos = pos;
			if (!state.getValue(isMainComponent))
				mainPos = findMainComponent(pos, worldIn);

			if (mainPos == null)
				worldIn.setBlockToAir(pos);
			else removePortal(mainPos, worldIn);
		}
	}

	protected boolean isValid(BlockPos pos, World world, IBlockState state)
	{
		if (state.getValue(isMainComponent))
			return isValid(pos, world);
		else
		{
			BlockPos mainPos = findMainComponent(pos, world);
			if (mainPos != null)
				return isValid(mainPos, world);
			else return false;
		}
	}

	protected boolean isValid(BlockPos pos, World world)
	{
		for (int x = -1; x <= 1; x++)
			for (int z = -1; z <= 1; z++)
				if (x != 0 || z != 0)
				{
					IBlockState block = world.getBlockState(pos.add(x, 0, z));
					if (block.getBlock() != this || block.getValue(isMainComponent))
						return false;
				}

		return true;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		super.breakBlock(worldIn, pos, state);
		if (state.getValue(isMainComponent))
			removePortal(pos, worldIn);
		else
		{
			BlockPos mainPos = findMainComponent(pos, worldIn);
			if (mainPos != null)
				removePortal(mainPos, worldIn);
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		if (entityIn instanceof EntityPlayerMP && entityIn.getRidingEntity() == null && entityIn.getPassengers().isEmpty())
		{


			BlockPos mainPos = pos;
			if (!state.getValue(isMainComponent))
			{
				if (this != MinestuckBlocks.gate)
					mainPos = this.findMainComponent(pos, worldIn);
				else return;
			}

			if (mainPos != null)
			{
				if (entityIn.timeUntilPortal != 0)
				{
					entityIn.timeUntilPortal = entityIn.getPortalCooldown();
					return;
				}
				TileEntity te = worldIn.getTileEntity(mainPos);
				if (te instanceof TileEntityGate)
					((TileEntityGate) te).teleportEntity(worldIn, (EntityPlayerMP) entityIn, this);
			}
			else worldIn.setBlockToAir(pos);
		}
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, isMainComponent);
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return state.getValue(isMainComponent);
	}

	@Override
	public TileEntity createTileEntity(World worldIn, IBlockState state)
	{
		return new TileEntityGate();
	}

	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion)
	{
		if (this instanceof BlockReturnNode || MinestuckConfig.canBreakGates)
			return super.getExplosionResistance(world, pos, exploder, explosion);
		else return 3600000.0F;
	}

	@Override
	public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager)
	{
		return true;
	}

	protected BlockPos findMainComponent(BlockPos pos, World world)
	{
		for (int x = -1; x <= 1; x++)
			for (int z = -1; z <= 1; z++)
				if (x != 0 || z != 0)
				{
					IBlockState block = world.getBlockState(pos.add(x, 0, z));
					if (block.getBlock() == this && block.getValue(isMainComponent))
						return pos.add(x, 0, z);
				}

		return null;
	}

	protected void removePortal(BlockPos pos, World world)
	{
		for (int x = -1; x <= 1; x++)
			for (int z = -1; z <= 1; z++)
				if (world.getBlockState(pos.add(x, 0, z)).getBlock() == this)
					world.setBlockToAir(pos.add(x, 0, z));
	}

	@Override
	public MSItemBlock getItemBlock()
	{
		return null;
	}
}