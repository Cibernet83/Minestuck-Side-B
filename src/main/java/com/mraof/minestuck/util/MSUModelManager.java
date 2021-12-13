package com.mraof.minestuck.util;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.alchemy.GristType;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

import static com.mraof.minestuck.item.MinestuckItems.*;

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

    @SideOnly(Side.CLIENT)
    public static class DualWeaponDefinition implements CustomItemMeshDefinition {
        private final String model1;
        private final String model2;
        public DualWeaponDefinition(String model1, String model2)
        {
            this.model1 = model1;
            this.model2 = model2;
        }

        public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation( Minestuck.MODID+":" + (batteryBeamBlade.isDrawn(stack) ? model1 : model2), "inventory");
        }

        @Override
        public ResourceLocation[] getResourceLocations() {
            return new ResourceLocation[]{new ResourceLocation(Minestuck.MODID, "" + model1), new ResourceLocation(Minestuck.MODID, "" + model2)};
        }
    }

    @SideOnly(Side.CLIENT)
    public static class DyedItemDefinition implements CustomItemMeshDefinition
    {
        private final String model;

        public DyedItemDefinition(String model) {
            this.model = model;
        }

        @Override
        public ResourceLocation[] getResourceLocations()
        {
            ResourceLocation[] result = new ResourceLocation[EnumDyeColor.values().length];

            for(int i = 0; i < result.length; i++)
                result[i] = new ResourceLocation(Minestuck.MODID, model + "_" + EnumDyeColor.byDyeDamage(i).getName());

            return result;
        }

        @Override
        public ModelResourceLocation getModelLocation(ItemStack stack) {
            return new ModelResourceLocation(getResourceLocations()[stack.getItemDamage()], "inventory");
        }
    }
}
