package com.mraof.minestuck.item.operandi;

import com.google.common.collect.Multimap;
import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.item.MSItemBase;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.item.MinestuckTabs;
import com.mraof.minestuck.util.MinestuckSounds;
import com.mraof.minestuck.util.ModusStorage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Set;

public class ItemCruxiteTool extends MSItemBase implements ICruxiteArtifact //TODO extend MSUWeaponBase
{
	private final CruxiteArtifactTeleporter teleporter;
	protected float efficiency;
	/**
	 * Damage versus entities.
	 */
	protected float attackDamage;
	protected float attackSpeed;
	/**
	 * The material this tool is made from.
	 */
	//protected Item.ToolMaterial toolMaterial;
	protected String toolClass = "";

	public ItemCruxiteTool(String name, String toolClass, boolean isEntryArtifact)
	{
		this(name, toolClass, 0.0F, 0.0F, 7.0f, 3, isEntryArtifact);
	}

	public ItemCruxiteTool(String name, String toolClass, float attackDamageIn, float attackSpeedIn, float efficiency, int maxUses, boolean isEntryArtifact)
	{
		super(name);
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

		this.efficiency = 4.0F;
		this.maxStackSize = 1;
		this.setMaxDamage(maxUses);
		this.efficiency = efficiency;
		this.attackDamage = attackDamageIn;
		this.attackSpeed = attackSpeedIn;
		this.toolClass = toolClass;

		setCreativeTab(MinestuckTabs.minestuck);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
	}

	public float getDestroySpeed(ItemStack stack, IBlockState state)
	{
		if (state.getBlock().isToolEffective(toolClass, state))
			return efficiency;
		return 1.0F;
	}

	/**
	 * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
	 * the damage on the stack.
	 */
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		stack.damageItem(getMaxDamage(stack) + 1, attacker);
		return true;
	}

	/**
	 * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
	 */
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
	{
		if ((double) state.getBlockHardness(worldIn, pos) != 0.0D)
		{
			if (state.getBlock().isToolEffective(toolClass, state))
			{
				ICaptchalogueable storedStack = ModusStorage.getStoredItem(stack);

				if (!worldIn.isRemote)
					stack.damageItem(1, entityLiving);
				if (stack.isEmpty())
				{
					if (isEntryArtifact() && entityLiving instanceof EntityPlayer)
					{
						getTeleporter().onArtifactActivated((EntityPlayer) entityLiving);
						return true;
					}

					worldIn.playSound(null, entityLiving.getPosition(), MinestuckSounds.operandiTaskComplete, SoundCategory.PLAYERS, 1, 1);

					if (entityLiving instanceof EntityPlayer)
						storedStack.fetch((EntityPlayer) entityLiving);
					else storedStack.drop(entityLiving);
				}
			}
			else if (!worldIn.isRemote) stack.damageItem(getMaxDamage(stack) + 1, entityLiving);
		}

		return true;
	}

	/**
	 * Returns True is the item is renderer in full 3D when hold.
	 */
	@SideOnly(Side.CLIENT)
	public boolean isFull3D()
	{
		return true;
	}

	@Override
	public boolean isEnchantable(ItemStack stack)
	{
		return false;
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based on material.
	 */
	public int getItemEnchantability()
	{
		return -1;
	}

	/**
	 * Return whether this item is repairable in an anvil.
	 *
	 * @param toRepair the {@code ItemStack} being repaired
	 * @param repair   the {@code ItemStack} being used to perform the repair
	 */
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
		return false;
	}

	/**
	 * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
	 */
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
	{
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

		if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
		{
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", (double) this.attackDamage, 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double) this.attackSpeed, 0));
		}

		return multimap;
	}

	@Override
	public Set<String> getToolClasses(ItemStack stack)
	{
		return toolClass != null ? com.google.common.collect.ImmutableSet.of(toolClass) : super.getToolClasses(stack);
	}

	@javax.annotation.Nullable
	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass, @javax.annotation.Nullable EntityPlayer player, @javax.annotation.Nullable IBlockState blockState)
	{
		int level = super.getHarvestLevel(stack, toolClass, player, blockState);
		if (level == -1 && toolClass.equals(this.toolClass))
		{
			return 2;
		}
		else
		{
			return level;
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
}
