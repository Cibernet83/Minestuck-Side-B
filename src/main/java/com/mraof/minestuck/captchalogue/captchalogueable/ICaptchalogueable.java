package com.mraof.minestuck.captchalogue.captchalogueable;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.captchalogue.sylladex.BottomSylladex;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.sylladex.GuiSylladex;
import com.mraof.minestuck.util.AlchemyUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
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
	Tuple<Boolean, Boolean> COOL_BEANS = new Tuple<Boolean, Boolean>(false, false)
	{{
		register(new ResourceLocation(Minestuck.MODID, "itemstack"), CaptchalogueableItemStack.class, CaptchalogueableItemStack::new);
		register(new ResourceLocation(Minestuck.MODID, "ghost"), CaptchalogueableGhost.class, CaptchalogueableGhost::new);
		register(new ResourceLocation(Minestuck.MODID, "entity"), CaptchalogueableEntity.class, CaptchalogueableEntity::new);
		register(new ResourceLocation(Minestuck.MODID, "hashstack"), CaptchalogueableHashStack.class, CaptchalogueableHashStack::new);
	}};

	//for addon makers, slap this in one of the init events to register custom captchalogueable objects
	static <T extends ICaptchalogueable> void register(ResourceLocation location, Class<T> objectType, Function<NBTTagCompound, T> constructor)
	{
		if (REGISTRY.containsKey(location))
			throw new IllegalArgumentException(location + " already exists.");
		REGISTRY.put(location, new Tuple<>(objectType, constructor));
	}

	static NBTTagCompound writeToNBT(ICaptchalogueable object)
	{
		if (object != null)
		{
			NBTTagCompound nbt = object.writeData();

			for (Map.Entry<ResourceLocation, Tuple<Class<? extends ICaptchalogueable>, Function<NBTTagCompound, ? extends ICaptchalogueable>>> set : REGISTRY.entrySet())
				if (set.getValue().getFirst().equals(object.getClass()))
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
	NBTTagCompound writeData();

	static ICaptchalogueable readFromNBT(NBTTagCompound nbt)
	{
		String className = nbt.getString("Class");
		if (!className.equals("null"))
		{
			if (!className.contains(":"))
				className = Minestuck.MODID + ":" + className;

			ResourceLocation loc = new ResourceLocation(className);
			if (!REGISTRY.containsKey(loc))
				return new CaptchalogueableInvalid();

			return REGISTRY.get(loc).getSecond().apply(nbt);
		}
		return null;
	}

	/** Insert as much of other into this as possible, and remove that much from this stack */
	void grow(ICaptchalogueable other);
	/** Get whether this stack is empty, to check things like when this stack is finished growing */
	boolean isEmpty();
	/** Void this stack such that isEmpty will return true */
	void empty();
	/** Get whether grow will be applicable to other */
	boolean isCompatibleWith(ICaptchalogueable other);
	/** Get whether this stack and other are pretty much the same generic type */
	boolean isLooselyCompatibleWith(ICaptchalogueable other);
	/** Retrieve this stack as a successful fetch operation. This stack is removed from the sylladex preceeding this operation */
	void fetch(EntityPlayerMP player);
	/** Add cards to fromSylladex if applicable, then throw the remaining stack from the player */
	default void eject(BottomSylladex fromSylladex, int index, EntityPlayerMP player)
	{
		eject(player);
	}
	/** Throw this stack from the player. This stack either is or will be removed from the sylladex */
	void eject(EntityPlayerMP player);
	/**
	 * Attempt to empty this stack by adding cards from this stack to fromSylladex
	 * @return Whether cards were added/ejected and this stack is empty
	 */
	default boolean tryPopCard(BottomSylladex fromSylladex, int index, EntityPlayerMP player)
	{
		return false;
	}
	/** Drop this stack gently into the world */
	default void drop(Entity entity)
	{
		drop(entity.world, entity.posX, entity.posY, entity.posZ);
	}
	/** Drop this stack gently into the worl. */
	void drop(World world, double posX, double posY, double posZ);
	/** Create a card from this stack */
	default ItemStack captchalogueIntoCardItem()
	{
		return AlchemyUtils.createCard(this, false);
	}
	default ICaptchalogueable getAlchemyComponent()
	{
		return this;
	}
	String getName();
	String getDisplayName();

	@SideOnly(Side.CLIENT)
	void draw(GuiSylladex gui, CardGuiContainer card, float mouseX, float mouseY, float partialTicks);
	@SideOnly(Side.CLIENT)
	ITextComponent getTextComponent();
	@SideOnly(Side.CLIENT)
	void renderTooltip(GuiSylladex gui, int x, int y);
	/** Get what content texture is used (ghost, item, abstract, etc.) */
	@SideOnly(Side.CLIENT)
	String getTextureKey();
}
