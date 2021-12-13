	package com.mraof.minestuck.block;

import com.mraof.minestuck.item.TabMinestuck;

import com.mraof.minestuck.util.IRegistryItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.registries.IForgeRegistry;

	public class BlockPetrifiedFlora extends BlockBush implements IRegistryItem<Block>
{
	private final String regName;

	public BlockPetrifiedFlora(String name)
	{
		super(Material.ROCK);
		setCreativeTab(TabMinestuck.instance);
		setSoundType(SoundType.STONE);
		setUnlocalizedName(name);
		regName = IRegistryItem.unlocToReg(name);
		MSBlockBase.blocks.add(this);
	}

	@Override
	public void register(IForgeRegistry<Block> registry)
	{
		setRegistryName(regName);
		registry.register(this);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState soil = worldIn.getBlockState(pos.down());
		return canSustainBush(soil);
	}
	
	@Override
    protected boolean canSustainBush(IBlockState state)
    {
        return state.getBlock() == Blocks.STONE || state.getBlock() == Blocks.GRAVEL || state.getBlock() == Blocks.COBBLESTONE;
    }

	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		return MapColor.GRAY;
	}
}
