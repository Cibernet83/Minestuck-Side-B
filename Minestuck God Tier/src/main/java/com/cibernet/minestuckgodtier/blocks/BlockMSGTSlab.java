package com.cibernet.minestuckgodtier.blocks;

import com.cibernet.minestuckgodtier.items.MSGTItems;
import net.minecraft.block.BlockPurpurSlab;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public abstract class BlockMSGTSlab extends net.minecraft.block.BlockSlab
{

    public static final PropertyEnum<BlockPurpurSlab.Variant> VARIANT = PropertyEnum.<BlockPurpurSlab.Variant>create("variant", BlockPurpurSlab.Variant.class);

    public BlockMSGTSlab(MapColor color, String unlocName, String registryName)
    {
        super(Material.ROCK, color);
        IBlockState iblockstate = this.blockState.getBaseState();

        setUnlocalizedName(unlocName);
        setRegistryName(registryName);


        setHarvestLevel("pickaxe", 3);
        setHardness(20.0F);
        setResistance(2000.0F);
        setCreativeTab(MSGTItems.tab);

        if (!this.isDouble())
        {
            iblockstate = iblockstate.withProperty(HALF, net.minecraft.block.BlockSlab.EnumBlockHalf.BOTTOM);
        }

        this.setDefaultState(iblockstate.withProperty(VARIANT, BlockPurpurSlab.Variant.DEFAULT));
        this.setCreativeTab(MSGTItems.tab);
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(this);
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(this);
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, BlockPurpurSlab.Variant.DEFAULT);

        if (!this.isDouble())
        {
            iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? net.minecraft.block.BlockSlab.EnumBlockHalf.BOTTOM : net.minecraft.block.BlockSlab.EnumBlockHalf.TOP);
        }

        return iblockstate;
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;

        if (!this.isDouble() && state.getValue(HALF) == net.minecraft.block.BlockSlab.EnumBlockHalf.TOP)
        {
            i |= 8;
        }

        return i;
    }

    protected BlockStateContainer createBlockState()
    {
        return this.isDouble() ? new BlockStateContainer(this, new IProperty[] {VARIANT}) : new BlockStateContainer(this, new IProperty[] {HALF, VARIANT});
    }

    /**
     * Returns the slab block name with the type associated with it
     */
    public String getUnlocalizedName(int meta)
    {
        return super.getUnlocalizedName();
    }

    public IProperty<?> getVariantProperty()
    {
        return VARIANT;
    }

    public Comparable<?> getTypeForItem(ItemStack stack)
    {
        return BlockPurpurSlab.Variant.DEFAULT;
    }

    public static class Double extends BlockMSGTSlab
    {
        final BlockSlab half;

        public Double(MapColor color, String unlocName, String registryName, BlockSlab half)
        {
            super(color, unlocName, registryName);
            MSGTBlocks.blocks.add(this);
            this.half = half;
        }

        /**
         * Get the Item that this Block should drop when harvested.
         */
        public Item getItemDropped(IBlockState state, Random rand, int fortune)
        {
            return Item.getItemFromBlock(half);
        }

        public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
        {
            return new ItemStack(half);
        }

        public boolean isDouble()
        {
            return true;
        }
    }

    public static class Half extends BlockMSGTSlab
    {
        public Half(MapColor color, String unlocName, String registryName) {
            super(color, unlocName, registryName);
            MSGTBlocks.blocks.add(this);
        }

        public boolean isDouble()
        {
            return false;
        }
    }

    public static enum Variant implements IStringSerializable
    {
        DEFAULT;

        public String getName()
        {
            return "default";
        }
    }
}
