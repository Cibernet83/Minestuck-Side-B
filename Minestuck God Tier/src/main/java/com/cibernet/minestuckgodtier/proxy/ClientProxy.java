package com.cibernet.minestuckgodtier.proxy;

import com.cibernet.minestuckgodtier.MinestuckGodTier;
import com.cibernet.minestuckgodtier.client.MSGTKeyHandler;
import com.cibernet.minestuckgodtier.entities.MSGTEntities;
import com.cibernet.minestuckgodtier.items.ItemKit;
import com.cibernet.minestuckgodtier.items.MSGTItems;
import com.cibernet.minestuckgodtier.network.MSGTChannelHandler;
import com.cibernet.minestuckgodtier.util.AspectColorHandler;
import com.cibernet.minestuckgodtier.util.MSGTModelManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends CommonProxy
{
	
	public static void registerRenderers()
	{
		Minecraft mc = Minecraft.getMinecraft();
		
		mc.getItemColors().registerItemColorHandler((stack, tintIndex) ->
		{
			switch(tintIndex)
			{
			case 0: return ItemKit.getColor(stack, AspectColorHandler.EnumColor.SHIRT);
			case 1: return ItemKit.getColor(stack, AspectColorHandler.EnumColor.PRIMARY);
			case 2: return ItemKit.getColor(stack, AspectColorHandler.EnumColor.SECONDARY);
			case 3: return ItemKit.getColor(stack, AspectColorHandler.EnumColor.SHOES);
			case 4: case 7: return ItemKit.getColor(stack, AspectColorHandler.EnumColor.SYMBOL);
			case 5: return ItemKit.getColor(stack, AspectColorHandler.EnumColor.DETAIL_PRIMARY);
			case 6: return ItemKit.getColor(stack, AspectColorHandler.EnumColor.DETAIL_SECONDARY);
			default: return 0xFFFFFF;
			}
		}, MSGTItems.armorKit, MSGTItems.gtHood, MSGTItems.gtShirt, MSGTItems.gtPants, MSGTItems.gtShoes);
	}
	
	@SubscribeEvent
	public void onStitchTexture(TextureStitchEvent.Pre event) 
	{
		event.getMap().registerSprite(new ResourceLocation(MinestuckGodTier.MODID,"items/symbol/breath"));
	}
	
	@Override
	public void preInit() 
	{
		super.preInit();
		// MinecraftForge.EVENT_BUS.registerBadgeEvents(ModelEventHandler.instance);
		MinecraftForge.EVENT_BUS.register(MSGTModelManager.class);
		//MinecraftForge.EVENT_BUS.register(MachineOutlineEvent.class);
		MinecraftForge.EVENT_BUS.register(MSGTKeyHandler.class);

		MSGTKeyHandler.registerKeys();
		MSGTEntities.bindRenderers();
	}

	@Override
	public void init()
	{
		super.init();
		MinecraftForge.EVENT_BUS.register(MSGTChannelHandler.instance);
	}

}
