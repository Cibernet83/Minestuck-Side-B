package com.mraof.minestuck.item.block;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.util.IUnlocSerializable;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public class MSItemBlockMultiTexture extends MSItemBlock
{
	protected final IUnlocSerializable[] states;

	public MSItemBlockMultiTexture(Block block, final IUnlocSerializable[] states)
	{
		super(block);
		this.states = states;
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return super.getUnlocalizedName() + "." + states[stack.getMetadata() % states.length].getUnlocalizedName();
	}

	@Override
	public void registerModel()
	{
		for (int i = 0; i < states.length; i++)
			ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation(new ResourceLocation(Minestuck.MODID, getRegistryName().getResourcePath() + "_" + states[i].getName()), "inventory"));
	}
}
