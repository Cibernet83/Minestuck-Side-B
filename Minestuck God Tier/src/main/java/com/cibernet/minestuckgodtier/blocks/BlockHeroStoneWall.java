package com.cibernet.minestuckgodtier.blocks;

import com.cibernet.minestuckgodtier.items.MSGTItems;
import com.mraof.minestuck.util.EnumAspect;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockHeroStoneWall extends BlockWall implements IGodTierBlock
{
    protected final EnumAspect aspect;

    public BlockHeroStoneWall(EnumAspect aspect)
    {
        super(BlockHeroStone.getAspectMapColor(aspect));
        this.aspect = aspect;

        setHarvestLevel("pickaxe", 3);
        setHardness(20.0F);
        setBlockUnbreakable();
        setCreativeTab(MSGTItems.tab);

        setRegistryName("hero_stone_wall" + (aspect == null ? "" : ("_" + aspect.toString())));
        setUnlocalizedName("heroStoneWall");
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
}
