package com.mraof.minestuck.client;

import com.mraof.minestuck.CommonProxy;
import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.block.MinestuckBlocks;
import com.mraof.minestuck.client.gui.GuiStrifeSwitcher;
import com.mraof.minestuck.client.model.*;
import com.mraof.minestuck.client.renderer.BlockColorCruxite;
import com.mraof.minestuck.client.renderer.CruxiteSlimeRenderer;
import com.mraof.minestuck.client.renderer.RenderMachineOutline;
import com.mraof.minestuck.client.renderer.ThrowableRenderFactory;
import com.mraof.minestuck.client.renderer.entity.*;
import com.mraof.minestuck.client.renderer.entity.frog.RenderFrog;
import com.mraof.minestuck.client.renderer.tileentity.RenderControlDeckModi;
import com.mraof.minestuck.client.renderer.tileentity.RenderGate;
import com.mraof.minestuck.client.renderer.tileentity.RenderSkaiaPortal;
import com.mraof.minestuck.client.settings.MinestuckKeyHandler;
import com.mraof.minestuck.editmode.ClientEditHandler;
import com.mraof.minestuck.entity.*;
import com.mraof.minestuck.entity.carapacian.EntityBishop;
import com.mraof.minestuck.entity.carapacian.EntityPawn;
import com.mraof.minestuck.entity.carapacian.EntityRook;
import com.mraof.minestuck.entity.consort.EntityIguana;
import com.mraof.minestuck.entity.consort.EntityNakagator;
import com.mraof.minestuck.entity.consort.EntitySalamander;
import com.mraof.minestuck.entity.consort.EntityTurtle;
import com.mraof.minestuck.entity.item.*;
import com.mraof.minestuck.entity.underling.*;
import com.mraof.minestuck.event.handlers.ClientEventHandler;
import com.mraof.minestuck.item.*;
import com.mraof.minestuck.item.weapon.ItemBeamBlade;
import com.mraof.minestuck.tileentity.TileEntityGate;
import com.mraof.minestuck.tileentity.TileEntityHolopad;
import com.mraof.minestuck.tileentity.TileEntityModusControlDeck;
import com.mraof.minestuck.tileentity.TileEntitySkaiaPortal;
import com.mraof.minestuck.util.AspectColorHandler;
import com.mraof.minestuck.util.ColorCollector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
	
	public static EntityPlayer getClientPlayer()	//Note: can't get the client player directly from FMLClientHandler either, as the server side will still crash because of the return type
	{
		return FMLClientHandler.instance().getClientPlayerEntity();
	}
	
	public static void addScheduledTask(Runnable runnable)
	{
		Minecraft.getMinecraft().addScheduledTask(runnable);
	}
	
	public static void registerRenderers() 
	{
		Minecraft mc = Minecraft.getMinecraft();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySkaiaPortal.class, new RenderSkaiaPortal());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGate.class, new RenderGate());
