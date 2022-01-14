package com.mraof.minestuck.block;

import com.mraof.minestuck.item.block.MSItemBlock;
import com.mraof.minestuck.item.block.MSItemBlockMultiTexture;
import com.mraof.minestuck.util.IRegistryObject;
import com.mraof.minestuck.util.IUnlocSerializable;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MSBlockLogVariant extends MSBlockLog implements IRegistryBlock
{
	public static final PropertyEnum<BlockType> VARIANT = PropertyEnum.create("variant", BlockType.class);

	public MSBlockLogVariant(String name)
	{
		super(name);
		setDefaultState(blockState.getBaseState().withProperty(VARIANT, BlockType.VINE_OAK).withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, BlockType.values()[meta & 3]);
		iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.values()[(meta >> 2) & 3]);

		return iblockstate;
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = state.getValue(VARIANT).ordinal();

		i |= state.getValue(LOG_AXIS).ordinal() << 2;

		return i;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, VARIANT, LOG_AXIS);
	}

	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		BlockType type = state.getValue(VARIANT);
		if (state.getValue(LOG_AXIS).equals(EnumAxis.Y))
			return type.topColor;
		else return type.sideColor;
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return state.getValue(VARIANT).ordinal();
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
	{
		for (BlockType type : BlockType.values())
			items.add(new ItemStack(this, 1, type.ordinal()));
	}

	@Override
	protected ItemStack getSilkTouchDrop(IBlockState state)
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, state.getValue(VARIANT).ordinal());
	}

	@Override
	public MSItemBlock getItemBlock()
	{
		return new MSItemBlockMultiTexture(this, BlockType.values())
		{
			@Override
			@SideOnly(Side.CLIENT)
			public void registerModel()
			{
				super.registerModel();
				ModelLoader.setCustomStateMapper(MSBlockLogVariant.this, (new StateMap.Builder()).withName(VARIANT).withSuffix("_log").build());
			}
		};
	}

	public enum BlockType implements IUnlocSerializable
	{
		VINE_OAK("vineOak", MapColor.WOOD, MapColor.OBSIDIAN),
		FLOWERY_VINE_OAK("floweryVineOak", MapColor.WOOD, MapColor.OBSIDIAN),
		FROST("frost", MapColor.ICE, MapColor.ICE),
		RAINBOW("rainbow", MapColor.WOOD, MapColor.WOOD);

		private final String name, regName;
		private final MapColor topColor, sideColor;

		BlockType(String name, MapColor topColor, MapColor sideColor)
		{
			this.name = name;
			this.regName = IRegistryObject.unlocToReg(name);
			this.topColor = topColor;
			this.sideColor = sideColor;
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
