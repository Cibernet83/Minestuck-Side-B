package com.mraof.minestuck.advancements;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.captchalogue.ModusLayer;
import com.mraof.minestuck.captchalogue.captchalogueable.CaptchalogueableItemStack;
import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.captchalogue.modus.Modus;
import com.mraof.minestuck.captchalogue.sylladex.MultiSylladex;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class CaptchalogueTrigger implements ICriterionTrigger<CaptchalogueTrigger.Instance>
{
	private static final ResourceLocation ID = new ResourceLocation(Minestuck.MODID, "captchalogue");
	private final Map<PlayerAdvancements, Listeners> listenersMap = Maps.newHashMap();
	
	@Override
	public ResourceLocation getId()
	{
		return ID;
	}
	
	@Override
	public void addListener(PlayerAdvancements playerAdvancementsIn, Listener<Instance> listener)
	{
		Listeners listeners = listenersMap.get(playerAdvancementsIn);
		if(listeners == null)
		{
			listeners = new Listeners(playerAdvancementsIn);
			listenersMap.put(playerAdvancementsIn, listeners);
		}
		listeners.add(listener);
	}
	
	@Override
	public void removeListener(PlayerAdvancements playerAdvancementsIn, Listener<Instance> listener)
	{
		Listeners listeners = listenersMap.get(playerAdvancementsIn);
		if(listeners != null)
		{
			listeners.remove(listener);
			if(listeners.isEmpty())
				listenersMap.remove(playerAdvancementsIn);
		}
	}
	
	@Override
	public void removeAllListeners(PlayerAdvancements playerAdvancementsIn)
	{
		listenersMap.remove(playerAdvancementsIn);
	}
	
	@Override
	public Instance deserializeInstance(JsonObject json, JsonDeserializationContext context)
	{
		String modus = null;
		if(json.has("modus"))
		{
			modus = json.get("modus").getAsString();
			if(Modus.REGISTRY.getValue(new ResourceLocation(modus)) != null)
				throw new IllegalArgumentException("Invalid modus "+modus);
		}
		ItemPredicate item = null;
		if(json.has("item"))
			item = ItemPredicate.deserialize(json.get("item"));
		MinMaxBounds count = MinMaxBounds.deserialize(json.get("count"));
		return new Instance(modus, item, count);
	}
	
	public void trigger(EntityPlayerMP player, MultiSylladex sylladex, ICaptchalogueable object)
	{
		Listeners listeners = listenersMap.get(player.getAdvancements());
		if(listeners != null)
		{
			Set<Modus> modusSet = new HashSet<>();
			for (ModusLayer modi : sylladex.getModusLayers())
				Collections.addAll(modusSet, modi.getModi());
			listeners.trigger(modusSet.toArray(new Modus[0]), object, sylladex.getTotalSlots() - sylladex.getFreeSlots());
		}
	}
	
	public static class Instance extends AbstractCriterionInstance
	{
		private final String modus;
		private final ItemPredicate item;
		private final MinMaxBounds count;
		public Instance(String modus, ItemPredicate item, MinMaxBounds count)
		{
			super(ID);
			this.modus = modus;
			this.item = item;
			this.count = count;
		}
		
		public boolean test(Modus[] modi, ICaptchalogueable object, int count)
		{
			return (this.modus == null || Arrays.stream(modi).anyMatch(modus -> modus.getRegistryName().toString().equals(this.modus))) && (this.item == null || (object instanceof CaptchalogueableItemStack && this.item.test(((CaptchalogueableItemStack)object).getStack()))) && this.count.test(count);
		}
	}
	
	static class Listeners
	{
		private final PlayerAdvancements playerAdvancements;
		private final Set<Listener<Instance>> listeners = Sets.newHashSet();
		
		public Listeners(PlayerAdvancements playerAdvancementsIn)
		{
			this.playerAdvancements = playerAdvancementsIn;
		}
		
		public boolean isEmpty()
		{
			return listeners.isEmpty();
		}
		
		public void add(Listener<Instance> listener)
		{
			this.listeners.add(listener);
		}
		
		public void remove(Listener<Instance> listener)
		{
			this.listeners.remove(listener);
		}
		
		public void trigger(Modus[] modi, ICaptchalogueable object, int count)
		{
			List<Listener<Instance>> list = Lists.newArrayList();
			for(Listener<Instance> listener : listeners)
				if(listener.getCriterionInstance().test(modi, object, count))
					list.add(listener);
			
			list.forEach((listener) -> listener.grantCriterion(playerAdvancements));
		}
	}
}