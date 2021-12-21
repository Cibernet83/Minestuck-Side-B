package com.mraof.minestuck.item;

import com.mraof.minestuck.util.EnumAspect;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemHeroStoneShard extends MSItemBase
{
    public final EnumAspect aspect;

    public ItemHeroStoneShard(EnumAspect aspect)
    {
        super("heroStoneShard");
        this.aspect = aspect;
        setCreativeTab(MinestuckTabs.godTier);
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

    @Override
    public void register(IForgeRegistry<Item> registry)
    {
        setRegistryName("hero_stone_shard" + (aspect == null ? "" : ("_" + aspect.toString())));
        registry.register(this);
    }
}
