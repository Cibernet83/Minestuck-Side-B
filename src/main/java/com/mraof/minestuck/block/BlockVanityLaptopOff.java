package com.mraof.minestuck.block;

import com.mraof.minestuck.item.block.MSItemBlock;
import com.mraof.minestuck.item.block.MSItemBlockMultiTexture;
import com.mraof.minestuck.tileentity.TileEntityComputer;
import com.mraof.minestuck.util.ComputerProgram;
import com.mraof.minestuck.util.IRegistryObject;
import com.mraof.minestuck.util.IUnlocSerializable;
import com.mraof.minestuck.util.IdentifierHandler;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockVanityLaptopOff extends BlockComputerOff
{
	protected static final AxisAlignedBB COMPUTER_AABB = new AxisAlignedBB(1/32D, 0.0D, 7/32D, 31/32D, 0.5/16D, 24.8/32D);
	protected static final AxisAlignedBB COMPUTER_SCREEN_AABB = new AxisAlignedBB(0.5/16D, 0.5D/16, 11.8/16D, 15.5/16D, 9.5/16D, 12.4/16D);

	public static final PropertyEnum<BlockType> VARIANT = PropertyEnum.create("type", BlockType.class);
	public static final PropertyDirection FACING = BlockComputerOff.FACING;
	
	public BlockVanityLaptopOff(String name)
	{
		super(name);
	}
	
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
	{
		for(BlockType type : BlockType.values())
			items.add(new ItemStack(this, 1, type.ordinal()));
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FACING, VARIANT);
	}
	
	@Override
	public int damageDropped(IBlockState state)
	{
		return (state.getValue(VARIANT)).ordinal();
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((state.getValue(FACING).getHorizontalIndex()) << 2) + ((state.getValue(VARIANT)).ordinal() % 4);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal((meta >> 2) % 4)).withProperty(VARIANT, BlockType.values()[meta & 3]);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack heldItem = playerIn.getHeldItem(hand);
		if(playerIn.isSneaking() || !EnumFacing.UP.equals(facing) || !heldItem.isEmpty() && ComputerProgram.getProgramID(heldItem) == -2)
			return false;
		
		if(!worldIn.isRemote)
		{
			worldIn.setBlockState(pos, MinestuckBlocks.blockLaptopOn.getDefaultState()
					.withProperty(FACING, state.getValue(FACING))
					.withProperty(VARIANT, state.getValue(VARIANT))
					, 2);
			
			TileEntityComputer te = (TileEntityComputer) worldIn.getTileEntity(pos);
			te.owner = IdentifierHandler.encode(playerIn);
			MinestuckBlocks.blockLaptopOn.onBlockActivated(worldIn, pos, worldIn.getBlockState(pos), playerIn, hand, facing, hitX, hitY, hitZ);
		}
		
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		int l = MathHelper.floor((double)(placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		
		EnumFacing facing = new EnumFacing[]{EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST}[l];
		
		worldIn.setBlockState(pos, state.withProperty(FACING, facing).withProperty(VARIANT, BlockType.values()[stack.getMetadata() % 4]), 2);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		if(state.getValue(VARIANT)==BlockType.LUNCH_TOP)
		{
			return modifyAABBForDirection(state.getValue(FACING), new AxisAlignedBB(5/16D, 0.0D, 5/16D, 11/16D, 3.5/16D, 10/16D));
		}
		return modifyAABBForDirection(state.getValue(FACING), COMPUTER_AABB);
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(MinestuckBlocks.blockLaptopOff, state.getValue(VARIANT).ordinal());
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_)
	{
		super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, p_185477_7_);
		if(state.getValue(VARIANT)!=BlockType.LUNCH_TOP)
		{
			EnumFacing rotation = state.getValue(FACING);
			AxisAlignedBB bb = modifyAABBForDirection(rotation, COMPUTER_SCREEN_AABB).offset(pos);
			if(entityBox.intersects(bb))
				collidingBoxes.add(bb);
		}
	}
	
	public enum BlockType implements IUnlocSerializable
	{
		CROCKER_TOP("crockertop", MapColor.RED),
		HUB_TOP("hubtop", MapColor.GREEN),
		LAP_TOP("laptop", MapColor.IRON),
		LUNCH_TOP("lunchtop", MapColor.RED);
		private final String name, regName;
		private final MapColor color;
		BlockType(String name, MapColor color)
		{
			this.name = name;
			this.regName = IRegistryObject.unlocToReg(name);
			this.color = color;
		}
		@Override
		public String getName()
		{
			return regName;
		}
		@Override
		public String getUnlocalizedName()
		{
			return name;
		}
	}

	@Override
	public MSItemBlock getItemBlock()
	{
		return new MSItemBlockMultiTexture(this, BlockType.values());
	}
}
