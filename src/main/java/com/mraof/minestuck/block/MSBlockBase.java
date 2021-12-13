package com.mraof.minestuck.block;

import com.mraof.minestuck.item.TabsMinestuck;
import com.mraof.minestuck.util.IRegistryItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nullable;
import java.util.List;

public class MSBlockBase extends Block implements IRegistryItem<Block>
{
    private final String regName;
    
    public MSBlockBase(Material blockMaterialIn, MapColor blockMapColorIn, String name)
    {
        super(blockMaterialIn, blockMapColorIn);
        this.setCreativeTab(TabsMinestuck.minestuck);
        this.setUnlocalizedName(name);
        this.regName = IRegistryItem.unlocToReg(name);
    }

    public MSBlockBase(Material blockMaterialIn, String name)
    {
        this(blockMaterialIn, blockMaterialIn.getMaterialMapColor(), name);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
    {
        String key = getUnlocalizedName()+".tooltip";
        if(!I18n.translateToLocal(key).equals(key))
            tooltip.add(I18n.translateToLocal(key));
        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    public void register(IForgeRegistry<Block> registry)
    {
        setRegistryName(regName);
        registry.register(this);
    }
}
