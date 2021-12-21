package com.mraof.minestuck.badges;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.alchemy.GristHelper;
import com.mraof.minestuck.alchemy.GristSet;
import com.mraof.minestuck.alchemy.GristType;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.client.gui.GuiGodTierMeditation;
import com.mraof.minestuck.client.gui.IGristSelectable;
import com.mraof.minestuck.network.MinestuckChannelHandler;
import com.mraof.minestuck.network.MinestuckPacket;
import com.mraof.minestuck.tracker.MinestuckPlayerTracker;
import com.mraof.minestuck.util.IdentifierHandler;
import com.mraof.minestuck.util.MinestuckPlayerData;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BadgeGristHoard extends BadgeLevel implements IGristSelectable
{
	private final int REQ = 2000;

	public BadgeGristHoard()
	{
		super("hoardOfTheAlchemizer", 4);
	}

	@Override
	public boolean canUnlock(World world, EntityPlayer player)
	{
		for(GristType type : GristType.REGISTRY.getValuesCollection())
			if(!GristHelper.canAfford(MinestuckPlayerData.getGristSet(player), new GristSet(type, REQ)))
				return false;

		MinestuckChannelHandler.sendToPlayer(MinestuckPacket.makePacket(MinestuckPacket.Type.REQUEST_GRIST_HOARD), player);
		return false;
	}

	@Override
	public void onBadgeUnlocked(World world, EntityPlayer player)
	{
	}

	@Override
	public String getUnlockRequirements() {
		return new TextComponentTranslation(getUnlocalizedName()+".unlock", REQ).getUnformattedText();
	}

	@Override
	public String getDisplayTooltip()
	{
		String str = new TextComponentTranslation(getUnlocalizedName()+".tooltip.any").getFormattedText();

		try
		{
			return new TextComponentTranslation(getUnlocalizedName()+".tooltip", hasBadge() ? new TextComponentTranslation("grist.format", getGristHoard().getDisplayName()).getFormattedText() : str).getFormattedText();
		} catch (NoClassDefFoundError error)
		{
			return new TextComponentTranslation(getUnlocalizedName()+".tooltip", str).getUnformattedText();
		}

	}

	@SideOnly(Side.CLIENT)
	private boolean hasBadge()
	{
		return Minecraft.getMinecraft().player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).hasBadge(this);
	}
	@SideOnly(Side.CLIENT)
	private GristType getGristHoard()
	{
		return Minecraft.getMinecraft().player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).getGristHoard();
	}

	@Override
	public void select(GristType grist)
	{
		Minecraft mc = Minecraft.getMinecraft();

		IdentifierHandler.PlayerIdentifier pid = IdentifierHandler.encode(mc.player);
		for(GristType type : GristType.REGISTRY.getValuesCollection())
			if(type.getRegistryName().getResourceDomain().equals(Minestuck.MODID))
				GristHelper.decrease(pid, new GristSet(type, REQ));
		MinestuckPlayerTracker.updateGristCache(pid);

		mc.player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).setGristHoard(grist);
		MinestuckChannelHandler.sendToServer(MinestuckPacket.makePacket(MinestuckPacket.Type.SEND_GRIST_HOARD, grist));
		Minecraft.getMinecraft().currentScreen = new GuiGodTierMeditation(Minecraft.getMinecraft().player);
	}

	@Override
	public void cancel()
	{
		Minecraft.getMinecraft().currentScreen = new GuiGodTierMeditation(Minecraft.getMinecraft().player);
	}
}
