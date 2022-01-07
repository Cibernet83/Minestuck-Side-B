package com.mraof.minestuck.item.block;

import com.mraof.minestuck.block.BlockMiniCruxtruder;
import com.mraof.minestuck.block.MinestuckBlocks;
import com.mraof.minestuck.tileentity.TileEntityMiniSburbMachine;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemMiniSburbMachine extends MSItemBlock
{
	public ItemMiniSburbMachine(Block block)
	{
		super(block);
	}

	public static ItemStack getCruxtruderWithColor(int color)
	{
		ItemStack stack = new ItemStack(MinestuckBlocks.miniCruxtruder, 1);
		stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger("color", color);
		return stack;
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState)
	{
		if (super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState))
		{
			if (Block.getBlockFromItem(stack.getItem()) instanceof BlockMiniCruxtruder && stack.hasTagCompound() && stack.getTagCompound().hasKey("color"))
			{
				TileEntity te = world.getTileEntity(pos);
				if (te instanceof TileEntityMiniSburbMachine)
					((TileEntityMiniSburbMachine) te).color = stack.getTagCompound().getInteger("color");
			}
			return true;
		}
		return false;
	}
}