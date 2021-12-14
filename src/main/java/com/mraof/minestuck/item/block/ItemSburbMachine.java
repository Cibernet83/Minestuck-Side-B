package com.mraof.minestuck.item.block;

import com.mraof.minestuck.block.BlockSburbMachine;
import com.mraof.minestuck.block.MinestuckBlocks;
import com.mraof.minestuck.tileentity.TileEntitySburbMachine;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemSburbMachine extends MSItemBlockMultiTexture
{
	public ItemSburbMachine(Block block)
	{
		super(block, (ItemStack input) -> ((BlockSburbMachine)Block.getBlockFromItem(input.getItem())).getType().getUnlocalizedName());
	}
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState)
	{
		if(super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState))
		{
			if(((BlockSburbMachine)Block.getBlockFromItem(stack.getItem())).getType() == BlockSburbMachine.MachineType.CRUXTRUDER && stack.hasTagCompound() && stack.getTagCompound().hasKey("color"))
			{
				TileEntity te = world.getTileEntity(pos);
				if(te instanceof TileEntitySburbMachine)
					((TileEntitySburbMachine)te).color = stack.getTagCompound().getInteger("color");
			}
			return true;
		}
		return false;
	}
	
	public static ItemStack getCruxtruderWithColor(int color)
	{
		ItemStack stack = new ItemStack(MinestuckBlocks.miniCruxtruder, 1);
		stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger("color", color);
		return stack;
	}
}