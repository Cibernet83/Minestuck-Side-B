package com.mraof.minestuck.item.weapon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;


public class ItemDualWeapon extends ItemWeapon
{
	public String prefix;
	private double power;
	private double shiethedPower;
	private double speed;
	private double shiethedSpeed;

	public ItemDualWeapon(int maxUses, double damageVsEntity, double damagedVsEntityWhileShiethed, double weaponSpeed, double weaponSpeedWhileShiethed, int enchantability, String name)
	{
		super(ToolMaterial.IRON, maxUses, damageVsEntity, weaponSpeed, enchantability, name, true);
		this.prefix = name;
		this.power = damageVsEntity;
		this.shiethedPower = damagedVsEntityWhileShiethed;
		this.speed = weaponSpeed;
		this.shiethedSpeed = weaponSpeedWhileShiethed;
	}

	public boolean isDrawn(ItemStack itemStack)
	{
		return checkTagCompound(itemStack).getBoolean("IsDrawn");
	}

	private NBTTagCompound checkTagCompound(ItemStack stack)
	{
		NBTTagCompound tagCompound = stack.getTagCompound();
		if (tagCompound == null)
			stack.setTagCompound(tagCompound = new NBTTagCompound());
		if (!tagCompound.hasKey("IsDrawn"))
			tagCompound.setBoolean("IsDrawn", true);
		return tagCompound;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack itemStackIn = player.getHeldItem(hand);
		if (player.isSneaking())
		{
			if (isDrawn(itemStackIn))
				sheath(itemStackIn);
			else
				draw(itemStackIn);
			return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
		}
		return new ActionResult<>(EnumActionResult.PASS, itemStackIn);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return "item." + prefix + (isDrawn(stack) ? "Drawn" : "Sheathed");
	}

	@Override
	public double getAttackDamage(ItemStack stack)
	{
		return isDrawn(stack) ? power : shiethedPower;
	}

	@Override
	protected double getAttackSpeed(ItemStack stack)
	{
		return isDrawn(stack) ? speed : shiethedSpeed;
	}


	public void sheath(ItemStack stack)
	{
		NBTTagCompound tagCompound = checkTagCompound(stack);
		tagCompound.setBoolean("IsDrawn", false);
	}

	public void draw(ItemStack stack)
	{
		NBTTagCompound tagCompound = checkTagCompound(stack);
		tagCompound.setBoolean("IsDrawn", true);
	}
}
