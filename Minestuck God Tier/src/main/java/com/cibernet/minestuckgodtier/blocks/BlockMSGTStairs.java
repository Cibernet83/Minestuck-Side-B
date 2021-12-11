package com.cibernet.minestuckgodtier.blocks;

import com.cibernet.minestuckgodtier.items.MSGTItems;
import net.minecraft.block.state.IBlockState;

public class BlockMSGTStairs extends net.minecraft.block.BlockStairs
{
    public BlockMSGTStairs(IBlockState modelState) {
        super(modelState);
        setCreativeTab(MSGTItems.tab);
        useNeighborBrightness = true;
        MSGTBlocks.blocks.add(this);
        MSGTItems.blocks.add(this);
    }
}
