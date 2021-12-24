package com.mraof.minestuck.inventory.captchalouge;

import com.mraof.minestuck.client.gui.captchalogue.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.ModusGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.SylladexGuiHandler;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.util.AlchemyUtils;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.LinkedList;

public interface ISylladex
{
	ICaptchalogueable get(int[] slots, int i, boolean asCard);
	boolean canGet(int[] slots, int i);
	ICaptchalogueable peek(int[] slots, int i);
	ICaptchalogueable tryGetEmptyCard(int[] slots, int i);
	void addCard(ICaptchalogueable object);
	void put(ICaptchalogueable object, EntityPlayer player);
	void grow(ICaptchalogueable object);
	void eject(EntityPlayer player);
	void ejectAll(EntityPlayer player, boolean asCards, boolean onlyFull);
	int getFreeSlots();
	int getTotalSlots();
	NBTTagCompound writeToNBT();
	@SideOnly(Side.CLIENT)
	ArrayList<ModusGuiContainer> generateSubContainers(ArrayList<CardGuiContainer.CardTextureIndex[]> textureIndices);

	class Sylladex implements ISylladex
	{
		private final LinkedList<ISylladex> sylladices; // Keep these as LL instead of array because we have to add/remove cards :/
		private final ArrayList<Modus> modi = new ArrayList<>();
		private final ArrayList<ModusGuiContainer> containers = new ArrayList<>();

		@SideOnly(Side.CLIENT)
		private SylladexGuiHandler gui;

		public Sylladex(LinkedList<ISylladex> sylladices, Modus... modi)
		{
			this.sylladices = sylladices;
			this.modi.addAll(Arrays.asList(modi));
		}

		public Sylladex(int cards, Modus... modi)
		{
			this(new LinkedList<>(), modi);
			for (int i = 0; i < cards; i++)
				addCard(null);
		}

		public Sylladex(Sylladex settings)
		{
			this.sylladices = new LinkedList<>();
			this.modi.addAll(settings.modi);
			this.sylladices.add(settings.sylladices.get(0) instanceof Sylladex ? new Sylladex((Sylladex)settings.sylladices.get(0)) : new CardSylladex(this));
		}

		public Sylladex(NBTTagCompound nbt)
		{
			this.sylladices = new LinkedList<>();
			readFromNBT(nbt);
		}

