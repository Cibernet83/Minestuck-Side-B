package com.mraof.minestuck.inventory.captchalouge;

import com.mraof.minestuck.client.gui.captchalogue.BaseModusGuiHandler;
import com.mraof.minestuck.util.ModusStorage;
import com.mraof.minestuck.client.gui.captchalogue.SylladexGuiHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class OperandiModus extends BaseModus
{
	
	public static final ArrayList<Item> itemPool = new ArrayList<>();
	
	@Nonnull
	@Override
	public ItemStack getItem(int id, boolean asCard)
	{
		ItemStack operandiItem = new ItemStack(itemPool.get(player.world.rand.nextInt(itemPool.size())));
		if(id == CaptchaDeckHandler.EMPTY_SYLLADEX)
		{
			for(ItemStack item : list)
				CaptchaDeckHandler.launchAnyItem(player, ModusStorage.storeItem(operandiItem, item));
			list.clear();
			return ItemStack.EMPTY;
		}
		ItemStack item = super.getItem(id, asCard);
		if(asCard || item.isEmpty() || itemPool.isEmpty())
			return item;
		return ModusStorage.storeItem(operandiItem, item);
	}
	
	@Override
	protected boolean getSort()
	{
		return false;
	}
	@Override
	@SideOnly(Side.CLIENT)
	public SylladexGuiHandler getGuiHandler()
	{
		if(gui == null)
			gui = new BaseModusGuiHandler(this, 13) {};
		return gui;
	}
}
