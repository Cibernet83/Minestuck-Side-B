package com.mraof.minestuck.block;

import com.mraof.minestuck.item.block.MSItemBlock;
import com.mraof.minestuck.util.IRegistryItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.registries.IForgeRegistry;

public class MSFluidBase extends BlockFluidClassic implements IRegistryBlock
{
    private final String regName;

    public MSFluidBase(String name, Fluid fluid, Material material)
    {
        super(fluid, material);
        setUnlocalizedName(name);
        regName = IRegistryItem.unlocToReg(name);
        MinestuckBlocks.blocks.add(this);
        MinestuckBlocks.fluids.add(getDefaultState());
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
