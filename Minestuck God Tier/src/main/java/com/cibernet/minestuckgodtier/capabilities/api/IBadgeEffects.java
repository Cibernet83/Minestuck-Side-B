package com.cibernet.minestuckgodtier.capabilities.api;

import com.cibernet.minestuckgodtier.capabilities.IBadgeEffect;
import com.cibernet.minestuckgodtier.capabilities.MSGTICapabilityBase;
import com.cibernet.minestuckgodtier.client.particles.MSGTParticles;
import com.cibernet.minestuckgodtier.util.SoulData;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.EnumClass;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Map;
import java.util.Queue;

public interface IBadgeEffects extends MSGTICapabilityBase<EntityLivingBase>
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
	void setMindflayedBy(EntityLivingBase entity);
	EntityLivingBase getMindflayedBy();
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
	BlockPos getEditPos2();
	Vec3d getEditTraceHit();
	EnumFacing getEditTraceFacing();
	void setEditPos1(BlockPos pos);
	void setEditPos2(BlockPos pos);
	void setEditTraceHit(Vec3d hit);
	void setEditTraceFacing(EnumFacing facing);

	boolean isEditDragging();
	void setEditDragging(boolean v);


	// ----- Particles
	Map<Class, MSGTParticles.PowerParticleState> getPowerParticles();

	default void startPowerParticles(Class badge, MSGTParticles.ParticleType particleType, EnumAspect aspect, int count) {
		startPowerParticles(badge, new MSGTParticles.PowerParticleState(particleType, aspect, count));
	}
	default void startPowerParticles(Class badge, MSGTParticles.ParticleType particleType, EnumClass clazz, int count) {
		startPowerParticles(badge, new MSGTParticles.PowerParticleState(particleType, clazz, count));
	}
	void startPowerParticles(Class badge, MSGTParticles.PowerParticleState state);

	void stopPowerParticles(Class badge);

	default void oneshotPowerParticles(MSGTParticles.ParticleType particleType, EnumAspect aspect, int count) {
		oneshotPowerParticles(new MSGTParticles.PowerParticleState(particleType, aspect, count));
	}
	default void oneshotPowerParticles(MSGTParticles.ParticleType particleType, EnumClass clazz, int count) {
		oneshotPowerParticles(new MSGTParticles.PowerParticleState(particleType, clazz, count));
	}
	void oneshotPowerParticles(MSGTParticles.PowerParticleState state);

	// ----- Data
	void setOwner(EntityLivingBase entity);

	void receive(String key, IBadgeEffect value);
}
