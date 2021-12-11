package com.cibernet.fetchmodiplus.items;

import com.cibernet.fetchmodiplus.captchalogue.OperandiModus;
import com.cibernet.fetchmodiplus.registries.FMPSounds;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;

public class OperandiFoodItem extends FoodItem
{
	private final EnumAction action;
	
	public OperandiFoodItem(String name, int amount, float saturation)
	{
		this(name, amount, saturation, EnumAction.EAT);
	}
	public OperandiFoodItem(String name, int amount, float saturation, EnumAction action)
	{
		super(name, amount, saturation, false, getConsumer());
		OperandiModus.itemPool.add(this);
		this.action = action;
		setMaxStackSize(1);
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
	}
	
	
	public EnumAction getItemUseAction(ItemStack stack) {
		return action;
	}
	
	public static FoodItem.FoodItemConsumer getConsumer()
	{
		return ((stack, worldIn, player) ->
		{
			ItemStack storedStack = BaseItem.getStoredItem(stack);
			if(storedStack.isEmpty())
				return null;
			
			worldIn.playSound(null, player.getPosition(), FMPSounds.operandiTaskComplete, SoundCategory.PLAYERS, 1, 1);
			
			if(stack.getCount() <= 1)
				return storedStack;
			else if(!player.addItemStackToInventory(storedStack))
				player.dropItem(storedStack, true);
			return null;
		});
	}
}
