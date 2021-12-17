package com.mraof.minestuck.inventory.captchalouge;

import com.mraof.minestuck.client.gui.captchalogue.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.ModusGuiContainer;
import com.mraof.minestuck.item.MinestuckItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.LinkedList;

public interface ISylladex
{
	ICaptchalogueable get(int[] slots, int i, boolean asCard);
	boolean canGet(int[] slots, int i);
	ICaptchalogueable tryGetEmptyCard(int[] slots, int i);
	void addCard(ICaptchalogueable object);
	boolean put(ICaptchalogueable object, EntityPlayer player);
	void grow(ICaptchalogueable object);
	void eject(EntityPlayer player);
	int getFreeSlots();
	int getTotalSlots();
	ArrayList<ModusGuiContainer> generateSubContainers();
	NBTTagCompound writeToNBT();
	void readFromNBT(NBTTagCompound nbt);

	class Sylladex implements ISylladex
	{
		private final LinkedList<ISylladex> sylladices; // Keep these as LL instead of array because we have to add/remove cards :/
		private final ArrayList<Modus> modi = new ArrayList<>();
		private final ArrayList<ModusGuiContainer> containers = new ArrayList<>();

		public Sylladex(LinkedList<ISylladex> sylladices, Modus... modi)
		{
			this.sylladices = sylladices;
			this.modi.addAll(Arrays.asList(modi));
		}

		private Sylladex()
		{
			sylladices = new LinkedList<>();
		}

		@Override
		public ICaptchalogueable get(int[] slots, int i, boolean asCard)
		{
			for (Modus modus : modi)
				if (!(asCard && getTotalSlots() == 1) && modus.canGet(sylladices, slots, i))
				{
					ICaptchalogueable rtn = modus.get(sylladices, slots, i, asCard);

					if (asCard)
						cleanUpMarkedCards(slots,  i);

					return rtn;
				}
			return null;
		}

		@Override
		public boolean canGet(int[] slots, int i)
		{
			for (Modus modus : modi)
				if (modus.canGet(sylladices, slots, i))
					return true;
			return false;
		}

		@Override
		public ICaptchalogueable tryGetEmptyCard(int[] slots, int i)
		{
			if (getTotalSlots() == 1)
				return null;

			ICaptchalogueable object = sylladices.get(slots[i]).tryGetEmptyCard(slots, i + 1);
			cleanUpMarkedCards(slots, i);
			return object;
		}

		private void cleanUpMarkedCards(int[] slots, int i)
		{
			if (sylladices.get(slots[i]).getTotalSlots() == 0)
			{
				sylladices.remove(slots[i]);
				slots[i] = -1;
			}
		}

		@Override
		public void addCard(ICaptchalogueable object)
		{
			sylladices.add(new CardSylladex(this, object));
		}

		@Override
		public boolean put(ICaptchalogueable object, EntityPlayer player)
		{
			return modi.get(0).put(sylladices, object, player);
		}

		@Override
		public void grow(ICaptchalogueable object)
		{
			modi.get(0).grow(sylladices, object);
		}

		@Override
		public void eject(EntityPlayer player)
		{
			modi.get(0).eject(sylladices, player);
		}

		@Override
		public int getFreeSlots()
		{
			int slots = 0;
			for (ISylladex sylladex : sylladices)
				slots += sylladex.getFreeSlots();
			return slots;
		}

		@Override
		public int getTotalSlots()
		{
			int slots = 0;
			for (ISylladex sylladex : sylladices)
				slots += sylladex.getFreeSlots();
			return slots;
		}

		@Override
		public ArrayList<ModusGuiContainer> generateSubContainers()
		{
			containers.clear();
			for (ISylladex sylladex : sylladices)
				containers.add(modi.get(0).getGuiContainer(sylladex));
			return containers;
		}

