package com.mraof.minestuck.capabilities.caps;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.ISylladexData;
import com.mraof.minestuck.captchalogue.ModusLayer;
import com.mraof.minestuck.captchalogue.ModusSettings;
import com.mraof.minestuck.captchalogue.modus.Modus;
import com.mraof.minestuck.captchalogue.sylladex.ISylladex;
import com.mraof.minestuck.captchalogue.sylladex.MultiSylladex;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.network.message.MessageSylladexData;
import com.mraof.minestuck.util.Debug;
import com.mraof.minestuck.util.SylladexUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public class SylladexData implements ISylladexData
{
	private EntityPlayer owner;

	private MultiSylladex sylladex;
	private boolean givenModus;

	@SubscribeEvent
	public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event)
	{
		EntityPlayer player = event.player;
		ISylladexData cap = player.getCapability(MinestuckCapabilities.SYLLADEX_DATA, null);

		if (cap.getSylladex() == null && MinestuckConfig.defaultModusTypes.length > 0 && !cap.wasGivenModus())
		{
			int index = player.world.rand.nextInt(MinestuckConfig.defaultModusTypes.length);
			Modus modus = Modus.REGISTRY.getValue(new ResourceLocation(MinestuckConfig.defaultModusTypes[index]));
			if (modus != null)
			{
				MultiSylladex newSylladex = ISylladex.newSylladex(player, new ModusLayer(-1, new ModusSettings(modus, new NBTTagCompound())));
				newSylladex.addCards(MinestuckConfig.initialModusSize);
				cap.setSylladex(newSylladex);
				MinestuckNetwork.sendTo(new MessageSylladexData(player), player);
			}
			else
				Debug.warnf("Couldn't create a modus by the name %s.", MinestuckConfig.defaultModusTypes[index]);
		}
		else if (SylladexUtils.getSylladex(player) != null)
			MinestuckNetwork.sendTo(new MessageSylladexData(player), player);
	}

	@Override
	public MultiSylladex getSylladex()
	{
		return sylladex;
	}

	public void setSylladex(MultiSylladex sylladex)
	{
		this.sylladex = sylladex;
		if (sylladex != null)
			givenModus = true;
	}

	@Override
	public boolean wasGivenModus()
	{
		return givenModus;
	}

	@Override
	public NBTTagCompound writeToNBT()
	{
		NBTTagCompound nbt = new NBTTagCompound();

		if (sylladex != null)
			nbt.setTag("Sylladex", sylladex.writeToNBT());

		nbt.setBoolean("GivenModus", givenModus);

		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		sylladex = ISylladex.readFromNBT(owner, nbt.getCompoundTag("Sylladex"));
		givenModus = nbt.getBoolean("GivenModus");
	}

	@Override
	public void setOwner(EntityPlayer owner)
	{
		this.owner = owner;
	}
}
