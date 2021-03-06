package com.mraof.minestuck.modSupport.jei;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.util.AlchemyUtils;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mraof on 2017 January 23 at 6:50 AM.
 */
public class DesignixRecipeCategory implements IRecipeCategory<DesignixRecipeWrapper>
{
	private IDrawable background;

	DesignixRecipeCategory(IGuiHelper guiHelper)
	{
		ResourceLocation punchDesignixBackground = new ResourceLocation(Minestuck.MODID, "textures/gui/designix.png");
		background = guiHelper.createDrawable(punchDesignixBackground, 43, 25, 94, 42);
	}

	@Override
	public String getUid()
	{
		return "minestuck.punchDesignix";
	}

	@Override
	public String getTitle()
	{
		return I18n.format("tile.miniPunchDesignix.name");
	}

	@Override
	public String getModName()
	{
		return Minestuck.NAME;
	}

	@Override
	public IDrawable getBackground()
	{
		return background;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, DesignixRecipeWrapper recipeWrapper, IIngredients ingredients)
	{
		IGuiItemStackGroup stackGroup = recipeLayout.getItemStacks();
		stackGroup.init(0, true, 0, 0);
		stackGroup.init(1, true, 0, 24);
		stackGroup.init(2, false, 72, 11);
		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		stackGroup.set(0, inputs.get(0));

		List<ItemStack> second = new ArrayList<ItemStack>();
		for (ItemStack stack : inputs.get(1))
		{
			second.add(AlchemyUtils.createCard(stack, true));
		}
		stackGroup.set(1, second);
		List<ItemStack> outputs = new ArrayList<ItemStack>(ingredients.getOutputs(ItemStack.class).get(0));
		outputs.add(AlchemyUtils.createCard(outputs.get(0), true));
		stackGroup.set(2, outputs);
	}
}
