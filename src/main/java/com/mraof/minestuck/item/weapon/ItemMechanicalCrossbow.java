package com.mraof.minestuck.item.weapon;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemMechanicalCrossbow extends MSWeaponBase
{

	protected float power;

	public ItemMechanicalCrossbow(String name, int maxUses, float power)
	{
		super(name, maxUses, 0, 0, 1);
		this.power = power;
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		return HashMultimap.create();
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer entityplayer, EnumHand handIn)
	{
		ActionResult<ItemStack> result = super.onItemRightClick(worldIn, entityplayer, handIn);
		if(result.getType() != EnumActionResult.PASS)
			return result;

		ItemStack stack = entityplayer.getHeldItem(handIn);

		boolean flag = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
		ItemStack itemstack = this.findAmmo(entityplayer);

		int i = (int) (power*20);
		i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, entityplayer, i, !itemstack.isEmpty() || flag);
		if (i < 0) return ActionResult.newResult(EnumActionResult.PASS, stack);

		if (!itemstack.isEmpty() || flag)
		{
			if (itemstack.isEmpty())
			{
				itemstack = new ItemStack(Items.ARROW);
			}

			float f = ItemBow.getArrowVelocity(i);

			if ((double)f >= 0.1D)
			{
				boolean flag1 = entityplayer.capabilities.isCreativeMode || (itemstack.getItem() instanceof ItemArrow && ((ItemArrow) itemstack.getItem()).isInfinite(itemstack, stack, entityplayer));

				if (!worldIn.isRemote)
				{
					ItemArrow itemarrow = (ItemArrow)(itemstack.getItem() instanceof ItemArrow ? itemstack.getItem() : Items.ARROW);
					EntityArrow entityarrow = itemarrow.createArrow(worldIn, itemstack, entityplayer);
					entityarrow.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, f * 3.0F, 1.0F);

					if (f == 1.0F)
					{
						entityarrow.setIsCritical(true);
					}

					int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);

					if (j > 0)
					{
						entityarrow.setDamage(entityarrow.getDamage() + (double)j * 0.5D + 0.5D);
					}

					int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);

					if (k > 0)
					{
						entityarrow.setKnockbackStrength(k);
					}

					if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0)
					{
						entityarrow.setFire(100);
					}

					stack.damageItem(1, entityplayer);

					if (flag1 || entityplayer.capabilities.isCreativeMode && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW))
					{
						entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
					}

					worldIn.spawnEntity(entityarrow);
				}

				worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

				if (!flag1 && !entityplayer.capabilities.isCreativeMode)
				{
					itemstack.shrink(1);

					if (itemstack.isEmpty())
					{
						entityplayer.inventory.deleteStack(itemstack);
					}
				}

				entityplayer.addStat(StatList.getObjectUseStats(this));
				return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
			}
		}

		return ActionResult.newResult(EnumActionResult.FAIL, stack);
	}

	protected boolean isArrow(ItemStack stack)
	{
		return stack.getItem() instanceof ItemArrow;
	}

	protected ItemStack findAmmo(EntityPlayer player)
	{
		if (this.isArrow(player.getHeldItem(EnumHand.OFF_HAND)))
		{
			return player.getHeldItem(EnumHand.OFF_HAND);
		}
		else if (this.isArrow(player.getHeldItem(EnumHand.MAIN_HAND)))
		{
			return player.getHeldItem(EnumHand.MAIN_HAND);
		}
		else
		{
			for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
			{
				ItemStack itemstack = player.inventory.getStackInSlot(i);

				if (this.isArrow(itemstack))
				{
					return itemstack;
				}
			}

			return ItemStack.EMPTY;
		}
	}
}
