package com.mraof.minestuck.block;

import com.mraof.minestuck.item.block.MSItemBlock;
import com.mraof.minestuck.util.IRegistryObject;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Created by mraof on 2017 January 13 at 4:49 PM.
 */
public class BlockFluidGrist extends BlockFluidClassic implements IRegistryBlock
{
	private final String regName;

	public BlockFluidGrist(String grist, Fluid fluid, Material material)
	{
		super(fluid, material);
		setUnlocalizedName("liquid_" + grist);
		regName = IRegistryObject.unlocToReg("liquid_" + grist);
		MinestuckBlocks.blocks.add(this);
	}

	@Override
	public void register(IForgeRegistry<Block> registry)
	{
		setRegistryName(regName);
		registry.register(this);
	}

	@Override
	public MSItemBlock getItemBlock()
	{
		return new MSItemBlock(this);
	}
}
