package com.mraof.minestuck;

import com.mraof.minestuck.advancements.MinestuckCriteriaTriggers;
import com.mraof.minestuck.alchemy.AlchemyRecipes;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.client.gui.MinestuckGuiHandler;
import com.mraof.minestuck.editmode.DeployList;
import com.mraof.minestuck.entity.MinestuckEntities;
import com.mraof.minestuck.entity.consort.ConsortDialogue;
import com.mraof.minestuck.entity.consort.ConsortRewardHandler;
import com.mraof.minestuck.event.handler.*;
import com.mraof.minestuck.item.ItemMinestuckCandy;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.modSupport.BotaniaSupport;
import com.mraof.minestuck.modSupport.CarryOnSupport;
import com.mraof.minestuck.modSupport.MSGTTrophySlotsSupport;
import com.mraof.minestuck.modSupport.SplatcraftSupport;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.network.skaianet.SessionHandler;
import com.mraof.minestuck.recipes.MachineChasisRecipes;
import com.mraof.minestuck.tileentity.*;
import com.mraof.minestuck.util.*;
import com.mraof.minestuck.world.MinestuckDimensionHandler;
import com.mraof.minestuck.world.gen.OreHandler;
import com.mraof.minestuck.world.gen.QuestBedWorldGenHandler;
import com.mraof.minestuck.world.gen.structure.StructureCastlePieces;
import com.mraof.minestuck.world.gen.structure.StructureCastleStart;
import com.mraof.minestuck.world.lands.LandAspectRegistry;
import com.mraof.minestuck.world.lands.structure.MapGenLandStructure;
import com.mraof.minestuck.world.lands.structure.village.ConsortVillageComponents;
import com.mraof.minestuck.world.storage.loot.MinestuckLoot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy
{
	public void preInit()
	{
		MinestuckCapabilities.registerCapabilities();

		if(Minestuck.isTrophySlotsLoaded)
			MinecraftForge.EVENT_BUS.register(MSGTTrophySlotsSupport.class);

		MinecraftForge.EVENT_BUS.register(MinestuckSoundHandler.class);
		MinestuckSoundHandler.initSound();
		
		MinestuckCriteriaTriggers.register();
	}
	
	public void init()
	{
		BannerPatterns.init();

		MinestuckDimensionHandler.register();

		//register ore generation
		OreHandler oreHandler = new OreHandler();
		GameRegistry.registerWorldGenerator(oreHandler, 0);

		//register GUI handler
		NetworkRegistry.INSTANCE.registerGuiHandler(Minestuck.instance, new MinestuckGuiHandler());
		
		MinestuckEntities.registerEntities();
		//register Tile Entities
		GameRegistry.registerTileEntity(TileEntitySkaiaPortal.class, Minestuck.MODID+":gate_portal");
		GameRegistry.registerTileEntity(TileEntityMiniAlchemiter.class, Minestuck.MODID+":mini_alchemiter");
		GameRegistry.registerTileEntity(TileEntityMiniCruxtruder.class, Minestuck.MODID+":mini_cruxtruder");
		GameRegistry.registerTileEntity(TileEntityMiniPunchDesignix.class, Minestuck.MODID+":mini_punch_designix");
		GameRegistry.registerTileEntity(TileEntityMiniTotemLathe.class, Minestuck.MODID+":mini_totem_lathe");
		GameRegistry.registerTileEntity(TileEntityPunchDesignix.class, Minestuck.MODID+":punch_designix");
		GameRegistry.registerTileEntity(TileEntityTotemLathe.class, Minestuck.MODID+":totem_lathe");
		GameRegistry.registerTileEntity(TileEntityAlchemiter.class,Minestuck.MODID+":alchemiter");
		GameRegistry.registerTileEntity(TileEntityCruxtruder.class, Minestuck.MODID+":cruxtruder");
		GameRegistry.registerTileEntity(TileEntityItemStack.class, Minestuck.MODID+":item_stack");
		GameRegistry.registerTileEntity(TileEntityGristWidget.class, Minestuck.MODID+":grist_widget");
		GameRegistry.registerTileEntity(TileEntityComputer.class, Minestuck.MODID+":computer_sburb");
		GameRegistry.registerTileEntity(TileEntityTransportalizer.class, Minestuck.MODID+":transportalizer");
		GameRegistry.registerTileEntity(TileEntityGate.class, Minestuck.MODID+":gate");
		GameRegistry.registerTileEntity(TileEntityUraniumCooker.class, Minestuck.MODID+":uranium_cooker");
		GameRegistry.registerTileEntity(TileEntityHolopad.class, Minestuck.MODID+":holopad");
		GameRegistry.registerTileEntity(TileEntityModusControlDeck.class, Minestuck.MODID+":modus_control_deck");
		GameRegistry.registerTileEntity(TileEntityGristHopper.class, Minestuck.MODID+":grist_hopper");
		GameRegistry.registerTileEntity(TileEntityAutoWidget.class, Minestuck.MODID+":auto_widget");
		GameRegistry.registerTileEntity(TileEntityAutoCaptcha.class, Minestuck.MODID+":auto_captcha");
		GameRegistry.registerTileEntity(TileEntityMachineChassis.class, Minestuck.MODID+":machine_chasis");
		GameRegistry.registerTileEntity(TileEntityRedTransportalizer.class, Minestuck.MODID+":red_transportalizer");
		GameRegistry.registerTileEntity(TileEntityParadoxTransportalizer.class, Minestuck.MODID+":paradox_transportalizer");
		GameRegistry.registerTileEntity(TileEntityPlatinumTransportalizer.class, Minestuck.MODID+":platinum_transportalizer");
		GameRegistry.registerTileEntity(TileEntityEffectBeacon.class, Minestuck.MODID+":effect_beacon");
		GameRegistry.registerTileEntity(TileEntityBoondollarRegister.class, Minestuck.MODID+":porkhollow_vault");

		//register channel handler
		MinestuckNetwork.setupChannel();

		//Register structures
		MapGenStructureIO.registerStructure(StructureCastleStart.class, "SkaiaCastle");
		StructureCastlePieces.registerComponents();
		MapGenLandStructure.registerStructures();
		ConsortVillageComponents.registerComponents();
		GameRegistry.registerWorldGenerator(new QuestBedWorldGenHandler(), 0);

		//update candy
		((ItemMinestuckCandy) MinestuckItems.candy).updateCandy();

		//register smelting recipes and oredictionary
		CraftingRecipes.registerSmelting();
		CraftingRecipes.addOredictionary();

		//register grist costs and combination recipes
		AlchemyRecipes.registerVanillaRecipes();
		AlchemyRecipes.registerMinestuckRecipes();
		AlchemyRecipes.registerModRecipes();
		MachineChasisRecipes.registerRecipes();

		//register consort shop prices
		ConsortRewardHandler.registerMinestuckPrices();

		//Register loot functionality objects
		MinestuckLoot.registerLootClasses();

		LandAspectRegistry.registerLandAspects();
		ConsortDialogue.init();

		KindAbstratusList.registerTypes();
		DeployList.registerItems();

		if(Minestuck.isCarryOnLoaded)
			MinecraftForge.EVENT_BUS.register(CarryOnSupport.class);

		ComputerProgram.registerProgram(0, SburbClient.class, new ItemStack(MinestuckItems.disk, 1, 0));	//This idea was kind of bad and should be replaced
		ComputerProgram.registerProgram(1, SburbServer.class, new ItemStack(MinestuckItems.disk, 1, 1));

		SessionHandler.maxSize = 144;//acceptTitleCollision?(generateSpecialClasses?168:144):12;
	}

	public void postInit()
	{
		if(Minestuck.isBotaniaLoaded)
			BotaniaSupport.generateGristCosts();

		MinestuckItems.setPostInitVariables();

		if(Minestuck.isSplatcraftLodaded)
		{
			MinecraftForge.EVENT_BUS.register(SplatcraftSupport.class);
			SplatcraftSupport.registerColors();
		}
	}

	public void serverStarted()
	{
		IDBasedAlchemyHandler.calculateMaxID();
	}
}
