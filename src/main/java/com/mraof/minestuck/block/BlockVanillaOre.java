package com.mraof.minestuck.block;

import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.item.MinestuckTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;


public class BlockVanillaOre extends MSBlockBase
{
	public final Block oreType;
	
	public BlockVanillaOre(String name, Block type)	//For vanilla ores with a different background texture
	{
		super(name, Material.ROCK);
		oreType = type;
		setHardness(3.0F);
		setResistance(5.0F);	//Values normally used by ores
		setHarvestLevel("pickaxe", type.getHarvestLevel(type.getDefaultState()));
		setCreativeTab(MinestuckTabs.minestuck);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		if (!MinestuckConfig.vanillaOreDrop && (oreType == Blocks.IRON_ORE || oreType == Blocks.GOLD_ORE))
			return Item.getItemFromBlock(this);
		else
			return oreType.getItemDropped(state, rand, fortune);
	}
	
	@Override
	public int quantityDropped(Random random)
	{
		return oreType.quantityDropped(random);
	}
	
	@Override
	public int quantityDroppedWithBonus(int fortune, Random random)
	{
		return oreType.quantityDroppedWithBonus(fortune, random);
	}
	
	@Override
	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune)
	{
		return oreType.getExpDrop(state, world, pos, fortune);
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return oreType.getPickBlock(state, target, world, pos, player);
	}
	
	@Override
	public int damageDropped(IBlockState state)
	{
		return oreType.damageDropped(state);
	}
	
	@Override
	protected ItemStack getSilkTouchDrop(IBlockState state)
	{
		if(MinestuckConfig.vanillaOreDrop)
			return new ItemStack(oreType);
		else
			return super.getSilkTouchDrop(state);
	}
	
}