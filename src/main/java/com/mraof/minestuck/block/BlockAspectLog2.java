package com.mraof.minestuck.block;

import com.mraof.minestuck.item.MinestuckTabs;
import com.mraof.minestuck.item.block.MSItemBlock;
import com.mraof.minestuck.item.block.MSItemBlockMultiTexture;
import com.mraof.minestuck.util.IRegistryItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.registries.IForgeRegistry;

public class BlockAspectLog2 extends BlockLog implements IRegistryItem<Block>, IRegistryBlock
{
	public static final PropertyEnum<BlockType> VARIANT = PropertyEnum.create("variant", BlockType.class);
	private final String regName;

	public BlockAspectLog2()
	{
		super();
		regName = "aspect_log_2";
		MinestuckBlocks.blocks.add(this);
		setCreativeTab(MinestuckTabs.minestuck);
		setDefaultState(blockState.getBaseState().withProperty(VARIANT, BlockType.ASPECT_HOPE).withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
		setUnlocalizedName("aspectLog2");
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {VARIANT, LOG_AXIS});
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, BlockType.values()[meta&3]);
		iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.values()[(meta>>2)&3]);
		
		return iblockstate;
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = state.getValue(VARIANT).ordinal();
		
		i |= state.getValue(LOG_AXIS).ordinal()<<2;
		
		return i;
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
	
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		BlockType type = state.getValue(VARIANT);
		if(state.getValue(LOG_AXIS).equals(EnumAxis.Y))
			return type.topColor;
		else return type.sideColor;
	}
	
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
	{
		for(BlockType type : BlockType.values())
			items.add(new ItemStack(this, 1, type.ordinal()));
	}
	
	@Override
	protected ItemStack getSilkTouchDrop(IBlockState state)
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, state.getValue(VARIANT).ordinal());
	}
	
	@Override
	public int damageDropped(IBlockState state)
	{
		return state.getValue(VARIANT).ordinal();
	}
	
	public enum BlockType implements IStringSerializable
	{
		ASPECT_HOPE("aspect_hope", "aspectHope", MapColor.WOOD, MapColor.WOOD),
		ASPECT_LIFE("aspect_life", "aspectLife", MapColor.WOOD, MapColor.WOOD),
		ASPECT_LIGHT("aspect_light", "aspectLight", MapColor.WOOD, MapColor.WOOD),
		ASPECT_MIND("aspect_mind", "aspectMind", MapColor.WOOD, MapColor.WOOD);
		
		private final String name;
		private final String unlocalizedName;
		private final MapColor topColor, sideColor;
		
		BlockType(String name, String unlocalizedName, MapColor topColor, MapColor sideColor)
		{
			this.name = name;
			this.unlocalizedName = unlocalizedName;
			this.topColor = topColor;
			this.sideColor = sideColor;
		}
		
		@Override
		public String getName()
		{
			return name;
		}
		
		public String getUnlocalizedName()
		{
			return unlocalizedName;
		}
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
		return new MSItemBlockMultiTexture(this, (ItemStack input) -> BlockType.values()[input.getItemDamage() % BlockType.values().length].getUnlocalizedName());
	}
}