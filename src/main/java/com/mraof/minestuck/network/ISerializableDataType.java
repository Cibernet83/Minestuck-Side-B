package com.mraof.minestuck.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.UUID;
import java.util.function.Function;

public interface ISerializableDataType
{
	void serialize(NBTTagCompound tag);
	void serialize(ByteBuf data);
	default ISerializableDataType initialize(World world)
	{
		return this;
	}

	enum DataType
	{
		INT(IntData::new, IntData::new),
		BOOLEAN(BooleanData::new, BooleanData::new),
		VEC4(Vec4Data::new, Vec4Data::new),
		MOVEMENT_INPUT(MovementInputData::new, MovementInputData::new),
		ENTITY(EntityIDData::new, EntityIDData::new),
		;

		public Function<NBTTagCompound, ? extends ISerializableDataType> tagReader;
		public Function<ByteBuf, ? extends ISerializableDataType> byteBufReader;
		DataType(Function<NBTTagCompound, ? extends ISerializableDataType> tagReader, Function<ByteBuf, ? extends ISerializableDataType> byteBufReader)
		{
			this.tagReader = tagReader;
			this.byteBufReader = byteBufReader;
		}
	}

	static ISerializableDataType deserialize(NBTTagCompound tag)
	{
		DataType type = DataType.values()[tag.getByte("DataType")];
		return type.tagReader.apply(tag);
	}

	static ISerializableDataType deserialize(ByteBuf data)
	{
		DataType type = DataType.values()[data.readByte()];
		return type.byteBufReader.apply(data);
	}


	class IntData implements ISerializableDataType
	{
		public final int value;

		public IntData(int value)
		{
			this.value = value;
		}

		private IntData(NBTTagCompound tag)
		{
			this.value = tag.getInteger("Value");
		}

		private IntData(ByteBuf data)
		{
			this.value = data.readInt();
		}

		@Override
		public void serialize(NBTTagCompound tag)
		{
			tag.setByte("BadgeEffectType", (byte) DataType.INT.ordinal());
			tag.setInteger("Value", value);
		}

		@Override
		public void serialize(ByteBuf data)
		{
			data.writeByte(DataType.INT.ordinal());
			data.writeInt(value);
		}

		@Override
		public boolean equals(Object obj)
		{
			if (!(obj instanceof IntData))
				return false;
			IntData effect = (IntData) obj;

			return this.value == effect.value;
		}
	}

	class BooleanData implements ISerializableDataType
	{
		public final boolean value;

		public BooleanData(boolean value)
		{
			this.value = value;
		}

		private BooleanData(NBTTagCompound tag)
		{
			this.value = tag.getBoolean("Value");
		}

		private BooleanData(ByteBuf data)
		{
			this.value = data.readBoolean();
		}

		@Override
		public void serialize(NBTTagCompound tag)
		{
			tag.setByte("BadgeEffectType", (byte) DataType.BOOLEAN.ordinal());
			tag.setBoolean("Value", value);
		}

		@Override
		public void serialize(ByteBuf data)
		{
			data.writeByte(DataType.BOOLEAN.ordinal());
			data.writeBoolean(value);
		}

		@Override
		public boolean equals(Object obj)
		{
			if (!(obj instanceof BooleanData))
				return false;
			BooleanData effect = (BooleanData) obj;

			return this.value == effect.value;
		}
	}

	class Vec4Data implements ISerializableDataType
	{
		public final Vec3d position;
		public final int dimension;

		public Vec4Data(Vec3d position, int dimension)
		{
			this.position = position;
			this.dimension = dimension;
		}

		private Vec4Data(NBTTagCompound tag)
		{
			this.position = new Vec3d(
					tag.getDouble("PositionX"),
					tag.getDouble("PositionY"),
					tag.getDouble("PositionZ")
			);
			this.dimension = tag.getInteger("Dimension");
		}

		private Vec4Data(ByteBuf data)
		{
			this.position = new Vec3d(
					data.readDouble(),
					data.readDouble(),
					data.readDouble()
			);
			this.dimension = data.readInt();
		}

		@Override
		public void serialize(NBTTagCompound tag)
		{
			tag.setByte("BadgeEffectType", (byte) DataType.VEC4.ordinal());
			tag.setDouble("PositionX", position.x);
			tag.setDouble("PositionY", position.y);
			tag.setDouble("PositionZ", position.z);
			tag.setInteger("Dimension", dimension);
		}

