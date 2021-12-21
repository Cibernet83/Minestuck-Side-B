package com.mraof.minestuck.item.operandi;

import com.mraof.minestuck.item.ItemFood;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.util.MinestuckSounds;
import com.mraof.minestuck.util.ModusStorage;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class ItemCruxiteFood extends ItemFood implements ICruxiteArtifact
{
	private final EnumAction action;
	private final CruxiteArtifactTeleporter teleporter;
	
	public ItemCruxiteFood(String name, int amount, float saturation, boolean isEntryArtifact)
	{
		this(name, amount, saturation, EnumAction.EAT, isEntryArtifact);
	}
	public ItemCruxiteFood(String name, int amount, float saturation, EnumAction action, boolean isEntryArtifact)
	{
		super(name, amount, saturation, false, getConsumer());
		this.action = action;
		setMaxStackSize(1);

		if(isEntryArtifact)
		{
			teleporter =  new CruxiteArtifactTeleporter();
			MinestuckItems.cruxiteArtifacts.add(this);
		}
		else
		{
			teleporter = null;
			//OperandiModus.itemPool.add(this);
		}
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if(isEntryArtifact())
			super.getSubItems(tab, items);
	}
	
	
	public EnumAction getItemUseAction(ItemStack stack) {
		return action;
	}
	
	public static FoodItemConsumer getConsumer()
	{
		return ((stack, worldIn, player) ->
		{

			if(stack.getItem() instanceof ICruxiteArtifact && ((ICruxiteArtifact) stack.getItem()).isEntryArtifact())
			{
				((ICruxiteArtifact) stack.getItem()).getTeleporter().onArtifactActivated(player);
				ItemStack result = stack.copy();
				result.shrink(1);
				return result;
			}

			ItemStack storedStack = ModusStorage.getStoredItem(stack);
			if(storedStack.isEmpty())
				return null;
			
			worldIn.playSound(null, player.getPosition(), MinestuckSounds.operandiTaskComplete, SoundCategory.PLAYERS, 1, 1);
			
			if(stack.getCount() <= 1)
				return storedStack;
			else if(!player.addItemStackToInventory(storedStack))
				player.dropItem(storedStack, true);
			return null;
		});
	}

	@Override
	public boolean isEntryArtifact() {
		return teleporter != null;
	}

	@Override
	public CruxiteArtifactTeleporter getTeleporter() {
		return teleporter;
	}


	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack itemstack = playerIn.getHeldItem(handIn);

		if (playerIn.isCreative())
		{
			playerIn.setActiveHand(handIn);
			return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
		}
		else return super.onItemRightClick(worldIn, playerIn, handIn);
	}

}
