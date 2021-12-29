package com.mraof.minestuck.util;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IGodTierData;
import com.mraof.minestuck.item.ItemBoondollars;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.network.MinestuckChannelHandler;
import com.mraof.minestuck.network.MinestuckPacket;
import com.mraof.minestuck.network.PacketPlayerData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.*;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;
import java.util.List;

public class MinestuckUtils
{
    public static final int TARGET_REACH = 10;

    public static boolean compareCards(ItemStack card1, ItemStack card2, boolean ignoreStacksize)
    {
        ItemStack stack1 = AlchemyUtils.getDecodedItem(card1);
        ItemStack stack2 = AlchemyUtils.getDecodedItem(card2);
        if(!card1.isItemEqual(card2))
            return false;
        if(!card1.hasTagCompound() || !card2.hasTagCompound())
            return true;
        if(card1.getTagCompound().getBoolean("punched") != card2.getTagCompound().getBoolean("punched"))
            return false;
        if(!ignoreStacksize && stack1.getCount() != stack2.getCount())
            return false;
        if(stack1.hasTagCompound() != stack2.hasTagCompound())
            return false;
        if(stack1.hasTagCompound() && stack2.hasTagCompound())
            return stack1.getTagCompound().equals(stack2.getTagCompound());
        else return true;
    }
    
    public static void giveBoonItem(EntityPlayer reciever, int value)
    {
        if(value == 0)
            return;
        ItemStack stack = ItemBoondollars.setCount(new ItemStack(MinestuckItems.boondollars), value);
        if(!reciever.addItemStackToInventory(stack))
        {
            EntityItem entity = reciever.dropItem(stack, false);
            if(entity != null)
                entity.setNoPickupDelay();
        } else reciever.inventoryContainer.detectAndSendChanges();
    }

    public static GameType getPlayerGameType(EntityPlayer player)
    {
        if(player.world.isRemote)
            return getPlayerGameTypeClient((EntityPlayerSP) player);
        return ((EntityPlayerMP) player).interactionManager.getGameType();
    }

    @SideOnly(Side.CLIENT)
    private static GameType getPlayerGameTypeClient(EntityPlayerSP player)
    {
        NetworkPlayerInfo networkplayerinfo = Minecraft.getMinecraft().getConnection().getPlayerInfo(player.getGameProfile().getId());
        return networkplayerinfo.getGameType();
    }

    @SideOnly(Side.CLIENT)
    public static void color(int color)
    {
        GlStateManager.color(((color & 0xFF0000) >> 16) / 255f, ((color & 0x00FF00) >> 8) / 255f, (color & 0x0000FF) / 255f);
    }

    @SideOnly(Side.CLIENT)
    public static int add(int color, int addend)
    {
        return (MathHelper.clamp(((color & 0xFF0000) >> 16) + addend, 0x00, 0xFF) << 16) | (MathHelper.clamp(((color & 0x00FF00) >> 8) + addend, 0x00, 0xFF) << 8) | (MathHelper.clamp((color & 0x0000FF) + addend, 0x00, 0xFF));
    }

    @SideOnly(Side.CLIENT)
    public static int multiply(int color, float factor)
    {
        return (MathHelper.clamp((int)(((color & 0xFF0000) >> 16) * factor), 0x00, 0xFF) << 16) | (MathHelper.clamp((int)(((color & 0x00FF00) >> 8) * factor), 0x00, 0xFF) << 8) | (MathHelper.clamp((int)((color & 0x0000FF) * factor), 0x00, 0xFF));
    }

    public static Vec3d getRGBVecFromHex(int hex)
    {
        float r = (float) ((hex & 16711680) >> 16) / 255f;
        float g = (float) ((hex & 65280) >> 8) / 255f;
        float b = (float) ((hex & 255) >> 0) / 255f;

        return new Vec3d(r, g, b);
    }

    public static RayTraceResult rayTraceBlocks(Entity entity, double blockReachDistance)
    {
        Vec3d vec3d = entity.getPositionEyes(1);
        Vec3d vec3d1 = entity.getLook(1);
        Vec3d vec3d2 = vec3d.addVector(vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance);
        return entity.world.rayTraceBlocks(vec3d, vec3d2, false, false, true);
    }

    public static RayTraceResult getMouseOver(EntityPlayer player, double blockReachDistance)
    {
        return getMouseOver(player.world, player, blockReachDistance);
    }

