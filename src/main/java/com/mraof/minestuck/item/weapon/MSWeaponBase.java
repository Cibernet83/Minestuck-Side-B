package com.mraof.minestuck.item.weapon;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.item.IClassedTool;
import com.mraof.minestuck.item.IPropertyWeapon;
import com.mraof.minestuck.item.MSItemBase;
import com.mraof.minestuck.item.MinestuckTabs;
import com.mraof.minestuck.item.properties.IEnchantableProperty;
import com.mraof.minestuck.item.properties.WeaponProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class MSWeaponBase extends MSItemBase implements IClassedTool, ISortedTabItem, IPropertyWeapon<MSWeaponBase>
{
	public static int slotIndex = 0;
	protected final ArrayList<WeaponProperty> properties = new ArrayList<>();
	protected boolean unbreakable;
	protected double weaponDamage;
	protected int enchantability;
	protected double weaponSpeed;
	protected ToolMaterial material;
	protected MSToolClass tool = null;
	protected float harvestSpeed = 0;
	ArrayList<ItemStack> repairMaterials = new ArrayList<>();
	private int tabSlot = 0;
	private int harvestLevel = 0;

	public MSWeaponBase(String name, double damageVsEntity, double weaponSpeed, int enchantability)
	{
		this(name, -1, damageVsEntity, weaponSpeed, enchantability);
		unbreakable = true;
	}

	public MSWeaponBase(String name, int maxUses, double damageVsEntity, double weaponSpeed, int enchantability)
	{
		super(name);
		this.setCreativeTab(MinestuckTabs.weapons);

		this.unbreakable = maxUses <= 0;
		this.maxStackSize = 1;
		this.setMaxDamage(maxUses);
		this.weaponDamage = damageVsEntity;
		this.weaponSpeed = weaponSpeed;
		this.enchantability = enchantability;

		this.addPropertyOverride(new ResourceLocation(Minestuck.MODID, "active"), (stack, worldIn, entityIn) -> isAbilityActive(stack, worldIn, entityIn) ? 1 : 0);
	}

	public boolean isAbilityActive(ItemStack stack, World worldIn, EntityLivingBase entityIn)
	{
		for (WeaponProperty p : getProperties(stack))
			if (p.isAbilityActive(stack, worldIn, entityIn))
				return true;
		return false;
	}

	public static UUID getAttackDamageUUID()
	{
		return ATTACK_DAMAGE_MODIFIER;
	}

	public static UUID getAttackSpeedUUID()
	{
		return ATTACK_SPEED_MODIFIER;
	}

	public MSWeaponBase setMaterial(Item.ToolMaterial material)
	{
		this.material = material;
		return this;
	}

	public MSWeaponBase setRepairMaterials(ItemStack... stacks)
	{
		for (ItemStack i : stacks)
			repairMaterials.add(i);
		return this;
	}

	public MSWeaponBase setRepairMaterial(String oredic)
	{
		if (OreDictionary.doesOreNameExist(oredic))
			setRepairMaterials(OreDictionary.getOres(oredic));
		return this;
	}

	public MSWeaponBase setRepairMaterials(Collection<ItemStack> stacks)
	{
		repairMaterials.addAll(stacks);
		return this;
	}

	public double getUnmodifiedAttackDamage(ItemStack stack)
	{
		double dmg = weaponDamage;

		for (WeaponProperty p : getProperties(stack))
			dmg = p.getAttackDamage(stack, dmg);

		return dmg;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		for (WeaponProperty p : getProperties(player.getHeldItem(hand)))
		{
			EnumActionResult actionResult = p.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
			if (actionResult != EnumActionResult.PASS)
				return actionResult;
		}

		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state)
	{
		if (getTool() == null)
			return super.getDestroySpeed(stack, state);
		if (getTool().canHarvest(state))
			return harvestSpeed;
		return super.getDestroySpeed(stack, state);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		for (WeaponProperty p : getProperties(playerIn.getHeldItem(handIn)))
		{
			EnumActionResult actionResult = p.onItemRightClick(worldIn, playerIn, handIn);
			if (actionResult != EnumActionResult.PASS)
				return ActionResult.newResult(actionResult, playerIn.getHeldItem(handIn));
		}

		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
	{
		for (WeaponProperty p : getProperties(stack))
			stack = p.onItemUseFinish(stack, worldIn, entityLiving);
		return super.onItemUseFinish(stack, worldIn, entityLiving);
	}

	@Override
	public boolean isDamageable()
	{
		return !unbreakable;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase player)
	{
		for (WeaponProperty p : getProperties(stack))
			p.onEntityHit(stack, target, player);

		if (!this.unbreakable)
		{
			stack.damageItem(1, player);
		}

		return true;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
	{
		int dmg = 1;
		if (!canHarvestBlock(state))
			dmg = 2;
		if ((double) state.getBlockHardness(worldIn, pos) == 0.0D)
			dmg = 0;

		getProperties(stack).forEach(p -> p.onBlockDestroyed(stack, worldIn, state, pos, entityLiving));
		stack.damageItem(dmg, entityLiving);

		return true;
	}

	@Override
	public boolean canHarvestBlock(IBlockState blockIn)
	{
		if (getTool() == null)
			return super.canHarvestBlock(blockIn);
		return tool.canHarvest(blockIn);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		getProperties(stack).forEach(p -> p.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected));
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		for (WeaponProperty p : getProperties(stack))
		{
			EnumAction result = p.getItemUseAction(stack);
			if (result != null)
				return result;
		}

		return super.getItemUseAction(stack);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		int result = super.getMaxItemUseDuration(stack);
		for (WeaponProperty p : getProperties(stack))
			result = p.getMaxItemUseDuration(stack, result);
		return result;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
	{
		for (WeaponProperty p : getProperties(stack))
			p.onStopUsing(stack, worldIn, entityLiving, timeLeft);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		String name = super.getItemStackDisplayName(stack);
		for (WeaponProperty p : getProperties(stack))
			name = p.getItemStackDisplayName(stack, name);
		return name;
	}

	@Override
	public boolean isEnchantable(ItemStack stack)
	{
		if (getTool() == null)
			return super.isEnchantable(stack);
		return !(getTool().getEnchantments().isEmpty() && getTool().getEnchantmentTypes().isEmpty()) || isDamageable();
	}

	@Override
	public int getItemEnchantability()
	{
		return enchantability;
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{

		for (ItemStack mat : repairMaterials)
			if (!mat.isEmpty() && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, repair, false)) return true;

		for (WeaponProperty p : getProperties(toRepair))
			if (p.getIsRepairable(toRepair, repair))
				return true;

		return super.getIsRepairable(toRepair, repair);
	}

	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
	{
		Multimap<String, AttributeModifier> multimap = HashMultimap.create();
		if (slot == EntityEquipmentSlot.MAINHAND)
		{
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.getAttackDamage(stack), 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", this.getAttackSpeed(stack), 0));
		}

		return multimap;
	}

	public double getAttackDamage(ItemStack stack)
	{
		double dmg = weaponDamage;
		double base = Math.floor(16 * 1.6 / (4 + weaponSpeed)) / 2d;

		if (dmg > base)
			dmg = Math.round(((dmg - base) * MinestuckConfig.weaponAttackMultiplier) * 2) / 2f + base;

		for (WeaponProperty p : getProperties(stack))
			dmg = p.getAttackDamage(stack, dmg);

		return dmg;
	}

	public double getAttackSpeed(ItemStack stack)
	{
		double spd = weaponSpeed;

		for (WeaponProperty p : getProperties(stack))
			spd = p.getAttackSpeed(stack, spd);

		return spd;
	}

	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
		for (WeaponProperty p : getProperties(player.getHeldItem(hand)))
		{
			EnumActionResult actionResult = p.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
			if (actionResult != EnumActionResult.PASS)
				return actionResult;
		}
		return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem)
	{
		getProperties(entityItem.getItem()).forEach(p -> p.onEntityItemUpdate(entityItem));
		return super.onEntityItemUpdate(entityItem);
	}

	@Override
	public void setDamage(ItemStack stack, int damage)
	{
		for (WeaponProperty p : getProperties(stack))
			damage = p.onDurabilityChanged(stack, damage);
		super.setDamage(stack, damage);
	}

	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass, @Nullable EntityPlayer player, @Nullable IBlockState blockState)
	{
		if (getTool() == null)
			return super.getHarvestLevel(stack, toolClass, player, blockState);
		return getTool().canHarvest(blockState) ? harvestLevel : -1;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
	{
		if (enchantment.type.equals(EnumEnchantmentType.BREAKABLE))
			return !unbreakable;

		for (WeaponProperty p : getProperties(stack))
			if (p instanceof IEnchantableProperty)
			{
				Boolean bool = ((IEnchantableProperty) p).canEnchantWith(stack, enchantment);
				if (bool != null)
					return bool;
			}

		if (getTool() == null)
			return super.canApplyAtEnchantingTable(stack, enchantment);

		return getTool().canEnchantWith(enchantment);
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
	{
		for (WeaponProperty p : getProperties(oldStack))
			if (!p.shouldCauseReequipAnimation(oldStack, newStack, slotChanged))
				return false;

		return super.shouldCauseBlockBreakReset(oldStack, newStack);
	}

	@Override
	public boolean shouldCauseBlockBreakReset(ItemStack oldStack, ItemStack newStack)
	{
		for (WeaponProperty p : getProperties(oldStack))
			if (!p.shouldCauseBlockBreakReset(oldStack, newStack))
				return false;

		return super.shouldCauseBlockBreakReset(oldStack, newStack);
	}

	@Override
	public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack)
	{
		return oldStack.isItemEqualIgnoreDurability(newStack);
	}

	@Override
	public boolean canDisableShield(ItemStack stack, ItemStack shield, EntityLivingBase entity, EntityLivingBase attacker)
	{
		return getToolClass() != null && getToolClass().disablesShield();
	}

	@Override
	public MSToolClass getToolClass()
	{
		return tool;
	}

	public MSToolClass getTool() {return tool;}

	public MSWeaponBase setTool(MSToolClass cls, int harvestLevel, float harvestSpeed)
	{
		tool = cls;
		this.harvestLevel = harvestLevel;
		this.harvestSpeed = harvestSpeed;
		return this;
	}

	@Override
	public List<WeaponProperty> getProperties()
	{
		return properties;
	}

	@Override
	public void setTabSlot()
	{
		tabSlot = slotIndex++;
	}
}
