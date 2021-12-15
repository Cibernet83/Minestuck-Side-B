package com.mraof.minestuck.block;

import com.mraof.minestuck.tileentity.TileEntityMiniTotemLathe;
import com.mraof.minestuck.util.SpaceSaltUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMiniTotemLathe extends BlockSburbMachine
{
	private static final AxisAlignedBB[] TOTEM_LATHE_AABB = {new AxisAlignedBB(0.0D, 0.0D, 5/16D, 1.0D, 1.0D, 11/16D), new AxisAlignedBB(5/16D, 0.0D, 0.0D, 11/16D, 1.0D, 1.0D)};

	public BlockMiniTotemLathe()
	{
		super("miniTotemLathe");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileEntityMiniTotemLathe();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return TOTEM_LATHE_AABB[state.getValue(FACING).getHorizontalIndex()];
	}

	@Override
	public boolean renderCheckItem(EntityPlayerSP player, ItemStack stack, RenderGlobal render, RayTraceResult rayTraceResult, float partialTicks, EnumFacing placedFacing)
	{
		BlockPos pos = rayTraceResult.getBlockPos();

		Block block = player.world.getBlockState(pos).getBlock();
		boolean flag = block.isReplaceable(player.world, pos);

		//if (!flag)
		//	pos = pos.up();

		//EnumFacing placedFacing = player.getHorizontalFacing().getOpposite();
		double hitX = rayTraceResult.hitVec.x - pos.getX(), hitZ = rayTraceResult.hitVec.z - pos.getZ();
		boolean r = placedFacing.getAxis() == EnumFacing.Axis.Z;
		boolean f = placedFacing== EnumFacing.NORTH || placedFacing==EnumFacing.EAST;
		double d1 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)partialTicks;
		double d2 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)partialTicks;
		double d3 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)partialTicks;

		boolean placeable;
		AxisAlignedBB boundingBox;

		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.glLineWidth(2.0F);
		GlStateManager.disableTexture2D();
		GlStateManager.depthMask(false);	//GL stuff was copied from the standard mouseover bounding box drawing, which is likely why the alpha isn't working
		BlockPos mchnPos = pos;

		pos = pos.offset(placedFacing.rotateY());

		if (placedFacing.getFrontOffsetX() > 0 && hitZ >= 0.5F || placedFacing.getFrontOffsetX() < 0 && hitZ < 0.5F
					|| placedFacing.getFrontOffsetZ() > 0 && hitX < 0.5F || placedFacing.getFrontOffsetZ() < 0 && hitX >= 0.5F)
			pos = pos.offset(placedFacing.rotateY());

		BlockPos placementPos = pos;
		if (placedFacing == EnumFacing.EAST || placedFacing == EnumFacing.NORTH)
			pos = pos.offset(placedFacing.rotateYCCW(), 3);    //The bounding box is symmetrical, so doing this gets rid of some rendering cases

		boundingBox = new AxisAlignedBB(0, 0, 0, (r ? 4 : 1), 3, (r ? 1 : 4)).offset(pos).offset(-d1, -d2, -d3).shrink(0.002);
		placeable = SpaceSaltUtils.canPlaceTotemLathe(stack, player, player.world, placementPos, placedFacing, mchnPos);

		RenderGlobal.drawSelectionBoundingBox(boundingBox, placeable ? 0 : 1, placeable ? 1 : 0, 0, 0.5F);
		GlStateManager.depthMask(true);
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();

		return true;
	}
}
