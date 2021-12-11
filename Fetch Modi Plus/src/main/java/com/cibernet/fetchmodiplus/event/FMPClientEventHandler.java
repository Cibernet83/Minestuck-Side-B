package com.cibernet.fetchmodiplus.event;

import com.cibernet.fetchmodiplus.captchalogue.*;
import com.cibernet.fetchmodiplus.items.BaseItem;
import com.cibernet.fetchmodiplus.network.FMPChannelHandler;
import com.cibernet.fetchmodiplus.network.FMPPacket;
import com.cibernet.fetchmodiplus.registries.FMPItems;
import com.cibernet.fetchmodiplus.registries.FMPSounds;
import com.mraof.minestuck.client.gui.playerStats.GuiPlayerStats;
import com.mraof.minestuck.client.settings.MinestuckKeyHandler;
import com.mraof.minestuck.editmode.ClientEditHandler;
import com.mraof.minestuck.inventory.captchalouge.CaptchaDeckHandler;
import com.mraof.minestuck.inventory.captchalouge.Modus;
import com.mraof.minestuck.network.CaptchaDeckPacket;
import com.mraof.minestuck.network.MinestuckChannelHandler;
import com.mraof.minestuck.network.MinestuckPacket;
import com.mraof.minestuck.util.MinestuckPlayerData;
import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.*;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.UUID;

public class FMPClientEventHandler
{
	private static int shake = 0;
	private static int shakeCooldown = 0;
	private static int prevSelectedSlot = 0;
	
	private static int eightBallMessage = -1;
	private static int maxEightBallMsgs = 20;
	
	private static final UUID WEIGHT_MODUS_SPEED_UUID = MathHelper.getRandomUUID(ThreadLocalRandom.current());
	private boolean captchaKeyPressed = false;

	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event)
	{
		
		EntityPlayer player = Minecraft.getMinecraft().player;
		
		if(player == null || event.phase != TickEvent.Phase.START)
			return;
		
		Modus modus = CaptchaDeckHandler.clientSideModus;
		
		if(modus != null)
		{
			double floatstoneValue = 0;
			double speedMod = 0;
			
			if(modus instanceof WeightModus)
			{
				floatstoneValue = ((WeightModus)modus).getFloatStones()*1.5;
				speedMod = (modus.getNonEmptyCards()-floatstoneValue) / -((WeightModus) modus).getItemCap();
			}
			
			AttributeModifier WEIGHT_MODUS_SPEED = (new AttributeModifier(WEIGHT_MODUS_SPEED_UUID, "Backpack Modus speed penalty", Math.min(0, speedMod), 2)).setSaved(false);
			
			IAttributeInstance attributeInstance = player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
			if(attributeInstance.hasModifier(WEIGHT_MODUS_SPEED))
				attributeInstance.removeModifier(WEIGHT_MODUS_SPEED);
			
			if(modus instanceof WeightModus)
			{
				attributeInstance.applyModifier(WEIGHT_MODUS_SPEED);
				if(!player.capabilities.isFlying)
				player.motionY += Math.min(0, speedMod+0.3)*(player.isInWater() || player.isElytraFlying() ? 0.07 : 0.1);
			}
		}
		if(modus instanceof CycloneModus)
			((CycloneModus) modus).cycle();
		
		if(prevSelectedSlot != player.inventory.currentItem)
			shakeCooldown = 1;
		
		float currentCameraAvg = (player.rotationPitch + player.rotationYawHead)/2f;
		float prevCameraAvg = (player.prevRotationPitch + player.prevRotationYawHead)/2f;
		
		if(Math.abs(currentCameraAvg - prevCameraAvg) > 10)
		{
			shake++;
			shakeCooldown = 16;
		}
		else if(shakeCooldown == 1)
		{
			shake = 0;
			shakeCooldown--;
			eightBallMessage = -1;
		}
		else if(shakeCooldown > 0) shakeCooldown--;
		
		if(player.getHeldItemMainhand().getItem().equals(FMPItems.eightBall))
		{
			if(shake > 30)
			{
				if(eightBallMessage == -1)
					eightBallMessage = player.world.rand.nextInt(maxEightBallMsgs);
				ITextComponent msg = new TextComponentTranslation("status.eightBallMessage."+eightBallMessage).setStyle(new Style().setColor(TextFormatting.BLUE));
				ItemStack storedStack = BaseItem.getStoredItem(player.getHeldItemMainhand());
				player.sendStatusMessage(storedStack.isEmpty() ? msg : storedStack.getTextComponent().setStyle(new Style().setColor(TextFormatting.BLUE)), true);
			}
		}
		else if(player.getHeldItemOffhand().getItem().equals(FMPItems.eightBall))
		{
			if(shake > 30)
			{
				if(eightBallMessage == -1)
				eightBallMessage = player.world.rand.nextInt(maxEightBallMsgs);
				ITextComponent msg = new TextComponentTranslation("status.eightBallMessage."+eightBallMessage).setStyle(new Style().setColor(TextFormatting.BLUE));
				ItemStack storedStack = BaseItem.getStoredItem(player.getHeldItemOffhand());
				player.sendStatusMessage(storedStack.isEmpty() ? msg : storedStack.getTextComponent().setStyle(new Style().setColor(TextFormatting.BLUE)), true);
			}
		}
		else if( shakeCooldown > 0) shakeCooldown = 0;
		
		prevSelectedSlot = player.inventory.currentItem;
	}
	
	@SubscribeEvent
	public static void onClientSendChat(ClientChatEvent event)
	{
		EntityPlayer player = Minecraft.getMinecraft().player;
		if(CaptchaDeckHandler.clientSideModus instanceof ChatModus)
			FMPChannelHandler.sendToServer(FMPPacket.makePacket(FMPPacket.Type.CHAT_MODUS_EJECT, event.getMessage(), true));
	}
	
	@SubscribeEvent
	public static void onReceiveChat(ClientChatReceivedEvent event)
	{

		EntityPlayer player = Minecraft.getMinecraft().player;
		if(CaptchaDeckHandler.clientSideModus instanceof ChatModus)
			FMPChannelHandler.sendToServer(FMPPacket.makePacket(FMPPacket.Type.CHAT_MODUS_EJECT, event.getMessage().getFormattedText(), false));
	}

	@SubscribeEvent
	public static void onKeyInput(InputEvent.KeyInputEvent event)	//This is only called during the game, when no gui is active
	{

		if(Keyboard.isKeyDown(MinestuckKeyHandler.instance.captchaKey.getKeyCode()) && Minecraft.getMinecraft().player.getHeldItemMainhand().isEmpty())
		{

			if(CaptchaDeckHandler.clientSideModus instanceof OuijaModus)
				MinestuckChannelHandler.sendToServer(MinestuckPacket.makePacket(MinestuckPacket.Type.CAPTCHA, CaptchaDeckPacket.GET, 0, false));
			if(CaptchaDeckHandler.clientSideModus instanceof WalletModus || CaptchaDeckHandler.clientSideModus instanceof CrystalBallModus)
				FMPChannelHandler.sendToServer(FMPPacket.makePacket(FMPPacket.Type.WALLET_CAPTCHA, Minecraft.getMinecraft().objectMouseOver));
		}

	}


	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		try
		{
			this.captchaKeyPressed = Keyboard.isKeyDown(MinestuckKeyHandler.instance.captchaKey.getKeyCode());
		} catch(IndexOutOfBoundsException ignored)
		{}
	}


}
