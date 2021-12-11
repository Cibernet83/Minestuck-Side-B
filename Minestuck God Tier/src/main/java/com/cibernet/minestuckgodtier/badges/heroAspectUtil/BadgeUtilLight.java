package com.cibernet.minestuckgodtier.badges.heroAspectUtil;

import com.cibernet.minestuckgodtier.blocks.MSGTBlocks;
import com.cibernet.minestuckgodtier.capabilities.GodKeyStates;
import com.cibernet.minestuckgodtier.capabilities.MSGTCapabilities;
import com.cibernet.minestuckgodtier.capabilities.api.IBadgeEffects;
import com.cibernet.minestuckgodtier.client.particles.MSGTParticles;
import com.mraof.minestuck.util.EnumAspect;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class BadgeUtilLight extends BadgeHeroAspectUtil
{
	public BadgeUtilLight()
	{
		super(EnumAspect.LIGHT, new ItemStack(Items.PRISMARINE_CRYSTALS, 200));
	}

	@Override
	public void onBadgeUnlocked(World world, EntityPlayer player) {
		super.onBadgeUnlocked(world, player);
		player.getCapability(MSGTCapabilities.BADGE_EFFECTS, null).setGlorbbing(true);
	}

	@Override
	public boolean canUse(World world, EntityPlayer player) {
		return super.canUse(world, player) && player.capabilities.allowEdit;
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if (state == GodKeyStates.KeyState.PRESS && player.isSneaking())
		{
			badgeEffects.setGlorbbing(!badgeEffects.isGlorbbing());
			player.sendStatusMessage(new TextComponentTranslation(badgeEffects.isGlorbbing() ? "status.badgeEnabled" : "status.badgeDisabled", getDisplayComponent()), true);
		}

		if(((badgeEffects.isGlorbbing() &&
				player.world.getLightFor(EnumSkyBlock.BLOCK, player.getPosition()) < 8)
				|| (!player.isSneaking() && state == GodKeyStates.KeyState.PRESS)) &&
				   player.world.getBlockState(player.getPosition()).getMaterial() == Material.AIR)
		{
			badgeEffects.startPowerParticles(getClass(), MSGTParticles.ParticleType.AURA, EnumAspect.LIGHT, 4);
			player.world.setBlockState(player.getPosition(), MSGTBlocks.glorb.getDefaultState());
			return true;
		}
		else
			return false;
	}
}
