package com.mraof.minestuck.block;

import com.mraof.minestuck.item.MinestuckTabs;

import com.mraof.minestuck.item.block.MSItemBlock;
import com.mraof.minestuck.util.IRegistryObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.registries.IForgeRegistry;

public class BlockPetrifiedLog extends BlockLog implements IRegistryBlock
{
	private final String regName;

	public BlockPetrifiedLog(String name)
	{
		setCreativeTab(MinestuckTabs.minestuck);
		setSoundType(SoundType.STONE);
		setHarvestLevel("pickaxe", 0);
		setUnlocalizedName(name);
		regName = IRegistryObject.unlocToReg(name);
		MinestuckBlocks.blocks.add(this);
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
	public Material getMaterial(IBlockState state)
	{
		return Material.ROCK;
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {LOG_AXIS});
	}
	
	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return 0;
	}

	@Override
	public void register(IForgeRegistry<Block> registry)
	{
		setRegistryName(regName);
		registry.register(this);
	}
}
