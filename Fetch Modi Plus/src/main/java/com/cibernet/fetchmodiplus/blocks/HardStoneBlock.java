package com.cibernet.fetchmodiplus.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

import java.util.Random;

public class HardStoneBlock extends Block
{
    public HardStoneBlock()
    {
        super(Material.ROCK);
        setHardness(10.0F).setResistance(6.0F);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(Blocks.COBBLESTONE);
    }
}
