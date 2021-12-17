package com.mraof.minestuck.strife;

import com.mraof.minestuck.block.MinestuckBlocks;
import com.mraof.minestuck.item.MSThrowableBase;
import com.mraof.minestuck.item.properties.PropertyBreakableItem;
import com.mraof.minestuck.item.weapon.ItemDualClaw;
import com.mraof.minestuck.item.weapon.ItemMechanicalCrossbow;
import com.mraof.minestuck.item.weapon.MSBowBase;
import com.mraof.minestuck.item.weapon.MSShieldBase;
import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.item.ItemCruxitePotion;
import com.mraof.minestuck.item.MinestuckItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;

public class MinestuckKindAbstrata
{
	public static final ArrayList<KindAbstratus> kindAbstrata = new ArrayList<>();

	public static KindAbstratus hammerkind = new KindAbstratus("hammer", MinestuckItems.toolHammer).addKeywords("hammer").addItemTools(getItem("ic2", "forge_hammer"));
	public static KindAbstratus bladekind = new KindAbstratus("sword", MinestuckItems.toolSword).addKeywords("sword", "katana", "kukiri", "saber", "rapier", "excalibur").setConditional(((item, stack, res) -> res && !PropertyBreakableItem.isBroken(item, stack))).addItemTools(
			getItem("ic2", "nano_saber"), getItem("rats", "pirat_cutlass")
	).setConditional((item, stack, originalResult) -> originalResult && !stack.getItem().getRegistryName().getResourceDomain().equals("bibliocraft")); //blep temp fix
	public static KindAbstratus clubkind = new KindAbstratus("club", MinestuckItems.toolClub).addKeywords("mace", "club");
	public static KindAbstratus canekind = new KindAbstratus("cane", MinestuckItems.toolCane);
	public static KindAbstratus sicklekind = new KindAbstratus("sickle", MinestuckItems.toolSickle).addKeywords("sickle", "scythe");
	public static KindAbstratus spoonkind = new KindAbstratus("spoon", MinestuckItems.toolSpoon).addItemTools(getItem("sbahjaddon", "the_spoon")).addKeywords("spoon");
	public static KindAbstratus forkkind = new KindAbstratus("fork", MinestuckItems.toolFork).addKeywords("fork");

	public static KindAbstratus pickaxekind = new KindAbstratus("pickaxe", MinestuckItems.toolPickaxe).addItemClasses(ItemPickaxe.class);
	public static KindAbstratus axekind = new KindAbstratus("axe", MinestuckItems.toolAxe).addKeywords("battleaxe", "halberd").addItemClasses(ItemAxe.class).addItemTools(getItem("mowziesmobs", "wrought_axe"));
	public static KindAbstratus shovelkind = new KindAbstratus("shovel", MinestuckItems.toolShovel).addItemClasses(ItemSpade.class);
	public static KindAbstratus hoekind = new KindAbstratus("hoe").addItemClasses(ItemHoe.class).addItemTools(getItem("rats", "plague_scythe"), getItem("ic2", "electric_hoe"));
	public static KindAbstratus fshngrodkind = new KindAbstratus("fishingRod").addItemClasses(ItemFishingRod.class);
	public static KindAbstratus potionkind = new KindAbstratus("potion").addItemClasses(ItemPotion.class, ItemCruxitePotion.class).addItemTools(getItem("botania", "brewvial"), getItem("botania", "brewflask"),
			getItem("botania", "incensestick"), getItem("botania", "manabottle"));
	public static KindAbstratus throwkind = new KindAbstratus("projectile").setPreventRightClick(true).addKeywords("shuriken").addItemClasses(MSThrowableBase.class).addItemTools(
					MinestuckItems.dragonCharge, Items.SNOWBALL, Items.EGG, Items.ENDER_PEARL, Items.ENDER_EYE, Items.EXPERIENCE_BOTTLE, Items.SPLASH_POTION, Items.LINGERING_POTION,
					getItem("botania", "chakram"));

	public static KindAbstratus clawkind = new KindAbstratus("claw", MinestuckItems.toolClaws).addKeywords("katar");
	public static KindAbstratus glovekind = new KindAbstratus("glove", MinestuckItems.toolGauntlet).addKeywords("glove", "gauntlet", "fist").addItemTools(getItem("thaumcraft", "caster_basic"));
	public static KindAbstratus needlekind = new KindAbstratus("needles", MinestuckItems.toolNeedles).addKeywords("needle");
	public static KindAbstratus shieldkind = new KindAbstratus("shield").addItemClasses(ItemShield.class, MSShieldBase.class).setConditional((i, itemStack, res) -> res || i.isShield(itemStack, null));
	public static KindAbstratus bowkind = new KindAbstratus("bow").setPreventRightClick(true).addItemClasses(ItemBow.class, MSBowBase.class, ItemMechanicalCrossbow.class).setConditional(((item, stack, res) -> res && !PropertyBreakableItem.isBroken(item, stack))).addItemTools(
			getItem("botania", "livingwood_bow"), getItem("botania", "crystal_bow")
																																																																				   );
	public static KindAbstratus dicekind = new KindAbstratus("dice").addItemTools(MinestuckItems.dice, MinestuckItems.fluoriteOctet);

