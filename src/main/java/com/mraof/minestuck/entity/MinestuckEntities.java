package com.mraof.minestuck.entity;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.entity.carapacian.*;
import com.mraof.minestuck.entity.consort.*;
import com.mraof.minestuck.entity.item.*;
import com.mraof.minestuck.entity.underling.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public final class MinestuckEntities
{
	public static int currentEntityIdOffset = 0;

	public static void registerEntities()
	{
		//register entities
		registerEntity(EntityFrog.class, "frog", 1100060, 11656884);
		registerEntity(EntityRabbitMedium.class, "rabbitMedium", "rabbit_medium");
		registerEntity(EntitySalamander.class, EnumConsort.SALAMANDER.getName());
		registerEntity(EntityNakagator.class, EnumConsort.NAKAGATOR.getName());
		registerEntity(EntityIguana.class, EnumConsort.IGUANA.getName());
		registerEntity(EntityTurtle.class, EnumConsort.TURTLE.getName());
		registerEntity(EntityImp.class, "imp");
		registerEntity(EntityOgre.class, "ogre");
		registerEntity(EntityBasilisk.class, "basilisk");
		registerEntity(EntityLich.class, "lich");
		registerEntity(EntityGiclops.class, "giclops");
		registerEntity(EntityWyrm.class, "wyrm");
		registerEntity(EntityBlackPawn.class, "dersitePawn", "dersite_pawn");
		registerEntity(EntityWhitePawn.class, "prospitianPawn", "prospitian_pawn");
		registerEntity(EntityBlackBishop.class, "dersiteBishop", "dersite_bishop");
		registerEntity(EntityWhiteBishop.class, "prospitianBishop", "prospitian_bishop");
		registerEntity(EntityBlackRook.class, "dersiteRook", "dersite_rook");
		registerEntity(EntityWhiteRook.class, "prospitianRook", "prospitian_rook");
		registerEntity(EntityDecoy.class, "playerDecoy", "player_decoy");
		registerEntity(EntityMetalBoat.class, "metalBoat", "metal_boat");
		registerEntity(EntityGrist.class, "grist", "grist", 512, 1, true);
		registerEntity(EntityVitalityGel.class, "vitalityGel", "vitality_gel", 512, 1, true);
		registerEntity(EntityCrewPoster.class, "midnightCrewPoster", "midnight_crew_poster");
		registerEntity(EntitySbahjPoster.class, "sbahjPoster", "sbahj_poster");
		registerEntity(EntityShopPoster.class, "shopPoster", "shop_poster");
		registerEntity(EntityHologram.class, "holoItem", "holo_item");
		registerEntity(EntityCruxiteSlime.class, "cruxite_slime");
		registerEntity(EntityEightBall.class, "eight_ball");
		registerEntity(EntityCrystalEightBall.class, "crystal_eight_ball");
		registerEntity(EntityOperandiEightBall.class, "operandi_eight_ball");
		registerEntity(EntityOperandiSplashPotion.class, "operandi_splash_potion");
		registerEntity(EntityAcheron.class, "acheron");
		registerEntity(EntityMSUThrowable.class, "throwable");
		registerEntity(EntityMSUArrow.class, "arrow");
		registerEntity(EntityUnrealAir.class, "unreal_air");
		registerEntity(EntityRock.class, "rock");
		registerEntity(EntityHopeGolem.class, "hope_golem");
		registerEntity(EntityLocatorEye.class, "denizen_eye");
	}


	public static void registerEntity(Class<? extends Entity> entityClass, String name)
	{
		registerEntity(entityClass, name, name, 80, 3, true);
	}

	public static void registerEntity(Class<? extends Entity> entityClass, String name, int eggPrimary, int eggSecondary)
	{
		registerEntity(entityClass, name, name, 80, 3, true, eggPrimary, eggSecondary);
	}

	public static void registerEntity(Class<? extends Entity> entityClass, String name, String registryName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int eggPrimary, int eggSecondary)
	{
		EntityRegistry.registerModEntity(new ResourceLocation("minestuck", registryName), entityClass, "minestuck." + name, currentEntityIdOffset, Minestuck.instance, trackingRange, updateFrequency, sendsVelocityUpdates, eggPrimary, eggSecondary);
		currentEntityIdOffset++;
	}

	//registers entity with forge and minecraft, and increases currentEntityIdOffset by one in order to prevent id collision
	public static void registerEntity(Class<? extends Entity> entityClass, String name, String registryName)
	{
		registerEntity(entityClass, name, registryName, 80, 3, true);
	}

	public static void registerEntity(Class<? extends Entity> entityClass, String name, String registryName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
	{
		EntityRegistry.registerModEntity(new ResourceLocation("minestuck", registryName), entityClass, "minestuck." + name, currentEntityIdOffset, Minestuck.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
		currentEntityIdOffset++;
	}

}
