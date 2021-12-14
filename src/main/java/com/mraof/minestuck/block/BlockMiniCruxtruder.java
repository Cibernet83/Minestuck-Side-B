package com.mraof.minestuck.block;

import com.mraof.minestuck.tileentity.TileEntityMiniCruxtruder;
import com.mraof.minestuck.tileentity.TileEntitySburbMachine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMiniCruxtruder extends BlockSburbMachine
{
	private static final AxisAlignedBB CRUXTRUDER_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 15/16D, 1.0D);

	public BlockMiniCruxtruder()
	{
		super("miniCruxtruder");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileEntityMiniCruxtruder();
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
	{
		TileEntitySburbMachine te = (TileEntitySburbMachine) world.getTileEntity(pos);

		boolean b = super.removedByPlayer(state, world, pos, player, willHarvest);

		if(!world.isRemote && willHarvest && te != null)
		{
			ItemStack stack = new ItemStack(Item.getItemFromBlock(this), 1);
			if(te.color != -1)
			{	//Moved this here because it's unnecessarily hard to check the tile entity in block.getDrops(), since it has been removed by then
				stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setInteger("color", te.color);
			}
			spawnAsEntity(world, pos, stack);
		}

		return b;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return CRUXTRUDER_AABB;
	}
}
