package com.mraof.minestuck.enchantments;

import com.mraof.minestuck.Minestuck;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public class MinestuckEnchantments
{
	public static final Enchantment SUPERPUNCH = new EnchantmentGauntlet(Enchantment.Rarity.RARE, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});

	@SubscribeEvent
	public static void register(RegistryEvent.Register<Enchantment> event)
	{
		IForgeRegistry<Enchantment> registry = event.getRegistry();

		registry.register(SUPERPUNCH.setRegistryName("superpunch"));
	}
}