package com.mraof.minestuck.block;

import com.mraof.minestuck.item.block.MSItemBlock;
import com.mraof.minestuck.item.block.MSItemBlockMultiTexture;
import com.mraof.minestuck.util.IRegistryObject;
import com.mraof.minestuck.util.IUnlocSerializable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class BlockDenseCloud extends MSBlockBase
{
	public static final PropertyEnum VARIANT = PropertyEnum.create("block_type", BlockType.class);

	public BlockDenseCloud()
	{
		super("denseCloud", Material.GLASS, MapColor.YELLOW);
		setHardness(0.5F);
		setSoundType(SoundType.SNOW);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(VARIANT, BlockType.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((BlockType) state.getValue(VARIANT)).ordinal();
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return getMetaFromState(state);
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
	{
		for (int i = 0; i < BlockType.values().length; i++)
			items.add(new ItemStack(this, 1, i));
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, VARIANT);
	}

	@Override
	public MSItemBlock getItemBlock()
	{
		return new MSItemBlockMultiTexture(this, BlockType.values())
		{
			@Override
			public int getMetadata(int damage)
			{
				return damage;
			}
		};
	}

	public enum BlockType implements IUnlocSerializable
	{
		NORMAL("normal"),
		BRIGHT("bright");
		public final String name, regName;

		BlockType(String name)
		{
			this.name = name;
			this.regName = IRegistryObject.unlocToReg(name);
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
}