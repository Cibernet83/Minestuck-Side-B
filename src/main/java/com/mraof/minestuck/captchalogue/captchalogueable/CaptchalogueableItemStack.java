package com.mraof.minestuck.captchalogue.captchalogueable;

import com.mraof.minestuck.captchalogue.sylladex.BottomSylladex;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.GuiSylladex;
import com.mraof.minestuck.util.AlchemyUtils;
import com.mraof.minestuck.util.SylladexUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class CaptchalogueableItemStack implements ICaptchalogueable
{
	private final ItemStack stack;

	public CaptchalogueableItemStack(@Nonnull ItemStack stack)
	{
		this.stack = stack;
	}

	public CaptchalogueableItemStack(NBTTagCompound nbt)
	{
		stack = new ItemStack(nbt);
	}

	@Override
	public NBTTagCompound writeData()
	{
		return stack.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void grow(ICaptchalogueable other)
	{
		if (!isCompatibleWith(other))
			return;
		SylladexUtils.moveItemStack(((CaptchalogueableItemStack) other).getStack(), stack);
	}

	@Override
	public boolean isEmpty()
	{
		return stack.isEmpty();
	}

	@Override
	public boolean isCompatibleWith(ICaptchalogueable other)
	{
		if (!(other instanceof CaptchalogueableItemStack))
			return false;
		ItemStack otherStack = ((CaptchalogueableItemStack) other).getStack();
		return SylladexUtils.areItemStacksCompatible(stack, otherStack);
	}

	public ItemStack getStack()
	{
		return stack;
	}

	@Override
	public void fetch(EntityPlayer player)
	{
		ItemStack handStack = player.getHeldItemMainhand().copy();

		if (handStack.isEmpty())
			player.setHeldItem(EnumHand.MAIN_HAND, stack);
		else if (!player.addItemStackToInventory(stack))
			SylladexUtils.launchItem(player, stack);
	}

	@Override
	public void eject(BottomSylladex fromSylladex, int index, EntityPlayer player)
	{
		if (fromSylladex != null && AlchemyUtils.isAppendable(stack))
			while (!stack.isEmpty()) // FIXME: Make these move up a stack when popping over the 256 card limit
			{
				if (fromSylladex.autoBalanceNewCards)
					SylladexUtils.getSylladex(player).addCard(AlchemyUtils.getCardContents(stack));
				else
					fromSylladex.addCard(index, AlchemyUtils.getCardContents(stack), player);
				stack.shrink(1);
			}
		if (!stack.isEmpty())
			eject(player);
	}

	@Override
	public void eject(EntityPlayer player)
	{
		SylladexUtils.launchItem(player, stack);
	}

	@Override
	public boolean tryEjectCard(BottomSylladex fromSylladex, int index, EntityPlayer player)
	{
		assert fromSylladex != null;

		if (AlchemyUtils.isAppendable(stack))
		{
			eject(fromSylladex, index, player);
			return true;
		}
		else
			return false;
	}

	@Override
	public void drop(World world, double posX, double posY, double posZ)
	{
		EntityItem item = new EntityItem(world, posX, posY, posZ, stack);
		item.motionY = (world.rand.nextGaussian() * 0.05 + 0.2) / 2.0;
		item.setDefaultPickupDelay();
		world.spawnEntity(item);
	}

	@Override
	public ItemStack captchalogueIntoCardItem()
	{
		return AlchemyUtils.createCard(this, false);
	}

	@Override
	public String getName()
	{
		return stack.getUnlocalizedName();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void draw(GuiSylladex gui, float mouseX, float mouseY, float partialTicks)
	{
		if (!stack.isEmpty())
		{
			int x = 2;
			int y = 7;

			RenderHelper.enableGUIStandardItemLighting();
			GlStateManager.enableRescaleNormal();
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
			gui.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
			gui.itemRender.renderItemOverlayIntoGUI(gui.mc.fontRenderer, stack, x, y, stack.getCount() == 1 ? "" : String.valueOf(stack.getCount()));
			GlStateManager.disableDepth();
			RenderHelper.disableStandardItemLighting();
			GlStateManager.color(1F, 1F, 1F, 1F);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getDisplayName()
	{
		return (stack.getCount() <= 1 ? "" : (stack.getCount() + "x ")) + stack.getDisplayName();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ITextComponent getTextComponent()
	{
		return stack.getTextComponent();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderTooltip(GuiSylladex gui, int x, int y)
	{
		gui.renderToolTip(stack, x, y);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTextureKey()
	{
		return "item";
	}
}
