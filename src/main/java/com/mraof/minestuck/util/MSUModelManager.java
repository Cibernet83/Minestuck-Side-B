package com.mraof.minestuck.util;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.alchemy.GristType;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

import static com.mraof.minestuck.item.MinestuckItems.candy;

public class MSUModelManager // TODO: remove
{
    public static List<Item> items = new ArrayList<>();
    public static List<Block> blocks = new ArrayList<>();

    public static List<com.mraof.minestuck.util.Pair<Item, CustomItemMeshDefinition>> customItemModels = new ArrayList<>();

    @SubscribeEvent
    public static void handleModelRegistry(ModelRegistryEvent event)
    {
        ItemModels();
        ItemBlockModels();
    }

    @SideOnly(Side.CLIENT)
    private static void ItemModels()
    {
        for(Item item : items)
            register(item);

        for(Pair<Item, CustomItemMeshDefinition> pair : customItemModels)
        {
            ModelLoader.registerItemVariants(pair.object1, pair.object2.getResourceLocations());
            ModelLoader.setCustomMeshDefinition(pair.object1, pair.object2);
        }

        //ModelLoader.registerItemVariants(batteryBeamBlade, new ModelResourceLocation[]{new ModelResourceLocation(Minestuck.MODID+":catclaws_sheathed"), new ModelResourceLocation(Minestuck.MODID+":catclaws_drawn")});
        //ModelLoader.setCustomMeshDefinition(batteryBeamBlade, new DualWeaponDefinition(batteryBeamBlade));


        //ModelLoader.registerItemVariants(batteryBeamBlade, );
        // ModelLoader.setCustomMeshDefinition(batteryBeamBlade, new BeamBladeDefinition());

        //register(batteryBeamBlade);

        //Grist Candy
        if(Minestuck.isThaumLoaded)
            register(candy, GristType.REGISTRY.getID(GristType.Vis) + 1, "vis_nerds");
        if(Minestuck.isBotaniaLoaded)
            register(candy, GristType.REGISTRY.getID(GristType.Mana) + 1, "mana_gummy_drop");
    }

    @SideOnly(Side.CLIENT)
    private static void ItemBlockModels()
    {
        for(Block block : blocks)
            register(block);
    }

    @SideOnly(Side.CLIENT)
    private static void register(Item item)
    {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    @SideOnly(Side.CLIENT)
    private static void register(Item item, int meta, String modelResource)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Minestuck.MODID+":"+modelResource, "inventory"));
    }

    @SideOnly(Side.CLIENT)
    private static void register(Block block)
    {
        register(Item.getItemFromBlock(block));
    }

    @SideOnly(Side.CLIENT)
    public interface CustomItemMeshDefinition extends ItemMeshDefinition {
        public ResourceLocation[] getResourceLocations();
    }
}
