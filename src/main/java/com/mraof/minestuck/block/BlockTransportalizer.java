package com.mraof.minestuck.block;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.client.gui.MinestuckGuiHandler;
import com.mraof.minestuck.tileentity.TileEntityTransportalizer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockTransportalizer extends MSBlockContainer
{
	protected static final AxisAlignedBB TRANSPORTALIZER_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1/2D, 1.0D);

	public BlockTransportalizer()
	{
		super("transportalizer", Material.IRON);
		setHardness(3.0F);
		setHarvestLevel("pickaxe", 0);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return TRANSPORTALIZER_AABB;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		TileEntityTransportalizer tileEntity = new TileEntityTransportalizer();
		return tileEntity;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
	{
		if (!world.isRemote && entity.getRidingEntity() == null && entity.getPassengers().isEmpty() && !world.isRemote)
		{
			if(entity.timeUntilPortal == 0)
				((TileEntityTransportalizer) world.getTileEntity(pos)).teleport(entity);
			else entity.timeUntilPortal = entity.getPortalCooldown();
		}
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		TileEntityTransportalizer tileEntity = (TileEntityTransportalizer) worldIn.getTileEntity(pos);

		if (tileEntity == null || playerIn.isSneaking())
		{
			return false;
		}

		if(worldIn.isRemote)
			playerIn.openGui(Minestuck.instance, MinestuckGuiHandler.GuiId.TRANSPORTALIZER.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());

		return true;
	}
	
	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
	{
		player.addStat(StatList.getBlockStats(this));
		player.addExhaustion(0.005F);
		
		if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0)
		{
			java.util.List<ItemStack> items = new java.util.ArrayList<ItemStack>();
			ItemStack itemstack = this.getSilkTouchDrop(state);
			
			if (!itemstack.isEmpty())
			{
				if(te instanceof TileEntityTransportalizer)
					itemstack.setStackDisplayName(((TileEntityTransportalizer) te).getId());
				
				items.add(itemstack);
			}
			
			net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, 0, 1.0f, true, player);
			for (ItemStack item : items)
			{
				spawnAsEntity(worldIn, pos, item);
			}
		}
		else
		{
			harvesters.set(player);
			int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
			this.dropBlockAsItem(worldIn, pos, state, i);
			harvesters.set(null);
		}
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return BlockFaceShape.UNDEFINED;
	}
}
