package com.mraof.minestuck.item;

import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.block.MinestuckBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemChisel extends MSItemBase
{
	public ItemChisel(String materialName, int durabillity)
	{
		super(materialName + "Chisel");
		this.setMaxDamage(durabillity);
		this.setMaxStackSize(1);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{

		if (worldIn.getBlockState(pos).getBlock().equals(MinestuckBlocks.zillyStone))
		{
			if (!worldIn.isRemote)
			{
				float luck = (player.getLuck() + MinestuckConfig.baseZillystoneLuck) * (float) MinestuckConfig.zillystoneYields;
				float rand = itemRand.nextFloat() * luck;
				float val = Math.round(1 + (rand));

				InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(MinestuckItems.zillystoneShard, (int) val));
				player.getHeldItem(hand).damageItem(1, player);
			}
			return EnumActionResult.SUCCESS;
		}
		else return EnumActionResult.PASS;
	}
}
