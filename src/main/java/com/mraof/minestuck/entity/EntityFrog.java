package com.mraof.minestuck.entity;

import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.util.MinestuckSoundHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class EntityFrog extends EntityMinestuck implements IWearsCosmetics
{

	private static final DataParameter<Float> FROG_SIZE = EntityDataManager.createKey(EntityFrog.class, DataSerializers.FLOAT);
	private static final DataParameter<Integer> SKIN_COLOR = EntityDataManager.createKey(EntityFrog.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> EYE_COLOR = EntityDataManager.createKey(EntityFrog.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> BELLY_COLOR = EntityDataManager.createKey(EntityFrog.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> EYE_TYPE = EntityDataManager.createKey(EntityFrog.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> BELLY_TYPE = EntityDataManager.createKey(EntityFrog.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntityFrog.class, DataSerializers.VARINT);
	private static final DataParameter<ItemStack> HAT = EntityDataManager.createKey(EntityFrog.class, DataSerializers.ITEM_STACK);
	private final int baseHealth = 5;
	private final double baseSpeed = 0.3D;
	private int jumpTicks;
	private int jumpDuration;
	private boolean wasOnGround;
	private boolean canDespawn = true;
	private int currentMoveTypeDuration;
	private int cosmeticPickupTimer = 0;

	public EntityFrog(World world)
	{
		this(world, 99);
	}

	public EntityFrog(World world, int type)
	{
		super(world);
		this.jumpHelper = new EntityFrog.FrogJumpHelper(this);
		this.moveHelper = new EntityFrog.FrogMoveHelper(this);
		this.setMovementSpeed(0.0D);


		int newType;

		if (type == 99) newType = getRandomFrogType();
		else newType = type;
		this.dataManager.register(TYPE, newType);
	}

	public void setMovementSpeed(double newSpeed)
	{
		this.getNavigator().setSpeed(newSpeed);
		this.moveHelper.setMoveTo(this.moveHelper.getX(), this.moveHelper.getY(), this.moveHelper.getZ(), newSpeed);
	}

	public int getRandomFrogType()
	{
		return getRandomFrogType(20, 50, 500);
	}

	public int getRandomFrogType(int chance1, int chance2, int chance3)
	{
		Random rand = new Random();
		int newType;

		if (rand.nextInt(chance1) == 1)
		{
			newType = 1;
		}
		else if (rand.nextInt(chance2) == 1)
		{
			newType = 2;
		}
		else if (rand.nextInt(chance3) == 1)
		{
			newType = 6;
		}
		else newType = 0;

		return newType;
	}

	public static int maxTypes()
	{
		return 6;
	}

	public static void registerFixesFrog(DataFixer fixer)
	{
		EntityLiving.registerFixesMob(fixer, EntityFrog.class);
	}

	//Entity AI
	@Override
	protected void initEntityAI()
	{

		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityFrog.AIPanic(this, 2.2D));
		this.tasks.addTask(3, new EntityAITempt(this, 1.0D, MinestuckItems.coneOfFlies, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.0D, MinestuckItems.bugOnAStick, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.0D, MinestuckItems.grasshopper, false));
		this.tasks.addTask(3, new EntityAITempt(this, 1.0D, MinestuckItems.jarOfBugs, false));
		this.tasks.addTask(6, new EntityAIWander(this, 0.6D));
		this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));


	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataManager.register(FROG_SIZE, randomFloat(1) + 0.6F);
		this.dataManager.register(SKIN_COLOR, random(16777215));
		this.dataManager.register(EYE_COLOR, random(16777215));
		this.dataManager.register(BELLY_COLOR, random(16777215));
		this.dataManager.register(EYE_TYPE, random(maxEyes()));
		this.dataManager.register(BELLY_TYPE, random(maxBelly()));
		this.dataManager.register(HAT, ItemStack.EMPTY);


		this.canDespawn = true;
	}

	public static int maxEyes()
	{
		return 2;
	}

	public static int maxBelly()
	{
		return 3;
	}

	public void handleStatusUpdate(byte id)
	{
		if (id == 1)
		{
			this.jumpDuration = 10;
			this.jumpTicks = 0;
		}
		else
		{
			super.handleStatusUpdate(id);
		}
	}

	protected SoundEvent getAmbientSound()
	{
		return MinestuckSoundHandler.soundFrogAmbient;
	}

	//NBT
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);
		compound.setInteger("Type", this.getType());
		if (getType() != 6) compound.setFloat("Size", this.getFrogSize() + 0.4f);
		else compound.setFloat("Size", 0.6f);
		compound.setInteger("skinColor", this.getSkinColor());
		compound.setInteger("eyeColor", this.getEyeColor());
		compound.setInteger("bellyColor", this.getBellyColor());
		compound.setInteger("eyeType", this.getEyeType());
		compound.setInteger("bellyType", this.getBellyType());
		compound.setBoolean("wasOnGround", this.wasOnGround);
		compound.setBoolean("canDespawn", canDespawn);

		NBTTagCompound stackNbt = new NBTTagCompound();
		getHeadStack().writeToNBT(stackNbt);
		compound.setTag("Hat", stackNbt);
		compound.setInteger("CosmeticPickupDelay", getCosmeticPickupDelay());
	}

	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);

		if (compound.hasKey("Type")) setType(compound.getInteger("Type"));
		else setType(getRandomFrogType());

		if (compound.hasKey("Size") && getType() != 6)
		{
			float i = compound.getFloat("Size");
			if (i <= 0.2f) i = 0.2f;
			this.setFrogSize(i - 0.4f, false);
		}
		else this.setFrogSize(0.6f, false);
		if (compound.hasKey("skinColor"))
		{
			if (compound.getInteger("skinColor") == 0)
				this.setSkinColor(0);

			else this.setSkinColor(compound.getInteger("skinColor"));
		}
		else this.setSkinColor(random(16777215));

		if (compound.hasKey("eyeColor"))
		{
			if (compound.getInteger("eyeColor") == 0)
				this.setEyeColor(0);

			else this.setEyeColor(compound.getInteger("eyeColor"));
		}
		else this.setEyeColor(random(16777215));

		if (compound.hasKey("eyeType"))
		{
			if (compound.getInteger("eyeType") == 0)
				this.setEyeType(0);

			else this.setEyeType(compound.getInteger("eyeType"));
		}
		else this.setEyeType(random(2));


		if (compound.hasKey("bellyColor"))
		{
			if (compound.getInteger("bellyColor") == 0)
				this.setBellyColor(0);

			else this.setBellyColor(compound.getInteger("bellyColor"));
		}
		else this.setBellyColor(random(16777215));

		if (compound.hasKey("bellyType"))
		{
			if (compound.getInteger("bellyType") == 0)
				this.setBellyType(0);

			else this.setBellyType(compound.getInteger("bellyType"));
		}
		else this.setBellyType(random(3));


		this.wasOnGround = compound.getBoolean("wasOnGround");
		if (compound.hasKey("canDespawn")) this.canDespawn = compound.getBoolean("canDespawn");
		else this.canDespawn = true;

		setHeadStack(new ItemStack(compound.getCompoundTag("Hat")));
		setCosmeticPickupDelay(compound.getInteger("CosmeticPickupDelay"));
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		if (this.jumpTicks != this.jumpDuration)
		{
			++this.jumpTicks;
		}
		else if (this.jumpDuration != 0)
		{
			this.jumpTicks = 0;
			this.jumpDuration = 0;
			this.setJumping(false);
		}

		if (world.isRemote)
			return;

		if (getCosmeticPickupDelay() <= 0)
			for (EntityItem entityitem : world.getEntitiesWithinAABB(EntityItem.class, getEntityBoundingBox().grow(1.0D, 0.0D, 1.0D)))
			{
				if (!entityitem.isDead && !entityitem.getItem().isEmpty() && !entityitem.cannotPickup())
				{
					ItemStack stack = entityitem.getItem();
					if (EntityLiving.getSlotForItemStack(stack) == EntityEquipmentSlot.HEAD && !ItemStack.areItemStacksEqual(stack, getHeadStack()))
					{
						if (!getHeadStack().isEmpty())
							world.spawnEntity(new EntityItem(world, posX, posY + height, posZ, getHeadStack()));

						ItemStack pickedUp = stack.copy();
						pickedUp.setCount(1);
						setHeadStack(pickedUp);
						stack.shrink(1);
						onItemPickup(entityitem, 1);
						entityitem.setDead();

						if (!stack.isEmpty())
							world.spawnEntity(new EntityItem(world, posX, posY + height, posZ, stack));

						setCosmeticPickupDelay(200);

						break;
					}
				}
			}
		shrinkPickupDelay();
	}

	@Override
	protected boolean canDespawn()
	{
		return canDespawn;
	}

	public void updateAITasks()
	{
		if (this.currentMoveTypeDuration > 0)
		{
			--this.currentMoveTypeDuration;
		}

		if (this.onGround)
		{
			if (!this.wasOnGround)
			{
				this.setJumping(false);
				this.checkLandingDelay();
			}


			EntityFrog.FrogJumpHelper entityfrog$frogjumphelper = (EntityFrog.FrogJumpHelper) this.jumpHelper;

			if (!entityfrog$frogjumphelper.getIsJumping())
			{
				if (this.moveHelper.isUpdating() && this.currentMoveTypeDuration == 0)
				{
					Path path = this.navigator.getPath();
					Vec3d vec3d = new Vec3d(this.moveHelper.getX(), this.moveHelper.getY(), this.moveHelper.getZ());

					if (path != null && path.getCurrentPathIndex() < path.getCurrentPathLength())
					{
						vec3d = path.getPosition(this);
					}

					this.calculateRotationYaw(vec3d.x, vec3d.z);
					this.startJumping();
				}
			}
			else if (!entityfrog$frogjumphelper.canJump())
			{
				this.enableJumpControl();
			}
		}

		this.wasOnGround = this.onGround;
	}

	public void startJumping()
	{
		this.setJumping(true);
		this.jumpDuration = 10;
		this.jumpTicks = 0;
	}

	private void calculateRotationYaw(double x, double z)
	{
		this.rotationYaw = (float) (MathHelper.atan2(z - this.posZ, x - this.posX) * (180D / Math.PI)) - 90.0F;
	}

	private void enableJumpControl()
	{
		((FrogJumpHelper) this.jumpHelper).setCanJump(true);
	}

	private void checkLandingDelay()
	{
		this.updateMoveTypeDuration();
		this.disableJumpControl();
	}

	private void disableJumpControl()
	{
		((FrogJumpHelper) this.jumpHelper).setCanJump(false);
	}

	private void updateMoveTypeDuration()
	{
		if (this.moveHelper.getSpeed() < 2.2D)
		{
			this.currentMoveTypeDuration = 10;
		}
		else
		{
			this.currentMoveTypeDuration = 1;
		}
	}

	@Override
	public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn)
	{
		return slotIn == EntityEquipmentSlot.HEAD ? getHeadStack() : super.getItemStackFromSlot(slotIn);
	}

	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand)
	{
		ItemStack itemstack = player.getHeldItem(hand);

		if (!isDead && player.getDistanceSq(this) < 9.0D && !this.world.isRemote)
		{
			if (itemstack.getItem() == MinestuckItems.bugNet)
			{
				itemstack.damageItem(1, player);
				ItemStack frogItem = new ItemStack(MinestuckItems.itemFrog, 1, this.dataManager.get(TYPE));

				frogItem.setTagCompound(getFrogData());
				if (this.hasCustomName()) frogItem.setStackDisplayName(this.getCustomNameTag());

				entityDropItem(frogItem, 0);
				this.setDead();
			}
			else if (itemstack.getItem() == MinestuckItems.goldenGrasshopper && this.getType() != 5)
			{
				if (!player.isCreative()) itemstack.shrink(1);

				((WorldServer) this.world).spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX, this.posY + 0.5, this.posZ, 1, 0, 0, 0, 0d);
				this.playSound(MinestuckSoundHandler.soundFrogGold, this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
				this.setType(5);
			}
		}
		return super.processInteract(player, hand);
	}

	public EntityItem entityDropItem(ItemStack stack, float offsetY)
	{
		EntityItem entityitem = new EntityItem(this.world, this.posX, this.posY, this.posZ, stack);
		entityitem.setDefaultPickupDelay();
		this.world.spawnEntity(entityitem);
		return entityitem;
	}

	protected NBTTagCompound getFrogData()
	{
		NBTTagCompound compound = new NBTTagCompound();

		compound.setInteger("Type", this.getType());
		compound.setFloat("Size", this.getFrogSize() + 0.4f);
		compound.setInteger("skinColor", this.getSkinColor());
		compound.setInteger("eyeColor", this.getEyeColor());
		compound.setInteger("bellyColor", this.getBellyColor());
		compound.setInteger("eyeType", this.getEyeType());
		compound.setInteger("bellyType", this.getBellyType());

		return compound;
	}


	//Frog Sounds

	public int getSkinColor()
	{
		return this.dataManager.get(SKIN_COLOR);
	}

	private void setSkinColor(int i)
	{
		this.dataManager.set(SKIN_COLOR, i);
	}

	public int getEyeColor()
	{
		return this.dataManager.get(EYE_COLOR);
	}

	private void setEyeColor(int i)
	{
		this.dataManager.set(EYE_COLOR, i);
	}

	public int getBellyColor()
	{
		return this.dataManager.get(BELLY_COLOR);
	}

	private void setBellyColor(int i)
	{
		this.dataManager.set(BELLY_COLOR, i);
	}

	public int getEyeType()
	{
		return this.dataManager.get(EYE_TYPE);
	}

	private void setEyeType(int i)
	{
		this.dataManager.set(EYE_TYPE, i);
	}

	public int getBellyType()
	{
		return this.dataManager.get(BELLY_TYPE);
	}

	private void setBellyType(int i)
	{
		this.dataManager.set(BELLY_TYPE, i);
	}

	public float getFrogSize()
	{
		return this.dataManager.get(FROG_SIZE);
	}

	public int getType()
	{
		return this.dataManager.get(TYPE);
	}

	private void setType(int i)
	{
		this.dataManager.set(TYPE, i);
	}

	protected void setFrogSize(float size, boolean p_70799_2_)
	{
		if (this.dataManager.get(TYPE) == 6) this.dataManager.set(FROG_SIZE, Float.valueOf(0.6f));
		else this.dataManager.set(FROG_SIZE, Float.valueOf(size));
		this.setSize(0.51000005F * size, 0.51000005F * size);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double) (baseHealth * size));
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((baseSpeed * size));

		if (p_70799_2_)
		{
			this.setHealth(this.getMaxHealth());
		}

		this.experienceValue = (int) size;
	}

	public int random(int max)
	{
		Random rand = new Random();
		return rand.nextInt(max + 1);
	}

	public float randomFloat(int max)
	{
		Random rand = new Random();
		return (float) (rand.nextInt(max * 10)) / 10;
	}

	@SideOnly(Side.CLIENT)
	public float setJumpCompletion(float p_175521_1_)
	{
		return this.jumpDuration == 0 ? 0.0F : ((float) this.jumpTicks + p_175521_1_) / (float) this.jumpDuration;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn)
	{
		return MinestuckSoundHandler.soundFrogHurt;
	}

	protected SoundEvent getDeathSound()
	{
		return MinestuckSoundHandler.soundFrogDeath;
	}

	protected float getSoundPitch()
	{
		return (this.rand.nextFloat() - this.rand.nextFloat()) / (this.getFrogSize() + 0.4f) * 0.2F + 1.0F;
	}

	//Making the frog jump
	protected float getJumpUpwardsMotion()
	{
		if (!this.collidedHorizontally && (!this.moveHelper.isUpdating() || this.moveHelper.getY() <= this.posY + 0.5D))
		{
			Path path = this.navigator.getPath();

			if (path != null && path.getCurrentPathIndex() < path.getCurrentPathLength())
			{
				Vec3d vec3d = path.getPosition(this);

				if (vec3d.y > this.posY + 0.5D)
				{
					return 0.5F;
				}
			}

			return this.moveHelper.getSpeed() <= 0.6D ? 0.2F : 0.3F;
		}
		else
		{
			return 0.5F;
		}
	}

	/**
	 * Causes this entity to do an upwards motion (jumping).
	 */
	protected void jump()
	{
		super.jump();
		double d0 = this.moveHelper.getSpeed();

		if (d0 > 0.0D)
		{
			double d1 = this.motionX * this.motionX + this.motionZ * this.motionZ;

			if (d1 < 0.010000000000000002D)
			{
				this.moveRelative(0.0F, 0.0F, 1.0F, 0.1F);
			}
		}

		if (!this.world.isRemote)
		{
			this.world.setEntityState(this, (byte) 1);
		}
	}

	public void setJumping(boolean jumping)
	{
		super.setJumping(jumping);

		if (jumping)
		{
			this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
		}
	}

	protected SoundEvent getJumpSound()
	{
		return SoundEvents.ENTITY_RABBIT_JUMP;
	}

	public void notifyDataManagerChange(DataParameter<?> key)
	{
		if (FROG_SIZE.equals(key))
		{
			float i = this.getFrogSize();
			this.setSize(0.51000005F * i, 0.51000005F * i);
			this.rotationYaw = this.rotationYawHead;
			this.renderYawOffset = this.rotationYawHead;

			if (this.isInWater() && this.rand.nextInt(20) == 0)
			{
				this.doWaterSplashEffect();
			}
		}

		super.notifyDataManagerChange(key);
	}

	@Override
	protected float getMaximumHealth()
	{
		return baseHealth;
	}

	@Override
	public String getTexture()
	{
		return "textures/mobs/frog/base.png";
	}

	static class AIAvoidEntity<T extends Entity> extends EntityAIAvoidEntity<T>
	{
		private final EntityFrog frog;

		public AIAvoidEntity(EntityFrog frog, Class<T> p_i46403_2_, float p_i46403_3_, double p_i46403_4_, double p_i46403_6_)
		{
			super(frog, p_i46403_2_, p_i46403_3_, p_i46403_4_, p_i46403_6_);
			this.frog = frog;
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute()
		{
			return super.shouldExecute();
		}
	}

	static class AIPanic extends EntityAIPanic
	{
		private final EntityFrog frog;

		public AIPanic(EntityFrog frog, double speedIn)
		{
			super(frog, speedIn);
			this.frog = frog;
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void updateTask()
		{
			super.updateTask();
			this.frog.setMovementSpeed(this.speed);
		}
	}

	static class FrogMoveHelper extends EntityMoveHelper
	{
		private final EntityFrog frog;
		private double nextJumpSpeed;

		public FrogMoveHelper(EntityFrog frog)
		{
			super(frog);
			this.frog = frog;
		}

		/**
		 * Sets the speed and location to move to
		 */
		public void setMoveTo(double x, double y, double z, double speedIn)
		{
			if (this.frog.isInWater())
			{
				speedIn = 1.5D;
			}

			super.setMoveTo(x, y, z, speedIn);

			if (speedIn > 0.0D)
			{
				this.nextJumpSpeed = speedIn;
			}
		}

		public void onUpdateMoveHelper()
		{
			if (this.frog.onGround && !this.frog.isJumping && !((EntityFrog.FrogJumpHelper) this.frog.jumpHelper).getIsJumping())
			{
				this.frog.setMovementSpeed(0.0D);
			}
			else if (this.isUpdating())
			{
				this.frog.setMovementSpeed(this.nextJumpSpeed);
			}

			super.onUpdateMoveHelper();
		}
	}

	public class FrogJumpHelper extends EntityJumpHelper
	{
		private final EntityFrog frog;
		private boolean canJump;

		public FrogJumpHelper(EntityFrog frog)
		{
			super(frog);
			this.frog = frog;
		}

		public boolean getIsJumping()
		{
			return this.isJumping;
		}

		public boolean canJump()
		{
			return this.canJump;
		}

		public void setCanJump(boolean canJumpIn)
		{
			this.canJump = canJumpIn;
		}

		/**
		 * Called to actually make the entity jump if isJumping is true.
		 */
		public void doJump()
		{
			if (this.isJumping)
			{
				this.frog.startJumping();
				this.isJumping = false;
			}
		}
	}	@Override
	public void setHeadStack(ItemStack stack)
	{
		dataManager.set(HAT, stack);
	}



	@Override
	public ItemStack getHeadStack()
	{
		return dataManager.get(HAT);
	}

	@Override
	public void setCosmeticPickupDelay(int i)
	{
		cosmeticPickupTimer = i;
	}

	@Override
	public int getCosmeticPickupDelay()
	{
		return cosmeticPickupTimer;
	}

	@Override
	public int shrinkPickupDelay()
	{
		return cosmeticPickupTimer = Math.max(0, cosmeticPickupTimer - 1);
	}
}