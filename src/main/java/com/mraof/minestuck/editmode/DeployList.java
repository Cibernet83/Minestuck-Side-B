package com.mraof.minestuck.editmode;

import com.mraof.minestuck.alchemy.Grist;
import com.mraof.minestuck.alchemy.GristSet;
import com.mraof.minestuck.alchemy.MinestuckGrist;
import com.mraof.minestuck.block.MinestuckBlocks;
import com.mraof.minestuck.item.ItemGTKit;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.item.block.ItemMiniSburbMachine;
import com.mraof.minestuck.network.skaianet.SburbConnection;
import com.mraof.minestuck.network.skaianet.SburbHandler;
import com.mraof.minestuck.util.AlchemyUtils;
import com.mraof.minestuck.util.MinestuckPlayerData;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class will be used to keep track of all deployable
 * items accessible by the server.
 *
 * @author kirderf1
 */
public class DeployList
{

	private static final ArrayList<DeployEntry> list = new ArrayList<DeployEntry>();
	@SideOnly(Side.CLIENT)
	private static List<ClientDeployEntry> clientDeployList;

	public static void registerItems()
	{
		registerItem("cruxtruder", new ItemStack(MinestuckBlocks.cruxtruder, 1, 0), new GristSet(), new GristSet(MinestuckGrist.build, 100), 0);
		registerItem("totem_lathe", new ItemStack(MinestuckBlocks.totemlathe[0], 1, 0), new GristSet(), new GristSet(MinestuckGrist.build, 100), 0);
		registerItem("artifact_card", new GristSet(), null, 0, connection -> !connection.enteredGame(),
				connection -> AlchemyUtils.createCard(SburbHandler.getEntryItem(connection.getClientIdentifier()), true));
		registerItem("alchemiter", new ItemStack(MinestuckBlocks.alchemiter[0], 1, 0), new GristSet(), new GristSet(MinestuckGrist.build, 100), 0);
		registerItem("punch_designix", 0, null, connection -> new ItemStack(MinestuckBlocks.punchDesignix, 1, 0),
				(isPrimary, connection) -> new GristSet(SburbHandler.getPrimaryGristType(connection.getClientIdentifier()), 4));
		/*registerItem("jumper_block_extension", new ItemStack(MinestuckBlocks.jumperBlockExtension[0]), new GristSet(GristType.build, 1000), 1);
		registerItem("punch_card_shunt", new ItemStack(MinestuckItems.shunt), new GristSet(GristType.build, 100), 1);
		registerItem("holopad", new ItemStack(MinestuckBlocks.holopad), new GristSet(GristType.build, 10000), 2);*/
		registerItem("holopad", new ItemStack(MinestuckBlocks.holopad), new GristSet(MinestuckGrist.build, 1000), 2);
		registerItem("gt_kit", new GristSet(MinestuckGrist.zillium, 50), 0, ItemGTKit::isAvailable, ItemGTKit::generateKit);
	}

	public static void registerItem(String name, ItemStack stack, GristSet cost, int tier)
	{
		registerItem(name, stack, cost, cost, tier);
	}

	/**
	 * Register the specific item as deployable.
	 *
	 * @param stack The item to be registered.
	 *              The itemstack can have nbt tags, with the exception of the display tag.
	 * @param cost1 How much it costs the first time deployed.
	 * @param cost2 How much it costs after the first times. Null if only deployable once.
	 *              First cost will always be used when not in hardmode.
	 * @param tier  The tier of the item; what connection position required in an unfinished chain to deploy.
	 *              All will be available to all players when the chain is complete.
	 */
	public static void registerItem(String name, ItemStack stack, GristSet cost1, GristSet cost2, int tier)
	{
		registerItem(name, cost1, cost2, tier, null, connection -> stack);
	}

	public static void registerItem(String name, GristSet cost, int tier, IAvailabilityCondition condition, IItemProvider item)
	{
		registerItem(name, tier, condition, item, (isPrimary, connection) -> cost);
	}

	public static void registerItem(String name, GristSet cost1, GristSet cost2, int tier, IAvailabilityCondition condition, IItemProvider item)
	{
		registerItem(name, tier, condition, item, (isPrimary, connection) -> isPrimary ? cost1 : cost2);
	}

	public static void registerItem(String name, int tier, IAvailabilityCondition condition, IItemProvider item, IGristCostProvider grist)
	{
		if (containsEntry(name))
			throw new IllegalStateException("Item stack already added to the deploy list: " + name);
		list.add(new DeployEntry(name, tier, condition, item, grist));
	}

