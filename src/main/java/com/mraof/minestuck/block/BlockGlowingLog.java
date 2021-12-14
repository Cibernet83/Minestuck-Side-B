package com.mraof.minestuck.block;

import com.mraof.minestuck.item.TabMinestuck;
import com.mraof.minestuck.util.IRegistryItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.registries.IForgeRegistry;

public class BlockGlowingLog extends BlockLog implements IRegistryItem<Block>
{
	private final String regName;

	public BlockGlowingLog()
	{
		super();
		setCreativeTab(TabMinestuck.instance);
		setLightLevel(0.75F);
		setDefaultState(blockState.getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
		setUnlocalizedName("glowingLog");
		regName = IRegistryItem.unlocToReg("glowingLog");
		MSBlockBase.blocks.add(this);
	}

	@Override
	public void register(IForgeRegistry<Block> registry)
	{
		setRegistryName(regName);
		registry.register(this);
	}
	
	@Override
	public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return false;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		IBlockState state = getDefaultState();
		EnumAxis axis = EnumAxis.values()[meta&3];
		state = state.withProperty(LOG_AXIS, axis);
		return state;
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		EnumAxis axis = (EnumAxis) state.getValue(LOG_AXIS);
		int meta = axis.ordinal();
		return meta;
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {LOG_AXIS});
	}
	
	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return 5;
	}
	
	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return 5;
	}
}