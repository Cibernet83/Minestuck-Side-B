package com.cibernet.minestuckgodtier.entities;

import com.cibernet.minestuckgodtier.MinestuckGodTier;
import com.cibernet.minestuckgodtier.client.renderer.RenderHopeGolem;
import com.cibernet.minestuckgodtier.items.MSGTItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MSGTEntities 
{
	public static int currentEntityIdOffset = 0;
	
	public static void registerEntities()
	{
		registerEntity(EntityHopeGolem.class, "hope_golem");
		registerEntity(EntityLocatorEye.class, "denizen_eye");
	}

	@SideOnly(Side.CLIENT)
	public static void bindRenderers()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityHopeGolem.class, (manager) -> new RenderHopeGolem(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityLocatorEye.class, (manager) -> new RenderSnowball<>(manager, MSGTItems.denizenEye, Minecraft.getMinecraft().getRenderItem()));
	}


	public static void registerEntity(Class<? extends Entity> entityClass, String name) {
		registerEntity(entityClass, name, name, 80, 3, true);
	}

	public static void registerEntity(Class<? extends Entity> entityClass, String name, int eggPrimary, int eggSecondary) {
		registerEntity(entityClass, name, name, 80, 3, true, eggPrimary, eggSecondary);
	}

	public static void registerEntity(Class<? extends Entity> entityClass, String name, String registryName) {
		registerEntity(entityClass, name, registryName, 80, 3, true);
	}

	public static void registerEntity(Class<? extends Entity> entityClass, String name, String registryName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		EntityRegistry.registerModEntity(new ResourceLocation(MinestuckGodTier.MODID, registryName), entityClass, MinestuckGodTier.MODID +":"+ name, currentEntityIdOffset, MinestuckGodTier.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
		++currentEntityIdOffset;
	}

	public static void registerEntity(Class<? extends Entity> entityClass, String name, String registryName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int eggPrimary, int eggSecondary) {
		EntityRegistry.registerModEntity(new ResourceLocation(MinestuckGodTier.MODID, registryName), entityClass, MinestuckGodTier.MODID + name, currentEntityIdOffset, MinestuckGodTier.instance, trackingRange, updateFrequency, sendsVelocityUpdates, eggPrimary, eggSecondary);
		++currentEntityIdOffset;
	}
}
