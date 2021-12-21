package com.mraof.minestuck.block;

import com.mraof.minestuck.item.block.MSItemBlock;
import com.mraof.minestuck.item.block.MSItemBlockMultiTexture;
import com.mraof.minestuck.util.IUnlocSerializable;
import com.mraof.minestuck.util.MinestuckRandom;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;

public class MSBlockCustomOre extends MSBlockBase
{
	public static final PropertyEnum<BlockType> VARIANT = PropertyEnum.create("variant", BlockType.class);

	public MSBlockCustomOre(String name)
	{
		super(name, Material.ROCK);
		setHardness(3.0F);
		setResistance(5.0F);
		setHarvestLevel("pickaxe", 0);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(VARIANT, BlockType.values()[meta % BlockType.values().length]);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((BlockType)state.getValue(VARIANT)).ordinal();
	}

	@Override
	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune)
	{
		return MathHelper.getInt(MinestuckRandom.getRandom(), 2, 5);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, VARIANT);
	}

	public static IBlockState getBlockState(IBlockState ground)
	{
		String type = ground.getBlock().getRegistryName().getResourcePath();
		int meta = 0;
		for (BlockType blockType : BlockType.values())
			if (type.equals(blockType.getName()))
				meta = blockType.ordinal();
		return MinestuckBlocks.oreCruxite.getBlockState().getBaseState().withProperty(VARIANT, BlockType.values()[meta]);
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		for(BlockType blockType : BlockType.values())
			items.add(new ItemStack(this, 1, blockType.ordinal()));
	}

	public enum BlockType implements IUnlocSerializable
	{
		STONE(Blocks.STONE),
		NETHERRACK(Blocks.NETHERRACK),
		COBBLESTONE(Blocks.COBBLESTONE),
		SANDSTONE(Blocks.SANDSTONE),
		RED_SANDSTONE(Blocks.RED_SANDSTONE),
		END_STONE(Blocks.END_STONE),
		PINK_STONE(MinestuckBlocks.pinkStoneSmooth);
		private String name, regName;

		BlockType(Block ground)
		{
			name = ground.getUnlocalizedName().substring(5);
			regName = toString().toLowerCase();
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