	public static KindAbstratus pistolkind = new KindAbstratus("pistol").setPreventRightClick(true).addItemTools(getItem("botania", "managun"), getItem("bibliocraft", "bibliodrill"), getItem("immersiveengineering", "revolver"));
	public static KindAbstratus riflekind = new KindAbstratus("rifle").setPreventRightClick(true).addItemTools(getItem("immersiveengineering", "railgun"), getItem("immersiveengineering", "chemthrower"), getItem("ic2", "mining_laser"));

	public static KindAbstratus halfBladekind = new KindAbstratus("halfSword", MinestuckItems.toolSword).addKeywords("sword", "blade").setConditional(((item, stack, res) -> res && PropertyBreakableItem.isBroken(item, stack))).setHidden(true);
	public static KindAbstratus halfBowkind = new KindAbstratus("halfBow").setConditional(((item, stack, res) -> res && PropertyBreakableItem.isBroken(item, stack) && bowkind.isStackCompatible(stack))).setHidden(true);

	//api abstrata
	public static KindAbstratus tridentkind = new KindAbstratus("trident").addKeywords("trident");
	public static KindAbstratus doubleTridentkind = new KindAbstratus("2x3dent");
	public static KindAbstratus lancekind = new KindAbstratus("lance").addKeywords("lance");
	public static KindAbstratus spearkind = new KindAbstratus("spear").addKeywords("spear").addItemTools(getItem("mowziesmobs", "spear"));
	public static KindAbstratus drillkind = new KindAbstratus("drill").addItemTools(getItem("actuallyadditions", "item_drill"), getItem("industrialforegoing", "infinity_drill"),
			getItem("ic2", "drill"), getItem("ic2", "diamond_drill"), getItem("ic2", "iridium_drill"));
	public static KindAbstratus chainsawkind = new KindAbstratus("chainsaw").addKeywords("chainsaw").addItemTools(getItem("ic2", "chainsaw"));
	public static KindAbstratus makeupkind = new KindAbstratus("makeup").addKeywords("lipstick", "lip_stick");
	public static KindAbstratus umbrellakind = new KindAbstratus("umbrella").addKeywords("umbrella");
	public static KindAbstratus broomkind = new KindAbstratus("broom").addKeywords("broom");
	public static KindAbstratus flshlghtkind = new KindAbstratus("flashlight").addKeywords("flashlight", "laser_pointer", "laserpointer");
	public static KindAbstratus fncysntatkind = new KindAbstratus("fancySanta").setHidden(true);
	public static KindAbstratus batonkind = new KindAbstratus("baton").addKeywords("baton");
	public static KindAbstratus wrenchkind = new KindAbstratus("wrench").addKeywords("wrench").addKeywords("wrench").addItemTools(getItem("ic2", "wrench"), getItem("openblocks", "wrench"), getItem("ic2", "wrench_new")
			, getItem("openblocks", "wrench"), getItem("refinedstorage", "wrench"), getItem("chiselsandbits", "wrench_wood"), getItem("actuallyadditions", "item_laser_wrench"),
			getItem("vending", "vendingmachinewrench"), getItem("ic2", "electric_wrench"), getItem("morphtool", "tool"));
	public static KindAbstratus knifekind = new KindAbstratus("knife").addKeywords("knife", "dagger", "katar", "knive", "kunai", "sai").addItemTools(getItem("actuallyadditions", "item_knife"), getItem("cfm", "item_knife"),
			getItem("mysticalworld", "amethyst_knife"), getItem("mysticalworld", "copper_knife"), getItem("mysticalworld", "silver_knife"), getItem("mysticalworld", "wood_knife"),
			getItem("mowziesmobs","naga_fang_dagger"),
			getItem("mysticalworld", "stone_knife"), getItem("mysticalworld", "iron_knife"), getItem("mysticalworld", "diamond_knife"), getItem("mysticalworld", "gold_knife"));
	public static KindAbstratus keykind = new KindAbstratus("key").addItemTools(getItem("locks", "key"), getItem("locks", "key_blank"),
			getItem("locks", "key_ring"), getItem("locks", "master_key"),
			getItem("randomthings", "spectrekey"), getItem("randomthings", "portkey"), getItem("tombstone", "grave_key"));
	public static KindAbstratus wandkind = new KindAbstratus("wand").addKeywords("wand").addItemTools(
			getItem("botania", "twigwand"), getItem("botania", "dirtrod"), getItem("botania", "skydirtrod"), getItem("botania", "cobblerod"),
			getItem("botania", "terraformrod"), getItem("botania", "waterrod"), getItem("botania", "rainbowrod"), getItem("botania", "tornadorod"),
			getItem("botania", "firerod"), getItem("botania", "smeltrod"), getItem("botania", "exchangerod"), getItem("botania", "diviningrod"),
			getItem("botania", "gravityrod"), getItem("botania", "missilerod"), getItem("thaumcraft", "pech_wand"), getItem("thaumcraft", "primal_crusher"),
			getItem("armourers_workshop", "item.wand-of-style"), getItem("betterbuilderswands", "wandstone"), getItem("betterbuilderswands", "wandstone"),
			getItem("betterbuilderswands", "wandiron"), getItem("betterbuilderswands", "wanddiamond"), getItem("betterbuilderswands", "wandunbreakable"),
			getItem("chisel", "offsettool"), getItem("customnpcs", "npcwand"), getItem("cyclicmagic", "cyclic_wand_build"), getItem("cyclicmagic", "tool_swap"),
			getItem("cyclicmagic", "tool_swap_match"), getItem("cyclicmagic", "tool_push"), getItem("roots", "staff"), getItem("rats", "cheese_stick"), getItem("rats", "radius_stick"));

