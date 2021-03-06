package com.mraof.minestuck.inventory.captchalouge;

import com.mraof.minestuck.entity.EntityCruxiteSlime;
import com.mraof.minestuck.alchemy.AlchemyRecipes;
import com.mraof.minestuck.client.gui.captchalogue.SylladexGuiHandler;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.util.ColorCollector;
import com.mraof.minestuck.util.MinestuckPlayerData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class SlimeModus extends Modus
{
	@Override
	protected boolean doesAutobalance()
	{
		return false;
	}
	
	@Nonnull
	@Override
	public ItemStack getItem(int id, boolean asCard)
	{
		if(id == SylladexUtils.EMPTY_CARD)
		{
			if(list.size() < size)
			{
				size--;
				return new ItemStack(MinestuckItems.captchaCard);
			} else return ItemStack.EMPTY;
		}
		
		if(list.isEmpty())
			return ItemStack.EMPTY;
		
		int color = MinestuckPlayerData.getData(player).color;
		
		if(id == SylladexUtils.EMPTY_SYLLADEX)
		{
			for(ItemStack item : list)
				createSlime(player, item, color);
			list.clear();
			return ItemStack.EMPTY;
		}
		
		if(id < 0 || id >= list.size())
			return ItemStack.EMPTY;
		
		ItemStack item = doesAutobalance() ? list.remove(id) : this.list.set(id, ItemStack.EMPTY);
		
		if(asCard)
		{
			size--;
			item = AlchemyRecipes.createCard(item, false);
			return item;
		}
		createSlime(player, item, color);
		return ItemStack.EMPTY;
	}
	
	public static void createSlime(EntityPlayer source, ItemStack stack, int color)
	{
		EntityCruxiteSlime entity = new EntityCruxiteSlime(source.world);
		entity.setSlimeSize(1, true);
		entity.setSlimeColor(color == -1 ? 0x99D9EA : ColorCollector.getColor(color));
		entity.setCruxiteType(color+1);
		entity.setStoredItem(stack);
		entity.motionX = SylladexUtils.rand.nextDouble() - 0.5D;
		entity.motionZ = SylladexUtils.rand.nextDouble() - 0.5D;
		entity.setPosition(source.posX, source.posY + 1.0D, source.posZ);
		source.world.spawnEntity(entity);
	}
	@Override
	@SideOnly(Side.CLIENT)
	public SylladexGuiHandler getGuiHandler()
	{
		if(gui == null)
			gui = new BaseModusGuiHandler(this, 6) {};
		return gui;
	}
}
