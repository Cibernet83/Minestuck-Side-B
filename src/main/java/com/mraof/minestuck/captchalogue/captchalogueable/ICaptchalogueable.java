package com.mraof.minestuck.captchalogue.captchalogueable;

import com.mraof.minestuck.captchalogue.sylladex.BottomSylladex;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.GuiSylladex;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.InvocationTargetException;

public interface ICaptchalogueable
{
	void grow(ICaptchalogueable other);
	boolean isEmpty();
	boolean isCompatibleWith(ICaptchalogueable other);
	void fetch(EntityPlayer player, boolean shrinkHand);
	default void fetch(EntityPlayer player)
	{
		fetch(player, false);
	}
	void eject(BottomSylladex fromSylladex, int index, EntityPlayer player);
	void eject(EntityPlayer player);
	default boolean tryEjectCard(BottomSylladex fromSylladex, int index, EntityPlayer player)
	{
		return false;
	}
	void drop(World world, double posX, double posY, double posZ);
	default void drop(Entity entity)
	{
		drop(entity.world, entity.posX, entity.posY, entity.posZ);
	}
	ItemStack captchalogueIntoCardItem();
	default ICaptchalogueable getAlchemyComponent()
	{
		return this;
	}
	String getName();
	NBTTagCompound writeToNBT();
	@SideOnly(Side.CLIENT)
	void draw(GuiSylladex gui);
	@SideOnly(Side.CLIENT)
	String getDisplayName();
	@SideOnly(Side.CLIENT)
	ITextComponent getTextComponent();
	@SideOnly(Side.CLIENT)
	void renderTooltip(GuiSylladex gui, int x, int y);

	/**
	 * Determines what content texture is used (ghost, item, abstract, etc.)
	 */
	@SideOnly(Side.CLIENT)
	String getTextureKey();

	static NBTTagCompound writeToNBT(ICaptchalogueable object)
	{
		if (object != null)
		{
			NBTTagCompound nbt = object.writeToNBT();
			nbt.setString("class", object.getClass().getName());
			return nbt;
		}
		else
		{
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("class", "null");
			return nbt;
		}
	}

	static ICaptchalogueable readFromNBT(NBTTagCompound nbt)
	{
		String className = nbt.getString("class");
		if (!className.equals("null"))
		{
			try
			{
				return (ICaptchalogueable) Class.forName(className).getConstructor(NBTTagCompound.class).newInstance(nbt);
			}
			catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e)
			{
				throw new RuntimeException(e);
			}
			catch (ClassNotFoundException e)
			{
				return new CaptchalogueableInvalid();
			}
		}
		return null;
	}
}