	public static KindAbstratus rockkind = new KindAbstratus("rock").addItemTools(MinestuckItems.pebble, MinestuckItems.rock, MinestuckItems.bigRock,
			Item.getItemFromBlock(Blocks.COBBLESTONE), Item.getItemFromBlock(Blocks.STONE), Item.getItemFromBlock(Blocks.MOSSY_COBBLESTONE));
	public static KindAbstratus paperkind = new KindAbstratus("paper").addKeywords("paper").addItemTools(Items.PAPER, Items.MAP, Items.FILLED_MAP, MinestuckItems.rolledUpPaper, MinestuckItems.yesterdaysNews);
	public static KindAbstratus scissorkind = new KindAbstratus("shears").addKeywords("scissor").addItemClasses(ItemShears.class);

	public static KindAbstratus fistkind = new KindAbstratus("fist").setFist(true);
	public static KindAbstratus jokerkind = new KindAbstratus("joker").setConditional((i, stack, res) -> res || !stack.isEmpty()).setHidden(true);
	public static KindAbstratus sbahjkind = new KindAbstratus("sbahj")
			.addItemTools(MinestuckItems.sbahjWhip, MinestuckItems.unrealAir, MinestuckItems.batleacks, MinestuckItems.sord, MinestuckItems.sbahjPoster,
						  Item.getItemFromBlock(MinestuckBlocks.sbahjBedrock), Item.getItemFromBlock(MinestuckBlocks.sbahjTree))
			.setConditional((i, stack, res) -> res || i.getRegistryName().getResourcePath().equals("sbahjaddon")).setHidden(true);
	public static KindAbstratus bunnykind = new KindAbstratus("bunny").addKeywords("bunny", "rabbit", "hare")
			.addItemTools(MinestuckItems.bunnySlippers, Items.RABBIT, Items.COOKED_RABBIT, Items.RABBIT_FOOT, Items.RABBIT_HIDE, Items.RABBIT_STEW).setHidden(true);

	@SubscribeEvent
	public static void register(RegistryEvent.Register<KindAbstratus> event)
	{
		IForgeRegistry<KindAbstratus> registry = event.getRegistry();

		for (KindAbstratus kindAbstratus : kindAbstrata)
			kindAbstratus.register(registry);

		if(Minestuck.isArsenalLoaded)
			registerArsenalApi();
		if(Minestuck.isVcLoaded)
			registerVariedCommoditiesApi();

		keykind.setHidden(keykind.getToolItems().isEmpty());
		keykind.addItemTools(MinestuckItems.dungeonKey);

		wandkind.setHidden(wandkind.getToolItems().isEmpty());
		wandkind.addItemTools(MinestuckItems.staffOfOvergrowth, MinestuckItems.needlewands);

		knifekind.setHidden(knifekind.getToolItems().isEmpty());
		knifekind.addItemTools(MinestuckItems.katars, MinestuckItems.rocketKatars, MinestuckItems.sneakyDaggers, MinestuckItems.blizzardCutters, MinestuckItems.katarsOfZillywhomst);
	}