		@Override
		public ICaptchalogueable get(int[] slots, int i, boolean asCard)
		{
			for (Modus modus : modi)
				if (!(asCard && getTotalSlots() == 1) && modus.canGet(sylladices, slots, i))
				{
					ICaptchalogueable rtn = modus.get(sylladices, slots, i, asCard);
					if (asCard)
						cleanUpMarkedCards(slots,  i); // TODO: the queuestack below an empty one would pop off into it
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
		public ICaptchalogueable peek(int[] slots, int i)
		{
			return sylladices.get(slots[i]).peek(slots, i + 1);
		}

		@Override
		public ICaptchalogueable tryGetEmptyCard(int[] slots, int i)
		{
			if (getTotalSlots() == 1)
				return null;

			ICaptchalogueable object = sylladices.get(slots[i]).tryGetEmptyCard(slots, i + 1);
			cleanUpMarkedCards(slots, i + 1);
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
			sylladices.addLast(new CardSylladex(this, object));
		}

		@Override
		public void put(ICaptchalogueable object, EntityPlayer player)
		{
			modi.get(0).put(sylladices, object, player);
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
		public void ejectAll(EntityPlayer player, boolean asCards, boolean onlyFull)
		{
			for (int i =  0; i < sylladices.size(); i++)
			{
				sylladices.get(i).ejectAll(player, asCards, onlyFull);
				if (sylladices.get(i).getTotalSlots() == 0)
					sylladices.remove(i--);
			}
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
				slots += sylladex.getTotalSlots();
			return slots;
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
				this.sylladices.add(sylladexTag.hasKey("modusTypes") ? new Sylladex(sylladexTag) : new CardSylladex(this, sylladexTag));
			}
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ArrayList<ModusGuiContainer> generateSubContainers(ArrayList<CardGuiContainer.CardTextureIndex[]> textureIndices)
		{
			containers.clear();

			CardGuiContainer.CardTextureIndex[] thisIndices = new CardGuiContainer.CardTextureIndex[modi.size()];
			for (int i = 0; i < thisIndices.length; i++)
				thisIndices[i] = modi.get(i).getCardTextureIndex();
			textureIndices.add(thisIndices);

			for (ISylladex sylladex : sylladices)
				containers.add(modi.get(0).getGuiContainer(textureIndices, sylladex));
			return containers;
		}

		@SideOnly(Side.CLIENT)
		public String getName(boolean plural)
		{
			StringBuilder name = new StringBuilder();
			for (int i  = 0; i < modi.size(); i++)
				name.append(I18n.format("modus." + modi.get(i).getUnlocalizedName() + (i == 0 ? ".prefix" : ".suffix") + (plural && i == modi.size() - 1 ? ".plural" : ".singular")));
			if (sylladices.getFirst() instanceof Sylladex)
				return I18n.format("modus.nameCombob", name.toString(), ((Sylladex)sylladices.getFirst()).getName(true));
			else
				return name.toString();
		}
		
		@SideOnly(Side.CLIENT)
		public String getName()
		{
			return getName(false);
		}

		@SideOnly(Side.CLIENT)
		public SylladexGuiHandler getGuiHandler()
		{
			if (gui == null)
				gui = new SylladexGuiHandler(this);
			return gui;
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
			this(owner, (ICaptchalogueable) null);
		}

		public CardSylladex(Sylladex owner, NBTTagCompound nbt)
		{
			this(owner);
			readFromNBT(nbt);
		}

		@Override
		public ICaptchalogueable get(int[] slots, int i, boolean asCard)
		{
			checkSlots(slots, i);
			ICaptchalogueable object = asCard ?
											   new CaptchalogueableItemStack(AlchemyUtils.setCardModi(this.object.captchalogueIntoCardItem(), owner.modi)) : // Shouldn't ever ask for empty cards
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
		public ICaptchalogueable peek(int[] slots, int i)
		{
			checkSlots(slots, i);
			return object;
		}

		@Override
		public ICaptchalogueable tryGetEmptyCard(int[] slots, int i)
		{
			checkSlots(slots, i);
			if (object == null)
				markedForDeletion = true;
			return object == null ? new CaptchalogueableItemStack(AlchemyUtils.setCardModi(new ItemStack(MinestuckItems.captchaCard), owner.modi)) : null;
		}

		@Override
		public void addCard(ICaptchalogueable object)
		{
			throw new ClassCastException("Attempted to add a card to a card");
		}

		@Override
		public void put(ICaptchalogueable object, EntityPlayer player)
		{
			if (this.object != null || markedForDeletion)
				throw new IllegalStateException("Attempted to put an item into a full card");
			this.object = object;
		}

		@Override
		public void grow(ICaptchalogueable other)
		{
			if (object != null)
				this.object.grow(other);
		}

		@Override
		public void eject(EntityPlayer player)
		{
			get(null, 0, false).eject(owner, player);
		}

		@Override
		public void ejectAll(EntityPlayer player, boolean asCards, boolean onlyFull)
		{
			if (!onlyFull || object != null)
				get(null, 0, asCards).eject(null, player);
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
			if (slots != null && i >= slots.length)
				throw new IndexOutOfBoundsException("Attempted to fetch a numbered slot from a card");
		}

		@Override
		public NBTTagCompound writeToNBT()
		{
			if (object != null)
			{
				NBTTagCompound nbt = object.writeToNBT();
				nbt.setString("class", object.getClass().getName());
				return nbt;
			}
			else
			{
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setString("class", "null");
				return nbt;
			}
		}

		public void readFromNBT(NBTTagCompound nbt)
		{
			String className = nbt.getString("class");
			if (!className.equals("null"))
			{
				try
				{
					object = (ICaptchalogueable) Class.forName(className).newInstance();
				}
				catch (ClassNotFoundException | InstantiationException | IllegalAccessException e)
				{
					throw new RuntimeException(e);
				}
				object.readFromNBT(nbt);
			}
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ArrayList<ModusGuiContainer> generateSubContainers(ArrayList<CardGuiContainer.CardTextureIndex[]> textureIndices)
		{
			containers.clear();
			containers.add(new CardGuiContainer(textureIndices.get(Math.min(owner.sylladices.indexOf(this), textureIndices.size() - 1)), object));
			return containers;
		}
	}
}
