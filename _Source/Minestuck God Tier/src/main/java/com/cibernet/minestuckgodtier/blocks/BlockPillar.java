package com.cibernet.minestuckgodtier.blocks;

import com.cibernet.minestuckgodtier.items.MSGTItems;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockPillar extends BlockRotatedPillar
{
    protected BlockPillar(MapColor color)
    {
        super(Material.ROCK, color);

        setHarvestLevel("pickaxe", 3);
        setHardness(20.0F);
        setResistance(2000.0F);
        setCreativeTab(MSGTItems.tab);
        MSGTBlocks.blocks.add(this);
        MSGTItems.blocks.add(this);
    }
}
