package com.mraof.minestuck.block;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.client.gui.MinestuckGuiHandler;
import com.mraof.minestuck.item.block.ItemMiniSburbMachine;
import com.mraof.minestuck.item.block.MSItemBlock;
import com.mraof.minestuck.tileentity.TileEntityMiniCruxtruder;
import com.mraof.minestuck.tileentity.TileEntityMiniSburbMachine;
import com.mraof.minestuck.util.SpaceSaltUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMiniCruxtruder extends MSFacingBase
{
	private static final AxisAlignedBB CRUXTRUDER_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 15/16D, 1.0D);

	public BlockMiniCruxtruder()
	{
		super("miniCruxtruder");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileEntityMiniCruxtruder();
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
	{
		TileEntityMiniSburbMachine te = (TileEntityMiniSburbMachine) world.getTileEntity(pos);

		boolean b = super.removedByPlayer(state, world, pos, player, willHarvest);

		if(!world.isRemote && willHarvest && te != null)
		{
			ItemStack stack = new ItemStack(Item.getItemFromBlock(this), 1);
			if(te.color != -1)
			{	//Moved this here because it's unnecessarily hard to check the tile entity in block.getDrops(), since it has been removed by then
				stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setInteger("color", te.color);
			}
			spawnAsEntity(world, pos, stack);
		}

		return b;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return CRUXTRUDER_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
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

		BlockPos placementPos = pos.offset(placedFacing.rotateYCCW(), (placedFacing.equals(EnumFacing.SOUTH) || placedFacing.equals(EnumFacing.WEST) ? -1 : 1)).offset(placedFacing, (placedFacing.equals(EnumFacing.NORTH) || placedFacing.equals(EnumFacing.WEST) ? 2 : 0));

		boundingBox = new AxisAlignedBB(0,0,0, 3, 3, 3).offset(placementPos).offset(-d1, -d2, -d3).shrink(0.002);
		placeable = SpaceSaltUtils.canPlaceCruxtruder(stack, player, player.world, pos.offset(placedFacing.rotateY()).offset(placedFacing, 2), placedFacing, mchnPos);

		RenderGlobal.drawSelectionBoundingBox(boundingBox, placeable ? 0 : 1, placeable ? 1 : 0, 0, 0.5F);
		GlStateManager.depthMask(true);
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();

		return true;
	}

	@Override
	public MSItemBlock getItemBlock()
	{
		return new ItemMiniSburbMachine(this);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (!(tileEntity instanceof TileEntityMiniSburbMachine) || playerIn.isSneaking())
			return false;

		if(!worldIn.isRemote)
			playerIn.openGui(Minestuck.instance, MinestuckGuiHandler.GuiId.MACHINE.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
}
