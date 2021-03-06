package com.mraof.minestuck.capabilities.caps;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.badges.MinestuckBadges;
import com.mraof.minestuck.badges.heroAspect.*;
import com.mraof.minestuck.badges.heroAspectUtil.*;
import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.client.particles.MinestuckParticles;
import com.mraof.minestuck.entity.ai.EntityAIMindflayerTarget;
import com.mraof.minestuck.network.ISerializableDataType;
import com.mraof.minestuck.network.ISerializableDataType.*;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.network.message.MessageBadgeEffect;
import com.mraof.minestuck.network.message.MessageBadgeEffectsAll;
import com.mraof.minestuck.network.message.MessageSendPowerParticlesState;
import com.mraof.minestuck.potions.PotionConceal;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.EnumClass;
import com.mraof.minestuck.util.SoulData;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public class BadgeEffects implements IBadgeEffects
{
	// Metadata
	private final Map<Class, MinestuckParticles.PowerParticleState> particleMap = new HashMap<>();
	// Unserialized data that we don't want to store or ship
	private Queue<SoulData> timeSoulData = new LinkedList<>();
	private Vec3d prevPos;
	private EntityLivingBase owner;
	// Serialized data
	private final Map<Class, ISerializableDataType> effects = new HashMap<Class, ISerializableDataType>()
	{
		@Override
		public ISerializableDataType put(Class key, ISerializableDataType value)
		{
			if (!containsKey(key) || !value.equals(get(key)))
				send(key.getName(), value);
			return super.put(key, value);
		}

		@Override
		public ISerializableDataType remove(Object key)
		{
			if (containsKey(key))
				send(((Class) key).getName(), null);
			return super.remove(key);
		}

		@Override
		public ISerializableDataType replace(Class key, ISerializableDataType value) // lmao this sucks but I love it
		{
			if (value == null)
				return super.remove(key);
			else
				return super.put(key, value);
		}
	};
	//Edit Mode Drag
	private BlockPos editPos1 = null;
	private BlockPos editPos2 = null;
	private Vec3d editTraceHit = new Vec3d(0, 0, 0);
	private EnumFacing editTraceFacing = EnumFacing.NORTH;
	private boolean isEditDragging = false;

	@SubscribeEvent
	public static void onPlayerLoggedIn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event)
	{
		if (!event.player.world.isRemote)
			MinestuckNetwork.sendTo(new MessageBadgeEffectsAll(event.player), event.player);
	}

	@SubscribeEvent
	public static void onStartTracking(PlayerEvent.StartTracking event) // Only fired from the server
	{
		if (event.getTarget() instanceof EntityLivingBase)
			MinestuckNetwork.sendTo(new MessageBadgeEffectsAll((EntityLivingBase) event.getTarget()), event.getEntityPlayer());
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event)
	{
		for (MinestuckParticles.PowerParticleState state : event.getEntityLiving().getCapability(MinestuckCapabilities.BADGE_EFFECTS, null).getPowerParticles().values())
			if (state.count != 0)
				spawnClientParticles(event.getEntityLiving(), state);
	}

	@Override
	public int getDecayTime()
	{
		return getInt(BadgeActiveDoom.class);
	}

	@Override
	public void setDecayTime(int decayTime)
	{
		setInt(BadgeActiveDoom.class, decayTime);
	}

	@Override
	public boolean isConcealed()
	{
		return getBoolean(PotionConceal.class);
	}

	@Override
	public void setConcealed(boolean concealed)
	{
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
	public EntityLivingBase getMindflayedBy()
	{
		return getEntity(BadgeActiveMind.IsMindflayed.class);
	}

	@Override
	public void setMindflayedBy(EntityLivingBase entity)
	{
		setEntity(BadgeActiveMind.IsMindflayed.class, entity);
	}

	@Override
	public boolean isMindflayed()
	{
		return getEntity(BadgeActiveMind.IsMindflayed.class) != null;
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
	public Vec3d getPrevPos()
	{
		return prevPos;
	}

	@Override
	public void setPrevPos(Vec3d pos)
	{
		this.prevPos = pos;
	}

	@Override
	public boolean isVoidstepping()
	{
		return getBoolean(BadgeActiveVoid.class) && owner.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).isBadgeActive(MinestuckBadges.BADGE_ACTIVE_VOID);
	}

	@Override
	public void setVoidstepping(boolean isVoidstepping)
	{
		setBoolean(BadgeActiveVoid.class, isVoidstepping);
	}

	@Override
	public boolean isGlorbbing()
	{
		return getBoolean(BadgeUtilLight.class) && owner.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).isBadgeActive(MinestuckBadges.BADGE_UTIL_LIGHT);
	}

	@Override
	public void setGlorbbing(boolean isGlorbbing)
	{
		setBoolean(BadgeUtilLight.class, isGlorbbing);
	}

	@Override
	public boolean isReforming()
	{
		return getBoolean(BadgeUtilBlood.class) && owner.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).isBadgeActive(MinestuckBadges.BADGE_UTIL_BLOOD);
	}

	@Override
	public void setReforming(boolean isReforming)
	{
		setBoolean(BadgeUtilBlood.class, isReforming);
	}

	@Override
	public BlockPos getManipulatedPos1()
	{
		return getVec4Position(BadgeUtilSpace.PosA.class) == null ? null : new BlockPos(getVec4Position(BadgeUtilSpace.PosA.class));
	}

	@Override
	public int getManipulatedPos1Dim()
	{
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
	public int getManipulatedPos2Dim()
	{
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

	@Override
	public BlockPos getEditPos1()
	{
		return editPos1;
	}

	@Override
	public void setEditPos1(BlockPos pos)
	{
		editPos1 = pos;
	}

	@Override
	public BlockPos getEditPos2()
	{
		return editPos2;
	}

	@Override
	public void setEditPos2(BlockPos pos)
	{
		editPos2 = pos;
	}

	@Override
	public Vec3d getEditTraceHit()
	{
		return editTraceHit;
	}

	@Override
	public void setEditTraceHit(Vec3d hit)
	{
		editTraceHit = hit;
	}

	@Override
	public EnumFacing getEditTraceFacing()
	{
		return editTraceFacing;
	}

	@Override
	public void setEditTraceFacing(EnumFacing facing)
	{
		editTraceFacing = facing;
	}

	@Override
	public boolean isEditDragging()
	{
		return isEditDragging;
	}

	@Override
	public void setEditDragging(boolean v)
	{
		isEditDragging = v;
	}

	@Override
	public Map<Class, MinestuckParticles.PowerParticleState> getPowerParticles()
	{
		return particleMap;
	}

	@Override
	public void startPowerParticles(Class badge, MinestuckParticles.PowerParticleState state)
	{
		if (!(particleMap.containsKey(badge) && particleMap.get(badge).equals(state)))
		{
			if (!owner.world.isRemote)
				if (owner instanceof EntityPlayer)
					MinestuckNetwork.sendToTrackingAndSelf(new MessageSendPowerParticlesState(owner, badge, state), (EntityPlayer) owner);
				else
					MinestuckNetwork.sendToTracking(new MessageSendPowerParticlesState(owner, badge, state), owner);

			particleMap.put(badge, state);
		}
	}

	@Override
	public void stopPowerParticles(Class badge)
	{
		if (particleMap.containsKey(badge))
		{
			if (!owner.world.isRemote)
				if (owner instanceof EntityPlayer)
					MinestuckNetwork.sendToTrackingAndSelf(new MessageSendPowerParticlesState(owner, badge, null), (EntityPlayer) owner);
				else
					MinestuckNetwork.sendToTracking(new MessageSendPowerParticlesState(owner, badge, null), owner);

			particleMap.remove(badge);
		}
	}

	@Override
	public void oneshotPowerParticles(MinestuckParticles.PowerParticleState state)
	{
		if (!owner.world.isRemote)
			if (owner instanceof EntityPlayer)
				MinestuckNetwork.sendToTrackingAndSelf(new MessageSendPowerParticlesState(owner, state), (EntityPlayer) owner);
			else
				MinestuckNetwork.sendToTracking(new MessageSendPowerParticlesState(owner, state), owner);
		else
			spawnClientParticles(owner, state);
	}

	@SideOnly(Side.CLIENT)
	private static void spawnClientParticles(EntityLivingBase entity, MinestuckParticles.PowerParticleState state)
	{
		int[] colors;
		if (state.aspect != null)
			colors = MinestuckParticles.getAspectParticleColors(state.aspect);
		else
			colors = MinestuckParticles.getClassParticleColors(state.clazz);

		int[] counts = new int[colors.length];
		for (int i = 0; i < state.count; i++)
			counts[i % counts.length]++;

		for (int i = 0; i < colors.length; i++)
			switch (state.type)
			{
				case AURA:
					MinestuckParticles.spawnAuraParticles(entity, colors[i], counts[i]);
					break;
				case BURST:
					MinestuckParticles.spawnBurstParticles(entity, colors[i], counts[i]);
					break;
			}
	}

	@Override
	public void setOwner(EntityLivingBase entity)
	{
		this.owner = entity;
	}

	@Override
	public void receive(String key, ISerializableDataType value)
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

	private void setMovementInput(Class badge, float moveStrafe, float moveForward, boolean jump, boolean sneak)
	{
		if (moveStrafe == 0 && moveForward == 0 && jump == false && sneak == false)
			effects.remove(badge);
		else
			effects.put(badge, new MovementInputData(moveStrafe, moveForward, jump, sneak));
	}

	private boolean getMovementInputSneak(Class badge)
	{
		return effects.containsKey(badge) && ((MovementInputData) effects.get(badge)).getValue().isSneaking();
	}

	private boolean getMovementInputJump(Class badge)
	{
		return effects.containsKey(badge) && ((MovementInputData) effects.get(badge)).getValue().isJumping();
	}

	private float getMovementInputForward(Class badge)
	{
		return effects.containsKey(badge) ? ((MovementInputData) effects.get(badge)).getValue().getMoveForward() : 0;
	}

	private float getMovementInputStrafe(Class badge)
	{
		return effects.containsKey(badge) ? ((MovementInputData) effects.get(badge)).getValue().getMoveStrafe() : 0;
	}

	private void setEntity(Class badge, EntityLivingBase entity)
	{
		if (entity == null)
			effects.remove(badge);
		else
			effects.put(badge, new EntityData(entity));
	}

	private EntityLivingBase getEntity(Class badge)
	{
		return effects.containsKey(badge) ? ((EntityData) effects.get(badge)).getValue() : null;
	}

	private void setVec4(Class badge, Vec3d position, int dimension)
	{
		if (position == null)
			effects.remove(badge);
		else
			effects.put(badge, new Vec4Data(position, dimension));
	}

	private int getVec4Dimension(Class badge)
	{
		return effects.containsKey(badge) ? ((Vec4Data) effects.get(badge)).getValue().getDimension() : 0;
	}

	private Vec3d getVec4Position(Class badge)
	{
		return effects.containsKey(badge) ? ((Vec4Data) effects.get(badge)).getValue().getPosition() : null;
	}

	private void setBoolean(Class badge, boolean value)
	{
		if (value == false)
			effects.remove(badge);
		else
			effects.put(badge, new BooleanData(value));
	}

	private boolean getBoolean(Class badge)
	{
		return effects.containsKey(badge) ? ((BooleanData) effects.get(badge)).getValue() : false;
	}

	private void setInt(Class badge, int value)
	{
		if (value == 0)
			effects.remove(badge);
		else
			effects.put(badge, new IntegerData(value));
	}

	private int getInt(Class badge)
	{
		return effects.containsKey(badge) ? ((IntegerData) effects.get(badge)).getValue() : 0;
	}

	@Override
	public NBTTagCompound writeToNBT()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		NBTTagList effects = new NBTTagList();
		NBTTagList particles = new NBTTagList();

		for (Map.Entry<Class, ISerializableDataType> entry : this.effects.entrySet())
		{
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString("DataTypeClass", entry.getValue().getClass().getName());
			tag.setString("Badge", entry.getKey().getName());
			entry.getValue().serialize(tag);
			effects.appendTag(tag);
		}

		for (Map.Entry<Class, MinestuckParticles.PowerParticleState> entry : particleMap.entrySet())
		{
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString("Badge", entry.getKey().getName());
			tag.setByte("Type", (byte) entry.getValue().type.ordinal());
			if (entry.getValue().aspect != null)
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
				ISerializableDataType dataType = ISerializableDataType.deserialize(tag, (Class<? extends ISerializableDataType>) Class.forName(tag.getString("DataTypeClass")));
				dataType.initialize(owner.world);
				effects.replace(Class.forName(tag.getString("Badge")), dataType);
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
				continue;
			}

		for (NBTBase tagBase : nbt.getTagList("Particles", Constants.NBT.TAG_COMPOUND))
			try
			{
				NBTTagCompound tag = (NBTTagCompound) tagBase;
				if (tag.hasKey("Aspect"))
					particleMap.put(Class.forName(tag.getString("Badge")), new MinestuckParticles.PowerParticleState(
							MinestuckParticles.ParticleType.values()[tag.getByte("Type")],
							EnumAspect.values()[tag.getByte("Aspect")],
							tag.getByte("Count")
					));
				else
					particleMap.put(Class.forName(tag.getString("Badge")), new MinestuckParticles.PowerParticleState(
							MinestuckParticles.ParticleType.values()[tag.getByte("Type")],
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

	private void send(String key, ISerializableDataType value)
	{
		if (owner instanceof EntityPlayer)
			MinestuckNetwork.sendToTrackingAndSelf(new MessageBadgeEffect(owner, key, value), (EntityPlayer) owner);
		else
			MinestuckNetwork.sendToTracking(new MessageBadgeEffect(owner, key, value), owner);
	}
}