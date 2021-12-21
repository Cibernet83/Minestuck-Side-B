package com.mraof.minestuck.item.properties;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.alchemy.Grist;
import com.mraof.minestuck.alchemy.MinestuckGrists;
import com.mraof.minestuck.block.MinestuckBlocks;
import com.mraof.minestuck.entity.EntityFrog;
import com.mraof.minestuck.entity.underling.EntityUnderling;
import com.mraof.minestuck.item.IPropertyWeapon;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public class PropertyGristSetter extends WeaponProperty
{
	public Grist grist;

	public PropertyGristSetter(Grist type)
	{
		this.grist = type;
	}

	@Override
	public void onEntityHit(ItemStack stack, EntityLivingBase target, EntityLivingBase player)
	{
		int frogType = -1;
		if(grist == MinestuckGrists.gold)
			frogType = 5;
		if(grist == MinestuckGrists.ruby)
			frogType = 2;
		if(grist == MinestuckGrists.artifact)
			frogType = target.world.rand.nextInt(100) == 0 ? 6 : 4;

		if(frogType != -1 && target instanceof EntityFrog && ((EntityFrog) target).getType() != frogType && ((EntityFrog) target).getType() != 4)
		{
			((WorldServer)target.world).spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, target.posX, target.posY + target.height/2f, target.posZ, 1, 0.0D, 0.0D, 0.0D, 0.0D, new int[0]);
			((WorldServer)target.world).spawnParticle(EnumParticleTypes.BLOCK_CRACK, target.posX, target.posY + target.height/2f, target.posZ, 5, 0.2D, 0.2D, 0.2D, 0.5D,
					Block.getStateId(MinestuckBlocks.gristBlocks.get(grist).getDefaultState()));
			target.playSound(SoundEvents.BLOCK_ANVIL_LAND, 0.7f, ((target.world.rand.nextFloat() - target.world.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);

			NBTTagCompound nbt = target.writeToNBT(new NBTTagCompound());
			nbt.setInteger("Type", frogType);
			target.readFromNBT(nbt);
		}
		if(target instanceof EntityUnderling && ((EntityUnderling) target).getGristType() != grist)
		{
			((WorldServer)target.world).spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, target.posX, target.posY + target.height/2f, target.posZ, 1, 0.0D, 0.0D, 0.0D, 0.0D, new int[0]);
			((WorldServer)target.world).spawnParticle(EnumParticleTypes.BLOCK_CRACK, target.posX, target.posY + target.height/2f, target.posZ, 5, target.width/2f, target.height/2f, target.width/2f, 0.5D,
					Block.getStateId(MinestuckBlocks.gristBlocks.get(grist).getDefaultState()));
			target.playSound(SoundEvents.BLOCK_ANVIL_LAND, 0.7f, ((target.world.rand.nextFloat() - target.world.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);

			((EntityUnderling) target).applyGristType(grist, false);
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onLivingDamage(LivingDamageEvent event)
	{
		if(event.getSource() instanceof EntityDamageSource && event.getSource().getTrueSource() instanceof EntityLivingBase)
		{
			EntityLivingBase source = ((EntityLivingBase) event.getSource().getTrueSource());
			ItemStack stack = source.getHeldItemMainhand();

			//On frog initial hit
			if(stack.getItem() instanceof IPropertyWeapon && ((IPropertyWeapon) stack.getItem()).hasProperty(PropertyGristSetter.class, stack))
			{
				Grist grist = ((PropertyGristSetter) ((IPropertyWeapon) stack.getItem()).getProperty(PropertyGristSetter.class, stack)).grist;
				int frogType = -1;
				if(grist == MinestuckGrists.gold)
					frogType = 5;
				if(grist == MinestuckGrists.ruby)
					frogType = 2;
				if(grist == MinestuckGrists.artifact)
					frogType = 6;

				if(event.getEntityLiving() instanceof EntityFrog && (((EntityFrog) event.getEntityLiving()).getType() != frogType || ((EntityFrog) event.getEntityLiving()).getType() == 4))
					event.setAmount(0);
			}

		}
	}
}
