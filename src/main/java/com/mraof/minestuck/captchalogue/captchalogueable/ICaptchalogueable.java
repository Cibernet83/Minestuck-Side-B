package com.mraof.minestuck.captchalogue.captchalogueable;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.captchalogue.sylladex.BottomSylladex;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.GuiSylladex;
import com.mraof.minestuck.util.AlchemyUtils;
import com.mraof.minestuck.util.Pair;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public interface ICaptchalogueable
{
	HashMap<ResourceLocation, Tuple<Class<? extends ICaptchalogueable>, Function<NBTTagCompound, ? extends ICaptchalogueable>>> REGISTRY = new HashMap<>();
	Pair<Boolean, Boolean> COOL_BEANS = new Pair<Boolean, Boolean>(false, false)
	{{
		register(new ResourceLocation(Minestuck.MODID, "itemstack"), CaptchalogueableItemStack.class, CaptchalogueableItemStack::new);
		register(new ResourceLocation(Minestuck.MODID, "ghost"), CaptchalogueableGhost.class, CaptchalogueableGhost::new);
		register(new ResourceLocation(Minestuck.MODID, "entity"), CaptchalogueableEntity.class, CaptchalogueableEntity::new);
	}};

	void grow(ICaptchalogueable other);
	boolean isEmpty();
	boolean isCompatibleWith(ICaptchalogueable other);
	void fetch(EntityPlayer player);
	default void eject(BottomSylladex fromSylladex, int index, EntityPlayer player)
	{
		eject(player);
	}
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
	default ItemStack captchalogueIntoCardItem()
	{
		return AlchemyUtils.createCard(this, false);
	}
	default ICaptchalogueable getAlchemyComponent()
	{
		return this;
	}
	String getName();
	NBTTagCompound writeData();
	@SideOnly(Side.CLIENT)
	void draw(GuiSylladex gui, float mouseX, float mouseY, float partialTicks);
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
			NBTTagCompound nbt = object.writeData();

			for(Map.Entry<ResourceLocation, Tuple<Class<? extends ICaptchalogueable>, Function<NBTTagCompound, ? extends ICaptchalogueable>>> set : REGISTRY.entrySet())
				if(set.getValue().getFirst().equals(object.getClass()))
				{
					nbt.setString("Class", set.getKey().toString());
					break;
				}
			return nbt;
		}
		else
		{
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("Class", "null");
			return nbt;
		}
	}

	static ICaptchalogueable readFromNBT(NBTTagCompound nbt)
	{
		String className = nbt.getString("Class");
		if (!className.equals("null"))
		{
			if(!className.contains(":"))
				className = Minestuck.MODID + ":" + className;

			ResourceLocation loc = new ResourceLocation(className);
			if(!REGISTRY.containsKey(loc))
				return new CaptchalogueableInvalid();

			return REGISTRY.get(loc).getSecond().apply(nbt);
		}
		return null;
	}

	//for addon makers, slap this in one of the init events to register custom captchalogueable objects
	static <T extends ICaptchalogueable> void register(ResourceLocation location, Class<T> objectType, Function<NBTTagCompound, T> constructor)
	{
		if(REGISTRY.containsKey(location))
			throw new IllegalArgumentException(location + " already exists.");
		REGISTRY.put(location, new Tuple<>(objectType, constructor));
	}
}
