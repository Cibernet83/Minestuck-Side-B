package com.mraof.minestuck.world;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.util.Debug;
import com.mraof.minestuck.world.lands.LandAspectRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

import java.util.HashMap;
import java.util.function.BiConsumer;

public class MinestuckDimensionHandler
{
	
	public static int skaiaProviderTypeId;
	public static int skaiaDimensionId;
	public static int landProviderTypeId;
	public static int landDimensionIdStart;
	public static int biomeIdStart;
	
	private static Exception unregisterTrace;
	private static HashMap<Integer, LandAspectRegistry.AspectCombination> lands = new HashMap<>();
	private static HashMap<Integer, BlockPos> spawnpoints = new HashMap<>();
	
	public static DimensionType landDimensionType;
	public static DimensionType skaiaDimensionType;
	
	public static void register()
	{
		//register world generators
		landDimensionType = DimensionType.register("The Medium", "_medium", landProviderTypeId, WorldProviderLands.class, MinestuckConfig.keepDimensionsLoaded);
		skaiaDimensionType = DimensionType.register("Skaia", "_skaia", skaiaProviderTypeId, WorldProviderSkaia.class, false);
		
		DimensionManager.registerDimension(skaiaDimensionId, skaiaDimensionType);
	}
	
	public static void unregisterDimensions()
	{
		for (int b : lands.keySet())
			if(DimensionManager.isDimensionRegistered(b))
				DimensionManager.unregisterDimension(b);
		
		if(Minestuck.isServerRunning)
			try
			{
				throw new Exception();
			}
			catch(Exception e)
			{
				unregisterTrace = e;
			}
		lands.clear();
		spawnpoints.clear();
		GateHandler.gateData.clear();
	}
	
	public static void saveData(NBTTagCompound nbt)
	{
		if(unregisterTrace != null)
			throw new IllegalStateException("Saving minestuck dimension data after unregistering dimensions. This is bad!", unregisterTrace);
		
		NBTTagList list = new NBTTagList();
		lands.forEach((Integer dim, LandAspectRegistry.AspectCombination type) -> {
			NBTTagCompound tagCompound = new NBTTagCompound();
			tagCompound.setInteger("dimID", dim);
			tagCompound.setString("type", "land");
			tagCompound.setString("aspect1", type.aspectTerrain.getPrimaryName());
			tagCompound.setString("aspect2", type.aspectTitle.getPrimaryName());
			BlockPos spawn = spawnpoints.get(dim);
			tagCompound.setInteger("spawnX", spawn.getX());
			tagCompound.setInteger("spawnY", spawn.getY());
			tagCompound.setInteger("spawnZ", spawn.getZ());
			list.appendTag(tagCompound);
		});
		GateHandler.saveData(list);
		nbt.setTag("dimensionData", list);
	}
	
	public static void loadData(NBTTagCompound nbt)
	{
		unregisterTrace = null;
		if(nbt == null)
			return;
		
		NBTTagList list = nbt.getTagList("dimensionData", new NBTTagCompound().getId());
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound tagCompound = list.getCompoundTagAt(i);
			int dim = tagCompound.getInteger("dimID");
			String type = tagCompound.getString("type");
			if(type.equals("land"))
			{
				String name1 = tagCompound.getString("aspect1");
				String name2 = tagCompound.getString("aspect2");
				LandAspectRegistry.AspectCombination aspects = new LandAspectRegistry.AspectCombination(LandAspectRegistry.fromNameTerrain(name1), LandAspectRegistry.fromNameTitle(name2));
				BlockPos spawn = new BlockPos(tagCompound.getInteger("spawnX"), tagCompound.getInteger("spawnY"), tagCompound.getInteger("spawnZ"));
				
				lands.put(dim, aspects);
				spawnpoints.put(dim, spawn);
				DimensionManager.registerDimension(dim, landDimensionType);
				Debug.debugf("Loaded minestuck info on land dimension %d", dim);
			}
			else
				Debug.warnf("Found data on a non-land dimension in the minestuck data (%d). Are you running a newer world on an older version?", dim);
		}
		Debug.debugf("Loaded minestuck data for %d land dimensions out of %d entries.", lands.size(), list.tagCount());
		GateHandler.loadData(list);
	}
	
	public static void registerLandDimension(int dimensionId, LandAspectRegistry.AspectCombination landAspects)
	{
		if(landAspects == null)
			throw new IllegalArgumentException("May not register a land aspect combination that is null");
		if(!lands.containsKey(dimensionId) && !DimensionManager.isDimensionRegistered(dimensionId))
		{
			lands.put(dimensionId, landAspects);
			DimensionManager.registerDimension(dimensionId, landDimensionType);
		}
		else Debug.warnf("Did not register land dimension with id %d. Appears to already be registered.", dimensionId);
	}
	
	public static LandAspectRegistry.AspectCombination getAspects(int dimensionId)
	{
		LandAspectRegistry.AspectCombination aspects = lands.get(dimensionId);
		
		if(aspects == null)
			Debug.warnf("Tried to access land aspect for dimension %d, but didn't find any!", dimensionId);
		
		return aspects;
	}
	
	public static boolean isLandDimension(int dimensionId)
	{
		return lands.containsKey(dimensionId);
	}
	
	public static boolean isSkaia(int dimensionId)
	{
		return dimensionId == skaiaDimensionId;
	}
	
	public static void forEachLand(BiConsumer<? super Integer, ? super LandAspectRegistry.AspectCombination> action)
	{
		lands.forEach(action);
	}
	
	public static void onLandPacket(HashMap<Integer, Tuple<LandAspectRegistry.AspectCombination, BlockPos>> newLands)
	{
		if(Minestuck.isServerRunning)
			return;
		unregisterTrace = null;
		lands.clear();
		spawnpoints.clear();

		newLands.forEach((Integer dim, Tuple<LandAspectRegistry.AspectCombination, BlockPos> data) -> {
			lands.put(dim, data.getFirst());
			spawnpoints.put(dim, data.getSecond());
		});
		
		for(int dim : lands.keySet())
			if(!DimensionManager.isDimensionRegistered(dim))
				DimensionManager.registerDimension(dim, landDimensionType);
	}
	
	public static BlockPos getSpawn(int dim)
	{
		return spawnpoints.get(dim);
	}
	
	public static void setSpawn(int dim, BlockPos spawnpoint)
	{
		spawnpoints.put(dim, spawnpoint);
	}
}
