package com.mraof.minestuck.captchalogue.captchalogueable;

import com.mraof.minestuck.client.gui.captchalogue.sylladex.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.GuiSylladex;
import com.mraof.minestuck.util.SylladexUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;

public class CaptchalogueableHashStack implements ICaptchalogueable
{
	private final LinkedList<ICaptchalogueable> stacks = new LinkedList<>();

	public CaptchalogueableHashStack(ICaptchalogueable stack)
	{
		this.stacks.push(stack);
	}

	public CaptchalogueableHashStack(NBTTagCompound nbt)
	{
		for (NBTBase stackTag : nbt.getTagList("Stacks", 10))
			stacks.add(ICaptchalogueable.readFromNBT((NBTTagCompound)stackTag));
	}

	@Override
	public NBTTagCompound writeData()
	{
		NBTTagCompound nbt = new NBTTagCompound();

		NBTTagList stackTags = new NBTTagList();
		for (ICaptchalogueable stack : stacks)
			stackTags.appendTag(stack.writeData());
		nbt.setTag("Stacks", stackTags);

		return nbt;
	}

	@Override
	public void grow(ICaptchalogueable other)
	{
		if (!isCompatibleWith(other))
			return;
		stacks.getLast().grow(other);
		if (!other.isEmpty())
			stacks.add(ICaptchalogueable.readFromNBT(other.writeData()));
		other.empty();
	}

	@Override
	public boolean isEmpty()
	{
		return stacks.isEmpty();
	}

	@Override
	public void empty()
	{
		stacks.clear();
	}

	@Override
	public boolean isCompatibleWith(ICaptchalogueable other)
	{
		return stacks.getLast().isLooselyCompatibleWith(other);
	}

	@Override
	public boolean isLooselyCompatibleWith(ICaptchalogueable other)
	{
		return stacks.getLast().isLooselyCompatibleWith(other);
	}

	@Override
	public void fetch(EntityPlayerMP player)
	{
		stacks.removeLast().fetch(player);
		if (!isEmpty())
			SylladexUtils.captchalogue(this, player);
	}

	@Override
	public void eject(EntityPlayerMP player)
	{
		for (ICaptchalogueable stack : stacks)
			stack.eject(player);
		stacks.clear();
	}

	@Override
	public void drop(World world, double posX, double posY, double posZ)
	{
		for (ICaptchalogueable stack : stacks)
			stack.drop(world, posX, posY, posZ);
		stacks.clear();
	}

	@Override
	public String getName()
	{
		return "hashstack with basis of " + stacks.getFirst().getName();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void draw(GuiSylladex gui, CardGuiContainer card, float mouseX, float mouseY, float partialTicks)
	{
		GlStateManager.translate(stacks.size() * 2, stacks.size() * 2, 0);
		for (ICaptchalogueable stack : stacks)
		{
			CardGuiContainer.drawCard(gui, card.getTextureIndices());
			GlStateManager.translate(-2, -2, 0);
		}
		stacks.getLast().draw(gui, card, mouseX, mouseY, partialTicks);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getDisplayName()
	{
		return "~" + stacks.getLast().getDisplayName() + "...";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ITextComponent getTextComponent()
	{
		return new TextComponentString("~").appendSibling(stacks.getLast().getTextComponent());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderTooltip(GuiSylladex gui, int x, int y)
	{
		stacks.getLast().renderTooltip(gui, x, y);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTextureKey()
	{
		return stacks.getLast().getTextureKey();
	}
}
