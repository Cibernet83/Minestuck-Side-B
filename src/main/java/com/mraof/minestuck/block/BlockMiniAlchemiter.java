package com.mraof.minestuck.block;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.client.gui.MSGuiHandler;
import com.mraof.minestuck.tileentity.TileEntityMiniAlchemiter;
import com.mraof.minestuck.tileentity.TileEntityMiniSburbMachine;
import com.mraof.minestuck.util.IdentifierHandler;
import com.mraof.minestuck.util.SpaceSaltUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockMiniAlchemiter extends BlockSburbMachine
{
	private static final AxisAlignedBB ALCHMITER_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1/2D, 1.0D);
	private static final AxisAlignedBB[] ALCHEMITER_POLE_AABB = {new AxisAlignedBB(0.0D, 2/16D, 0.0D, 4.5/16D, 1.0D, 1/8D), new AxisAlignedBB(7/8D, 2/16D, 0.0D, 1.0D, 1.0D, 4.5/16D), new AxisAlignedBB(11.5/16D, 2/16D, 7/8D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 2/16D, 11.5/16D, 1/8D, 1.0D, 1.0D)};

	public BlockMiniAlchemiter()
	{
		 super("miniAlchemiter");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileEntityMiniAlchemiter();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (!(tileEntity instanceof TileEntityMiniSburbMachine) || playerIn.isSneaking())
			return false;

		if(!worldIn.isRemote)
		{
			((TileEntityMiniSburbMachine) tileEntity).owner = IdentifierHandler.encode(playerIn);
			playerIn.openGui(Minestuck.instance, MSGuiHandler.GuiId.MACHINE.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return ALCHMITER_AABB;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_)
	{
		super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, p_185477_7_);
		AxisAlignedBB bb = ALCHEMITER_POLE_AABB[getActualState(state, worldIn, pos).getValue(FACING).getHorizontalIndex()].offset(pos);
		if(entityBox.intersects(bb))
			collidingBoxes.add(bb);
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
			pos = pos.offset(placedFacing.rotateYCCW(), 3);
		if(placedFacing == EnumFacing.EAST || placedFacing == EnumFacing.SOUTH)
			pos = pos.offset(placedFacing.getOpposite(), 3);

		boundingBox = new AxisAlignedBB(0, 0, 0, 4, 4, 4).offset(pos).offset(-d1, -d2, -d3).shrink(0.002);
		placeable = SpaceSaltUtils.canPlaceAlchemiter(stack, player, player.world, placementPos, placedFacing, mchnPos);

		BlockPos rodOff = new BlockPos(0,0,0).offset(placedFacing, -3);

		//If you don't want the extra details to the alchemiter outline, comment out the following two lines
		RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(1F/4F + rodOff.getX(),1,1F/4F + rodOff.getZ(), 3F/4F + rodOff.getX(), 4, 3F/4F + rodOff.getZ()).offset(placementPos).offset(-d1, -d2, -d3).shrink(0.002), placeable ? 0 : 1, placeable ? 1 : 0, 0, 0.5F);
		RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(0,0,0, 4, 1, 4).offset(pos).offset(-d1, -d2, -d3).shrink(0.002), placeable ? 0 : 1, placeable ? 1 : 0, 0, 0.5F);

		RenderGlobal.drawSelectionBoundingBox(boundingBox, placeable ? 0 : 1, placeable ? 1 : 0, 0, 0.5F);
		GlStateManager.depthMask(true);
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();

		return true;
	}
}