		@Override
		public void serialize(ByteBuf data)
		{
			data.writeByte(DataType.VEC4.ordinal());
			data.writeDouble(position.x);
			data.writeDouble(position.y);
			data.writeDouble(position.z);
			data.writeInt(dimension);
		}

		@Override
		public boolean equals(Object obj)
		{
			if (!(obj instanceof Vec4Data))
				return false;
			Vec4Data effect = (Vec4Data) obj;

			return this.position.equals(effect.position) && this.dimension == effect.dimension;
		}
	}

	class MovementInputData implements ISerializableDataType
	{
		public final float moveStrafe;
		public final float moveForward;
		public final boolean jump;
		public final boolean sneak;

		public MovementInputData(float moveStrafe, float moveForward, boolean jump, boolean sneak)
		{
			this.moveStrafe = moveStrafe;
			this.moveForward = moveForward;
			this.jump = jump;
			this.sneak = sneak;
		}

		private MovementInputData(NBTTagCompound tag)
		{
			this.moveStrafe = tag.getFloat("MoveStrafe");
			this.moveForward = tag.getFloat("MoveForward");
			this.jump = tag.getBoolean("Jump");
			this.sneak = tag.getBoolean("Sneak");
		}

		private MovementInputData(ByteBuf data)
		{
			this.moveStrafe = data.readFloat();
			this.moveForward = data.readFloat();
			this.jump = data.readBoolean();
			this.sneak = data.readBoolean();
		}

		@Override
		public void serialize(NBTTagCompound tag)
		{
			tag.setByte("BadgeEffectType", (byte) DataType.MOVEMENT_INPUT.ordinal());
			tag.setFloat("MoveStrafe", moveStrafe);
			tag.setFloat("MoveForward", moveForward);
			tag.setBoolean("Jump", jump);
			tag.setBoolean("Sneak", sneak);
		}

		@Override
		public void serialize(ByteBuf data)
		{
			data.writeByte(DataType.MOVEMENT_INPUT.ordinal());
			data.writeFloat(moveStrafe);
			data.writeFloat(moveForward);
			data.writeBoolean(jump);
			data.writeBoolean(sneak);
		}

		@Override
		public boolean equals(Object obj)
		{
			if (!(obj instanceof MovementInputData))
				return false;
			MovementInputData effect = (MovementInputData) obj;

			return this.moveStrafe == effect.moveStrafe && this.moveForward == effect.moveForward && this.jump == effect.jump && this.sneak == effect.sneak;
		}
	}

	class EntityIDData implements ISerializableDataType // Helper class to get around the fromBytes/execute limits
	{
		private final int id;
		private final UUID uuid;

		private EntityIDData(ByteBuf data)
		{
			this.id = data.readInt();
			this.uuid = null;
		}

		private EntityIDData(NBTTagCompound tag)
		{
			this.id = -1;
			this.uuid = UUID.fromString(tag.getString("UUID"));
		}

		@Override
		public void serialize(NBTTagCompound tag)
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public void serialize(ByteBuf data)
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public ISerializableDataType initialize(World world)
		{
			if (uuid == null)
				return new EntityData((EntityLivingBase) world.getEntityByID(id));
			else
				return new EntityData(world.getEntities(EntityLivingBase.class, (entity) -> entity.getUniqueID().equals(uuid)).get(0));
		}
	}

	class EntityData implements ISerializableDataType
	{
		public final EntityLivingBase value;

		public EntityData(EntityLivingBase value)
		{
			this.value = value;
		}

		@Override
		public void serialize(NBTTagCompound tag)
		{
			tag.setByte("BadgeEffectType", (byte) DataType.ENTITY.ordinal());
			tag.setString("UUID", value.getUniqueID().toString());
		}

		@Override
		public void serialize(ByteBuf data)
		{
			data.writeByte(DataType.ENTITY.ordinal());
			data.writeInt(value.getEntityId());
		}

		@Override
		public boolean equals(Object obj)
		{
			if (!(obj instanceof EntityData))
				return false;
			EntityData effect = (EntityData) obj;

			return this.value.equals(effect.value);
		}
	}
}
