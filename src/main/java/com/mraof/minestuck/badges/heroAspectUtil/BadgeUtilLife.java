package com.mraof.minestuck.badges.heroAspectUtil;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.client.particles.MinestuckParticles;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.MSGTUtils;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BadgeUtilLife extends BadgeHeroAspectUtil
{
	public BadgeUtilLife()
	{
		super(EnumAspect.LIFE, new ItemStack(Items.WHEAT, 200));
	}

	protected static final int RADIUS = 20;

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if(state.equals(GodKeyStates.KeyState.NONE) || time >= 16)
			return false;

		if(!player.isCreative() && player.getFoodStats().getFoodLevel() < 4)
		{
			player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
			return false;
		}

		if(time % 10 == 1)
		{
			BlockPos targetPos = MSGTUtils.getTargetBlock(player);

			if(targetPos != null && world.getBlockState(targetPos).getBlock() instanceof IGrowable)
			{
				IGrowable growable = (IGrowable) world.getBlockState(targetPos).getBlock();
				if(growable.canGrow(world, targetPos, world.getBlockState(targetPos), world.isRemote))
				{
					growable.grow(world, world.rand, targetPos, world.getBlockState(targetPos));

					if(!world.isRemote)
						world.playEvent(2005, targetPos, 0);

					badgeEffects.startPowerParticles(getClass(), MinestuckParticles.ParticleType.AURA, EnumAspect.LIFE, 4);
					if (!player.isCreative() && !world.isRemote)
						player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 4);
				}
			}
		}

		if(!player.isCreative() && player.getFoodStats().getFoodLevel() < 4)
		{
			player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
			return false;
		}

		if(time > 10)
			badgeEffects.startPowerParticles(getClass(), MinestuckParticles.ParticleType.BURST, EnumAspect.LIFE, 20);
        else
        	badgeEffects.startPowerParticles(getClass(), MinestuckParticles.ParticleType.AURA, EnumAspect.LIFE, 6);

		if(time >= 15)
		{
			for(EntityAnimal target : world.getEntitiesWithinAABB(EntityAnimal.class, player.getEntityBoundingBox().grow(RADIUS)))
			{
				target.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).oneshotPowerParticles(MinestuckParticles.ParticleType.AURA, EnumAspect.LIFE, 3);

				target.setInLove(player);
			}
			if (!player.isCreative() && !world.isRemote)
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 4);
		}

		return true;
	}
}