	protected static void registerArsenalApi()
	{
		doubleTridentkind.addItemTools(getItem("minestuckarsenal", "poseidons_entente"), getItem("minestuckarsenal", "imperial_fork"));
		axekind.addItemTools(getItem("minestuckarsenal", "key_axe"), getItem("minestuckarsenal", "shadow_axe"), getItem("minestuckarsenal", "mass_deduction"),
				getItem("minestuckarsenal", "aks"));
		batonkind.addItemTools(getItem("minestuckarsenal", "twirling_baton"), getItem("minestuckarsenal", "conductors_baton"), getItem("minestuckarsenal", "nightstick"),
				getItem("minestuckarsenal", "uranium_baton"), getItem("minestuckarsenal", "windwaker"), getItem("minestuckarsenal", "celestial_fulcrum"));
		bladekind.addItemTools(getItem("minestuckarsenal", "ancestral_sword"), getItem("minestuckarsenal", "cutlass"), getItem("minestuckarsenal", "candy_sword"),
				getItem("minestuckarsenal", "bladed_cane"), getItem("minestuckarsenal", "keyblade"));
		broomkind.addItemTools(getItem("minestuckarsenal", "broom"));
		canekind.addItemTools(getItem("minestuckarsenal", "horse_hitcher"), getItem("minestuckarsenal", "cuestick"), getItem("minestuckarsenal", "blindmans_directions"),
				getItem("minestuckarsenal", "bladed_cane"), getItem("minestuckarsenal", "regicane"), getItem("minestuckarsenal", "black_staff"),
				getItem("minestuckarsenal", "gold_staff"), getItem("minestuckarsenal", "crowbar"), getItem("minestuckarsenal", "blazing_glory"),
				getItem("minestuckarsenal", "radioactive_staff"));
		clawkind.addItemTools(getItem("minestuckarsenal", "nepeta_claws"), getItem("minestuckarsenal", "blue_claws"));
		clubkind.addItemTools(getItem("minestuckarsenal", "club_of_felony"), getItem("minestuckarsenal", "wooden_bat"), getItem("minestuckarsenal", "cricket_bat"));
		hammerkind.addItemTools(getItem("minestuckarsenal", "wrinklefucker"), getItem("minestuckarsenal", "barber_basher"));
		hoekind.addItemTools(getItem("minestuckarsenal", "scythe"), getItem("minestuckarsenal", "eightball_scythe"));
		needlekind.addItemTools(getItem("minestuckarsenal", "knitting_needles"), getItem("minestuckarsenal", "needle_wands"), getItem("minestuckarsenal", "thorns_of_oglogoth"),
				getItem("minestuckarsenal", "quills_of_echidna"));
		paperkind.addItemTools(getItem("minestuckarsenal", "paper_sword"));
		pickaxekind.addItemTools(getItem("minestuckarsenal", "mine_and_grist"));
		sicklekind.addItemTools(getItem("minestuckarsenal", "thorny_subject"), getItem("minestuckarsenal", "babys_first_thresher"), getItem("minestuckarsenal", "derse_reaper"),
				getItem("minestuckarsenal", "prospit_reaper"), getItem("minestuckarsenal", "hemeoreaper"), getItem("minestuckarsenal", "ow_the_edge"));
		tridentkind.addItemTools(getItem("minestuckarsenal", "wooden_trident"), getItem("minestuckarsenal", "silver_trident"), getItem("minestuckarsenal", "gold_trident"));
		wandkind.addItemTools(getItem("minestuckarsenal", "needle_wands"));

		ArrayList<Item> lipstickChainsaws = new ArrayList<>();
		for(Item i : new Item[]{getItem("minestuckarsenal", "lipstick_chainsaw"), getItem("minestuckarsenal", "demonbane_ragripper")})
			lipstickChainsaws.add(i);

		chainsawkind.addItemTools(getItem("minestuckarsenal", "dainty_disembowler"), getItem("minestuckarsenal", "uranium_chainsaw"))
				.setConditional((item, stack, res) -> item != null && lipstickChainsaws.contains(item) && ItemDualClaw.isDrawn(stack));
		keykind.addItemTools(getItem("minestuckarsenal", "keyboard_key"), getItem("minestuckarsenal", "keyblade"), getItem("minestuckarsenal", "true_blue"),
				getItem("minestuckarsenal", "candy_key"), getItem("minestuckarsenal", "chronolatch"), getItem("minestuckarsenal", "yaldabaoths_keyton"),
				getItem("minestuckarsenal", "allweddol")).setConditional((item, stack, res) -> res || item != null && item == getItem("minestuckarsenal", "house_key") && !ItemDualClaw.isDrawn(stack));
		makeupkind.setConditional((item, stack, res) -> item != null && lipstickChainsaws.contains(item) && !ItemDualClaw.isDrawn(stack));

		sbahjkind.addItemTools(getItem("minestuckarsenal", "sbahjifier"), getItem("minestuckarsenal", "aks"));
		lancekind.addItemTools(getItem("minestuckarsenal", "cigarette_lance"), getItem("minestuckarsenal", "jousting_lance"), getItem("minestuckarsenal", "rocketpop_lance"),
				getItem("minestuckarsenal", "fiduspawn_lance"));
		pistolkind.addItemTools(getItem("minestuckarsenal", "beretta"), getItem("minestuckarsenal", "gold_beretta"), getItem("minestuckarsenal", "gunblade"),
				getItem("minestuckarsenal", "gun_of_ghouls")).setConditional((item, stack, res) -> res || item != null && item == getItem("minestuckarsenal", "house_key") && ItemDualClaw.isDrawn(stack));
		riflekind.addItemTools(getItem("minestuckarsenal", "hunting_rifle"), getItem("minestuckarsenal", "harpoon_gun"), getItem("minestuckarsenal", "girls_best_friend"),
				getItem("minestuckarsenal", "green_sun_streetsweeper"), getItem("minestuckarsenal", "ahabs_crosshairs"));

	}