    public static RayTraceResult getMouseOver(World world, EntityPlayer player, double blockReachDistance)
    {
        if (world != null)
        {
            Entity pointedEntity = null;
            RayTraceResult objectMouseOver = rayTraceBlocks(player, blockReachDistance);
            Vec3d eyePos = player.getPositionEyes(1);
            double blockHitDistance = blockReachDistance;

            if (objectMouseOver != null)
                blockHitDistance = objectMouseOver.hitVec.distanceTo(eyePos);

            Vec3d look = player.getLook(1.0F);
            Vec3d lookPos = eyePos.addVector(look.x * blockReachDistance, look.y * blockReachDistance, look.z * blockReachDistance);
            Vec3d hitPos = null;
            List<Entity> entities = world.getEntitiesInAABBexcluding(player, player.getEntityBoundingBox().expand(look.x * blockReachDistance, look.y * blockReachDistance, look.z * blockReachDistance).grow(1.0D, 1.0D, 1.0D), Predicates.and(EntitySelectors.NOT_SPECTATING, (Predicate<Entity>) p_apply_1_ -> p_apply_1_ != null && p_apply_1_.canBeCollidedWith()));
            double entityHitDistance = blockHitDistance;

            for (Entity entity : entities) {
                AxisAlignedBB entityAABB = entity.getEntityBoundingBox().grow((double) entity.getCollisionBorderSize());
                RayTraceResult entityResult = entityAABB.calculateIntercept(eyePos, lookPos);

                if (entityAABB.contains(eyePos)) {
                    if (entityHitDistance >= 0.0D) {
                        pointedEntity = entity;
                        hitPos = entityResult == null ? eyePos : entityResult.hitVec;
                        entityHitDistance = 0.0D;
                    }
                }
                else if (entityResult != null) {
                    double eyeToHitDistance = eyePos.distanceTo(entityResult.hitVec);

                    if (eyeToHitDistance < entityHitDistance || entityHitDistance == 0.0D) {
                        if (entity.getLowestRidingEntity() == player.getLowestRidingEntity() && !entity.canRiderInteract()) {
                            if (entityHitDistance == 0.0D) {
                                pointedEntity = entity;
                                hitPos = entityResult.hitVec;
                            }
                        } else {
                            pointedEntity = entity;
                            hitPos = entityResult.hitVec;
                            entityHitDistance = eyeToHitDistance;
                        }
                    }
                }
            }

            if (pointedEntity != null && (entityHitDistance < blockHitDistance || objectMouseOver == null))
            {
                objectMouseOver = new RayTraceResult(pointedEntity, hitPos);
            }

            return objectMouseOver;
        }
        return null;
    }

    public static EntityLivingBase getTargetEntity(EntityPlayer player)
    {
        return getTargetEntity(player, 10);
    }

    public static EntityLivingBase getTargetEntity(EntityPlayer player, double blockReachDistance)
    {
        RayTraceResult rayTraceResult = getMouseOver(player, blockReachDistance);
        return (rayTraceResult != null && rayTraceResult.entityHit instanceof EntityLivingBase) ? (EntityLivingBase) rayTraceResult.entityHit : null;
    }

    public static BlockPos getTargetBlock(EntityPlayer player)
    {
        return getTargetBlock(player, 10);
    }

    public static BlockPos getTargetBlock(EntityPlayer player, double blockReachDistance)
    {
        RayTraceResult rayTraceResult = getMouseOver(player, blockReachDistance);
        return (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) ? rayTraceResult.getBlockPos() : null;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isClientPlayer(Entity entity)
    {
        return Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().player.equals(entity);
    }

    public static boolean resetGodTier(EntityPlayer player)
    {
        if(!(player instanceof FakePlayer))
        {
            if(!player.world.isRemote)
            {
                IGodTierData data = player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null);
                data.resetBadges();
                data.resetSkills(true);
                MinestuckPlayerData.getData(player).echeladder.setProgressEnabled(true);
                data.markForReset();
                data.update();
            }
        }
        return false;
    }

    public static boolean changeTitle(EntityPlayer player, EnumClass heroClass, EnumAspect aspect)
    {
        if(!(player instanceof FakePlayer))
        {
            MinestuckPlayerData.getData(player).title = new Title(heroClass, aspect);
            MinestuckChannelHandler.sendToPlayer(MinestuckPacket.makePacket(MinestuckPacket.Type.PLAYER_DATA, PacketPlayerData.TITLE, heroClass, aspect), player);
            IGodTierData data = player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null);
            if(!data.hasMasterControl())
                data.resetTitleBadges(true);
            return true;
        }
        return false;
    }

    public static void onResetGodTier(EntityPlayer player)
    {
        player.capabilities.allowFlying = player.isCreative() || player.isSpectator();
        if(!player.capabilities.allowFlying)
            player.capabilities.isFlying = false;
    }

	public static boolean isPointInRegion(int regionX, int regionY, int regionWidth, int regionHeight, int pointX, int pointY)
	{
		return pointX >= regionX && pointX < regionX + regionWidth && pointY >= regionY && pointY < regionY + regionHeight;
	}
	
	public static <T> Iterable<T> reverse(LinkedList<T> list)
    {
        return list::descendingIterator;
    }
}