		@Override
		public NBTTagCompound writeToNBT()
		{
			NBTTagCompound nbt = new NBTTagCompound();

			NBTTagList modusTypes = new NBTTagList();
			for (Modus modus : modi)
				modusTypes.appendTag(new NBTTagString(modus.getRegistryName().toString()));
			nbt.setTag("modusTypes", modusTypes);

			NBTTagList sylladices = new NBTTagList();
			for (ISylladex sylladex : this.sylladices)
				sylladices.appendTag(sylladex.writeToNBT());
			nbt.setTag("sylladices", sylladices);

			return nbt;
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt)
		{
			modi.clear();
			sylladices.clear();

			NBTTagList modusTypes = nbt.getTagList("modusTypes", 8);
			for (NBTBase modusType : modusTypes)
				modi.add(Modus.REGISTRY.getValue(new ResourceLocation(((NBTTagString)modusType).getString())));

			NBTTagList sylladices = nbt.getTagList("sylladices", 10);
			for (NBTBase sylladexTagBase : sylladices)
			{
				NBTTagCompound sylladexTag = (NBTTagCompound) sylladexTagBase;
				ISylladex sylladex = sylladexTag.hasKey("modusTypes") ? new Sylladex() : new CardSylladex(this);
				sylladex.readFromNBT(sylladexTag);
				this.sylladices.add(sylladex);
			}
		}
	}

	class CardSylladex implements ISylladex
	{
		private final Sylladex owner;
		private ICaptchalogueable object;
		private final ArrayList<ModusGuiContainer> containers = new ArrayList<>();
		private boolean markedForDeletion = false;

		public CardSylladex(Sylladex owner, ICaptchalogueable object)
		{
			this.owner = owner;
			this.object = object;
		}

		public CardSylladex(Sylladex owner)
		{
			this(owner, null);
		}

		@Override
		public ICaptchalogueable get(int[] slots, int i, boolean asCard)
		{
			checkSlots(slots, i);
			ICaptchalogueable object = asCard ?
											   new CaptchalogueableItemStack(this.object.captchalogueIntoCardItem()) : // Shouldn't ever ask for empty cards
											   this.object == null ? new CaptchalogueableItemStack(ItemStack.EMPTY) : this.object;
			this.object = null;
			if (asCard)
				markedForDeletion = true;
			return object;
		}

		@Override
		public boolean canGet(int[] slots, int i)
		{
			checkSlots(slots, i);
			return true;
		}

		@Override
		public ICaptchalogueable tryGetEmptyCard(int[] slots, int i)
		{
			checkSlots(slots, i);
			if (object == null)
				markedForDeletion = true;
			return object == null ? new CaptchalogueableItemStack(new ItemStack(MinestuckItems.captchaCard)) : null;
		}

		@Override
		public void addCard(ICaptchalogueable object)
		{
			throw new ClassCastException("Attempted to add a card to a card");
		}

		@Override
		public boolean put(ICaptchalogueable object, EntityPlayer player)
		{
			if (this.object != null || markedForDeletion)
				return false;
			this.object = object;
			return true;
		}

		@Override
		public void grow(ICaptchalogueable other)
		{
			this.object.grow(other);
		}

		@Override
		public void eject(EntityPlayer player)
		{
			object.eject(owner, player);
		}

		@Override
		public int getFreeSlots()
		{
			return object == null && !markedForDeletion ? 1 : 0;
		}

		@Override
		public int getTotalSlots()
		{
			return markedForDeletion ? 0 : 1;
		}

		private void checkSlots(int[] slots, int i)
		{
			if (markedForDeletion)
				throw new NullPointerException("Attempted to interact from a card marked for deletion");
			if (i >= slots.length)
				throw new IndexOutOfBoundsException("Attempted to retrieve a numbered slot from a card");
		}

		@Override
		public ArrayList<ModusGuiContainer> generateSubContainers()
		{
			containers.clear();
			containers.add(new CardGuiContainer());
			return containers;
		}

		@Override
		public NBTTagCompound writeToNBT()
		{
			NBTTagCompound nbt = object.writeToNBT();
			nbt.setString("class", object.getClass().getName());
			return nbt;
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt)
		{
			try
			{
				object = (ICaptchalogueable) Class.forName(nbt.getString("class")).newInstance();
			}
			catch (ClassNotFoundException | InstantiationException | IllegalAccessException e)
			{
				throw new RuntimeException(e);
			}
			object.readFromNBT(nbt);
		}
	}
}
