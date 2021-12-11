package com.cibernet.minestuckgodtier.capabilities;

import com.cibernet.minestuckgodtier.badges.MSGTBadges;
import com.cibernet.minestuckgodtier.badges.heroAspect.*;
import com.cibernet.minestuckgodtier.badges.heroAspectUtil.*;
import com.cibernet.minestuckgodtier.capabilities.IBadgeEffect.*;
import com.cibernet.minestuckgodtier.capabilities.api.IBadgeEffects;
import com.cibernet.minestuckgodtier.client.particles.MSGTParticles;
import com.cibernet.minestuckgodtier.entities.ai.EntityAIMindflayerTarget;
import com.cibernet.minestuckgodtier.network.MSGTChannelHandler;
import com.cibernet.minestuckgodtier.network.MSGTPacket;
import com.cibernet.minestuckgodtier.potions.PotionConceal;
import com.cibernet.minestuckgodtier.util.SoulData;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.EnumClass;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class BadgeEffects implements IBadgeEffects
{
	// Serialized data
	private final Map<Class, IBadgeEffect> effects = new HashMap<Class, IBadgeEffect>()
	{
		@Override
		public IBadgeEffect put(Class key, IBadgeEffect value)
		{
			if (!containsKey(key) || !value.equals(get(key)))
				send(key.getName(), value);
			return super.put(key, value);
		}

		@Override
		public IBadgeEffect remove(Object key)
		{
			if (containsKey(key))
				send(((Class) key).getName(), null);
			return super.remove(key);
		}

		@Override
		public IBadgeEffect replace(Class key, IBadgeEffect value) // lmao this sucks but I love it
		{
			if (value == null)
				return super.remove(key);
			else
				return super.put(key, value);
		}
	};

	// Unserialized data that we don't want to store or ship
	private Queue<SoulData> timeSoulData = new LinkedList<>();
	private Vec3d prevPos;

	// Metadata
	private final Map<Class, MSGTParticles.PowerParticleState> particleMap = new HashMap<>();

	private EntityLivingBase owner;



	@Override
	public int getDecayTime() {
		return getInt(BadgeActiveDoom.class);
	}

	@Override
	public void setDecayTime(int decayTime) {
		setInt(BadgeActiveDoom.class, decayTime);
	}

	@Override
	public boolean isConcealed() {
		return getBoolean(PotionConceal.class);
	}

	@Override
	public void setConcealed(boolean concealed) {
		setBoolean(PotionConceal.class, concealed);
	}

	@Override
	public boolean isTimeStopped()
	{
		return getBoolean(BadgePassiveTime.class);
	}

	@Override
	public void setTimeStopped(boolean timeStopped)
	{
		setBoolean(BadgePassiveTime.class, timeStopped);
	}

	@Override
	public Vec3d getWarpPoint()
	{
		return getVec4Position(BadgePassiveSpace.class);
	}

	@Override
	public int getWarpPointDim()
	{
		return getVec4Dimension(BadgePassiveSpace.class);
	}

	@Override
	public boolean hasWarpPoint()
	{
		return effects.containsKey(BadgePassiveSpace.class);
	}

	@Override
	public void setWarpPoint(Vec3d warpPoint, int warpPointDim)
	{
		setVec4(BadgePassiveSpace.class, warpPoint, warpPointDim);
	}

	@Override
	public void unsetWarpPoint()
	{
		setWarpPoint(null, 0);
	}

	@Override
	public boolean isRageShifted()
	{
		return getBoolean(BadgeUtilRage.class);
	}

	@Override
	public void setRageShifted(boolean rageShifted)
	{
		setBoolean(BadgeUtilRage.class, rageShifted);
	}

	@Override
	public void cleanRageShift()
	{
		// This literally does nothing Ciber
	}

	@Override
	public boolean isRageShiftDirty()
	{
		return false;
	}

	@Override
	public EntityLivingBase getMindflayerEntity()
	{
		return getEntity(EntityAIMindflayerTarget.class); // huehuehue not a badge class
	}

	@Override
	public void setMindflayerEntity(EntityLivingBase entity)
	{
		setEntity(EntityAIMindflayerTarget.class, entity);
	}

	@Override
	public void setMindflayedBy(EntityLivingBase entity)
	{
		setEntity(BadgeActiveMind.IsMindflayed.class, entity);
	}

	@Override
	public EntityLivingBase getMindflayedBy()
	{
		return getEntity(BadgeActiveMind.IsMindflayed.class);
	}


	@Override
	public float getMoveStrafe()
	{
		return getMovementInputStrafe(BadgeActiveMind.class);
	}

	@Override
	public float getMoveForward()
	{
		return getMovementInputForward(BadgeActiveMind.class);
	}

	@Override
	public boolean getJump()
	{
		return getMovementInputJump(BadgeActiveMind.class);
	}

	@Override
	public boolean getSneak()
	{
		return getMovementInputSneak(BadgeActiveMind.class);
	}

	@Override
	public boolean hasMovement()
	{
		return effects.containsKey(BadgeActiveMind.class);
	}

	@Override
	public void setMovement(float moveStrafe, float moveForward, boolean jump, boolean sneak)
	{
		setMovementInput(BadgeActiveMind.class, moveStrafe, moveForward, jump, sneak);
	}

	@Override
	public void unsetMovement()
	{
		setMovement(0, 0, false, false);
	}

	@Override
	public boolean isMindflayed() {
		return getEntity(BadgeActiveMind.IsMindflayed.class) != null;
	}

	@Override
	public boolean isDoingWimdyThing()
	{
		return getBoolean(BadgeUtilBreath.class);
	}

	@Override
	public void setDoingWimdyThing(boolean doingWimdyThing)
	{
		setBoolean(BadgeUtilBreath.class, doingWimdyThing);
	}

	@Override
	public Queue<SoulData> getTimeSoulData()
	{
		return timeSoulData;
	}

	@Override
	public Vec3d getPrevPos() {
		return prevPos;
	}

	@Override
	public void setPrevPos(Vec3d pos) {
		this.prevPos = pos;
	}

	@Override
	public boolean isVoidstepping()
	{
		return getBoolean(BadgeActiveVoid.class) && owner.getCapability(MSGTCapabilities.GOD_TIER_DATA, null).isBadgeActive(MSGTBadges.BADGE_ACTIVE_VOID);
	}

	@Override
	public void setVoidstepping(boolean isVoidstepping)
	{
		setBoolean(BadgeActiveVoid.class, isVoidstepping);
	}

	@Override
	public boolean isGlorbbing()
	{
		return getBoolean(BadgeUtilLight.class) && owner.getCapability(MSGTCapabilities.GOD_TIER_DATA, null).isBadgeActive(MSGTBadges.BADGE_UTIL_LIGHT);
	}

	@Override
	public void setGlorbbing(boolean isGlorbbing)
	{
		setBoolean(BadgeUtilLight.class, isGlorbbing);
	}

	@Override
	public boolean isReforming()
	{
		return getBoolean(BadgeUtilBlood.class) && owner.getCapability(MSGTCapabilities.GOD_TIER_DATA, null).isBadgeActive(MSGTBadges.BADGE_UTIL_BLOOD);
	}

	@Override
	public void setReforming(boolean isReforming)
	{
		setBoolean(BadgeUtilBlood.class, isReforming);
	}

	@Override
	public BlockPos getManipulatedPos1()
	{
		return getVec4Position(BadgeUtilSpace.PosA.class) == null ? null :  new BlockPos(getVec4Position(BadgeUtilSpace.PosA.class));
	}

	@Override
	public int getManipulatedPos1Dim() {
		return getVec4Dimension(BadgeUtilSpace.PosA.class);
	}

	@Override
	public void setManipulatedPos1(BlockPos pos, int dim)
	{
		setVec4(BadgeUtilSpace.PosA.class, pos == null ? null : new Vec3d(pos.getX(), pos.getY(), pos.getZ()), dim);
		setBoolean(BadgeUtilSpace.class, true);
	}

	@Override
	public BlockPos getManipulatedPos2()
	{
		return getVec4Position(BadgeUtilSpace.PosB.class) == null ? null : new BlockPos(getVec4Position(BadgeUtilSpace.PosB.class));
	}

	@Override
	public int getManipulatedPos2Dim() {
		return getVec4Dimension(BadgeUtilSpace.PosB.class);
	}
	@Override
	public void setManipulatedPos2(BlockPos pos, int dim)
	{
		setVec4(BadgeUtilSpace.PosB.class, pos == null ? null : new Vec3d(pos.getX(), pos.getY(), pos.getZ()), dim);
		setBoolean(BadgeUtilSpace.class, false);
	}

	@Override
	public boolean isManipulatingPos2()
	{
		return getBoolean(BadgeUtilSpace.class);
	}


	//Edit Mode Drag
	private BlockPos editPos1 = null;
	private BlockPos editPos2 = null;
	private Vec3d editTraceHit = new Vec3d(0,0,0);
	private EnumFacing editTraceFacing = EnumFacing.NORTH;
	private boolean isEditDragging = false;

	@Override
	public BlockPos getEditPos1() {
		return editPos1;
	}

	@Override
	public BlockPos getEditPos2() {
		return editPos2;
	}

	@Override
	public Vec3d getEditTraceHit() {
		return editTraceHit;
	}

	@Override
	public EnumFacing getEditTraceFacing() {
		return editTraceFacing;
	}

	@Override
	public void setEditPos1(BlockPos pos) {
		editPos1 = pos;
	}

	@Override
	public void setEditPos2(BlockPos pos) {
		editPos2 = pos;
	}

	@Override
	public void setEditTraceHit(Vec3d hit) {
		editTraceHit = hit;
	}

	@Override
	public void setEditTraceFacing(EnumFacing facing) {
		editTraceFacing = facing;
	}

	@Override
	public boolean isEditDragging() {
		return isEditDragging;
	}

	@Override
	public void setEditDragging(boolean v) {
		isEditDragging = v;
	}

	@Override
	public Map<Class, MSGTParticles.PowerParticleState> getPowerParticles()
	{
		return particleMap;
	}

	@Override
	public void startPowerParticles(Class badge, MSGTParticles.PowerParticleState state)
	{
		if (!(particleMap.containsKey(badge) && particleMap.get(badge).equals(state)))
		{
			if (!owner.world.isRemote)
				MSGTChannelHandler.sendToTrackingAndSelf(MSGTPacket.makePacket(MSGTPacket.Type.SEND_POWER_PARTICLES, owner, badge, state), owner);

			particleMap.put(badge, state);
		}
	}

	@Override
	public void stopPowerParticles(Class badge)
	{
		if (particleMap.containsKey(badge))
		{
			if (!owner.world.isRemote)
				MSGTChannelHandler.sendToTrackingAndSelf(MSGTPacket.makePacket(MSGTPacket.Type.SEND_POWER_PARTICLES, owner, badge), owner);

			particleMap.remove(badge);
		}
	}

	@Override
	public void oneshotPowerParticles(MSGTParticles.PowerParticleState state)
	{
		if (!owner.world.isRemote)
			MSGTChannelHandler.sendToTrackingAndSelf(MSGTPacket.makePacket(MSGTPacket.Type.SEND_POWER_PARTICLES, owner, state), owner);
		else
			spawnClientParticles(owner, state);
	}

	@SideOnly(Side.CLIENT)
	private static void spawnClientParticles(EntityLivingBase entity, MSGTParticles.PowerParticleState state)
	{
		int[] colors;
		if (state.aspect != null)
			colors = MSGTParticles.getAspectParticleColors(state.aspect);
		else
			colors = MSGTParticles.getClassParticleColors(state.clazz);

		int[] counts = new int[colors.length];
		for (int i = 0; i < state.count; i++)
			counts[i % counts.length]++;

		for (int i = 0; i < colors.length; i++)
			switch (state.type)
			{
				case AURA:
					MSGTParticles.spawnAuraParticles(entity, colors[i], counts[i]);
					break;
				case BURST:
					MSGTParticles.spawnBurstParticles(entity, colors[i], counts[i]);
					break;
			}
	}


	@Override
	public void setOwner(EntityLivingBase entity)
	{
		this.owner = entity;
	}

	@Override
	public NBTTagCompound writeToNBT()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		NBTTagList effects = new NBTTagList();
		NBTTagList particles = new NBTTagList();

		for (Map.Entry<Class, IBadgeEffect> entry : this.effects.entrySet())
		{
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString("Badge", entry.getKey().getName());
			entry.getValue().serialize(tag);
			effects.appendTag(tag);
		}

		for (Map.Entry<Class, MSGTParticles.PowerParticleState> entry : particleMap.entrySet())
		{
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString("Badge", entry.getKey().getName());
			tag.setByte("Type", (byte) entry.getValue().type.ordinal());
			if(entry.getValue().aspect != null)
				tag.setByte("Aspect", (byte) entry.getValue().aspect.ordinal());
			else tag.setByte("Class", (byte) entry.getValue().clazz.ordinal());
			tag.setByte("Count", (byte) entry.getValue().count);
			particles.appendTag(tag);
		}

		nbt.setTag("Effects", effects);
		nbt.setTag("Particles", particles);

		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		for (NBTBase tagBase : nbt.getTagList("Effects", Constants.NBT.TAG_COMPOUND))
			try
			{
				NBTTagCompound tag = (NBTTagCompound) tagBase;
				effects.replace(Class.forName(tag.getString("Badge")), IBadgeEffect.deserialize(tag).initialize(owner.world));
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
				continue;
			}
			catch (IndexOutOfBoundsException e)
			{
				e.printStackTrace();
				continue;
			}

		for (NBTBase tagBase : nbt.getTagList("Particles", Constants.NBT.TAG_COMPOUND))
			try
			{
				NBTTagCompound tag = (NBTTagCompound) tagBase;
				if(tag.hasKey("Aspect"))
				particleMap.put(Class.forName(tag.getString("Badge")), new MSGTParticles.PowerParticleState(
						MSGTParticles.ParticleType.values()[tag.getByte("Type")],
						 EnumAspect.values()[tag.getByte("Aspect")],
						tag.getByte("Count")
						));
				else
				particleMap.put(Class.forName(tag.getString("Badge")), new MSGTParticles.PowerParticleState(
								MSGTParticles.ParticleType.values()[tag.getByte("Type")],
								EnumClass.values()[tag.getByte("Class")],
						tag.getByte("Count")
				));
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
				continue;
			}
	}



	private void send(String key, IBadgeEffect value)
	{
		MSGTChannelHandler.sendToTrackingAndSelf(MSGTPacket.makePacket(MSGTPacket.Type.UPDATE_BADGE_EFFECT, owner, key, value), owner);
	}

	@Override
	public void receive(String key, IBadgeEffect value)
	{
		try
		{
			effects.replace(Class.forName(key), value);
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}

	}



	private int getInt(Class badge) {
		return effects.containsKey(badge) ? ((IntEffect) effects.get(badge)).value : 0;
	}

	private void setInt(Class badge, int value) {
		if (value == 0)
			effects.remove(badge);
		else
			effects.put(badge, new IntEffect(value));
	}

	private boolean getBoolean(Class badge)
	{
		return effects.containsKey(badge) ? ((BooleanEffect) effects.get(badge)).value : false;
	}

	private void setBoolean(Class badge, boolean value)
	{
		if (value == false)
			effects.remove(badge);
		else
			effects.put(badge, new BooleanEffect(value));
	}

	private Vec3d getVec4Position(Class badge) {
		return effects.containsKey(badge) ? ((Vec4Effect) effects.get(badge)).position : null;
	}

	private int getVec4Dimension(Class badge) {
		return effects.containsKey(badge) ? ((Vec4Effect) effects.get(badge)).dimension : 0;
	}

	private void setVec4(Class badge, Vec3d position, int dimension) {
		if (position == null)
			effects.remove(badge);
		else
			effects.put(badge, new Vec4Effect(position, dimension));
	}

	private float getMovementInputStrafe(Class badge) {
		return effects.containsKey(badge) ? ((MovementInputEffect) effects.get(badge)).moveStrafe : 0;
	}

	private float getMovementInputForward(Class badge) {
		return effects.containsKey(badge) ? ((MovementInputEffect) effects.get(badge)).moveForward : 0;
	}

	private boolean getMovementInputJump(Class badge) {
		return effects.containsKey(badge) ? ((MovementInputEffect) effects.get(badge)).jump : false;
	}

	private boolean getMovementInputSneak(Class badge) {
		return effects.containsKey(badge) ? ((MovementInputEffect) effects.get(badge)).sneak : false;
	}

	private void setMovementInput(Class badge, float moveStrafe, float moveForward, boolean jump, boolean sneak)
	{
		if (moveStrafe == 0 && moveForward == 0 && jump == false && sneak == false)
			effects.remove(badge);
		else
			effects.put(badge, new MovementInputEffect(moveStrafe, moveForward, jump, sneak));
	}

	private EntityLivingBase getEntity(Class badge) {
		return effects.containsKey(badge) ? ((EntityEffect) effects.get(badge)).value : null;
	}

	private void setEntity(Class badge, EntityLivingBase entity)
	{
		if (entity == null)
			effects.remove(badge);
		else
			effects.put(badge, new EntityEffect(entity));
	}



	@SubscribeEvent
	public static void onPlayerLoggedIn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event)
	{
		if (!event.player.world.isRemote)
			MSGTChannelHandler.sendToPlayer(MSGTPacket.makePacket(MSGTPacket.Type.UPDATE_ALL_BADGE_EFFECTS, event.player), event.player);
	}

	@SubscribeEvent
	public static void onStartTracking(PlayerEvent.StartTracking event) // Only fired from the server
	{
		if (event.getTarget() instanceof EntityLivingBase)
			MSGTChannelHandler.sendToPlayer(MSGTPacket.makePacket(MSGTPacket.Type.UPDATE_ALL_BADGE_EFFECTS, event.getTarget()), event.getEntityPlayer());
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event)
	{
		for (MSGTParticles.PowerParticleState state : event.getEntityLiving().getCapability(MSGTCapabilities.BADGE_EFFECTS, null).getPowerParticles().values())
			if (state.count != 0)
				spawnClientParticles(event.getEntityLiving(), state);
	}
}