	public static void removeEntry(String name)
	{
		Iterator<DeployEntry> iterator = list.iterator();
		while (iterator.hasNext())
			if (iterator.next().name.equals(name))
			{
				iterator.remove();
				return;
			}
	}

	public static List<DeployEntry> getItemList(SburbConnection c)
	{
		int tier = SburbHandler.availableTier(c.getClientIdentifier());
		ArrayList<DeployEntry> itemList = new ArrayList<>();
		for (DeployEntry entry : list)
			if (entry.tier <= tier && (entry.condition == null || entry.condition.test(c)))
				itemList.add(entry);

		return itemList;
	}

	public static boolean containsEntry(String name)
	{
		return getEntryForName(name) != null;
	}

	public static boolean containsItemStack(ItemStack stack, SburbConnection c)
	{
		return getEntryForItem(stack, c) != null;
	}

	public static DeployEntry getEntryForItem(ItemStack stack, SburbConnection c)
	{
		stack = cleanStack(stack);
		for (DeployEntry entry : list)
			if (ItemStack.areItemStacksEqual(stack, entry.item.apply(c)))
				return entry;
		return null;
	}

	@Nonnull
	private static ItemStack cleanStack(ItemStack stack)
	{
		if (stack.isEmpty())
			return ItemStack.EMPTY;
		stack = stack.copy();
		stack.setCount(1);
		if (stack.hasTagCompound() && stack.getTagCompound().hasNoTags())
			stack.setTagCompound(null);
		return stack;
	}

	public static int getOrdinal(String name)
	{
		for (int i = 0; i < list.size(); i++)
			if (list.get(i).name.equals(name))
				return i;
		return -1;
	}

	public static int getOrdinal(ItemStack stack, SburbConnection c)
	{
		stack = cleanStack(stack);
		for (int i = 0; i < list.size(); i++)
			if (ItemStack.areItemStacksEqual(list.get(i).item.apply(c), stack))
				return i;
		return -1;
	}

	public static String[] getNameList()
	{
		String[] str = new String[list.size()];
		for (int i = 0; i < list.size(); i++)
			str[i] = list.get(i).getName();
		return str;
	}

	public static int getEntryCount()
	{
		return list.size();
	}

	public static void applyConfigValues(boolean[] booleans)
	{
		if (booleans[0] != containsEntry("card_punched_card"))
		{
			if (booleans[0])
				registerItem("card_punched_card", AlchemyUtils.createCard(new ItemStack(MinestuckItems.captchaCard), true), new GristSet(MinestuckGrist.build, 25), null, 0);
			else removeEntry("card_punched_card");
		}
		if (booleans[1] != containsEntry("portable_cruxtruder"))
		{
			if (booleans[1])
			{
				registerItem("portable_cruxtruder", new GristSet(MinestuckGrist.build, 200), 1, null,
						connection -> ItemMiniSburbMachine.getCruxtruderWithColor(MinestuckPlayerData.getData(connection.getClientIdentifier()).color));
				registerItem("portable_punch_designix", new ItemStack(MinestuckBlocks.miniPunchDesignix, 1, 1), new GristSet(MinestuckGrist.build, 200), 1);
				registerItem("portable_totem_lathe", new ItemStack(MinestuckBlocks.miniTotemLathe, 1, 2), new GristSet(MinestuckGrist.build, 200), 1);
				registerItem("portable_alchemiter", new ItemStack(MinestuckBlocks.miniAlchemiter, 1, 3), new GristSet(MinestuckGrist.build, 300), 1);
			}
			else
			{
				removeEntry("portable_cruxtruder");
				removeEntry("portable_totem_lathe");
				removeEntry("portable_alchemiter");
				removeEntry("portable_punch_designix");
			}
		}
	}

	public static DeployEntry getEntryForName(String name)
	{
		for (DeployEntry entry : list)
			if (entry.name.equals(name))
				return entry;
		return null;
	}

