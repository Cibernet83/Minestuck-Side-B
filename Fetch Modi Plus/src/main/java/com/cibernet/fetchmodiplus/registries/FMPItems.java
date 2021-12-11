package com.cibernet.fetchmodiplus.registries;

import com.cibernet.fetchmodiplus.captchalogue.PopTartModus;
import com.cibernet.fetchmodiplus.entities.EightBallEntity;
import com.cibernet.fetchmodiplus.items.*;
import com.google.common.collect.Lists;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.item.TabMinestuck;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;

public class FMPItems
{
	public static final ArrayList<Item> items = Lists.newArrayList();
	
	public static final Item wildMagicModus = new ModusItem("wild_magic_modus");
	public static final Item weightModus = new ModusItem("weight_modus");
	public static final Item bookModus = new ModusItem("book_modus");
	public static final Item capitalistModus = new ModusItem("capitalist_modus");
	public static final Item modUs = new ModusItem("mod_us");
	public static final Item operandiModus = new ModusItem("operandi_modus");
	public static final Item onionModus = new ModusItem("onion_modus");
	public static final Item slimeModus = new ModusItem("slime_modus");
	public static final Item popTartModus = new ModusItem("pop_tart_modus");
	public static final Item deckModus = new ModusItem("deck_modus");
	public static final Item hueModus = new ModusItem("hue_modus");
	public static final Item hueStackModus = new ModusItem("hue_stack_modus");
	public static final Item chatModus = new ModusItem("chat_modus");
	public static final Item cycloneModus = new ModusItem("cyclone_modus");
	public static final Item energyModus = new ModusItem("energy_modus");
	public static final Item scratchAndSniffModus = new ModusItem("scratch_and_sniff_modus");
	public static final Item eightBallModus = new ModusItem("eight_ball_modus");
	public static final Item chasityModus = new ModusItem("chasity_modus");
	public static final Item jujuModus = new ModusItem("juju_modus");
	public static final Item alcheModus = new ModusItem("alchemodus");


	public static final Item arrayModus = new ModusItem("array_modus");
	public static final Item monsterModus = new ModusItem("monster_modus");
	public static final Item walletModus = new ModusItem("wallet_modus");
	public static final Item crystalBallModus = new ModusItem("crystal_ball_modus");

	/*
	public static final Item memoryModus = new ModusItem("memory_modus");
	public static final Item recipeModus = new ModusItem("recipe_modus");
	public static final Item bottledMsgModus = new ModusItem("message_in_a_bottle_modus");
	public static final Item techHopModus = new ModusItem("tech_hop_modus");
	public static final Item encryptionModus = new ModusItem("encryption_modus");
	public static final Item ouijaModus = new ModusItem("ouija_modus");
	public static final Item bundleModus = new ModusItem("bundle_modus");
	public static final Item cakeModus = new ModusItem("cake_modus");
	public static final Item cipherModus = new ModusItem("cipher_modus");
	*/

	public static final Item hashchatModus = new ModusItem("hashchat_modus");
	public static final Item sacrificeModus = new ModusItem("sacrifice_modus");

	public static final Item popTart = new FoodItem("pop_tart", 3, 0, false, PopTartModus.getConsumer());
	public static final Item eightBall = new EightBallItem("eight_ball", false);
	public static final Item popBall = new FoodItem("magic_pop_balls", 6, 0.4f, false, FoodItem.getPopBallConsumer());
	public static final Item floatStone = new BaseItem("float_stone").setMaxStackSize(1).setCreativeTab(TabMinestuck.instance);
	public static final Item energyCell = new BaseItem("energy_cell").setCreativeTab(TabMinestuck.instance);
	public static final Item captchalogueBook = new CaptchaBookItem("captchalogue_book");
	public static final Item chasityKey = new ChasityKeyItem("chasity_key");
	//public static final Item cruxiteBottle = new BaseItem("cruxite_bottle");
	public static final Item crystalEightBall = new EightBallItem("crystal_eight_ball", true);

