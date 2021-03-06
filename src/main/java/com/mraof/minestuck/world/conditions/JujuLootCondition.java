package com.mraof.minestuck.world.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.capabilities.caps.GameData;
import com.mraof.minestuck.item.MinestuckItems;
import net.minecraft.item.Item;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;

import java.util.Random;

public class JujuLootCondition implements LootCondition
{
	private final float chance;
	private final Item juju;

	public JujuLootCondition(float chance, Item juju)
	{
		this.chance = chance;
		this.juju = juju;
	}

	public float getChance()
	{
		return chance;
	}

	@Override
	public boolean testCondition(Random rand, LootContext context)
	{
		if (!GameData.hasJujuSpawned(juju) && rand.nextFloat() <= chance)
		{
			GameData.setJujuSpawned(juju, true);
			return true;
		}
		return false;
	}

	public static class Serializer extends LootCondition.Serializer<JujuLootCondition>
	{

		public Serializer()
		{
			super(new ResourceLocation(Minestuck.MODID, "juju"), JujuLootCondition.class);
		}

		@Override
		public void serialize(JsonObject json, JujuLootCondition value, JsonSerializationContext context)
		{
			json.addProperty("chance", value.chance);
			json.addProperty("juju", value.juju.getRegistryName().toString());
		}

		@Override
		public JujuLootCondition deserialize(JsonObject json, JsonDeserializationContext context)
		{
			float chance = JsonUtils.getFloat(json, "chance", 1);
			Item juju = Item.REGISTRY.getObject(new ResourceLocation(JsonUtils.getString(json, "juju", MinestuckItems.cueBall.getRegistryName().toString())));

			return new JujuLootCondition(chance, juju);
		}
	}
}

