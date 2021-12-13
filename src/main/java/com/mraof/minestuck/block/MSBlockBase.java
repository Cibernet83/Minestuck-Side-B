package com.mraof.minestuck.block;

import com.mraof.minestuck.item.TabMinestuck;
import com.mraof.minestuck.util.IRegistryItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;


public class MSBlockBase extends Block implements IRegistryItem<Block>
{
    private final String regName;
    public static final ArrayList<IRegistryItem<Block>> blocks = new ArrayList<>();

    public MSBlockBase(String name, CreativeTabs tab, int stackSize, Material blockMaterialIn, MapColor blockMapColorIn)
    {

        super(blockMaterialIn, blockMapColorIn);
        regName = IRegistryItem.unlocToReg(name);
        setUnlocalizedName(name);
        setCreativeTab(tab);
        blocks.add(this);
    }

    public MSBlockBase(String name, Material material)
    {
        this(name, TabMinestuck.instance,64,material,material.getMaterialMapColor());
    }

    public MSBlockBase(String name, Material material, MapColor mapcolor){ this(name, TabMinestuck.instance, 64,material, mapcolor);}

    @Override
    public void register(IForgeRegistry<Block> registry)
    {
        setRegistryName(regName);
        registry.register(this);
    }

}
