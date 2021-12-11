package com.cibernet.minestuckgodtier.blocks;

import com.cibernet.minestuckgodtier.items.MSGTItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockStone extends Block
{
    public BlockStone( MapColor blockMapColorIn)
    {
        super(Material.ROCK, blockMapColorIn);


        setHarvestLevel("pickaxe", 3);
        setHardness(20.0F);
        setResistance(2000.0F);
        setCreativeTab(MSGTItems.tab);
        MSGTBlocks.blocks.add(this);
        MSGTItems.blocks.add(this);
    }
}
