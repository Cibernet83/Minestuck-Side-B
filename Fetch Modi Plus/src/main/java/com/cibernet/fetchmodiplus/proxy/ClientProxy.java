package com.cibernet.fetchmodiplus.proxy;

import com.cibernet.fetchmodiplus.FMPModelManager;
import com.cibernet.fetchmodiplus.event.FMPClientEventHandler;
import com.cibernet.fetchmodiplus.network.FMPChannelHandler;
import com.cibernet.fetchmodiplus.registries.FMPEntities;
import com.cibernet.fetchmodiplus.registries.FMPItems;
import com.mraof.minestuck.client.renderer.BlockColorCruxite;
import com.mraof.minestuck.event.ClientEventHandler;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.util.ColorCollector;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy
{
	@Override
	public void pre()
	{
		super.pre();
		MinecraftForge.EVENT_BUS.register(FMPModelManager.class);
		
		FMPEntities.bindRenderers();
	}
	
	@Override
	public void init()
	{
		super.init();
		MinecraftForge.EVENT_BUS.register(FMPChannelHandler.instance);
		MinecraftForge.EVENT_BUS.register(FMPClientEventHandler.class);
		
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) ->
				BlockColorCruxite.handleColorTint(stack.getMetadata() == 0 ? 0x99D9EA : ColorCollector.getColor(stack.getMetadata() - 1), tintIndex),
				new Item[]{FMPItems.cruxiteGel, FMPItems.cruxtruderGel, FMPItems.captchalogueBook, FMPItems.chasityKey});
		
	}
}
