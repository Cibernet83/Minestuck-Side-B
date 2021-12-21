package com.mraof.minestuck.badges.heroAspect;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.client.particles.MinestuckParticles;
import com.mraof.minestuck.network.MinestuckChannelHandler;
import com.mraof.minestuck.network.MinestuckPacket;
import com.mraof.minestuck.potions.MinestuckPotions;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.EnumRole;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.GetCollisionBoxesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public class BadgeActiveVoid extends BadgeHeroAspect
{
    public BadgeActiveVoid()
    {
        super(EnumAspect.VOID, EnumRole.ACTIVE, EnumAspect.BREATH);
    }

    @Override
    public void onBadgeUnlocked(World world, EntityPlayer player) {
        super.onBadgeUnlocked(world, player);
        player.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).setVoidstepping(true);
    }

    @Override
    public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
    {
        if (state == GodKeyStates.KeyState.PRESS)
        {
            badgeEffects.setVoidstepping(!badgeEffects.isVoidstepping());
            player.sendStatusMessage(new TextComponentTranslation(badgeEffects.isVoidstepping() ? "status.badgeEnabled" : "status.badgeDisabled", getDisplayComponent()), true);
        }

        if(!(badgeEffects.isVoidstepping() && (player.capabilities.isFlying || badgeEffects.isDoingWimdyThing())))
            return false;

        if(!player.isCreative() && player.ticksExisted % 40 == 1)
        {
            player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel()-1);
            if(player.getFoodStats().getFoodLevel() < 1)
            {
                player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
                return false;
            }
        }

        if(!player.isPotionActive(MinestuckPotions.VOID_CONCEAL))
            MinestuckChannelHandler.sendToTrackingAndSelf(MinestuckPacket.makePacket(MinestuckPacket.Type.SEND_PARTICLE, MinestuckParticles.ParticleType.AURA, (player.ticksExisted % 2) == 0 ? 0x104EA2 : 0x001856, 1, player), player);

        return true;
    }

    @Override
    public boolean canUse(World world, EntityPlayer player) {
        return player.getFoodStats().getFoodLevel() > 0 && super.canUse(world, player);
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event)
    {
        if (!(event.getEntity() instanceof EntityPlayer) || event.getEntity().world.isRemote)
            return;

        EntityPlayer player = (EntityPlayer) event.getEntityLiving();
        player.noClip = player.noClip || (player.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).isVoidstepping() &&
                                                  (player.capabilities.isFlying || player.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).isDoingWimdyThing()));
    }

    @SubscribeEvent
    public static void onPlayerCollision(GetCollisionBoxesEvent event)
    {
        if (!(event.getEntity() instanceof EntityPlayer))
            return;

        if(event.getEntity().getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).isVoidstepping()
                   && (((EntityPlayer) event.getEntity()).capabilities.isFlying || event.getEntity().getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).isDoingWimdyThing()))
            event.getCollisionBoxesList().clear();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    @SideOnly(Side.CLIENT)
    public static void onPlayerPushOutOfBlocks(PlayerSPPushOutOfBlocksEvent event)
    {
        if(event.getEntity() != null && event.getEntityPlayer().isUser() && event.getEntity().getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).isVoidstepping())
            event.setCanceled(true);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void renderBlockOverlay(RenderBlockOverlayEvent event)
    {
        if(Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().player.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).isVoidstepping())
            event.setCanceled(true);
    }
}
