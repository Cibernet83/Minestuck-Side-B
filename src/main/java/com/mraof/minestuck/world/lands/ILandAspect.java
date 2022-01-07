package com.mraof.minestuck.world.lands;

import com.mraof.minestuck.world.lands.gen.ChunkProviderLands;
import com.mraof.minestuck.world.lands.structure.IGateStructure;

import java.util.List;

public interface ILandAspect<A extends ILandAspect>
{

	/**
	 * Returns a string that represents a unique name for a land, Used in saving and loading land data.
	 *
	 * @return
	 */
	String getPrimaryName();

	/**
	 * Returns a list of strings used in giving a land a random name.
	 */
	String[] getNames();

	IGateStructure getGateStructure();

	void prepareChunkProvider(ChunkProviderLands chunkProvider);
	void prepareChunkProviderServer(ChunkProviderLands chunkProvider);

	List<A> getVariations();

	A getPrimaryVariant();
}
