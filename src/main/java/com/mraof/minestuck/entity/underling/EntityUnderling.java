package com.mraof.minestuck.entity.underling;

import com.mraof.minestuck.alchemy.Grist;
import com.mraof.minestuck.alchemy.GristAmount;
import com.mraof.minestuck.alchemy.GristSet;
import com.mraof.minestuck.alchemy.MinestuckGrist;
import com.mraof.minestuck.entity.EntityListFilter;
import com.mraof.minestuck.entity.EntityMinestuck;
import com.mraof.minestuck.entity.ai.EntityAIHurtByTargetAllied;
import com.mraof.minestuck.entity.ai.EntityAINearestAttackableTargetWithHeight;
import com.mraof.minestuck.entity.item.EntityGrist;
import com.mraof.minestuck.entity.item.EntityVitalityGel;
import com.mraof.minestuck.event.UnderlingSpoilsEvent;
import com.mraof.minestuck.network.skaianet.SburbHandler;
import com.mraof.minestuck.util.Debug;
import com.mraof.minestuck.util.Echeladder;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import java.util.*;

public abstract class EntityUnderling extends EntityMinestuck implements IEntityAdditionalSpawnData, IMob
{
	//The type of the underling
	protected static final DataParameter<String> GRIST_TYPE = EntityDataManager.createKey(EntityUnderling.class, DataSerializers.STRING);
	private static final float maxSharedProgress = 2;    //The multiplier for the maximum amount progress that can be gathered from each enemy with the group fight bonus
	@SuppressWarnings("unchecked")
	protected static EntityListFilter underlingSelector = new EntityListFilter(Arrays.asList(EntityImp.class, EntityOgre.class, EntityBasilisk.class, EntityLich.class, EntityGiclops.class));
	public boolean fromSpawner;
	protected EntityListFilter attackEntitySelector;
	protected DamageSource lastSource; //doesn't need to be saved to NBT, does it?
	protected Map<EntityPlayerMP, Double> damageMap = new HashMap<EntityPlayerMP, Double>();    //Map that stores how much damage each player did to this to this underling. Null is used for environmental or other non-player damage

	public EntityUnderling(World par1World)
	{
		super(par1World);
	}

