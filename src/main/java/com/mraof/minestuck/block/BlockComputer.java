package com.mraof.minestuck.block;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.client.gui.MinestuckGuiHandler;
import com.mraof.minestuck.network.skaianet.SkaiaClient;
import com.mraof.minestuck.tileentity.TileEntityComputer;
import com.mraof.minestuck.util.ComputerProgram;
import com.mraof.minestuck.util.IdentifierHandler;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;

public class BlockComputer extends MSBlockBase implements ITileEntityProvider
{
	public static final PropertyDirection DIRECTION = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyEnum<EnumComputerState> COMPUTER_STATE = PropertyEnum.create("state", EnumComputerState.class);
	protected final AxisAlignedBB[] AABBS;

	public BlockComputer(String name, AxisAlignedBB... aabb)
	{
		super(name, Material.ROCK);
		setHardness(4.0F);
		setHarvestLevel("pickaxe", 0);
		lightOpacity = 1;
		this.translucent = true;
		setDefaultState(getDefaultState().withProperty(DIRECTION, EnumFacing.NORTH).withProperty(COMPUTER_STATE, EnumComputerState.OFF));

		AABBS = aabb;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(DIRECTION, EnumFacing.getHorizontal(meta % 4)).withProperty(COMPUTER_STATE, EnumComputerState.values()[meta / 4]);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(DIRECTION).getHorizontalIndex() + state.getValue(COMPUTER_STATE).ordinal() * 4;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return BlockDecor.modifyAABBForDirection(state.getValue(DIRECTION), AABBS[0]);
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_)
	{
		for (AxisAlignedBB bb : AABBS)
		{
			bb = BlockDecor.modifyAABBForDirection(state.getValue(DIRECTION), bb).offset(pos);
			if (entityBox.intersects(bb))
				collidingBoxes.add(bb);
		}
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		dropItems(worldIn, pos.getX(), pos.getY(), pos.getZ(), state);
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
									EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		TileEntityComputer tileEntity = (TileEntityComputer) worldIn.getTileEntity(pos);

		if (tileEntity == null || playerIn.isSneaking())
			return false;

		if (state.getValue(COMPUTER_STATE) == EnumComputerState.OFF)
		{
			ItemStack heldItem = playerIn.getHeldItem(hand);
			if (!EnumFacing.UP.equals(facing) || !heldItem.isEmpty() && ComputerProgram.getProgramID(heldItem) == -2)
				return false;
			if (!worldIn.isRemote)
				worldIn.setBlockState(pos, state.withProperty(COMPUTER_STATE, EnumComputerState.ON), 2);
			tileEntity.owner = IdentifierHandler.encode(playerIn);
		}

		int id = ComputerProgram.getProgramID(playerIn.getHeldItem(hand));
		if (id != -2 && !tileEntity.hasProgram(id) && tileEntity.installedPrograms.size() < 2 && !tileEntity.hasProgram(-1))
		{
			if (worldIn.isRemote)
				return true;
			playerIn.setHeldItem(hand, ItemStack.EMPTY);
			if (id == -1)
			{
				tileEntity.closeAll();
				worldIn.setBlockState(pos, state.withProperty(COMPUTER_STATE, EnumComputerState.BSOD), 2);
			}
			else tileEntity.installedPrograms.put(id, true);
			tileEntity.markDirty();
			worldIn.notifyBlockUpdate(pos, state, state, 3);
			return true;
		}

		if (worldIn.isRemote && SkaiaClient.requestData(tileEntity))
			playerIn.openGui(Minestuck.instance, MinestuckGuiHandler.GuiId.COMPUTER.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());

		return true;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		int l = MathHelper.floor((double) (placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		EnumFacing facing = new EnumFacing[]{EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST}[l];

		worldIn.setBlockState(pos, state.withProperty(DIRECTION, facing), 2);
	}

	@Override
	public EnumPushReaction getMobilityFlag(IBlockState state)
	{
		return EnumPushReaction.BLOCK;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, DIRECTION, COMPUTER_STATE);
	}

	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return false;
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		list.add(new ItemStack(MinestuckBlocks.sburbComputer));

		return list;
	}

	private void dropItems(World world, int x, int y, int z, IBlockState state)
	{
		Random rand = new Random();
		TileEntityComputer te = (TileEntityComputer) world.getTileEntity(new BlockPos(x, y, z));
		if (te == null)
		{
			return;
		}
		te.closeAll();
		float factor = 0.05F;

		Iterator<Map.Entry<Integer, Boolean>> it = te.installedPrograms.entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry<Integer, Boolean> pairs = it.next();
			if (!pairs.getValue())
				continue;
			int program = pairs.getKey();

			float rx = rand.nextFloat() * 0.8F + 0.1F;
			float ry = rand.nextFloat() * 0.8F + 0.1F;
			float rz = rand.nextFloat() * 0.8F + 0.1F;
			EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, ComputerProgram.getItem(program));
			entityItem.motionX = rand.nextGaussian() * factor;
			entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
			entityItem.motionZ = rand.nextGaussian() * factor;
			world.spawnEntity(entityItem);
		}
		if (state.getValue(COMPUTER_STATE) == EnumComputerState.BSOD)
		{
			float rx = rand.nextFloat() * 0.8F + 0.1F;
			float ry = rand.nextFloat() * 0.8F + 0.1F;
			float rz = rand.nextFloat() * 0.8F + 0.1F;
			EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, ComputerProgram.getItem(-1));
			entityItem.motionX = rand.nextGaussian() * factor;
			entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
			entityItem.motionZ = rand.nextGaussian() * factor;
			world.spawnEntity(entityItem);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TileEntityComputer();
	}

	public enum EnumComputerState implements IStringSerializable
	{
		OFF("off"),
		ON("on"),
		BSOD("bsod");

		final String name;

		EnumComputerState(String name)
		{
			this.name = name;
		}

		@Override
		public String getName()
		{
			return name;
		}
	}
}
