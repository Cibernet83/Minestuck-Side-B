package com.mraof.minestuck.badges.heroAspectUtil;

import com.mraof.minestuck.badges.Badge;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.client.particles.MinestuckParticles;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.MinestuckUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.Random;

public class BadgeUtilTime extends BadgeHeroAspectUtil
{
	private static final Random random = new Random();

	public BadgeUtilTime() {
		super(EnumAspect.TIME, new ItemStack(Items.CLOCK, 100));
	}

	@Override
	public boolean canUnlock(World world, EntityPlayer player)
	{
		if (Badge.findItem(player, new ItemStack(MinestuckItems.heroStoneShards.get(EnumAspect.TIME), 128), false) &&
			Badge.findItem(player, new ItemStack(Items.CLOCK, 100), false))
		{
			Badge.findItem(player, new ItemStack(MinestuckItems.heroStoneShards.get(EnumAspect.TIME), 128), true);
			Badge.findItem(player, new ItemStack(Items.CLOCK, 100), true);
			return true;
		}
		return false;
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time) {
		if(!state.equals(GodKeyStates.KeyState.HELD))
			return false;

		if(!player.isCreative() && player.getFoodStats().getFoodLevel() < 1)
		{
			player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
			return false;
		}

		BlockPos target = MinestuckUtils.getTargetBlock(player);
		if (target == null)
		{
			badgeEffects.startPowerParticles(getClass(), MinestuckParticles.ParticleType.AURA, EnumAspect.TIME, 2);
			return true;
		}

		IBlockState blockState = world.getBlockState(target);
		blockState.getBlock().updateTick(world, target, blockState, random);

		TileEntity tileEntity = world.getTileEntity(target);
		if (tileEntity instanceof ITickable)
			((ITickable)tileEntity).update();

		if (time % 20 == 0 && !player.isCreative())
			player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 1);

		badgeEffects.startPowerParticles(getClass(), MinestuckParticles.ParticleType.AURA, EnumAspect.TIME, 2);

		return true;
	}
}
