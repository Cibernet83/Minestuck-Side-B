package com.cibernet.fetchmodiplus.items;

import com.cibernet.fetchmodiplus.captchalogue.OperandiModus;
import com.cibernet.fetchmodiplus.registries.FMPItems;
import com.cibernet.fetchmodiplus.registries.FMPSounds;
import com.mraof.minestuck.item.TabMinestuck;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OperandiHoeItem extends ItemHoe
{
	public OperandiHoeItem(String name)
	{
		super(ToolMaterial.IRON);
		setMaxDamage(10);
		
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(TabMinestuck.instance);
		
		FMPItems.items.add(this);
		OperandiModus.itemPool.add(this);
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		stack.damageItem(getMaxDamage(stack)+1, attacker);
		return true;
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
	{
		stack.damageItem(getMaxDamage(stack)+1, entityLiving);
		return true;
	}
	
	
	protected void setBlock(ItemStack stack, EntityPlayer player, World worldIn, BlockPos pos, IBlockState state)
	{
		worldIn.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
		ItemStack storedStack = BaseItem.getStoredItem(stack);
		
		if (!worldIn.isRemote)
		{
			worldIn.setBlockState(pos, state, 11);
			stack.damageItem(1, player);
		}
		if(stack.isEmpty())
		{
			worldIn.playSound(null, player.getPosition(), FMPSounds.operandiTaskComplete, SoundCategory.PLAYERS, 1, 1);
			
			if(!player.addItemStackToInventory(storedStack))
				player.dropItem(storedStack, true);
		}
	}
}
