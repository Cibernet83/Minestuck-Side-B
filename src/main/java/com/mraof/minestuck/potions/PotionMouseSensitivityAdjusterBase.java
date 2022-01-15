package com.mraof.minestuck.potions;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.network.message.MessageMouseSensitivityPotion;
import com.mraof.minestuck.util.MinestuckUtils;
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

	protected PotionMouseSensitivityAdjusterBase(String name, boolean isBadEffectIn, int liquidColorIn)
	{
		super(name, isBadEffectIn, liquidColorIn);
	}

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

	@Override
	public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier)
	{
		super.removeAttributesModifiersFromEntity(entityLivingBaseIn, attributeMapIn, amplifier);
		applyNextSensitivity(entityLivingBaseIn, this);
	}

	@Override
	public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier)
	{
		super.applyAttributesModifiersToEntity(entityLivingBaseIn, attributeMapIn, amplifier);
		setMouseSensitivity(entityLivingBaseIn);
	}

	public void setMouseSensitivity(EntityLivingBase entityLivingBase)
	{
		if (entityLivingBase.world.isRemote)
			if (MinestuckUtils.isClientPlayer(entityLivingBase))
				Minecraft.getMinecraft().gameSettings.mouseSensitivity = getNewMouseSensitivity(prevMouseSensitivity);
			else ;
		else if (entityLivingBase instanceof EntityPlayer)
			MinestuckNetwork.sendTo(new MessageMouseSensitivityPotion(this), (EntityPlayer) entityLivingBase);
	}

	abstract protected float getNewMouseSensitivity(float currentSensitivity);

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

	public static void resetMouseSensitivity(EntityLivingBase entityLivingBase)
	{
		if (entityLivingBase.world.isRemote)
			if (MinestuckUtils.isClientPlayer(entityLivingBase))
				Minecraft.getMinecraft().gameSettings.mouseSensitivity = prevMouseSensitivity;
			else ;
		else if (entityLivingBase instanceof EntityPlayer)
			MinestuckNetwork.sendTo(new MessageMouseSensitivityPotion(null), (EntityPlayer) entityLivingBase);
	}
}
