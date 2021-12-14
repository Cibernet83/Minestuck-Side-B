package com.mraof.minestuck.block;

import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.item.MinestuckTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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
		if (oreType == Blocks.COAL_ORE) return Items.COAL;
		if (oreType == Blocks.IRON_ORE) return Item.getItemFromBlock(MinestuckConfig.vanillaOreDrop ? Blocks.IRON_ORE : this);
		if (oreType == Blocks.GOLD_ORE) return Item.getItemFromBlock(MinestuckConfig.vanillaOreDrop ? Blocks.GOLD_ORE : this);
		if (oreType == Blocks.LAPIS_ORE) return Items.DYE;
		if (oreType == Blocks.DIAMOND_ORE) return Items.DIAMOND;
		if (oreType == Blocks.EMERALD_ORE) return Items.EMERALD;
		if (oreType == Blocks.QUARTZ_ORE) return Items.QUARTZ;
		if (oreType == Blocks.REDSTONE_ORE) return Items.REDSTONE;
		return Item.getItemFromBlock(this);
	}
	
	@Override
	public int quantityDropped(Random random)
	{
		return oreType == Blocks.LAPIS_ORE ? 4 + random.nextInt(5) : oreType == Blocks.REDSTONE_ORE ? 4 + random.nextInt(2) : 1;
	}
	
	@Override
	public int quantityDroppedWithBonus(int fortune, Random random)
	{
		if(oreType == Blocks.REDSTONE_ORE)
		{
			return this.quantityDropped(random) + random.nextInt(fortune + 1);
		}
		else if(fortune > 0 && oreType != Blocks.IRON_ORE && oreType != Blocks.GOLD_ORE)
		{
			int j = random.nextInt(fortune + 2) - 1;
			
			if(j < 0)
				j = 0;
			
			return this.quantityDropped(random) * (j + 1);
		}
		else return this.quantityDropped(random);
	}
	
	@Override
	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune)
	{
		Random rand = world instanceof World ? ((World)world).rand : new Random();
		if(oreType != Blocks.IRON_ORE && oreType != Blocks.GOLD_ORE)
		{
			int j = 0;
			
			if(oreType == Blocks.COAL_ORE)
				j = MathHelper.getInt(rand, 0, 2);
			else if(oreType == Blocks.DIAMOND_ORE)
				j = MathHelper.getInt(rand, 3, 7);
			else if(oreType == Blocks.EMERALD_ORE)
				j = MathHelper.getInt(rand, 3, 7);
			else if(oreType == Blocks.LAPIS_ORE)
				j = MathHelper.getInt(rand, 2, 5);
			else if(oreType == Blocks.QUARTZ_ORE)
				j = MathHelper.getInt(rand, 2, 5);
			else if(oreType == Blocks.REDSTONE_ORE)
				j = MathHelper.getInt(rand, 1, 6);
			
			return j;
		}
		return 0;
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(this);
	}
	
	@Override
	public int damageDropped(IBlockState state)
	{
		return oreType == Blocks.LAPIS_ORE ? EnumDyeColor.BLUE.getDyeDamage() : 0;
	}
	
	@Override
	protected ItemStack getSilkTouchDrop(IBlockState state)
	{
		if(!MinestuckConfig.vanillaOreDrop)
			return super.getSilkTouchDrop(state);

		if (oreType == Blocks.COAL_ORE) return new ItemStack(Blocks.COAL_ORE);
		if (oreType == Blocks.IRON_ORE) return new ItemStack(Blocks.IRON_ORE);
		if (oreType == Blocks.GOLD_ORE) return new ItemStack(Blocks.GOLD_ORE);
		if (oreType == Blocks.LAPIS_ORE) return new ItemStack(Blocks.LAPIS_ORE);
		if (oreType == Blocks.DIAMOND_ORE) return new ItemStack(Blocks.DIAMOND_ORE);
		if (oreType == Blocks.EMERALD_ORE) return new ItemStack(Blocks.EMERALD_ORE);
		if (oreType == Blocks.QUARTZ_ORE) return new ItemStack(Blocks.QUARTZ_ORE);
		if (oreType == Blocks.REDSTONE_ORE) return new ItemStack(Blocks.REDSTONE_ORE);
		return new ItemStack(this);
	}
	
}