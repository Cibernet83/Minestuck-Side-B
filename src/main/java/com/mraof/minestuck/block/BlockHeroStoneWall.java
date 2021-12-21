package com.mraof.minestuck.block;

import com.mraof.minestuck.item.MinestuckTabs;
import com.mraof.minestuck.util.EnumAspect;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nullable;
import java.util.List;

public class BlockHeroStoneWall extends MSBlockWall implements IGodTierBlock
{
    protected final EnumAspect aspect;

    public BlockHeroStoneWall(IBlockState heroStoneBlockState)
    {
        super("heroStoneWall", heroStoneBlockState);
        this.aspect = ((IGodTierBlock) heroStoneBlockState.getBlock()).getAspect();
        setCreativeTab(MinestuckTabs.godTier);
    }

    @Override
    public boolean canGodTier() {
        return false;
    }

    @Override
    public EnumAspect getAspect()
    {
        return aspect;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        String heroAspect = TextFormatting.OBFUSCATED + "Thing" + TextFormatting.RESET;

        if(getAspect() != null)
            heroAspect = getAspect().getDisplayName();


        tooltip.add(heroAspect);
    }

    @Override
    public void register(IForgeRegistry<Block> registry)
    {
		setRegistryName("hero_stone_wall" + (aspect == null ? "" : "_" + aspect.toString()));
    	registry.register(this);
    }
}
