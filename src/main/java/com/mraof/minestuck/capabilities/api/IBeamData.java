package com.mraof.minestuck.capabilities.api;

import com.mraof.minestuck.capabilities.IMinestuckCapabilityBase;
import com.mraof.minestuck.capabilities.Beam;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.UUID;

public interface IBeamData extends IMinestuckCapabilityBase<World>
{
	ArrayList<Beam> getBeams();
	Beam getBeam(UUID beamId);
	void addBeam(Beam beam);
	void tickBeams();
}
