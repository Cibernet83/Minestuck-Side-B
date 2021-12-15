package com.mraof.minestuck.item.weapon;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.Beam;
import com.mraof.minestuck.item.properties.PropertyDualWield;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mraof.minestuck.network.MinestuckChannelHandler;
import com.mraof.minestuck.network.MinestuckPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemBeamWeapon extends MSWeaponBase implements IBeamStats
{
	public float beamRadius;
	public float beamDamage;
	public float beamSpeed;
	public int beamHurtTime;
	public int beamTime;
	protected ResourceLocation beamTexture = new ResourceLocation(Minestuck.MODID, "textures/entity/projectiles/beam.png");

	public ItemBeamWeapon(String name, int maxUses, double damageVsEntity, double weaponSpeed, float beamRadius, float beamDamage, float beamSpeed, int beamTime, int beamHurtTime, int enchantability) {
		super(name, maxUses, damageVsEntity, weaponSpeed, enchantability);
		this.beamDamage = beamDamage;
		this.beamRadius = beamRadius;
		this.beamSpeed = beamSpeed;
		this.beamHurtTime = beamHurtTime;
		this.beamTime = beamTime;
	}

	public ItemBeamWeapon(String name, int maxUses, double damageVsEntity, double weaponSpeed, float beamRadius, float beamDamage, float beamSpeed, int beamTime, int enchantability) {
		this(name, maxUses, damageVsEntity, weaponSpeed, beamRadius, beamDamage, beamSpeed, beamTime,15, enchantability);
	}

	public ItemBeamWeapon(String name, int maxUses, double damageVsEntity, double weaponSpeed, float beamRadius, float beamDamage, float beamSpeed, int enchantability) {
		this(name, maxUses, damageVsEntity, weaponSpeed, beamRadius, beamDamage, beamSpeed, 72000, enchantability);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 72000;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ActionResult<ItemStack> sup = super.onItemRightClick(worldIn, playerIn, handIn);

		if(sup.getType() != EnumActionResult.PASS)
			return sup;

		ItemStack stack = playerIn.getHeldItem(handIn);

		if(hasProperty(PropertyDualWield.class, stack) && (handIn != EnumHand.MAIN_HAND || !ItemStack.areItemsEqualIgnoreDurability(stack, playerIn.getHeldItemOffhand())))
			return ActionResult.newResult(EnumActionResult.PASS, stack);

		if(!worldIn.isRemote)
		{
			Beam beam = new Beam(playerIn, stack, beamSpeed);

			beam.damage = beamDamage;

			if(!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setUniqueId("Beam", beam.getUniqueID());

			Beam.fireBeam(beam);

			playerIn.setActiveHand(handIn);
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);

		if(!worldIn.isRemote && entityIn instanceof EntityLivingBase &&
				((EntityLivingBase) entityIn).getActiveItemStack().equals(stack) && stack.hasTagCompound() && stack.getTagCompound().hasUniqueId("Beam"))
		{
			int useTime = getMaxItemUseDuration(stack)-((EntityLivingBase) entityIn).getItemInUseCount();
			if(useTime % 20 == 0)
			{
				if(hasProperty(PropertyDualWield.class, stack))
					((EntityLivingBase) entityIn).getHeldItemOffhand().damageItem(1, (EntityLivingBase) entityIn);
				stack.damageItem(1, (EntityLivingBase) entityIn);
			}

			if(useTime > beamTime)
			{
				Beam beam = worldIn.getCapability(MinestuckCapabilities.BEAM_DATA, null).getBeam(stack.getTagCompound().getUniqueId("Beam"));
				if(beam != null && !beam.isBeamReleased())
				{
					if(entityIn instanceof EntityPlayer)
						((EntityPlayer) entityIn).getCooldownTracker().setCooldown(stack.getItem(), beam.getDuration());
					beam.releaseBeam();
					for(EntityPlayer player : beam.world.playerEntities)
						MinestuckChannelHandler.sendToPlayer(MinestuckPacket.makePacket(MinestuckPacket.Type.UPDATE_BEAMS, beam.world), player);
				}
			}

		}
	}

	@Override
	public float getBeamRadius(ItemStack stack) {
		return beamRadius;
	}

	@Override
	public int getBeamHurtTime(ItemStack stack) {
		return beamHurtTime;
	}

	@Override
	public ResourceLocation getBeamTexture()
	{
		return beamTexture;
	}

	@Override
	public void setBeamTexture(String fileName)
	{
		beamTexture = new ResourceLocation(getRegistryName().getResourceDomain(), "textures/entity/projectiles/"+fileName+".png");
	}

	@Override
	public void setCustomBeamTexture()
	{
		setBeamTexture(getRegistryName().getResourcePath());
	}


	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = HashMultimap.create();
		if (slot == EntityEquipmentSlot.MAINHAND)
		{
			if(getAttackDamage(stack) != 0)
				multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.getAttackDamage(stack), 0));
			if(getAttackSpeed(stack) != 0)
				multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", this.getAttackSpeed(stack), 0));
		}

		return multimap;
	}
}
