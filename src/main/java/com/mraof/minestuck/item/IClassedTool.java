package com.mraof.minestuck.item;

import com.mraof.minestuck.item.weapon.MSToolClass;
import com.mraof.minestuck.util.IRegistryItem;
import net.minecraft.item.Item;

public interface IClassedTool extends IRegistryItem<Item>
{
	MSToolClass getToolClass();
}
