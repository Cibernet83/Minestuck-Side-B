package com.mraof.minestuck.block;

import com.mraof.minestuck.item.ItemGhost;
import com.mraof.minestuck.item.block.MSItemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

import java.util.Collections;

public class BlockReturnNode extends BlockGate
{
	
	public BlockReturnNode(String name)
	{
		super(name);
		setResistance(10.0F);
	}
	
	@Override
	protected boolean isValid(BlockPos pos, World world)
	{
		for(int x = -1; x <= 0; x++)
			for(int z = -1; z <= 0; z++)
				if(x != 0 || z != 0)
				{
					IBlockState block = world.getBlockState(pos.add(x, 0, z));
					if(block.getBlock() != this || (Boolean) block.getValue(isMainComponent))
						return false;
				}
		
		return true;
	}
	
	@Override
	protected BlockPos findMainComponent(BlockPos pos, World world)
	{
		for(int x = 0; x <= 1; x++)
			for(int z = 0; z <= 1; z++)
				if(x != 0 || z != 0)
				{
					IBlockState block = world.getBlockState(pos.add(x, 0, z));
					if(block.getBlock() == this && (Boolean) block.getValue(isMainComponent))
						return pos.add(x, 0, z);
				}
		
		return null;
	}
	
	@Override
	protected void removePortal(BlockPos pos, World world)
	{
		for(int x = -1; x <= 0; x++)
			for(int z = -1; z <= 0; z++)
				if(world.getBlockState(pos.add(x, 0, z)).getBlock() == this)
					world.setBlockToAir(pos.add(x, 0, z));
	}

	@Override
	public MSItemBlock getItemBlock()
	{
		return new MSItemBlock(this)
		{
			@Override
			public void registerModel()
			{
				super.registerModel();
				ModelLoader.setCustomStateMapper(BlockReturnNode.this, (Block block) -> Collections.emptyMap());
			}
		};
	}
}
