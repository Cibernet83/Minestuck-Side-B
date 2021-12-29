package com.mraof.minestuck.capabilities.api;

import com.mraof.minestuck.capabilities.IMinestuckCapabilityBase;
import com.mraof.minestuck.captchalogue.sylladex.MultiSylladex;
import net.minecraft.entity.player.EntityPlayer;

public interface ISylladexData extends IMinestuckCapabilityBase<EntityPlayer>
{
	MultiSylladex getSylladex();
	void setSylladex(MultiSylladex sylladex);
}
