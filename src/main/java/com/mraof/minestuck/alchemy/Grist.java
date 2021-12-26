package com.mraof.minestuck.alchemy;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.util.IRegistryObject;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.Collection;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public class Grist extends IForgeRegistryEntry.Impl<Grist> implements Comparable<Grist>, IRegistryObject<Grist>
{
	public static ForgeRegistry<Grist> REGISTRY;

	private final String name, regName;
	private final float rarity;
	private final float value;
	private ItemStack candyItem = ItemStack.EMPTY;

	public Grist(String name, float rarity)
	{
		this(name, rarity, 2);
	}

	public Grist(String name, float rarity, float value)
	{
		this.name = name;
		this.regName = IRegistryObject.unlocToReg(name);
		this.rarity = rarity;
		this.value = value;
		MinestuckGrist.grists.add(this);
	}

	public static Grist getTypeFromString(String string)
	{
		if (!string.contains(":"))
		{
			string = Minestuck.MODID+":" + string;
		}
		return REGISTRY.getValue(new ResourceLocation(string));
	}

	public static Collection<Grist> values()
	{
		return REGISTRY.getValuesCollection();
	}

	public String getDisplayName()	//TODO Phase out serverside usage of this method
	{
		return I18n.translateToLocal("grist." + regName);
	}

	public String getUnlocalizedName()
	{
		return name;
	}

	/**
	 * Returns the grist's full unlocalized name.
	 *
	 * @return the grist's full unlocalized name
	 */
	public String getName()
	{
		return regName;
	}

	/**
	 * Returns the grist's rarity. Is a number from 0.0 to 1.0.
	 *
	 * @return the rarity
	 */
	public float getRarity()
	{
		return rarity;
	}

	/**
	 * Returns the power level of a underling of a grist's type. Don't call this with grists like zillium or build.
	 */
	public float getPower()
	{
		return 1 / rarity;
	}


	/**
	 * @return a value estimate for this grist type
	 */
	public float getValue()
	{
		return value;
	}

	public ResourceLocation getIcon()
	{
		return getRegistryName();
	}

	public ItemStack getCandyItem()
	{
		return candyItem.copy();
	}

	public Grist setCandyItem(ItemStack stack)
	{
		candyItem = stack;
		return this;
	}

	public int getId()
	{
		return REGISTRY.getID(this);
	}

	@Override
	public int compareTo(Grist grist)
	{
		return this.getId() - grist.getId();
	}

	@Override
	public void register(IForgeRegistry<Grist> registry)
	{
		setRegistryName(regName);
		registry.register(this);
	}

	@SubscribeEvent
	public static void onNewRegistry(RegistryEvent.NewRegistry event)
	{
		REGISTRY = (ForgeRegistry<Grist>) new RegistryBuilder<Grist>()
				.setName(new ResourceLocation(Minestuck.MODID, "grist"))
				.setType(Grist.class)
				.create();
	}
}

