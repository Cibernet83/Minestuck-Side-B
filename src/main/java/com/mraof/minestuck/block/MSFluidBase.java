package com.mraof.minestuck.block;

import com.mraof.minestuck.item.block.MSItemBlock;
import com.mraof.minestuck.util.IRegistryObject;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
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
        regName = IRegistryObject.unlocToReg(name);
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
        return new MSItemBlock(this)
        {
            @Override
            public void registerModel()
            {
                super.registerModel();
                ModelLoader.setCustomStateMapper(MSFluidBase.this, (new StateMap.Builder()).ignore(BlockFluidBase.LEVEL).build());
            }
        };
    }
}
