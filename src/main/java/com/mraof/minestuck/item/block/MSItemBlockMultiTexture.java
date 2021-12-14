package com.mraof.minestuck.item.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import static net.minecraft.item.ItemMultiTexture.Mapper;

public class MSItemBlockMultiTexture extends MSItemBlock
{
	protected final Mapper nameFunction;

	public MSItemBlockMultiTexture(Block block, Mapper nameFunction)
	{
		super(block);
		this.nameFunction = nameFunction;
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	public MSItemBlockMultiTexture(Block block, final String[] namesByMeta)
	{
		this(block, (ItemStack input) -> input.getMetadata() < 0 || input.getMetadata() >= namesByMeta.length ? namesByMeta[0] : namesByMeta[input.getMetadata()]);
	}

	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return super.getUnlocalizedName() + "." + this.nameFunction.apply(stack);
	}
}
