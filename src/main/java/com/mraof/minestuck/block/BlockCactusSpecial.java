package com.mraof.minestuck.block;

import com.mraof.minestuck.item.MinestuckTabs;
import com.mraof.minestuck.item.block.MSItemBlock;
import com.mraof.minestuck.util.IRegistryObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Random;

public class BlockCactusSpecial extends BlockCactus implements IRegistryBlock
{
	private final String regName;
	private String toolType;
	
	public BlockCactusSpecial(String name, SoundType soundType, String effectiveTool)
	{
		super();
		setUnlocalizedName(name);
		regName = IRegistryObject.unlocToReg(name);
		MinestuckBlocks.blocks.add(this);
		this.setCreativeTab(MinestuckTabs.minestuck);
		setSoundType(soundType);
		this.toolType = effectiveTool;
	}

	@Override
	public boolean isToolEffective(String type, IBlockState state)
	{
		if(type.equals(toolType))
			return true;
		else
			return super.isToolEffective(type, state);
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		super.updateTick(worldIn, pos, state, rand);
	}
	
	@Override
	public boolean canBlockStay(World worldIn, BlockPos pos)
	{
		for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
		{
			Material material = worldIn.getBlockState(pos.offset(enumfacing)).getMaterial();
			
			if(material.isSolid() || material == Material.LAVA)
			{
				return false;
			}
		}
		
		IBlockState state = worldIn.getBlockState(pos.down());
		return state.getBlock() == this || state.getBlock().canSustainPlant(state, worldIn, pos.down(), EnumFacing.UP, this)
				&& !worldIn.getBlockState(pos.up()).getMaterial().isLiquid();
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
		return new MSItemBlock(this)
		{
			@Override
			@SideOnly(Side.CLIENT)
			public void registerModel()
			{
				super.registerModel();
				ModelLoader.setCustomStateMapper(BlockCactusSpecial.this, new StateMap.Builder().ignore(BlockCactus.AGE).build());
			}
		};
	}
}