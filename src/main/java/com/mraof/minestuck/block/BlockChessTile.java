package com.mraof.minestuck.block;

import com.mraof.minestuck.item.block.MSItemBlock;
import com.mraof.minestuck.item.block.MSItemBlockMultiTexture;
import com.mraof.minestuck.util.IRegistryObject;
import com.mraof.minestuck.util.IUnlocSerializable;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import static com.mraof.minestuck.block.MinestuckBlocks.chessTile;

public class BlockChessTile extends MSBlockBase
{
	public static final PropertyEnum VARIANT = PropertyEnum.create("variant", BlockType.class);

	public BlockChessTile()
	{
		super("chessTile", Material.GROUND);
		setHardness(0.5F);

		setDefaultState(getDefaultState().withProperty(VARIANT, BlockType.BLACK));
	}

	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		switch ((BlockType) state.getValue(VARIANT))
		{
			case WHITE:
				return MapColor.SNOW;
			case LIGHT_GREY:
				return MapColor.SILVER;
			case DARK_GREY:
				return MapColor.GRAY;
			case BLACK:
				return MapColor.BLACK;
			default:
				return super.getMapColor(state, worldIn, pos);
		}
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
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, SpawnPlacementType type)
	{
		return true;
	}

	@Override
	public MSItemBlock getItemBlock()
	{
		return new MSItemBlockMultiTexture(chessTile, BlockType.values());
	}

	public enum BlockType implements IUnlocSerializable
	{
		BLACK("black"),
		WHITE("white"),
		DARK_GREY("darkGrey"),
		LIGHT_GREY("lightGrey");
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