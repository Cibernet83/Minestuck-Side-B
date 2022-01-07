package com.mraof.minestuck.block;

import com.mraof.minestuck.item.MinestuckTabs;
import com.mraof.minestuck.util.IRegistryObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Random;

public class BlockGlowingMushroom extends BlockBush implements IRegistryBlock
{
	private final String regName;

	public BlockGlowingMushroom()
	{
		super();
		setCreativeTab(MinestuckTabs.minestuck);
		setLightLevel(0.75F);
		setSoundType(SoundType.PLANT);
		setUnlocalizedName("glowingMushroom");
		regName = IRegistryObject.unlocToReg("glowingMushroom");
		MinestuckBlocks.blocks.add(this);
	}

	@Override
	public void register(IForgeRegistry<Block> registry)
	{
		setRegistryName(regName);
		registry.register(this);
	}

	@Override
	protected boolean canSustainBush(IBlockState state)
	{
		return state.isFullBlock();
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		super.updateTick(worldIn, pos, state, rand);
		if (canSpread(worldIn, pos, state) && rand.nextInt(25) == 0)
		{
			int count = 0;
			Iterable blocks = BlockPos.getAllInBoxMutable(pos.add(-4, -1, -4), pos.add(4, 1, 4));

			for (BlockPos checkPos : (Iterable<BlockPos>) blocks)
				if (worldIn.getBlockState(checkPos).getBlock() == this)
				{
					count++;
					if (count >= 5)
						return;
				}

			for (int i = 0; i < 5; ++i)
			{
				BlockPos spreadPos = pos.add(rand.nextInt(3) - 1, rand.nextInt(2) - rand.nextInt(2), rand.nextInt(3) - 1);
				if (worldIn.isAirBlock(spreadPos) && this.canSpread(worldIn, spreadPos, this.getDefaultState()))
				{
					worldIn.setBlockState(spreadPos, this.getDefaultState(), 2);
					return;
				}
			}
		}
	}

	public boolean canSpread(World world, BlockPos pos, IBlockState state)
	{
		IBlockState soil = world.getBlockState(pos.down());
		return soil.getBlock().equals(MinestuckBlocks.coloredDirt) && soil.getValue(BlockColoredDirt.VARIANT).equals(BlockColoredDirt.BlockType.BLUE);
	}

	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		return MapColor.DIAMOND;
	}
}