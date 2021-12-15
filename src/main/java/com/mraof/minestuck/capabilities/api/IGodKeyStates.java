package com.mraof.minestuck.capabilities.api;

import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.capabilities.IMinestuckCapabilityBase;
import net.minecraft.entity.player.EntityPlayer;

public interface IGodKeyStates extends IMinestuckCapabilityBase<EntityPlayer>
{
	void updateKeyState(GodKeyStates.Key key, boolean pressed);
	int getKeyTime(GodKeyStates.Key key);
	GodKeyStates.KeyState getKeyState(GodKeyStates.Key key);
	void tickKeyStates();
	void resetKeyStates();
}
