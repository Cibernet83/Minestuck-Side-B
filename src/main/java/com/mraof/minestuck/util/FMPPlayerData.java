package com.mraof.minestuck.util;

import com.mraof.minestuck.editmode.ServerEditHandler;
import com.mraof.minestuck.event.CommonEventHandler;
import com.mraof.minestuck.network.skaianet.SkaianetHandler;
import com.mraof.minestuck.tileentity.TileEntityTransportalizer;
import com.mraof.minestuck.world.MinestuckDimensionHandler;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FMPPlayerData
{

	@SubscribeEvent
	public void onWorldSave(WorldEvent.Save event)
	{
		if (event.getWorld().provider.getDimension() != 0)    //Only save one time each world-save instead of one per dimension each world-save.
			return;

		File dataFile = event.getWorld().getSaveHandler().getMapFileFromName("MinestuckData");
		if (dataFile != null)
		{
			NBTTagCompound nbt = new NBTTagCompound();

			ServerEditHandler.saveData(nbt);    //Keep this before skaianet
			MinestuckDimensionHandler.saveData(nbt);
			TileEntityTransportalizer.saveTransportalizers(nbt);
			SkaianetHandler.saveData(nbt);
			MinestuckPlayerData.writeToNBT(nbt);


			NBTTagList list = new NBTTagList();
			for (PostEntryTask task : CommonEventHandler.tickTasks)
				list.appendTag(task.toNBTTagCompound());
			if (list.tagCount() > 0)
				nbt.setTag("tickTasks", list);

			try
			{
				CompressedStreamTools.writeCompressed(nbt, new FileOutputStream(dataFile));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	}

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event)
	{
		if (event.getWorld().provider.getDimension() != 0 || event.getWorld().isRemote)
			return;
		ISaveHandler saveHandler = event.getWorld().getSaveHandler();
		File dataFile = saveHandler.getMapFileFromName("MinestuckData");
		if (dataFile != null && dataFile.exists())
		{
			NBTTagCompound nbt = null;
			try
			{
				nbt = CompressedStreamTools.readCompressed(new FileInputStream(dataFile));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			if (nbt != null)
			{
				ServerEditHandler.loadData(nbt);
				MinestuckDimensionHandler.loadData(nbt);
				SkaianetHandler.loadData(nbt.getCompoundTag("skaianet"));
				MinestuckPlayerData.readFromNBT(nbt);
				TileEntityTransportalizer.loadTransportalizers(nbt.getCompoundTag("transportalizers"));

				CommonEventHandler.tickTasks.clear();
				if (nbt.hasKey("tickTasks", 9))
				{
					NBTTagList list = nbt.getTagList("tickTasks", 10);
					for (int i = 0; i < list.tagCount(); i++)
						CommonEventHandler.tickTasks.add(new PostEntryTask(list.getCompoundTagAt(i)));
				}

				return;
			}
		}

		CommonEventHandler.tickTasks.clear();
		MinestuckDimensionHandler.loadData(null);
		SkaianetHandler.loadData(null);
		MinestuckPlayerData.readFromNBT(null);
		ServerEditHandler.loadData(null);
	}
}
