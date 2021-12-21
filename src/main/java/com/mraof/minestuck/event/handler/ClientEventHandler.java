package com.mraof.minestuck.event.handler;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.block.MinestuckBlocks;
import com.mraof.minestuck.client.gui.GuiColorSelector;
import com.mraof.minestuck.client.gui.playerStats.GuiDataChecker;
import com.mraof.minestuck.client.gui.playerStats.GuiEcheladder;
import com.mraof.minestuck.client.gui.playerStats.GuiPlayerStats;
import com.mraof.minestuck.client.settings.MinestuckKeyHandler;
import com.mraof.minestuck.entity.consort.EnumConsort;
import com.mraof.minestuck.inventory.ContainerConsortMerchant;
import com.mraof.minestuck.inventory.ContainerEditmode;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.network.skaianet.SkaiaClient;
import com.mraof.minestuck.util.ColorCollector;
import com.mraof.minestuck.util.Debug;
import com.mraof.minestuck.util.MinestuckPlayerData;
import com.mraof.minestuck.util.ModusStorage;
import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.block.BlockStem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Used to track mixed client sided events.
 */
@SideOnly(Side.CLIENT)
public class ClientEventHandler
{
	private static int shake = 0;
	private static int shakeCooldown = 0;
	private static int prevSelectedSlot = 0;

	private static int eightBallMessage = -1;
	private static int maxEightBallMsgs = 20;

	private static final UUID WEIGHT_MODUS_SPEED_UUID = MathHelper.getRandomUUID(ThreadLocalRandom.current());