	protected static void registerVariedCommoditiesApi()
	{
		bladekind.addItemTools(getItem("variedcommodities", "wooden_glaive"), getItem("variedcommodities", "iron_glaive"), getItem("variedcommodities", "golden_glaive"),
				getItem("variedcommodities", "diamond_glaive"), getItem("variedcommodities", "bronze_glaive"), getItem("variedcommodities", "emerald_glaive"),
				getItem("variedcommodities", "demonic_glaive"), getItem("variedcommodities", "frost_glaive"), getItem("variedcommodities", "mithril_glaive"));
		bowkind.addItemTools(getItem("variedcommodities", "crossbow"), getItem("variedcommodities", "slingshot"));
		canekind.addItemTools(getItem("variedcommodities", "golf_club"), getItem("variedcommodities", "hockey_stick"), getItem("variedcommodities", "crowbar"),
				getItem("variedcommodities", "bo_staff"));
		chainsawkind.addItemTools(getItem("variedcommodities", "chainsaw_gun"));
		clawkind.addItemTools(getItem("variedcommodities", "ninja_claw"), getItem("variedcommodities", "steel_claw"), getItem("variedcommodities", "bear_claw"),
				getItem("variedcommodities", "katar"));
		clubkind.addItemTools(getItem("variedcommodities", "baseball_bat"), getItem("variedcommodities", "lead_pipe"), getItem("variedcommodities", "macuahuitl"));
		knifekind.addItemTools(getItem("variedcommodities", "cleaver"), getItem("variedcommodities", "broken_bottle"));
		pistolkind.addItemTools(getItem("variedcommodities", "wooden_gun"), getItem("variedcommodities", "stone_gun"), getItem("variedcommodities", "iron_gun"),
				getItem("variedcommodities", "golden_gun"), getItem("variedcommodities", "diamond_gun"), getItem("variedcommodities", "bronze_gun"),
				getItem("variedcommodities", "emerald_gun"));
		riflekind.addItemTools(getItem("variedcommodities", "machine_gun"), getItem("variedcommodities", "musket"));
		throwkind.addItemTools(getItem("variedcommodities", "kunai"), getItem("variedcommodities", "kunai_reversed"));
		spearkind.addItemTools(getItem("variedcommodities", "wooden_spear"));
		tridentkind.addItemTools(getItem("variedcommodities", "wooden_trident"));
		wandkind.addItemTools(getItem("variedcommodities", "wooden_staff"), getItem("variedcommodities", "stone_staff"), getItem("variedcommodities", "iron_staff"),
				getItem("variedcommodities", "golden_staff"), getItem("variedcommodities", "diamond_staff"), getItem("variedcommodities", "bronze_staff"),
				getItem("variedcommodities", "emerald_staff"), getItem("variedcommodities", "demonic_staff"), getItem("variedcommodities", "frost_staff"),
				getItem("variedcommodities", "mithril_staff"), getItem("variedcommodities", "elemental_staff"));

	}


	public static Item getItem(String modid, String regName)
	{
		return Item.REGISTRY.getObject(new ResourceLocation(modid, regName));
	}
}
