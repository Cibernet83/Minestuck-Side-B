package com.mraof.minestuck.block;

import com.mraof.minestuck.util.IRegistryItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.registries.IForgeRegistry;

public class MSFluidBase extends BlockFluidClassic implements IRegistryItem<Block>
{
    private final String regName;

    public MSFluidBase(String name, Fluid fluid, Material material)
    {
        super(fluid, material);
        setUnlocalizedName(name);
        regName = IRegistryItem.unlocToReg(name);
        MSBlockBase.blocks.add(this);
    }

    @Override
    public void register(IForgeRegistry<Block> registry)
    {
        setRegistryName(regName);
        registry.register(this);
    }
}
