package com.mraof.minestuck.block;

import com.mraof.minestuck.util.IRegistryItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Created by mraof on 2017 January 13 at 4:49 PM.
 */
public class BlockFluidGrist extends BlockFluidClassic implements IRegistryItem<Block> {
    private final String regName;

    public BlockFluidGrist(String grist, Fluid fluid, Material material) {
        super(fluid, material);
        setUnlocalizedName("liquid_"+grist);
        regName = IRegistryItem.unlocToReg("liquid_"+grist);
        MSBlockBase.blocks.add(this);
    }

    @Override
    public void register(IForgeRegistry<Block> registry)
    {
        setRegistryName(regName);
        registry.register(this);
    }
}
