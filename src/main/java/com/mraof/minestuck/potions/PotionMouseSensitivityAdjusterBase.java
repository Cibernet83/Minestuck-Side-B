package com.mraof.minestuck.potions;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.network.MinestuckChannelHandler;
import com.mraof.minestuck.network.MinestuckPacket;
import com.mraof.minestuck.util.MSGTUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public abstract class PotionMouseSensitivityAdjusterBase extends MSPotionBase
{
	@SideOnly(Side.CLIENT)
	private static float prevMouseSensitivity;

	protected PotionMouseSensitivityAdjusterBase(String name, boolean isBadEffectIn, int liquidColorIn) {
		super(name, isBadEffectIn, liquidColorIn);
	}

	@Override
	public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier)
	{
		super.applyAttributesModifiersToEntity(entityLivingBaseIn, attributeMapIn, amplifier);
		setMouseSensitivity(entityLivingBaseIn);
	}

	@Override
	public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier)
	{
		super.removeAttributesModifiersFromEntity(entityLivingBaseIn, attributeMapIn, amplifier);
		applyNextSensitivity(entityLivingBaseIn, this);
	}

	public void setMouseSensitivity(EntityLivingBase entityLivingBase)
	{
		if (entityLivingBase.world.isRemote)
			if (MSGTUtils.isClientPlayer(entityLivingBase))
				Minecraft.getMinecraft().gameSettings.mouseSensitivity = getNewMouseSensitivity(prevMouseSensitivity);
			else;
		else
			if (entityLivingBase instanceof EntityPlayer)
				MinestuckChannelHandler.sendToPlayer(MinestuckPacket.makePacket(MinestuckPacket.Type.SET_MOUSE_SENSITIVITY, getIdFromPotion(this)), (EntityPlayer) entityLivingBase);
	}

	public static void resetMouseSensitivity(EntityLivingBase entityLivingBase)
	{
		if (entityLivingBase.world.isRemote)
			if (MSGTUtils.isClientPlayer(entityLivingBase))
				Minecraft.getMinecraft().gameSettings.mouseSensitivity = prevMouseSensitivity;
			else;
		else
			if (entityLivingBase instanceof EntityPlayer)
				MinestuckChannelHandler.sendToPlayer(MinestuckPacket.makePacket(MinestuckPacket.Type.SET_MOUSE_SENSITIVITY, -1), (EntityPlayer) entityLivingBase);
	}

	public static void applyNextSensitivity(EntityLivingBase entityLivingBase, @Nullable PotionMouseSensitivityAdjusterBase removingPotion)
	{
		for (PotionEffect potionEffect : entityLivingBase.getActivePotionEffects())
			if (potionEffect.getPotion() instanceof PotionMouseSensitivityAdjusterBase && !potionEffect.getPotion().equals(removingPotion))
			{
				((PotionMouseSensitivityAdjusterBase) potionEffect.getPotion()).setMouseSensitivity(entityLivingBase);
				return;
			}
		resetMouseSensitivity(entityLivingBase);
	}

	abstract protected float getNewMouseSensitivity(float currentSensitivity);

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onClientConnectedToServer(FMLNetworkEvent.ClientConnectedToServerEvent event)
	{
		prevMouseSensitivity = Minecraft.getMinecraft().gameSettings.mouseSensitivity;
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onClientDisconnectedFromServer(FMLNetworkEvent.ClientDisconnectionFromServerEvent event)
	{
		Minecraft.getMinecraft().gameSettings.mouseSensitivity = prevMouseSensitivity;
	}

	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
	{
		applyNextSensitivity(event.player, null);
	}

	@SubscribeEvent
	public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
	{
		if (!event.player.world.isRemote)
			resetMouseSensitivity(event.player);
	}
}