	@Override
	protected void initEntityAI()
	{
		attackEntitySelector = new EntityListFilter(new ArrayList<>());
		attackEntitySelector.entityList.add(EntityPlayerMP.class);

		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, getWanderSpeed()));
		tasks.addTask(5, new EntityAIWander(this, getWanderSpeed()));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(7, new EntityAILookIdle(this));

		targetTasks.addTask(1, new EntityAIHurtByTargetAllied(this, underlingSelector));
		targetTasks.addTask(2, new EntityAINearestAttackableTargetWithHeight(this, EntityLivingBase.class, 128.0F, 2, true, false, attackEntitySelector));
	}

	protected abstract double getWanderSpeed();

	@Override
	public void setAttackTarget(EntityLivingBase par1EntityLivingBase)
	{
		super.setAttackTarget(par1EntityLivingBase);
		if (par1EntityLivingBase != null)
		{
			this.addEnemy(par1EntityLivingBase.getClass());
		}
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataManager.register(GRIST_TYPE, MinestuckGrist.artifact.getRegistryName().toString());
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setString("type", getGristType().getRegistryName().toString());
		tagCompound.setBoolean("spawned", fromSpawner);
		if (hasHome())
		{
			NBTTagCompound nbt = new NBTTagCompound();
			BlockPos home = getHomePosition();
			nbt.setInteger("homeX", home.getX());
			nbt.setInteger("homeY", home.getY());
			nbt.setInteger("homeZ", home.getZ());
			nbt.setInteger("maxHomeDistance", (int) getMaximumHomeDistance());
			tagCompound.setTag("homePos", nbt);
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompound)
	{
		if (tagCompound.hasKey("type", 8))
		{
			Grist grist = Grist.getTypeFromString(tagCompound.getString("type"));
			if (grist != null)
				applyGristType(grist, false);
			else
				Debug.warnf("Unable to load grist type \"%s\" when reading underling from nbt!", tagCompound.getString("type"));
		}
		else applyGristType(SburbHandler.getUnderlingType(this), true);
		super.readEntityFromNBT(tagCompound);

		fromSpawner = tagCompound.getBoolean("spawned");

		if (tagCompound.hasKey("homePos", 10))
		{
			NBTTagCompound nbt = tagCompound.getCompoundTag("homePos");
			BlockPos pos = new BlockPos(nbt.getInteger("homeX"), nbt.getInteger("homeY"), nbt.getInteger("homeZ"));
			setHomePosAndDistance(pos, nbt.getInteger("maxHomeDistance"));
		}
	}

	public void applyGristType(Grist type, boolean fullHeal)
	{
		type = Objects.requireNonNull(type);
		if (type.getRarity() == 0)    //Utility grist type
			type = SburbHandler.getUnderlingType(this);

		dataManager.set(GRIST_TYPE, type.getRegistryName().toString());

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.getMaximumHealth());
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(this.getAttackDamage());
		if (fullHeal)
			this.setHealth(this.getMaxHealth());
	}

	protected abstract double getAttackDamage();

	@Override
	protected boolean canDespawn()
	{
		return !this.hasHome();
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingData)
	{

		if (!(livingData instanceof UnderlingData))
		{
			if (this.getGristType() == MinestuckGrist.artifact)
				applyGristType(SburbHandler.getUnderlingType(this), true);
			livingData = new UnderlingData(this.getGristType());
		}
		else
		{
			applyGristType(((UnderlingData) livingData).type, true);
		}

		return super.onInitialSpawn(difficulty, livingData);
	}

	public void addEnemy(Class<? extends EntityLivingBase> enemyClass)
	{
		if (!attackEntitySelector.entityList.contains(enemyClass) && !underlingSelector.entityList.contains(enemyClass))
		{
			attackEntitySelector.entityList.add(enemyClass);
		}
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);

		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue((double) (this.getKnockbackResistance()));
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.getWanderSpeed());
	}

	protected abstract float getKnockbackResistance();

	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();
	}

	@Override
	public String getTexture()
	{
		return "textures/mobs/underlings/" + getGristType().getName() + '_' + getUnderlingName() + ".png";
	}

	@Override
	protected void onDeathUpdate()
	{
		super.onDeathUpdate();
		if (this.deathTime == 20 && !this.world.isRemote)
		{

			UnderlingSpoilsEvent event = new UnderlingSpoilsEvent(this, lastSource, getGristSpoils());
			MinecraftForge.EVENT_BUS.post(event);
			GristSet grist = event.getSpoils();

			if (grist == null)
				return;
			if (fromSpawner)
				grist.scaleGrist(0.5F);

			for (GristAmount gristType : grist.getArray())
				this.world.spawnEntity(new EntityGrist(world, randX(), this.posY, randZ(), gristType));

			for (ItemStack item : event.getDrops())
			{
				this.world.spawnEntity(new EntityItem(world, randX(), this.posY, randZ(), item));
			}

			if (this.rand.nextInt(4) == 0)
				this.world.spawnEntity(new EntityVitalityGel(world, randX(), this.posY, randZ(), this.getVitalityGel()));
		}
	}

	//used when getting how much grist should be dropped on death
	public abstract GristSet getGristSpoils();

	protected abstract int getVitalityGel();

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		lastSource = source;
		return super.attackEntityFrom(source, amount);
	}

	@Override
	public boolean attackEntityAsMob(Entity entityIn)
	{
		boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
		return flag;
	}

	private double randX()
	{
		return this.posX + this.rand.nextDouble() * this.width - this.width / 2;
	}

	private double randZ()
	{
		return this.posZ + this.rand.nextDouble() * this.width - this.width / 2;
	}

	@Override
	public String getName()
	{
		return I18n.translateToLocalFormatted("entity.minestuck." + getUnderlingName() + ".type", getGristType().getDisplayName());
	}

	public Grist getGristType()
	{
		return Grist.getTypeFromString(dataManager.get(GRIST_TYPE));
	}

	protected abstract String getUnderlingName();

	@Override
	public boolean getCanSpawnHere()
	{
		return this.world.getDifficulty() != EnumDifficulty.PEACEFUL && super.getCanSpawnHere();
	}

	@Override
	public void writeSpawnData(ByteBuf buffer)
	{
		buffer.writeInt(getGristType().getId());
	}

	@Override
	public void readSpawnData(ByteBuf additionalData)
	{
		applyGristType(Grist.REGISTRY.getValue(additionalData.readInt()), false);
		this.textureResource = new ResourceLocation("minestuck", this.getTexture());
	}

	public void onEntityDamaged(DamageSource source, float amount)
	{
		EntityPlayerMP player = null;
		if (source.getTrueSource() instanceof EntityPlayerMP)
			player = (EntityPlayerMP) source.getTrueSource();
		if (damageMap.containsKey(player))
			damageMap.put(player, damageMap.get(player) + amount);
		else damageMap.put(player, (double) amount);
	}

	protected void computePlayerProgress(int progress)
	{
		double totalDamage = 0;
		for (Double i : damageMap.values())
			totalDamage += i;
		if (totalDamage < this.getMaxHealth())
			totalDamage = this.getMaxHealth();

		int maxProgress = (int) (progress * maxSharedProgress);
		damageMap.remove(null);
		EntityPlayerMP[] playerList = damageMap.keySet().toArray(new EntityPlayerMP[damageMap.size()]);
		double[] modifiers = new double[playerList.length];
		double totalModifier = 0;

		for (int i = 0; i < playerList.length; i++)
		{
			double f = damageMap.get(playerList[i]) / totalDamage;
			modifiers[i] = 2 * f - f * f;
			totalModifier += modifiers[i];
		}

		Debug.debugf("%s players are splitting on %s progress from %s", playerList.length, progress, getUnderlingName());
		if (totalModifier > maxSharedProgress)
			for (int i = 0; i < playerList.length; i++)
				Echeladder.increaseProgress(playerList[i], (int) (maxProgress * modifiers[i] / totalModifier));
		else
			for (int i = 0; i < playerList.length; i++)
				Echeladder.increaseProgress(playerList[i], (int) (progress * modifiers[i]));
	}

	protected static class UnderlingData implements IEntityLivingData
	{
		public final Grist type;

		public UnderlingData(Grist type)
		{
			this.type = type;
		}
	}

}
