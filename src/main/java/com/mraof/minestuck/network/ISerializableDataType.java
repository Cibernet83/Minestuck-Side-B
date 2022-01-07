package com.mraof.minestuck.network;

import com.mraof.minestuck.util.MovementInput;
import com.mraof.minestuck.util.Vec4;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.UUID;
import java.util.function.Function;

public interface ISerializableDataType<T>
{
	void serialize(NBTTagCompound tag);
	void serialize(ByteBuf buf);
	T getValue();
	default void initialize(World world) { }

	LinkedHashMap<Class<? extends ISerializableDataType>, Tuple<Function<NBTTagCompound, ? extends ISerializableDataType>, Function<ByteBuf, ? extends ISerializableDataType>>> REGISTRY = new LinkedHashMap<>();
	Tuple<Boolean, Boolean> COOL_BEANS = new Tuple<Boolean, Boolean>(false, false)
	{{
		register(IntegerData.class, IntegerData::new, IntegerData::new);
		register(BooleanData.class, BooleanData::new, BooleanData::new);
		register(StringData.class, StringData::new, StringData::new);
		register(Vec4Data.class, Vec4Data::new, Vec4Data::new);
		register(MovementInputData.class, MovementInputData::new, MovementInputData::new);
		register(EntityData.class, EntityData::new, EntityData::new);
		register(DataTypeData.class, DataTypeData::new, DataTypeData::new);
	}};

	static <T extends ISerializableDataType> void register(Class<T> dataType, Function<NBTTagCompound, T> tagReader, Function<ByteBuf, T> byteBufReader)
	{
		if(REGISTRY.containsKey(dataType))
			throw new IllegalArgumentException(dataType + " already exists.");
		REGISTRY.put(dataType, new Tuple<>(tagReader, byteBufReader));
	}


	static <T extends ISerializableDataType> T deserialize(NBTTagCompound tag, Class<T> dataType)
	{
		return (T)REGISTRY.get(dataType).getFirst().apply(tag);
	}

	static <T extends ISerializableDataType> T deserialize(ByteBuf buf, Class<T> dataType)
	{
		return (T)REGISTRY.get(dataType).getSecond().apply(buf);
	}


	class IntegerData implements ISerializableDataType<Integer>
	{
		private final int value;

		public IntegerData(int value)
		{
			this.value = value;
		}

		private IntegerData(NBTTagCompound tag)
		{
			this.value = tag.getInteger("Value");
		}

		private IntegerData(ByteBuf data)
		{
			this.value = data.readInt();
		}

		@Override
		public void serialize(NBTTagCompound tag)
		{
			tag.setInteger("Value", value);
		}

		@Override
		public void serialize(ByteBuf buf)
		{
			buf.writeInt(value);
		}

		@Override
		public Integer getValue()
		{
			return value;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (!(obj instanceof ISerializableDataType.IntegerData))
				return false;
			IntegerData other = (IntegerData) obj;

			return this.value == other.value;
		}
	}

	class BooleanData implements ISerializableDataType<Boolean>
	{
		private final boolean value;

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
			tag.setBoolean("Value", value);
		}

		@Override
		public void serialize(ByteBuf buf)
		{
			buf.writeBoolean(value);
		}

