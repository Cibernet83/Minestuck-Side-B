package com.mraof.minestuck.block;

import com.mraof.minestuck.item.block.MSItemBlock;
import com.mraof.minestuck.item.block.MSItemBlockMultiTexture;
import com.mraof.minestuck.util.IRegistryObject;
import com.mraof.minestuck.util.IUnlocSerializable;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MSBlockLeavesVariant extends MSBlockLeaves
{
	public static final PropertyEnum<BlockType> VARIANT = PropertyEnum.create("variant", BlockType.class);

	public MSBlockLeavesVariant()
	{
		super("leaves");
		setDefaultState(blockState.getBaseState().withProperty(VARIANT, BlockType.VINE_OAK).withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(VARIANT, BlockType.values()[meta]);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = state.getValue(VARIANT).ordinal();
		return i;
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		if (state.getValue(VARIANT) == BlockType.RAINBOW)
			return 0;
		else
			return BlockType.values()[getMetaFromState(state)].ordinal();
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
	{
		for (BlockType blocktype : BlockType.values())
			items.add(new ItemStack(this, 1, blocktype.ordinal()));
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(this, 1, state.getValue(VARIANT).ordinal());
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
	{
		ArrayList out = new ArrayList<ItemStack>();
		out.add(new ItemStack(this));
		return out;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		return true;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return Blocks.LEAVES.isOpaqueCube(state);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return ItemStack.EMPTY.getItem();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return Blocks.LEAVES.getBlockLayer();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, DECAYABLE, CHECK_DECAY, VARIANT);
	}

	@Override
	protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance)
	{
		if (state.getValue(VARIANT) == BlockType.RAINBOW && worldIn.rand.nextInt(chance) == 0)
		{
			int i = worldIn.rand.nextInt(16);
			spawnAsEntity(worldIn, pos, new ItemStack(Items.DYE, 1, i));
		}
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
				ModelLoader.setCustomStateMapper(MSBlockLeavesVariant.this, (new StateMap.Builder()).withName(VARIANT).withSuffix("_leaves").build());
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