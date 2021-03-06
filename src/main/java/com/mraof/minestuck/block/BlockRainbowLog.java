package com.mraof.minestuck.block;

import com.mraof.minestuck.item.MinestuckTabs;
import com.mraof.minestuck.util.IRegistryObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistry;

public class BlockRainbowLog extends BlockLog implements IRegistryBlock
{
	public static final PropertyEnum<BlockLog.EnumAxis> SECOND_AXIS = PropertyEnum.create("axis2", BlockLog.EnumAxis.class);
	private final String regName;

	public BlockRainbowLog(String name)
	{
		super();
		setCreativeTab(MinestuckTabs.minestuck);
		setDefaultState(blockState.getBaseState().withProperty(SECOND_AXIS, BlockLog.EnumAxis.NONE).withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
		setUnlocalizedName(name);
		regName = IRegistryObject.unlocToReg(name);
		MinestuckBlocks.blocks.add(this);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		IBlockState iblockstate = this.getDefaultState().withProperty(SECOND_AXIS, BlockLog.EnumAxis.values()[(meta - 1) & 3]);
		iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.values()[(meta >> 2) & 3]);

		return iblockstate;
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = (state.getValue(SECOND_AXIS).ordinal() + 1) & 3;

		i |= state.getValue(LOG_AXIS).ordinal() << 2;

		return i;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, SECOND_AXIS, LOG_AXIS);
	}

	@Override
	protected ItemStack getSilkTouchDrop(IBlockState state)
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, 0);
	}

	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		return MapColor.WOOD;
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return 0;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		EnumFacing playerFacing = EnumFacing.getDirectionFromEntityLiving(pos, placer);
		BlockLog.EnumAxis toSecond;
		switch (playerFacing)
		{
			case DOWN:
			case UP:
				toSecond = BlockLog.EnumAxis.Y;
				break;
			case EAST:
			case WEST:
				toSecond = BlockLog.EnumAxis.X;
				break;
			case NORTH:
			case SOUTH:
				toSecond = BlockLog.EnumAxis.Z;
				break;
			default:
				toSecond = BlockLog.EnumAxis.NONE;
				break;
		}
		worldIn.setBlockState(pos, state.withProperty(SECOND_AXIS, toSecond), 2);
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
	{
		items.add(new ItemStack(this, 1, 0));
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return 1;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return 1;
	}

	@Override
	public void register(IForgeRegistry<Block> registry)
	{
		setRegistryName(regName);
		registry.register(this);
	}
}