package com.mraof.minestuck.world.lands.gen;

import net.minecraft.world.chunk.ChunkPrimer;

public interface ILandTerrainGen
{
	ChunkPrimer createChunk(int chunkX, int chunkZ);
}