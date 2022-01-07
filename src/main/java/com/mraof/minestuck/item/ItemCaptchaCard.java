package com.mraof.minestuck.item;

import com.mraof.minestuck.captchalogue.captchalogueable.CaptchalogueableItemStack;
import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.client.model.item.ModelCaptchaCard;
import com.mraof.minestuck.util.AlchemyUtils;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemCaptchaCard extends MSItemBase
{
	
	public ItemCaptchaCard()
	{
		super("captchaCard", true);
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		if(AlchemyUtils.containsObject(stack))
			return 16;
		else return 64;
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if(this.isInCreativeTab(tab))
		{
			items.add(new ItemStack(this));
			items.add(AlchemyUtils.createCard(new CaptchalogueableItemStack(new ItemStack(MinestuckItems.cruxiteApple)), true));
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		
		NBTTagCompound nbt = playerIn.getHeldItem(handIn).getTagCompound();
		ItemStack stack = playerIn.getHeldItem(handIn);
		
		if(playerIn.isSneaking() && stack.hasTagCompound() && ((nbt.getInteger("contentSize") <= 0 && !nbt.getBoolean("punched") && AlchemyUtils.getDecodedItem(stack) != ItemStack.EMPTY) || nbt.getTag("contentID") == null || nbt.getTag("contentMeta") == null))
		{
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, new ItemStack(playerIn.getHeldItem(handIn).getItem(), playerIn.getHeldItem(handIn).getCount()));
		}
		else
			return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if(stack.hasTagCompound())
		{
			ICaptchalogueable content = AlchemyUtils.getCardContents(stack);
			tooltip.add("(" + (content == null ? net.minecraft.client.resources.I18n.format("item.captchaCard.empty") : content.getDisplayName()) + ")");

			if(AlchemyUtils.isPunchedCard(stack))
				tooltip.add("("+I18n.translateToLocal("item.captchaCard.punched")+")");
		} else
			tooltip.add("("+I18n.translateToLocal("item.captchaCard.empty")+")");
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel()
	{
		ModelLoader.setCustomMeshDefinition(this, stack -> ModelCaptchaCard.LOCATION);
		ModelBakery.registerItemVariants(this, ModelCaptchaCard.LOCATION);

		/*
		ModelLoader.registerItemVariants(captchaCard, new ResourceLocation(Minestuck.MODID, "card_empty"), new ResourceLocation(Minestuck.MODID, "card_full"), new ResourceLocation(Minestuck.MODID, "card_punched"), new ResourceLocation(Minestuck.MODID, "card_ghost"));
		ModelLoader.setCustomMeshDefinition(captchaCard, (ItemStack stack) ->
		{
			NBTTagCompound nbt = stack.getTagCompound();
			String str;
			if(nbt != null && nbt.hasKey("contentID"))
			{
				if(nbt.getBoolean("punched") && !(Item.REGISTRY.getObject(new ResourceLocation(nbt.getString("contentID"))) == Item.getItemFromBlock(genericObject)))
					str = "card_punched";
				else if(nbt.getInteger("contentSize") <= 0) str = "card_ghost";
				else str = "card_full";
			}
			else
				str = "card_empty";
			return new ModelResourceLocation(new ResourceLocation(Minestuck.MODID, str), "inventory");
		});
		*/
	}
}
