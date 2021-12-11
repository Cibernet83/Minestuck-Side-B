package com.cibernet.minestuckgodtier.items;

import com.mraof.minestuck.util.EnumAspect;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

public class ItemHeroStoneShard extends Item
{
    public final EnumAspect aspect;

    public ItemHeroStoneShard(EnumAspect aspect)
    {
        this.aspect = aspect;
        setCreativeTab(MSGTItems.tab);

        setUnlocalizedName("heroStoneShard");
        setRegistryName("hero_stone_shard" + (aspect == null ? "" : ("_" + aspect.toString())));
    }

    @SuppressWarnings("deprecation")
    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {

        String heroAspect = TextFormatting.OBFUSCATED + "Thing" + TextFormatting.RESET;

        if(aspect != null)
            heroAspect = I18n.translateToLocal("title." + aspect.toString());

        return I18n.translateToLocalFormatted(this.getUnlocalizedNameInefficiently(stack) + ".name", heroAspect).trim();
    }
}
