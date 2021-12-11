package com.cibernet.minestuckgodtier.capabilities.api;

import com.cibernet.minestuckgodtier.capabilities.GodKeyStates;
import com.cibernet.minestuckgodtier.capabilities.MSGTICapabilityBase;
import net.minecraft.entity.player.EntityPlayer;

public interface IGodKeyStates extends MSGTICapabilityBase<EntityPlayer>
{
	void updateKeyState(GodKeyStates.Key key, boolean pressed);
	int getKeyTime(GodKeyStates.Key key);
	GodKeyStates.KeyState getKeyState(GodKeyStates.Key key);
	void tickKeyStates();
	void resetKeyStates();
}
