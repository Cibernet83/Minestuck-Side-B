package com.mraof.minestuck.item;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.block.BlockLargeMachine;
import com.mraof.minestuck.captchalogue.captchalogueable.CaptchalogueableEntity;
import com.mraof.minestuck.captchalogue.captchalogueable.CaptchalogueableGhost;
import com.mraof.minestuck.captchalogue.captchalogueable.CaptchalogueableItemStack;
import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.captchalogue.modus.MinestuckModi;
import com.mraof.minestuck.captchalogue.modus.Modus;
import com.mraof.minestuck.event.handler.CommonEventHandler;
import com.mraof.minestuck.util.AlchemyUtils;
import com.mraof.minestuck.world.MinestuckDimensionHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public class ItemCaptcharoidCamera extends MSItemBase {

	public ItemCaptcharoidCamera()
	{
		super("captcharoidCamera");
		this.maxStackSize = 1;
		this.setMaxDamage(64);
	}

	@Override
	protected boolean isInCreativeTab(CreativeTabs targetTab)
	{
		return targetTab == CreativeTabs.SEARCH || targetTab == MinestuckTabs.minestuck;
	}
	
	@Override
	public CreativeTabs[] getCreativeTabs()
	{
		return new CreativeTabs[] {MinestuckTabs.minestuck};
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D()
	{
		return true;
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		//pos.offset(facing).offset(facing.rotateY()).up(), pos.offset(facing.getOpposite()).offset(facing.rotateYCCW()).down()
		if(!worldIn.isRemote) 
		{
			{
				IBlockState state = worldIn.getBlockState(pos);

				if(ItemGhost.containsKey(state.getBlock()))
				{
					ItemStack stack = new ItemStack(ItemGhost.get(state.getBlock()));
					if(!player.inventory.addItemStackToInventory(createCard(new CaptchalogueableItemStack(stack))))
						player.dropItem(stack, false);
					player.getHeldItem(hand).damageItem(1, player);
				}
				else
				{
					ItemStack block = new ItemStack(Item.getItemFromBlock(state.getBlock()));
					int meta = state.getBlock().damageDropped(state);
					block.setItemDamage(meta);

					if(worldIn.getBlockState(pos).getBlock() instanceof BlockLargeMachine)
						block = new ItemStack(((BlockLargeMachine) worldIn.getBlockState(pos).getBlock()).getItemFromMachine());

					if(!player.inventory.addItemStackToInventory(createCard(new CaptchalogueableItemStack(block))))
						player.dropItem(block, false);
					player.getHeldItem(hand).damageItem(1, player);
				}
			}
			return EnumActionResult.SUCCESS;
		}
		
		return EnumActionResult.SUCCESS;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn)
	{

		World world = player.world;

		ItemStack camera = player.getHeldItem(handIn);
		ItemStack stack = ItemStack.EMPTY;

		if(MinestuckDimensionHandler.isLandDimension(world.provider.getDimension()) && player.rotationPitch < -75)
			stack = new ItemStack(MinestuckItems.skaia);
		else if(world.provider.getDimension() == 0)
		{
			Vec3d playerPosVec = new Vec3d(player.posX, player.posY, player.posZ);
			Vec3d playerLookVec = player.getLookVec();

			RayTraceResult rayTrace = world.rayTraceBlocks(playerPosVec, playerPosVec.add(playerLookVec.scale(200)), true, true, false);

			if(rayTrace != null)
				super.onItemRightClick(worldIn, player, handIn);

			for(Entity weatherEffect : world.weatherEffects)
				if(weatherEffect instanceof EntityLightningBolt)
					stack = new ItemStack(MinestuckItems.lightning);

			if(stack.isEmpty() && !world.isThundering())
			{
				playerLookVec = CommonEventHandler.getVecFromRotation(-player.rotationPitch, player.rotationYaw);
				float celestialAngle = (world.getCelestialAngle(0)*360f + 90f) % 360f;
				Vec3d sunVec = CommonEventHandler.getVecFromRotation(celestialAngle, -90);
				Vec3d moonVec = CommonEventHandler.getVecFromRotation((celestialAngle+180f) % 360f, -90);

				if(playerLookVec.squareDistanceTo(sunVec) < 0.07f)
					stack = new ItemStack(MinestuckItems.sun);
				else if(playerLookVec.distanceTo(moonVec) < 0.07f)
					stack = new ItemStack(MinestuckItems.moon);
			}
		}

		if(!player.inventory.addItemStackToInventory(createCard(new CaptchalogueableItemStack(stack))))
			player.dropItem(stack, false);
		camera.damageItem(1, player);
		return ActionResult.newResult(EnumActionResult.SUCCESS, camera);
	}

	@SubscribeEvent
	public static void onEntityInteract(PlayerInteractEvent.EntityInteract event)
	{
		if(!(event.getEntityPlayer().getHeldItem(event.getHand()).getItem() instanceof ItemCaptcharoidCamera) || event.getEntityPlayer().isSneaking())
			return;

		EntityPlayer player = event.getEntityPlayer();
		ICaptchalogueable item;

		if(event.getTarget() instanceof EntityItemFrame)
		{
			ItemStack stack = ((EntityItemFrame)event.getTarget()).getDisplayedItem();
			if(stack.isEmpty()) stack = new ItemStack(Items.ITEM_FRAME);
			item = new CaptchalogueableItemStack(stack);

		}
		else if(event.getTarget() instanceof EntityItem) //probably never going to happen but might as well
			item = new CaptchalogueableItemStack(((EntityItem) event.getTarget()).getItem());
		else item = new CaptchalogueableEntity(event.getTarget());

		ItemStack card = createCard(item);
		if(!player.inventory.addItemStackToInventory(card))
			player.dropItem(card, false);
		player.getHeldItem(event.getHand()).damageItem(1, player);
		event.setCancellationResult(EnumActionResult.SUCCESS);
		event.setCanceled(true);
	}

	@Nonnull
	public static ItemStack createCard(ICaptchalogueable item)
	{
		return AlchemyUtils.setCardModi(AlchemyUtils.createCard(new CaptchalogueableGhost(item), false), new Modus[]{ MinestuckModi.hashtable, MinestuckModi.queue });
	}
}
