package com.mraof.minestuck.block;

import com.mraof.minestuck.item.MinestuckTabs;
import com.mraof.minestuck.item.block.MSItemBlock;
import com.mraof.minestuck.item.block.MSItemBlockSlab;
import com.mraof.minestuck.util.IRegistryItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistry;

public class MSBlockSlab extends BlockSlab implements IRegistryItem<Block>, IRegistryBlock
{
	private final IBlockState modelState;
	private final String regName;
	public final MSBlockSlab fullSlab;

	/**
	 * <code>getVariantProperty()</code> must return a non-null property found in this block's blockstates.
	 * As a result, this dummy property exists to be the return value of that method. It does nothing else.
	 */
	public final static PropertyInteger dummy = PropertyInteger.create("dummy", 0, 1);

	public MSBlockSlab(String name, IBlockState modelState)
	{
		super(modelState.getMaterial());
		setCreativeTab(MinestuckTabs.minestuck);
		this.modelState = modelState;
		this.useNeighborBrightness = true;
		setUnlocalizedName(name);
		regName = IRegistryItem.unlocToReg(name);
		MinestuckBlocks.blocks.add(this);

		//TODO: Use the modelState's hardness.
		setHardness(modelState.getMaterial()==Material.WOOD ? 1.0F : 3.0F);
		setHarvestLevel("pickaxe", modelState.getBlock().getHarvestLevel(modelState));

		if (!isDouble())
			fullSlab = new MSBlockSlab(name + "Full", modelState)
			{
				@Override
				public boolean isDouble()
				{
					return true;
				}

				@Override
				public MSItemBlock getItemBlock()
				{
					return null;
				}
			};
		else
			fullSlab = null;
	}

	protected BlockStateContainer createBlockState()
	{
		return this.isDouble() ? new BlockStateContainer(this, new IProperty[] {dummy}) : new BlockStateContainer(this, new IProperty[] {HALF, dummy});
	}
	
	public IBlockState getModelState()
	{
		return modelState;
	}
	
	@Override
	public String getUnlocalizedName(int meta)
	{
		return super.getUnlocalizedName();
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		if(isDouble())	return 0;
		return state.getValue(HALF).ordinal();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		if(isDouble())	return getDefaultState();
		return this.getDefaultState().withProperty(HALF, EnumBlockHalf.values()[meta % 2]); 
	}
	
	@Override
	public boolean isDouble()
	{
		return false;
	}
	
	@Override public IProperty<?> getVariantProperty() { return dummy; }
	@Override public Comparable<?> getTypeForItem(ItemStack stack) { return 0; }

	@Override
	public void register(IForgeRegistry<Block> registry)
	{
		setRegistryName(regName);
		registry.register(this);
	}

	@Override
	public MSItemBlock getItemBlock()
	{
		return new MSItemBlockSlab(this, fullSlab);
	}
}
