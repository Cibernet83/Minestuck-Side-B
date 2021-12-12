package com.mraof.minestuck.item;

import com.mraof.minestuck.inventory.captchalouge.OperandiModus;
import com.mraof.minestuck.item.TabMinestuck;
import com.mraof.minestuck.tileentity.TileEntityItemStack;
import com.mraof.minestuck.util.ModusStorage;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemOperandiBlock extends ItemBlock
{
	public ItemOperandiBlock(String name, Block block)
	{
		super(block);
		
		setUnlocalizedName(name);
		
		setCreativeTab(TabMinestuck.instance);
		setMaxStackSize(1);

		OperandiModus.itemPool.add(this);
	}
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState)
	{
		if (super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState))
		{
			TileEntityItemStack te = (TileEntityItemStack)world.getTileEntity(pos);
			ItemStack newStack = ModusStorage.getStoredItem(stack);
			te.setStack(newStack);
			return true;
		} else return false;
	}
}
