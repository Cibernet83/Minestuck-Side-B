package com.mraof.minestuck.alchemy;

import com.mraof.minestuck.editmode.EditData;
import com.mraof.minestuck.editmode.ServerEditHandler;
import com.mraof.minestuck.network.skaianet.SburbConnection;
import com.mraof.minestuck.network.skaianet.SkaianetHandler;
import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.util.Debug;
import com.mraof.minestuck.util.IdentifierHandler;
import com.mraof.minestuck.util.IdentifierHandler.PlayerIdentifier;
import com.mraof.minestuck.util.MinestuckPlayerData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class GristHelper {
	private static Random random = new Random();
	private static final boolean SHOULD_OUTPUT_GRIST_CHANGES = MinestuckConfig.showGristChanges;
	
	public static HashMap<Grist, ArrayList<Grist>> secondaryGristMap;

	static
	{
		secondaryGristMap = new HashMap<>();
		for(Grist type : Grist.values())
			secondaryGristMap.put(type, new ArrayList<>());
		secondaryGristMap.get(MinestuckGrist.amber).add(MinestuckGrist.rust);
		secondaryGristMap.get(MinestuckGrist.amber).add(MinestuckGrist.sulfur);
		secondaryGristMap.get(MinestuckGrist.amethyst).add(MinestuckGrist.quartz);
		secondaryGristMap.get(MinestuckGrist.amethyst).add(MinestuckGrist.garnet);
		secondaryGristMap.get(MinestuckGrist.caulk).add(MinestuckGrist.iodine);
		secondaryGristMap.get(MinestuckGrist.caulk).add(MinestuckGrist.chalk);
		secondaryGristMap.get(MinestuckGrist.chalk).add(MinestuckGrist.shale);
		secondaryGristMap.get(MinestuckGrist.chalk).add(MinestuckGrist.marble);
		secondaryGristMap.get(MinestuckGrist.cobalt).add(MinestuckGrist.ruby);
		secondaryGristMap.get(MinestuckGrist.cobalt).add(MinestuckGrist.amethyst);
		secondaryGristMap.get(MinestuckGrist.garnet).add(MinestuckGrist.ruby);
		secondaryGristMap.get(MinestuckGrist.garnet).add(MinestuckGrist.gold);
		secondaryGristMap.get(MinestuckGrist.iodine).add(MinestuckGrist.amber);
		secondaryGristMap.get(MinestuckGrist.iodine).add(MinestuckGrist.chalk);
		secondaryGristMap.get(MinestuckGrist.marble).add(MinestuckGrist.caulk);
		secondaryGristMap.get(MinestuckGrist.marble).add(MinestuckGrist.amethyst);
		secondaryGristMap.get(MinestuckGrist.mercury).add(MinestuckGrist.cobalt);
		secondaryGristMap.get(MinestuckGrist.mercury).add(MinestuckGrist.rust);
		secondaryGristMap.get(MinestuckGrist.quartz).add(MinestuckGrist.marble);
		secondaryGristMap.get(MinestuckGrist.quartz).add(MinestuckGrist.uranium);
		secondaryGristMap.get(MinestuckGrist.ruby).add(MinestuckGrist.quartz);
		secondaryGristMap.get(MinestuckGrist.ruby).add(MinestuckGrist.diamond);
		secondaryGristMap.get(MinestuckGrist.rust).add(MinestuckGrist.shale);
		secondaryGristMap.get(MinestuckGrist.rust).add(MinestuckGrist.garnet);
		secondaryGristMap.get(MinestuckGrist.shale).add(MinestuckGrist.mercury);
		secondaryGristMap.get(MinestuckGrist.shale).add(MinestuckGrist.tar);
		secondaryGristMap.get(MinestuckGrist.sulfur).add(MinestuckGrist.iodine);
		secondaryGristMap.get(MinestuckGrist.sulfur).add(MinestuckGrist.tar);
		secondaryGristMap.get(MinestuckGrist.tar).add(MinestuckGrist.amber);
		secondaryGristMap.get(MinestuckGrist.tar).add(MinestuckGrist.cobalt);
		
		secondaryGristMap.get(MinestuckGrist.uranium).add(MinestuckGrist.diamond);
		secondaryGristMap.get(MinestuckGrist.diamond).add(MinestuckGrist.gold);
		secondaryGristMap.get(MinestuckGrist.gold).add(MinestuckGrist.uranium);
	}

	
	/**
	 * Returns a random grist type. Used for creating randomly aligned underlings.
	 */
	public static Grist getPrimaryGrist()
	{
		while (true)
		{
			Grist[] grists = Grist.REGISTRY.getValuesCollection().toArray(new Grist[0]);
			Grist randGrist = grists[random.nextInt(grists.length)];
			if (randGrist.getRarity() > random.nextFloat() && randGrist != MinestuckGrist.artifact)
				return randGrist;
		}
	}
	
	/**
	 * Returns a secondary grist type based on primary grist
	 */
	public static Grist getSecondaryGrist(Grist primary)
	{
		if(secondaryGristMap.get(primary).size() != 0 && random.nextInt(secondaryGristMap.get(primary).size() * 2) != 0)
			return secondaryGristMap.get(primary).get(random.nextInt(secondaryGristMap.get(primary).size()));
		else return primary;
	}

	
	/**
	 * Returns a GristSet representing the drops from an underling, given the underling's type and a static loot multiplier.
	 */
	public static GristSet getRandomDrop(Grist primary, double multiplier)
	{
		if(primary == null)
		{
			Debug.warn("Got an underling grist drop call with a null grist type. (multiplier:"+multiplier+")");
			return null;
		}
		
		GristSet set = new GristSet();
		set.addGrist(MinestuckGrist.build, (int)(2*multiplier + random.nextDouble()*18*multiplier));
		set.addGrist(primary, (int)(1*multiplier + random.nextDouble()*9*multiplier));
		set.addGrist(getSecondaryGrist(primary), (int)(0.5*multiplier + random.nextDouble()*4*multiplier));
		return set;
		
	}
	
	/**
	 * A shortened statement to obtain a certain grist count.
	 * Uses the encoded version of the username!
	 */
	public static int getGrist(PlayerIdentifier player, Grist type)
	{
		return MinestuckPlayerData.getGristSet(player).getGrist(type);
	}
	
	public static boolean canAfford(PlayerIdentifier player, @Nonnull ItemStack stack, boolean clientSide)
	{
		return canAfford(clientSide ? MinestuckPlayerData.getClientGrist() : MinestuckPlayerData.getGristSet(player), GristRegistry.getGristConversion(stack));
	}
	
	public static boolean canAfford(GristSet base, GristSet cost) {
		if (base == null || cost == null) {return false;}
		Map<Grist, Integer> reqs = cost.getMap();
		
		if (reqs != null) {
			for (Entry<Grist, Integer> pairs : reqs.entrySet())
			{
				Grist type = pairs.getKey();
				int need = pairs.getValue();
				int have = base.getGrist(type);

				if (need > have) return false;
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Uses the encoded version of the username!
	 */
	public static void decrease(PlayerIdentifier player, GristSet set)
	{
		Map<Grist, Integer> reqs = set.getMap();
		if (reqs != null) {
			for (Entry<Grist, Integer> pairs : reqs.entrySet())
			{
				setGrist(player, pairs.getKey(), getGrist(player, pairs.getKey()) - pairs.getValue());
				notifyServer(player, pairs.getKey().getDisplayName(), pairs.getValue(), "spent");
			}
		}
	}
	
	public static void setGrist(PlayerIdentifier player, Grist type, int i)
	{
		MinestuckPlayerData.getGristSet(player).setGrist(type, i);
	}
	
	/**
	 * This method will probably be used somewhere in the future.
	 */
	public static int getGristValue(GristSet set) {
		int i = 0;
		for(Grist type : Grist.values()) {
			if(type.equals(MinestuckGrist.build))
				i += set.getGrist(type);
			else if(type.getRarity() == 0.0F)
				i += set.getGrist(type)*15;
			else i += set.getGrist(type)*type.getPower();
		}
		return i;
	}
	
	public static boolean increase(PlayerIdentifier player, GristSet set)
	{
		if(player == null || set == null)
			return false;
		Map<Grist, Integer> reqs = set.getMap();
		if (reqs != null)
		{
			for (Entry<Grist, Integer> pairs : reqs.entrySet())
			{
				setGrist(player, pairs.getKey(), getGrist(player, pairs.getKey()) + pairs.getValue());
				notify(player, pairs.getKey().getDisplayName(), pairs.getValue(), "gained");
			}
		}
		return true;
	}
	
	private static void notify(PlayerIdentifier player, String type, Integer difference, String action)
	{
		if(SHOULD_OUTPUT_GRIST_CHANGES)
		{
			if (player != null)
			{
				EntityPlayerMP client = player.getPlayer();
				if(client != null)
				{
					//"true" sends the message to the action bar (like bed messages), while "false" sends it to the chat.
					player.getPlayer().sendStatusMessage(new TextComponentTranslation("You " + action + " " + difference + " " + type + " grist."), true);
				}
			}
		}
	}
	
	private static void notifyServer(PlayerIdentifier player, String type, Integer difference, String action)
	{
		SburbConnection sc = SkaianetHandler.getClientConnection(player);
		if (sc==null) return;
		EditData ed = ServerEditHandler.getData(sc);
		if(ed==null) return;
		notify(IdentifierHandler.encode(ed.getEditor()), type, difference, action);
	}
	
}
