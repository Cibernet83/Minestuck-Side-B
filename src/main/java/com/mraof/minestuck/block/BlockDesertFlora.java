package com.mraof.minestuck.block;

import com.mraof.minestuck.item.MinestuckTabs;

import com.mraof.minestuck.item.block.MSItemBlock;
import com.mraof.minestuck.util.IRegistryItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistry;

public class BlockDesertFlora extends BlockBush implements IRegistryBlock
{
	private final String regName;

	public BlockDesertFlora(String name) 
	{
		super(Material.PLANTS);
		regName = IRegistryItem.unlocToReg(name);
		MinestuckBlocks.blocks.add(this);
		setCreativeTab(MinestuckTabs.minestuck);
		setUnlocalizedName(name);
		setSoundType(SoundType.PLANT);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState soil = worldIn.getBlockState(pos.down());
		return canSustainBush(soil);
	}
	
	public boolean canSustainBush(IBlockState state) {
		return state.getBlock() == Blocks.SAND;
	}

	@Override
	public void register(IForgeRegistry<Block> registry)
	{
		setRegistryName(regName);
		registry.register(this);
	}

	@Override
	public MSItemBlock getItemBlock()
	{
		return new MSItemBlock(this);
	}
}
