package com.mraof.minestuck.alchemy;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.block.BlockGrist;
import com.mraof.minestuck.block.BlockWoolTransportalizer;
import com.mraof.minestuck.block.MinestuckBlocks;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.item.properties.PropertyBreakableItem;
import com.mraof.minestuck.modSupport.*;
import com.mraof.minestuck.util.Debug;
import com.mraof.minestuck.util.MSUCalendarUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.items.ItemsTC;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.ModItems;

import java.util.*;

import static com.mraof.minestuck.MinestuckConfig.oreMultiplier;
import static com.mraof.minestuck.alchemy.CombinationRegistry.Mode.MODE_AND;
import static com.mraof.minestuck.alchemy.CombinationRegistry.Mode.MODE_OR;
import static com.mraof.minestuck.block.MinestuckBlocks.*;
import static com.mraof.minestuck.item.MinestuckItems.returnNode;
import static com.mraof.minestuck.item.MinestuckItems.*;
import static net.minecraftforge.oredict.OreDictionary.WILDCARD_VALUE;

public class AlchemyRecipes
{
	private static final TreeMap<EnumDyeColor, GristSet> dyeGrist = new TreeMap<>();
	private static Map<ItemStack, GristSet> containerlessCosts = new HashMap<>();

	public static void registerVanillaRecipes()
	{
		//Set up Alchemiter recipes
		//Blocks
		GristRegistry.addGristConversion(new ItemStack(Blocks.COBBLESTONE), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.DEADBUSH), false, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.sulfur}, new int[]{2, 1}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.DIRT, 1, 0), true, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.DIRT, 1, 2), true, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.DRAGON_EGG), false, new GristSet(new Grist[]{MinestuckGrist.uranium, MinestuckGrist.tar, MinestuckGrist.zillium}, new int[]{800, 800, 10}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.END_STONE), false, new GristSet(new Grist[]{MinestuckGrist.caulk, MinestuckGrist.build}, new int[]{3, 4}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.GLASS), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{1}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.GRASS), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.GRAVEL), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{3}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.HARDENED_CLAY), false, new GristSet(new Grist[]{MinestuckGrist.shale, MinestuckGrist.marble}, new int[]{12, 4}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.ICE), false, new GristSet(new Grist[]{MinestuckGrist.cobalt}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.LEAVES), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{1}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.LOG), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{8}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.LOG2), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{8}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.MELON_BLOCK), false, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.chalk, MinestuckGrist.build}, new int[]{8, 8, 2}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.MYCELIUM), false, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.ruby, MinestuckGrist.build}, new int[]{2, 2, 2}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.NETHERRACK), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.tar}, new int[]{2, 1}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.OBSIDIAN), false, new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.tar, MinestuckGrist.build}, new int[]{8, 16, 6}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.PUMPKIN), false, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.caulk}, new int[]{12, 6}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.SAND), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{1}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.SAPLING), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{16}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.SNOW), false, new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.build}, new int[]{5, 3}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.SNOW_LAYER), false, new GristSet(new Grist[]{MinestuckGrist.cobalt}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.SOUL_SAND), false, new GristSet(new Grist[]{MinestuckGrist.sulfur, MinestuckGrist.caulk, MinestuckGrist.build}, new int[]{5, 3, 2}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.SPONGE, 1, 0), new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.sulfur}, new int[]{20, 30}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.SPONGE, 1, 1), new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.sulfur, MinestuckGrist.cobalt}, new int[]{20, 30, 10}));
		//GristRegistry.addGristConversion(new ItemStack(Blocks.STAINED_HARDENED_CLAY), false, new GristSet(new GristType[] {GristType.shale, GristType.marble}, new int[] {12, 4}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.STONE), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.STONE_SLAB, 1, 5), true, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{1}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.STONEBRICK, 1, 2), true, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.STONE_BRICK_STAIRS), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{3}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.SANDSTONE_STAIRS), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{6}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.WEB), false, new GristSet(new Grist[]{MinestuckGrist.chalk}, new int[]{18}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.WOOL, 1, 0), true, new GristSet(new Grist[]{MinestuckGrist.chalk}, new int[]{6}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.WOOL, 1, 1), true, new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.garnet, MinestuckGrist.amber}, new int[]{6, 1, 1}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.WOOL, 1, 10), true, new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.amethyst, MinestuckGrist.garnet}, new int[]{6, 1, 1}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.WOOL, 1, 11), true, new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.amethyst}, new int[]{6, 2}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.WOOL, 1, 12), true, new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.iodine}, new int[]{6, 2}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.WOOL, 1, 13), true, new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.amber}, new int[]{6, 2}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.WOOL, 1, 14), true, new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.garnet}, new int[]{6, 2}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.WOOL, 1, 15), true, new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.tar}, new int[]{6, 2}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.WOOL, 1, 2), true, new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.amethyst, MinestuckGrist.garnet}, new int[]{6, 1, 2}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.WOOL, 1, 3), true, new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.amethyst}, new int[]{8, 1}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.WOOL, 1, 4), true, new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.amber}, new int[]{6, 2}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.WOOL, 1, 5), true, new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.amber}, new int[]{6, 2}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.WOOL, 1, 6), true, new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.garnet}, new int[]{6, 1}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.WOOL, 1, 7), true, new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.tar}, new int[]{6, 1}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.WOOL, 1, 8), true, new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.tar}, new int[]{6, 1}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.WOOL, 1, 9), true, new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.amethyst, MinestuckGrist.amber}, new int[]{6, 1, 1}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.PACKED_ICE), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.cobalt}, new int[]{10, 6}));
		GristRegistry.addGristConversion(Blocks.TORCH, new GristSet(MinestuckGrist.build, 2));
		GristRegistry.addGristConversion(Blocks.PRISMARINE, BlockPrismarine.ROUGH_META, new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.build}, new int[]{7, 12}));
		GristRegistry.addGristConversion(Blocks.PRISMARINE, BlockPrismarine.BRICKS_META, new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.build}, new int[]{12, 18}));
		GristRegistry.addGristConversion(Blocks.PRISMARINE, BlockPrismarine.DARK_META, new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.tar, MinestuckGrist.build}, new int[]{10, 2, 18}));
		GristRegistry.addGristConversion(Blocks.SEA_LANTERN, new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.diamond, MinestuckGrist.amethyst}, new int[]{32, 6, 12}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.SANDSTONE), false, new GristSet(MinestuckGrist.build, 4));
		GristRegistry.addGristConversion(new ItemStack(Blocks.PLANKS), false, new GristSet(MinestuckGrist.build, 2));
		GristRegistry.addGristConversion(new ItemStack(Blocks.CHORUS_FLOWER), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.amethyst, MinestuckGrist.shale}, new int[]{26, 23, 10}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.PURPUR_PILLAR), new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.shale}, new int[]{2, 4}));
		GristRegistry.addGristConversion(Blocks.DISPENSER, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.garnet, MinestuckGrist.chalk}, new int[]{17, 4, 6}));

		//Items
		GristRegistry.addGristConversion(new ItemStack(Items.BLAZE_ROD), new GristSet(new Grist[]{MinestuckGrist.tar, MinestuckGrist.uranium}, new int[]{20, 2}));
		GristRegistry.addGristConversion(new ItemStack(Items.BONE), new GristSet(new Grist[]{MinestuckGrist.chalk}, new int[]{6}));
		GristRegistry.addGristConversion(new ItemStack(Items.BRICK), new GristSet(new Grist[]{MinestuckGrist.shale, MinestuckGrist.tar}, new int[]{3, 1}));
		GristRegistry.addGristConversion(new ItemStack(Items.CHAINMAIL_BOOTS), false, new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.mercury}, new int[]{16, 8}));
		GristRegistry.addGristConversion(new ItemStack(Items.CHAINMAIL_CHESTPLATE), false, new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.mercury}, new int[]{32, 16}));
		GristRegistry.addGristConversion(new ItemStack(Items.CHAINMAIL_HELMET), false, new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.mercury}, new int[]{20, 10}));
		GristRegistry.addGristConversion(new ItemStack(Items.CHAINMAIL_LEGGINGS), false, new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.mercury}, new int[]{28, 14}));
		GristRegistry.addGristConversion(new ItemStack(Items.CLAY_BALL), new GristSet(new Grist[]{MinestuckGrist.shale}, new int[]{3}));
		GristRegistry.addGristConversion(new ItemStack(Items.DIAMOND_HORSE_ARMOR), false, new GristSet(new Grist[]{MinestuckGrist.diamond}, new int[]{80}));
		GristRegistry.addGristConversion(new ItemStack(Items.DYE, 1, 0), true, new GristSet(new Grist[]{MinestuckGrist.tar}, new int[]{4}));                                                //Black
		GristRegistry.addGristConversion(new ItemStack(Items.DYE, 1, 1), true, new GristSet(new Grist[]{MinestuckGrist.garnet}, new int[]{4}));                                            //Red
		GristRegistry.addGristConversion(new ItemStack(Items.DYE, 1, 10), true, new GristSet(new Grist[]{MinestuckGrist.amber}, new int[]{3}));                                            //Lime
		GristRegistry.addGristConversion(new ItemStack(Items.DYE, 1, 11), true, new GristSet(new Grist[]{MinestuckGrist.amber}, new int[]{4}));                                            //Yellow
		GristRegistry.addGristConversion(new ItemStack(Items.DYE, 1, 12), true, new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.chalk}, new int[]{2, 2}));                        //Light Blue
		GristRegistry.addGristConversion(new ItemStack(Items.DYE, 1, 13), true, new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.garnet}, new int[]{1, 3}));                    //Magenta
		GristRegistry.addGristConversion(new ItemStack(Items.DYE, 1, 14), true, new GristSet(new Grist[]{MinestuckGrist.garnet, MinestuckGrist.amber}, new int[]{2, 2}));                        //Orange
		GristRegistry.addGristConversion(new ItemStack(Items.DYE, 1, 2), true, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.iodine}, new int[]{3, 1}));                        //Green
		GristRegistry.addGristConversion(new ItemStack(Items.DYE, 1, 3), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.amber}, new int[]{3, 1}));                        //Brown
		GristRegistry.addGristConversion(new ItemStack(Items.DYE, 1, 4), true, new GristSet(new Grist[]{MinestuckGrist.amethyst}, new int[]{4}));                                            //Blue
		GristRegistry.addGristConversion(new ItemStack(Items.DYE, 1, 5), true, new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.garnet}, new int[]{2, 2}));                        //Purple
		GristRegistry.addGristConversion(new ItemStack(Items.DYE, 1, 6), true, new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.amber, MinestuckGrist.iodine}, new int[]{2, 2, 1})); //Cyan
		GristRegistry.addGristConversion(new ItemStack(Items.DYE, 1, 7), true, new GristSet(new Grist[]{MinestuckGrist.tar, MinestuckGrist.chalk}, new int[]{1, 3}));                            //Light Gray
		GristRegistry.addGristConversion(new ItemStack(Items.DYE, 1, 8), true, new GristSet(new Grist[]{MinestuckGrist.tar, MinestuckGrist.chalk}, new int[]{3, 1}));                            //Gray
		GristRegistry.addGristConversion(new ItemStack(Items.DYE, 1, 9), true, new GristSet(new Grist[]{MinestuckGrist.garnet, MinestuckGrist.chalk}, new int[]{2, 2}));                        //Pink
		GristRegistry.addGristConversion(new ItemStack(Items.ENCHANTED_BOOK), false, new GristSet(new Grist[]{MinestuckGrist.uranium, MinestuckGrist.quartz, MinestuckGrist.diamond, MinestuckGrist.ruby, MinestuckGrist.chalk, MinestuckGrist.iodine}, new int[]{8, 1, 4, 4, 16, 2}));
		GristRegistry.addGristConversion(new ItemStack(Items.ENDER_PEARL), new GristSet(new Grist[]{MinestuckGrist.uranium, MinestuckGrist.diamond, MinestuckGrist.mercury}, new int[]{13, 5, 8}));
		GristRegistry.addGristConversion(new ItemStack(Items.EXPERIENCE_BOTTLE), new GristSet(new Grist[]{MinestuckGrist.uranium, MinestuckGrist.quartz, MinestuckGrist.diamond, MinestuckGrist.ruby}, new int[]{16, 3, 4, 6}));
		GristRegistry.addGristConversion(new ItemStack(Items.FEATHER), new GristSet(new Grist[]{MinestuckGrist.chalk}, new int[]{4}));
		GristRegistry.addGristConversion(new ItemStack(Items.FIREWORK_CHARGE), false, new GristSet(new Grist[]{MinestuckGrist.sulfur, MinestuckGrist.chalk}, new int[]{4, 2}));
		GristRegistry.addGristConversion(new ItemStack(Items.FIREWORKS), false, new GristSet(new Grist[]{MinestuckGrist.sulfur, MinestuckGrist.chalk}, new int[]{4, 5}));
		GristRegistry.addGristConversion(new ItemStack(Items.FLINT), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{4}));
		GristRegistry.addGristConversion(new ItemStack(Items.GHAST_TEAR), new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.chalk}, new int[]{10, 19}));
		GristRegistry.addGristConversion(new ItemStack(Items.GLOWSTONE_DUST), new GristSet(new Grist[]{MinestuckGrist.tar, MinestuckGrist.chalk}, new int[]{4, 6}));
		GristRegistry.addGristConversion(new ItemStack(Items.GOLDEN_HORSE_ARMOR), false, new GristSet(new Grist[]{MinestuckGrist.gold}, new int[]{40}));
		GristRegistry.addGristConversion(new ItemStack(Items.GUNPOWDER), new GristSet(new Grist[]{MinestuckGrist.sulfur, MinestuckGrist.chalk}, new int[]{3, 2}));
		GristRegistry.addGristConversion(new ItemStack(Items.IRON_HORSE_ARMOR), false, new GristSet(new Grist[]{MinestuckGrist.rust}, new int[]{40}));
		registerContainerlessCost(new ItemStack(Items.LAVA_BUCKET), new GristSet(new Grist[]{MinestuckGrist.tar}, new int[]{16}));
		GristRegistry.addGristConversion(new ItemStack(Items.LEATHER), false, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.chalk}, new int[]{3, 3}));
		GristRegistry.addGristConversion(new ItemStack(Items.MAP), false, new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.chalk, MinestuckGrist.garnet}, new int[]{32, 10, 2}));
		registerContainerlessCost(new ItemStack(Items.MILK_BUCKET), new GristSet(new Grist[]{MinestuckGrist.chalk}, new int[]{8}));
		GristRegistry.addGristConversion(new ItemStack(Items.NAME_TAG), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.caulk, MinestuckGrist.amber, MinestuckGrist.chalk}, new int[]{4, 10, 12, 8}));
		GristRegistry.addGristConversion(new ItemStack(Items.NETHER_STAR), new GristSet(new Grist[]{MinestuckGrist.uranium, MinestuckGrist.tar, MinestuckGrist.diamond}, new int[]{344, 135, 92}));
		GristRegistry.addGristConversion(new ItemStack(Items.NETHER_WART), new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.tar}, new int[]{3, 5}));
		GristRegistry.addGristConversion(new ItemStack(Items.NETHERBRICK), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.tar}, new int[]{1, 2}));
		GristRegistry.addGristConversion(new ItemStack(Items.RECORD_11), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.caulk, MinestuckGrist.tar, MinestuckGrist.mercury}, new int[]{10, 5, 2, 2}));
		GristRegistry.addGristConversion(new ItemStack(Items.RECORD_13), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.caulk, MinestuckGrist.amber, MinestuckGrist.chalk}, new int[]{15, 8, 5, 5}));
		GristRegistry.addGristConversion(new ItemStack(Items.RECORD_BLOCKS), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.caulk, MinestuckGrist.ruby, MinestuckGrist.rust}, new int[]{15, 8, 5, 5}));
		GristRegistry.addGristConversion(new ItemStack(Items.RECORD_CAT), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.caulk, MinestuckGrist.uranium, MinestuckGrist.shale}, new int[]{15, 8, 2, 5}));
		GristRegistry.addGristConversion(new ItemStack(Items.RECORD_CHIRP), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.caulk, MinestuckGrist.ruby, MinestuckGrist.garnet}, new int[]{15, 8, 5, 5}));
		GristRegistry.addGristConversion(new ItemStack(Items.RECORD_FAR), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.caulk, MinestuckGrist.uranium, MinestuckGrist.sulfur}, new int[]{15, 8, 2, 5}));
		GristRegistry.addGristConversion(new ItemStack(Items.RECORD_MALL), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.caulk, MinestuckGrist.amethyst, MinestuckGrist.tar}, new int[]{15, 8, 5, 5}));
		GristRegistry.addGristConversion(new ItemStack(Items.RECORD_MELLOHI), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.caulk, MinestuckGrist.marble, MinestuckGrist.shale}, new int[]{15, 8, 5, 5}));
		GristRegistry.addGristConversion(new ItemStack(Items.RECORD_STAL), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.caulk, MinestuckGrist.tar, MinestuckGrist.mercury}, new int[]{15, 8, 5, 5}));
		GristRegistry.addGristConversion(new ItemStack(Items.RECORD_STRAD), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.caulk, MinestuckGrist.chalk, MinestuckGrist.quartz}, new int[]{15, 8, 5, 5}));
		GristRegistry.addGristConversion(new ItemStack(Items.RECORD_WAIT), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.caulk, MinestuckGrist.cobalt, MinestuckGrist.quartz}, new int[]{15, 8, 5, 5}));
		GristRegistry.addGristConversion(new ItemStack(Items.RECORD_WARD), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.caulk, MinestuckGrist.iodine, MinestuckGrist.gold}, new int[]{15, 8, 5, 2}));
		GristRegistry.addGristConversion(new ItemStack(Items.REDSTONE), new GristSet(new Grist[]{MinestuckGrist.garnet}, new int[]{4}));
		GristRegistry.addGristConversion(new ItemStack(Items.REEDS), new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.iodine}, new int[]{3, 2}));
		GristRegistry.addGristConversion(new ItemStack(Items.ROTTEN_FLESH), new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.iodine}, new int[]{1, 1}));
		GristRegistry.addGristConversion(new ItemStack(Items.SADDLE), new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.iodine, MinestuckGrist.chalk}, new int[]{16, 7, 14}));
		GristRegistry.addGristConversion(new ItemStack(Items.SKULL, 1, 0), true, new GristSet(new Grist[]{MinestuckGrist.chalk}, new int[]{28}));
		GristRegistry.addGristConversion(new ItemStack(Items.SKULL, 1, 1), true, new GristSet(new Grist[]{MinestuckGrist.uranium, MinestuckGrist.tar, MinestuckGrist.diamond}, new int[]{35, 48, 16}));
		GristRegistry.addGristConversion(new ItemStack(Items.SKULL, 1, 2), true, new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.iodine}, new int[]{5, 20}));
		GristRegistry.addGristConversion(new ItemStack(Items.SKULL, 1, 3), true, new GristSet(new Grist[]{MinestuckGrist.artifact}, new int[]{10}));
		GristRegistry.addGristConversion(new ItemStack(Items.SKULL, 1, 4), true, new GristSet(new Grist[]{MinestuckGrist.sulfur, MinestuckGrist.chalk}, new int[]{10, 18}));
		GristRegistry.addGristConversion(new ItemStack(Items.SKULL, 1, 5), true, new GristSet(new Grist[]{MinestuckGrist.uranium, MinestuckGrist.tar, MinestuckGrist.zillium}, new int[]{25, 70, 1}));
		GristRegistry.addGristConversion(new ItemStack(Items.SLIME_BALL), new GristSet(new Grist[]{MinestuckGrist.caulk}, new int[]{8}));
		GristRegistry.addGristConversion(new ItemStack(Items.SNOWBALL), new GristSet(new Grist[]{MinestuckGrist.cobalt}, new int[]{1}));
		GristRegistry.addGristConversion(new ItemStack(Items.SPIDER_EYE), new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.iodine}, new int[]{6, 2}));
		GristRegistry.addGristConversion(new ItemStack(Items.STRING), new GristSet(new Grist[]{MinestuckGrist.chalk}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(Items.SHULKER_SHELL), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.diamond, MinestuckGrist.uranium, MinestuckGrist.mercury}, new int[]{800, 24, 58, 20}));
		GristRegistry.addGristConversion(new ItemStack(Items.TOTEM_OF_UNDYING), new GristSet(new Grist[]{MinestuckGrist.diamond, MinestuckGrist.uranium, MinestuckGrist.ruby, MinestuckGrist.gold}, new int[]{200, 350, 90, 90}));
		GristRegistry.addGristConversion(new ItemStack(Items.SUGAR), new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.amber}, new int[]{2, 3}));
		registerContainerlessCost(new ItemStack(Items.WATER_BUCKET), new GristSet(new Grist[]{MinestuckGrist.cobalt}, new int[]{4}));
		GristRegistry.addGristConversion(new ItemStack(Items.WHEAT), new GristSet(new Grist[]{MinestuckGrist.iodine}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(Items.WHEAT_SEEDS), new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.iodine}, new int[]{1, 1}));
		GristRegistry.addGristConversion(new ItemStack(Items.WRITABLE_BOOK), false, new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.iodine}, new int[]{16, 2}));
		GristRegistry.addGristConversion(new ItemStack(Items.PRISMARINE_CRYSTALS), new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.diamond, MinestuckGrist.amethyst}, new int[]{5, 2, 4}));
		GristRegistry.addGristConversion(new ItemStack(Items.PRISMARINE_SHARD), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.cobalt}, new int[]{3, 3}));
		GristRegistry.addGristConversion(new ItemStack(Items.RABBIT_HIDE), new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.chalk}, new int[]{1, 1}));
		GristRegistry.addGristConversion(new ItemStack(Items.RABBIT_FOOT), new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.chalk, MinestuckGrist.rust}, new int[]{10, 12, 3}));
		GristRegistry.addGristConversion(new ItemStack(Items.CHORUS_FRUIT), new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.shale}, new int[]{2, 4}));
		GristRegistry.addGristConversion(new ItemStack(Items.CHORUS_FRUIT_POPPED), new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.shale}, new int[]{2, 4}));
		GristRegistry.addGristConversion(new ItemStack(Items.BEETROOT), new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.caulk}, new int[]{1, 2}));
		GristRegistry.addGristConversion(new ItemStack(Items.BEETROOT_SEEDS), new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.iodine}, new int[]{2, 2}));
		GristRegistry.addGristConversion(new ItemStack(Items.ELYTRA), new GristSet(new Grist[]{MinestuckGrist.diamond, MinestuckGrist.sulfur, MinestuckGrist.caulk}, new int[]{51, 38, 65}));
		GristRegistry.addGristConversion(new ItemStack(Items.DRAGON_BREATH), new GristSet(new Grist[]{MinestuckGrist.ruby, MinestuckGrist.sulfur}, new int[]{4, 13}));
		GristRegistry.addGristConversion(new ItemStack(Items.CARROT_ON_A_STICK), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.amber, MinestuckGrist.chalk}, new int[]{3, 3, 5}));

		GristRegistry.addGristConversion(new ItemStack(Blocks.CONCRETE, 1, 15), true, new GristSet(new Grist[]{MinestuckGrist.tar, MinestuckGrist.cobalt, MinestuckGrist.build}, new int[]{1, 4, 2}));                                                //Black
		GristRegistry.addGristConversion(new ItemStack(Blocks.CONCRETE, 1, 14), true, new GristSet(new Grist[]{MinestuckGrist.garnet, MinestuckGrist.cobalt, MinestuckGrist.build}, new int[]{1, 4, 2}));                                            //Red
		GristRegistry.addGristConversion(new ItemStack(Blocks.CONCRETE, 1, 5), true, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.cobalt, MinestuckGrist.build}, new int[]{1, 4, 2}));                                            //Lime
		GristRegistry.addGristConversion(new ItemStack(Blocks.CONCRETE, 1, 4), true, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.cobalt, MinestuckGrist.build}, new int[]{1, 4, 2}));                                            //Yellow
		GristRegistry.addGristConversion(new ItemStack(Blocks.CONCRETE, 1, 3), true, new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.chalk, MinestuckGrist.cobalt, MinestuckGrist.build}, new int[]{1, 1, 4, 2}));                        //Light Blue
		GristRegistry.addGristConversion(new ItemStack(Blocks.CONCRETE, 1, 2), true, new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.garnet, MinestuckGrist.cobalt, MinestuckGrist.build}, new int[]{1, 1, 4, 2}));                    //Magenta
		GristRegistry.addGristConversion(new ItemStack(Blocks.CONCRETE, 1, 1), true, new GristSet(new Grist[]{MinestuckGrist.garnet, MinestuckGrist.amber, MinestuckGrist.cobalt, MinestuckGrist.build}, new int[]{1, 1, 4, 2}));                        //Orange
		GristRegistry.addGristConversion(new ItemStack(Blocks.CONCRETE, 1, 13), true, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.iodine, MinestuckGrist.cobalt, MinestuckGrist.build}, new int[]{1, 1, 4, 2}));                        //Green
		GristRegistry.addGristConversion(new ItemStack(Blocks.CONCRETE, 1, 12), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.amber, MinestuckGrist.cobalt, MinestuckGrist.build}, new int[]{1, 1, 4, 2}));                        //Brown
		GristRegistry.addGristConversion(new ItemStack(Blocks.CONCRETE, 1, 11), true, new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.cobalt, MinestuckGrist.build}, new int[]{1, 4, 2}));                                            //Blue
		GristRegistry.addGristConversion(new ItemStack(Blocks.CONCRETE, 1, 10), true, new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.garnet, MinestuckGrist.cobalt, MinestuckGrist.build}, new int[]{1, 1, 4, 2}));                        //Purple
		GristRegistry.addGristConversion(new ItemStack(Blocks.CONCRETE, 1, 9), true, new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.amber, MinestuckGrist.iodine, MinestuckGrist.cobalt, MinestuckGrist.build}, new int[]{1, 1, 1, 4, 2})); //Cyan
		GristRegistry.addGristConversion(new ItemStack(Blocks.CONCRETE, 1, 8), true, new GristSet(new Grist[]{MinestuckGrist.tar, MinestuckGrist.chalk, MinestuckGrist.cobalt, MinestuckGrist.build}, new int[]{1, 1, 4, 2}));                            //Light Gray
		GristRegistry.addGristConversion(new ItemStack(Blocks.CONCRETE, 1, 7), true, new GristSet(new Grist[]{MinestuckGrist.tar, MinestuckGrist.chalk, MinestuckGrist.cobalt, MinestuckGrist.build}, new int[]{1, 1, 4, 2}));                            //Gray
		GristRegistry.addGristConversion(new ItemStack(Blocks.CONCRETE, 1, 6), true, new GristSet(new Grist[]{MinestuckGrist.garnet, MinestuckGrist.chalk, MinestuckGrist.cobalt, MinestuckGrist.build}, new int[]{1, 1, 4, 2}));                        //Pink
		GristRegistry.addGristConversion(new ItemStack(Blocks.CONCRETE, 1, 0), true, new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.cobalt, MinestuckGrist.build}, new int[]{1, 4, 2}));                        //White

		GristRegistry.addGristConversion(new ItemStack(Blocks.BLACK_GLAZED_TERRACOTTA), true, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.shale, MinestuckGrist.tar}, new int[]{6, 4, 12}));                                                //Black
		GristRegistry.addGristConversion(new ItemStack(Blocks.RED_GLAZED_TERRACOTTA), true, new GristSet(new Grist[]{MinestuckGrist.garnet, MinestuckGrist.build, MinestuckGrist.shale, MinestuckGrist.tar}, new int[]{1, 4, 12, 2}));                                            //Red
		GristRegistry.addGristConversion(new ItemStack(Blocks.LIME_GLAZED_TERRACOTTA), true, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.build, MinestuckGrist.shale, MinestuckGrist.tar}, new int[]{1, 4, 12, 2}));                                            //Lime
		GristRegistry.addGristConversion(new ItemStack(Blocks.YELLOW_GLAZED_TERRACOTTA), true, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.build, MinestuckGrist.shale, MinestuckGrist.tar}, new int[]{1, 4, 12, 2}));                                            //Yellow
		GristRegistry.addGristConversion(new ItemStack(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA), true, new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.chalk, MinestuckGrist.build, MinestuckGrist.shale, MinestuckGrist.tar}, new int[]{1, 1, 4, 12, 2}));                        //Light Blue
		GristRegistry.addGristConversion(new ItemStack(Blocks.MAGENTA_GLAZED_TERRACOTTA), true, new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.garnet, MinestuckGrist.build, MinestuckGrist.shale, MinestuckGrist.tar}, new int[]{1, 1, 4, 12, 2}));                    //Magenta
		GristRegistry.addGristConversion(new ItemStack(Blocks.ORANGE_GLAZED_TERRACOTTA), true, new GristSet(new Grist[]{MinestuckGrist.garnet, MinestuckGrist.amber, MinestuckGrist.build, MinestuckGrist.shale, MinestuckGrist.tar}, new int[]{1, 1, 4, 12, 2}));                        //Orange
		GristRegistry.addGristConversion(new ItemStack(Blocks.GREEN_GLAZED_TERRACOTTA), true, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.iodine, MinestuckGrist.build, MinestuckGrist.shale, MinestuckGrist.tar}, new int[]{1, 1, 4, 12, 2}));                        //Green
		GristRegistry.addGristConversion(new ItemStack(Blocks.BROWN_GLAZED_TERRACOTTA), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.amber, MinestuckGrist.build, MinestuckGrist.shale, MinestuckGrist.tar}, new int[]{1, 1, 4, 12, 2}));                        //Brown
		GristRegistry.addGristConversion(new ItemStack(Blocks.BLUE_GLAZED_TERRACOTTA), true, new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.build, MinestuckGrist.shale, MinestuckGrist.tar}, new int[]{1, 4, 12, 2}));                                            //Blue
		GristRegistry.addGristConversion(new ItemStack(Blocks.PURPLE_GLAZED_TERRACOTTA), true, new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.garnet, MinestuckGrist.build, MinestuckGrist.shale, MinestuckGrist.tar}, new int[]{1, 1, 4, 12, 2}));                        //Purple
		GristRegistry.addGristConversion(new ItemStack(Blocks.CYAN_GLAZED_TERRACOTTA), true, new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.amber, MinestuckGrist.iodine, MinestuckGrist.build, MinestuckGrist.shale, MinestuckGrist.tar}, new int[]{1, 1, 1, 4, 12, 2})); //Cyan
		GristRegistry.addGristConversion(new ItemStack(Blocks.SILVER_GLAZED_TERRACOTTA), true, new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.build, MinestuckGrist.shale, MinestuckGrist.tar}, new int[]{1, 4, 12, 3}));                            //Light Gray
		GristRegistry.addGristConversion(new ItemStack(Blocks.GRAY_GLAZED_TERRACOTTA), true, new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.build, MinestuckGrist.shale, MinestuckGrist.tar}, new int[]{1, 4, 12, 5}));                            //Gray
		GristRegistry.addGristConversion(new ItemStack(Blocks.PINK_GLAZED_TERRACOTTA), true, new GristSet(new Grist[]{MinestuckGrist.garnet, MinestuckGrist.chalk, MinestuckGrist.build, MinestuckGrist.shale, MinestuckGrist.tar}, new int[]{1, 1, 4, 12, 2}));                        //Pink
		GristRegistry.addGristConversion(new ItemStack(Blocks.WHITE_GLAZED_TERRACOTTA), true, new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.build, MinestuckGrist.shale, MinestuckGrist.tar}, new int[]{1, 4, 12, 2}));                        //White

		//Ores
		if (oreMultiplier != 0)
		{
			GristRegistry.addGristConversion("oreCoal", new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.tar}, new int[]{4, 8 * oreMultiplier}));
			GristRegistry.addGristConversion("oreIron", new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.rust}, new int[]{4, 9 * oreMultiplier}));
			GristRegistry.addGristConversion("oreGold", new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.gold}, new int[]{4, 9 * oreMultiplier}));
			GristRegistry.addGristConversion("oreRedstone", new GristSet(new Grist[]{MinestuckGrist.garnet, MinestuckGrist.build}, new int[]{16 * oreMultiplier, 4}));
			GristRegistry.addGristConversion("oreLapis", new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.build}, new int[]{16 * oreMultiplier, 4}));
			GristRegistry.addGristConversion("oreDiamond", new GristSet(new Grist[]{MinestuckGrist.diamond, MinestuckGrist.build}, new int[]{18 * oreMultiplier, 4}));
			GristRegistry.addGristConversion("oreEmerald", new GristSet(new Grist[]{MinestuckGrist.ruby, MinestuckGrist.diamond, MinestuckGrist.build}, new int[]{9 * oreMultiplier, 9 * oreMultiplier, 4}));
			GristRegistry.addGristConversion("oreQuartz", new GristSet(new Grist[]{MinestuckGrist.quartz, MinestuckGrist.marble, MinestuckGrist.build}, new int[]{8 * oreMultiplier, 2 * oreMultiplier, 2}));
		}
		GristRegistry.addGristConversion(new ItemStack(Items.COAL, 1, 0), true, new GristSet(new Grist[]{MinestuckGrist.tar}, new int[]{8}));
		GristRegistry.addGristConversion(new ItemStack(Items.COAL, 1, 1), true, new GristSet(new Grist[]{MinestuckGrist.tar, MinestuckGrist.amber}, new int[]{6, 2}));
		GristRegistry.addGristConversion("ingotGold", new GristSet(new Grist[]{MinestuckGrist.gold}, new int[]{9}));
		GristRegistry.addGristConversion("ingotIron", new GristSet(new Grist[]{MinestuckGrist.rust}, new int[]{9}));
		GristRegistry.addGristConversion(new ItemStack(Items.QUARTZ), false, new GristSet(new Grist[]{MinestuckGrist.quartz, MinestuckGrist.marble}, new int[]{4, 1}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.QUARTZ_BLOCK), false, new GristSet(new Grist[]{MinestuckGrist.quartz, MinestuckGrist.marble}, new int[]{16, 4}));
		GristRegistry.addGristConversion(new ItemStack(Items.EMERALD), false, new GristSet(new Grist[]{MinestuckGrist.ruby, MinestuckGrist.diamond}, new int[]{9, 9}));
		GristRegistry.addGristConversion(new ItemStack(Items.DIAMOND), false, new GristSet(new Grist[]{MinestuckGrist.diamond}, new int[]{18}));

		//Plants
		GristRegistry.addGristConversion(new ItemStack(Blocks.LEAVES2), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{1}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.YELLOW_FLOWER), false, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.iodine}, new int[]{4, 1}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.RED_FLOWER, 1, 0), true, new GristSet(new Grist[]{MinestuckGrist.garnet, MinestuckGrist.iodine}, new int[]{4, 1}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.RED_FLOWER, 1, 1), true, new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.chalk, MinestuckGrist.iodine}, new int[]{2, 2, 1}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.RED_FLOWER, 1, 2), true, new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.garnet, MinestuckGrist.iodine}, new int[]{1, 3, 1}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.RED_FLOWER, 1, 3), true, new GristSet(new Grist[]{MinestuckGrist.tar, MinestuckGrist.chalk, MinestuckGrist.iodine}, new int[]{1, 3, 1}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.RED_FLOWER, 1, 4), true, new GristSet(new Grist[]{MinestuckGrist.tar, MinestuckGrist.chalk, MinestuckGrist.iodine}, new int[]{1, 3, 1}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.RED_FLOWER, 1, 5), true, new GristSet(new Grist[]{MinestuckGrist.garnet, MinestuckGrist.amber, MinestuckGrist.iodine}, new int[]{2, 2, 1}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.RED_FLOWER, 1, 6), true, new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.chalk, MinestuckGrist.iodine}, new int[]{2, 2, 1}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.RED_FLOWER, 1, 7), true, new GristSet(new Grist[]{MinestuckGrist.garnet, MinestuckGrist.iodine}, new int[]{4, 1}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.RED_FLOWER, 1, 8), true, new GristSet(new Grist[]{MinestuckGrist.tar, MinestuckGrist.chalk, MinestuckGrist.iodine}, new int[]{1, 3, 1}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.BROWN_MUSHROOM), false, new GristSet(new Grist[]{MinestuckGrist.iodine}, new int[]{5}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.RED_MUSHROOM), false, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.ruby}, new int[]{3, 2}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.CACTUS), false, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.iodine}, new int[]{4, 1}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.TALLGRASS), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{1}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.VINE), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.amber}, new int[]{2, 1}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.WATERLILY), false, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.iodine}, new int[]{4, 1}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.DOUBLE_PLANT, 1, 0), true, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.iodine}, new int[]{7, 2}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.DOUBLE_PLANT, 1, 1), true, new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.garnet, MinestuckGrist.iodine}, new int[]{2, 5, 2}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.DOUBLE_PLANT, 1, 2), true, new GristSet(new Grist[]{MinestuckGrist.amber}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.DOUBLE_PLANT, 1, 3), true, new GristSet(new Grist[]{MinestuckGrist.amber}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.DOUBLE_PLANT, 1, 4), true, new GristSet(new Grist[]{MinestuckGrist.garnet, MinestuckGrist.iodine}, new int[]{7, 2}));
		GristRegistry.addGristConversion(new ItemStack(Blocks.DOUBLE_PLANT, 1, 5), true, new GristSet(new Grist[]{MinestuckGrist.garnet, MinestuckGrist.chalk, MinestuckGrist.iodine}, new int[]{4, 4, 2}));

		//Food
		GristRegistry.addGristConversion(new ItemStack(Items.APPLE), false, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.shale}, new int[]{2, 2}));
		GristRegistry.addGristConversion(new ItemStack(Items.CARROT), false, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.chalk}, new int[]{3, 1}));
		GristRegistry.addGristConversion(new ItemStack(Items.POTATO), false, new GristSet(new Grist[]{MinestuckGrist.amber}, new int[]{4}));
		GristRegistry.addGristConversion(new ItemStack(Items.POISONOUS_POTATO), false, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.iodine}, new int[]{4, 2}));
		GristRegistry.addGristConversion(new ItemStack(Items.BAKED_POTATO), false, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.tar}, new int[]{4, 1}));
		GristRegistry.addGristConversion(new ItemStack(Items.MELON), false, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.caulk}, new int[]{1, 1}));
		GristRegistry.addGristConversion(new ItemStack(Items.EGG), false, new GristSet(new Grist[]{MinestuckGrist.amber}, new int[]{5}));
		GristRegistry.addGristConversion(new ItemStack(Items.BEEF), false, new GristSet(new Grist[]{MinestuckGrist.iodine}, new int[]{12}));
		GristRegistry.addGristConversion(new ItemStack(Items.PORKCHOP), false, new GristSet(new Grist[]{MinestuckGrist.iodine}, new int[]{10}));
		GristRegistry.addGristConversion(new ItemStack(Items.CHICKEN), false, new GristSet(new Grist[]{MinestuckGrist.iodine}, new int[]{10}));
		GristRegistry.addGristConversion(new ItemStack(Items.FISH, 1, 0), true, new GristSet(new Grist[]{MinestuckGrist.caulk, MinestuckGrist.amber, MinestuckGrist.cobalt}, new int[]{4, 4, 2}));
		GristRegistry.addGristConversion(new ItemStack(Items.MUTTON), new GristSet(MinestuckGrist.iodine, 10));
		GristRegistry.addGristConversion(new ItemStack(Items.RABBIT), new GristSet(MinestuckGrist.iodine, 8));
		GristRegistry.addGristConversion(new ItemStack(Items.COOKED_BEEF), false, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.tar}, new int[]{12, 1}));
		GristRegistry.addGristConversion(new ItemStack(Items.COOKED_PORKCHOP), false, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.tar}, new int[]{10, 4}));
		GristRegistry.addGristConversion(new ItemStack(Items.COOKED_CHICKEN), false, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.tar}, new int[]{10, 1}));
		GristRegistry.addGristConversion(new ItemStack(Items.COOKED_FISH, 1, 0), true, new GristSet(new Grist[]{MinestuckGrist.caulk, MinestuckGrist.amber, MinestuckGrist.cobalt, MinestuckGrist.tar}, new int[]{4, 4, 2, 1}));
		GristRegistry.addGristConversion(new ItemStack(Items.COOKED_MUTTON), new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.tar}, new int[]{10, 1}));
		GristRegistry.addGristConversion(new ItemStack(Items.COOKED_RABBIT), new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.tar}, new int[]{8, 1}));
		GristRegistry.addGristConversion(new ItemStack(Items.GOLDEN_APPLE, 1, 1), new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.gold, MinestuckGrist.uranium}, new int[]{4, 150, 10}));

		//Potions
		/*GristRegistry.addGristConversion(new ItemStack(Items.POTIONITEM, 1, 0), true, new GristSet(new GristType[] {GristType.build, GristType.cobalt}, new int[] {1, 4}));	//water
		GristRegistry.addGristConversion(new ItemStack(Items.POTIONITEM, 1, 8192), true, new GristSet(new GristType[] {GristType.build, GristType.cobalt}, new int[] {1, 4}));	//mundane
		GristRegistry.addGristConversion(new ItemStack(Items.POTIONITEM, 1, 64), true, new GristSet(new GristType[] {GristType.build, GristType.cobalt, GristType.garnet}, new int[] {1, 4, 1}));	//mundane from using redstone
		GristRegistry.addGristConversion(new ItemStack(Items.POTIONITEM, 1, 16), true, new GristSet(new GristType[] {GristType.build, GristType.cobalt, GristType.iodine, GristType.tar}, new int[] {1, 4, 1, 2}));	//awkward
		GristRegistry.addGristConversion(new ItemStack(Items.POTIONITEM, 1, 32), true, new GristSet(new GristType[] {GristType.build, GristType.cobalt, GristType.tar, GristType.chalk}, new int[] {1, 4, 1, 2}));	//thick
		GristRegistry.addGristConversion(new ItemStack(Items.POTIONITEM, 1, 8200), true, new GristSet(new GristType[] {GristType.build, GristType.cobalt, GristType.amber, GristType.iodine}, new int[] {1, 4, 3, 2}));	//weakness
		GristRegistry.addGristConversion(new ItemStack(Items.POTIONITEM, 1, 8193), true, new GristSet(new GristType[] {GristType.build, GristType.cobalt, GristType.iodine, GristType.tar, GristType.chalk}, new int[] {1, 8, 1, 2, 5}));	//regen*/
		//TODO Continue with potion grist costs
		/*GristRegistry.addGristConversion(new ItemStack(Items.potionitem, 1, 10), true, new GristSet(new GristType[] {GristType.build, GristType.cobalt, GristType.iodine, GristType.amber, GristType.tar}, new int[] {1, 4, 8, 6, 2}));	//slowness
		GristRegistry.addGristConversion(new ItemStack(Items.potionitem, 1, 12), true, new GristSet(new GristType[] {GristType.build, GristType.cobalt, GristType.gold, GristType.amber, GristType.iodine, GristType.tar, GristType.chalk}, new int[] {1, 4, 16, 7, 4, 2, 1}));	//harming
		GristRegistry.addGristConversion(new ItemStack(Items.potionitem, 1, 14), true, new GristSet(new GristType[] {GristType.build, GristType.cobalt, GristType.amber, GristType.chalk, GristType.gold, GristType.iodine, GristType.tar}, new int[] {1, 4, 9, 1, 16, 4, 2}));	//invisibility
		GristRegistry.addGristConversion(new ItemStack(Items.potionitem, 1, 2), true, new GristSet(new GristType[] {GristType.build, GristType.cobalt, GristType.iodine}, new int[] {1, 4, 4}));	//speed
		GristRegistry.addGristConversion(new ItemStack(Items.potionitem, 1, 3), true, new GristSet(new GristType[] {GristType.build, GristType.cobalt, GristType.tar, GristType.uranium, GristType.caulk}, new int[] {1, 4, 8, 1, 8}));	//fire resistance
		GristRegistry.addGristConversion(new ItemStack(Items.potionitem, 1, 4), true, new GristSet(new GristType[] {GristType.build, GristType.cobalt, GristType.amber, GristType.iodine}, new int[] {1, 4, 4, 2}));	//poison
		GristRegistry.addGristConversion(new ItemStack(Items.potionitem, 1, 5), true, new GristSet(new GristType[] {GristType.build, GristType.cobalt, GristType.gold, GristType.amber, GristType.chalk}, new int[] {1, 4, 16, 1, 1}));	//healing
		GristRegistry.addGristConversion(new ItemStack(Items.potionitem, 1, 6), true, new GristSet(new GristType[] {GristType.build, GristType.cobalt, GristType.amber, GristType.chalk, GristType.gold}, new int[] {1, 4, 3, 1, 16}));	//night vision
		GristRegistry.addGristConversion(new ItemStack(Items.potionitem, 1, 9), true, new GristSet(new GristType[] {GristType.build, GristType.tar, GristType.uranium}, new int[] {1, 8, 1}));	//strength*/

		GristRegistry.addGristConversion(new ItemStack(Blocks.SLIME_BLOCK), new GristSet(new Grist[]{MinestuckGrist.caulk}, new int[]{72}));

		GristRegistry.addGristConversion(new ItemStack(MinestuckItems.clawSickle), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.iodine, MinestuckGrist.artifact}, new int[]{5000, 4000, 1}));
		GristRegistry.addGristConversion(new ItemStack(MinestuckItems.dragonCane), new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.ruby, MinestuckGrist.mercury}, new int[]{200, 400, 300}));

		GristRegistry.addGristConversion(new ItemStack(MinestuckItems.royalDeringer), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.gold, MinestuckGrist.garnet, MinestuckGrist.diamond}, new int[]{1000, 2400, 20, 350}));
		GristRegistry.addGristConversion(new ItemStack(MinestuckItems.caledfwlch), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.chalk, MinestuckGrist.gold, MinestuckGrist.zillium}, new int[]{5000, 10000, 200, 1}));
		GristRegistry.addGristConversion(new ItemStack(MinestuckItems.caledscratch), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.chalk, MinestuckGrist.garnet, MinestuckGrist.uranium}, new int[]{2500, 3500, 1500, 1550}));
		GristRegistry.addGristConversion(new ItemStack(MinestuckItems.scarletRibbitar), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.ruby, MinestuckGrist.quartz, MinestuckGrist.diamond, MinestuckGrist.zillium}, new int[]{3000, 4000, 2000, 1000, 2000}));
		GristRegistry.addGristConversion(new ItemStack(MinestuckItems.doggMachete), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.cobalt, MinestuckGrist.chalk, MinestuckGrist.shale, MinestuckGrist.diamond}, new int[]{1000, 1000, 2500, 500, 100}));

		GristRegistry.addGristConversion(new ItemStack(MinestuckItems.scarletZillyhoo), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.zillium, MinestuckGrist.ruby, MinestuckGrist.quartz, MinestuckGrist.diamond}, new int[]{1200, 800, 600, 30, 15}));
		GristRegistry.addGristConversion(new ItemStack(MinestuckItems.zillyhooHammer), new GristSet(MinestuckGrist.zillium, 1000));
		GristRegistry.addGristConversion(new ItemStack(MinestuckItems.zillywairCutlass), new GristSet(MinestuckGrist.zillium, 1000));
		GristRegistry.addGristConversion(new ItemStack(MinestuckItems.popamaticVrillyhoo), new GristSet(new Grist[]{MinestuckGrist.zillium, MinestuckGrist.cobalt, MinestuckGrist.diamond}, new int[]{10000, 32768, 4096}));

		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.clawHammer), new ItemStack(zillyStone), MODE_AND, false, false, new ItemStack(MinestuckItems.zillyhooHammer));
		CombinationRegistry.addCombination(new ItemStack(Items.IRON_SWORD), new ItemStack(zillyStone), MODE_AND, false, false, new ItemStack(MinestuckItems.zillywairCutlass));

		//Set up Punch Designix recipes

		//Wood
		final String[] woodDict = {"logWood", "plankWood", "slabWood", "stairWood", "treeSapling", "treeLeaves", "doorWood", "fenceWood", "fencegateWood"};
		final ItemStack[][] woodItems = {
				{new ItemStack(Blocks.LOG, 1, 0), new ItemStack(Blocks.LOG, 1, 1), new ItemStack(Blocks.LOG, 1, 2), new ItemStack(Blocks.LOG, 1, 3), new ItemStack(Blocks.LOG2, 1, 0), new ItemStack(Blocks.LOG2, 1, 1)},
				{new ItemStack(Blocks.PLANKS, 1, 0), new ItemStack(Blocks.PLANKS, 1, 1), new ItemStack(Blocks.PLANKS, 1, 2), new ItemStack(Blocks.PLANKS, 1, 3), new ItemStack(Blocks.PLANKS, 1, 4), new ItemStack(Blocks.PLANKS, 1, 5)},
				{new ItemStack(Blocks.WOODEN_SLAB, 1, 0), new ItemStack(Blocks.WOODEN_SLAB, 1, 1), new ItemStack(Blocks.WOODEN_SLAB, 1, 2), new ItemStack(Blocks.WOODEN_SLAB, 1, 3), new ItemStack(Blocks.WOODEN_SLAB, 1, 4), new ItemStack(Blocks.WOODEN_SLAB, 1, 5)},
				{new ItemStack(Blocks.OAK_STAIRS), new ItemStack(Blocks.SPRUCE_STAIRS), new ItemStack(Blocks.BIRCH_STAIRS), new ItemStack(Blocks.JUNGLE_STAIRS), new ItemStack(Blocks.ACACIA_STAIRS), new ItemStack(Blocks.DARK_OAK_STAIRS)},
				{new ItemStack(Blocks.SAPLING, 1, 0), new ItemStack(Blocks.SAPLING, 1, 1), new ItemStack(Blocks.SAPLING, 1, 2), new ItemStack(Blocks.SAPLING, 1, 3), new ItemStack(Blocks.SAPLING, 1, 4), new ItemStack(Blocks.SAPLING, 1, 5)},
				{new ItemStack(Blocks.LEAVES, 1, 0), new ItemStack(Blocks.LEAVES, 1, 1), new ItemStack(Blocks.LEAVES, 1, 2), new ItemStack(Blocks.LEAVES, 1, 3), new ItemStack(Blocks.LEAVES2, 1, 0), new ItemStack(Blocks.LEAVES2, 1, 1)},
				{new ItemStack(Items.OAK_DOOR), new ItemStack(Items.SPRUCE_DOOR), new ItemStack(Items.BIRCH_DOOR), new ItemStack(Items.JUNGLE_DOOR), new ItemStack(Items.ACACIA_DOOR), new ItemStack(Items.DARK_OAK_DOOR)},
				{new ItemStack(Blocks.OAK_FENCE), new ItemStack(Blocks.SPRUCE_FENCE), new ItemStack(Blocks.BIRCH_FENCE), new ItemStack(Blocks.JUNGLE_FENCE), new ItemStack(Blocks.ACACIA_FENCE), new ItemStack(Blocks.DARK_OAK_FENCE)},
				{new ItemStack(Blocks.OAK_FENCE_GATE), new ItemStack(Blocks.SPRUCE_FENCE_GATE), new ItemStack(Blocks.BIRCH_FENCE_GATE), new ItemStack(Blocks.JUNGLE_FENCE_GATE), new ItemStack(Blocks.ACACIA_FENCE_GATE), new ItemStack(Blocks.DARK_OAK_FENCE_GATE)}};
		for (int i = 6; i < woodItems.length; i++)
			for (ItemStack stack : woodItems[i])
				checkRegistered(stack, woodDict[i]);

		for (int i1 = 0; i1 < woodItems.length; i1++)
		{
			CombinationRegistry.addCombination(woodItems[i1][0], woodItems[i1][1], MODE_OR, woodItems[i1][5]);    //Oak | spruce -> dark oak
			CombinationRegistry.addCombination(woodItems[i1][2], woodItems[i1][3], MODE_OR, woodItems[i1][4]);    //Birch | jungle -> acacia
		}
		for (int i2 = 0; i2 < woodItems[0].length; i2++)
		{
			CombinationRegistry.addCombination(woodItems[1][i2], woodItems[2][i2], MODE_OR, woodItems[3][i2]);    //plank | slab -> stair
			CombinationRegistry.addCombination(woodItems[0][i2], woodItems[5][i2], MODE_OR, woodItems[4][i2]);    //leaf | log -> sapling
			CombinationRegistry.addCombination(woodItems[6][i2], woodItems[7][i2], MODE_OR, woodItems[8][i2]);    //door | fence -> fence gate
			CombinationRegistry.addCombination(new ItemStack(Items.WHEAT_SEEDS), woodItems[5][i2], MODE_AND, new ItemStack(Blocks.SAPLING, 1, i2));
			CombinationRegistry.addCombination(new ItemStack(Items.STICK), woodItems[5][i2], MODE_AND, new ItemStack(Blocks.SAPLING, 1, i2));
		}
		CombinationRegistry.addCombination(woodItems[1][0], woodItems[2][1], MODE_OR, woodItems[3][5]);
		CombinationRegistry.addCombination(woodItems[2][0], woodItems[1][1], MODE_OR, woodItems[3][5]);
		CombinationRegistry.addCombination(woodItems[0][0], woodItems[5][1], MODE_OR, woodItems[4][5]);
		CombinationRegistry.addCombination(woodItems[5][0], woodItems[0][1], MODE_OR, woodItems[4][5]);
		CombinationRegistry.addCombination(woodItems[6][0], woodItems[7][1], MODE_OR, woodItems[8][5]);
		CombinationRegistry.addCombination(woodItems[7][0], woodItems[6][1], MODE_OR, woodItems[8][5]);

		CombinationRegistry.addCombination(woodItems[1][2], woodItems[2][3], MODE_OR, woodItems[3][4]);
		CombinationRegistry.addCombination(woodItems[2][2], woodItems[1][3], MODE_OR, woodItems[3][4]);
		CombinationRegistry.addCombination(woodItems[0][2], woodItems[5][3], MODE_OR, woodItems[4][4]);
		CombinationRegistry.addCombination(woodItems[5][2], woodItems[0][3], MODE_OR, woodItems[4][4]);
		CombinationRegistry.addCombination(woodItems[6][2], woodItems[7][3], MODE_OR, woodItems[8][4]);
		CombinationRegistry.addCombination(woodItems[7][2], woodItems[6][3], MODE_OR, woodItems[8][4]);

		CombinationRegistry.addCombination("doorWood", Items.IRON_INGOT, WILDCARD_VALUE, MODE_AND, new ItemStack(Items.IRON_DOOR));
		CombinationRegistry.addCombination("fenceWood", Blocks.NETHER_BRICK, WILDCARD_VALUE, MODE_AND, new ItemStack(Blocks.NETHER_BRICK_FENCE));
		CombinationRegistry.addCombination("stairWood", Blocks.NETHER_BRICK, WILDCARD_VALUE, MODE_AND, new ItemStack(Blocks.NETHER_BRICK_STAIRS));
		CombinationRegistry.addCombination("fenceWood", Items.NETHERBRICK, WILDCARD_VALUE, MODE_AND, new ItemStack(Blocks.NETHER_BRICK_FENCE));
		CombinationRegistry.addCombination("stairWood", Items.NETHERBRICK, WILDCARD_VALUE, MODE_AND, new ItemStack(Blocks.NETHER_BRICK_STAIRS));
		CombinationRegistry.addCombination("doorWood", "slabWood", MODE_AND, new ItemStack(Blocks.TRAPDOOR));
		CombinationRegistry.addCombination("logWood", Items.COAL, 0, MODE_AND, new ItemStack(Items.COAL, 1, 1));

		CombinationRegistry.addCombination(new ItemStack(Blocks.GRAVEL), new ItemStack(Blocks.SAND), MODE_AND, new ItemStack(Blocks.CONCRETE, 1, 8));

		//Dye
		Block[] coloredBlocks = {Blocks.WOOL, Blocks.STAINED_HARDENED_CLAY, Blocks.STAINED_GLASS, Blocks.STAINED_GLASS_PANE};
		for (int i1 = 0; i1 < coloredBlocks.length; i1++)
		{
			for (EnumDyeColor color : EnumDyeColor.values())
			{
				if (color != EnumDyeColor.WHITE)
					CombinationRegistry.addCombination(new ItemStack(Items.DYE, 1, color.getDyeDamage()), new ItemStack(coloredBlocks[i1], 1, EnumDyeColor.WHITE.getMetadata()), MODE_OR, new ItemStack(coloredBlocks[i1], 1, color.getMetadata()));
			}
		}
		for (int i1 = 0; i1 < coloredBlocks.length; i1++)
		{
			for (EnumDyeColor color : EnumDyeColor.values())
			{
				if (color != EnumDyeColor.SILVER)
					CombinationRegistry.addCombination(new ItemStack(Items.DYE, 1, color.getDyeDamage()), new ItemStack(Blocks.CONCRETE_POWDER, 1, EnumDyeColor.WHITE.getMetadata()), MODE_OR, new ItemStack(Blocks.CONCRETE_POWDER, 1, color.getMetadata()));
			}
		}
		for (EnumDyeColor color : EnumDyeColor.values())
		{
			CombinationRegistry.addCombination(new ItemStack(Blocks.GLASS), new ItemStack(Items.DYE, 1, color.getDyeDamage()), MODE_AND, new ItemStack(Blocks.STAINED_GLASS, 1, color.getMetadata()));
			CombinationRegistry.addCombination(new ItemStack(Blocks.GLASS_PANE), new ItemStack(Items.DYE, 1, color.getDyeDamage()), MODE_AND, new ItemStack(Blocks.STAINED_GLASS_PANE, 1, color.getMetadata()));
			CombinationRegistry.addCombination(new ItemStack(Blocks.HARDENED_CLAY), new ItemStack(Items.DYE, 1, color.getDyeDamage()), MODE_AND, new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, color.getMetadata()));
		}

		//ore related
		CombinationRegistry.addCombination(new ItemStack(Items.COAL), new ItemStack(Blocks.STONE), MODE_OR, new ItemStack(Blocks.COAL_BLOCK));
		CombinationRegistry.addCombination(new ItemStack(Items.DIAMOND), new ItemStack(Blocks.STONE), MODE_OR, new ItemStack(Blocks.DIAMOND_BLOCK));
		CombinationRegistry.addCombination(new ItemStack(Items.DYE, 1, 4), new ItemStack(Blocks.STONE), MODE_OR, new ItemStack(Blocks.LAPIS_BLOCK));
		CombinationRegistry.addCombination(new ItemStack(Items.EMERALD), new ItemStack(Blocks.STONE), MODE_OR, new ItemStack(Blocks.EMERALD_BLOCK));
		CombinationRegistry.addCombination(new ItemStack(Items.GOLD_INGOT), new ItemStack(Blocks.STONE), MODE_OR, new ItemStack(Blocks.GOLD_BLOCK));
		CombinationRegistry.addCombination(new ItemStack(Items.IRON_INGOT), new ItemStack(Blocks.STONE), MODE_OR, new ItemStack(Blocks.IRON_BLOCK));
		CombinationRegistry.addCombination(new ItemStack(Items.QUARTZ), new ItemStack(Blocks.STONE), MODE_OR, new ItemStack(Blocks.QUARTZ_BLOCK));
		CombinationRegistry.addCombination(new ItemStack(Items.REDSTONE), new ItemStack(Blocks.STONE), MODE_OR, new ItemStack(Blocks.REDSTONE_BLOCK));

		if (oreMultiplier != 0)
		{
			CombinationRegistry.addCombination(new ItemStack(Items.COAL), new ItemStack(Blocks.STONE), MODE_AND, new ItemStack(Blocks.COAL_ORE));
			CombinationRegistry.addCombination(new ItemStack(Items.DIAMOND), new ItemStack(Blocks.STONE), MODE_AND, new ItemStack(Blocks.DIAMOND_ORE));
			CombinationRegistry.addCombination(new ItemStack(Items.DYE, 1, 4), new ItemStack(Blocks.STONE), MODE_AND, new ItemStack(Blocks.LAPIS_ORE));
			CombinationRegistry.addCombination(new ItemStack(Items.EMERALD), new ItemStack(Blocks.STONE), MODE_AND, new ItemStack(Blocks.EMERALD_ORE));
			CombinationRegistry.addCombination(new ItemStack(Items.GOLD_INGOT), new ItemStack(Blocks.STONE), MODE_AND, new ItemStack(Blocks.GOLD_ORE));
			CombinationRegistry.addCombination(new ItemStack(Items.IRON_INGOT), new ItemStack(Blocks.STONE), MODE_AND, new ItemStack(Blocks.IRON_ORE));
			CombinationRegistry.addCombination(new ItemStack(Items.QUARTZ), new ItemStack(Blocks.NETHERRACK), MODE_AND, new ItemStack(Blocks.QUARTZ_ORE));
			CombinationRegistry.addCombination(new ItemStack(Items.REDSTONE), new ItemStack(Blocks.STONE), MODE_AND, new ItemStack(Blocks.REDSTONE_ORE));
		}

		CombinationRegistry.addCombination(new ItemStack(Blocks.STONE), new ItemStack(Blocks.STONEBRICK, 1, 2), MODE_AND, new ItemStack(Blocks.STONEBRICK, 1, 0));
		CombinationRegistry.addCombination(new ItemStack(Blocks.STONE), new ItemStack(Blocks.STONEBRICK, 1, 2), MODE_OR, new ItemStack(Blocks.COBBLESTONE));
		CombinationRegistry.addCombination(new ItemStack(Blocks.STONE), new ItemStack(Blocks.GRAVEL), MODE_OR, new ItemStack(Blocks.COBBLESTONE));
		CombinationRegistry.addCombination(new ItemStack(Blocks.STONE), new ItemStack(Blocks.SAND, 1, 0), MODE_OR, new ItemStack(Blocks.SANDSTONE, 1, 0));
		CombinationRegistry.addCombination(new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.SANDSTONE, 1, 2), MODE_AND, new ItemStack(Blocks.STONE));
		CombinationRegistry.addCombination(new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.SANDSTONE, 1, 2), MODE_OR, new ItemStack(Blocks.SANDSTONE, 1, 0));
		CombinationRegistry.addCombination(new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.SAND), MODE_AND, true, false, new ItemStack(Blocks.GRAVEL));
		CombinationRegistry.addCombination(new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.SAND, 1, 0), MODE_OR, new ItemStack(Blocks.SANDSTONE, 1, 0));
		CombinationRegistry.addCombination(new ItemStack(Blocks.GRAVEL), new ItemStack(Blocks.SANDSTONE, 1, 2), MODE_OR, new ItemStack(Blocks.SANDSTONE, 1, 0));
		CombinationRegistry.addCombination(new ItemStack(Blocks.SANDSTONE, 1, 2), new ItemStack(Blocks.SAND, 1, 0), MODE_OR, new ItemStack(Blocks.SANDSTONE, 1, 0));

		//misc
		CombinationRegistry.addCombination(new ItemStack(Blocks.COBBLESTONE), new ItemStack(Items.COAL), MODE_AND, new ItemStack(Blocks.FURNACE));
		CombinationRegistry.addCombination(new ItemStack(Blocks.COBBLESTONE), new ItemStack(Items.WHEAT_SEEDS), MODE_OR, new ItemStack(Blocks.MOSSY_COBBLESTONE));
		CombinationRegistry.addCombination(new ItemStack(Blocks.COBBLESTONE_WALL), new ItemStack(Items.WHEAT_SEEDS), MODE_OR, new ItemStack(Blocks.COBBLESTONE_WALL, 1, 1));
		CombinationRegistry.addCombination(new ItemStack(Blocks.DIRT), new ItemStack(Blocks.TALLGRASS), MODE_OR, true, false, new ItemStack(Blocks.GRASS));
		CombinationRegistry.addCombination(new ItemStack(Blocks.DIRT), new ItemStack(Items.WHEAT_SEEDS), MODE_AND, new ItemStack(Blocks.GRASS));
		CombinationRegistry.addCombination(new ItemStack(Blocks.GRASS), new ItemStack(Blocks.BROWN_MUSHROOM), MODE_AND, new ItemStack(Blocks.MYCELIUM));
		CombinationRegistry.addCombination(new ItemStack(Blocks.GRASS), new ItemStack(Blocks.RED_MUSHROOM), MODE_AND, new ItemStack(Blocks.MYCELIUM));
		CombinationRegistry.addCombination(new ItemStack(Blocks.LADDER), new ItemStack(Items.IRON_INGOT), MODE_AND, new ItemStack(Blocks.RAIL));
		CombinationRegistry.addCombination(new ItemStack(Blocks.NETHERRACK), new ItemStack(Blocks.BRICK_BLOCK), MODE_AND, new ItemStack(Blocks.NETHER_BRICK));
		CombinationRegistry.addCombination(new ItemStack(Blocks.NETHERRACK), new ItemStack(Items.BRICK), MODE_AND, new ItemStack(Blocks.NETHER_BRICK));
		CombinationRegistry.addCombination(new ItemStack(Blocks.NETHERRACK), new ItemStack(Items.BRICK), MODE_OR, new ItemStack(Items.NETHERBRICK));
		CombinationRegistry.addCombination(new ItemStack(Blocks.NETHER_BRICK), new ItemStack(Items.BRICK), MODE_OR, new ItemStack(Items.NETHERBRICK));
		CombinationRegistry.addCombination(new ItemStack(Blocks.NETHERRACK), new ItemStack(Items.GLOWSTONE_DUST), MODE_AND, new ItemStack(Blocks.GLOWSTONE));
		CombinationRegistry.addCombination(new ItemStack(Blocks.NOTEBLOCK), new ItemStack(Items.DIAMOND), MODE_AND, new ItemStack(Blocks.JUKEBOX));
		CombinationRegistry.addCombination(new ItemStack(Blocks.RAIL), new ItemStack(Blocks.PLANKS), MODE_AND, true, false, new ItemStack(Blocks.LADDER));
		CombinationRegistry.addCombination(new ItemStack(Blocks.SAPLING, 1, 0), new ItemStack(Items.WHEAT_SEEDS), MODE_AND, true, false, new ItemStack(Items.APPLE));
		CombinationRegistry.addCombination(new ItemStack(Blocks.LEAVES, 1, 0), new ItemStack(Items.WHEAT_SEEDS), MODE_OR, true, false, new ItemStack(Items.APPLE));
		CombinationRegistry.addCombination(new ItemStack(Blocks.STONE), new ItemStack(Items.ENDER_PEARL), MODE_AND, new ItemStack(Blocks.END_STONE));
		CombinationRegistry.addCombination(new ItemStack(Blocks.STONEBRICK, 1, 0), new ItemStack(Items.WHEAT_SEEDS), MODE_OR, new ItemStack(Blocks.STONEBRICK, 1, 1));
		CombinationRegistry.addCombination(new ItemStack(Items.APPLE), new ItemStack(Items.GOLD_INGOT), MODE_AND, new ItemStack(Items.GOLDEN_APPLE, 1, 0));
		CombinationRegistry.addCombination(new ItemStack(Items.APPLE), new ItemStack(Items.GOLD_NUGGET), MODE_AND, new ItemStack(Items.GOLDEN_APPLE, 1, 0));
		CombinationRegistry.addCombination(new ItemStack(Items.APPLE), new ItemStack(Blocks.GOLD_BLOCK), MODE_AND, new ItemStack(Items.GOLDEN_APPLE, 1, 1));
		CombinationRegistry.addCombination(new ItemStack(Items.CARROT), new ItemStack(Items.WHEAT_SEEDS), MODE_AND, new ItemStack(Items.POTATO));
		CombinationRegistry.addCombination(new ItemStack(Items.CLOCK), new ItemStack(Items.IRON_INGOT), MODE_AND, new ItemStack(Items.COMPASS));
		CombinationRegistry.addCombination(new ItemStack(Items.COMPASS), new ItemStack(Items.GOLD_INGOT), MODE_AND, new ItemStack(Items.CLOCK));
		CombinationRegistry.addCombination(new ItemStack(Items.DIAMOND), new ItemStack(Items.SADDLE), MODE_AND, new ItemStack(Items.DIAMOND_HORSE_ARMOR));
		CombinationRegistry.addCombination(new ItemStack(Items.ENDER_EYE), new ItemStack(Items.EGG), MODE_AND, new ItemStack(Blocks.DRAGON_EGG));
		CombinationRegistry.addCombination(new ItemStack(Items.ENDER_PEARL), new ItemStack(Items.BLAZE_POWDER), MODE_AND, new ItemStack(Items.ENDER_EYE));
		CombinationRegistry.addCombination(new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.SADDLE), MODE_AND, new ItemStack(Items.GOLDEN_HORSE_ARMOR));
		CombinationRegistry.addCombination(new ItemStack(Items.GUNPOWDER), new ItemStack(Blocks.SAND), MODE_AND, true, false, new ItemStack(Blocks.TNT));
		CombinationRegistry.addCombination(new ItemStack(Items.IRON_INGOT), new ItemStack(Blocks.TALLGRASS), MODE_AND, true, false, new ItemStack(Items.SHEARS));
		CombinationRegistry.addCombination(new ItemStack(Items.IRON_INGOT), new ItemStack(Items.SADDLE), MODE_AND, new ItemStack(Items.IRON_HORSE_ARMOR));
		CombinationRegistry.addCombination(new ItemStack(Items.POTATO), new ItemStack(Items.WHEAT_SEEDS), MODE_OR, new ItemStack(Items.CARROT));
		CombinationRegistry.addCombination(new ItemStack(Items.REDSTONE), new ItemStack(Items.GOLD_INGOT), MODE_OR, new ItemStack(Items.CLOCK));
		CombinationRegistry.addCombination(new ItemStack(Items.REDSTONE), new ItemStack(Items.IRON_INGOT), MODE_OR, new ItemStack(Items.COMPASS));
		CombinationRegistry.addCombination(new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.CARROT), MODE_OR, new ItemStack(Items.PORKCHOP));
		CombinationRegistry.addCombination(new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.WATER_BUCKET), MODE_OR, new ItemStack(Items.LEATHER));
		CombinationRegistry.addCombination(new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.WHEAT), MODE_OR, new ItemStack(Items.BEEF));
		CombinationRegistry.addCombination(new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.WHEAT_SEEDS), MODE_OR, new ItemStack(Items.CHICKEN));
		CombinationRegistry.addCombination(new ItemStack(Items.SLIME_BALL), new ItemStack(Items.BLAZE_POWDER), MODE_AND, new ItemStack(Items.MAGMA_CREAM));
		CombinationRegistry.addCombination("stickWood", Items.LAVA_BUCKET, WILDCARD_VALUE, MODE_AND, new ItemStack(Items.BLAZE_ROD));
		CombinationRegistry.addCombination(new ItemStack(Items.STRING), new ItemStack(Items.LEATHER), MODE_AND, new ItemStack(Items.SADDLE));
		CombinationRegistry.addCombination(new ItemStack(Items.REDSTONE), new ItemStack(Items.LAVA_BUCKET), MODE_OR, new ItemStack(Items.BLAZE_POWDER));
		CombinationRegistry.addCombination(new ItemStack(Items.REDSTONE), new ItemStack(Blocks.NETHERRACK), MODE_OR, new ItemStack(Items.BLAZE_POWDER));
		CombinationRegistry.addCombination("stickWood", Items.BLAZE_POWDER, WILDCARD_VALUE, MODE_AND, new ItemStack(Items.BLAZE_ROD));
		CombinationRegistry.addCombination(new ItemStack(Items.BOAT), new ItemStack(Blocks.RAIL), MODE_OR, new ItemStack(Items.MINECART));
		CombinationRegistry.addCombination(new ItemStack(Items.IRON_INGOT), new ItemStack(Blocks.TRAPDOOR), MODE_OR, new ItemStack(Blocks.IRON_TRAPDOOR));
		CombinationRegistry.addCombination(new ItemStack(Blocks.IRON_BLOCK), new ItemStack(Blocks.TRAPDOOR), MODE_OR, new ItemStack(Blocks.IRON_TRAPDOOR));
		CombinationRegistry.addCombination("stickWood", Blocks.IRON_BARS, WILDCARD_VALUE, MODE_AND, new ItemStack(Blocks.RAIL));
		CombinationRegistry.addCombination(new ItemStack(Blocks.COBBLESTONE_WALL), new ItemStack(Blocks.MOSSY_COBBLESTONE), MODE_OR, new ItemStack(Blocks.COBBLESTONE_WALL, 1, 1));
		CombinationRegistry.addCombination(new ItemStack(Blocks.DISPENSER), new ItemStack(Blocks.HOPPER), MODE_AND, new ItemStack(Blocks.DROPPER));
		CombinationRegistry.addCombination(new ItemStack(Blocks.TORCH), new ItemStack(Items.REDSTONE), MODE_AND, new ItemStack(Blocks.REDSTONE_TORCH));
		CombinationRegistry.addCombination(new ItemStack(Blocks.TORCH), new ItemStack(Items.REDSTONE), MODE_OR, new ItemStack(Items.GLOWSTONE_DUST));
		CombinationRegistry.addCombination(new ItemStack(Items.MINECART), new ItemStack(Blocks.CHEST), MODE_AND, new ItemStack(Items.CHEST_MINECART));
		CombinationRegistry.addCombination(new ItemStack(Items.MINECART), new ItemStack(Blocks.FURNACE), MODE_AND, new ItemStack(Items.FURNACE_MINECART));
		CombinationRegistry.addCombination(new ItemStack(Items.MINECART), new ItemStack(Blocks.TNT), MODE_AND, new ItemStack(Items.TNT_MINECART));
		CombinationRegistry.addCombination(new ItemStack(Items.MINECART), new ItemStack(Blocks.HOPPER), MODE_AND, new ItemStack(Items.HOPPER_MINECART));
		CombinationRegistry.addCombination(new ItemStack(Blocks.RAIL), new ItemStack(Blocks.REDSTONE_TORCH), MODE_AND, new ItemStack(Blocks.ACTIVATOR_RAIL));
		CombinationRegistry.addCombination(new ItemStack(Blocks.RAIL), new ItemStack(Blocks.WOODEN_PRESSURE_PLATE), MODE_AND, new ItemStack(Blocks.DETECTOR_RAIL));
		CombinationRegistry.addCombination(new ItemStack(Blocks.RAIL), new ItemStack(Blocks.STONE_PRESSURE_PLATE), MODE_AND, new ItemStack(Blocks.DETECTOR_RAIL));
		CombinationRegistry.addCombination(new ItemStack(Blocks.RAIL), new ItemStack(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE), MODE_AND, new ItemStack(Blocks.DETECTOR_RAIL));
		CombinationRegistry.addCombination(new ItemStack(Blocks.RAIL), new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE), MODE_AND, new ItemStack(Blocks.DETECTOR_RAIL));
		CombinationRegistry.addCombination(new ItemStack(Blocks.RAIL), new ItemStack(Items.GOLD_INGOT), MODE_AND, new ItemStack(Blocks.GOLDEN_RAIL));
		CombinationRegistry.addCombination(new ItemStack(Blocks.RAIL), new ItemStack(Items.FURNACE_MINECART), MODE_OR, new ItemStack(Blocks.GOLDEN_RAIL));
		CombinationRegistry.addCombination(new ItemStack(Blocks.GLOWSTONE), new ItemStack(Blocks.REDSTONE_TORCH), MODE_AND, new ItemStack(Blocks.REDSTONE_LAMP));
		CombinationRegistry.addCombination(new ItemStack(Items.PRISMARINE_SHARD), new ItemStack(Items.DIAMOND), MODE_OR, new ItemStack(Items.PRISMARINE_CRYSTALS));
		CombinationRegistry.addCombination(new ItemStack(Items.PRISMARINE_SHARD), new ItemStack(Items.EMERALD), MODE_OR, new ItemStack(Items.PRISMARINE_CRYSTALS));
		CombinationRegistry.addCombination(new ItemStack(Blocks.TALLGRASS), new ItemStack(Blocks.SAND), MODE_OR, false, false, new ItemStack(Blocks.DEADBUSH));
		CombinationRegistry.addCombination(new ItemStack(Blocks.TALLGRASS), new ItemStack(Blocks.SAND), MODE_AND, false, false, new ItemStack(Blocks.CACTUS));
		CombinationRegistry.addCombination("treeSapling", Blocks.SAND, WILDCARD_VALUE, MODE_AND, new ItemStack(Blocks.DEADBUSH));
		CombinationRegistry.addCombination(new ItemStack(Items.ENDER_PEARL), new ItemStack(Blocks.CHEST), MODE_AND, new ItemStack(Blocks.ENDER_CHEST));
		CombinationRegistry.addCombination(new ItemStack(Blocks.GLASS), new ItemStack(Blocks.SNOW), MODE_AND, new ItemStack(Blocks.ICE));
		CombinationRegistry.addCombination(new ItemStack(Blocks.SPONGE, 1, 0), new ItemStack(Items.WATER_BUCKET), MODE_AND, new ItemStack(Blocks.SPONGE, 1, 1));
		CombinationRegistry.addCombination(new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.GUNPOWDER), MODE_OR, new ItemStack(Items.FIRE_CHARGE));
		CombinationRegistry.addCombination(new ItemStack(Blocks.SAND, 1, 0), new ItemStack(Blocks.STONE_STAIRS), MODE_OR, new ItemStack(Blocks.SANDSTONE_STAIRS));
		CombinationRegistry.addCombination(new ItemStack(Blocks.SAND, 1, 1), new ItemStack(Blocks.STONE_STAIRS), MODE_OR, new ItemStack(Blocks.RED_SANDSTONE_STAIRS));
		CombinationRegistry.addCombination(new ItemStack(Blocks.SAND, 1, 0), new ItemStack(Items.DYE, 1, EnumDyeColor.RED.getDyeDamage()), MODE_AND, new ItemStack(Blocks.SAND, 1, 1));
		for (int i = 0; i <= 2; i++)
			CombinationRegistry.addCombination(new ItemStack(Blocks.SANDSTONE, 1, i), new ItemStack(Items.DYE, 1, EnumDyeColor.RED.getDyeDamage()), MODE_AND, new ItemStack(Blocks.RED_SANDSTONE, 1, i));
		CombinationRegistry.addCombination(new ItemStack(Blocks.SANDSTONE_STAIRS), new ItemStack(Items.DYE, 1, EnumDyeColor.RED.getDyeDamage()), MODE_AND, new ItemStack(Blocks.RED_SANDSTONE_STAIRS));
		CombinationRegistry.addCombination(new ItemStack(Items.BOOK), new ItemStack(Blocks.PLANKS), MODE_OR, true, false, new ItemStack(Blocks.BOOKSHELF));
		CombinationRegistry.addCombination("record", Blocks.NOTEBLOCK, WILDCARD_VALUE, MODE_AND, new ItemStack(Blocks.JUKEBOX));
		CombinationRegistry.addCombination("stickWood", Blocks.VINE, WILDCARD_VALUE, MODE_AND, new ItemStack(Blocks.LADDER));
		CombinationRegistry.addCombination("treeLeaves", Blocks.LADDER, WILDCARD_VALUE, MODE_OR, new ItemStack(Blocks.VINE));
		CombinationRegistry.addCombination(new ItemStack(Items.PRISMARINE_SHARD), new ItemStack(Blocks.COBBLESTONE), MODE_AND, new ItemStack(Blocks.PRISMARINE, 1, 0));
		CombinationRegistry.addCombination(new ItemStack(Items.PRISMARINE_SHARD), new ItemStack(Blocks.STONEBRICK, 1, 0), MODE_AND, new ItemStack(Blocks.PRISMARINE, 1, 1));
		CombinationRegistry.addCombination(new ItemStack(Blocks.PRISMARINE), new ItemStack(Items.DYE, 1, EnumDyeColor.BLACK.getDyeDamage()), MODE_AND, false, true, new ItemStack(Blocks.PRISMARINE, 1, 2));
		CombinationRegistry.addCombination(new ItemStack(Items.PRISMARINE_SHARD), new ItemStack(Blocks.GLOWSTONE), MODE_OR, new ItemStack(Blocks.SEA_LANTERN));
		CombinationRegistry.addCombination(new ItemStack(Items.PRISMARINE_CRYSTALS), new ItemStack(Blocks.PRISMARINE), MODE_AND, true, false, new ItemStack(Blocks.SEA_LANTERN));
		CombinationRegistry.addCombination(new ItemStack(Blocks.RED_FLOWER), new ItemStack(Items.CHORUS_FRUIT), MODE_AND, false, false, new ItemStack(Blocks.CHORUS_FLOWER));
		CombinationRegistry.addCombination(new ItemStack(Blocks.YELLOW_FLOWER), new ItemStack(Items.CHORUS_FRUIT), MODE_AND, false, false, new ItemStack(Blocks.CHORUS_FLOWER));
		CombinationRegistry.addCombination(new ItemStack(Blocks.RED_MUSHROOM), new ItemStack(Blocks.SOUL_SAND), MODE_AND, new ItemStack(Items.NETHER_WART));
		CombinationRegistry.addCombination(new ItemStack(Blocks.PRISMARINE), new ItemStack(Items.FLINT), MODE_OR, false, true, new ItemStack(Items.PRISMARINE_SHARD));
		CombinationRegistry.addCombination(new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.STONEBRICK, 1, 0), MODE_AND, new ItemStack(Blocks.STONEBRICK, 1, 2));
		CombinationRegistry.addCombination(new ItemStack(Items.GUNPOWDER), new ItemStack(Blocks.STONE_BUTTON), MODE_OR, new ItemStack(Items.REDSTONE));
		CombinationRegistry.addCombination(new ItemStack(Items.GUNPOWDER), new ItemStack(Blocks.STONE_PRESSURE_PLATE), MODE_OR, new ItemStack(Items.REDSTONE));
		CombinationRegistry.addCombination(new ItemStack(Items.GUNPOWDER), new ItemStack(Blocks.LEVER), MODE_OR, new ItemStack(Items.REDSTONE));
		CombinationRegistry.addCombination(new ItemStack(Items.GUNPOWDER), new ItemStack(Blocks.WOODEN_BUTTON), MODE_OR, new ItemStack(Blocks.REDSTONE_TORCH));
		CombinationRegistry.addCombination(new ItemStack(Items.GUNPOWDER), new ItemStack(Blocks.WOODEN_PRESSURE_PLATE), MODE_OR, new ItemStack(Blocks.REDSTONE_TORCH));
		CombinationRegistry.addCombination(new ItemStack(Blocks.GRAVEL), new ItemStack(Items.DYE, 1, EnumDyeColor.RED.getDyeDamage()), MODE_AND, new ItemStack(Items.REDSTONE));
		CombinationRegistry.addCombination(new ItemStack(Blocks.PUMPKIN), new ItemStack(strawberry), MODE_OR, new ItemStack(Blocks.MELON_BLOCK));
		CombinationRegistry.addCombination(new ItemStack(Blocks.MELON_BLOCK), new ItemStack(Items.CARROT), MODE_AND, new ItemStack(Blocks.PUMPKIN));
		CombinationRegistry.addCombination(new ItemStack(Items.SKULL, 1, 1), new ItemStack(Items.BONE), MODE_AND, new ItemStack(Items.SKULL, 1, 0));
		CombinationRegistry.addCombination(new ItemStack(Items.SKULL, 1, 1), new ItemStack(Items.DYE, 1, EnumDyeColor.WHITE.getDyeDamage()), MODE_AND, new ItemStack(Items.SKULL, 1, 0));
		CombinationRegistry.addCombination(new ItemStack(Items.SKULL, 1, 0), new ItemStack(Items.ROTTEN_FLESH), MODE_OR, new ItemStack(Items.SKULL, 1, 2));
		CombinationRegistry.addCombination(new ItemStack(Items.NETHER_WART), new ItemStack(Blocks.SAND), MODE_OR, new ItemStack(Blocks.SOUL_SAND));
		CombinationRegistry.addCombination(new ItemStack(Items.BOOK), new ItemStack(Blocks.CHEST), MODE_AND, new ItemStack(Blocks.BOOKSHELF));
		CombinationRegistry.addCombination(new ItemStack(Items.WATER_BUCKET), new ItemStack(Blocks.WOOL, 1, EnumDyeColor.YELLOW.getMetadata()), MODE_AND, new ItemStack(Blocks.SPONGE, 1, 1));
		CombinationRegistry.addCombination(new ItemStack(Items.BOOK), new ItemStack(Items.EXPERIENCE_BOTTLE), MODE_AND, new ItemStack(Blocks.ENCHANTING_TABLE));
		CombinationRegistry.addCombination("treeLeaves", new ItemStack(Items.WATER_BUCKET), true, MODE_OR, new ItemStack(Blocks.WATERLILY));
		CombinationRegistry.addCombination(new ItemStack(Blocks.CRAFTING_TABLE), new ItemStack(Blocks.IRON_BLOCK), MODE_OR, new ItemStack(Blocks.ANVIL));
		CombinationRegistry.addCombination(new ItemStack(Items.WOODEN_SWORD), new ItemStack(Items.ARROW), MODE_OR, false, true, new ItemStack(Items.BOW));
		CombinationRegistry.addCombination(new ItemStack(Blocks.RED_FLOWER), new ItemStack(Items.BRICK), MODE_AND, false, true, new ItemStack(Items.FLOWER_POT));
		CombinationRegistry.addCombination(new ItemStack(Blocks.YELLOW_FLOWER), new ItemStack(Items.BRICK), MODE_AND, false, true, new ItemStack(Items.FLOWER_POT));
		CombinationRegistry.addCombination(new ItemStack(Blocks.COBBLESTONE), new ItemStack(Items.LAVA_BUCKET), MODE_AND, new ItemStack(Blocks.NETHERRACK));
		CombinationRegistry.addCombination(new ItemStack(Items.EMERALD), new ItemStack(Items.COAL), MODE_AND, new ItemStack(Items.DIAMOND));
		CombinationRegistry.addCombination(new ItemStack(Items.EMERALD), new ItemStack(Items.DYE, 1, EnumDyeColor.BLUE.getDyeDamage()), MODE_AND, new ItemStack(Items.DIAMOND));
		CombinationRegistry.addCombination(new ItemStack(Items.POTIONITEM, 1, 0), new ItemStack(Items.ENCHANTED_BOOK), MODE_OR, true, false, new ItemStack(Items.EXPERIENCE_BOTTLE));
		CombinationRegistry.addCombination(new ItemStack(Items.GLASS_BOTTLE), new ItemStack(Items.ENCHANTED_BOOK), MODE_OR, true, false, new ItemStack(Items.EXPERIENCE_BOTTLE));
		CombinationRegistry.addCombination(new ItemStack(Items.QUARTZ), new ItemStack(Items.WATER_BUCKET), MODE_OR, new ItemStack(Items.PRISMARINE_CRYSTALS));
		CombinationRegistry.addCombination(new ItemStack(Items.QUARTZ), new ItemStack(Items.WATER_BUCKET), MODE_AND, new ItemStack(Items.PRISMARINE_SHARD));
		CombinationRegistry.addCombination(new ItemStack(Items.FEATHER), new ItemStack(Items.ENDER_PEARL), MODE_OR, new ItemStack(Items.ELYTRA));
		CombinationRegistry.addCombination(new ItemStack(Items.COOKIE), new ItemStack(Items.REDSTONE), MODE_AND, new ItemStack(Items.SUGAR));

		for (EnumDyeColor color : EnumDyeColor.values())
			dyeGrist.put(color, GristRegistry.getGristConversion(new ItemStack(Items.DYE, 1, color.getDyeDamage())));
	}

	public static void registerContainerlessCost(ItemStack stack, GristSet cost)
	{
		if (containerlessCosts != null)
			containerlessCosts.put(stack, cost);
	}

	public static void checkRegistered(ItemStack item, String name)
	{
		String[] names = CombinationRegistry.getDictionaryNames(item);
		for (String toCompare : names)
			if (toCompare.equals(name))
				return;

		OreDictionary.registerOre(name, item);
	}

	public static void registerMinestuckRecipes()
	{
		//add grist conversions
		GristRegistry.addGristConversion(new ItemStack(coloredDirt, 1, 0), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.shale}, new int[]{1, 1}));
		GristRegistry.addGristConversion(new ItemStack(coloredDirt, 1, 1), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.caulk}, new int[]{1, 1}));
		GristRegistry.addGristConversion(new ItemStack(gristWidget, 1, 0), true, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.garnet, MinestuckGrist.ruby}, new int[]{550, 55, 34}));
		GristRegistry.addGristConversion(new ItemStack(genericObject), true, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(chessboard), true, new GristSet(new Grist[]{MinestuckGrist.shale, MinestuckGrist.marble}, new int[]{25, 25}));
		GristRegistry.addGristConversion(new ItemStack(grimoire), true, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.amethyst, MinestuckGrist.garnet}, new int[]{120, 60, 33}));
		GristRegistry.addGristConversion(new ItemStack(longForgottenWarhorn), true, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.amethyst, MinestuckGrist.tar, MinestuckGrist.garnet}, new int[]{550, 120, 50, 80}));

		for (Item i : cruxiteArtifacts)
			GristRegistry.addGristConversion(new ItemStack(i), new GristSet());

		GristRegistry.addGristConversion(new ItemStack(cruxiteApple), new GristSet());

		GristRegistry.addGristConversion(new ItemStack(catClaws), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.rust}, new int[]{15, 5}));

		GristRegistry.addGristConversion(new ItemStack(clawHammer), false, new GristSet(MinestuckGrist.build, 8));
		GristRegistry.addGristConversion(new ItemStack(sledgeHammer), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.shale}, new int[]{10, 4}));
		GristRegistry.addGristConversion(new ItemStack(blacksmithHammer), false, new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.sulfur, MinestuckGrist.caulk}, new int[]{8, 9, 5}));
		GristRegistry.addGristConversion(new ItemStack(pogoHammer), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.shale}, new int[]{20, 16}));
		GristRegistry.addGristConversion(new ItemStack(telescopicSassacrusher), false, new GristSet(new Grist[]{MinestuckGrist.shale, MinestuckGrist.tar, MinestuckGrist.mercury}, new int[]{39, 18, 23}));
		GristRegistry.addGristConversion(new ItemStack(regiHammer), false, new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.tar, MinestuckGrist.gold}, new int[]{25, 70, 34}));
		GristRegistry.addGristConversion(new ItemStack(fearNoAnvil), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.garnet, MinestuckGrist.diamond, MinestuckGrist.gold, MinestuckGrist.quartz}, new int[]{999, 150, 54, 61, 1}));
		GristRegistry.addGristConversion(new ItemStack(meltMasher), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.tar, MinestuckGrist.garnet, MinestuckGrist.diamond, MinestuckGrist.gold, MinestuckGrist.ruby, MinestuckGrist.sulfur}, new int[]{1000, 400, 200, 340, 100, 150, 450}));
		GristRegistry.addGristConversion(new ItemStack(qEHammerAxe), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.shale, MinestuckGrist.uranium, MinestuckGrist.rust}, new int[]{8000, 1280, 640, 300}));
		GristRegistry.addGristConversion(new ItemStack(dDEHammerAxe), false, new GristSet(new Grist[]{MinestuckGrist.artifact, MinestuckGrist.build}, new int[]{-100, 1}));

		GristRegistry.addGristConversion(new ItemStack(cactusCutlass), false, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.marble}, new int[]{7, 2}));
		GristRegistry.addGristConversion(new ItemStack(steakSword), false, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.tar}, new int[]{55, 18}));
		GristRegistry.addGristConversion(new ItemStack(beefSword), false, new GristSet(new Grist[]{MinestuckGrist.iodine}, new int[]{55}));
		GristRegistry.addGristConversion(new ItemStack(irradiatedSteakSword), false, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.tar, MinestuckGrist.uranium}, new int[]{55, 10, 8}));
		GristRegistry.addGristConversion(new ItemStack(sord), false, new GristSet(MinestuckGrist.build, 0));
		GristRegistry.addGristConversion(new ItemStack(katana), false, new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.quartz, MinestuckGrist.rust}, new int[]{12, 10, 6}));
		GristRegistry.addGristConversion(new ItemStack(firePoker), false, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.ruby, MinestuckGrist.sulfur, MinestuckGrist.gold}, new int[]{41, 14, 38, 3}));
		GristRegistry.addGristConversion(new ItemStack(claymore), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.rust}, new int[]{400, 240}));
		GristRegistry.addGristConversion(new ItemStack(hotHandle), false, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.ruby, MinestuckGrist.sulfur}, new int[]{10, 15, 10}));
		GristRegistry.addGristConversion(new ItemStack(regisword), false, new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.tar, MinestuckGrist.gold}, new int[]{27, 62, 38}));
		GristRegistry.addGristConversion(new ItemStack(unbreakableKatana), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.uranium, MinestuckGrist.quartz, MinestuckGrist.ruby}, new int[]{1100, 63, 115, 54}));
		GristRegistry.addGristConversion(new ItemStack(cobaltSabre), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.uranium, MinestuckGrist.cobalt, MinestuckGrist.diamond}, new int[]{1300, 90, 175, 30}));
		GristRegistry.addGristConversion(new ItemStack(quantumSabre), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.uranium}, new int[]{413, 11}));
		GristRegistry.addGristConversion(new ItemStack(shatterBeacon), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.cobalt, MinestuckGrist.diamond, MinestuckGrist.uranium}, new int[]{25, 15, 150, 400}));
		GristRegistry.addGristConversion(new ItemStack(scarletRibbitar), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.ruby, MinestuckGrist.quartz, MinestuckGrist.diamond, MinestuckGrist.garnet}, new int[]{100, 400, 20, 10, 200}));

		GristRegistry.addGristConversion(new ItemStack(woodenSpoon), false, new GristSet(MinestuckGrist.build, 5));
		GristRegistry.addGristConversion(new ItemStack(silverSpoon), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.mercury}, new int[]{6, 4}));
		GristRegistry.addGristConversion(new ItemStack(crockerSpoon), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.iodine, MinestuckGrist.chalk, MinestuckGrist.ruby}, new int[]{90, 34, 34, 6}));
		GristRegistry.addGristConversion(new ItemStack(skaiaFork), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.quartz, MinestuckGrist.gold, MinestuckGrist.amethyst}, new int[]{900, 94, 58, 63}));
		GristRegistry.addGristConversion(new ItemStack(goldenSpork), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.gold, MinestuckGrist.diamond}, new int[]{70, 40, 1}));

		GristRegistry.addGristConversion(new ItemStack(batleacks), false, new GristSet(MinestuckGrist.build, 0));
		GristRegistry.addGristConversion(new ItemStack(copseCrusher), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.rust}, new int[]{25, 15}));
		GristRegistry.addGristConversion(new ItemStack(battleaxe), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.rust}, new int[]{400, 240}));
		GristRegistry.addGristConversion(new ItemStack(blacksmithBane), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.rust, MinestuckGrist.tar}, new int[]{30, 15, 12}));
		GristRegistry.addGristConversion(new ItemStack(scraxe), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.tar, MinestuckGrist.rust, MinestuckGrist.ruby}, new int[]{139, 86, 43, 8}));
		GristRegistry.addGristConversion(new ItemStack(qPHammerAxe), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.shale, MinestuckGrist.rust}, new int[]{150, 64, 15}));
		GristRegistry.addGristConversion(new ItemStack(rubyCroak), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.garnet, MinestuckGrist.ruby, MinestuckGrist.diamond}, new int[]{900, 103, 64, 16}));
		GristRegistry.addGristConversion(new ItemStack(hephaestusLumber), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.gold, MinestuckGrist.ruby}, new int[]{625, 49, 36}));
		GristRegistry.addGristConversion(new ItemStack(qFHammerAxe), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.shale, MinestuckGrist.uranium, MinestuckGrist.rust}, new int[]{800, 128, 64, 30}));

		GristRegistry.addGristConversion(new ItemStack(regiSickle), false, new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.tar, MinestuckGrist.gold}, new int[]{25, 57, 33}));
		GristRegistry.addGristConversion(new ItemStack(sickle), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{8}));
		GristRegistry.addGristConversion(new ItemStack(homesSmellYaLater), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.amber, MinestuckGrist.amethyst}, new int[]{34, 19, 10}));
		GristRegistry.addGristConversion(new ItemStack(fudgeSickle), false, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.amber, MinestuckGrist.chalk}, new int[]{23, 15, 12}));
		GristRegistry.addGristConversion(new ItemStack(clawOfNrubyiglith), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.amethyst, MinestuckGrist.chalk, MinestuckGrist.garnet, MinestuckGrist.shale}, new int[]{333, 80, 6, 6, 6}));
		GristRegistry.addGristConversion(new ItemStack(candySickle), false, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.gold, MinestuckGrist.chalk, MinestuckGrist.amber}, new int[]{65, 38, 53, 20}));

		GristRegistry.addGristConversion(new ItemStack(nightClub), false, new GristSet(new Grist[]{MinestuckGrist.tar, MinestuckGrist.shale, MinestuckGrist.cobalt}, new int[]{28, 19, 6}));
		GristRegistry.addGristConversion(new ItemStack(pogoClub), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.shale}, new int[]{15, 12}));
		GristRegistry.addGristConversion(new ItemStack(metalBat), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.mercury}, new int[]{35, 23}));
		GristRegistry.addGristConversion(new ItemStack(spikedClub), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.garnet, MinestuckGrist.iodine}, new int[]{46, 38, 13}));

		GristRegistry.addGristConversion(new ItemStack(spearCane), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.mercury, MinestuckGrist.amber}, new int[]{28, 14, 11}));
		GristRegistry.addGristConversion(new ItemStack(paradisesPortabello), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.iodine, MinestuckGrist.ruby}, new int[]{40, 30, 20}));
		GristRegistry.addGristConversion(new ItemStack(regiCane), false, new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.tar, MinestuckGrist.gold}, new int[]{30, 55, 32}));
		GristRegistry.addGristConversion(new ItemStack(pogoCane), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.shale}, new int[]{18, 14}));
		GristRegistry.addGristConversion(new ItemStack(upStick), false, new GristSet(new Grist[]{MinestuckGrist.uranium}, new int[]{1}));


		GristRegistry.addGristConversion(new ItemStack(captcharoidCamera), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.caulk, MinestuckGrist.gold, MinestuckGrist.marble, MinestuckGrist.mercury, MinestuckGrist.shale}, new int[]{5000, 500, 500, 500, 500, 500}));
		GristRegistry.addGristConversion(new ItemStack(transportalizer), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.amethyst, MinestuckGrist.rust, MinestuckGrist.uranium}, new int[]{350, 27, 36, 18}));
		GristRegistry.addGristConversion(new ItemStack(metalBoat, 1, 0), true, new GristSet(MinestuckGrist.rust, 30));
		GristRegistry.addGristConversion(new ItemStack(metalBoat, 1, 1), true, new GristSet(MinestuckGrist.gold, 30));
		GristRegistry.addGristConversion(new ItemStack(layeredSand), new GristSet(MinestuckGrist.build, 1));
		registerContainerlessCost(new ItemStack(minestuckBucket, 1, 0), new GristSet(new Grist[]{MinestuckGrist.tar, MinestuckGrist.shale}, new int[]{8, 8}));
		registerContainerlessCost(new ItemStack(minestuckBucket, 1, 1), new GristSet(new Grist[]{MinestuckGrist.garnet, MinestuckGrist.iodine}, new int[]{8, 8}));
		registerContainerlessCost(new ItemStack(minestuckBucket, 1, 2), new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.chalk}, new int[]{8, 8}));
		registerContainerlessCost(new ItemStack(minestuckBucket, 1, 3), new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.chalk, MinestuckGrist.garnet, MinestuckGrist.amber}, new int[]{4, 4, 4, 4}));
		registerContainerlessCost(new ItemStack(minestuckBucket, 1, 4), new GristSet(new Grist[]{MinestuckGrist.mercury, MinestuckGrist.uranium}, new int[]{8, 8}));
		registerContainerlessCost(new ItemStack(obsidianBucket), new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.tar, MinestuckGrist.build}, new int[]{8, 16, 4}));
		GristRegistry.addGristConversion(new ItemStack(glowingMushroom), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.shale, MinestuckGrist.mercury}, new int[]{5, 3, 2}));
		GristRegistry.addGristConversion(new ItemStack(glowingLog), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.amber, MinestuckGrist.mercury}, new int[]{8, 4, 4}));
		GristRegistry.addGristConversion(new ItemStack(glowingPlanks), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.amber, MinestuckGrist.mercury}, new int[]{2, 1, 1}));
		GristRegistry.addGristConversion(new ItemStack(glowyGoop), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.caulk, MinestuckGrist.mercury}, new int[]{8, 8, 4}));

		GristRegistry.addGristConversion(new ItemStack(petrifiedLog), new GristSet(MinestuckGrist.build, 4));
		GristRegistry.addGristConversion(new ItemStack(petrifiedGrass), new GristSet(MinestuckGrist.build, 1));
		GristRegistry.addGristConversion(new ItemStack(petrifiedPoppy), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.iodine}, new int[]{1, 1}));
		GristRegistry.addGristConversion(new ItemStack(bloomingCactus), new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.iodine}, new int[]{1, 2}));
		GristRegistry.addGristConversion(new ItemStack(goldSeeds), new GristSet(MinestuckGrist.gold, 3));
		GristRegistry.addGristConversion(new ItemStack(emeraldSword), false, new GristSet(new Grist[]{MinestuckGrist.quartz, MinestuckGrist.diamond, MinestuckGrist.ruby}, new int[]{44, 76, 72}));
		GristRegistry.addGristConversion(new ItemStack(emeraldAxe), false, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.diamond, MinestuckGrist.ruby}, new int[]{40, 73, 70}));
		GristRegistry.addGristConversion(new ItemStack(emeraldPickaxe), false, new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.diamond, MinestuckGrist.ruby}, new int[]{42, 72, 70}));
		GristRegistry.addGristConversion(new ItemStack(emeraldShovel), false, new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.diamond, MinestuckGrist.ruby}, new int[]{40, 70, 66}));
		GristRegistry.addGristConversion(new ItemStack(emeraldHoe), false, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.diamond, MinestuckGrist.ruby}, new int[]{32, 50, 45}));
		GristRegistry.addGristConversion(new ItemStack(prismarineHelmet), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.cobalt, MinestuckGrist.marble}, new int[]{75, 30, 15}));
		GristRegistry.addGristConversion(new ItemStack(prismarineChestplate), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.cobalt, MinestuckGrist.marble}, new int[]{120, 48, 24}));
		GristRegistry.addGristConversion(new ItemStack(prismarineLeggings), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.cobalt, MinestuckGrist.marble}, new int[]{105, 42, 21}));
		GristRegistry.addGristConversion(new ItemStack(prismarineBoots), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.cobalt, MinestuckGrist.marble}, new int[]{60, 24, 12}));
		GristRegistry.addGristConversion(new ItemStack(glowystoneDust), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.amber}, new int[]{1, 1}));
		GristRegistry.addGristConversion(new ItemStack(rawCruxite), new GristSet(MinestuckGrist.build, 3));
		GristRegistry.addGristConversion(new ItemStack(rawUranium), new GristSet(MinestuckGrist.uranium, 3));
		GristRegistry.addGristConversion(new ItemStack(goldenGrasshopper), new GristSet(MinestuckGrist.gold, 4000));
		GristRegistry.addGristConversion(new ItemStack(bugNet), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.chalk}, new int[]{40, 25}));

		GristRegistry.addGristConversion(new ItemStack(spork), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{13}));
		GristRegistry.addGristConversion(new ItemStack(candy, 1, 0), new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.sulfur, MinestuckGrist.iodine}, new int[]{1, 1, 1}));

		for (Grist type : Grist.REGISTRY.getValues())
		{
			GristRegistry.addGristConversion(new ItemStack(candy, 1, type.getId() + 1), new GristSet(type, 3));
		}

		GristRegistry.addGristConversion(new ItemStack(beverage, 1, 0), new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.iodine}, new int[]{1, 1}));        //Tab
		GristRegistry.addGristConversion(new ItemStack(beverage, 1, 1), new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.amber}, new int[]{1, 1}));        //Faygo (orange)
		GristRegistry.addGristConversion(new ItemStack(beverage, 1, 2), new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.shale}, new int[]{1, 1}));        //Candy apple Faygo
		GristRegistry.addGristConversion(new ItemStack(beverage, 1, 3), new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.iodine}, new int[]{1, 1}));        //Faygo cola
		GristRegistry.addGristConversion(new ItemStack(beverage, 1, 4), new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.amethyst}, new int[]{1, 1}));    //Cotton Candy Faygo
		GristRegistry.addGristConversion(new ItemStack(beverage, 1, 5), new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.chalk}, new int[]{1, 1}));        //Creme Faygo
		GristRegistry.addGristConversion(new ItemStack(beverage, 1, 6), new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.shale}, new int[]{1, 1}));        //Grape Faygo
		GristRegistry.addGristConversion(new ItemStack(beverage, 1, 7), new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.amber}, new int[]{1, 1}));        //Moonmist Faygo
		GristRegistry.addGristConversion(new ItemStack(beverage, 1, 8), new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.garnet}, new int[]{1, 1}));        //Peach Faygo
		GristRegistry.addGristConversion(new ItemStack(beverage, 1, 9), new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.sulfur}, new int[]{1, 1}));        //Redpop Faygo

		GristRegistry.addGristConversion(new ItemStack(salad), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.iodine}, new int[]{1, 3}));
		GristRegistry.addGristConversion(new ItemStack(bugOnAStick), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.chalk}, new int[]{1, 1}));
		GristRegistry.addGristConversion(new ItemStack(chocolateBeetle), new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.iodine}, new int[]{2, 4}));
		GristRegistry.addGristConversion(new ItemStack(coneOfFlies), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.amber}, new int[]{2, 2}));
		GristRegistry.addGristConversion(new ItemStack(grasshopper), new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.amber}, new int[]{3, 7}));
		GristRegistry.addGristConversion(new ItemStack(jarOfBugs), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.chalk}, new int[]{5, 3}));
		GristRegistry.addGristConversion(new ItemStack(onion), new GristSet(new Grist[]{MinestuckGrist.iodine}, new int[]{3}));
		GristRegistry.addGristConversion(new ItemStack(irradiatedSteak), false, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.uranium}, new int[]{12, 1}));
		GristRegistry.addGristConversion(new ItemStack(rockCookie), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.marble}, new int[]{10, 5}));
		GristRegistry.addGristConversion(new ItemStack(strawberryChunk), new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.build, MinestuckGrist.ruby}, new int[]{2, 1, 2}));
		GristRegistry.addGristConversion(new ItemStack(desertFruit), false, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.caulk}, new int[]{1, 1}));
		GristRegistry.addGristConversion(new ItemStack(fungalSpore), new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.amber}, new int[]{2, 1}));
		GristRegistry.addGristConversion(new ItemStack(sporeo), new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.amber}, new int[]{3, 1}));
		GristRegistry.addGristConversion(new ItemStack(morelMushroom), new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.amber}, new int[]{20, 12}));
		GristRegistry.addGristConversion(new ItemStack(frenchFry), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.iodine}, new int[]{1, 1}));
		GristRegistry.addGristConversion(new ItemStack(surpriseEmbryo), false, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.iodine}, new int[]{15, 5}));
		GristRegistry.addGristConversion(new ItemStack(unknowableEgg), false, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.amethyst, MinestuckGrist.tar}, new int[]{15, 15, 1}));

		GristRegistry.addGristConversion(primedTnt, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.chalk, MinestuckGrist.sulfur}, new int[]{8, 10, 14}));
		GristRegistry.addGristConversion(unstableTnt, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.chalk, MinestuckGrist.sulfur}, new int[]{5, 11, 15}));
		GristRegistry.addGristConversion(instantTnt, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.chalk, MinestuckGrist.sulfur}, new int[]{6, 11, 17}));
		GristRegistry.addGristConversion(stoneExplosiveButton, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.chalk, MinestuckGrist.sulfur}, new int[]{7, 5, 8}));
		GristRegistry.addGristConversion(woodenExplosiveButton, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.chalk, MinestuckGrist.sulfur}, new int[]{7, 5, 8}));
		GristRegistry.addGristConversion(new ItemStack(coarseStone, 1, 0), new GristSet(MinestuckGrist.build, 4));
		GristRegistry.addGristConversion(new ItemStack(chiseledCoarseStone, 1, 1), new GristSet(MinestuckGrist.build, 4));
		GristRegistry.addGristConversion(new ItemStack(shadeBrick, 1, 2), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.shale}, new int[]{2, 1}));
		GristRegistry.addGristConversion(new ItemStack(shadeBrickSmooth, 1, 3), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.shale}, new int[]{2, 1}));
		GristRegistry.addGristConversion(new ItemStack(frostBrick, 1, 4), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.cobalt}, new int[]{2, 1}));
		GristRegistry.addGristConversion(new ItemStack(frostTile, 1, 5), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.cobalt}, new int[]{2, 1}));
		GristRegistry.addGristConversion(new ItemStack(chiseledFrostBrick, 1, 6), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.cobalt}, new int[]{3, 1}));
		GristRegistry.addGristConversion(new ItemStack(castIron, 1, 7), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.rust}, new int[]{3, 2}));
		GristRegistry.addGristConversion(new ItemStack(chiseledCastIron, 1, 8), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.rust}, new int[]{3, 2}));
		GristRegistry.addGristConversion(new ItemStack(coarseEndStone), false, new GristSet(new Grist[]{MinestuckGrist.caulk, MinestuckGrist.build}, new int[]{3, 4}));
		GristRegistry.addGristConversion(new ItemStack(endGrass), false, new GristSet(new Grist[]{MinestuckGrist.caulk, MinestuckGrist.build}, new int[]{3, 4}));
		GristRegistry.addGristConversion(new ItemStack(log, 1, 0), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{8}));
		GristRegistry.addGristConversion(new ItemStack(log, 1, 1), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.iodine}, new int[]{7, 1}));
		GristRegistry.addGristConversion(new ItemStack(log, 1, 2), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.cobalt}, new int[]{7, 1}));
		GristRegistry.addGristConversion(new ItemStack(log, 1, 3), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{8}));
		GristRegistry.addGristConversion(new ItemStack(floweryMossBrick), new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.build, MinestuckGrist.iodine}, new int[]{1, 7, 1}));
		GristRegistry.addGristConversion(new ItemStack(floweryMossStone), new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.build, MinestuckGrist.iodine}, new int[]{1, 7, 1}));
		GristRegistry.addGristConversion(endLog, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{8}));
		GristRegistry.addGristConversion(treatedPlanks, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(leaves), false, new GristSet(MinestuckGrist.build, 1));
		GristRegistry.addGristConversion(new ItemStack(endLeaves), false, new GristSet(MinestuckGrist.build, 1));
		GristRegistry.addGristConversion(rainbowSapling, new GristSet(MinestuckGrist.build, 16));
		GristRegistry.addGristConversion(new ItemStack(sbahjPoster), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{4}));
		GristRegistry.addGristConversion(new ItemStack(crewPoster), new GristSet(new Grist[]{MinestuckGrist.tar, MinestuckGrist.rust}, new int[]{3, 2}));
		GristRegistry.addGristConversion(new ItemStack(threshDvd), new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.amethyst}, new int[]{3, 2}));
		GristRegistry.addGristConversion(new ItemStack(gamebroMagazine), new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.garnet}, new int[]{3, 2}));
		GristRegistry.addGristConversion(new ItemStack(gamegrlMagazine), new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.amethyst}, new int[]{3, 2}));
		GristRegistry.addGristConversion(new ItemStack(carvingTool), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.rust}, new int[]{10, 2}));
		GristRegistry.addGristConversion(new ItemStack(crumplyHat), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{20}));
		GristRegistry.addGristConversion(new ItemStack(frogStatueReplica), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{30}));
		GristRegistry.addGristConversion(new ItemStack(stoneTablet), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{5}));
		GristRegistry.addGristConversion(woodenCactus, new GristSet(MinestuckGrist.build, 7));
		GristRegistry.addGristConversion(new ItemStack(blueCake), new GristSet(new Grist[]{MinestuckGrist.shale, MinestuckGrist.mercury, MinestuckGrist.cobalt, MinestuckGrist.diamond}, new int[]{24, 6, 5, 1}));
		GristRegistry.addGristConversion(new ItemStack(coldCake), new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.marble}, new int[]{15, 12}));
		GristRegistry.addGristConversion(new ItemStack(redCake), new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.chalk, MinestuckGrist.iodine, MinestuckGrist.garnet}, new int[]{20, 9, 6, 1}));
		GristRegistry.addGristConversion(new ItemStack(hotCake), new GristSet(new Grist[]{MinestuckGrist.sulfur, MinestuckGrist.iodine}, new int[]{17, 10}));
		GristRegistry.addGristConversion(new ItemStack(reverseCake), new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.chalk, MinestuckGrist.iodine}, new int[]{10, 24, 11}));
		GristRegistry.addGristConversion(new ItemStack(fuchsiaCake), new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.garnet, MinestuckGrist.iodine}, new int[]{85, 54, 40}));
		GristRegistry.addGristConversion(cruxtruderLid, new GristSet(MinestuckGrist.build, 8));
		GristRegistry.addGristConversion((new ItemStack(blender)), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{16}));
		/*GristRegistry.addGristConversion(new ItemStack(shopPoster, 1, 0), new GristSet(new GristType[] {GristType.build, GristType.cobalt, GristType.shale}, new int[] {8, 2, 2}));
		GristRegistry.addGristConversion(new ItemStack(shopPoster, 1, 1), new GristSet(new GristType[] {GristType.build, GristType.iodine}, new int[] {9, 3}));
		GristRegistry.addGristConversion(new ItemStack(shopPoster, 1, 2), new GristSet(new GristType[] {GristType.build, GristType.chalk, GristType.caulk}, new int[] {8, 2, 2}));
		GristRegistry.addGristConversion(new ItemStack(shopPoster, 1, 3), new GristSet(new GristType[] {GristType.build, GristType.rust, GristType.amber}, new int[] {7, 3, 2}));
		GristRegistry.addGristConversion(new ItemStack(shopPoster, 1, 4), new GristSet(new GristType[] {GristType.build, GristType.iodine, GristType.chalk}, new int[] {9, 2, 1}));*/

		GristRegistry.addGristConversion(new ItemStack(hardStone), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{4}));
		GristRegistry.addGristConversion(new ItemStack(floatStone), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{400}));
		GristRegistry.addGristConversion(new ItemStack(energyCell), new GristSet(new Grist[]{MinestuckGrist.gold, MinestuckGrist.rust, MinestuckGrist.uranium}, new int[]{20, 10, 10}));
		//GristRegistry.addGristConversion(new ItemStack(popBall), new GristSet(new Grist[] {MinestuckGrist.iodine, MinestuckGrist.amber, MinestuckGrist.shale}, new int[] {8, 5, 2}));
		GristRegistry.addGristConversion(new ItemStack(dragonGel), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.sulfur, MinestuckGrist.uranium, MinestuckGrist.zillium}, new int[]{1010, 500, 742, 1525}));

		GristRegistry.addGristConversion(new ItemStack(MinestuckItems.operandiBlock), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.garnet}, new int[]{1, 1}));
		GristRegistry.addGristConversion(new ItemStack(MinestuckItems.operandiStone), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.garnet}, new int[]{1, 1}));
		GristRegistry.addGristConversion(new ItemStack(MinestuckItems.operandiLog), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.garnet}, new int[]{1, 1}));
		GristRegistry.addGristConversion(new ItemStack(MinestuckItems.operandiGlass), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.garnet}, new int[]{1, 1}));

		/*GristRegistry.addGristConversion(new ItemStack(queueStackModus), true, new GristSet(MinestuckGrist.build, 140));
		GristRegistry.addGristConversion(new ItemStack(treeModus), true, new GristSet(new Grist[] {MinestuckGrist.build, MinestuckGrist.amber}, new int[] {400, 35}));
		GristRegistry.addGristConversion(new ItemStack(hashmapModus), true, new GristSet(new Grist[] {MinestuckGrist.build, MinestuckGrist.ruby}, new int[] {280, 23}));
		GristRegistry.addGristConversion(new ItemStack(setModus), true, new GristSet(new Grist[] {MinestuckGrist.build, MinestuckGrist.mercury}, new int[] {350, 29}));
		GristRegistry.addGristConversion(new ItemStack(cycloneModus), new GristSet(new Grist[] {MinestuckGrist.build}, new int[] {16}));
		GristRegistry.addGristConversion(new ItemStack(wildMagicModus), new GristSet(new Grist[] {MinestuckGrist.build, MinestuckGrist.garnet, MinestuckGrist.amethyst}, new int[] {310, 32, 65}));
		GristRegistry.addGristConversion(new ItemStack(capitalistModus), new GristSet(new Grist[] {MinestuckGrist.build, MinestuckGrist.gold, MinestuckGrist.diamond}, new int[] {270, 55, 5}));
		GristRegistry.addGristConversion(new ItemStack(deckModus), new GristSet(new Grist[] {MinestuckGrist.build, MinestuckGrist.ruby}, new int[] {250, 52}));
		GristRegistry.addGristConversion(new ItemStack(popTartModus), new GristSet(new Grist[] {MinestuckGrist.build, MinestuckGrist.chalk, MinestuckGrist.iodine}, new int[] {320, 34, 20}));
		GristRegistry.addGristConversion(new ItemStack(eightBallModus), new GristSet(new Grist[] {MinestuckGrist.build, MinestuckGrist.mercury, MinestuckGrist.cobalt}, new int[] {320, 16, 38}));
		GristRegistry.addGristConversion(new ItemStack(eightBallModus), new GristSet(new Grist[] {MinestuckGrist.build, MinestuckGrist.mercury, MinestuckGrist.cobalt}, new int[] {320, 16, 38}));
		GristRegistry.addGristConversion(new ItemStack(scratchAndSniffModus), new GristSet(new Grist[] {MinestuckGrist.build, MinestuckGrist.amethyst, MinestuckGrist.cobalt}, new int[] {260, 40, 20}));
		GristRegistry.addGristConversion(new ItemStack(chasityModus), new GristSet(new Grist[] {MinestuckGrist.build, MinestuckGrist.rust, MinestuckGrist.quartz}, new int[] {210, 36, 2}));
		GristRegistry.addGristConversion(new ItemStack(slimeModus), new GristSet(new Grist[] {MinestuckGrist.build, MinestuckGrist.caulk}, new int[] {340, 28}));
		GristRegistry.addGristConversion(new ItemStack(onionModus), new GristSet(new Grist[] {MinestuckGrist.build, MinestuckGrist.iodine, MinestuckGrist.amber}, new int[] {210, 30, 13}));
		GristRegistry.addGristConversion(new ItemStack(chatModus), new GristSet(new Grist[] {MinestuckGrist.build, MinestuckGrist.garnet, MinestuckGrist.artifact}, new int[] {310, 32, 10}));
		GristRegistry.addGristConversion(new ItemStack(hueModus), new GristSet(new Grist[] {MinestuckGrist.build, MinestuckGrist.cobalt, MinestuckGrist.amber, MinestuckGrist.amethyst, MinestuckGrist.ruby}, new int[] {290, 3, 3, 3, 3}));
		GristRegistry.addGristConversion(new ItemStack(hueStackModus), new GristSet(new Grist[] {MinestuckGrist.build, MinestuckGrist.cobalt, MinestuckGrist.amber, MinestuckGrist.amethyst, MinestuckGrist.ruby, MinestuckGrist.zillium}, new int[] {585, 12, 12, 12, 12, 1}));
		GristRegistry.addGristConversion(new ItemStack(jujuModus), new GristSet(new Grist[] {MinestuckGrist.build, MinestuckGrist.zillium}, new int[] {604, 100}));
		GristRegistry.addGristConversion(new ItemStack(modUs), new GristSet(new Grist[] {MinestuckGrist.build, MinestuckGrist.gold, MinestuckGrist.ruby}, new int[] {707, 128, 128}));
		GristRegistry.addGristConversion(new ItemStack(alcheModus), new GristSet(new Grist[] {MinestuckGrist.build, MinestuckGrist.uranium, MinestuckGrist.zillium}, new int[] {2500, 640, 4}));
		GristRegistry.addGristConversion(new ItemStack(operandiModus), new GristSet(new Grist[] {MinestuckGrist.build, MinestuckGrist.garnet, MinestuckGrist.shale}, new int[] {340, 24, 33}));
		GristRegistry.addGristConversion(new ItemStack(weightModus), new GristSet(new Grist[] {MinestuckGrist.build, MinestuckGrist.rust}, new int[] {32, 10}));
		GristRegistry.addGristConversion(new ItemStack(energyModus), new GristSet(new Grist[] {MinestuckGrist.build, MinestuckGrist.gold, MinestuckGrist.uranium}, new int[] {180, 32, 21}));
		GristRegistry.addGristConversion(new ItemStack(bookModus), new GristSet(new Grist[] {MinestuckGrist.build, MinestuckGrist.iodine, MinestuckGrist.chalk}, new int[] {980, 18, 26}));


		GristRegistry.addGristConversion(new ItemStack(arrayModus), new GristSet(new Grist[] {MinestuckGrist.build}, new int[] {350}));
		GristRegistry.addGristConversion(new ItemStack(walletModus), new GristSet(new Grist[] {MinestuckGrist.build, MinestuckGrist.iodine, MinestuckGrist.diamond, MinestuckGrist.zillium, MinestuckGrist.artifact}, new int[] {3000, 540, 20, 4050, 110}));
		GristRegistry.addGristConversion(new ItemStack(crystalBallModus), new GristSet(new Grist[] {MinestuckGrist.build, MinestuckGrist.iodine, MinestuckGrist.diamond, MinestuckGrist.zillium, MinestuckGrist.artifact, MinestuckGrist.cobalt, MinestuckGrist.iodine}, new int[] {4000, 800, 40, 10000, 500, 800, 400}));
		GristRegistry.addGristConversion(new ItemStack(monsterModus), new GristSet(new Grist[] {MinestuckGrist.build, MinestuckGrist.rust, MinestuckGrist.chalk}, new int[] {230, 24, 17}));*/

		/*
		GristRegistry.addGristConversion(new ItemStack(FMPItems.memoryModus), new GristSet(new GristType[] {build, amethyst, chalk}, new int[] {140, 22, 8}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.recipeModus), new GristSet(new GristType[] {build, garnet, ruby}, new int[] {180, 12, 8}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.bottledMsgModus), new GristSet(new GristType[] {build, quartz}, new int[] {250, 7}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.techHopModus), new GristSet(new GristType[] {build, caulk, diamond}, new int[] {280, 26, 4}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.encryptionModus), new GristSet(new GristType[] {build, artifact, uranium}, new int[] {240, 16, 8}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.ouijaModus), new GristSet(new GristType[] {build, amber, amethyst}, new int[] {200, 12, 23}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.bundleModus), new GristSet(new GristType[] {build, iodine, chalk}, new int[] {160, 16, 12}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.cakeModus), new GristSet(new GristType[] {build, amber, chalk}, new int[] {210, 18, 32}));
		GristRegistry.addGristConversion(new ItemStack(FMPItems.cipherModus), new GristSet(new GristType[] {build, rust, amber}, new int[] {190, 22, 26}));
		*/

		//GristRegistry.addGristConversion(new ItemStack(hashchatModus), new GristSet(new Grist[] {MinestuckGrist.build, MinestuckGrist.garnet, MinestuckGrist.artifact}, new int[] {510, 64, 10}));
		//GristRegistry.addGristConversion(new ItemStack(sacrificeModus), new GristSet(new Grist[] {MinestuckGrist.build, MinestuckGrist.rust, MinestuckGrist.tar}, new int[] {210, 45, 8}));

		GristSet magicBlockCost = new GristSet(MinestuckGrist.build, 2);
		int magicGristTotal = (
				(Minestuck.isThaumLoaded ? 1 : 0)
						+ (Minestuck.isBotaniaLoaded ? 1 : 0)

		);

		int magicGristSpread = 0;
		if (magicGristTotal > 0)
			magicGristSpread = 16 / magicGristTotal;

		if (Minestuck.isThaumLoaded) magicBlockCost.addGrist(MinestuckGrist.vis, magicGristSpread);
		if (Minestuck.isBotaniaLoaded) magicBlockCost.addGrist(MinestuckGrist.mana, magicGristSpread);

		//Grist Conversions

		GristRegistry.addGristConversion(new ItemStack(uniqueObject), new GristSet(new Grist[]{MinestuckGrist.zillium}, new int[]{1}));
		GristRegistry.addGristConversion(new ItemStack(artifact), new GristSet(new Grist[]{MinestuckGrist.artifact}, new int[]{1000}));

		GristRegistry.addGristConversion(new ItemStack(diverHelmet), new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.cobalt}, new int[]{80, 16}));
		GristRegistry.addGristConversion(new ItemStack(spikedHelmet), new GristSet(new Grist[]{MinestuckGrist.rust}, new int[]{260}));
		GristRegistry.addGristConversion(new ItemStack(frogHat), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{20}));
		GristRegistry.addGristConversion(new ItemStack(wizardHat), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.amethyst}, new int[]{10, 8}));
		GristRegistry.addGristConversion(new ItemStack(cozySweater), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.garnet}, new int[]{10, 8}));
		GristRegistry.addGristConversion(new ItemStack(archmageHat), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.amethyst, MinestuckGrist.cobalt, MinestuckGrist.garnet}, new int[]{10, 800, 800, 800}));
		GristRegistry.addGristConversion(new ItemStack(bunnySlippers), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.chalk}, new int[]{10, 8}));
		GristRegistry.addGristConversion(new ItemStack(cruxtruderHat), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{100}));

		GristRegistry.addGristConversion(new ItemStack(rubberBoots), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.shale}, new int[]{10, 20}));
		GristRegistry.addGristConversion(new ItemStack(moonShoes), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.shale}, new int[]{20, 10}));
		GristRegistry.addGristConversion(new ItemStack(sunShoes), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.shale, MinestuckGrist.sulfur}, new int[]{200, 100, 250}));
		GristRegistry.addGristConversion(new ItemStack(rocketBoots), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.tar, MinestuckGrist.sulfur}, new int[]{1600, 200, 80}));
		GristRegistry.addGristConversion(new ItemStack(windWalkers), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.cobalt, MinestuckGrist.uranium, MinestuckGrist.chalk}, new int[]{120, 60, 1, 200}));
		GristRegistry.addGristConversion(new ItemStack(airJordans), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.ruby, MinestuckGrist.chalk}, new int[]{800, 600, 800}));
		GristRegistry.addGristConversion(new ItemStack(cobaltJordans), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.cobalt, MinestuckGrist.chalk}, new int[]{800, 600, 800}));

		GristRegistry.addGristConversion(new ItemStack(magicBlock), magicBlockCost);
		GristRegistry.addGristConversion(new ItemStack(sbahjBedrock), new GristSet(new Grist[]{MinestuckGrist.artifact}, new int[]{2000}));
		GristRegistry.addGristConversion(new ItemStack(zillyStone), new GristSet(new Grist[]{MinestuckGrist.zillium, MinestuckGrist.build}, new int[]{1, 100}));
		GristRegistry.addGristConversion(new ItemStack(smoothIron), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.rust, MinestuckGrist.quartz}, new int[]{10, 8, 4}));
		GristRegistry.addGristConversion(new ItemStack(wizardStatue), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{12}));
		GristRegistry.addGristConversion(new ItemStack(netherReactorCore), new GristSet(new Grist[]{MinestuckGrist.uranium, MinestuckGrist.garnet, MinestuckGrist.rust, MinestuckGrist.diamond}, new int[]{250, 121, 50, 2}));
		GristRegistry.addGristConversion(new ItemStack(bigRock), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{1000}));
		GristRegistry.addGristConversion(new ItemStack(wizardbeardYarn), new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.amethyst, MinestuckGrist.garnet}, new int[]{100, 200, 250}));
		GristRegistry.addGristConversion(new ItemStack(unrealAir), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{0}));
		GristRegistry.addGristConversion(new ItemStack(whip), new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.build}, new int[]{14, 8}));
		GristRegistry.addGristConversion(new ItemStack(sbahjWhip), new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.build, MinestuckGrist.artifact}, new int[]{10, 10, -10}));
		GristRegistry.addGristConversion(new ItemStack(laserPointer), new GristSet(new Grist[]{MinestuckGrist.mercury, MinestuckGrist.ruby}, new int[]{8, 10}));

		if (MSUCalendarUtil.isChristmas())
			GristRegistry.addGristConversion(new ItemStack(sbahjTree), new GristSet(new Grist[]{}, new int[]{}));
		else
			GristRegistry.addGristConversion(new ItemStack(sbahjTree), new GristSet(new Grist[]{MinestuckGrist.artifact}, new int[]{10}));

		GristRegistry.addGristConversion(new ItemStack(machineChasis), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.rust, MinestuckGrist.uranium, MinestuckGrist.diamond}, new int[]{750, 98, 55, 4}));
		GristRegistry.addGristConversion(new ItemStack(gristHopper), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.rust, MinestuckGrist.uranium}, new int[]{250, 55, 10}));
		GristRegistry.addGristConversion(new ItemStack(autoWidget), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.rust, MinestuckGrist.uranium, MinestuckGrist.garnet, MinestuckGrist.zillium}, new int[]{550, 34, 24, 35, 1}));
		GristRegistry.addGristConversion(new ItemStack(autoCaptcha), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.rust, MinestuckGrist.uranium, MinestuckGrist.cobalt, MinestuckGrist.ruby, MinestuckGrist.quartz}, new int[]{140, 36, 22, 16, 12, 1}));
		GristRegistry.addGristConversion(new ItemStack(ceramicPorkhollow), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.rust, MinestuckGrist.uranium, MinestuckGrist.iodine, MinestuckGrist.diamond}, new int[]{180, 35, 16, 18, 4}));
		GristRegistry.addGristConversion(new ItemStack(boondollarRegister), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.rust, MinestuckGrist.uranium, MinestuckGrist.garnet, MinestuckGrist.iodine, MinestuckGrist.quartz}, new int[]{280, 35, 20, 25, 16, 8}));

		GristRegistry.addGristConversion(new ItemStack(rubyRedTransportalizer), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.ruby, MinestuckGrist.rust, MinestuckGrist.uranium}, new int[]{450, 100, 36, 24}));
		GristRegistry.addGristConversion(new ItemStack(goldenTransportalizer), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.gold, MinestuckGrist.shale, MinestuckGrist.uranium}, new int[]{350, 36, 30, 20}));
		GristRegistry.addGristConversion(new ItemStack(paradoxTransportalizer), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.rust, MinestuckGrist.uranium, MinestuckGrist.diamond, MinestuckGrist.zillium}, new int[]{750, 120, 256, 74, 25}));

		GristRegistry.addGristConversion(new ItemStack(battery), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.mercury}, new int[]{1, 2}));
		GristRegistry.addGristConversion(new ItemStack(batteryBeamBlade), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.uranium, MinestuckGrist.mercury}, new int[]{27, 54, 83}));
		GristRegistry.addGristConversion(new ItemStack(bloodKatana), new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.iodine}, new int[]{2600, 30}));
		GristRegistry.addGristConversion(new ItemStack(unbreakableKatana), new GristSet(MinestuckGrist.zillium, 1000));
		GristRegistry.addGristConversion(new ItemStack(quantumEntangloporter), new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.uranium, MinestuckGrist.diamond}, new int[]{4400, 680, 2}));
		GristRegistry.addGristConversion(new ItemStack(lightbringer), new GristSet(new Grist[]{MinestuckGrist.gold, MinestuckGrist.tar, MinestuckGrist.sulfur, MinestuckGrist.diamond}, new int[]{3400, 1200, 800, 22}));
		GristRegistry.addGristConversion(new ItemStack(cybersword), new GristSet(new Grist[]{MinestuckGrist.gold, MinestuckGrist.cobalt, MinestuckGrist.uranium, MinestuckGrist.diamond}, new int[]{4400, 8030, 8, 220}));
		GristRegistry.addGristConversion(new ItemStack(valorsEdge), new GristSet(new Grist[]{MinestuckGrist.diamond, MinestuckGrist.ruby, MinestuckGrist.uranium, MinestuckGrist.gold}, new int[]{8400, 200, 200, 800}));

		GristRegistry.addGristConversion(new ItemStack(loghammer), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{24}));
		GristRegistry.addGristConversion(new ItemStack(overgrownLoghammer), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.amber}, new int[]{48, 16}));
		GristRegistry.addGristConversion(new ItemStack(glowingLoghammer), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.shale}, new int[]{48, 16}));
		GristRegistry.addGristConversion(new ItemStack(midasMallet), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.gold, MinestuckGrist.quartz, MinestuckGrist.diamond}, new int[]{1500, 4000, 20, 20}));
		GristRegistry.addGristConversion(new ItemStack(aaaNailShocker), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.mercury}, new int[]{20, 16}));
		GristRegistry.addGristConversion(new ItemStack(highVoltageStormCrusher), new GristSet(new Grist[]{MinestuckGrist.gold, MinestuckGrist.mercury, MinestuckGrist.uranium, MinestuckGrist.quartz}, new int[]{2000, 1230, 6060, 800}));
		GristRegistry.addGristConversion(new ItemStack(barrelsWarhammer), new GristSet(new Grist[]{MinestuckGrist.ruby, MinestuckGrist.diamond, MinestuckGrist.uranium, MinestuckGrist.gold}, new int[]{8400, 200, 200, 800}));
		GristRegistry.addGristConversion(new ItemStack(stardustSmasher), new GristSet(new Grist[]{MinestuckGrist.gold, MinestuckGrist.zillium, MinestuckGrist.artifact}, new int[]{5600, 10, 1}));
		GristRegistry.addGristConversion(new ItemStack(MinestuckItems.mwrthwl), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.chalk, MinestuckGrist.gold, MinestuckGrist.zillium}, new int[]{2000, 12000, 200, 1}));

		GristRegistry.addGristConversion(new ItemStack(hereticusAurum), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.gold, MinestuckGrist.amethyst}, new int[]{210, 2000, 256}));

		GristRegistry.addGristConversion(new ItemStack(rubyContrabat), new GristSet(new Grist[]{MinestuckGrist.ruby, MinestuckGrist.diamond, MinestuckGrist.quartz, MinestuckGrist.build}, new int[]{800, 30, 15, 1400}));
		GristRegistry.addGristConversion(new ItemStack(dynamiteStick), new GristSet(new Grist[]{MinestuckGrist.sulfur, MinestuckGrist.chalk, MinestuckGrist.build}, new int[]{400, 320, 800}));
		GristRegistry.addGristConversion(new ItemStack(nightmareMace), new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.shale, MinestuckGrist.caulk, MinestuckGrist.tar}, new int[]{6000, 4000, 4000, 6000}));
		GristRegistry.addGristConversion(new ItemStack(cranialEnder), new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.cobalt, MinestuckGrist.uranium, MinestuckGrist.mercury, MinestuckGrist.tar, MinestuckGrist.diamond}, new int[]{6800, 4020, 300, 540, 210, 4}));
		GristRegistry.addGristConversion(new ItemStack(homeRunBat), new GristSet(new Grist[]{MinestuckGrist.ruby, MinestuckGrist.gold, MinestuckGrist.garnet, MinestuckGrist.artifact}, new int[]{4500, 8000, 4500, 1}));
		GristRegistry.addGristConversion(new ItemStack(badaBat), new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.chalk, MinestuckGrist.amethyst, MinestuckGrist.artifact}, new int[]{4500, 8000, 4500, 100}));

		GristRegistry.addGristConversion(new ItemStack(goldCane), new GristSet(new Grist[]{MinestuckGrist.gold, MinestuckGrist.build}, new int[]{20, 16}));
		GristRegistry.addGristConversion(new ItemStack(staffOfOvergrowth), new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.ruby, MinestuckGrist.amber}, new int[]{540, 800, 240}));
		GristRegistry.addGristConversion(new ItemStack(atomicIrradiator), new GristSet(new Grist[]{MinestuckGrist.uranium, MinestuckGrist.build}, new int[]{200, 2000}));
		GristRegistry.addGristConversion(new ItemStack(scepterOfZillywuud), new GristSet(MinestuckGrist.zillium, 1000));

		GristRegistry.addGristConversion(new ItemStack(battlepickOfZillydew), new GristSet(MinestuckGrist.zillium, 1000));
		GristRegistry.addGristConversion(new ItemStack(battleaxeOfZillywahoo), new GristSet(MinestuckGrist.zillium, 1000));
		GristRegistry.addGristConversion(new ItemStack(battlesporkOfZillywut), new GristSet(MinestuckGrist.zillium, 1000));

		GristRegistry.addGristConversion(new ItemStack(gravediggerShovel), new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.garnet}, new int[]{2000, 2}));
		GristRegistry.addGristConversion(new ItemStack(rolledUpPaper), new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.iodine}, new int[]{3, 2}));
		GristRegistry.addGristConversion(new ItemStack(yesterdaysNews), new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.quartz, MinestuckGrist.garnet}, new int[]{300, 200, 125}));
		GristRegistry.addGristConversion(new ItemStack(pebble), new GristSet(MinestuckGrist.build, 1));
		GristRegistry.addGristConversion(new ItemStack(rock), new GristSet(MinestuckGrist.build, 10));

		GristRegistry.addGristConversion(new ItemStack(fancyGlove), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.chalk}, new int[]{5, 2}));
		GristRegistry.addGristConversion(new ItemStack(spikedGlove), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.rust, MinestuckGrist.chalk}, new int[]{25, 5, 3}));
		GristRegistry.addGristConversion(new ItemStack(cobbleBasher), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.marble}, new int[]{30, 5}));
		GristRegistry.addGristConversion(new ItemStack(pogoFist), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.shale}, new int[]{16, 21}));
		GristRegistry.addGristConversion(new ItemStack(fluoriteGauntlet), false, new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.caulk, MinestuckGrist.quartz, MinestuckGrist.shale}, new int[]{803, 500, 10, 2}));
		GristRegistry.addGristConversion(new ItemStack(goldenGenesisGauntlet), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.gold, MinestuckGrist.uranium, MinestuckGrist.diamond, MinestuckGrist.artifact}, new int[]{8000, 5000, 300, 250, 10}));
		GristRegistry.addGristConversion(new ItemStack(rocketFist), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.chalk, MinestuckGrist.sulfur, MinestuckGrist.tar}, new int[]{220, 40, 200, 80}));
		GristRegistry.addGristConversion(new ItemStack(eldrichGauntlet), false, new GristSet(new Grist[]{MinestuckGrist.tar, MinestuckGrist.amethyst, MinestuckGrist.shale, MinestuckGrist.quartz}, new int[]{3000, 4000, 5000, 66}));
		GristRegistry.addGristConversion(new ItemStack(jawbreaker), false, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.iodine, MinestuckGrist.marble}, new int[]{500, 400, 100}));
		GristRegistry.addGristConversion(new ItemStack(gasterBlaster), false, new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.cobalt, MinestuckGrist.diamond, MinestuckGrist.artifact}, new int[]{800, 4000, 200, 1}));
		GristRegistry.addGristConversion(new ItemStack(midasGlove), false, new GristSet(new Grist[]{MinestuckGrist.gold, MinestuckGrist.diamond, MinestuckGrist.quartz}, new int[]{4000, 200, 200}));
		GristRegistry.addGristConversion(new ItemStack(gauntletOfZillywenn), false, new GristSet(new Grist[]{MinestuckGrist.zillium}, new int[]{1000}));

		GristRegistry.addGristConversion(new ItemStack(pointySticks), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{12}));
		GristRegistry.addGristConversion(new ItemStack(drumstickNeedles), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.garnet}, new int[]{200, 10}));
		GristRegistry.addGristConversion(new ItemStack(boneNeedles), false, new GristSet(new Grist[]{MinestuckGrist.chalk}, new int[]{24}));
		GristRegistry.addGristConversion(new ItemStack(needlewands), false, new GristSet(new Grist[]{MinestuckGrist.diamond, MinestuckGrist.chalk, MinestuckGrist.garnet, MinestuckGrist.gold}, new int[]{1000, 2000, 3000, 500}));
		GristRegistry.addGristConversion(new ItemStack(oglogothThorn), false, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.chalk, MinestuckGrist.amethyst, MinestuckGrist.gold, MinestuckGrist.tar}, new int[]{6000, 5000, 4000, 3000, 666}));
		GristRegistry.addGristConversion(new ItemStack(litGlitterBeamTransistor), false, new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.gold, MinestuckGrist.ruby, MinestuckGrist.cobalt, MinestuckGrist.uranium, MinestuckGrist.amethyst}, new int[]{333, 3300, 6000, 6000, 6000, 6000}));
		GristRegistry.addGristConversion(new ItemStack(echidnaQuills), false, new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.iodine, MinestuckGrist.artifact, MinestuckGrist.zillium}, new int[]{16000, 8000, 10000, 10}));
		GristRegistry.addGristConversion(new ItemStack(thistlesOfZillywitch), false, new GristSet(new Grist[]{MinestuckGrist.zillium}, new int[]{1000}));

		GristRegistry.addGristConversion(new ItemStack(actionClaws), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.diamond, MinestuckGrist.cobalt}, new int[]{200, 80, 20}));
		GristRegistry.addGristConversion(new ItemStack(sneakyDaggers), new GristSet(new Grist[]{MinestuckGrist.tar, MinestuckGrist.chalk, MinestuckGrist.rust}, new int[]{200, 68, 90}));
		GristRegistry.addGristConversion(new ItemStack(candyCornClaws), new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.chalk, MinestuckGrist.iodine}, new int[]{10, 35, 43}));
		GristRegistry.addGristConversion(new ItemStack(rocketKatars), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.chalk, MinestuckGrist.sulfur, MinestuckGrist.tar}, new int[]{110, 64, 20, 80}));
		GristRegistry.addGristConversion(new ItemStack(blizzardCutters), new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.shale}, new int[]{800, 640}));
		GristRegistry.addGristConversion(new ItemStack(thunderbirdTalons), new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.mercury, MinestuckGrist.quartz, MinestuckGrist.gold}, new int[]{2400, 810, 20, 1000}));
		GristRegistry.addGristConversion(new ItemStack(archmageDaggers), new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.garnet, MinestuckGrist.gold, MinestuckGrist.chalk}, new int[]{5000, 2000, 1500, 1500}));
		GristRegistry.addGristConversion(new ItemStack(katarsOfZillywhomst), new GristSet(MinestuckGrist.zillium, 1000));

		GristRegistry.addGristConversion(new ItemStack(energyBow), new GristSet(new Grist[]{MinestuckGrist.uranium, MinestuckGrist.build}, new int[]{24, 32}));
		GristRegistry.addGristConversion(new ItemStack(infernoShot), new GristSet(new Grist[]{MinestuckGrist.sulfur, MinestuckGrist.tar, MinestuckGrist.build}, new int[]{24, 16, 32}));
		GristRegistry.addGristConversion(new ItemStack(icicleBow), new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.build}, new int[]{24, 32}));
		GristRegistry.addGristConversion(new ItemStack(shiverburnWing), new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.sulfur, MinestuckGrist.ruby, MinestuckGrist.build}, new int[]{24, 24, 60, 100}));
		GristRegistry.addGristConversion(new ItemStack(sweetBow), new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.amber, MinestuckGrist.iodine, MinestuckGrist.gold}, new int[]{40, 32, 32, 20}));
		GristRegistry.addGristConversion(new ItemStack(tempestBow), new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.cobalt, MinestuckGrist.quartz}, new int[]{200, 490, 40}));
		GristRegistry.addGristConversion(new ItemStack(magneticHookshot), new GristSet(new Grist[]{MinestuckGrist.uranium, MinestuckGrist.mercury, MinestuckGrist.rust}, new int[]{400, 680, 850}));
		GristRegistry.addGristConversion(new ItemStack(wormholePiercer), new GristSet(new Grist[]{MinestuckGrist.uranium, MinestuckGrist.mercury, MinestuckGrist.ruby, MinestuckGrist.rust}, new int[]{4000, 860, 400, 2050}));
		GristRegistry.addGristConversion(new ItemStack(telegravitationalWarper), new GristSet(new Grist[]{MinestuckGrist.uranium, MinestuckGrist.mercury, MinestuckGrist.diamond, MinestuckGrist.artifact}, new int[]{4000, 1030, 100, 10}));
		GristRegistry.addGristConversion(new ItemStack(mechanicalCrossbow), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.rust, MinestuckGrist.garnet}, new int[]{6000, 4000, 200}));
		GristRegistry.addGristConversion(new ItemStack(crabbow), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.iodine, MinestuckGrist.quartz}, new int[]{6000, 6000, 20}));
		GristRegistry.addGristConversion(new ItemStack(kingOfThePond), new GristSet(new Grist[]{MinestuckGrist.gold, MinestuckGrist.iodine, MinestuckGrist.ruby, MinestuckGrist.tar}, new int[]{2000, 3000, 200, 1000}));
		GristRegistry.addGristConversion(new ItemStack(gildedGuidance), new GristSet(new Grist[]{MinestuckGrist.gold, MinestuckGrist.quartz, MinestuckGrist.diamond}, new int[]{3000, 400, 400}));
		GristRegistry.addGristConversion(new ItemStack(bowOfLight), new GristSet(new Grist[]{MinestuckGrist.gold, MinestuckGrist.sulfur, MinestuckGrist.diamond, MinestuckGrist.cobalt}, new int[]{2000, 4000, 200, 10}));
		GristRegistry.addGristConversion(new ItemStack(theChancemaker), new GristSet(new Grist[]{MinestuckGrist.gold, MinestuckGrist.ruby, MinestuckGrist.diamond, MinestuckGrist.cobalt}, new int[]{1800, 400, 2000, 10000}));
		GristRegistry.addGristConversion(new ItemStack(wisdomsPierce), new GristSet(new Grist[]{MinestuckGrist.uranium, MinestuckGrist.diamond, MinestuckGrist.ruby, MinestuckGrist.gold}, new int[]{8400, 200, 200, 800}));

		GristRegistry.addGristConversion(new ItemStack(clearShield), new GristSet(MinestuckGrist.build, 20));
		GristRegistry.addGristConversion(new ItemStack(woodenDoorshield), new GristSet(MinestuckGrist.build, 60));
		GristRegistry.addGristConversion(new ItemStack(ironDoorshield), new GristSet(MinestuckGrist.rust, 60));
		GristRegistry.addGristConversion(new ItemStack(bladedShield), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.rust}, new int[]{50, 24}));
		GristRegistry.addGristConversion(new ItemStack(shockerShell), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.mercury}, new int[]{1000, 800}));
		GristRegistry.addGristConversion(new ItemStack(windshield), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.cobalt, MinestuckGrist.chalk}, new int[]{1600, 600, 600}));
		GristRegistry.addGristConversion(new ItemStack(clarityWard), new GristSet(new Grist[]{MinestuckGrist.gold, MinestuckGrist.quartz, MinestuckGrist.amethyst}, new int[]{240, 200, 2400}));
		GristRegistry.addGristConversion(new ItemStack(rocketRiotShield), new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.tar, MinestuckGrist.sulfur}, new int[]{2000, 6040, 2010}));
		GristRegistry.addGristConversion(new ItemStack(ejectorShield), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.sulfur}, new int[]{1330, 610}));
		GristRegistry.addGristConversion(new ItemStack(firewall), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.tar, MinestuckGrist.amber}, new int[]{880, 1210, 624}));
		GristRegistry.addGristConversion(new ItemStack(obsidianShield), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.rust, MinestuckGrist.tar, MinestuckGrist.cobalt}, new int[]{3000, 1000, 500, 500}));
		GristRegistry.addGristConversion(new ItemStack(wallOfThorns), new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.gold, MinestuckGrist.ruby}, new int[]{1000, 200, 435}));
		GristRegistry.addGristConversion(new ItemStack(livingShield), new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.gold, MinestuckGrist.ruby, MinestuckGrist.diamond}, new int[]{1100, 2000, 800, 4}));
		GristRegistry.addGristConversion(new ItemStack(hardRindHarvest), new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.gold, MinestuckGrist.caulk}, new int[]{800, 500, 800}));
		GristRegistry.addGristConversion(new ItemStack(nuclearNeglector), new GristSet(new Grist[]{MinestuckGrist.uranium, MinestuckGrist.rust, MinestuckGrist.shale}, new int[]{4500, 432, 800}));
		GristRegistry.addGristConversion(new ItemStack(perfectAegis), new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.uranium, MinestuckGrist.gold, MinestuckGrist.zillium}, new int[]{8000, 6000, 4500, 1}));

		GristRegistry.addGristConversion(new ItemStack(markedBoomerang), new GristSet(MinestuckGrist.build, 10));
		GristRegistry.addGristConversion(new ItemStack(redHotRang), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.sulfur, MinestuckGrist.tar}, new int[]{200, 40, 20}));
		GristRegistry.addGristConversion(new ItemStack(goldenStar), new GristSet(MinestuckGrist.gold, 1));
		GristRegistry.addGristConversion(new ItemStack(suitarang), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.tar}, new int[]{5, 1}));
		GristRegistry.addGristConversion(new ItemStack(psionicStar), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.amethyst}, new int[]{20, 4}));
		GristRegistry.addGristConversion(new ItemStack(hotPotato), new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.tar}, new int[]{20, 200}));
		GristRegistry.addGristConversion(new ItemStack(dragonCharge), new GristSet(new Grist[]{MinestuckGrist.ruby, MinestuckGrist.sulfur, MinestuckGrist.tar}, new int[]{200, 800, 450}));
		GristRegistry.addGristConversion(new ItemStack(tornadoGlaive), new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.gold, MinestuckGrist.quartz, MinestuckGrist.uranium}, new int[]{260, 120, 30, 2}));

		GristRegistry.addGristConversion(new ItemStack(moonstone), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.cobalt, MinestuckGrist.amethyst, MinestuckGrist.uranium}, new int[]{5, 4, 3, 2}));
		GristRegistry.addGristConversion(new ItemStack(moonstoneOre), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.cobalt, MinestuckGrist.amethyst, MinestuckGrist.uranium, MinestuckGrist.build}, new int[]{5, 4, 3, 2, 4}));

		GristRegistry.addGristConversion(new ItemStack(fluorite), new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.diamond}, new int[]{20, 1}));
		GristRegistry.addGristConversion(new ItemStack(zillystoneShard), new GristSet(new Grist[]{MinestuckGrist.zillium}, new int[]{1}));
		GristRegistry.addGristConversion(new ItemStack(spaceSalt), new GristSet(new Grist[]{MinestuckGrist.uranium, MinestuckGrist.tar, MinestuckGrist.zillium}, new int[]{10, 32, 1}));
		GristRegistry.addGristConversion(new ItemStack(timetable), new GristSet(new Grist[]{MinestuckGrist.uranium, MinestuckGrist.garnet, MinestuckGrist.rust, MinestuckGrist.zillium}, new int[]{300, 1600, 1600, 500}));

		GristRegistry.addGristConversion(new ItemStack(fluoriteOre), new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.diamond, MinestuckGrist.build}, new int[]{20, 1, 4}));

		GristRegistry.addGristConversion(new ItemStack(unbreakableKatana), new GristSet(MinestuckGrist.zillium, 1000));

		GristRegistry.addGristConversion(new ItemStack(returnMedallion), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.rust, MinestuckGrist.artifact, MinestuckGrist.quartz}, new int[]{10000, 1000, 250, 500}));
		GristRegistry.addGristConversion(new ItemStack(teleportMedallion), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.uranium, MinestuckGrist.rust, MinestuckGrist.artifact}, new int[]{5000, 5000, 1000, 250}));
		GristRegistry.addGristConversion(new ItemStack(skaianMedallion), new GristSet(
				new Grist[]{MinestuckGrist.amber, MinestuckGrist.amethyst, MinestuckGrist.caulk, MinestuckGrist.chalk, MinestuckGrist.cobalt, MinestuckGrist.diamond, MinestuckGrist.garnet, MinestuckGrist.gold, MinestuckGrist.iodine, MinestuckGrist.marble, MinestuckGrist.mercury, MinestuckGrist.quartz, MinestuckGrist.ruby, MinestuckGrist.rust, MinestuckGrist.shale, MinestuckGrist.sulfur, MinestuckGrist.tar, MinestuckGrist.uranium, MinestuckGrist.zillium, MinestuckGrist.artifact},
				new int[]{250000, 250000, 250000, 250000, 250000, 250000, 250000, 250000, 250000, 250000, 250000, 250000, 250000, 250000, 250000, 250000, 250000, 250000, 100000, 100000}
		));

		GristRegistry.addGristConversion(new ItemStack(MinestuckItems.denizenEye), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.mercury, MinestuckGrist.uranium, MinestuckGrist.amethyst,
				MinestuckGrist.diamond, MinestuckGrist.quartz, MinestuckGrist.zillium, MinestuckGrist.artifact}, new int[]{10000, 5000, 5000, 5000, 2500, 2500, 1000, 1000}));
		GristRegistry.addGristConversion(new ItemStack(MinestuckItems.sashKit), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.gold, MinestuckGrist.zillium, MinestuckGrist.artifact}, new int[]{1080, 400, 100, 100}));
		GristRegistry.addGristConversion(new ItemStack(MinestuckItems.skillReseter), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.gold, MinestuckGrist.diamond, MinestuckGrist.zillium, MinestuckGrist.artifact}, new int[]{1080, 200, 200, 100, 100}));

		//add Designix and Lathe combinations

		//swords
		CombinationRegistry.addCombination(new ItemStack(Items.WOODEN_SWORD), new ItemStack(Blocks.CACTUS), MODE_AND, false, true, new ItemStack(cactusCutlass));
		CombinationRegistry.addCombination(new ItemStack(Items.WOODEN_SWORD), new ItemStack(bloomingCactus), MODE_AND, false, true, new ItemStack(cactusCutlass));
		CombinationRegistry.addCombination(new ItemStack(Items.WOODEN_SWORD), new ItemStack(Items.COOKED_BEEF), MODE_OR, false, true, new ItemStack(steakSword));
		CombinationRegistry.addCombination(new ItemStack(Items.WOODEN_SWORD), new ItemStack(Items.BEEF), MODE_OR, false, true, new ItemStack(beefSword));
		CombinationRegistry.addCombination(new ItemStack(Items.WOODEN_SWORD), new ItemStack(irradiatedSteak), MODE_OR, false, true, new ItemStack(irradiatedSteakSword));
		CombinationRegistry.addCombination(new ItemStack(Items.STONE_SWORD), new ItemStack(Items.COOKED_BEEF), MODE_OR, false, true, new ItemStack(steakSword));
		CombinationRegistry.addCombination(new ItemStack(Items.WOODEN_SWORD), new ItemStack(sbahjPoster), MODE_AND, false, true, new ItemStack(sord));
		CombinationRegistry.addCombination(new ItemStack(Items.STONE_SWORD), new ItemStack(sbahjPoster), MODE_AND, false, true, new ItemStack(sord));
		CombinationRegistry.addCombination(new ItemStack(Items.STONE_SWORD), new ItemStack(Items.ROTTEN_FLESH), MODE_AND, false, true, new ItemStack(katana));
		CombinationRegistry.addCombination(new ItemStack(Items.IRON_SWORD), new ItemStack(Items.ROTTEN_FLESH), MODE_AND, false, true, new ItemStack(katana));
		CombinationRegistry.addCombination(new ItemStack(Items.IRON_SWORD), new ItemStack(Items.BLAZE_ROD), MODE_AND, false, true, new ItemStack(firePoker));
		CombinationRegistry.addCombination(new ItemStack(Items.IRON_SWORD), new ItemStack(Items.BLAZE_ROD), MODE_OR, false, true, new ItemStack(hotHandle));
		CombinationRegistry.addCombination(new ItemStack(Items.IRON_SWORD), new ItemStack(chessboard), MODE_AND, false, true, new ItemStack(regisword));
		CombinationRegistry.addCombination(new ItemStack(katana), new ItemStack(chessboard), MODE_AND, false, true, new ItemStack(regisword));
		CombinationRegistry.addCombination(new ItemStack(Items.IRON_SWORD), new ItemStack(Blocks.IRON_BLOCK), MODE_AND, false, true, new ItemStack(claymore));
		CombinationRegistry.addCombination(new ItemStack(obsidianKatana), new ItemStack(Blocks.OBSIDIAN), MODE_AND, false, true, new ItemStack(unbreakableKatana));
		CombinationRegistry.addCombination(new ItemStack(hotHandle), new ItemStack(Blocks.LAPIS_BLOCK), MODE_OR, false, true, new ItemStack(cobaltSabre));
		CombinationRegistry.addCombination(new ItemStack(caledscratch), new ItemStack(itemFrog, 1, 2), MODE_AND, false, true, new ItemStack(scarletRibbitar));
		CombinationRegistry.addCombination(new ItemStack(Blocks.BEACON), new ItemStack(Items.DIAMOND_SWORD), MODE_AND, false, false, new ItemStack(shatterBeacon));

		//axes
		CombinationRegistry.addCombination(new ItemStack(Items.WOODEN_AXE), new ItemStack(sbahjPoster), MODE_AND, false, true, new ItemStack(batleacks));
		CombinationRegistry.addCombination(new ItemStack(Items.STONE_AXE), new ItemStack(sbahjPoster), MODE_AND, false, true, new ItemStack(batleacks));
		CombinationRegistry.addCombination(new ItemStack(Items.IRON_AXE), new ItemStack(Blocks.PISTON), MODE_AND, false, true, new ItemStack(copseCrusher));
		CombinationRegistry.addCombination(new ItemStack(Items.IRON_AXE), new ItemStack(Blocks.IRON_BLOCK), MODE_AND, false, true, new ItemStack(battleaxe));
		CombinationRegistry.addCombination(new ItemStack(Items.WOODEN_AXE), new ItemStack(Blocks.ANVIL), MODE_AND, false, true, new ItemStack(blacksmithBane));
		CombinationRegistry.addCombination("record", Items.IRON_AXE, OreDictionary.WILDCARD_VALUE, MODE_AND, new ItemStack(scraxe));
		CombinationRegistry.addCombination(new ItemStack(copseCrusher), new ItemStack(pogoHammer), MODE_AND, new ItemStack(qPHammerAxe));
		CombinationRegistry.addCombination(new ItemStack(qPHammerAxe), new ItemStack(energyCore), MODE_AND, new ItemStack(qFHammerAxe));
		CombinationRegistry.addCombination(new ItemStack(Items.GOLDEN_AXE), new ItemStack(Items.LAVA_BUCKET), MODE_AND, false, true, new ItemStack(hephaestusLumber));
		CombinationRegistry.addCombination(new ItemStack(Items.IRON_AXE), new ItemStack(itemFrog, 1, 2), MODE_AND, false, true, new ItemStack(rubyCroak));

		//sickles
		CombinationRegistry.addCombination(new ItemStack(Items.IRON_HOE), new ItemStack(Items.WHEAT), MODE_AND, false, true, new ItemStack(sickle));
		CombinationRegistry.addCombination(new ItemStack(sickle), new ItemStack(threshDvd), MODE_OR, false, true, new ItemStack(homesSmellYaLater));
		CombinationRegistry.addCombination(new ItemStack(sickle), new ItemStack(Items.DYE, 1, 3), MODE_OR, false, true, new ItemStack(fudgeSickle));
		CombinationRegistry.addCombination(new ItemStack(sickle), new ItemStack(chessboard), MODE_AND, false, true, new ItemStack(regiSickle));
		CombinationRegistry.addCombination(new ItemStack(sickle), new ItemStack(candy, 1, 0), MODE_OR, false, true, new ItemStack(candySickle));
		CombinationRegistry.addCombination(new ItemStack(fudgeSickle), new ItemStack(Items.SUGAR), MODE_AND, false, true, new ItemStack(candySickle));
		CombinationRegistry.addCombination(new ItemStack(catClaws), new ItemStack(grimoire), MODE_AND, false, true, new ItemStack(clawOfNrubyiglith));

		//clubs
		CombinationRegistry.addCombination(new ItemStack(deuceClub), new ItemStack(Items.SLIME_BALL), MODE_AND, false, true, new ItemStack(pogoClub));
		CombinationRegistry.addCombination(new ItemStack(deuceClub), new ItemStack(crewPoster), MODE_AND, false, true, new ItemStack(nightClub));
		CombinationRegistry.addCombination(new ItemStack(deuceClub), new ItemStack(Items.IRON_INGOT), MODE_AND, false, true, new ItemStack(metalBat));
		CombinationRegistry.addCombination("logWood", metalBat, OreDictionary.WILDCARD_VALUE, MODE_OR, new ItemStack(spikedClub));

		//hammers
		CombinationRegistry.addCombination(new ItemStack(clawHammer), new ItemStack(Blocks.BRICK_BLOCK), MODE_AND, false, false, new ItemStack(sledgeHammer));
		CombinationRegistry.addCombination(new ItemStack(clawHammer), new ItemStack(Blocks.COBBLESTONE), MODE_AND, false, false, new ItemStack(sledgeHammer));
		CombinationRegistry.addCombination(new ItemStack(Blocks.ANVIL), new ItemStack(sledgeHammer), MODE_AND, false, false, new ItemStack(blacksmithHammer));
		CombinationRegistry.addCombination(new ItemStack(Blocks.IRON_BLOCK), new ItemStack(sledgeHammer), MODE_AND, false, false, new ItemStack(blacksmithHammer));
		CombinationRegistry.addCombination(new ItemStack(Items.SLIME_BALL), new ItemStack(sledgeHammer), MODE_AND, false, false, new ItemStack(pogoHammer));
		CombinationRegistry.addCombination(new ItemStack(clawHammer), new ItemStack(chessboard), MODE_AND, false, true, new ItemStack(regiHammer));
		CombinationRegistry.addCombination(new ItemStack(blacksmithHammer), new ItemStack(Items.CLOCK), MODE_OR, false, false, new ItemStack(fearNoAnvil));
		CombinationRegistry.addCombination(new ItemStack(sledgeHammer), new ItemStack(Items.BOOK), MODE_AND, false, false, new ItemStack(telescopicSassacrusher));
		CombinationRegistry.addCombination(new ItemStack(fearNoAnvil), new ItemStack(Items.LAVA_BUCKET), MODE_OR, false, false, new ItemStack(meltMasher));
		CombinationRegistry.addCombination(new ItemStack(qFHammerAxe), new ItemStack(gamegrlMagazine), MODE_OR, false, false, new ItemStack(qEHammerAxe));
		CombinationRegistry.addCombination(new ItemStack(qEHammerAxe), new ItemStack(sbahjPoster), MODE_AND, false, false, new ItemStack(dDEHammerAxe));
		CombinationRegistry.addCombination(new ItemStack(zillyhooHammer), new ItemStack(fluoriteOctet), MODE_AND, false, false, new ItemStack(popamaticVrillyhoo));
		CombinationRegistry.addCombination(new ItemStack(zillyhooHammer), new ItemStack(itemFrog, 1, 2), MODE_AND, false, true, new ItemStack(scarletZillyhoo));

		//canes
		CombinationRegistry.addCombination(new ItemStack(cane), new ItemStack(Items.IRON_SWORD), MODE_OR, false, false, new ItemStack(spearCane));
		CombinationRegistry.addCombination(new ItemStack(cane), new ItemStack(katana), MODE_OR, false, false, new ItemStack(spearCane));
		CombinationRegistry.addCombination(new ItemStack(ironCane), new ItemStack(Items.STONE_SWORD), MODE_OR, false, false, new ItemStack(spearCane));
		CombinationRegistry.addCombination(new ItemStack(ironCane), new ItemStack(Items.IRON_SWORD), MODE_OR, false, false, new ItemStack(spearCane));
		CombinationRegistry.addCombination(new ItemStack(ironCane), new ItemStack(katana), MODE_OR, false, false, new ItemStack(spearCane));
		CombinationRegistry.addCombination(new ItemStack(Blocks.RED_MUSHROOM), new ItemStack(Items.STICK), MODE_OR, false, false, new ItemStack(paradisesPortabello));
		CombinationRegistry.addCombination(new ItemStack(Blocks.BROWN_MUSHROOM), new ItemStack(Items.STICK), MODE_OR, false, false, new ItemStack(paradisesPortabello));
		CombinationRegistry.addCombination(new ItemStack(cane), new ItemStack(chessboard), MODE_AND, false, true, new ItemStack(regiCane));
		CombinationRegistry.addCombination(new ItemStack(ironCane), new ItemStack(chessboard), MODE_AND, false, true, new ItemStack(regiCane));
		CombinationRegistry.addCombination(new ItemStack(Items.STICK), new ItemStack(rawUranium), MODE_OR, false, false, new ItemStack(upStick));
		CombinationRegistry.addCombination(new ItemStack(ironCane), new ItemStack(Items.SLIME_BALL), MODE_AND, new ItemStack(pogoCane));

		//spoons/sporks/forks
		CombinationRegistry.addCombination(new ItemStack(Items.WOODEN_SHOVEL), new ItemStack(Items.BOWL), MODE_AND, false, true, new ItemStack(woodenSpoon));
		CombinationRegistry.addCombination(new ItemStack(Items.WOODEN_SHOVEL), new ItemStack(Items.MUSHROOM_STEW), MODE_AND, false, true, new ItemStack(woodenSpoon));
		CombinationRegistry.addCombination(new ItemStack(Items.WOODEN_SHOVEL), new ItemStack(Items.RABBIT_STEW), MODE_AND, false, true, new ItemStack(woodenSpoon));
		CombinationRegistry.addCombination(new ItemStack(Items.WOODEN_SHOVEL), new ItemStack(Items.BEETROOT_SOUP), MODE_AND, false, true, new ItemStack(woodenSpoon));
		CombinationRegistry.addCombination(new ItemStack(Items.IRON_SHOVEL), new ItemStack(Items.BOWL), MODE_AND, false, true, new ItemStack(silverSpoon));
		CombinationRegistry.addCombination(new ItemStack(Items.IRON_SHOVEL), new ItemStack(Items.MUSHROOM_STEW), MODE_AND, false, true, new ItemStack(silverSpoon));
		CombinationRegistry.addCombination(new ItemStack(Items.IRON_SHOVEL), new ItemStack(Items.RABBIT_STEW), MODE_AND, false, true, new ItemStack(silverSpoon));
		CombinationRegistry.addCombination(new ItemStack(Items.IRON_SHOVEL), new ItemStack(Items.BEETROOT_SOUP), MODE_AND, false, true, new ItemStack(silverSpoon));
		CombinationRegistry.addCombination(new ItemStack(woodenSpoon), new ItemStack(Items.IRON_INGOT), MODE_AND, false, false, new ItemStack(silverSpoon));
		CombinationRegistry.addCombination(new ItemStack(silverSpoon), new ItemStack(Items.CAKE), MODE_AND, false, false, new ItemStack(crockerSpoon));
		CombinationRegistry.addCombination(new ItemStack(fork), new ItemStack(chessboard), MODE_AND, false, true, new ItemStack(skaiaFork));
		CombinationRegistry.addCombination(new ItemStack(fork), new ItemStack(woodenSpoon), MODE_OR, false, false, new ItemStack(spork));
		CombinationRegistry.addCombination(new ItemStack(spork), new ItemStack(Items.GOLD_INGOT), MODE_OR, false, false, new ItemStack(goldenSpork));

		CombinationRegistry.addCombination(new ItemStack(crockerSpoon), new ItemStack(captchaCard), MODE_OR, false, true, new ItemStack(gristWidget, 1, 0));
		CombinationRegistry.addCombination(new ItemStack(Items.ENDER_PEARL), new ItemStack(Blocks.IRON_BLOCK), MODE_AND, false, false, new ItemStack(transportalizer));
		CombinationRegistry.addCombination(new ItemStack(captchaCard), new ItemStack(MinestuckBlocks.sburbComputer), MODE_AND, false, false, new ItemStack(captcharoidCamera));
		CombinationRegistry.addCombination(new ItemStack(captchaCard), new ItemStack(Items.ENDER_EYE), MODE_OR, false, false, new ItemStack(captcharoidCamera));

		CombinationRegistry.addCombination(new ItemStack(Blocks.IRON_BARS), new ItemStack(Items.LEATHER), MODE_AND, false, true, new ItemStack(catClaws));
		CombinationRegistry.addCombination(new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.GOLD_NUGGET), MODE_AND, new ItemStack(goldSeeds));
		CombinationRegistry.addCombination(new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.GOLD_INGOT), MODE_AND, new ItemStack(goldSeeds));
		CombinationRegistry.addCombination(new ItemStack(Items.BOAT), new ItemStack(Items.MINECART), MODE_OR, new ItemStack(metalBoat, 1, 0));
		CombinationRegistry.addCombination(new ItemStack(Items.BOAT), new ItemStack(Items.IRON_INGOT), MODE_AND, new ItemStack(metalBoat, 1, 0));
		CombinationRegistry.addCombination(new ItemStack(Items.BOAT), new ItemStack(Blocks.IRON_BLOCK), MODE_AND, new ItemStack(metalBoat, 1, 0));
		CombinationRegistry.addCombination(new ItemStack(Items.BOAT), new ItemStack(Items.GOLD_INGOT), MODE_AND, new ItemStack(metalBoat, 1, 1));
		CombinationRegistry.addCombination(new ItemStack(Items.BOAT), new ItemStack(Blocks.GOLD_BLOCK), MODE_AND, new ItemStack(metalBoat, 1, 1));
		CombinationRegistry.addCombination(new ItemStack(Blocks.DIRT), new ItemStack(Items.DYE, 1, EnumDyeColor.BLUE.getDyeDamage()), MODE_OR, new ItemStack(coloredDirt, 1, 0));
		CombinationRegistry.addCombination(new ItemStack(Blocks.DIRT), new ItemStack(Items.DYE, 1, EnumDyeColor.LIME.getDyeDamage()), MODE_OR, new ItemStack(coloredDirt, 1, 1));
		CombinationRegistry.addCombination(new ItemStack(Blocks.SAND), new ItemStack(Blocks.SNOW_LAYER), MODE_OR, new ItemStack(layeredSand));
		CombinationRegistry.addCombination(new ItemStack(Items.WATER_BUCKET), new ItemStack(Items.LAVA_BUCKET), MODE_OR, new ItemStack(obsidianBucket));    //water_bucket && lava bucket could make a bucket with liquid obsidian? (from a mod that adds liquid obsidian)
		CombinationRegistry.addCombination(new ItemStack(Items.BUCKET), new ItemStack(Blocks.OBSIDIAN), MODE_AND, new ItemStack(obsidianBucket));    //bucket || obsidian could make a bucket made out of obsidian
		CombinationRegistry.addCombination(new ItemStack(Blocks.BROWN_MUSHROOM), new ItemStack(Items.GLOWSTONE_DUST), MODE_OR, new ItemStack(glowingMushroom));
		CombinationRegistry.addCombination(new ItemStack(Blocks.LOG), new ItemStack(glowingMushroom), MODE_OR, false, true, new ItemStack(glowingLog));
		CombinationRegistry.addCombination(new ItemStack(Blocks.LOG2), new ItemStack(glowingMushroom), MODE_OR, false, true, new ItemStack(glowingLog));
		CombinationRegistry.addCombination(new ItemStack(Blocks.PLANKS), new ItemStack(glowingMushroom), MODE_OR, false, true, new ItemStack(glowingPlanks));
		CombinationRegistry.addCombination(new ItemStack(Blocks.SLIME_BLOCK), new ItemStack(glowingMushroom), MODE_OR, false, true, new ItemStack(glowyGoop));
		CombinationRegistry.addCombination(new ItemStack(Blocks.STONE), new ItemStack(Blocks.RED_FLOWER), MODE_OR, new ItemStack(petrifiedPoppy));
		CombinationRegistry.addCombination(new ItemStack(Blocks.GRAVEL), new ItemStack(Blocks.RED_FLOWER), MODE_OR, new ItemStack(petrifiedPoppy));
		CombinationRegistry.addCombination(new ItemStack(Blocks.COBBLESTONE), new ItemStack(Blocks.RED_FLOWER), MODE_OR, new ItemStack(petrifiedPoppy));
		CombinationRegistry.addCombination(new ItemStack(Blocks.LOG), new ItemStack(Blocks.STONE), MODE_OR, new ItemStack(petrifiedLog));
		CombinationRegistry.addCombination(new ItemStack(Blocks.LOG), new ItemStack(Blocks.COBBLESTONE), MODE_OR, new ItemStack(petrifiedLog));
		CombinationRegistry.addCombination(new ItemStack(Blocks.LOG), new ItemStack(Blocks.GRAVEL), MODE_OR, new ItemStack(petrifiedLog));
		CombinationRegistry.addCombination(new ItemStack(Blocks.CACTUS), new ItemStack(Blocks.RED_FLOWER), MODE_AND, new ItemStack(bloomingCactus));
		CombinationRegistry.addCombination(new ItemStack(Blocks.CACTUS), new ItemStack(Blocks.YELLOW_FLOWER), MODE_AND, new ItemStack(bloomingCactus));
		CombinationRegistry.addCombination(new ItemStack(Items.SUGAR), new ItemStack(Items.WHEAT_SEEDS), MODE_AND, new ItemStack(candy, 1, 0));

		CombinationRegistry.addCombination(new ItemStack(itemFrog, 1, 1), new ItemStack(Blocks.STONE), MODE_OR, true, true, new ItemStack(frogStatueReplica));
		CombinationRegistry.addCombination(new ItemStack(Items.GLASS_BOTTLE), new ItemStack(Items.IRON_SWORD), MODE_OR, false, false, new ItemStack(blender));

		CombinationRegistry.addCombination(new ItemStack(grasshopper), new ItemStack(Items.GOLD_INGOT), MODE_OR, false, false, new ItemStack(goldenGrasshopper));
		CombinationRegistry.addCombination(new ItemStack(Items.STICK), new ItemStack(Blocks.WEB), MODE_OR, false, false, new ItemStack(bugNet));
		CombinationRegistry.addCombination(new ItemStack(Items.STRING), new ItemStack(Items.BUCKET), MODE_AND, new ItemStack(bugNet));
		CombinationRegistry.addCombination(new ItemStack(Items.COOKIE), new ItemStack(Blocks.REDSTONE_BLOCK), MODE_AND, new ItemStack(sugarCube));
		CombinationRegistry.addCombination(new ItemStack(surpriseEmbryo), new ItemStack(grimoire), MODE_OR, new ItemStack(unknowableEgg));
		CombinationRegistry.addCombination(new ItemStack(Blocks.NOTEBLOCK), new ItemStack(grimoire), MODE_AND, new ItemStack(longForgottenWarhorn));

		if (oreMultiplier != 0)
		{
			CombinationRegistry.addCombination(new ItemStack(Items.COAL), new ItemStack(Blocks.NETHERRACK), MODE_AND, new ItemStack(coalOreNetherrack));
			CombinationRegistry.addCombination(new ItemStack(Items.IRON_INGOT), new ItemStack(Blocks.END_STONE), MODE_AND, new ItemStack(ironOreEndStone));
			CombinationRegistry.addCombination(new ItemStack(Items.IRON_INGOT), new ItemStack(Blocks.SANDSTONE), MODE_AND, new ItemStack(ironOreSandstone));
			CombinationRegistry.addCombination(new ItemStack(Items.IRON_INGOT), new ItemStack(Blocks.RED_SANDSTONE), MODE_AND, new ItemStack(ironOreSandstoneRed));
			CombinationRegistry.addCombination(new ItemStack(Items.GOLD_INGOT), new ItemStack(Blocks.SANDSTONE), MODE_AND, new ItemStack(goldOreSandstone));
			CombinationRegistry.addCombination(new ItemStack(Items.GOLD_INGOT), new ItemStack(Blocks.RED_SANDSTONE), MODE_AND, new ItemStack(goldOreSandstoneRed));
			CombinationRegistry.addCombination(new ItemStack(Items.REDSTONE), new ItemStack(Blocks.END_STONE), MODE_AND, new ItemStack(redstoneOreEndStone));
		}

		CombinationRegistry.addCombination(new ItemStack(Items.DIAMOND_SWORD), new ItemStack(Items.EMERALD), MODE_OR, false, false, new ItemStack(emeraldSword));
		CombinationRegistry.addCombination(new ItemStack(Items.DIAMOND_AXE), new ItemStack(Items.EMERALD), MODE_OR, false, false, new ItemStack(emeraldAxe));
		CombinationRegistry.addCombination(new ItemStack(Items.DIAMOND_PICKAXE), new ItemStack(Items.EMERALD), MODE_OR, false, false, new ItemStack(emeraldPickaxe));
		CombinationRegistry.addCombination(new ItemStack(Items.DIAMOND_SHOVEL), new ItemStack(Items.EMERALD), MODE_OR, false, false, new ItemStack(emeraldShovel));
		CombinationRegistry.addCombination(new ItemStack(Items.DIAMOND_HOE), new ItemStack(Items.EMERALD), MODE_OR, false, false, new ItemStack(emeraldHoe));

		ItemArmor[] metalHelmets = new ItemArmor[]{Items.IRON_HELMET, Items.GOLDEN_HELMET, Items.DIAMOND_HELMET};
		ItemArmor[] metalChestplates = new ItemArmor[]{Items.IRON_CHESTPLATE, Items.GOLDEN_CHESTPLATE, Items.DIAMOND_CHESTPLATE};
		ItemArmor[] metalLeggings = new ItemArmor[]{Items.IRON_LEGGINGS, Items.GOLDEN_LEGGINGS, Items.DIAMOND_LEGGINGS};
		ItemArmor[] metalBoots = new ItemArmor[]{Items.IRON_BOOTS, Items.GOLDEN_BOOTS, Items.DIAMOND_BOOTS};
		for (int i = 0; i < metalHelmets.length; i++)    //Two out of three possible for-loops is enough for me
			for (ItemStack prismarine : new ItemStack[]{new ItemStack(Items.PRISMARINE_SHARD), new ItemStack(Blocks.PRISMARINE)})
			{
				CombinationRegistry.addCombination(new ItemStack(metalHelmets[i]), prismarine, MODE_OR, false, false, new ItemStack(prismarineHelmet));
				CombinationRegistry.addCombination(new ItemStack(metalChestplates[i]), prismarine, MODE_OR, false, false, new ItemStack(prismarineChestplate));
				CombinationRegistry.addCombination(new ItemStack(metalLeggings[i]), prismarine, MODE_OR, false, false, new ItemStack(prismarineLeggings));
				CombinationRegistry.addCombination(new ItemStack(metalBoots[i]), prismarine, MODE_OR, false, false, new ItemStack(prismarineBoots));
			}

		CombinationRegistry.addCombination(new ItemStack(Blocks.TNT), new ItemStack(Blocks.STONE_BUTTON), MODE_OR, new ItemStack(primedTnt));
		CombinationRegistry.addCombination(new ItemStack(Blocks.TNT), new ItemStack(Blocks.WOODEN_BUTTON), MODE_OR, new ItemStack(primedTnt));
		CombinationRegistry.addCombination(new ItemStack(Blocks.TNT), new ItemStack(Blocks.REDSTONE_TORCH), MODE_OR, new ItemStack(unstableTnt));
		CombinationRegistry.addCombination(new ItemStack(Blocks.TNT), new ItemStack(Items.POTIONITEM, 1, 8236), MODE_OR, true, true, new ItemStack(instantTnt));    //Instant damage potions
		CombinationRegistry.addCombination(new ItemStack(Blocks.TNT), new ItemStack(Items.POTIONITEM, 1, 8268), MODE_OR, true, true, new ItemStack(instantTnt));
		CombinationRegistry.addCombination(new ItemStack(Blocks.TNT), new ItemStack(Blocks.STONE_BUTTON), MODE_AND, new ItemStack(stoneExplosiveButton));
		CombinationRegistry.addCombination(new ItemStack(instantTnt), new ItemStack(Blocks.STONE_BUTTON), MODE_AND, new ItemStack(stoneExplosiveButton));
		CombinationRegistry.addCombination(new ItemStack(Blocks.TNT), new ItemStack(Blocks.WOODEN_BUTTON), MODE_AND, new ItemStack(woodenExplosiveButton));
		CombinationRegistry.addCombination(new ItemStack(instantTnt), new ItemStack(Blocks.WOODEN_BUTTON), MODE_AND, new ItemStack(woodenExplosiveButton));
		CombinationRegistry.addCombination(new ItemStack(Blocks.GRAVEL), new ItemStack(Blocks.STONE), MODE_AND, new ItemStack(coarseStone));
		CombinationRegistry.addCombination(new ItemStack(Blocks.GRAVEL), new ItemStack(Blocks.STONEBRICK, 1, BlockStoneBrick.CHISELED_META), MODE_AND, new ItemStack(chiseledCoarseStone));
		CombinationRegistry.addCombination(new ItemStack(coarseStone), new ItemStack(Blocks.STONEBRICK, 1, BlockStoneBrick.CHISELED_META), MODE_AND, new ItemStack(chiseledCoarseStone));
		CombinationRegistry.addCombination(new ItemStack(Blocks.STONEBRICK), new ItemStack(coloredDirt, 1, 0), MODE_OR, new ItemStack(shadeBrick));
		CombinationRegistry.addCombination(new ItemStack(Blocks.STONE_BRICK_STAIRS), new ItemStack(coloredDirt, 1, 0), MODE_OR, new ItemStack(stairs.get(shadeBrick)));
		CombinationRegistry.addCombination(new ItemStack(Blocks.STONEBRICK), new ItemStack(Items.DYE, 1, EnumDyeColor.BLUE.getDyeDamage()), MODE_OR, new ItemStack(shadeBrick));
		CombinationRegistry.addCombination(new ItemStack(Blocks.STONE_BRICK_STAIRS), new ItemStack(Items.DYE, 1, EnumDyeColor.BLUE.getDyeDamage()), MODE_OR, new ItemStack(stairs.get(shadeBrick)));
		for (int i = 0; i <= 6; i += 2)    //Stone and polished stone
		{
			CombinationRegistry.addCombination(new ItemStack(Blocks.STONE, 1, i), new ItemStack(coloredDirt, 1, 0), MODE_OR, new ItemStack(shadeBrickSmooth));
			CombinationRegistry.addCombination(new ItemStack(Blocks.STONE, 1, i), new ItemStack(Items.DYE, 1, EnumDyeColor.BLUE.getDyeDamage()), MODE_OR, new ItemStack(shadeBrickSmooth));
			CombinationRegistry.addCombination(new ItemStack(Blocks.STONE, 1, i), new ItemStack(Blocks.ICE), MODE_AND, new ItemStack(frostTile));
			CombinationRegistry.addCombination(new ItemStack(Blocks.STONE, 1, i), new ItemStack(Blocks.PACKED_ICE), MODE_AND, new ItemStack(frostTile));
		}
		CombinationRegistry.addCombination(new ItemStack(Blocks.STONEBRICK), new ItemStack(Blocks.ICE), MODE_AND, new ItemStack(frostTile));
		CombinationRegistry.addCombination(new ItemStack(Blocks.STONEBRICK), new ItemStack(Blocks.PACKED_ICE), MODE_AND, new ItemStack(frostTile));
		CombinationRegistry.addCombination(new ItemStack(Blocks.STONEBRICK, 1, BlockStoneBrick.CHISELED_META), new ItemStack(Blocks.ICE), MODE_AND, new ItemStack(chiseledFrostBrick));
		CombinationRegistry.addCombination(new ItemStack(Blocks.STONEBRICK, 1, BlockStoneBrick.CHISELED_META), new ItemStack(Blocks.PACKED_ICE), MODE_AND, new ItemStack(chiseledFrostBrick));
		CombinationRegistry.addCombination(new ItemStack(Blocks.IRON_BLOCK), new ItemStack(Items.LAVA_BUCKET), MODE_AND, new ItemStack(castIron));
		CombinationRegistry.addCombination(new ItemStack(Blocks.STONEBRICK, 1, BlockStoneBrick.CHISELED_META), new ItemStack(castIron), MODE_OR, new ItemStack(chiseledCastIron));
		CombinationRegistry.addCombination(new ItemStack(Blocks.DIRT, 1, 1), new ItemStack(Blocks.END_STONE), MODE_OR, new ItemStack(coarseEndStone));
		CombinationRegistry.addCombination(new ItemStack(Blocks.GRASS), new ItemStack(Blocks.END_STONE), MODE_OR, new ItemStack(endGrass));
		CombinationRegistry.addCombination(new ItemStack(Blocks.MYCELIUM), new ItemStack(Blocks.END_STONE), MODE_OR, new ItemStack(endGrass));
		CombinationRegistry.addCombination(new ItemStack(endGrass), new ItemStack(Blocks.DIRT), MODE_OR, new ItemStack(Blocks.GRASS));
		CombinationRegistry.addCombination(new ItemStack(Blocks.LOG, 1, 0), new ItemStack(Blocks.VINE), MODE_AND, new ItemStack(log, 1, 0));
		CombinationRegistry.addCombination(new ItemStack(log, 1, 0), new ItemStack(Blocks.YELLOW_FLOWER), MODE_OR, true, false, new ItemStack(log, 1, 1));
		CombinationRegistry.addCombination(new ItemStack(log, 1, 0), new ItemStack(Blocks.RED_FLOWER), MODE_OR, true, false, new ItemStack(log, 1, 1));
		CombinationRegistry.addCombination(new ItemStack(Blocks.MOSSY_COBBLESTONE, 1, 0), new ItemStack(Blocks.YELLOW_FLOWER), MODE_OR, true, false, new ItemStack(floweryMossStone));
		CombinationRegistry.addCombination(new ItemStack(Blocks.MOSSY_COBBLESTONE, 1, 0), new ItemStack(Blocks.RED_FLOWER), MODE_OR, true, false, new ItemStack(floweryMossStone));
		CombinationRegistry.addCombination(new ItemStack(Blocks.STONEBRICK, 1, BlockStoneBrick.MOSSY_META), new ItemStack(Blocks.YELLOW_FLOWER), MODE_OR, true, false, new ItemStack(floweryMossBrick));
		CombinationRegistry.addCombination(new ItemStack(Blocks.STONEBRICK, 1, BlockStoneBrick.MOSSY_META), new ItemStack(Blocks.RED_FLOWER), MODE_OR, true, false, new ItemStack(floweryMossBrick));
		CombinationRegistry.addCombination("plankWood", new ItemStack(Blocks.NETHERRACK), false, MODE_OR, new ItemStack(treatedPlanks));
		CombinationRegistry.addCombination(new ItemStack(Blocks.SNOW), new ItemStack(Blocks.LOG), MODE_OR, false, true, new ItemStack(log, 1, 2));
		CombinationRegistry.addCombination(new ItemStack(Blocks.SNOW_LAYER), new ItemStack(Blocks.LOG), MODE_OR, false, true, new ItemStack(log, 1, 2));
		CombinationRegistry.addCombination(new ItemStack(Items.SNOWBALL), new ItemStack(Blocks.LOG), MODE_OR, false, true, new ItemStack(log, 1, 2));
		CombinationRegistry.addCombination(new ItemStack(Items.WOODEN_SWORD), new ItemStack(Blocks.CACTUS), MODE_OR, false, true, new ItemStack(woodenCactus));
		CombinationRegistry.addCombination(new ItemStack(Blocks.PLANKS), new ItemStack(Blocks.CACTUS), MODE_OR, false, true, new ItemStack(woodenCactus));
		CombinationRegistry.addCombination(new ItemStack(Blocks.LOG), new ItemStack(Blocks.CACTUS), MODE_OR, false, true, new ItemStack(woodenCactus));
		CombinationRegistry.addCombination(new ItemStack(Blocks.LOG2), new ItemStack(Blocks.CACTUS), MODE_OR, false, true, new ItemStack(woodenCactus));
		CombinationRegistry.addCombination(new ItemStack(leaves, 1, 3), new ItemStack(log, 1, 3), MODE_OR, new ItemStack(rainbowSapling));
		CombinationRegistry.addCombination(new ItemStack(endLeaves), new ItemStack(endLog), MODE_OR, new ItemStack(endSapling));
		CombinationRegistry.addCombination(new ItemStack(Blocks.STONE), new ItemStack(carvingTool), MODE_AND, false, true, new ItemStack(stoneTablet));
		CombinationRegistry.addCombination(new ItemStack(Items.REDSTONE), new ItemStack(Items.GLOWSTONE_DUST), MODE_OR, new ItemStack(glowystoneDust));
		CombinationRegistry.addCombination(new ItemStack(Items.CAKE), new ItemStack(Items.APPLE), MODE_OR, new ItemStack(appleCake));
		CombinationRegistry.addCombination(new ItemStack(Items.CAKE), new ItemStack(glowingMushroom), MODE_OR, new ItemStack(blueCake));
		CombinationRegistry.addCombination(new ItemStack(Items.CAKE), new ItemStack(Blocks.ICE), MODE_OR, new ItemStack(coldCake));
		CombinationRegistry.addCombination(new ItemStack(Items.CAKE), new ItemStack(Blocks.PACKED_ICE), MODE_OR, new ItemStack(coldCake));
		CombinationRegistry.addCombination(new ItemStack(Items.CAKE), new ItemStack(Items.MELON), MODE_OR, new ItemStack(redCake));
		CombinationRegistry.addCombination(new ItemStack(Items.CAKE), new ItemStack(Items.SPECKLED_MELON), MODE_OR, new ItemStack(redCake));
		CombinationRegistry.addCombination(new ItemStack(Items.CAKE), new ItemStack(Items.LAVA_BUCKET), MODE_OR, new ItemStack(hotCake));
		CombinationRegistry.addCombination(new ItemStack(Items.CAKE), new ItemStack(Items.BLAZE_POWDER), MODE_OR, new ItemStack(hotCake));
		CombinationRegistry.addCombination(new ItemStack(Items.CAKE), new ItemStack(Blocks.MAGMA), MODE_OR, new ItemStack(hotCake));
		CombinationRegistry.addCombination(new ItemStack(Items.CAKE), new ItemStack(Blocks.GLASS), MODE_OR, new ItemStack(reverseCake));
		CombinationRegistry.addCombination(new ItemStack(Items.CAKE), new ItemStack(Blocks.GLASS_PANE), MODE_OR, new ItemStack(reverseCake));
		CombinationRegistry.addCombination(new ItemStack(Items.COOKIE), new ItemStack(Blocks.STONE), MODE_AND, false, false, new ItemStack(rockCookie));
		CombinationRegistry.addCombination(new ItemStack(Items.COOKIE), new ItemStack(Blocks.COBBLESTONE), MODE_AND, false, false, new ItemStack(rockCookie));
		CombinationRegistry.addCombination(new ItemStack(Items.COOKIE), new ItemStack(Blocks.GRAVEL), MODE_AND, false, false, new ItemStack(rockCookie));
		CombinationRegistry.addCombination(new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Blocks.BROWN_MUSHROOM), MODE_OR, false, false, new ItemStack(fungalSpore));
		CombinationRegistry.addCombination(new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Blocks.RED_MUSHROOM), MODE_OR, false, false, new ItemStack(fungalSpore));
		CombinationRegistry.addCombination(new ItemStack(Items.COOKIE), new ItemStack(fungalSpore), MODE_AND, false, false, new ItemStack(sporeo));
		CombinationRegistry.addCombination(new ItemStack(Items.POTATO), new ItemStack(Items.STICK), MODE_AND, new ItemStack(frenchFry));
		CombinationRegistry.addCombination(new ItemStack(Items.POTATO), new ItemStack(Items.BLAZE_ROD), MODE_AND, new ItemStack(frenchFry));
		CombinationRegistry.addCombination(new ItemStack(Items.EGG), new ItemStack(Blocks.PUMPKIN), MODE_AND, false, false, new ItemStack(surpriseEmbryo));

		//uranium-based non-weapon and uranium cooker recipes
		CombinationRegistry.addCombination(new ItemStack(rawCruxite), new ItemStack(rawUranium), MODE_AND, new ItemStack(energyCore));
		CombinationRegistry.addCombination(new ItemStack(rawUranium), new ItemStack(Items.COOKED_BEEF), MODE_OR, new ItemStack(irradiatedSteak));
		CombinationRegistry.addCombination(new ItemStack(upStick), new ItemStack(energyCore), MODE_AND, new ItemStack(quantumSabre));

		//CombinationRegistry.addCombination(new ItemStack(Items.POTIONITEM, 1, 0), new ItemStack(Items.SUGAR), MODE_OR, false, false, new ItemStack(beverage, 1, 0));		//Tab
		//CombinationRegistry.addCombination(new ItemStack(Items.POTIONITEM, 1, 0), new ItemStack(Items.DYE, 1, 14), MODE_OR, false, true, new ItemStack(beverage, 1, 1));	//Orange F
		//CombinationRegistry.addCombination(new ItemStack(beverage, 1, 1), new ItemStack(Items.APPLE), MODE_OR, true, false, new ItemStack(beverage, 1, 2));				//CandyApple F
		//CombinationRegistry.addCombination(new ItemStack(beverage, 1, 1), new ItemStack(beverage, 1, 0), MODE_OR, true, true, new ItemStack(beverage, 1, 3));				//Cola F
		//CombinationRegistry.addCombination(new ItemStack(beverage, 1, 1), new ItemStack(Blocks.WOOL, 1, 3), MODE_OR, true, true, new ItemStack(beverage, 1, 4));			//Cotton Candy F
		//CombinationRegistry.addCombination(new ItemStack(beverage, 1, 1), new ItemStack(Items.MILK_BUCKET), MODE_OR, true, false, new ItemStack(beverage, 1, 5));			//Creme F
		//CombinationRegistry.addCombination(new ItemStack(beverage, 1, 1), new ItemStack(Items.CHORUS_FRUIT), MODE_OR, true, false, new ItemStack(beverage, 1, 6));		//Grape F
		//CombinationRegistry.addCombination(new ItemStack(beverage, 1, 1), new ItemStack(Items.DYE, 1, 10), MODE_OR, true, true, new ItemStack(beverage, 1, 7));			//Moonmist F
		//CombinationRegistry.addCombination(new ItemStack(beverage, 1, 2), new ItemStack(Items.DYE, 1, 9), MODE_AND, true, true, new ItemStack(beverage, 1, 8));			//Peach F
		//CombinationRegistry.addCombination(new ItemStack(beverage, 1, 1), new ItemStack(Blocks.TNT), MODE_OR, true, false, new ItemStack(beverage, 1, 9));				//Redpop F

		CombinationRegistry.addCombination(new ItemStack(rawCruxite), new ItemStack(Items.EMERALD), MODE_OR, new ItemStack(boondollars));

		CombinationRegistry.addCombination(new ItemStack(Blocks.STONE), new ItemStack(Blocks.OBSIDIAN), MODE_OR, new ItemStack(hardStone));
		CombinationRegistry.addCombination(new ItemStack(Blocks.STONE), new ItemStack(Blocks.BEDROCK), MODE_AND, new ItemStack(hardStone));
		CombinationRegistry.addCombination(new ItemStack(MinestuckBlocks.cruxiteBlock), new ItemStack(Items.FEATHER), MODE_AND, new ItemStack(floatStone));
		CombinationRegistry.addCombination(new ItemStack(Items.ENDER_EYE), new ItemStack(Items.BOOK), MODE_OR, new ItemStack(eightBall));
		//CombinationRegistry.addCombination(new ItemStack(eightBall), new ItemStack(popTart), MODE_AND, new ItemStack(popBall));
		CombinationRegistry.addCombination(new ItemStack(cruxiteGel), new ItemStack(Items.DRAGON_BREATH), MODE_OR, false, false, new ItemStack(dragonGel));
		//CombinationRegistry.addCombination(new ItemStack(dragonGel), new ItemStack(popBall), MODE_AND, MinestuckGrist.zillium.getCandyItem());

		/*CombinationRegistry.addCombination(new ItemStack(stackModus), new ItemStack(queueModus), MODE_AND, true, true, new ItemStack(queueStackModus));
		CombinationRegistry.addCombination("stickWood", "modus", MODE_OR, new ItemStack(treeModus));
		CombinationRegistry.addCombination("treeSapling", "modus", MODE_OR, new ItemStack(treeModus));
		CombinationRegistry.addCombination("treeLeaves", "modus", MODE_OR, new ItemStack(treeModus));	//Not planks and logs though. Too little branch-related.
		CombinationRegistry.addCombination("modus", new ItemStack(sburbComputer), true, MODE_AND, new ItemStack(hashmapModus));
		CombinationRegistry.addCombination("modus", new ItemStack(Items.ITEM_FRAME), true, MODE_AND, new ItemStack(setModus));
		CombinationRegistry.addCombination("modus", new ItemStack(grimoire), MODE_OR, new ItemStack(wildMagicModus));
		CombinationRegistry.addCombination("modus", new ItemStack(Items.BOOK), MODE_OR, new ItemStack(bookModus));
		CombinationRegistry.addCombination("modus", new ItemStack(Items.WRITABLE_BOOK), MODE_AND, new ItemStack(bookModus));
		CombinationRegistry.addCombination("modus", new ItemStack(Items.WRITTEN_BOOK), MODE_AND, new ItemStack(bookModus));
		CombinationRegistry.addCombination("modus", new ItemStack(Items.ENCHANTED_BOOK), MODE_AND, new ItemStack(bookModus));
		CombinationRegistry.addCombination("modus", new ItemStack(boondollars), MODE_OR, new ItemStack(capitalistModus));
		CombinationRegistry.addCombination(new ItemStack(captchaCard), new ItemStack(setModus), MODE_AND, new ItemStack(deckModus));
		CombinationRegistry.addCombination("modus", new ItemStack(Items.CAKE), MODE_AND, new ItemStack(popTartModus));
		CombinationRegistry.addCombination("modus", new ItemStack(eightBall), MODE_OR, new ItemStack(eightBallModus));
		CombinationRegistry.addCombination(new ItemStack(bookModus), new ItemStack(Items.ENDER_EYE), MODE_OR, true, false, new ItemStack(eightBallModus));
		CombinationRegistry.addCombination(new ItemStack(Items.DYE), new ItemStack(queueModus), MODE_OR, false, true, new ItemStack(hueModus));
		CombinationRegistry.addCombination(new ItemStack(Items.DYE), new ItemStack(queueStackModus), MODE_OR, false, true, new ItemStack(hueStackModus));
		CombinationRegistry.addCombination(new ItemStack(hueModus), new ItemStack(stackModus), MODE_AND, new ItemStack(hueStackModus));
		CombinationRegistry.addCombination(new ItemStack(MinestuckBlocks.sburbComputer), new ItemStack(setModus), MODE_OR, false, true, new ItemStack(chatModus));
		CombinationRegistry.addCombination(new ItemStack(Items.ITEM_FRAME), new ItemStack(hashmapModus), MODE_AND, false, true, new ItemStack(chatModus));
		CombinationRegistry.addCombination(new ItemStack(deckModus), new ItemStack(hueModus), MODE_AND, new ItemStack(scratchAndSniffModus));
		CombinationRegistry.addCombination(new ItemStack(Items.APPLE), new ItemStack(setModus), MODE_OR, new ItemStack(scratchAndSniffModus));
		CombinationRegistry.addCombination(new ItemStack(Blocks.LEVER), new ItemStack(operandiModus), MODE_AND, new ItemStack(chasityModus));
		CombinationRegistry.addCombination("modus", new ItemStack(Blocks.IRON_TRAPDOOR), MODE_OR, new ItemStack(chasityModus));
		CombinationRegistry.addCombination(new ItemStack(Blocks.TRAPDOOR), new ItemStack(weightModus), MODE_OR, true, false, new ItemStack(chasityModus));
		CombinationRegistry.addCombination(new ItemStack(Items.SLIME_BALL), new ItemStack(setModus), MODE_AND,true, false, new ItemStack(slimeModus));
		CombinationRegistry.addCombination(new ItemStack(cycloneModus), new ItemStack(onion), MODE_OR, new ItemStack(onionModus));
		CombinationRegistry.addCombination(new ItemStack(cycloneModus), new ItemStack(treeModus), MODE_AND, new ItemStack(onionModus));
		CombinationRegistry.addCombination(new ItemStack(energyCell), new ItemStack(hashmapModus), MODE_OR, false, true, new ItemStack(energyModus));
		CombinationRegistry.addCombination(new ItemStack(weightModus), new ItemStack(energyCore), MODE_AND, new ItemStack(energyModus));
		CombinationRegistry.addCombination(new ItemStack(eightBallModus), new ItemStack(popTartModus), MODE_AND, new ItemStack(operandiModus));
		CombinationRegistry.addCombination(new ItemStack(Items.REDSTONE), new ItemStack(hashmapModus), MODE_AND, new ItemStack(operandiModus));
		CombinationRegistry.addCombination("modus", new ItemStack(Blocks.IRON_BLOCK), MODE_OR, new ItemStack(weightModus));
		CombinationRegistry.addCombination(new ItemStack(Blocks.CHEST), new ItemStack(jujuModus), MODE_AND, new ItemStack(modUs));
		CombinationRegistry.addCombination("modus", new ItemStack(MinestuckBlocks.alchemiter[0]), MODE_OR, new ItemStack(alcheModus));
		CombinationRegistry.addCombination("modus", new ItemStack(miniAlchemiter, 1), MODE_OR, new ItemStack(alcheModus));



		CombinationRegistry.addCombination("modus", new ItemStack(rawCruxite), MODE_AND, new ItemStack(arrayModus));
		CombinationRegistry.addCombination(new ItemStack(arrayModus), new ItemStack(Items.LEATHER), MODE_AND, false, false, new ItemStack(walletModus));
		CombinationRegistry.addCombination(new ItemStack(walletModus), new ItemStack(eightBallModus), MODE_AND, false, false, new ItemStack(crystalBallModus));
		CombinationRegistry.addCombination(new ItemStack(walletModus), new ItemStack(eightBall), MODE_AND, false, false, new ItemStack(crystalBallModus));
		CombinationRegistry.addCombination("modus", new ItemStack(crystalEightBall), MODE_OR, new ItemStack(crystalBallModus));
		CombinationRegistry.addCombination(new ItemStack(arrayModus), new ItemStack(crystalEightBall), MODE_OR, false, false, new ItemStack(crystalBallModus));
		CombinationRegistry.addCombination("modus", new ItemStack(Items.ROTTEN_FLESH), MODE_OR, new ItemStack(monsterModus));
		CombinationRegistry.addCombination("modus", new ItemStack(Items.BONE), MODE_OR, new ItemStack(monsterModus));*/

		/*
		CombinationRegistry.addCombination(new ItemStack(Items.DYE, 1, 5), new ItemStack(deckModus), MODE_OR, true, true, new ItemStack(memoryModus));
		CombinationRegistry.addCombination(new ItemStack(minestuckBucket, 1, 2), new ItemStack(modusCard, 1, 5), MODE_OR, true, true, new ItemStack(memoryModus));
		CombinationRegistry.addCombination(new ItemStack(crockerSpoon), new ItemStack(modusCard), MODE_AND, false, false, new ItemStack(recipeModus));
		CombinationRegistry.addCombination(new ItemStack(Items.GLASS_BOTTLE), new ItemStack(modusCard), MODE_OR, false, false, new ItemStack(bottledMsgModus));
		CombinationRegistry.addCombination(new ItemStack(cruxiteBottle), new ItemStack(modusCard), MODE_OR, false, false, new ItemStack(bottledMsgModus));
		CombinationRegistry.addCombination(new ItemStack(Blocks.JUKEBOX), new ItemStack(modusCard, 1, 4), MODE_OR, false, true, new ItemStack(techHopModus));
		CombinationRegistry.addCombination(new ItemStack(chasityModus), new ItemStack(sburbComputer), MODE_AND, false, true, new ItemStack(encryptionModus));
		CombinationRegistry.addCombination(new ItemStack(Blocks.IRON_TRAPDOOR), new ItemStack(modusCard, 1, 4), MODE_OR, false, true, new ItemStack(encryptionModus));
		CombinationRegistry.addCombination(new ItemStack(chatModus), new ItemStack(grimoire), MODE_AND, false, false, new ItemStack(ouijaModus));
		CombinationRegistry.addCombination(new ItemStack(modusCard, 1, 2), new ItemStack(Items.RABBIT_HIDE), MODE_OR, true, false, new ItemStack(bundleModus));
		CombinationRegistry.addCombination(new ItemStack(Items.CAKE), new ItemStack(modusCard), MODE_OR, true, false, new ItemStack(cakeModus));
		CombinationRegistry.addCombination(new ItemStack(popTartModus), new ItemStack(bundleModus), MODE_AND, false, false, new ItemStack(cakeModus));
		CombinationRegistry.addCombination(new ItemStack(popTart), new ItemStack(bundleModus), MODE_AND, false, false, new ItemStack(cakeModus));
		CombinationRegistry.addCombination(new ItemStack(encryptionModus), new ItemStack(bookModus), MODE_OR, false, false, new ItemStack(cipherModus));
		*/

		//CombinationRegistry.addCombination(new ItemStack(hashchatModus), new ItemStack(chatModus), MODE_OR, true, false, new ItemStack(hashchatModus));
		//CombinationRegistry.addCombination(new ItemStack(monsterModus), new ItemStack(minestuckBucket, 1, 1), MODE_OR, false, true, new ItemStack(sacrificeModus));

		//CombinationRegistry.addCombination("modus", new ItemStack(zillystoneShard), MODE_AND, new ItemStack(jujuModus));
		//CombinationRegistry.addCombination(new ItemStack(moonstone), new ItemStack(slimeModus), MODE_AND, true, false, new ItemStack(monsterModus));

		//Alchemy
		CombinationRegistry.addCombination(new ItemStack(zillystoneShard), new ItemStack(Items.SUGAR), MODE_OR, MinestuckGrist.zillium.getCandyItem());

		//armor
		CombinationRegistry.addCombination(new ItemStack(Items.FISH, 1, 3), new ItemStack(Items.IRON_HELMET), MODE_AND, true, false, new ItemStack(diverHelmet));
		CombinationRegistry.addCombination(new ItemStack(diverHelmet), new ItemStack(knittingNeedles), MODE_OR, false, false, new ItemStack(spikedHelmet));
		CombinationRegistry.addCombination(new ItemStack(Items.LEATHER_HELMET), new ItemStack(MinestuckItems.itemFrog, 1, 1), MODE_OR, false, true, new ItemStack(frogHat));
		CombinationRegistry.addCombination(new ItemStack(Items.IRON_HELMET), new ItemStack(MinestuckBlocks.cruxtruderLid), MODE_OR, false, true, new ItemStack(cruxtruderHat));
		CombinationRegistry.addCombination(new ItemStack(Items.SLIME_BALL), new ItemStack(Items.LEATHER_BOOTS), MODE_AND, new ItemStack(rubberBoots));
		CombinationRegistry.addCombination(new ItemStack(Items.SLIME_BALL), new ItemStack(Items.LEATHER_BOOTS), MODE_OR, new ItemStack(moonShoes));
		CombinationRegistry.addCombination(new ItemStack(moonShoes), new ItemStack(sun), MODE_AND, new ItemStack(sunShoes));
		CombinationRegistry.addCombination(new ItemStack(rubberBoots), new ItemStack(yarnBall, 1, 14), MODE_AND, new ItemStack(airJordans));
		CombinationRegistry.addCombination(new ItemStack(rubberBoots), new ItemStack(yarnBall, 1, 4), MODE_AND, new ItemStack(cobaltJordans));
		CombinationRegistry.addCombination(new ItemStack(airJordans), new ItemStack(Blocks.OBSIDIAN), MODE_AND, new ItemStack(cobaltJordans));
		CombinationRegistry.addCombination(new ItemStack(moonShoes), new ItemStack(bunnySlippers), MODE_OR, new ItemStack(windWalkers));
		CombinationRegistry.addCombination(new ItemStack(airJordans), new ItemStack(Items.FIREWORKS), MODE_OR, new ItemStack(rocketBoots));

		//materials
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.rawCruxite), new ItemStack(Blocks.END_STONE), MODE_OR, new ItemStack(moonstone));
		CombinationRegistry.addCombination(new ItemStack(Items.DYE, 1, 4), new ItemStack(Items.DIAMOND), MODE_AND, true, false, new ItemStack(fluorite));
		CombinationRegistry.addCombination(new ItemStack(yarnBall, 1, 15), new ItemStack(wizardStatue), MODE_AND, true, false, new ItemStack(wizardbeardYarn));

		CombinationRegistry.addCombination(new ItemStack(Blocks.BEDROCK), new ItemStack(MinestuckItems.sbahjPoster), MODE_AND, new ItemStack(sbahjBedrock));
		CombinationRegistry.addCombination(new ItemStack(sbahjBedrock), MinestuckGrist.zillium.getCandyItem(), MODE_OR, false, true, new ItemStack(zillyStone));
		CombinationRegistry.addCombination(new ItemStack(Blocks.IRON_BLOCK), new ItemStack(Blocks.QUARTZ_BLOCK, 1, 0), MODE_OR, false, true, new ItemStack(smoothIron));
		CombinationRegistry.addCombination(new ItemStack(Blocks.LAPIS_BLOCK), new ItemStack(Blocks.DIAMOND_BLOCK), MODE_AND, new ItemStack(fluoriteBlock));
		CombinationRegistry.addCombination(new ItemStack(fluorite), new ItemStack(Blocks.STONE), MODE_AND, new ItemStack(fluoriteOre));
		CombinationRegistry.addCombination(new ItemStack(moonstone), new ItemStack(Blocks.STONE), MODE_AND, new ItemStack(moonstoneOre));
		CombinationRegistry.addCombination(new ItemStack(MinestuckBlocks.uraniumBlock), new ItemStack(netherPortal), MODE_AND, new ItemStack(netherReactorCore));
		CombinationRegistry.addCombination(new ItemStack(wizardHat), new ItemStack(Blocks.STONE), MODE_OR, new ItemStack(wizardStatue));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.sbahjPoster), new ItemStack(Blocks.SAPLING), MODE_AND, new ItemStack(sbahjTree));
		CombinationRegistry.addCombination(new ItemStack(moonstone), new ItemStack(Blocks.IRON_BLOCK), MODE_AND, new ItemStack(machineChasis));
		CombinationRegistry.addCombination(new ItemStack(gristBlocks.get(MinestuckGrist.build)), new ItemStack(wizardStatue), MODE_AND, new ItemStack(magicBlock));
		CombinationRegistry.addCombination(new ItemStack(Items.STRING), new ItemStack(Items.LEATHER), MODE_AND, new ItemStack(whip));
		CombinationRegistry.addCombination(new ItemStack(whip), new ItemStack(MinestuckItems.sbahjPoster), MODE_AND, new ItemStack(sbahjWhip));

		//transportalizers
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.itemFrog, 1, 2), new ItemStack(MinestuckBlocks.transportalizer), MODE_AND, true, false, new ItemStack(rubyRedTransportalizer));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.itemFrog, 1, 5), new ItemStack(MinestuckBlocks.transportalizer), MODE_AND, true, false, new ItemStack(goldenTransportalizer));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.itemFrog, 1, 6), new ItemStack(MinestuckBlocks.transportalizer), MODE_AND, true, false, new ItemStack(paradoxTransportalizer));

		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.itemFrog, 1, 6), new ItemStack(Items.GLOWSTONE_DUST), MODE_OR, true, false, new ItemStack(spaceSalt));
		CombinationRegistry.addCombination(new ItemStack(tickingStopwatch), new ItemStack(MinestuckItems.recordDanceStab), MODE_OR, false, false, new ItemStack(timetable));

		//hammerkind
		CombinationRegistry.addCombination(new ItemStack(Blocks.LOG), new ItemStack(MinestuckItems.sledgeHammer), MODE_OR, new ItemStack(loghammer));
		CombinationRegistry.addCombination(new ItemStack(Blocks.LOG2), new ItemStack(MinestuckItems.sledgeHammer), MODE_OR, new ItemStack(loghammer));
		CombinationRegistry.addCombination(new ItemStack(MinestuckBlocks.glowingLog), new ItemStack(MinestuckItems.sledgeHammer), MODE_OR, new ItemStack(glowingLoghammer));
		CombinationRegistry.addCombination(new ItemStack(MinestuckBlocks.glowingMushroom), new ItemStack(loghammer), MODE_OR, new ItemStack(glowingLoghammer));
		CombinationRegistry.addCombination(new ItemStack(MinestuckBlocks.log, 1, 0), new ItemStack(MinestuckItems.sledgeHammer), MODE_OR, true, false, new ItemStack(overgrownLoghammer));
		CombinationRegistry.addCombination(new ItemStack(MinestuckBlocks.log, 1, 0), new ItemStack(MinestuckItems.sledgeHammer), MODE_OR, true, false, new ItemStack(overgrownLoghammer));
		CombinationRegistry.addCombination(new ItemStack(Blocks.VINE), new ItemStack(loghammer), MODE_AND, false, false, new ItemStack(overgrownLoghammer));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.regiHammer), new ItemStack(MinestuckItems.itemFrog, 1, 5), MODE_OR, false, true, new ItemStack(midasMallet));
		CombinationRegistry.addCombination(new ItemStack(midasMallet), new ItemStack(cueBall), MODE_AND, false, true, new ItemStack(MinestuckItems.mwrthwl));
		CombinationRegistry.addCombination(new ItemStack(battery), new ItemStack(MinestuckItems.clawHammer), MODE_AND, false, false, new ItemStack(aaaNailShocker));
		CombinationRegistry.addCombination(new ItemStack(aaaNailShocker), new ItemStack(midasMallet), MODE_AND, false, false, new ItemStack(highVoltageStormCrusher));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.scarletZillyhoo), new ItemStack(rocketFist), MODE_AND, false, false, new ItemStack(barrelsWarhammer));
		CombinationRegistry.removeCombination(new ItemStack(MinestuckItems.blacksmithHammer), new ItemStack(Items.CLOCK), CombinationRegistry.Mode.MODE_OR, false, false);
		CombinationRegistry.addCombination(new ItemStack(midasMallet), new ItemStack(tickingStopwatch), MODE_OR, new ItemStack(MinestuckItems.fearNoAnvil));
		CombinationRegistry.removeCombination(new ItemStack(MinestuckItems.fearNoAnvil), new ItemStack(Items.LAVA_BUCKET), CombinationRegistry.Mode.MODE_OR, false, false);
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.blacksmithHammer), new ItemStack(Items.LAVA_BUCKET), MODE_AND, new ItemStack(MinestuckItems.meltMasher));

		//bladekind
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.katana), new ItemStack(MinestuckItems.minestuckBucket, 1, 1), MODE_OR, false, true, new ItemStack(bloodKatana));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.unbreakableKatana), new ItemStack(Blocks.BEDROCK), MODE_AND, false, false, new ItemStack(unbreakableKatana));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.katana), new ItemStack(battery), MODE_AND, false, false, new ItemStack(batteryBeamBlade));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.unbreakableKatana), new ItemStack(Blocks.BEDROCK), MODE_AND, false, false, new ItemStack(unbreakableKatana));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.cactusCutlass), new ItemStack(wormholePiercer), MODE_AND, false, false, new ItemStack(quantumEntangloporter));
		CombinationRegistry.addCombination(new ItemStack(Items.DIAMOND_SWORD), new ItemStack(MinestuckItems.itemFrog, 1, 6), MODE_OR, false, true, new ItemStack(crystallineRibbitar));
		CombinationRegistry.addCombination(new ItemStack(cozySweater), new ItemStack(crystallineRibbitar), CombinationRegistry.Mode.MODE_AND, false, false, new ItemStack(valorsEdge));
		CombinationRegistry.addCombination(new ItemStack(batteryBeamBlade), new ItemStack(sun), CombinationRegistry.Mode.MODE_AND, false, false, new ItemStack(lightbringer));
		CombinationRegistry.addCombination(new ItemStack(lightbringer), new ItemStack(lightning), CombinationRegistry.Mode.MODE_OR, false, false, new ItemStack(cybersword));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.claymore), new ItemStack(MinestuckItems.itemFrog, 1, 5), CombinationRegistry.Mode.MODE_OR, false, true, new ItemStack(MinestuckItems.royalDeringer));
		CombinationRegistry.addCombination(PropertyBreakableItem.getBrokenStack(MinestuckItems.royalDeringer), new ItemStack(cueBall), CombinationRegistry.Mode.MODE_AND, true, true, new ItemStack(MinestuckItems.caledfwlch));
		CombinationRegistry.addCombination(PropertyBreakableItem.getBrokenStack(MinestuckItems.caledfwlch), new ItemStack(timetable), CombinationRegistry.Mode.MODE_AND, true, false, new ItemStack(MinestuckItems.caledscratch));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.caledscratch), new ItemStack(Blocks.PACKED_ICE), CombinationRegistry.Mode.MODE_OR, new ItemStack(MinestuckItems.doggMachete));

		CombinationRegistry.removeCombination(new ItemStack(Items.STONE_SWORD), new ItemStack(Items.ROTTEN_FLESH), CombinationRegistry.Mode.MODE_AND, false, true);
		CombinationRegistry.removeCombination(new ItemStack(Items.IRON_SWORD), new ItemStack(Items.ROTTEN_FLESH), CombinationRegistry.Mode.MODE_AND, false, true);
		CombinationRegistry.addCombination(new ItemStack(Items.IRON_SWORD), new ItemStack(throwingStar), MODE_AND, new ItemStack(MinestuckItems.katana));
		CombinationRegistry.removeCombination(new ItemStack(Items.IRON_SWORD), new ItemStack(Items.BLAZE_ROD), CombinationRegistry.Mode.MODE_AND, false, true);
		CombinationRegistry.addCombination(new ItemStack(Items.IRON_SWORD), new ItemStack(Items.BLAZE_ROD), CombinationRegistry.Mode.MODE_AND, false, true, new ItemStack(MinestuckItems.firePoker));
		CombinationRegistry.removeCombination(new ItemStack(MinestuckItems.regisword), new ItemStack(Items.BLAZE_ROD), CombinationRegistry.Mode.MODE_OR, false, true);
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.regisword), new ItemStack(Items.BLAZE_ROD), CombinationRegistry.Mode.MODE_OR, false, true, new ItemStack(MinestuckItems.hotHandle));
		CombinationRegistry.removeCombination(new ItemStack(MinestuckItems.hotHandle), new ItemStack(Blocks.LAPIS_BLOCK), CombinationRegistry.Mode.MODE_OR, false, true);
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.hotHandle), new ItemStack(fluorite), CombinationRegistry.Mode.MODE_AND, false, true, new ItemStack(MinestuckItems.cobaltSabre));
		CombinationRegistry.removeCombination(new ItemStack(MinestuckItems.upStick), new ItemStack(MinestuckItems.energyCore), CombinationRegistry.Mode.MODE_AND, false, false);
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.regisword), new ItemStack(MinestuckItems.energyCore), CombinationRegistry.Mode.MODE_AND, false, false, new ItemStack(MinestuckItems.quantumSabre));
		CombinationRegistry.removeCombination(new ItemStack(Blocks.BEACON), new ItemStack(Items.DIAMOND_SWORD), CombinationRegistry.Mode.MODE_AND, false, false);
		CombinationRegistry.addCombination(new ItemStack(Blocks.BEACON), new ItemStack(crystallineRibbitar), CombinationRegistry.Mode.MODE_AND, false, false, new ItemStack(MinestuckItems.shatterBeacon));
		CombinationRegistry.addCombination(new ItemStack(Blocks.BEACON), new ItemStack(crystallineRibbitar), CombinationRegistry.Mode.MODE_AND, false, false, new ItemStack(MinestuckItems.shatterBeacon));

		//gauntletkind
		CombinationRegistry.addCombination(new ItemStack(Blocks.IRON_BARS), new ItemStack(fancyGlove), MODE_AND, false, false, new ItemStack(spikedGlove));
		CombinationRegistry.addCombination(new ItemStack(Blocks.COBBLESTONE), new ItemStack(fancyGlove), MODE_AND, false, false, new ItemStack(cobbleBasher));
		CombinationRegistry.addCombination(new ItemStack(Items.SLIME_BALL), new ItemStack(spikedGlove), MODE_OR, false, false, new ItemStack(pogoFist));
		CombinationRegistry.addCombination(new ItemStack(fluorite), new ItemStack(goldenGenesisGauntlet), MODE_OR, false, false, new ItemStack(fluoriteGauntlet));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.itemFrog, 1, 5), new ItemStack(cobbleBasher), MODE_OR, true, false, new ItemStack(goldenGenesisGauntlet));
		CombinationRegistry.addCombination(new ItemStack(Items.FIREWORKS), new ItemStack(spikedGlove), MODE_AND, false, false, new ItemStack(rocketFist));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.grimoire), new ItemStack(spikedGlove), MODE_AND, false, false, new ItemStack(eldrichGauntlet));
		CombinationRegistry.addCombination(MinestuckGrist.marble.getCandyItem(), new ItemStack(cobbleBasher), MODE_OR, true, false, new ItemStack(jawbreaker));
		CombinationRegistry.addCombination(new ItemStack(goldenGenesisGauntlet), new ItemStack(midasMallet), MODE_AND, false, false, new ItemStack(midasGlove));
		CombinationRegistry.addCombination(new ItemStack(fancyGlove), new ItemStack(zillyStone), MODE_AND, false, false, new ItemStack(gauntletOfZillywenn));

		//needlekind
		CombinationRegistry.addCombination(new ItemStack(Items.FLINT), new ItemStack(Items.STICK), MODE_AND, false, false, new ItemStack(pointySticks));
		CombinationRegistry.addCombination(new ItemStack(knittingNeedles), new ItemStack(Items.BONE), MODE_OR, false, false, new ItemStack(boneNeedles));
		CombinationRegistry.addCombination(new ItemStack(knittingNeedles), new ItemStack(wizardStatue), MODE_OR, false, false, new ItemStack(needlewands));
		CombinationRegistry.addCombination(new ItemStack(needlewands), new ItemStack(MinestuckItems.grimoire), MODE_AND, false, false, new ItemStack(oglogothThorn));
		CombinationRegistry.addCombination(new ItemStack(laserPointer), new ItemStack(MinestuckItems.minestuckBucket, 1, 3), MODE_OR, false, true, new ItemStack(litGlitterBeamTransistor));
		CombinationRegistry.addCombination(new ItemStack(knittingNeedles), new ItemStack(zillyStone), MODE_AND, false, false, new ItemStack(thistlesOfZillywitch));
		CombinationRegistry.addCombination(new ItemStack(pointySticks), new ItemStack(Blocks.NOTEBLOCK), MODE_OR, false, false, new ItemStack(drumstickNeedles));

		//clawkind
		CombinationRegistry.removeCombination(new ItemStack(Blocks.IRON_BARS), new ItemStack(Items.LEATHER), CombinationRegistry.Mode.MODE_AND, false, true);
		CombinationRegistry.addCombination(new ItemStack(Blocks.IRON_BARS), new ItemStack(fancyGlove), MODE_OR, false, false, new ItemStack(catClaws));
		CombinationRegistry.addCombination(new ItemStack(catClaws), new ItemStack(fluorite), MODE_AND, false, false, new ItemStack(actionClaws));
		CombinationRegistry.addCombination(new ItemStack(yarnBall, 1, 0), new ItemStack(actionClaws), MODE_AND, true, false, new ItemStack(sneakyDaggers));
		CombinationRegistry.addCombination(new ItemStack(Blocks.ICE), new ItemStack(actionClaws), MODE_OR, false, false, new ItemStack(blizzardCutters));
		CombinationRegistry.addCombination(new ItemStack(battery), new ItemStack(blizzardCutters), MODE_AND, false, false, new ItemStack(thunderbirdTalons));
		CombinationRegistry.addCombination(new ItemStack(bladesOfTheWarrior), new ItemStack(archmageHat), MODE_OR, false, false, new ItemStack(archmageDaggers));
		CombinationRegistry.addCombination(new ItemStack(katars), new ItemStack(Items.FIREWORKS), MODE_AND, false, false, new ItemStack(rocketKatars));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.candy, 1, 0), new ItemStack(catClaws), MODE_OR, true, false, new ItemStack(candyCornClaws));
		CombinationRegistry.addCombination(new ItemStack(zillyStone), new ItemStack(catClaws), MODE_OR, false, false, new ItemStack(katarsOfZillywhomst));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.clawSickle), new ItemStack(MinestuckItems.grimoire), MODE_AND, false, false, new ItemStack(MinestuckItems.clawOfNrubyiglith));
		CombinationRegistry.removeCombination(new ItemStack(MinestuckItems.catClaws), new ItemStack(MinestuckItems.grimoire), MODE_AND, false, true);

		//shieldkind
		CombinationRegistry.addCombination(new ItemStack(Items.SHIELD), new ItemStack(Blocks.OAK_DOOR), MODE_OR, false, false, new ItemStack(woodenDoorshield));
		CombinationRegistry.addCombination(new ItemStack(Items.SHIELD), new ItemStack(Blocks.IRON_DOOR), MODE_OR, false, false, new ItemStack(ironDoorshield));
		CombinationRegistry.addCombination(new ItemStack(Items.IRON_INGOT), new ItemStack(woodenDoorshield), MODE_AND, false, false, new ItemStack(ironDoorshield));
		CombinationRegistry.addCombination(new ItemStack(Items.FIREWORKS), new ItemStack(ironDoorshield), MODE_AND, false, false, new ItemStack(rocketRiotShield));
		CombinationRegistry.addCombination(new ItemStack(Items.SHIELD), new ItemStack(Items.IRON_SWORD), MODE_OR, false, false, new ItemStack(bladedShield));
		CombinationRegistry.addCombination(new ItemStack(diamondKatars), new ItemStack(bladedShield), MODE_AND, false, false, new ItemStack(clarityWard));
		CombinationRegistry.addCombination(new ItemStack(battery), new ItemStack(bladedShield), MODE_AND, false, false, new ItemStack(shockerShell));
		CombinationRegistry.addCombination(new ItemStack(Items.SHIELD), new ItemStack(MinestuckBlocks.stoneExplosiveButton), MODE_OR, false, false, new ItemStack(ejectorShield));
		CombinationRegistry.addCombination(new ItemStack(Items.SHIELD), new ItemStack(Items.FIRE_CHARGE), MODE_AND, false, false, new ItemStack(firewall));
		CombinationRegistry.addCombination(new ItemStack(firewall), new ItemStack(Items.WATER_BUCKET), MODE_AND, false, false, new ItemStack(obsidianShield));
		CombinationRegistry.addCombination(new ItemStack(ironDoorshield), new ItemStack(Blocks.OBSIDIAN), MODE_OR, false, false, new ItemStack(obsidianShield));
		CombinationRegistry.addCombination(new ItemStack(Items.SHIELD), new ItemStack(Blocks.GLASS_PANE), MODE_OR, false, false, new ItemStack(clearShield));
		CombinationRegistry.addCombination(new ItemStack(Items.FEATHER), new ItemStack(clearShield), MODE_AND, false, false, new ItemStack(windshield));
		CombinationRegistry.addCombination(new ItemStack(woodenDoorshield), new ItemStack(Blocks.DOUBLE_PLANT, 1, 4), MODE_AND, false, true, new ItemStack(wallOfThorns));
		CombinationRegistry.addCombination(new ItemStack(wallOfThorns), new ItemStack(MinestuckBlocks.uraniumBlock), MODE_OR, false, false, new ItemStack(nuclearNeglector));
		CombinationRegistry.addCombination(new ItemStack(wallOfThorns), new ItemStack(Items.SPECKLED_MELON), MODE_OR, false, false, new ItemStack(hardRindHarvest));
		CombinationRegistry.addCombination(new ItemStack(wallOfThorns), new ItemStack(Items.SPECKLED_MELON), MODE_AND, false, false, new ItemStack(livingShield));
		CombinationRegistry.addCombination(new ItemStack(nuclearNeglector), new ItemStack(moon), MODE_AND, false, false, new ItemStack(perfectAegis));

		//bowkind
		CombinationRegistry.addCombination(new ItemStack(Items.BOW), new ItemStack(MinestuckItems.energyCore), MODE_AND, false, false, new ItemStack(energyBow));
		CombinationRegistry.addCombination(new ItemStack(Items.BOW), new ItemStack(Items.BLAZE_POWDER), MODE_AND, false, false, new ItemStack(infernoShot));
		CombinationRegistry.addCombination(new ItemStack(Items.BOW), new ItemStack(Blocks.ICE), MODE_OR, false, false, new ItemStack(icicleBow));
		CombinationRegistry.addCombination(new ItemStack(Items.BOW), new ItemStack(MinestuckItems.candy), MODE_OR, false, true, new ItemStack(sweetBow));
		CombinationRegistry.addCombination(new ItemStack(Items.BOW), new ItemStack(MinestuckItems.clawSickle), MODE_OR, false, false, new ItemStack(crabbow));
		CombinationRegistry.addCombination(new ItemStack(infernoShot), new ItemStack(Blocks.DISPENSER), MODE_AND, false, false, new ItemStack(mechanicalCrossbow));
		CombinationRegistry.addCombination(new ItemStack(infernoShot), new ItemStack(icicleBow), MODE_OR, false, false, new ItemStack(shiverburnWing));
		CombinationRegistry.addCombination(new ItemStack(infernoShot), new ItemStack(MinestuckItems.itemFrog, 1, 5), MODE_AND, false, true, new ItemStack(kingOfThePond));
		CombinationRegistry.addCombination(new ItemStack(icicleBow), new ItemStack(windshield), MODE_OR, false, false, new ItemStack(tempestBow));
		CombinationRegistry.addCombination(new ItemStack(energyBow), new ItemStack(Blocks.IRON_BLOCK), MODE_AND, false, false, new ItemStack(magneticHookshot));
		CombinationRegistry.addCombination(new ItemStack(energyBow), new ItemStack(teleportMedallion), MODE_AND, false, false, new ItemStack(wormholePiercer));
		CombinationRegistry.addCombination(new ItemStack(energyBow), new ItemStack(Items.ENDER_EYE), MODE_OR, false, false, new ItemStack(wormholePiercer));
		CombinationRegistry.addCombination(new ItemStack(energyBow), new ItemStack(kingOfThePond), MODE_OR, false, false, new ItemStack(gildedGuidance));
		CombinationRegistry.addCombination(new ItemStack(magneticHookshot), new ItemStack(Items.ENDER_EYE), MODE_OR, false, false, new ItemStack(wormholePiercer));
		CombinationRegistry.addCombination(new ItemStack(magneticHookshot), new ItemStack(tempestBow), MODE_AND, false, false, new ItemStack(telegravitationalWarper));
		CombinationRegistry.addCombination(new ItemStack(gildedGuidance), new ItemStack(fluoriteOctet), MODE_AND, false, false, new ItemStack(theChancemaker));
		CombinationRegistry.addCombination(new ItemStack(gildedGuidance), new ItemStack(lightbringer), MODE_AND, false, false, new ItemStack(bowOfLight));
		CombinationRegistry.addCombination(new ItemStack(gildedGuidance), new ItemStack(magneticHookshot), MODE_OR, false, false, new ItemStack(wisdomsPierce));

		//dicekind
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.dice), new ItemStack(fluorite), MODE_AND, new ItemStack(fluoriteOctet));

		//sicklekind
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.regiSickle), new ItemStack(MinestuckItems.itemFrog, 1, 5), MODE_AND, false, true, new ItemStack(hereticusAurum));

		//clubkind
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.metalBat), new ItemStack(Blocks.TNT), MODE_OR, new ItemStack(dynamiteStick));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.nightClub), new ItemStack(MinestuckItems.itemFrog, 1, 2), MODE_AND, false, true, new ItemStack(rubyContrabat));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.spikedClub), new ItemStack(eldrichGauntlet), MODE_AND, false, false, new ItemStack(nightmareMace));
		CombinationRegistry.addCombination(new ItemStack(nightmareMace), new ItemStack(Items.END_CRYSTAL), MODE_AND, false, false, new ItemStack(cranialEnder));
		CombinationRegistry.addCombination(new ItemStack(homeRunBat), new ItemStack(MinestuckItems.boondollars), MODE_AND, false, false, new ItemStack(badaBat));

		//canekind
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.spearCane), new ItemStack(Blocks.SKULL, 1, 5), MODE_AND, false, true, new ItemStack(MinestuckItems.dragonCane));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.paradisesPortabello), new ItemStack(Blocks.DOUBLE_PLANT, 1, 4), MODE_AND, false, true, new ItemStack(staffOfOvergrowth));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.upStick), new ItemStack(MinestuckItems.quantumSabre), MODE_AND, new ItemStack(atomicIrradiator));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.cane), new ItemStack(Items.GOLD_INGOT), MODE_OR, new ItemStack(goldCane));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.cane), new ItemStack(Items.IRON_INGOT), MODE_OR, new ItemStack(MinestuckItems.ironCane));
		CombinationRegistry.addCombination(new ItemStack(goldCane), new ItemStack(cueBall), MODE_AND, new ItemStack(goldenCuestaff));
		CombinationRegistry.addCombination(new ItemStack(goldCane), new ItemStack(zillyStone), MODE_AND, new ItemStack(scepterOfZillywuud));

		//throwkind
		CombinationRegistry.addCombination(new ItemStack(throwingStar), new ItemStack(MinestuckItems.crewPoster), MODE_AND, new ItemStack(suitarang));
		CombinationRegistry.addCombination(new ItemStack(throwingStar), new ItemStack(Items.GOLD_INGOT), MODE_OR, new ItemStack(goldenStar));
		CombinationRegistry.addCombination(new ItemStack(goldenStar), new ItemStack(MinestuckItems.minestuckBucket, 1, 2), MODE_OR, false, true, new ItemStack(psionicStar));
		CombinationRegistry.addCombination(new ItemStack(Items.POTATO), new ItemStack(Items.FIRE_CHARGE), MODE_OR, new ItemStack(hotPotato));
		CombinationRegistry.addCombination(new ItemStack(boomerang), new ItemStack(Items.LAVA_BUCKET), MODE_AND, new ItemStack(redHotRang));
		CombinationRegistry.addCombination(new ItemStack(Items.DRAGON_BREATH), new ItemStack(Items.FIRE_CHARGE), MODE_AND, new ItemStack(dragonCharge));

		//rockkind
		CombinationRegistry.addCombination(new ItemStack(Items.BEETROOT_SEEDS), new ItemStack(Blocks.COBBLESTONE), MODE_AND, new ItemStack(pebble));
		CombinationRegistry.addCombination(new ItemStack(pebble), new ItemStack(Blocks.STONE), MODE_AND, new ItemStack(rock));
		CombinationRegistry.addCombination(new ItemStack(rock), new ItemStack(MinestuckItems.telescopicSassacrusher), MODE_AND, new ItemStack(bigRock));

		//misckind
		CombinationRegistry.addCombination(new ItemStack(battery), new ItemStack(Blocks.REDSTONE_TORCH), MODE_AND, new ItemStack(laserPointer));
		CombinationRegistry.addCombination(new ItemStack(Items.IRON_SHOVEL), new ItemStack(Items.ROTTEN_FLESH), MODE_AND, new ItemStack(gravediggerShovel));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.fearNoAnvil), new ItemStack(rolledUpPaper), MODE_AND, new ItemStack(yesterdaysNews));
		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.spork), new ItemStack(zillyStone), MODE_AND, false, false, new ItemStack(battlesporkOfZillywut));
		CombinationRegistry.addCombination(new ItemStack(Items.IRON_PICKAXE), new ItemStack(zillyStone), MODE_AND, false, false, new ItemStack(battlepickOfZillydew));
		CombinationRegistry.addCombination(new ItemStack(Items.IRON_AXE), new ItemStack(zillyStone), MODE_AND, false, false, new ItemStack(battleaxeOfZillywahoo));

		//ghost items
		CombinationRegistry.addCombination(new ItemStack(endPortal), new ItemStack(MinestuckBlocks.chessboard), MODE_OR, false, false, new ItemStack(MinestuckBlocks.skaiaPortal));
		CombinationRegistry.addCombination(new ItemStack(sun), new ItemStack(MinestuckItems.rawUranium), MODE_OR, false, false, new ItemStack(greenSun));

		//medalions
		CombinationRegistry.addCombination(new ItemStack(ironMedallion), new ItemStack(returnNode), MODE_OR, new ItemStack(returnMedallion));
		CombinationRegistry.addCombination(new ItemStack(returnMedallion), new ItemStack(MinestuckBlocks.transportalizer), MODE_OR, false, false, new ItemStack(teleportMedallion));
		CombinationRegistry.addCombination(new ItemStack(returnMedallion), new ItemStack(MinestuckBlocks.skaiaPortal), MODE_OR, false, false, new ItemStack(skaianMedallion));

		//dyed beam blades
		for (EnumDyeColor dyeColor : EnumDyeColor.values())
		{
			ItemStack dye = new ItemStack(Items.DYE, 1, dyeColor.getDyeDamage());
			ItemStack wool = new ItemStack(Blocks.WOOL, 1, dyeColor.getMetadata());
			GristSet dyeGrist = GristRegistry.getGristConversion(wool).addGrist(MinestuckGrist.chalk, -6);
			if (dyeGrist.getGrist(MinestuckGrist.chalk) == 0) dyeGrist.addGrist(MinestuckGrist.chalk, 0);

			ItemStack beamBlade = new ItemStack(dyedBeamBlade[dyeColor.getMetadata()]);

			GristRegistry.addGristConversion(beamBlade, GristRegistry.getGristConversion(new ItemStack(batteryBeamBlade)).addGrist(dyeGrist));
			CombinationRegistry.addCombination(new ItemStack(batteryBeamBlade), dye, MODE_OR, false, true, beamBlade);
		}

		for (BlockWoolTransportalizer block : sleevedTransportalizers.values())
		{
			ItemStack dye = new ItemStack(Items.DYE, 1, block.color.getDyeDamage());
			ItemStack wool = new ItemStack(Blocks.WOOL, 1, block.color.getMetadata());
			ItemStack stack = new ItemStack(block);
			GristRegistry.addGristConversion(stack, GristRegistry.getGristConversion(wool).addGrist(GristRegistry.getGristConversion(new ItemStack(MinestuckBlocks.transportalizer))));
			if (!block.equals(whiteWoolTransportalizer))
				CombinationRegistry.addCombination(new ItemStack(whiteWoolTransportalizer), dye, MODE_OR, stack);
			CombinationRegistry.addCombination(new ItemStack(MinestuckBlocks.transportalizer), wool, MODE_AND, stack);
		}

		CombinationRegistry.addCombination(new ItemStack(MinestuckItems.tomeOfTheAncients, 1, 2), new ItemStack(Items.ENDER_EYE),
				CombinationRegistry.Mode.MODE_OR, true, false, new ItemStack(MinestuckItems.denizenEye));
		CombinationRegistry.addCombination("chiseled_hero_stone", new ItemStack(MinestuckItems.knittingNeedles), false, CombinationRegistry.Mode.MODE_OR, new ItemStack(MinestuckItems.sashKit));
		CombinationRegistry.addCombination("chiseled_hero_stone", new ItemStack(MinestuckItems.ironMedallion), false, CombinationRegistry.Mode.MODE_AND, new ItemStack(MinestuckItems.skillReseter));

		for (Block block : gristBlocks.values())
		{
			GristRegistry.addGristConversion(new ItemStack(block), new GristSet(new Grist[]{((BlockGrist) block).type}, new int[]{((BlockGrist) block).value}));
			CombinationRegistry.addCombination(new ItemStack(block), new ItemStack(Items.SUGAR), MODE_OR, ((BlockGrist) block).type.getCandyItem());
			CombinationRegistry.addCombination(((BlockGrist) block).type.getCandyItem(), new ItemStack(Blocks.STONE), MODE_AND, new ItemStack(block));
		}
	}

	public static void registerModRecipes()
	{

		GristRegistry.addGristConversion("ingotCopper", new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.cobalt}, new int[]{16, 3}));
		GristRegistry.addGristConversion("ingotTin", new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.caulk}, new int[]{12, 8}));
		GristRegistry.addGristConversion("ingotSilver", new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.mercury}, new int[]{12, 8}));
		GristRegistry.addGristConversion("ingotLead", new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.cobalt, MinestuckGrist.shale}, new int[]{12, 4, 4}));
		GristRegistry.addGristConversion("ingotNickel", new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.sulfur}, new int[]{12, 8}));
		GristRegistry.addGristConversion("ingotInvar", new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.sulfur}, new int[]{12, 5}));
		GristRegistry.addGristConversion("ingotAluminium", new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.chalk}, new int[]{12, 6}));

		GristRegistry.addGristConversion("ingotCobalt", new GristSet(new Grist[]{MinestuckGrist.cobalt}, new int[]{18}));
		GristRegistry.addGristConversion("ingotArdite", new GristSet(new Grist[]{MinestuckGrist.garnet, MinestuckGrist.sulfur}, new int[]{12, 8}));
		GristRegistry.addGristConversion("ingotRedAlloy", new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.garnet}, new int[]{18, 32}));

		if (oreMultiplier != 0)
		{
			GristRegistry.addGristConversion("oreCopper", new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.cobalt, MinestuckGrist.build}, new int[]{16 * oreMultiplier, 3 * oreMultiplier, 4}));
			GristRegistry.addGristConversion("oreTin", new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.caulk, MinestuckGrist.build}, new int[]{12 * oreMultiplier, 8 * oreMultiplier, 4}));
			GristRegistry.addGristConversion("oreSilver", new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.mercury, MinestuckGrist.build}, new int[]{12 * oreMultiplier, 8 * oreMultiplier, 4}));
			GristRegistry.addGristConversion("oreLead", new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.cobalt, MinestuckGrist.shale, MinestuckGrist.build}, new int[]{12 * oreMultiplier, 4 * oreMultiplier, 4 * oreMultiplier, 4}));
			GristRegistry.addGristConversion("oreNickel", new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.sulfur, MinestuckGrist.build}, new int[]{12 * oreMultiplier, 8 * oreMultiplier, 4}));
			GristRegistry.addGristConversion("oreAluminium", new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.chalk, MinestuckGrist.build}, new int[]{12 * oreMultiplier, 6 * oreMultiplier, 4}));

			GristRegistry.addGristConversion("oreCobalt", new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.build}, new int[]{18 * oreMultiplier, 4}));
			GristRegistry.addGristConversion("oreArdite", new GristSet(new Grist[]{MinestuckGrist.garnet, MinestuckGrist.sulfur, MinestuckGrist.build}, new int[]{12 * oreMultiplier, 8 * oreMultiplier, 4}));
		}

		if (!OreDictionary.getOres("ingotRedAlloy").isEmpty())
			CombinationRegistry.addCombination(new ItemStack(Items.IRON_INGOT), new ItemStack(Items.REDSTONE), MODE_OR, OreDictionary.getOres("ingotRedAlloy").get(0));

		if (oreMultiplier != 0)
		{
			GristRegistry.addGristConversion("oreCopper", new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.cobalt, MinestuckGrist.build}, new int[]{16 * oreMultiplier, 3 * oreMultiplier, 4}));
			GristRegistry.addGristConversion("oreTin", new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.caulk, MinestuckGrist.build}, new int[]{12 * oreMultiplier, 8 * oreMultiplier, 4}));
			GristRegistry.addGristConversion("oreSilver", new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.mercury, MinestuckGrist.build}, new int[]{12 * oreMultiplier, 8 * oreMultiplier, 4}));
			GristRegistry.addGristConversion("oreLead", new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.cobalt, MinestuckGrist.shale, MinestuckGrist.build}, new int[]{12 * oreMultiplier, 4 * oreMultiplier, 4 * oreMultiplier, 4}));
			GristRegistry.addGristConversion("oreNickel", new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.sulfur, MinestuckGrist.build}, new int[]{12 * oreMultiplier, 8 * oreMultiplier, 4}));
			GristRegistry.addGristConversion("oreAluminium", new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.chalk, MinestuckGrist.build}, new int[]{12 * oreMultiplier, 6 * oreMultiplier, 4}));

			GristRegistry.addGristConversion("oreCobalt", new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.build}, new int[]{18 * oreMultiplier, 4}));
			GristRegistry.addGristConversion("oreArdite", new GristSet(new Grist[]{MinestuckGrist.garnet, MinestuckGrist.sulfur, MinestuckGrist.build}, new int[]{12 * oreMultiplier, 8 * oreMultiplier, 4}));
		}

		try
		{
			if (Loader.isModLoaded("IronChest"))
			{
				Block ironChest = ((Block) (Class.forName("cpw.mods.ironchest.IronChest").getField("ironChestBlock").get(null)));
				GristRegistry.addGristConversion(ironChest, 0, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.rust}, new int[]{16, 128}));
				CombinationRegistry.addCombination(new ItemStack(Blocks.CHEST), new ItemStack(Items.IRON_INGOT), MODE_AND, new ItemStack(ironChest, 1, 0));
			}
		}
		catch (Exception e)
		{
			Debug.logger.warn("Exception while getting things for mod \"IronChest\".", e);
		}


		registerRecipes(new ActuallyAdditionsSupport(), "actuallyadditions", false);
		registerRecipes(new Minegicka3Support(), "minegicka3", false);
		registerRecipes(new NeverSayNetherSupport(), "nsn", false);
		registerRecipes(new ExtraUtilitiesSupport(), "extrautils2", false);
		registerRecipes(new TinkersConstructSupport(), "tconstruct", false);
		// TODO: Use these, these look cool

		if (Minestuck.isChiselLoaded) registerChiselRecipes();
		if (Minestuck.isMysticalWorldLoaded) registerMysticalWorldRecipes();
		if (Minestuck.isBOPLoaded) registerBOPRecipes();
		if (Minestuck.isThaumLoaded) registerThaumcraft();
		if (Minestuck.isBotaniaLoaded) registerBotania();
		if (Minestuck.isSplatcraftLodaded) registerSplatcraft();
		if (Minestuck.isFutureMcLoaded) registerFutureMcRecipes();
		if (Minestuck.isMekanismLoaded)
		{
			GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism", "ingot")), 1, 1), true, new GristSet(new Grist[]{MinestuckGrist.mercury, MinestuckGrist.rust}, new int[]{8, 12}));
		}
		if (Minestuck.isCyclicLoaded)
		{
			GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("cyclicmagic", "crystalized_amber"))), false, new GristSet(new Grist[]{MinestuckGrist.amber}, new int[]{20}));
			GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("cyclicmagic", "crystalized_obsidian"))), false, new GristSet(new Grist[]{MinestuckGrist.tar, MinestuckGrist.cobalt, MinestuckGrist.amber, MinestuckGrist.diamond}, new int[]{40, 20, 40, 8}));
		}
		if (Minestuck.isVampirismLoaded)
		{
			GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("vampirism", "blood_bottle"))), false, new GristSet(new Grist[]{MinestuckGrist.garnet, MinestuckGrist.iodine}, new int[]{16, 16}));
			GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("vampirism", "vampire_blood_bottle"))), false, new GristSet(new Grist[]{MinestuckGrist.garnet, MinestuckGrist.iodine, MinestuckGrist.ruby}, new int[]{20, 8, 4}));
			GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("vampirism", "vampirism_flower"))), false, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.shale, MinestuckGrist.garnet}, new int[]{2, 4, 2}));
		}
		if (Minestuck.isIndustrialForegoingLoaded)
		{
			GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("industrialforegoing", "plastic"))), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.quartz}, new int[]{500, 1}));
		}
	}

	private static final void registerMysticalWorldRecipes()
	{
		String modid = "mysticalworld";
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "amethyst_gem"))), false, new GristSet(new Grist[]{MinestuckGrist.amethyst}, new int[]{18}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "mud_block"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "carapace"))), false, new GristSet(new Grist[]{MinestuckGrist.shale, MinestuckGrist.iodine}, new int[]{8, 5}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "venison"))), false, new GristSet(new Grist[]{MinestuckGrist.iodine}, new int[]{12}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "cooked_venison"))), false, new GristSet(new Grist[]{MinestuckGrist.tar, MinestuckGrist.iodine}, new int[]{1, 12}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "antlers"))), false, new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.build}, new int[]{12, 2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "pelt"))), false, new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.iodine}, new int[]{3, 3}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "raw_squid"))), false, new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.iodine}, new int[]{1, 8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "cooked_squid"))), false, new GristSet(new Grist[]{MinestuckGrist.tar, MinestuckGrist.iodine}, new int[]{1, 8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "aubergine"))), false, new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.amber}, new int[]{2, 2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "cooked_aubergine"))), false, new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.amber, MinestuckGrist.tar}, new int[]{2, 2, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "silk_cocoon"))), false, new GristSet(new Grist[]{MinestuckGrist.chalk}, new int[]{8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "silk_thread"))), false, new GristSet(new Grist[]{MinestuckGrist.chalk}, new int[]{1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "gall_apple"))), false, new GristSet(new Grist[]{MinestuckGrist.shale, MinestuckGrist.iodine}, new int[]{2, 2}));
	}

	private static final void registerChiselRecipes()
	{
		String modid = "chisel";
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "planks-oak"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "planks-birch"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "planks-spruce"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "planks-jungle"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "planks-acacia"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "planks-dark-oak"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{2}));

		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "marble"))), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.marble}, new int[]{1, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "marble1"))), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.marble}, new int[]{1, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "marble2"))), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.marble}, new int[]{1, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "marblepillar"))), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.marble}, new int[]{1, 1}));

		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "glass"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "stonebrick"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "cobblestone"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "cobblestone1"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid, "cobblestone2"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{2}));
	}

	private static final void registerBOPRecipes()
	{
		//dirt
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "grass")), 1, 0), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.caulk}, new int[]{4, 3}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "grass")), 1, 1), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "grass")), 1, 2), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "grass")), 1, 3), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "grass")), 1, 4), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "grass")), 1, 5), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "grass")), 1, 6), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.tar}, new int[]{2, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "grass")), 1, 7), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "grass")), 1, 8), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.tar, MinestuckGrist.iodine, MinestuckGrist.ruby}, new int[]{2, 1, 2, 2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "dirt"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "mudball"))), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.cobalt}, new int[]{1, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "mud_brick"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{1}));

		//sand
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "white_sand"))), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.chalk}, new int[]{1, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "dried_sand"))), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{1}));

		//Flowers
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 0), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.chalk, MinestuckGrist.tar}, new int[]{1, 6, 2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 1), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.amber, MinestuckGrist.amethyst}, new int[]{2, 4, 4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 2), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.uranium, MinestuckGrist.tar}, new int[]{2, 2, 8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 3), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.amber, MinestuckGrist.amethyst, MinestuckGrist.tar}, new int[]{1, 4, 4, 2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 4), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.chalk, MinestuckGrist.amethyst}, new int[]{1, 4, 4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 5), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.garnet, MinestuckGrist.amber}, new int[]{1, 4, 4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 6), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.chalk, MinestuckGrist.garnet}, new int[]{1, 4, 4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 7), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.amethyst, MinestuckGrist.garnet}, new int[]{1, 2, 6}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 8), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.amethyst, MinestuckGrist.garnet}, new int[]{1, 4, 4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 9), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.chalk}, new int[]{1, 8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 10), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.caulk, MinestuckGrist.tar}, new int[]{1, 2, 8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 11), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.ruby}, new int[]{1, 8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 12), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.chalk, MinestuckGrist.tar}, new int[]{1, 2, 6}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 13), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.chalk, MinestuckGrist.garnet}, new int[]{1, 4, 4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 14), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.chalk}, new int[]{1, 8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_0")), 1, 15), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.amber, MinestuckGrist.garnet, MinestuckGrist.sulfur}, new int[]{1, 4, 4, 3}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_1")), 1, 0), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.amethyst, MinestuckGrist.garnet}, new int[]{1, 4, 4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_1")), 1, 1), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.amber}, new int[]{1, 8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_1")), 1, 2), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.amethyst}, new int[]{1, 8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_1")), 1, 3), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.chalk, MinestuckGrist.garnet}, new int[]{2, 4, 4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_1")), 1, 4), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.chalk, MinestuckGrist.cobalt}, new int[]{2, 4, 4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_1")), 1, 5), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.garnet}, new int[]{2, 8}));

		//Plant Things
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "log_0"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "log_1"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "log_2"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "log_3"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "log_4"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{8}));

		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "leaves_0"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "leaves_1"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "leaves_2"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "leaves_3"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "leaves_4"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "leaves_5"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "leaves_6"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{1}));

		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "sapling_0"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{16}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "sapling_1"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{16}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "sapling_2"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{16}));

		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "plant_0"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{1}));

		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "plant_1")), 1, 0), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "plant_1")), 1, 1), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.caulk}, new int[]{1, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "plant_1")), 1, 2), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "plant_1")), 1, 3), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.iodine}, new int[]{1, 2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "plant_1")), 1, 4), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.chalk}, new int[]{1, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "plant_1")), 1, 4), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.chalk}, new int[]{1, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "plant_1")), 1, 5), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "plant_1")), 1, 6), new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.iodine}, new int[]{3, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "plant_1")), 1, 7), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "plant_1")), 1, 8), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "plant_1")), 1, 9), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "plant_1")), 1, 10), new GristSet(new Grist[]{MinestuckGrist.garnet, MinestuckGrist.shale}, new int[]{4, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "plant_1")), 1, 11), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "bamboo"))), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{2}));

		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "double_plant")), 1, 0), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.amethyst, MinestuckGrist.chalk}, new int[]{1, 4, 4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "double_plant")), 1, 1), true, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.chalk}, new int[]{2, 2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "double_plant")), 1, 2), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.sulfur}, new int[]{1, 3}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "double_plant")), 1, 3), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.build}, new int[]{1, 3}));

		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "waterlily"))), false, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.iodine}, new int[]{4, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "coral"))), false, new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.amber, MinestuckGrist.iodine}, new int[]{1, 4, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "seaweed"))), false, new GristSet(new Grist[]{MinestuckGrist.cobalt, MinestuckGrist.amber, MinestuckGrist.iodine}, new int[]{1, 4, 1}));

		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "willow_vine"))), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.amber}, new int[]{2, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "ivy"))), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.amber}, new int[]{2, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "bramble_plant"))), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{6}));

		//Mushrooms
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "mushroom")), 1, 0), true, new GristSet(new Grist[]{MinestuckGrist.iodine}, new int[]{5}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "mushroom")), 1, 1), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.chalk}, new int[]{2, 3}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "mushroom")), 1, 2), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.cobalt}, new int[]{2, 3}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "mushroom")), 1, 3), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.uranium}, new int[]{2, 3}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "mushroom")), 1, 4), true, new GristSet(new Grist[]{MinestuckGrist.iodine}, new int[]{5}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "mushroom")), 1, 5), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.shale}, new int[]{3, 2}));

		//Fruit
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "berry")), 1), false, new GristSet(new Grist[]{MinestuckGrist.amber}, new int[]{1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "pear")), 1), false, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.shale}, new int[]{3, 2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "persimmon")), 1), false, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.iodine}, new int[]{3, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "peach")), 1), false, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.caulk}, new int[]{3, 1}));

		//Misc.
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "hive")), 1, 0), true, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "hive")), 1, 2), true, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "honeycomb")), 1), false, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "filled_honeycomb")), 1), false, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.build}, new int[]{1, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "record_wanderer")), 1), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.caulk, MinestuckGrist.quartz, MinestuckGrist.mercury}, new int[]{15, 8, 5, 5}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "jar_filled")), 1, 0), true, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.amber}, new int[]{2, 8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "jar_filled")), 1, 1), true, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.sulfur, MinestuckGrist.cobalt}, new int[]{2, 4, 4}));

		//Dyes
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "blue_dye")), 1), false, new GristSet(new Grist[]{MinestuckGrist.amethyst}, new int[]{4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "black_dye")), 1), false, new GristSet(new Grist[]{MinestuckGrist.tar}, new int[]{4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "brown_dye")), 1), false, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.iodine}, new int[]{1, 3}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "green_dye")), 1), false, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.iodine}, new int[]{3, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "white_dye")), 1), false, new GristSet(new Grist[]{MinestuckGrist.chalk}, new int[]{2}));

		//cold blocks
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "hard_ice"))), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.cobalt}, new int[]{10, 6}));

		//warm blocks
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "ash"))), new GristSet(new Grist[]{MinestuckGrist.tar}, new int[]{1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "fleshchunk"))), new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.rust}, new int[]{1, 1}));

		//Gemstones
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "crystal_shard"))), new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.tar}, new int[]{6, 4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "gem")), 1, 0), true, new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.uranium}, new int[]{12, 6}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "gem")), 1, 1), true, new GristSet(new Grist[]{MinestuckGrist.ruby}, new int[]{12}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "gem")), 1, 2), true, new GristSet(new Grist[]{MinestuckGrist.mercury, MinestuckGrist.rust}, new int[]{6, 6}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "gem")), 1, 3), true, new GristSet(new Grist[]{MinestuckGrist.diamond, MinestuckGrist.amber}, new int[]{6, 6}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "gem")), 1, 4), true, new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.amethyst}, new int[]{3, 9}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "gem")), 1, 5), true, new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.quartz}, new int[]{6, 6}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "gem")), 1, 6), true, new GristSet(new Grist[]{MinestuckGrist.cobalt}, new int[]{12}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "gem")), 1, 7), true, new GristSet(new Grist[]{MinestuckGrist.amber}, new int[]{12}));

		//Alchemy Recipes
		CombinationRegistry.addCombination(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "brown_dye"))), new ItemStack(Items.WHEAT_SEEDS), MODE_OR, false, false, new ItemStack(Items.DYE, 1, 3));
		CombinationRegistry.addCombination(new ItemStack(Items.QUARTZ), new ItemStack(Items.DYE, 1, 4), MODE_OR, false, true, new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "gem")), 1, 5));
		CombinationRegistry.addCombination(new ItemStack(Blocks.ICE), new ItemStack(Blocks.RED_FLOWER, 1, 1), MODE_AND, false, true, new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_1")), 1, 4));
		CombinationRegistry.addCombination(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "sapling_1")), 1, 0), new ItemStack(Blocks.RED_FLOWER, 1, 0), MODE_OR, true, true, new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("biomesoplenty", "flower_1")), 1, 5));
	}

	private static final void registerFutureMcRecipes()
	{
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("futuremc", "stripped_acacia_log"))), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("futuremc", "stripped_jungle_log"))), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("futuremc", "stripped_oak_log"))), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("futuremc", "stripped_birch_log"))), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("futuremc", "stripped_spruce_log"))), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("futuremc", "stripped_dark_oak_log"))), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("futuremc", "soul_soil"))), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.caulk, MinestuckGrist.sulfur}, new int[]{4, 3, 2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("futuremc", "smooth_stone"))), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{2}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("futuremc", "smooth_sandstone"))), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("futuremc", "smooth_red_sandstone"))), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{4}));

		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("futuremc", "honey_block"))), new GristSet(new Grist[]{MinestuckGrist.amber}, new int[]{32}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("futuremc", "honeycomb"))), new GristSet(new Grist[]{MinestuckGrist.amber}, new int[]{4}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("futuremc", "bell"))), new GristSet(new Grist[]{MinestuckGrist.gold}, new int[]{500}));

		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("futuremc", "lily_of_the_valley"))), new GristSet(new Grist[]{MinestuckGrist.caulk, MinestuckGrist.iodine}, new int[]{3, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("futuremc", "cornflower"))), new GristSet(new Grist[]{MinestuckGrist.amethyst, MinestuckGrist.iodine}, new int[]{3, 1}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("futuremc", "wither_rose"))), new GristSet(new Grist[]{MinestuckGrist.tar}, new int[]{8}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("futuremc", "bamboo"))), new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{1}));

		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("futuremc", "ancient_debris"))), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.tar, MinestuckGrist.diamond}, new int[]{8, 18, 18}));
		GristRegistry.addGristConversion(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("futuremc", "netherite_scrap"))), new GristSet(new Grist[]{MinestuckGrist.tar, MinestuckGrist.diamond}, new int[]{18, 18}));
	}

	public static void registerThaumcraft()
	{
		//Grist Conversions
		GristRegistry.addGristConversion(new ItemStack(ItemsTC.amber), new GristSet(MinestuckGrist.amber, 8));
		GristRegistry.addGristConversion(new ItemStack(ItemsTC.quicksilver), new GristSet(MinestuckGrist.mercury, 9));
		GristRegistry.addGristConversion(new ItemStack(ItemsTC.thaumonomicon), new GristSet(new Grist[]{MinestuckGrist.vis, MinestuckGrist.iodine, MinestuckGrist.chalk}, new int[]{24, 5, 2}));
		GristRegistry.addGristConversion(new ItemStack(ItemsTC.salisMundus), new GristSet(MinestuckGrist.vis, 12));
		GristRegistry.addGristConversion(new ItemStack(ItemsTC.crystalEssence), new GristSet(new Grist[]{MinestuckGrist.vis, MinestuckGrist.quartz}, new int[]{2, 1}));
		GristRegistry.addGristConversion(new ItemStack(ItemsTC.phial, 1, 1), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.shale, MinestuckGrist.vis}, new int[]{1, 1, 20}));
		GristRegistry.addGristConversion(new ItemStack(ItemsTC.primordialPearl), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.vis, MinestuckGrist.zillium}, new int[]{1600, 240, 5}));

		GristRegistry.addGristConversion(new ItemStack(ItemsTC.nuggets, 1, 0), new GristSet(new Grist[]{MinestuckGrist.rust}, new int[]{1}));
		GristRegistry.addGristConversion(new ItemStack(ItemsTC.nuggets, 1, 10), new GristSet(new Grist[]{MinestuckGrist.vis, MinestuckGrist.rust, MinestuckGrist.gold}, new int[]{2, 2, 1}));

		GristRegistry.addGristConversion(new ItemStack(ItemsTC.ingots, 1, 0), new GristSet(new Grist[]{MinestuckGrist.vis, MinestuckGrist.rust}, new int[]{18, 9}));
		GristRegistry.addGristConversion(new ItemStack(ItemsTC.ingots, 1, 2), new GristSet(new Grist[]{MinestuckGrist.vis, MinestuckGrist.gold, MinestuckGrist.rust}, new int[]{5, 4, 9}));

		GristRegistry.addGristConversion(new ItemStack(ItemsTC.clusters, 1, 0), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.rust}, new int[]{4, 16 * MinestuckConfig.oreMultiplier}));
		GristRegistry.addGristConversion(new ItemStack(ItemsTC.clusters, 1, 1), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.gold}, new int[]{4, 16 * MinestuckConfig.oreMultiplier}));
		GristRegistry.addGristConversion(new ItemStack(ItemsTC.clusters, 1, 6), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.mercury}, new int[]{4, 16 * MinestuckConfig.oreMultiplier}));
		GristRegistry.addGristConversion(new ItemStack(ItemsTC.clusters, 1, 7), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.quartz, MinestuckGrist.marble}, new int[]{2, 16 * MinestuckConfig.oreMultiplier, 2 * MinestuckConfig.oreMultiplier}));

		GristRegistry.addGristConversion(new ItemStack(ItemsTC.voidSeed), new GristSet(new Grist[]{MinestuckGrist.vis, MinestuckGrist.shale, MinestuckGrist.tar, MinestuckGrist.uranium}, new int[]{45, 21, 32, 4}));
		GristRegistry.addGristConversion(new ItemStack(ItemsTC.brain), new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.vis, MinestuckGrist.shale}, new int[]{1, 5, 8}));
		GristRegistry.addGristConversion(new ItemStack(ItemsTC.tallow), new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.vis, MinestuckGrist.rust}, new int[]{1, 2, 1}));
		GristRegistry.addGristConversion(new ItemStack(ItemsTC.fabric), new GristSet(new Grist[]{MinestuckGrist.chalk, MinestuckGrist.vis}, new int[]{14, 1}));

		GristRegistry.addGristConversion(new ItemStack(ItemsTC.chunks), new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.tar}, new int[]{2, 1}));

		GristRegistry.addGristConversion(new ItemStack(BlocksTC.oreAmber), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.amber}, new int[]{4, 8 * MinestuckConfig.oreMultiplier}));
		GristRegistry.addGristConversion(new ItemStack(BlocksTC.oreCinnabar), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.mercury}, new int[]{4, 9 * MinestuckConfig.oreMultiplier}));

		GristRegistry.addGristConversion(new ItemStack(BlocksTC.vishroom), new GristSet(new Grist[]{MinestuckGrist.vis, MinestuckGrist.iodine}, new int[]{5, 4}));
		GristRegistry.addGristConversion(new ItemStack(BlocksTC.cinderpearl), new GristSet(new Grist[]{MinestuckGrist.tar, MinestuckGrist.uranium, MinestuckGrist.iodine}, new int[]{8, 2, 2}));
		GristRegistry.addGristConversion(new ItemStack(BlocksTC.shimmerleaf), new GristSet(new Grist[]{MinestuckGrist.mercury, MinestuckGrist.iodine}, new int[]{8, 2}));

		GristRegistry.addGristConversion(new ItemStack(BlocksTC.crystalAir), new GristSet(new Grist[]{MinestuckGrist.vis, MinestuckGrist.build, MinestuckGrist.marble}, new int[]{20, 8, 2}));
		GristRegistry.addGristConversion(new ItemStack(BlocksTC.crystalFire), new GristSet(new Grist[]{MinestuckGrist.vis, MinestuckGrist.build, MinestuckGrist.amber}, new int[]{20, 8, 2}));
		GristRegistry.addGristConversion(new ItemStack(BlocksTC.crystalWater), new GristSet(new Grist[]{MinestuckGrist.vis, MinestuckGrist.build, MinestuckGrist.tar}, new int[]{20, 8, 2}));
		GristRegistry.addGristConversion(new ItemStack(BlocksTC.crystalEarth), new GristSet(new Grist[]{MinestuckGrist.vis, MinestuckGrist.build, MinestuckGrist.uranium}, new int[]{20, 8, 2}));
		GristRegistry.addGristConversion(new ItemStack(BlocksTC.crystalOrder), new GristSet(new Grist[]{MinestuckGrist.vis, MinestuckGrist.build, MinestuckGrist.quartz}, new int[]{8, 8, 14}));
		GristRegistry.addGristConversion(new ItemStack(BlocksTC.crystalEntropy), new GristSet(new Grist[]{MinestuckGrist.vis, MinestuckGrist.build, MinestuckGrist.quartz}, new int[]{13, 8, 9}));
		GristRegistry.addGristConversion(new ItemStack(BlocksTC.crystalTaint), new GristSet(new Grist[]{MinestuckGrist.vis, MinestuckGrist.build}, new int[]{28, 2}));

		GristRegistry.addGristConversion(new ItemStack(BlocksTC.saplingSilverwood), new GristSet(new Grist[]{MinestuckGrist.vis, MinestuckGrist.build, MinestuckGrist.mercury}, new int[]{16, 16, 8}));
		GristRegistry.addGristConversion(new ItemStack(BlocksTC.saplingGreatwood), new GristSet(new Grist[]{MinestuckGrist.vis, MinestuckGrist.build, MinestuckGrist.iodine}, new int[]{8, 16, 16}));

		GristRegistry.addGristConversion(new ItemStack(BlocksTC.logSilverwood), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.vis, MinestuckGrist.mercury}, new int[]{8, 4, 4}));
		GristRegistry.addGristConversion(new ItemStack(BlocksTC.logGreatwood), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.vis}, new int[]{12, 4}));
		GristRegistry.addGristConversion(new ItemStack(BlocksTC.leafSilverwood), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.mercury}, new int[]{1, 1}));
		GristRegistry.addGristConversion(new ItemStack(BlocksTC.leafGreatwood), new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.vis}, new int[]{1, 1}));

		//Alchemy
		CombinationRegistry.addCombination(new ItemStack(ItemsTC.crystalEssence), new ItemStack(Items.SUGAR), MODE_OR, MinestuckGrist.vis.getCandyItem());

		CombinationRegistry.addCombination(new ItemStack(Items.WHEAT_SEEDS), new ItemStack(ItemsTC.quicksilver), MODE_AND, new ItemStack(BlocksTC.shimmerleaf));
		CombinationRegistry.addCombination(new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.BLAZE_POWDER), MODE_AND, new ItemStack(BlocksTC.cinderpearl));
		CombinationRegistry.addCombination(new ItemStack(Blocks.RED_MUSHROOM), new ItemStack(ItemsTC.crystalEssence), MODE_AND, new ItemStack(BlocksTC.vishroom));
		CombinationRegistry.addCombination(new ItemStack(Blocks.SAPLING, 1, 0), new ItemStack(Blocks.SAPLING, 1, 5), MODE_OR, true, true, new ItemStack(BlocksTC.saplingGreatwood));
		CombinationRegistry.addCombination(new ItemStack(Blocks.SAPLING, 1, 0), new ItemStack(ItemsTC.quicksilver), MODE_OR, true, false, new ItemStack(BlocksTC.saplingSilverwood));
		CombinationRegistry.addCombination(new ItemStack(BlocksTC.leafGreatwood, 1, 0), new ItemStack(BlocksTC.logGreatwood, 1, 5), MODE_AND, new ItemStack(BlocksTC.saplingGreatwood));
		CombinationRegistry.addCombination(new ItemStack(BlocksTC.leafSilverwood, 1, 0), new ItemStack(BlocksTC.leafSilverwood, 1, 5), MODE_AND, new ItemStack(BlocksTC.saplingSilverwood));

		//Crucible Recipes
		ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("Minestuck", "magicBlock"), new CrucibleRecipe("UNLOCKALCHEMY@1", new ItemStack(magicBlock), new ItemStack(MinestuckBlocks.genericObject), (new AspectList()).merge(Aspect.MAGIC, 10).merge(Aspect.ALCHEMY, 10)));
	}

	public static void registerBotania()
	{
		//Grist Conversions
		GristRegistry.addGristConversion(ModBlocks.livingwood, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{4}));
		GristRegistry.addGristConversion(ModBlocks.livingrock, new GristSet(new Grist[]{MinestuckGrist.build}, new int[]{4}));

		GristRegistry.addGristConversion(new ItemStack(ModItems.manaResource, 1, 0), true, GristRegistry.getGristConversion(new ItemStack(Items.IRON_INGOT)).addGrist(MinestuckGrist.mana, 3));
		GristRegistry.addGristConversion(new ItemStack(ModItems.manaResource, 1, 1), true, GristRegistry.getGristConversion(new ItemStack(Items.ENDER_PEARL)).addGrist(MinestuckGrist.mana, 6));
		GristRegistry.addGristConversion(new ItemStack(ModItems.manaResource, 1, 2), true, GristRegistry.getGristConversion(new ItemStack(Items.DIAMOND)).addGrist(MinestuckGrist.mana, 10));
		GristRegistry.addGristConversion(new ItemStack(ModItems.manaResource, 1, 16), true, GristRegistry.getGristConversion(new ItemStack(Items.STRING)).addGrist(MinestuckGrist.mana, 5));
		GristRegistry.addGristConversion(new ItemStack(ModItems.manaBottle, 1, 16), true, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.mana}, new int[]{1, 5}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.manaCookie), false, new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.iodine, MinestuckGrist.mana}, new int[]{1, 1, 20}));
		GristRegistry.addGristConversion(new ItemStack(ModBlocks.pistonRelay), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.garnet, MinestuckGrist.rust, MinestuckGrist.mana}, new int[]{14, 4, 9, 15}));
		GristRegistry.addGristConversion(new ItemStack(ModBlocks.tinyPotato), false, GristRegistry.getGristConversion(new ItemStack(Items.POTATO)).addGrist(MinestuckGrist.mana, 1));
		GristRegistry.addGristConversion(new ItemStack(ModBlocks.manaGlass), false, GristRegistry.getGristConversion(new ItemStack(Blocks.GLASS)).addGrist(MinestuckGrist.mana, 1));

		GristRegistry.addGristConversion(new ItemStack(ModItems.manaResource, 1, 23), true, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.chalk}, new int[]{5, 5}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.quartz, 1, 1), true, new GristSet(new Grist[]{MinestuckGrist.quartz, MinestuckGrist.marble, MinestuckGrist.mana}, new int[]{4, 1, 1}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.grassSeeds, 1, 0), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.amber}, new int[]{1, 1}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.grassSeeds, 1, 1), true, new GristSet(new Grist[]{MinestuckGrist.sulfur, MinestuckGrist.amber}, new int[]{1, 1}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.grassSeeds, 1, 2), true, new GristSet(new Grist[]{MinestuckGrist.iodine, MinestuckGrist.ruby}, new int[]{1, 1}));

		GristRegistry.addGristConversion(new ItemStack(ModItems.manaResource, 1, 4), true, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.mana, MinestuckGrist.uranium, MinestuckGrist.rust, MinestuckGrist.diamond}, new int[]{210, 500, 22, 18, 24}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.manaResource, 1, 5), true, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.uranium, MinestuckGrist.artifact}, new int[]{440, 84, 10}));


		GristRegistry.addGristConversion(new ItemStack(ModItems.overgrowthSeed), false, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.iodine, MinestuckGrist.amber, MinestuckGrist.build}, new int[]{18, 132, 128, 24}));

		if (Minestuck.isThaumLoaded)
			GristRegistry.addGristConversion(new ItemStack(ModItems.manaInkwell), false, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.build, MinestuckGrist.chalk, MinestuckGrist.shale, MinestuckGrist.tar}, new int[]{35, 1, 4, 1, 4}));


		GristRegistry.addGristConversion(new ItemStack(ModItems.blackLotus, 1, 0), true, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.iodine}, new int[]{8, 2}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.blackLotus, 1, 1), true, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.iodine}, new int[]{100, 2}));

		GristRegistry.addGristConversion(new ItemStack(ModItems.manaResource, 1, 6), true, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.garnet}, new int[]{1, 4}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.manaResource, 1, 15), true, new GristSet(new Grist[]{MinestuckGrist.mercury, MinestuckGrist.uranium, MinestuckGrist.build}, new int[]{2, 2, 3}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.manaResource, 1, 7), true, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.ruby, MinestuckGrist.rust}, new int[]{5, 8, 16}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.manaResource, 1, 8), true, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.ruby, MinestuckGrist.uranium}, new int[]{5, 8, 16}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.manaResource, 1, 9), true, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.ruby, MinestuckGrist.diamond}, new int[]{5, 16, 8}));
		GristRegistry.addGristConversion(new ItemStack(ModBlocks.elfGlass), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.mana, MinestuckGrist.diamond}, new int[]{1, 2, 1}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.quartz, 1, 5), true, new GristSet(new Grist[]{MinestuckGrist.quartz, MinestuckGrist.marble, MinestuckGrist.mana}, new int[]{1, 1, 4}));
		GristRegistry.addGristConversion(new ItemStack(ModBlocks.dreamwood), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.mana}, new int[]{2, 4}));

		GristRegistry.addGristConversion(new ItemStack(ModBlocks.altar), false, new GristSet(MinestuckGrist.build, 10));

		GristRegistry.addGristConversion(new ItemStack(ModItems.rune, 1, 0), true, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.marble, MinestuckGrist.rust, MinestuckGrist.cobalt}, new int[]{5, 8, 4, 16}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.rune, 1, 1), true, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.marble, MinestuckGrist.rust, MinestuckGrist.tar}, new int[]{5, 8, 4, 16}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.rune, 1, 2), true, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.marble, MinestuckGrist.rust, MinestuckGrist.shale}, new int[]{5, 8, 4, 16}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.rune, 1, 3), true, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.marble, MinestuckGrist.rust, MinestuckGrist.chalk}, new int[]{5, 8, 4, 16}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.rune, 1, 4), true, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.marble, MinestuckGrist.rust, MinestuckGrist.cobalt, MinestuckGrist.tar, MinestuckGrist.iodine}, new int[]{8, 8, 4, 8, 8, 12}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.rune, 1, 5), true, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.marble, MinestuckGrist.rust, MinestuckGrist.shale, MinestuckGrist.chalk, MinestuckGrist.caulk}, new int[]{8, 8, 4, 8, 8, 12}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.rune, 1, 6), true, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.marble, MinestuckGrist.rust, MinestuckGrist.chalk, MinestuckGrist.tar, MinestuckGrist.sulfur}, new int[]{8, 8, 4, 8, 8, 12}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.rune, 1, 7), true, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.marble, MinestuckGrist.rust, MinestuckGrist.cobalt, MinestuckGrist.shale}, new int[]{8, 8, 4, 16, 8}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.rune, 1, 8), true, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.marble, MinestuckGrist.rust, MinestuckGrist.uranium}, new int[]{24, 8, 8, 4}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.rune, 1, 9), true, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.marble, MinestuckGrist.rust, MinestuckGrist.shale, MinestuckGrist.chalk, MinestuckGrist.caulk, MinestuckGrist.quartz}, new int[]{12, 8, 4, 8, 16, 8, 4}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.rune, 1, 10), true, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.marble, MinestuckGrist.rust, MinestuckGrist.shale, MinestuckGrist.tar, MinestuckGrist.cobalt, MinestuckGrist.iodine}, new int[]{12, 8, 4, 8, 8, 16, 4}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.rune, 1, 11), true, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.marble, MinestuckGrist.rust, MinestuckGrist.cobalt, MinestuckGrist.tar, MinestuckGrist.iodine, MinestuckGrist.gold}, new int[]{12, 8, 4, 8, 16, 8, 4}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.rune, 1, 12), true, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.marble, MinestuckGrist.rust, MinestuckGrist.chalk, MinestuckGrist.tar, MinestuckGrist.sulfur, MinestuckGrist.caulk}, new int[]{12, 8, 4, 8, 16, 8, 4}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.rune, 1, 13), true, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.marble, MinestuckGrist.rust, MinestuckGrist.shale, MinestuckGrist.cobalt, MinestuckGrist.garnet}, new int[]{12, 8, 4, 16, 16, 4}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.rune, 1, 13), true, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.marble, MinestuckGrist.rust, MinestuckGrist.shale, MinestuckGrist.cobalt, MinestuckGrist.amethyst}, new int[]{12, 8, 4, 8, 24, 4}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.rune, 1, 12), true, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.marble, MinestuckGrist.rust, MinestuckGrist.chalk, MinestuckGrist.tar, MinestuckGrist.shale, MinestuckGrist.caulk, MinestuckGrist.diamond}, new int[]{12, 8, 4, 8, 8, 8, 8, 4}));

		GristRegistry.addGristConversion(new ItemStack(ModItems.gaiaHead), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.mana, MinestuckGrist.artifact, MinestuckGrist.zillium}, new int[]{125, 340, 200, 10}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.recordGaia1), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.mana, MinestuckGrist.artifact, MinestuckGrist.tar}, new int[]{84, 4, 5, 4}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.recordGaia2), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.mana, MinestuckGrist.artifact, MinestuckGrist.gold}, new int[]{88, 20, 50, 4}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.pinkinator), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.mana, MinestuckGrist.artifact, MinestuckGrist.zillium}, new int[]{1200, 200, 924, 1}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.ancientWill), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.mana}, new int[]{800, 400}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.infiniteFruit), false, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.iodine, MinestuckGrist.zillium}, new int[]{800, 1600, 1}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.kingKey), false, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.sulfur, MinestuckGrist.zillium}, new int[]{800, 1600, 1}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.flugelEye), false, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.uranium, MinestuckGrist.zillium}, new int[]{800, 1200, 1}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.odinRing), false, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.garnet, MinestuckGrist.ruby, MinestuckGrist.zillium}, new int[]{800, 600, 600, 1}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.thorRing), false, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.uranium, MinestuckGrist.shale, MinestuckGrist.zillium}, new int[]{800, 400, 800, 1}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.lokiRing), false, new GristSet(new Grist[]{MinestuckGrist.mana, MinestuckGrist.cobalt, MinestuckGrist.gold, MinestuckGrist.zillium}, new int[]{800, 800, 400, 1}));

		GristRegistry.addGristConversion(new ItemStack(ModItems.starSword), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.mana, MinestuckGrist.uranium, MinestuckGrist.rust, MinestuckGrist.diamond, MinestuckGrist.ruby, MinestuckGrist.garnet}, new int[]{450, 1020, 48, 36, 56, 84, 12}));
		GristRegistry.addGristConversion(new ItemStack(ModItems.thunderSword), false, new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.mana, MinestuckGrist.uranium, MinestuckGrist.rust, MinestuckGrist.diamond, MinestuckGrist.cobalt, MinestuckGrist.mercury}, new int[]{450, 1020, 48, 36, 56, 84, 12}));

		//Combination Recipes
		CombinationRegistry.addCombination(new ItemStack(ModItems.manaBottle), new ItemStack(Items.SUGAR), MODE_OR, MinestuckGrist.mana.getCandyItem());
		CombinationRegistry.addCombination(new ItemStack(gristBlocks.get(MinestuckGrist.mana)), new ItemStack(Items.GLASS_BOTTLE), MODE_OR, false, false, new ItemStack(ModItems.manaBottle));
		CombinationRegistry.addCombination(new ItemStack(ModItems.grassSeeds), new ItemStack(Blocks.DIRT), MODE_OR, true, false, new ItemStack(Blocks.GRASS));
		CombinationRegistry.addCombination(new ItemStack(ModItems.grassSeeds, 1, 1), new ItemStack(Blocks.DIRT), MODE_OR, true, false, new ItemStack(Blocks.DIRT, 1, 2));
		CombinationRegistry.addCombination(new ItemStack(ModItems.grassSeeds, 1, 2), new ItemStack(Blocks.DIRT), MODE_OR, true, false, new ItemStack(Blocks.MYCELIUM));
		CombinationRegistry.addCombination(new ItemStack(ModItems.overgrowthSeed), new ItemStack(Blocks.DIRT), MODE_OR, false, false, new ItemStack(ModBlocks.enchantedSoil));
		CombinationRegistry.addCombination(new ItemStack(ModItems.manaResource, 1, 5), new ItemStack(Items.SKULL, 1, 3), MODE_AND, true, true, new ItemStack(ModItems.gaiaHead));
		CombinationRegistry.addCombination(new ItemStack(ModItems.manaResource, 1, 5), new ItemStack(Items.RECORD_WAIT), MODE_AND, true, false, new ItemStack(ModItems.recordGaia1));
		CombinationRegistry.addCombination(new ItemStack(ModItems.manaResource, 1, 5), new ItemStack(Items.RECORD_13), MODE_AND, true, false, new ItemStack(ModItems.recordGaia1));
		CombinationRegistry.addCombination(new ItemStack(ModItems.manaResource, 1, 14), new ItemStack(Items.RECORD_13), MODE_AND, true, false, new ItemStack(ModItems.recordGaia2));
		CombinationRegistry.addCombination(new ItemStack(ModItems.manaResource, 1, 14), new ItemStack(Items.RECORD_WAIT), MODE_AND, true, false, new ItemStack(ModItems.recordGaia2));
		CombinationRegistry.addCombination(new ItemStack(ModBlocks.dreamwood, 1, 1), new ItemStack(Blocks.CRAFTING_TABLE), MODE_AND, true, false, new ItemStack(ModBlocks.openCrate, 1, 1));
		CombinationRegistry.addCombination(new ItemStack(ModBlocks.livingwood, 1, 1), new ItemStack(Blocks.CRAFTING_TABLE), MODE_AND, true, false, new ItemStack(ModBlocks.openCrate, 1, 0));

		for (EnumDyeColor color : EnumDyeColor.values())
		{
			int i = color.getMetadata();
			//flowerGrist.addGrist(flowerGrist);
			//flowerGrist.addGrist(iodine, 1);

			ItemStack wool = new ItemStack(Blocks.WOOL, 1, color.getMetadata());
			GristSet dyeGrist = GristRegistry.getGristConversion(wool).addGrist(MinestuckGrist.chalk, -6);

			if (dyeGrist.getGrist(MinestuckGrist.chalk) == 0) dyeGrist.addGrist(MinestuckGrist.chalk, 0);

			GristSet flowerGrist = dyeGrist.copy().scaleGrist(2).addGrist(MinestuckGrist.iodine, 1);
			GristSet doubleFlowerGrist = dyeGrist.copy().scaleGrist(4).addGrist(MinestuckGrist.iodine, 2);

			GristRegistry.addGristConversion(ModBlocks.flower, i, flowerGrist);
			GristRegistry.addGristConversion(i < 8 ? ModBlocks.doubleFlower1 : ModBlocks.doubleFlower2, i % 8, doubleFlowerGrist);
			GristRegistry.addGristConversion(new ItemStack(ModItems.dye, 1, i), dyeGrist);
			CombinationRegistry.addCombination(new ItemStack(Items.SUGAR), new ItemStack(Items.DYE, 1, color.getDyeDamage()), MODE_AND, false, true, new ItemStack(ModItems.dye, 1, i));
			CombinationRegistry.addCombination(new ItemStack(Blocks.TALLGRASS), new ItemStack(ModItems.petal, 1, i), MODE_AND, false, true, new ItemStack(ModBlocks.flower, 1, i));
			CombinationRegistry.addCombination(new ItemStack(Blocks.DOUBLE_PLANT, 1, 2), new ItemStack(ModItems.petal, 1, i), MODE_AND, true, true, new ItemStack(i < 8 ? ModBlocks.doubleFlower1 : ModBlocks.doubleFlower2, 1, i));
			CombinationRegistry.addCombination(new ItemStack(ModBlocks.flower, 1, i), new ItemStack(Items.GLOWSTONE_DUST), MODE_OR, true, false, new ItemStack(ModBlocks.shinyFlower, 1, i));
			CombinationRegistry.addCombination(new ItemStack(ModBlocks.shinyFlower, 1, i), new ItemStack(Blocks.GRASS), MODE_OR, true, false, new ItemStack(ModBlocks.floatingFlower, 1, i));
		}

		//Botania Recipes
		BotaniaAPI.registerManaInfusionRecipe(new ItemStack(magicBlock), new ItemStack(MinestuckBlocks.genericObject), 16000);

		BotaniaSupport.gristCosts = GristRegistry.getAllConversions();
	}

	private static void registerSplatcraft()
	{
		GristRegistry.addGristConversion("splatcraft", "power_egg", new GristSet(new Grist[]{MinestuckGrist.amber, MinestuckGrist.iodine}, new int[]{6, 3}));
		GristRegistry.addGristConversion("splatcraft", "sardinium", new GristSet(new Grist[]{MinestuckGrist.rust, MinestuckGrist.caulk}, new int[]{8, 2}));
		GristRegistry.addGristConversion("splatcraft", "sardinium_ore", new GristSet(new Grist[]{MinestuckGrist.build, MinestuckGrist.rust, MinestuckGrist.caulk}, new int[]{4, 8, 2}));

		CombinationRegistry.addCombination(new ItemStack(Items.FISH), new ItemStack(Blocks.IRON_ORE), MODE_AND, true, false, new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("splatcraft", "sardinium_ore"))));
	}

	private static void registerRecipes(ModSupport modSupport, String modname, boolean dynamic)
	{
		try
		{
			if (Loader.isModLoaded(modname))
			{
				if (dynamic)
					modSupport.registerDynamicRecipes();
				else modSupport.registerRecipes();
			}
		}
		catch (Exception e)
		{
			Debug.logger.error("Exception while creating" + (dynamic ? " dynamic" : "") + " recipes for mod \"" + modname + "\":", e);
		}
	}

	public static void registerAutomaticRecipes()
	{
		AutoGristGenerator autogrist = new AutoGristGenerator();
		autogrist.prepare();

		//Register container costs such as for filled buckets
		for (Map.Entry<ItemStack, GristSet> entry : containerlessCosts.entrySet())
		{
			ItemStack stack = entry.getKey();
			if (stack.isEmpty() || GristRegistry.getGristConversion(stack) != null)
				continue;
			ItemStack container = stack.getItem().getContainerItem(stack);
			GristSet cost = entry.getValue();
			if (!container.isEmpty())
			{
				GristSet containerCost = autogrist.lookupCostForItem(container);
				if (containerCost == null)
				{
					Debug.warnf("Can't generate a cost for %s: %s does not have a cost.", stack.getDisplayName(), container.getDisplayName());
					continue;
				}
				else cost.addGrist(containerCost);
			}

			GristRegistry.addGristConversion(stack, cost);
		}

		autogrist.excecute();

		registerRecipes(new Minegicka3Support(), "minegicka3", true);
	}

	public static List<ItemStack> getItems(Object item, int damage)
	{
		if (item instanceof ItemStack)
			return Arrays.asList((ItemStack) item);
		if (item instanceof Item)
			return Arrays.asList(new ItemStack((Item) item, 1, damage));
		else
		{
			List<ItemStack> list = OreDictionary.getOres((String) item);
			return list;
		}
	}
}
