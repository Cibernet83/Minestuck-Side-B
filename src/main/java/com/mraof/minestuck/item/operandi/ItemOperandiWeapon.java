package com.mraof.minestuck.item.operandi;

import com.mraof.minestuck.util.MinestuckSounds;
import com.mraof.minestuck.util.ModusStorage;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;

public class ItemOperandiWeapon extends ItemOperandiTool
{
	public ItemOperandiWeapon(String name, String toolClass, float attackDamageIn, float attackSpeedIn, float efficiency, int maxUses)
	{
		super(name, toolClass, attackDamageIn, attackSpeedIn, efficiency, maxUses);
	}
	
	public float getDestroySpeed(ItemStack stack, IBlockState state)
	{
		if(!toolClass.isEmpty())
			return super.getDestroySpeed(stack, state);
		
		Block block = state.getBlock();
		
		if (block == Blocks.WEB)
		{
			return 15.0F;
		}
		else
		{
			Material material = state.getMaterial();
			return material != Material.PLANTS && material != Material.VINE && material != Material.CORAL && material != Material.LEAVES && material != Material.GOURD ? 1.0F : 1.5F;
		}
	}
	
	
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		ItemStack storedStack = ModusStorage.getStoredItem(stack);
		stack.damageItem(1, attacker);
		
		if(stack.isEmpty())
		{
			attacker.world.playSound(null, attacker.getPosition(), MinestuckSounds.operandiTaskComplete, SoundCategory.PLAYERS, 1, 1);
			
			if((attacker instanceof EntityPlayer) && !((EntityPlayer)attacker).addItemStackToInventory(storedStack))
				((EntityPlayer) attacker).dropItem(storedStack, true);
		}
		
		return true;
	}
	
	@Override
	public boolean canHarvestBlock(IBlockState blockIn)
	{
		return toolClass.isEmpty() ? blockIn.getBlock() == Blocks.WEB : super.canHarvestBlock(blockIn);
	}
	
	
}