		@Override
		public Boolean getValue()
		{
			return value;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (!(obj instanceof BooleanData))
				return false;
			BooleanData other = (BooleanData) obj;

			return this.value == other.value;
		}
	}

	class StringData implements ISerializableDataType<String>
	{
		private final String value;

		public StringData(String value)
		{
			this.value = value;
		}

		private StringData(NBTTagCompound tag)
		{
			this.value = tag.getString("Value");
		}

		private StringData(ByteBuf data)
		{
			this.value = ByteBufUtils.readUTF8String(data);
		}

		@Override
		public void serialize(NBTTagCompound tag)
		{
			tag.setString("Value", value);
		}

		@Override
		public void serialize(ByteBuf buf)
		{
			ByteBufUtils.writeUTF8String(buf, value);
		}

		@Override
		public String getValue()
		{
			return value;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (!(obj instanceof StringData))
				return false;
			StringData other = (StringData) obj;

			return this.value.equals(other.value);
		}
	}

	class Vec4Data implements ISerializableDataType<Vec4>
	{
		private final Vec4 value;

		public Vec4Data(Vec3d position, int dimension)
		{
			this.value = new Vec4(position.x, position.y, position.z, dimension);
		}

		private Vec4Data(NBTTagCompound tag)
		{
			this.value = new Vec4(
					tag.getDouble("PositionX"),
					tag.getDouble("PositionY"),
					tag.getDouble("PositionZ"),
					tag.getInteger("Dimension")
			);
		}

		private Vec4Data(ByteBuf data)
		{
			this.value = new Vec4(
					data.readDouble(),
					data.readDouble(),
					data.readDouble(),
					data.readInt()
			);
		}

		@Override
		public void serialize(NBTTagCompound tag)
		{
			tag.setDouble("PositionX", value.getX());
			tag.setDouble("PositionY", value.getY());
			tag.setDouble("PositionZ", value.getZ());
			tag.setInteger("Dimension", value.getDimension());
		}

		@Override
		public void serialize(ByteBuf buf)
		{
			buf.writeDouble(value.getX());
			buf.writeDouble(value.getY());
			buf.writeDouble(value.getZ());
			buf.writeInt(value.getDimension());
		}

		@Override
		public Vec4 getValue()
		{
			return value;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (!(obj instanceof Vec4Data))
				return false;
			Vec4Data other = (Vec4Data) obj;

			return this.value.equals(other.value);
		}
	}

	class MovementInputData implements ISerializableDataType<MovementInput>
	{
		private final MovementInput value;

		public MovementInputData(float moveStrafe, float moveForward, boolean jump, boolean sneak)
		{
			this.value = new MovementInput(moveStrafe, moveForward, jump, sneak);
		}

		private MovementInputData(NBTTagCompound tag)
		{
			this.value = new MovementInput(tag.getFloat("MoveStrafe"), tag.getFloat("MoveForward"), tag.getBoolean("Jump"), tag.getBoolean("Sneak"));
		}

		private MovementInputData(ByteBuf data)
		{
			this.value = new MovementInput(data.readFloat(), data.readFloat(), data.readBoolean(), data.readBoolean());
		}

		@Override
		public void serialize(NBTTagCompound tag)
		{
			tag.setFloat("MoveStrafe", value.getMoveStrafe());
			tag.setFloat("MoveForward", value.getMoveForward());
			tag.setBoolean("Jump", value.isJumping());
			tag.setBoolean("Sneak", value.isSneaking());
		}

		@Override
		public void serialize(ByteBuf buf)
		{
			buf.writeFloat(value.getMoveStrafe());
			buf.writeFloat(value.getMoveForward());
			buf.writeBoolean(value.isJumping());
			buf.writeBoolean(value.isSneaking());
		}

		@Override
		public MovementInput getValue()
		{
			return value;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (!(obj instanceof MovementInputData))
				return false;
			MovementInputData other = (MovementInputData) obj;

			return this.value.equals(other.value);
		}
	}

	class EntityData implements ISerializableDataType<EntityLivingBase>
	{
		// no final :cryign:
		private EntityLivingBase value;

		private UUID uuid;
		private int id;

		public EntityData(EntityLivingBase value)
		{
			this.value = value;
		}

		private EntityData(NBTTagCompound tag)
		{
			this.uuid = UUID.fromString(tag.getString("UUID"));
		}

		private EntityData(ByteBuf data)
		{
			this.id = data.readInt();
		}

		@Override
		public void serialize(NBTTagCompound tag)
		{
			tag.setString("UUID", value.getUniqueID().toString());
		}

		@Override
		public void serialize(ByteBuf buf)
		{
			buf.writeInt(value.getEntityId());
		}

		@Override
		public void initialize(World world)
		{
			if (uuid == null)
				value = (EntityLivingBase) world.getEntityByID(id);
			else
				value = world.getEntities(EntityLivingBase.class, (entity) -> entity.getUniqueID().equals(uuid)).get(0);
		}

		@Override
		public EntityLivingBase getValue()
		{
			return value;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (!(obj instanceof EntityData))
				return false;
			EntityData other = (EntityData) obj;

			return this.value.equals(other.value);
		}
	}

	class DataTypeData implements ISerializableDataType<ISerializableDataType>
	{
		private final ISerializableDataType value;

		public <T extends ISerializableDataType> DataTypeData(T value)
		{
			this.value = value;
		}

		private DataTypeData(NBTTagCompound tag)
		{
			String clazzName = tag.getString("Class");
			if (clazzName.equals("null"))
				this.value = null;
			else
				try
				{
					this.value = deserialize(tag.getCompoundTag("Data"), (Class<? extends ISerializableDataType>) Class.forName(clazzName));
				}
				catch (ClassNotFoundException e)
				{
					throw new RuntimeException("yeah you gotta throw out the whole data lol idk how to catch this from here safely", e);
				}
		}

		private DataTypeData(ByteBuf data)
		{
			int clazzIndex = data.readInt();
			if (clazzIndex > 0)
				this.value = deserialize(data, REGISTRY.keySet().toArray(new Class[0])[clazzIndex]);
			else
				this.value = null;
		}

		@Override
		public void serialize(NBTTagCompound tag)
		{
			if (value == null)
				tag.setString("Class", "null");
			else
			{
				NBTTagCompound valueTag = new NBTTagCompound();
				value.serialize(valueTag);
				tag.setTag("Data", valueTag);
				tag.setString("Class", value.getClass().getName());
			}
		}

		@Override
		public void serialize(ByteBuf buf)
		{
			if (value == null)
				buf.writeInt(-1);
			else
			{
				buf.writeInt(Arrays.asList(REGISTRY.keySet().toArray(new Class[0])).indexOf(value.getClass()));
				value.serialize(buf);
			}
		}

		@Override
		public void initialize(World world)
		{
			if (value != null)
				value.initialize(world);
		}

		@Override
		public ISerializableDataType getValue()
		{
			return value;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (!(obj instanceof EntityData))
				return false;
			EntityData other = (EntityData) obj;

			return this.value != null && this.value.equals(other.value);
		}
	}
}
