package com.mraof.minestuck.capabilities.api;

import com.mraof.minestuck.capabilities.IMinestuckCapabilityBase;
import com.mraof.minestuck.client.particles.MinestuckParticles;
import com.mraof.minestuck.network.ISerializableDataType;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.EnumClass;
import com.mraof.minestuck.util.SoulData;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Map;
import java.util.Queue;

public interface IBadgeEffects extends IMinestuckCapabilityBase<EntityLivingBase>
{
	// ----- Effects
	int getDecayTime();
	void setDecayTime(int decayTime);

	boolean isConcealed();
	void setConcealed(boolean concealed);

	boolean isTimeStopped();
	void setTimeStopped(boolean timeStopped);

	Vec3d getWarpPoint();
	int getWarpPointDim();
	boolean hasWarpPoint();
	void setWarpPoint(Vec3d warpPoint, int warpPointDim);
	void unsetWarpPoint();

	boolean isRageShifted();
	void setRageShifted(boolean rageShifted);
	void cleanRageShift();
	boolean isRageShiftDirty();

	EntityLivingBase getMindflayerEntity();
	void setMindflayerEntity(EntityLivingBase entity);
	EntityLivingBase getMindflayedBy();
	void setMindflayedBy(EntityLivingBase entity);
	boolean isMindflayed();

	float getMoveStrafe();
	float getMoveForward();
	boolean getJump();
	boolean getSneak();
	boolean hasMovement();
	void setMovement(float moveStrafe, float moveForward, boolean jump, boolean sneak);
	void unsetMovement();


	boolean isDoingWimdyThing();
	void setDoingWimdyThing(boolean doingWimdyThing);

	Queue<SoulData> getTimeSoulData();

	Vec3d getPrevPos();
	void setPrevPos(Vec3d pos);

	boolean isVoidstepping();
	void setVoidstepping(boolean isVoidstepping);

	boolean isGlorbbing();
	void setGlorbbing(boolean isGlorbbing);

	boolean isReforming();
	void setReforming(boolean isReforming);

	BlockPos getManipulatedPos1();
	int getManipulatedPos1Dim();
	void setManipulatedPos1(BlockPos pos, int dim);
	BlockPos getManipulatedPos2();
	int getManipulatedPos2Dim();
	void setManipulatedPos2(BlockPos pos, int dim);
	boolean isManipulatingPos2();

	BlockPos getEditPos1();
	void setEditPos1(BlockPos pos);
	BlockPos getEditPos2();
	void setEditPos2(BlockPos pos);
	Vec3d getEditTraceHit();
	void setEditTraceHit(Vec3d hit);
	EnumFacing getEditTraceFacing();
	void setEditTraceFacing(EnumFacing facing);

	boolean isEditDragging();
	void setEditDragging(boolean v);


	// ----- Particles
	Map<Class, MinestuckParticles.PowerParticleState> getPowerParticles();

	default void startPowerParticles(Class badge, MinestuckParticles.ParticleType particleType, EnumAspect aspect, int count)
	{
		startPowerParticles(badge, new MinestuckParticles.PowerParticleState(particleType, aspect, count));
	}
	void startPowerParticles(Class badge, MinestuckParticles.PowerParticleState state);
	default void startPowerParticles(Class badge, MinestuckParticles.ParticleType particleType, EnumClass clazz, int count)
	{
		startPowerParticles(badge, new MinestuckParticles.PowerParticleState(particleType, clazz, count));
	}
	void stopPowerParticles(Class badge);

	default void oneshotPowerParticles(MinestuckParticles.ParticleType particleType, EnumAspect aspect, int count)
	{
		oneshotPowerParticles(new MinestuckParticles.PowerParticleState(particleType, aspect, count));
	}
	void oneshotPowerParticles(MinestuckParticles.PowerParticleState state);
	default void oneshotPowerParticles(MinestuckParticles.ParticleType particleType, EnumClass clazz, int count)
	{
		oneshotPowerParticles(new MinestuckParticles.PowerParticleState(particleType, clazz, count));
	}
	// ----- Data
	void setOwner(EntityLivingBase entity);

	void receive(String key, ISerializableDataType value);
}