	public static final Item cruxiteGel = new CruxiteItem("cruxite_gel");
	public static final Item dragonGel = new BaseItem("dragon_gel").setCreativeTab(TabMinestuck.instance);
	public static final Item cruxtruderGel = new CruxtruderGelItem("cruxtruder_gel");
	
	
	public static final Item operandiPickaxe = new OperandiToolItem("operandi_pickaxe", "pickaxe", 1.0F, -2.8F, 7.0f, 3);
	public static final Item operandiAxe = new OperandiToolItem("operandi_axe", "axe", 5.0f, -3.2F, 7.0f, 3);
	public static final Item operandiShovel = new OperandiToolItem("operandi_shovel", "shovel", 1.5F, -3.0F, 7.0F, 3);
	public static final Item operandiHoe = new OperandiHoeItem("operandi_hoe");
	public static final Item operandiSword = new OperandiWeaponItem("operandi_sword", "", 4.0f, -2.4000000953674316f, 0f, 3);
	public static final Item operandiHammer = new OperandiWeaponItem("operandi_hammer", "pickaxe", 5.0f, -2.45f, 7.0f, 4);
	public static final Item operandiClub = new OperandiWeaponItem("operandi_club", "club", 4.0F, -2.2F, 7.0f, 5);
	public static final Item operandiBattleaxe = new OperandiWeaponItem("operandi_battleaxe", "axe", 7.0F, -3.2F, 7.0f, 2);
	public static final Item operandiApple = new OperandiFoodItem("operandi_apple", 4, 0.15F);
	public static final Item operandiPotion = new OperandiFoodItem("operandi_potion", 1, 0.4f, EnumAction.DRINK);
	public static final Item operandiPopTart = new OperandiFoodItem("operandi_pop_tart", 3, 0);
	public static final Item operandiEightBall = new OperandiThrowableItem("operandi_eight_ball", 0, 1.5f, FMPSounds.eightBallThrow);
	public static final Item operandiSplashPotion = new OperandiThrowableItem("operandi_splash_potion", -20, 0.5f, SoundEvents.ENTITY_SPLASH_POTION_THROW);
	public static final Item operandiHelmet = new OperandiArmorItem("operandi_helmet", EntityEquipmentSlot.HEAD);
	public static final Item operandiChestplate = new OperandiArmorItem("operandi_chestplate", EntityEquipmentSlot.CHEST);
	public static final Item operandiLeggings = new OperandiArmorItem("operandi_leggings", EntityEquipmentSlot.LEGS);
	public static final Item operandiBoots = new OperandiArmorItem("operandi_boots", EntityEquipmentSlot.FEET);
	
	public static final Item operandiBlock = new OperandiBlockItem("operandi_block", FMPBlocks.operandiBlock);
	public static final Item operandiStone = new OperandiBlockItem("operandi_stone", FMPBlocks.operandiStone);
	public static final Item operandiLog = new OperandiBlockItem("operandi_log", FMPBlocks.operandiLog);
	public static final Item operandiGlass = new OperandiBlockItem("operandi_glass", FMPBlocks.operandiGlass);
	public static final Item hardStone = new ItemBlock(FMPBlocks.hardStone).setRegistryName("hard_stone").setCreativeTab(TabMinestuck.instance);
    public static final Item walletEntityItem = new WalletEntityItem("wallet_entity");


    @SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		items.add(hardStone);
		
		IForgeRegistry<Item> registry = event.getRegistry();
		for(Item item : items)
			registry.register(item);
		
		for(Item modus : ModusItem.fetchModi)
			OreDictionary.registerOre("modus", modus);
		for(int i = 0; i < 6; i++)
			OreDictionary.registerOre("modus", new ItemStack(MinestuckItems.modusCard, 1, i));
		
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(eightBall, new BehaviorProjectileDispense()
		{
			/**
			 * Return the projectile entity spawned by this dispense behavior.
			 */
			protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn)
			{
				return new EightBallEntity(worldIn, position.getX(), position.getY(), position.getZ(), BaseItem.getStoredItem(stackIn));
			}
		});
	}
}
