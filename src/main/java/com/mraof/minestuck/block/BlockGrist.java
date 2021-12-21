package com.mraof.minestuck.block;

import com.mraof.minestuck.alchemy.GristAmount;
import com.mraof.minestuck.alchemy.Grist;
import com.mraof.minestuck.alchemy.MinestuckGrists;
import com.mraof.minestuck.entity.item.EntityGrist;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;

public class BlockGrist extends MSBlockBase
{
	public Grist type;
	public int value;

	public BlockGrist(Grist type)
	{
		super("gristBlock" + type.getName().toLowerCase().replaceFirst(type.getName().charAt(0)+"", (type.getName().charAt(0)+"").toUpperCase()), Material.GOURD, MapColor.LIGHT_BLUE_STAINED_HARDENED_CLAY);
		this.type = type;
		this.value = (type.getValue() >= 5.0F || !type.equals(MinestuckGrists.build)) ? 10 : 100;
		
		MinestuckBlocks.gristBlocks.put(type, this);

		setHardness(0.4f);
		setHarvestLevel("pickaxe", 0);
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
	{
		tooltip.add(I18n.translateToLocal("tile.gristBlock.tooltip"));
		super.addInformation(stack, player, tooltip, advanced);
	}
	
	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn)
	{
		if(!worldIn.isRemote)
			worldIn.spawnEntity(new EntityGrist(worldIn, pos.getX(),pos.getY() + 0.5F,pos.getZ(), new GristAmount(this.type,this.value)));
		super.onBlockDestroyedByExplosion(worldIn, pos, explosionIn);
	}
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		if(!worldIn.isRemote && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, player.getHeldItemMainhand()) <= 0 && !player.isCreative())
			worldIn.spawnEntity(new EntityGrist(worldIn, pos.getX(),pos.getY() + 0.5F,pos.getZ(), new GristAmount(this.type,this.value)));
		
		super.onBlockHarvested(worldIn, pos, state, player);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Items.AIR;
	}
	
	
}
