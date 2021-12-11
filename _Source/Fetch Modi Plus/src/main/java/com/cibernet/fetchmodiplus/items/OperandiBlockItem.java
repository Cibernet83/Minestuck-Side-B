package com.cibernet.fetchmodiplus.items;

import com.cibernet.fetchmodiplus.captchalogue.OperandiModus;
import com.cibernet.fetchmodiplus.registries.FMPItems;
import com.mraof.minestuck.item.TabMinestuck;
import com.mraof.minestuck.tileentity.TileEntityItemStack;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OperandiBlockItem extends ItemBlock
{
	public OperandiBlockItem(String name, Block block)
	{
		super(block);
		
		setUnlocalizedName(name);
		setRegistryName(name);
		
		setCreativeTab(TabMinestuck.instance);
		setMaxStackSize(1);
		
		FMPItems.items.add(this);
		OperandiModus.itemPool.add(this);
	}
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState)
	{
		if (super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState))
		{
			TileEntityItemStack te = (TileEntityItemStack)world.getTileEntity(pos);
			ItemStack newStack = BaseItem.getStoredItem(stack);
			te.setStack(newStack);
			return true;
		} else return false;
	}
}
