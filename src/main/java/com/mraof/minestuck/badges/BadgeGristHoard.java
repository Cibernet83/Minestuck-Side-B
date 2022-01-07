package com.mraof.minestuck.badges;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.alchemy.Grist;
import com.mraof.minestuck.alchemy.GristHelper;
import com.mraof.minestuck.alchemy.GristSet;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.client.gui.GuiGodTierMeditation;
import com.mraof.minestuck.client.gui.IGristSelectable;
import com.mraof.minestuck.client.gui.MinestuckGuiHandler;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.network.message.MessageOpenGui;
import com.mraof.minestuck.network.message.MessageGristHoardRequest;
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
		for(Grist type : Grist.REGISTRY.getValuesCollection())
			if(!GristHelper.canAfford(MinestuckPlayerData.getGristSet(player), new GristSet(type, REQ)))
				return false;

		MinestuckNetwork.sendTo(new MessageOpenGui(MinestuckGuiHandler.GuiId.HOARD_SELECTOR), player);
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
	@SideOnly(Side.CLIENT)
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
	private Grist getGristHoard()
	{
		return Minecraft.getMinecraft().player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).getGristHoard();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void select(Grist grist)
	{
		Minecraft mc = Minecraft.getMinecraft();

		IdentifierHandler.PlayerIdentifier pid = IdentifierHandler.encode(mc.player);
		for(Grist type : Grist.REGISTRY.getValuesCollection())
			if(type.getRegistryName().getResourceDomain().equals(Minestuck.MODID))
				GristHelper.decrease(pid, new GristSet(type, REQ));
		MinestuckPlayerTracker.updateGristCache(pid);

		mc.player.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).setGristHoard(grist);
		MinestuckNetwork.sendToServer(new MessageGristHoardRequest(grist));
		Minecraft.getMinecraft().currentScreen = new GuiGodTierMeditation(Minecraft.getMinecraft().player);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void cancel()
	{
		Minecraft.getMinecraft().currentScreen = new GuiGodTierMeditation(Minecraft.getMinecraft().player);
	}
}
