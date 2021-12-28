package com.mraof.minestuck.captchalogue.captchalogueable;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.captchalogue.sylladex.BottomSylladex;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.GuiSylladex;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public interface ICaptchalogueable
{
	HashMap<ResourceLocation, Class<? extends ICaptchalogueable>> REGISTRY = new HashMap<ResourceLocation, Class<? extends ICaptchalogueable>>()
	{{
		put(new ResourceLocation(Minestuck.MODID, "itemstack"), CaptchalogueableItemStack.class);
		put(new ResourceLocation(Minestuck.MODID, "ghost"), CaptchalogueableGhost.class);
	}};

	void grow(ICaptchalogueable other);
	boolean isEmpty();
	boolean isCompatibleWith(ICaptchalogueable other);
	void fetch(EntityPlayer player, boolean shrinkHand);
	default void fetch(EntityPlayer player)
	{
		fetch(player, false);
	}
	void eject(BottomSylladex fromSylladex, EntityPlayer player);
	void eject(EntityPlayer player);
	ItemStack captchalogueIntoCardItem();
	NBTTagCompound writeData();
	@SideOnly(Side.CLIENT)
	void draw(GuiSylladex gui);
	@SideOnly(Side.CLIENT)
	String getDisplayName();
	@SideOnly(Side.CLIENT)
	ITextComponent getTextComponent();
	@SideOnly(Side.CLIENT)
	void renderTooltip(GuiSylladex gui, int x, int y);
	default ICaptchalogueable getAlchemyComponent()
	{
		return this;
	}

	/**
	 * Determines what content texture is used (ghost, item, abstract, etc.)
	 */
	@SideOnly(Side.CLIENT)
	String getTextureKey();

	static NBTTagCompound writeToNBT(ICaptchalogueable object)
	{
		if (object != null)
		{
			NBTTagCompound nbt = object.writeData();

			for(Map.Entry<ResourceLocation, Class<? extends ICaptchalogueable>> set : REGISTRY.entrySet())
				if(set.getValue().equals(object.getClass()))
				{
					nbt.setString("class", set.getKey().toString());
					break;
				}
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
			if(!className.contains(":"))
				className = Minestuck.MODID + ":" + className;

			ResourceLocation loc = new ResourceLocation(className);
			if(!REGISTRY.containsKey(loc))
				return new CaptchalogueableInvalid();

			try
			{
				return REGISTRY.get(loc).getConstructor(NBTTagCompound.class).newInstance(nbt);
			}
			catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e)
			{
				throw new RuntimeException(e);
			}
		}
		return null;
	}

	void drop(World world, double posX, double posY, double posZ);

	default void drop(Entity entity)
	{
		drop(entity.world, entity.posX, entity.posY, entity.posZ);
	}

	//for addon makers, slap this in one of the init events to register custom captchalogueable objects
	static void register(ResourceLocation location, Class<? extends ICaptchalogueable> objectType)
	{
		if(REGISTRY.containsKey(location))
			throw new IllegalArgumentException(location + " already exists.");
		REGISTRY.put(location, objectType);
	}
}
