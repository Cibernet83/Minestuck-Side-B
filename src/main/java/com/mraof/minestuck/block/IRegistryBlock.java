package com.mraof.minestuck.block;

import com.mraof.minestuck.item.block.MSItemBlock;
import com.mraof.minestuck.util.IRegistryItem;
import net.minecraft.block.Block;

public interface IRegistryBlock extends IRegistryItem<Block>
{
	MSItemBlock getItemBlock();
}
