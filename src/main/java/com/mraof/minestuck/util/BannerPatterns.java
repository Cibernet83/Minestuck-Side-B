package com.mraof.minestuck.util;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.util.EnumAspect;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;

public class BannerPatterns
{

    public static void init()
    {
        for(EnumAspect aspect : EnumAspect.values())
            registerPattern(aspect.toString(), new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("minestuckgodtier", "hero_stone_shard_"+aspect.toString()))));

        registerPattern("moon", new ItemStack(MinestuckItems.moonstone));
    }



    public static BannerPattern registerPattern(String id, ItemStack ingredient)
    {
        Class<?>[] paramTypes = new Class[] {String.class, String.class, ItemStack.class};
        Object[] paramVals = new Object[] {Minestuck.MODID + "_" + id, Minestuck.MODID + "." + id, ingredient};
        return EnumHelper.addEnum(BannerPattern.class, id.toUpperCase(), paramTypes, paramVals);
    }
}
