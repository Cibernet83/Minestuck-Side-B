package com.mraof.minestuck.item;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.network.skaianet.SburbConnection;
import com.mraof.minestuck.network.skaianet.SkaianetHandler;
import com.mraof.minestuck.util.IdentifierHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDenizenTome extends MSItemBase
{
	public ItemDenizenTome(String name)
	{
		super(name);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setMaxStackSize(1);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if (stack.getMetadata() == 1 && Minecraft.getMinecraft().player != null)
		{
			IdentifierHandler.PlayerIdentifier pid = IdentifierHandler.encode(Minecraft.getMinecraft().player);
			SburbConnection c = SkaianetHandler.getClientConnection(pid);

			if (c != null && c.getServerIdentifier().equals(pid))
			{
				tooltip.add(I18n.format("item.tomeOfTheAncients.tooltip.deadSession"));
				return;
			}
		}
		if (stack.getMetadata() < 2)
			tooltip.add(I18n.format("item.tomeOfTheAncients.tooltip"));
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{

		if (this.isInCreativeTab(tab))
		{
			items.add(new ItemStack(this));
			items.add(new ItemStack(this, 1, 2));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel()
	{
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(new ModelResourceLocation(Minestuck.MODID + ":" + "sealed_tome"), "inventory"));
		ModelLoader.setCustomModelResourceLocation(this, 1, new ModelResourceLocation(new ModelResourceLocation(Minestuck.MODID + ":" + "server_sealed_tome"), "inventory"));
		ModelLoader.setCustomModelResourceLocation(this, 2, new ModelResourceLocation(new ModelResourceLocation(Minestuck.MODID + ":" + "tome_of_the_ancients"), "inventory"));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack stack = playerIn.getHeldItem(handIn);

		if (stack.getMetadata() >= 2)
			return super.onItemRightClick(worldIn, playerIn, handIn);

		if (worldIn.isRemote)
			return new ActionResult<>(EnumActionResult.SUCCESS, stack);

		IdentifierHandler.PlayerIdentifier pid = IdentifierHandler.encode(playerIn);

		if (SkaianetHandler.getClientConnection(pid) == null)
		{
			playerIn.sendStatusMessage(new TextComponentTranslation("status.tome.reject"), true);
			return new ActionResult<>(EnumActionResult.FAIL, stack);
		}

		if (stack.getMetadata() == 1 && stack.hasTagCompound())
		{
			IdentifierHandler.PlayerIdentifier clientPid = IdentifierHandler.load(stack.getTagCompound(), "Client");

			if (clientPid == null || SkaianetHandler.getClientConnection(clientPid) == null || clientPid.equals(SkaianetHandler.getClientConnection(clientPid).getServerIdentifier()))
			{
				playerIn.sendStatusMessage(new TextComponentTranslation("status.tome.reject"), true);
				return new ActionResult<>(EnumActionResult.FAIL, stack);
			}

			if (pid.equals(SkaianetHandler.getClientConnection(clientPid).getServerIdentifier()))
			{
				stack.setItemDamage(2);
				playerIn.renderBrokenItemStack(stack);
				return new ActionResult<>(EnumActionResult.SUCCESS, stack);
			}

			if (pid.equals(clientPid))
			{
				playerIn.sendStatusMessage(new TextComponentTranslation("status.tome.server"), true);
				return new ActionResult<>(EnumActionResult.FAIL, stack);
			}

			playerIn.sendStatusMessage(new TextComponentTranslation("status.tome.reject"), true);
			return new ActionResult<>(EnumActionResult.FAIL, stack);
		}

		stack.setItemDamage(1);
		playerIn.renderBrokenItemStack(stack);

		stack.setTagCompound(new NBTTagCompound());
		pid.saveToNBT(stack.getTagCompound(), "Client");

		return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return super.getUnlocalizedName() + "." + stack.getMetadata();
	}

	@Override
	public boolean hasEffect(ItemStack stack)
	{
		return stack.getMetadata() == 2;
	}
}
