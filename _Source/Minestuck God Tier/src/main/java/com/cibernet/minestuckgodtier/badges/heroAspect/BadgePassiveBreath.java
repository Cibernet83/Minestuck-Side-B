package com.cibernet.minestuckgodtier.badges.heroAspect;

import com.cibernet.minestuckgodtier.capabilities.GodKeyStates;
import com.cibernet.minestuckgodtier.capabilities.api.IBadgeEffects;
import com.cibernet.minestuckgodtier.client.particles.MSGTParticles;
import com.cibernet.minestuckgodtier.util.EnumRole;
import com.mraof.minestuck.util.EnumAspect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BadgePassiveBreath extends BadgeHeroAspect
{
    public BadgePassiveBreath()
    {
        super(EnumAspect.BREATH, EnumRole.PASSIVE, EnumAspect.RAGE);
    }

    protected static final int RADIUS = 16;

    @Override
    public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
    {
        if(state == GodKeyStates.KeyState.NONE || time >= 19)
            return false;

        if(!player.isCreative() && player.getFoodStats().getFoodLevel() < 4)
        {
            player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
            return false;
        }

        if(time > 15)
            badgeEffects.startPowerParticles(getClass(), MSGTParticles.ParticleType.BURST, EnumAspect.BREATH, 20);
        else
            badgeEffects.startPowerParticles(getClass(), MSGTParticles.ParticleType.AURA, EnumAspect.BREATH, 10);


        if (time % 3 == 0 && !player.isCreative())
            player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 1);

        if(time >= 18)
        {
            player.setAir(300);
            for(Entity target : world.getEntitiesWithinAABB(Entity.class, player.getEntityBoundingBox().grow(RADIUS), (entity) -> entity != player))
            {
                float strength = 3;
                Vec3d vec = new Vec3d(player.posX-target.posX, player.posY-target.posY, player.posZ-target.posZ).normalize();

                target.velocityChanged = true;
                if(target instanceof EntityLivingBase)
                {
                    ((EntityLivingBase) target).knockBack(player, strength, vec.x, vec.z);

                    target.setAir(0);
                }
                else
                {
                    strength *= 0.3f;
                    target.isAirBorne = true;
                    float f = MathHelper.sqrt(vec.x * vec.x + vec.z * vec.z);
                    target.motionX /= 2.0D;
                    target.motionZ /= 2.0D;
                    target.motionX -= vec.x / (double)f * (double)strength;
                    target.motionZ -= vec.z / (double)f * (double)strength;

                    if (target.onGround)
                    {
                        target.motionY /= 2.0D;
                        target.motionY += (double)strength;
                        if (target.motionY > 0.4000000059604645D)
                            target.motionY = 0.4000000059604645D;
                    }
                }

            }
        }

        return true;
    }
}