//		MinecraftForgeClient.registerItemRenderer(Minestuck.captchaCard, new RenderCard());

		Item[] cruxiteItems = new Item[MinestuckItems.cruxiteArtifacts.size()];
		MinestuckItems.cruxiteArtifacts.toArray(cruxiteItems);

		mc.getItemColors().registerItemColorHandler((stack, tintIndex) ->
				BlockColorCruxite.handleColorTint(ColorCollector.getColorFromNBT(stack), tintIndex), cruxiteItems);

		mc.getBlockColors().registerBlockColorHandler(new BlockColorCruxite(), MinestuckBlocks.alchemiter[0], MinestuckBlocks.totemlathe[1], MinestuckBlocks.blockCruxiteDowel, MinestuckBlocks.ceramicPorkhollow);

		Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) ->
						BlockColorCruxite.handleColorTint(stack.getMetadata() == 0 ? 0x99D9EA : ColorCollector.getColor(stack.getMetadata() - 1), tintIndex),
				new Item[]{MinestuckItems.cruxiteDowel, MinestuckItems.cruxiteGel, MinestuckItems.cruxtruderGel, MinestuckItems.captchalogueBook, MinestuckItems.chasityKey, Item.getItemFromBlock(MinestuckBlocks.ceramicPorkhollow)});

		mc.getItemColors().registerItemColorHandler((stack, tintIndex) -> {
			ItemFrog item = ((ItemFrog)stack.getItem());
			int color = -1;

			if((stack.getMetadata() > EntityFrog.maxTypes() || stack.getMetadata() < 1))
			{
				switch(tintIndex)
				{
				case 0: color = item.getSkinColor(stack); break;
				case 1: color = item.getEyeColor(stack); break;
				case 2: color = item.getBellyColor(stack); break;
				}
			}
		    return color;
		}, MinestuckItems.itemFrog);

		mc.getItemColors().registerItemColorHandler(new ItemBeamBlade.BladeColorHandler(), MinestuckItems.dyedBeamBlade);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) -> BlockColorCruxite.handleColorTint(ItemWarpMedallion.getColor(stack), tintIndex), MinestuckItems.returnMedallion);

		mc.getItemColors().registerItemColorHandler((stack, tintIndex) ->
		{
			switch(tintIndex)
			{
				case 0: return ItemGTKit.getColor(stack, AspectColorHandler.EnumColor.SHIRT);
				case 1: return ItemGTKit.getColor(stack, AspectColorHandler.EnumColor.PRIMARY);
				case 2: return ItemGTKit.getColor(stack, AspectColorHandler.EnumColor.SECONDARY);
				case 3: return ItemGTKit.getColor(stack, AspectColorHandler.EnumColor.SHOES);
				case 4: case 7: return ItemGTKit.getColor(stack, AspectColorHandler.EnumColor.SYMBOL);
				case 5: return ItemGTKit.getColor(stack, AspectColorHandler.EnumColor.DETAIL_PRIMARY);
				case 6: return ItemGTKit.getColor(stack, AspectColorHandler.EnumColor.DETAIL_SECONDARY);
				default: return 0xFFFFFF;
			}
		}, MinestuckItems.gtArmorKit, MinestuckItems.gtHood, MinestuckItems.gtShirt, MinestuckItems.gtPants, MinestuckItems.gtShoes);
	}
	
	@Override
	public void preInit()
	{
		super.preInit();
		RenderingRegistry.registerEntityRenderingHandler(EntityFrog.class, manager -> new RenderFrog(manager, new ModelBiped(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityNakagator.class, RenderEntityMinestuck.getFactory(new ModelNakagator(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntitySalamander.class, RenderEntityMinestuck.getFactory(new ModelSalamander(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityIguana.class, RenderEntityMinestuck.getFactory(new ModelIguana(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityTurtle.class, RenderEntityMinestuck.getFactory(new ModelTurtle(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityImp.class, RenderEntityMinestuck.getFactory(new ModelImp(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityOgre.class, RenderEntityMinestuck.getFactory(new ModelOgre(), 2.8F));
		RenderingRegistry.registerEntityRenderingHandler(EntityBasilisk.class, RenderEntityMinestuck.getFactory(new ModelBasilisk(), 2.8F));
		RenderingRegistry.registerEntityRenderingHandler(EntityLich.class, RenderEntityMinestuck.getFactory(new ModelLich(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityGiclops.class, RenderEntityMinestuck.getFactory(new ModelGiclops(), 7.6F));
		RenderingRegistry.registerEntityRenderingHandler(EntityBishop.class, RenderEntityMinestuck.getFactory(new ModelBishop(), 1.8F));
		RenderingRegistry.registerEntityRenderingHandler(EntityRook.class, RenderEntityMinestuck.getFactory(new ModelRook(), 2.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityUnderlingPart.class, manager -> new RenderShadow<>(manager, 2.8F));
		RenderingRegistry.registerEntityRenderingHandler(EntityBigPart.class, manager -> new RenderShadow<>(manager, 0F));
		RenderingRegistry.registerEntityRenderingHandler(EntityPawn.class, manager -> new RenderPawn(manager, new ModelBiped(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrist.class, RenderGrist::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityVitalityGel.class, RenderVitalityGel::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityDecoy.class, RenderDecoy::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityMetalBoat.class, RenderMetalBoat::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityCrewPoster.class, manager -> new RenderHangingArt<>(manager, "midnight_poster"));
		RenderingRegistry.registerEntityRenderingHandler(EntitySbahjPoster.class, manager -> new RenderHangingArt<>(manager, "sbahj_poster"));
		RenderingRegistry.registerEntityRenderingHandler(EntityShopPoster.class, manager -> new RenderHangingArt<>(manager, "shop_poster"));
		RenderingRegistry.registerEntityRenderingHandler(EntityCruxiteSlime.class, new CruxiteSlimeRenderer.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityEightBall.class, new ThrowableRenderFactory<>(MinestuckItems.eightBall));
		RenderingRegistry.registerEntityRenderingHandler(EntityCrystalEightBall.class, new ThrowableRenderFactory<>(MinestuckItems.crystalEightBall));
		RenderingRegistry.registerEntityRenderingHandler(EntityOperandiEightBall.class, new ThrowableRenderFactory<>(MinestuckItems.operandiEightBall));
		RenderingRegistry.registerEntityRenderingHandler(EntityOperandiSplashPotion.class, new ThrowableRenderFactory<>(MinestuckItems.operandiSplashPotion));
		RenderingRegistry.registerEntityRenderingHandler(EntityAcheron.class, RenderEntityMinestuck.getFactory(new ModelAcheron(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityMSUThrowable.class, RenderThrowable::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityMSUArrow.class, RenderArrow::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityUnrealAir.class, RenderUnrealAir::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRock.class, RenderRock::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityHopeGolem.class, (manager) -> new RenderHopeGolem(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityLocatorEye.class, (manager) -> new RenderSnowball<>(manager, MinestuckItems.denizenEye, Minecraft.getMinecraft().getRenderItem()));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHolopad.class, new RenderHologram());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityModusControlDeck.class, new RenderControlDeckModi());

		MinestuckKeyHandler.instance.registerKeys();
		MSGTKeyHandler.registerKeys();

		MinecraftForge.EVENT_BUS.register(MinestuckKeyHandler.instance);
		MinecraftForge.EVENT_BUS.register(ClientEventHandler.class);
		MinecraftForge.EVENT_BUS.register(MSURenderMachineOutline.class);
		MinecraftForge.EVENT_BUS.register(RenderBeams.class);
		MinecraftForge.EVENT_BUS.register(GuiStrifeSwitcher.class);
		MinecraftForge.EVENT_BUS.register(MSGTKeyHandler.class);
		MinecraftForge.EVENT_BUS.register(ClientProxy.class);
	}
	
	@Override
	public void init()
	{
		ClientProxy.registerRenderers();

		super.init();
		MinecraftForge.EVENT_BUS.register(ClientEditHandler.instance);
		MinecraftForge.EVENT_BUS.register(new MinestuckConfig());
		MinecraftForge.EVENT_BUS.register(RenderMachineOutline.class);

		MSKeyHandler.register();
		MSUFontRenderer.registerFonts();
	}

	@SubscribeEvent
	public static void handleModelRegistry(ModelRegistryEvent event)
	{
		for (IRegistryItem item : MinestuckItems.items)
			item.registerModel();

		if(MinestuckItems.splatcraftCruxiteFilter != null)
			ModelLoader.setCustomModelResourceLocation(MinestuckItems.splatcraftCruxiteFilter, 0,
					new ModelResourceLocation(MinestuckItems.splatcraftCruxiteFilter.getRegistryName(), "inventory"));
	}
}
