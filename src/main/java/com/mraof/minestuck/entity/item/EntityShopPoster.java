package com.mraof.minestuck.entity.item;

import com.mraof.minestuck.entity.EntityFrog;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Set;

public class EntityShopPoster extends EntityHangingArt<EntityShopPoster.ShopArt>
{
	private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntityFrog.class, DataSerializers.VARINT);
	protected int dmg = 0;

	public EntityShopPoster(World worldIn)
	{
		super(worldIn);
	}

	public EntityShopPoster(World worldIn, BlockPos pos, EnumFacing facing, ItemStack stack, int meta)
	{
		super(worldIn, pos, facing, stack, meta, false);
		setType(meta);
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataManager.register(TYPE, 0);

	}

	@Override
	public Set<ShopArt> getArtSet()
	{
		return EnumSet.allOf(ShopArt.class);
	}

	//NBT
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);
		compound.setInteger("Type", this.getType());
	}

	public int getType()
	{
		return this.dataManager.get(TYPE);
	}

	private void setType(int i)
	{
		this.dataManager.set(TYPE, i);
	}

	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);

		if (compound.hasKey("Type")) setType(compound.getInteger("Type"));
		else setType(0);
	}

	@Override
	public ItemStack getStackDropped()
	{
		return ItemStack.EMPTY; //return new ItemStack(MinestuckItems.shopPoster, 1, getType());
	}

	@Override
	public ShopArt getDefault()
	{
		return ShopArt.FRAYMOTIFS;
	}

	public enum ShopArt implements EntityHangingArt.IArt
	{
		FRAYMOTIFS("Fraymotifs", 48, 48, 0, 0),
		FOOD("Food", 48, 48, 48, 0),
		HATS("Hats", 48, 48, 96, 0),
		GENERAL("General", 48, 48, 144, 0),
		CANDY("Candy", 48, 48, 0, 48);

		private final String title;
		private final int sizeX, sizeY;
		private final int offsetX, offsetY;

		ShopArt(String title, int sizeX, int sizeY, int offsetX, int offsetY)
		{
			this.title = title;
			this.sizeX = sizeX;
			this.sizeY = sizeY;
			this.offsetX = offsetX;
			this.offsetY = offsetY;
		}


		@Override
		public String getTitle()
		{return title;}

		@Override
		public int getSizeX()
		{return sizeX;}

		@Override
		public int getSizeY()
		{return sizeY;}

		@Override
		public int getOffsetX()
		{return offsetX;}

		@Override
		public int getOffsetY()
		{return offsetY;}
	}
}