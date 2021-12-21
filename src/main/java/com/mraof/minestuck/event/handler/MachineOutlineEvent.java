package com.mraof.minestuck.event.handler;

import com.mraof.minestuck.badges.MinestuckBadges;
import com.mraof.minestuck.block.BlockSburbMachine;
import com.mraof.minestuck.block.MSFacingBase;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Deprecated
public class MachineOutlineEvent
{
	@SubscribeEvent
	public static void renderWorld(RenderWorldLastEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();

		if (mc.player != null && mc.getRenderViewEntity() == mc.player)
		{
			RayTraceResult rayTraceResult = mc.objectMouseOver;
			if (rayTraceResult == null || rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK)
				return;


			IBlockState state = mc.player.getEntityWorld().getBlockState(mc.objectMouseOver.getBlockPos());
			if (mc.player.isSneaking() && mc.player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).isBadgeActive(MinestuckBadges.BADGE_UTIL_SPACE) && state.getBlock() instanceof MSFacingBase)
			{
				EnumFacing facing = state.getValue(MSFacingBase.FACING);
				//facing = mc.player.getHorizontalFacing().getOpposite();
				ItemStack stack = mc.player.getHeldItemMainhand();

				((MSFacingBase)state.getBlock()).renderCheckItem(mc.player, stack, event.getContext(), rayTraceResult, event.getPartialTicks(), facing);
			}
		}
	}
}
