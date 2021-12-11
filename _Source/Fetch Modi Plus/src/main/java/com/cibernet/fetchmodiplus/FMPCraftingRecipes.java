package com.cibernet.fetchmodiplus;

import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.util.CraftingRecipes;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class FMPCraftingRecipes
{
	public static class EmptyCardRecipe extends CraftingRecipes.NonMirroredRecipe
	{
		public EmptyCardRecipe(String group, int width, int height, NonNullList<Ingredient> ingredients, ItemStack result) {
			super(group, width, height, ingredients, result);
		}
		
		
		
		public static class Factory extends CraftingRecipes.ShapedFactory {
			public Factory() {
			}
			
			public IRecipe initRecipe(String group, int width, int height, NonNullList<Ingredient> ingredients, ItemStack result) {
				return new CraftingRecipes.EmptyCardRecipe(group, width, height, ingredients, result);
			}
		}
	}
}
