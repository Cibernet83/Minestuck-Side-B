package com.mraof.minestuck.badges.heroAspectUtil;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.client.particles.MSGTParticles;
import com.mraof.minestuck.item.ItemManipulatedMatter;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.MSGTUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BadgeUtilSpace extends BadgeHeroAspectUtil
{
	public BadgeUtilSpace()
	{
		super(EnumAspect.SPACE, new ItemStack(MinestuckItems.spaceSalt, 200));
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if (state == GodKeyStates.KeyState.NONE)
			return false;

		BlockPos pos1 = badgeEffects.getManipulatedPos1(), pos2 = badgeEffects.getManipulatedPos2();
		if (state == GodKeyStates.KeyState.RELEASED)
		{
			if (time < 20 || pos1 == null || pos2 == null)
			{
				BlockPos pos = MSGTUtils.getTargetBlock(player);

				if(pos == null || player.isSneaking())
				{
					badgeEffects.setManipulatedPos1(null, player.dimension);
					badgeEffects.setManipulatedPos2(null, player.dimension);
					player.sendStatusMessage(new TextComponentTranslation("item.manipulatedMatter.posReset"), true);
				}
				else
				{
					boolean manipulatingPos2 = badgeEffects.isManipulatingPos2() ;//&& badgeEffects.getManipulatedPos1Dim() == badgeEffects.getManipulatedPos2Dim();

					if (manipulatingPos2)
						badgeEffects.setManipulatedPos2(pos, player.dimension);
					else
						badgeEffects.setManipulatedPos1(pos, player.dimension);
					player.sendStatusMessage(new TextComponentTranslation("item.manipulatedMatter.posSet" + (badgeEffects.isManipulatingPos2() ? "A" : "B"), pos.getX() + ", " + pos.getY() + ", " + pos.getZ()), true);
				}
			}
			else if (player.capabilities.allowEdit)
			{
				if (badgeEffects.getManipulatedPos1Dim() != badgeEffects.getManipulatedPos2Dim() ||
						Math.abs(pos1.getX() - pos2.getX()) >= 8 ||
						Math.abs(pos1.getY() - pos2.getY()) >= 8 ||
						Math.abs(pos1.getZ() - pos2.getZ()) >= 8)
					player.sendStatusMessage(new TextComponentTranslation("item.manipulatedMatter.tooBig"), true);
				else
				{
					int energyRequired = (int) (((Math.abs(pos1.getX() - pos2.getX()))*(Math.abs(pos1.getY() - pos2.getY()))*(Math.abs(pos1.getZ() - pos2.getZ())))/512f * 18);

					if(!player.isCreative())
					{
						if(player.getFoodStats().getFoodLevel() < energyRequired)
						{
							player.sendStatusMessage(new TextComponentTranslation("status.tooExhausted"), true);
							return false;
						}
						player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel()-energyRequired);
					}

					ItemStack manipulatedMatter = new ItemStack(MinestuckItems.manipulatedMatter);
					ItemManipulatedMatter.storeStructure(manipulatedMatter, player, world, badgeEffects.getManipulatedPos1(), badgeEffects.getManipulatedPos2());
					if (!player.addItemStackToInventory(manipulatedMatter))
						player.dropItem(manipulatedMatter, true, false);
				}
			}
			else
				player.sendStatusMessage(new TextComponentTranslation("item.manipulatedMatter.cantEdit"), true);

			if (time >= 20)
				badgeEffects.startPowerParticles(BadgeUtilSpace.class, MSGTParticles.ParticleType.BURST, EnumAspect.SPACE, 10);
			else badgeEffects.startPowerParticles(BadgeUtilSpace.class, MSGTParticles.ParticleType.AURA, EnumAspect.SPACE, 5);
		}
		else badgeEffects.startPowerParticles(BadgeUtilSpace.class, MSGTParticles.ParticleType.AURA, EnumAspect.SPACE, (time >= 20 && pos1 != null && pos2 != null) ? 6 : 1);

		return true;
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void renderOutline(RenderWorldLastEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();

		if (mc.player != null && mc.getRenderViewEntity() == mc.player)
		{
			RayTraceResult rayTraceResult = mc.objectMouseOver;

			EntityPlayerSP player = mc.player;
			IBadgeEffects cap = player.getCapability(MinestuckCapabilities.BADGE_EFFECTS, null);
			float partialTicks = event.getPartialTicks();
			double d1 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)partialTicks;
			double d2 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)partialTicks;
			double d3 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)partialTicks;

			if (player.getHeldItemMainhand().getItem().equals(MinestuckItems.manipulatedMatter) && rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK)
			{
				ItemStack stack = player.getHeldItemMainhand();

				if(stack.isEmpty())
					stack = player.getHeldItemOffhand();

				BlockPos pos = rayTraceResult.getBlockPos().offset(rayTraceResult.sideHit);

				if(stack.hasTagCompound())
				{
					NBTTagCompound nbt = stack.getTagCompound();
					int w = nbt.getInteger("width")+1, h = nbt.getInteger("height")+1, d = nbt.getInteger("depth")+1;

					AxisAlignedBB boundingBox = new AxisAlignedBB(0,0,0, w, h, d).offset(ItemManipulatedMatter.getPlacementPos(player, player.world, pos, stack));
					AxisAlignedBB boundingBoxRelative = boundingBox.offset(-d1, -d2, -d3).shrink(0.002);

					GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
					GlStateManager.glLineWidth(2.0F);
					GlStateManager.disableTexture2D();
					GlStateManager.depthMask(false);

					if(player.world.checkNoEntityCollision(boundingBox))
						RenderGlobal.drawSelectionBoundingBox(boundingBoxRelative, 0, 1, 0.5f, 0.5F);
					else RenderGlobal.drawSelectionBoundingBox(boundingBoxRelative, 1, 0.2f, 0, 0.5F);

					GlStateManager.depthMask(true);
					GlStateManager.enableTexture2D();
					GlStateManager.disableBlend();

				}
			}
			if (cap.getManipulatedPos1() != null)
			{
				BlockPos posA = cap.getManipulatedPos1();
				BlockPos posB = cap.getManipulatedPos2();
				int dimB = cap.getManipulatedPos2Dim();

				boolean lockedIn = posB != null;

				if(posB == null && rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK)
				{
					posB = rayTraceResult.getBlockPos();
					dimB = player.dimension;
				}

				if(posB != null && cap.getManipulatedPos1Dim() == dimB)
				{
					AxisAlignedBB boundingBox = new AxisAlignedBB(Math.min(posA.getX(), posB.getX()), Math.min(posA.getY(), posB.getY()), Math.min(posA.getZ(), posB.getZ()),
							Math.max(posA.getX(), posB.getX())+1, Math.max(posA.getY(), posB.getY())+1, Math.max(posA.getZ(), posB.getZ())+1).offset(-d1, -d2, -d3).grow(0.002);
					boolean canSet = Math.abs(posA.getX() - posB.getX()) < 8 &&
									 Math.abs(posA.getY() - posB.getY()) < 8 &&
									 Math.abs(posA.getZ() - posB.getZ()) < 8;

					GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
					GlStateManager.glLineWidth(2.0F);
					GlStateManager.disableTexture2D();
					GlStateManager.depthMask(false);

					RenderGlobal.drawSelectionBoundingBox(boundingBox, !canSet ? lockedIn ? 1 : 0.8f : 0, canSet ? lockedIn ? 1 : 0.8f : 0, 0, 0.5F);

					GlStateManager.depthMask(true);
					GlStateManager.enableTexture2D();
					GlStateManager.disableBlend();
				}

			}
		}
	}

	public static class PosA {}
	public static class PosB {}
}
