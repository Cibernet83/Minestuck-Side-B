package com.mraof.minestuck.advancements;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mraof.minestuck.Minestuck;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TreeModusRootTrigger implements ICriterionTrigger<TreeModusRootTrigger.Instance>
{
	private static final ResourceLocation ID = new ResourceLocation(Minestuck.MODID, "tree_modus_root");
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
		if (listeners == null)
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
		if (listeners != null)
		{
			listeners.remove(listener);
			if (listeners.isEmpty())
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
		MinMaxBounds count = MinMaxBounds.deserialize(json.get("count"));
		return new Instance(count);
	}

	public void trigger(EntityPlayerMP player, int count)
	{
		Listeners listeners = listenersMap.get(player.getAdvancements());
		if (listeners != null)
			listeners.trigger(player, count);
	}

	public static class Instance extends AbstractCriterionInstance
	{
		private final MinMaxBounds count;

		public Instance(MinMaxBounds count)
		{
			super(ID);
			this.count = count;
		}

		public boolean test(int count)
		{
			return this.count.test(count);
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

		public void trigger(EntityPlayerMP player, int count)
		{
			List<Listener<Instance>> list = Lists.newArrayList();
			for (Listener<Instance> listener : listeners)
				if (listener.getCriterionInstance().test(count))
					list.add(listener);

			list.forEach((listener) -> listener.grantCriterion(playerAdvancements));
		}
	}
}