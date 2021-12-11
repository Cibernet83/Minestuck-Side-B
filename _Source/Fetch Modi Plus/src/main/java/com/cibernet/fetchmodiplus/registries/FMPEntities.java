package com.cibernet.fetchmodiplus.registries;

import com.cibernet.fetchmodiplus.FetchModiPlus;
import com.cibernet.fetchmodiplus.client.renderer.CruxiteSlimeRenderer;
import com.cibernet.fetchmodiplus.client.renderer.ThrowableRenderFactory;
import com.cibernet.fetchmodiplus.entities.*;
import com.mraof.minestuck.client.renderer.entity.RenderEntityMinestuck;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FMPEntities
{
	public static int currentEntityIdOffset = 0;
	
	public static void registerEntities()
	{
		registerEntity(CruxiteSlimeEntity.class, "cruxite_slime");
		registerEntity(EightBallEntity.class, "eight_ball");
		registerEntity(CrystalEightBallEntity.class, "crystal_eight_ball");
		registerEntity(OperandiEightBallEntity.class, "operandi_eight_ball");
		registerEntity(OperandiSplashPotionEntity.class, "operandi_splash_potion");
	}
	
	@SideOnly(Side.CLIENT)
	public static void bindRenderers()
	{
		RenderingRegistry.registerEntityRenderingHandler(CruxiteSlimeEntity.class, new CruxiteSlimeRenderer.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EightBallEntity.class, new ThrowableRenderFactory<>(FMPItems.eightBall));
		RenderingRegistry.registerEntityRenderingHandler(CrystalEightBallEntity.class, new ThrowableRenderFactory<>(FMPItems.crystalEightBall));
		RenderingRegistry.registerEntityRenderingHandler(OperandiEightBallEntity.class, new ThrowableRenderFactory<>(FMPItems.operandiEightBall));
		RenderingRegistry.registerEntityRenderingHandler(OperandiSplashPotionEntity.class, new ThrowableRenderFactory<>(FMPItems.operandiSplashPotion));
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
		EntityRegistry.registerModEntity(new ResourceLocation(FetchModiPlus.MODID, registryName), entityClass, FetchModiPlus.MODID +":"+ name, currentEntityIdOffset, FetchModiPlus.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
		++currentEntityIdOffset;
	}
	
	public static void registerEntity(Class<? extends Entity> entityClass, String name, String registryName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int eggPrimary, int eggSecondary) {
		EntityRegistry.registerModEntity(new ResourceLocation(FetchModiPlus.MODID, registryName), entityClass, FetchModiPlus.MODID + name, currentEntityIdOffset, FetchModiPlus.instance, trackingRange, updateFrequency, sendsVelocityUpdates, eggPrimary, eggSecondary);
		++currentEntityIdOffset;
	}
}
