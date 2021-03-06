package com.mraof.minestuck.item.operandi;

import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.item.IRegistryItem;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.item.MinestuckTabs;
import com.mraof.minestuck.util.IRegistryObject;
import com.mraof.minestuck.util.MinestuckSounds;
import com.mraof.minestuck.util.ModusStorage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemCruxiteHoe extends ItemHoe implements IRegistryItem, ICruxiteArtifact
{
	private final String regName;
	private final CruxiteArtifactTeleporter teleporter;

	public ItemCruxiteHoe(String name, boolean isEntryArtifact)
	{
		super(ToolMaterial.IRON);
		setMaxDamage(10);

		regName = IRegistryObject.unlocToReg(name);
		setUnlocalizedName(name);
		setCreativeTab(MinestuckTabs.minestuck);

		if (isEntryArtifact)
		{
			teleporter = new CruxiteArtifactTeleporter();
			MinestuckItems.cruxiteArtifacts.add(this);
		}
		else
		{
			teleporter = null;
			//OperandiModus.itemPool.add(this);
		}
		MinestuckItems.items.add(this);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		stack.damageItem(getMaxDamage(stack) + 1, attacker);
		return true;
	}

	protected void setBlock(ItemStack stack, EntityPlayer player, World worldIn, BlockPos pos, IBlockState state)
	{
		worldIn.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

		if (!worldIn.isRemote)
		{
			worldIn.setBlockState(pos, state, 11);
			stack.damageItem(1, player);
		}
		if (stack.isEmpty())
		{
			if (isEntryArtifact())
			{
				getTeleporter().onArtifactActivated(player);
				return;
			}

			ICaptchalogueable storedStack = ModusStorage.getStoredItem(stack);
			worldIn.playSound(null, player.getPosition(), MinestuckSounds.operandiTaskComplete, SoundCategory.PLAYERS, 1, 1);

			storedStack.fetch(player);
		}
	}

	@Override
	public boolean isEntryArtifact()
	{
		return teleporter != null;
	}

	@Override
	public CruxiteArtifactTeleporter getTeleporter()
	{
		return teleporter;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
	{
		stack.damageItem(getMaxDamage(stack) + 1, entityLiving);
		return true;
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
	}

	@Override
	public void register(IForgeRegistry<Item> registry)
	{
		setRegistryName(regName);
		registry.register(this);
	}
}
