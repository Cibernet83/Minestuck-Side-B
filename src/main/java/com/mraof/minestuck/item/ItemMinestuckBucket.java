package com.mraof.minestuck.item;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.block.MinestuckBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemMinestuckBucket extends ItemBucket implements IRegistryItem
{
	public ItemMinestuckBucket()
	{
		super(Blocks.AIR);
		setUnlocalizedName("minestuckBucket");
		setCreativeTab(MinestuckTabs.minestuck);
		setContainerItem(Items.BUCKET);
		setHasSubtypes(true);
		MinestuckItems.items.add(this);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack stack = playerIn.getHeldItem(handIn);
		RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, false);

		ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onBucketUse(playerIn, worldIn, stack, raytraceresult);
		if (ret != null) return ret;

		if (raytraceresult == null)
		{
			return new ActionResult(EnumActionResult.PASS, stack);
		}
		else if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK)
		{
			return new ActionResult(EnumActionResult.PASS, stack);
		}
		else
		{
			BlockPos blockpos = raytraceresult.getBlockPos();

			if (!worldIn.isBlockModifiable(playerIn, blockpos))
			{
				return new ActionResult(EnumActionResult.FAIL, stack);
			}
			else
			{
				boolean flag1 = worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos);
				BlockPos blockpos1 = flag1 && raytraceresult.sideHit == EnumFacing.UP ? blockpos : blockpos.offset(raytraceresult.sideHit);

				if (!playerIn.canPlayerEdit(blockpos1, raytraceresult.sideHit, stack))
				{
					return new ActionResult(EnumActionResult.FAIL, stack);
				}
				else if (this.tryPlaceContainedLiquid(playerIn, worldIn, blockpos1, MinestuckBlocks.fluids.get(stack.getItemDamage())))
				{
					playerIn.addStat(StatList.getObjectUseStats(this));
					return new ActionResult(EnumActionResult.SUCCESS, !playerIn.capabilities.isCreativeMode ? new ItemStack(Items.BUCKET) : stack);
				}
				else
				{
					return new ActionResult(EnumActionResult.FAIL, stack);
				}
			}
		}
	}

	/**
	 * Attempts to place the liquid contained inside the bucket.
	 */
	public boolean tryPlaceContainedLiquid(EntityPlayer player, World world, BlockPos pos, IBlockState block)
	{
		Material material = world.getBlockState(pos).getMaterial();
		boolean flag = !material.isSolid();

		if (!world.isAirBlock(pos) && !flag)
		{
			return false;
		}
		else
		{
			if (!world.isRemote && flag && !material.isLiquid())
			{
				world.destroyBlock(pos, true);
			}

			world.playSound(player, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
			world.setBlockState(pos, block, 3);

			return true;
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		return getUnlocalizedName() + "." + MinestuckBlocks.fluids.get(par1ItemStack.getItemDamage()).getBlock().getUnlocalizedName().replace("tile.", "");
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if (this.isInCreativeTab(tab))
			for (int id = 0; id < MinestuckBlocks.fluids.size(); id++)
				items.add(new ItemStack(this, 1, id));
	}

	@Override
	public void register(IForgeRegistry<Item> registry)
	{
		setRegistryName("minestuck_bucket");
		registry.register(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel()
	{
		for (int i = 0; i < MinestuckBlocks.fluids.size(); i++)
			ModelLoader.setCustomModelResourceLocation(ItemMinestuckBucket.this, i, new ModelResourceLocation(new ResourceLocation(Minestuck.MODID, "bucket_" + MinestuckBlocks.fluids.get(i).getBlock().getRegistryName().getResourcePath()), "inventory"));
	}
}