package com.mraof.minestuck.item;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.alchemy.GristType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

import java.util.List;
import java.util.TreeMap;

public class ItemMinestuckCandy extends ItemFood
{
	TreeMap<Integer, Candy> candyMap;
	Candy invalidCandy = new Candy(0, 0, "Invalid");

	public ItemMinestuckCandy()
	{
		super("candy", 0, 0, false);
		this.setHasSubtypes(true);
		this.setCreativeTab(MinestuckTabs.minestuck);

		candyMap = new TreeMap<>();

		candyMap.put(0, new Candy(2, 0.3F, "Corn"));
	}
	
	public void updateCandy()
	{
		for (GristType type : GristType.REGISTRY.getValues())
		{
			if(type.getCandyItem().isEmpty())
			{
				float saturationModifier = type == GristType.Build ? 0.0F : 0.6F - type.getRarity(); //Perhaps change build to 0.1 or 0.05
				String name = type.getName();
				candyMap.put(type.getId() + 1, new Candy(2, saturationModifier, name.substring(0, 1).toUpperCase() + name.substring(1)));
				type.setCandyItem(new ItemStack(this, 1, type.getId() + 1));
			}
		}
	}
	
	private Candy getCandy(int id) {
		return candyMap.getOrDefault(id, invalidCandy);
	}

	@Override
	public int getHealAmount(ItemStack stack)
	{
		return getCandy(stack.getItemDamage()).healAmount;
	}

	@Override
	public float getSaturationModifier(ItemStack stack)
	{
		return getCandy(stack.getItemDamage()).saturation;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return getCandy(stack.getItemDamage()).name;
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if (this.isInCreativeTab(tab))
			for (int id : candyMap.keySet())
				items.add(new ItemStack(this, 1, id));
	}

	private static class Candy
	{
		private final int healAmount;
		private final float saturation;
		private final String name;

		Candy(int healAmount, float saturation, String name)
		{
			this.healAmount = healAmount;
			this.saturation = saturation;
			this.name = "item.candy" + name;
		}
	}

	@Override
	public void registerModel()
	{
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(new ResourceLocation(Minestuck.MODID, "candy_corn"), "inventory"));
		List<GristType> gristTypes = GristType.values();
		for (int i = 0; i < gristTypes.size(); i++)
			ModelLoader.setCustomModelResourceLocation(this, i + 1, new ModelResourceLocation(new ResourceLocation(Minestuck.MODID, "grist_candy_" + gristTypes.get(i).getName()), "inventory"));
	}
}