	static NBTTagCompound getDeployListTag(SburbConnection c)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		NBTTagList tagList = new NBTTagList();
		nbt.setTag("l", tagList);
		int tier = SburbHandler.availableTier(c.getClientIdentifier());
		for (int i = 0; i < list.size(); i++)
		{
			DeployEntry entry = list.get(i);
			if (entry.isAvailable(c, tier))
			{
				ItemStack stack = entry.getItemStack(c);
				GristSet primary = entry.getPrimaryGristCost(c);
				GristSet secondary = entry.getSecondaryGristCost(c);
				NBTTagCompound tag = new NBTTagCompound();
				stack.writeToNBT(tag);
				tag.setInteger("i", i);
				NBTTagList listPrimary = new NBTTagList();
				for (Grist type : Grist.values())
				{
					if (primary.getGrist(type) == 0)
						continue;
					NBTTagCompound gristTag = new NBTTagCompound();
					gristTag.setString("id", String.valueOf(type.getRegistryName()));
					gristTag.setInteger("amount", primary.getGrist(type));
					listPrimary.appendTag(gristTag);
				}
				tag.setTag("primary", listPrimary);
				if (secondary != null)
				{
					NBTTagList listSecondary = new NBTTagList();
					for (Grist type : Grist.values())
					{
						if (secondary.getGrist(type) == 0)
							continue;
						NBTTagCompound gristTag = new NBTTagCompound();
						gristTag.setString("id", String.valueOf(type.getRegistryName()));
						gristTag.setInteger("amount", secondary.getGrist(type));
						listSecondary.appendTag(gristTag);
					}
					tag.setTag("secondary", listSecondary);
				}
				tagList.appendTag(tag);
			}
		}
		return nbt;
	}

	@SideOnly(Side.CLIENT)
	static void loadClientDeployList(NBTTagCompound nbt)
	{
		if (clientDeployList == null)
			clientDeployList = new ArrayList<>();
		else clientDeployList.clear();
		NBTTagList list = nbt.getTagList("l", 10);
		for (int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound tag = list.getCompoundTagAt(i);
			ClientDeployEntry entry = new ClientDeployEntry();
			entry.item = new ItemStack(tag);
			entry.index = tag.getInteger("i");
			entry.cost1 = new GristSet();
			for (NBTBase nbtBase : tag.getTagList("primary", 10))
			{
				NBTTagCompound gristTag = (NBTTagCompound) nbtBase;
				Grist type = Grist.getTypeFromString(gristTag.getString("id"));
				if (type != null)
					entry.cost1.setGrist(type, gristTag.getInteger("amount"));
			}
			if (tag.hasKey("secondary", 9))
			{
				entry.cost2 = new GristSet();
				for (NBTBase nbtBase : tag.getTagList("secondary", 10))
				{
					NBTTagCompound gristTag = (NBTTagCompound) nbtBase;
					Grist type = Grist.getTypeFromString(gristTag.getString("id"));
					if (type != null)
						entry.cost2.setGrist(type, gristTag.getInteger("amount"));
				}
			}
			else entry.cost2 = null;
			clientDeployList.add(entry);
		}
	}

	@SideOnly(Side.CLIENT)
	public static ClientDeployEntry getEntryClient(ItemStack stack)
	{
		stack = cleanStack(stack);
		for (ClientDeployEntry entry : clientDeployList)
			if (ItemStack.areItemStacksEqual(entry.item, stack))
				return entry;
		return null;
	}

	public interface IAvailabilityCondition
	{
		boolean test(SburbConnection connection);
	}

	//Clientside

	public interface IItemProvider
	{
		ItemStack apply(SburbConnection connection);
	}

	public interface IGristCostProvider
	{
		GristSet apply(boolean isPrimary, SburbConnection connection);
	}

	public static class DeployEntry
	{
		private String name;

		private int tier;
		private IAvailabilityCondition condition;
		private IItemProvider item;
		private IGristCostProvider grist;

		private DeployEntry(String name, int tier, IAvailabilityCondition condition, IItemProvider item, IGristCostProvider grist)
		{
			this.name = name;
			this.tier = tier;
			this.condition = condition;
			this.item = item;
			this.grist = grist;
		}

		public String getName()
		{
			return name;
		}

		public int getTier()
		{
			return tier;
		}

		public boolean isAvailable(SburbConnection c, int tier)
		{
			return (condition == null || condition.test(c)) && this.tier <= tier;
		}

		public ItemStack getItemStack(SburbConnection c)
		{
			return item.apply(c).copy();
		}

		public GristSet getPrimaryGristCost(SburbConnection c)
		{
			return grist.apply(true, c);
		}

		public GristSet getSecondaryGristCost(SburbConnection c)
		{
			return grist.apply(false, c);
		}
	}

	@SideOnly(Side.CLIENT)
	public static class ClientDeployEntry
	{
		private ItemStack item;
		private GristSet cost1;
		private GristSet cost2;
		private int index;

		public GristSet getPrimaryCost()
		{
			return cost1;
		}

		public GristSet getSecondaryCost()
		{
			return cost2;
		}

		public int getIndex()
		{
			return index;
		}
	}
}