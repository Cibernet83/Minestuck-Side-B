package com.mraof.minestuck.event.handler;

import com.mraof.minestuck.client.model.item.ModelCaptchaCard;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemModelEventHandler
{
	@SubscribeEvent
	public static void onTextureStitchPre(TextureStitchEvent.Pre event)
	{
		ModelCaptchaCard.LoaderCaptchaCard.INSTANCE.registerTextures(event.getMap());
	}
}
