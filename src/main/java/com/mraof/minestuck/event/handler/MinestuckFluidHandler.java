package com.mraof.minestuck.event.handler;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.block.BlockFluidGrist;
import com.mraof.minestuck.block.MinestuckBlocks;
import com.mraof.minestuck.item.ItemMinestuckBucket;
import com.mraof.minestuck.item.MinestuckItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public class MinestuckFluidHandler
{
	@SubscribeEvent
	public static void onBucketFill(FillBucketEvent event)
	{

		if (event.getEmptyBucket() == null || event.getEmptyBucket().getItem() != Items.BUCKET || event.getFilledBucket() != null || event.getTarget() == null || event.getResult() == Result.DENY)
			return;

		ItemStack result = fillCustomBucket(event.getWorld(), event.getTarget());

		if (result == null)
			return;

		event.setFilledBucket(result);
		event.setResult(Result.ALLOW);
	}

	private static ItemStack fillCustomBucket(World world, RayTraceResult pos)
	{
		ItemMinestuckBucket bucket = (MinestuckItems.minestuckBucket);
		IBlockState block = world.getBlockState(pos.getBlockPos());

		//TODO add same checks as used in buckets class
		if (MinestuckBlocks.fluids.contains(block))
		{
			world.setBlockToAir(pos.getBlockPos());
			return new ItemStack(MinestuckItems.minestuckBucket, 1, MinestuckBlocks.fluids.indexOf(block));
		}
		else
			return null;

	}

	@SubscribeEvent
	public static void onCreateFluidSource(BlockEvent.CreateFluidSourceEvent event)
	{
		if (event.getState().getBlock() instanceof BlockFluidGrist)
		{
			event.setResult(Result.DENY);
		}
	}
}