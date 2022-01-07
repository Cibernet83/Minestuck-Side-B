package com.mraof.minestuck.block;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.item.MinestuckTabs;
import com.mraof.minestuck.item.block.MSItemBlock;
import com.mraof.minestuck.item.block.MSItemBlockMultiTexture;
import com.mraof.minestuck.util.IRegistryObject;
import com.mraof.minestuck.util.IUnlocSerializable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class BlockAspectLog2 extends BlockLog implements IRegistryObject<Block>, IRegistryBlock
{
	public static final PropertyEnum<BlockType> VARIANT = PropertyEnum.create("variant", BlockType.class);

	public BlockAspectLog2()
	{
		super();
		MinestuckBlocks.blocks.add(this);
		setCreativeTab(MinestuckTabs.minestuck);
		setDefaultState(blockState.getBaseState().withProperty(VARIANT, BlockType.HOPE).withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
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
	
	public enum BlockType implements IUnlocSerializable
	{
		HOPE, LIFE, LIGHT, MIND;
		@Override
		public String getName()
		{
			return name().toLowerCase();
		}
		@Override
		public String getUnlocalizedName()
		{
			return name().toLowerCase();
		}
	}

	@Override
	public void register(IForgeRegistry<Block> registry)
	{
		setRegistryName("aspect_log_2");
		registry.register(this);
	}

	@Override
	public MSItemBlock getItemBlock()
	{
		return new MSItemBlockMultiTexture(this, BlockAspectLog1.BlockType.values())
		{
			@Override
			@SideOnly(Side.CLIENT)
			public void registerModel()
			{
				for (int i = 0; i < states.length; i++)
					ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation(new ResourceLocation(Minestuck.MODID, "log_aspect_" + states[i].getName()), "inventory"));
				ModelLoader.setCustomStateMapper(BlockAspectLog2.this, (new StateMap.Builder()).withName(BlockAspectLog2.VARIANT).withSuffix("_log").build());
			}
		};
	}
}