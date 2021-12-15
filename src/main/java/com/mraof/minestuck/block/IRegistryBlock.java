package com.mraof.minestuck.block;

import com.mraof.minestuck.item.block.MSItemBlock;
import com.mraof.minestuck.util.IRegistryObject;
import net.minecraft.block.Block;

public interface IRegistryBlock extends IRegistryObject<Block>
{
	default MSItemBlock getItemBlock()
	{
		return new MSItemBlock((Block) this);
	}

	/*default void registerModel()
	{
		Item item = Item.getItemFromBlock((Block)this);
		if(item == Items.AIR)
			throw new IllegalArgumentException("That block doesn't have an item, and this method is only intended for blocks with a connected itemblock.");
		((MSItemBlock)item).registerModel();
	}*/
}