	@SubscribeEvent
	public static void onConnectedToServer(ClientConnectedToServerEvent event)	//Reset all static client-side data here
	{
		GuiPlayerStats.normalTab = GuiPlayerStats.NormalGuiType.CAPTCHA_DECK;
		GuiPlayerStats.editmodeTab = GuiPlayerStats.EditmodeGuiType.DEPLOY_LIST;
		ContainerEditmode.clientScroll = 0;
		MinestuckPlayerData.clientData = new MinestuckPlayerData.PlayerData();
		ColorCollector.playerColor = -1;
		ColorCollector.displaySelectionGui = false;
		GuiDataChecker.activeComponent = null;
		GuiEcheladder.lastRung = -1;
		GuiEcheladder.animatedRung = 0;
		SkaiaClient.clear();
	}
	
	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event)
	{
		if(event.phase == TickEvent.Phase.END)
		{
			if(ColorCollector.displaySelectionGui && Minecraft.getMinecraft().currentScreen == null)
			{
				ColorCollector.displaySelectionGui = false;
				if(MinestuckConfig.loginColorSelector)
					Minecraft.getMinecraft().displayGuiScreen(new GuiColorSelector(true));
			}
			
		}

		EntityPlayer player = Minecraft.getMinecraft().player;

		if(player == null || event.phase != TickEvent.Phase.START)
			return;

		/*Modus modus = SylladexUtils.clientSideModus;

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
			((CycloneModus) modus).cycle();*/ // TODO: Weight and cyclone modi

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

		if(player.getHeldItemMainhand().getItem().equals(MinestuckItems.eightBall))
		{
			if(shake > 30)
			{
				if(eightBallMessage == -1)
					eightBallMessage = player.world.rand.nextInt(maxEightBallMsgs);
				ITextComponent msg = new TextComponentTranslation("status.eightBallMessage." + eightBallMessage).setStyle(new Style().setColor(TextFormatting.BLUE));
				ItemStack storedStack = ModusStorage.getStoredItem(player.getHeldItemMainhand());
				player.sendStatusMessage(storedStack.isEmpty() ? msg : storedStack.getTextComponent().setStyle(new Style().setColor(TextFormatting.BLUE)), true);
			}
		}
		else if(player.getHeldItemOffhand().getItem().equals(MinestuckItems.eightBall))
		{
			if(shake > 30)
			{
				if(eightBallMessage == -1)
					eightBallMessage = player.world.rand.nextInt(maxEightBallMsgs);
				ITextComponent msg = new TextComponentTranslation("status.eightBallMessage."+eightBallMessage).setStyle(new Style().setColor(TextFormatting.BLUE));
				ItemStack storedStack = ModusStorage.getStoredItem(player.getHeldItemOffhand());
				player.sendStatusMessage(storedStack.isEmpty() ? msg : storedStack.getTextComponent().setStyle(new Style().setColor(TextFormatting.BLUE)), true);
			}
		}
		else if( shakeCooldown > 0) shakeCooldown = 0;

		prevSelectedSlot = player.inventory.currentItem;
	}
	
	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public static void addCustomTooltip(ItemTooltipEvent event)
	{
		//Add config check
		{
			ItemStack stack = event.getItemStack();
			if(event.getEntityPlayer() != null && event.getEntityPlayer().openContainer instanceof ContainerConsortMerchant
					&& event.getEntityPlayer().openContainer.getInventory().contains(stack))
			{
				String unlocalized = stack.getUnlocalizedName();
				if(stack.getItem() instanceof ItemPotion)
					unlocalized = PotionUtils.getPotionFromItem(stack).getNamePrefixed("potion.");
				
				EnumConsort type = ((ContainerConsortMerchant)event.getEntityPlayer().openContainer).inventory.getConsortType();
				String arg1 = I18n.format("entity.minestuck." + type.getName() + ".name");
				
				String name = "store."+unlocalized+".name";
				String tooltip = "store."+unlocalized+".tooltip";
				event.getToolTip().clear();
				if(I18n.hasKey(name))
					event.getToolTip().add(I18n.format(name, arg1));
				else event.getToolTip().add(stack.getDisplayName());
				if(I18n.hasKey(tooltip))
					event.getToolTip().add(I18n.format(tooltip, arg1));
			}
		}
	}
	
	@SubscribeEvent
	public static void onFogRender(EntityViewRenderEvent.FogDensity event)
	{
		if (event.getState().getBlock() == MinestuckBlocks.blockEnder)
		{
			event.setCanceled(true);
			event.setDensity(Float.MAX_VALUE);
			GlStateManager.setFog(GlStateManager.FogMode.EXP);
		}
	}
	
	@SubscribeEvent
	public static void onModelBake(ModelBakeEvent event)
	{
		Debug.info(event.getModelManager().getModel(new ModelResourceLocation(Minestuck.MODID+":alchemiter#facing=east,has_dowel=true,part=totem_pad")));
	}
	
	@SubscribeEvent
	public static void onBlockColorsInit(ColorHandlerEvent.Block e)
	{
		BlockColors blockColors = e.getBlockColors();
		blockColors.registerBlockColorHandler(new IBlockColor()
		{
			public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
			{
				int age = ((Integer)state.getValue(BlockStem.AGE)).intValue();
				int red = age * 32;
				int green = 255 - age * 8;
				int blue = age * 4;
				return red << 16 | green << 8 | blue;
			}
		}, MinestuckBlocks.strawberryStem);
	}

	/*@SubscribeEvent
	public static void onClientSendChat(ClientChatEvent event)
	{
		EntityPlayer player = Minecraft.getMinecraft().player;
		if(SylladexUtils.clientSideModus instanceof ChatModus)
			MinestuckChannelHandler.sendToServer(MinestuckPacket.makePacket(MinestuckPacket.Type.CHAT_MODUS_EJECT, event.getMessage(), true));
	}

	@SubscribeEvent
	public static void onReceiveChat(ClientChatReceivedEvent event)
	{
		EntityPlayer player = Minecraft.getMinecraft().player;
		if(SylladexUtils.clientSideModus instanceof ChatModus)
			MinestuckChannelHandler.sendToServer(MinestuckPacket.makePacket(MinestuckPacket.Type.CHAT_MODUS_EJECT, event.getMessage().getFormattedText(), false));
	}

	@SubscribeEvent
	public static void onKeyInput(InputEvent.KeyInputEvent event)	//This is only called during the game, when no gui is active
	{
		if(Keyboard.isKeyDown(MinestuckKeyHandler.instance.captchaKey.getKeyCode()) && Minecraft.getMinecraft().player.getHeldItemMainhand().isEmpty())
		{
			if(SylladexUtils.clientSideModus instanceof OuijaModus)
				MinestuckChannelHandler.sendToServer(MinestuckPacket.makePacket(MinestuckPacket.Type.CAPTCHA, PacketCaptchaDeck.GET, 0, false));
			if(SylladexUtils.clientSideModus instanceof WalletModus || SylladexUtils.clientSideModus instanceof CrystalBallModus)
				MinestuckChannelHandler.sendToServer(MinestuckPacket.makePacket(MinestuckPacket.Type.WALLET_CAPTCHA, Minecraft.getMinecraft().objectMouseOver));
		}
	}*/ // TODO: more modi
}
