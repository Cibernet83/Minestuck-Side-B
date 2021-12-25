package com.mraof.minestuck.inventory.captchalouge;

import com.mraof.minestuck.client.gui.captchalogue.CardGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.ModusGuiContainer;
import com.mraof.minestuck.client.gui.captchalogue.SylladexGuiHandler;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.util.SylladexUtils;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public interface ISylladex
{
	ICaptchalogueable get(int[] slots, int i, boolean asCard);
	boolean canGet(int[] slots, int i);
	ICaptchalogueable peek(int[] slots, int i);
	ICaptchalogueable tryGetEmptyCard(int[] slots, int i);
	void addCard(int index, ICaptchalogueable object, EntityPlayer player);
	void put(ICaptchalogueable object, EntityPlayer player);
	void grow(ICaptchalogueable object);
	void eject(EntityPlayer player);
	void ejectAll(EntityPlayer player, boolean asCards, boolean onlyFull);
	int getFreeSlots();
	int getTotalSlots();
	NBTTagCompound writeToNBT();
	@SideOnly(Side.CLIENT)
	ArrayList<ModusGuiContainer> generateSubContainers(ArrayList<CardGuiContainer.CardTextureIndex[]> textureIndices);

	static Sylladex readFromNBT(NBTTagCompound nbt)
	{
		return nbt.getBoolean("isBottom") ? new BottomSylladex(nbt) : new Sylladex(nbt);
	}

	static Sylladex newSylladex(int[] lengths, Modus[][] modi)
	{
		return lengths.length == 0 ? new ISylladex.BottomSylladex(modi[0]) : new ISylladex.Sylladex(lengths, modi, 0);
	}

	class Sylladex implements ISylladex
	{
		protected final LinkedList<ISylladex> sylladices = new LinkedList<>(); // Keep these as LL instead of array because we have to add/remove cards :/
		protected final ArrayList<Modus> modi = new ArrayList<>();
		protected final ArrayList<ModusGuiContainer> guiContainers = new ArrayList<>();
		public boolean autoBalanceNewCards = false;

		@SideOnly(Side.CLIENT)
		private SylladexGuiHandler gui;

		private Sylladex(Modus[] modi)
		{
			Collections.addAll(this.modi, modi);
		}

		private Sylladex(int[] lengths, Modus[][] modi, int index)
		{
			this(modi[index]);
			int length = lengths[index] & 0xff;
			for (int i = 0; i < length; i++)
				this.sylladices.add(index == lengths.length - 1 ? new BottomSylladex(modi[index + 1]) : new Sylladex(lengths, modi, index + 1));
		}

		private Sylladex(NBTTagCompound nbt)
		{
			readFromNBT(nbt);
		}

		@Override
		public ICaptchalogueable get(int[] slots, int i, boolean asCard)
		{
			for (Modus modus : modi)
				if (modus.canGet(sylladices, slots, i))
					return modus.get(sylladices, slots, i, asCard);
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
			return sylladices.get(slots[i]).tryGetEmptyCard(slots, i + 1);
		}

		@Override
		public void addCard(int index, ICaptchalogueable object, EntityPlayer player)
		{
			int leastSlots = 0;
			ISylladex leastSlotsSylladex = null;
			for (ISylladex sylladex : sylladices)
			{
				int slots = sylladex.getTotalSlots();
				if (slots < leastSlots || leastSlotsSylladex == null)
				{
					leastSlots = slots;
					leastSlotsSylladex = sylladex;
				}
			}
			if (leastSlots == 256)
				SylladexUtils.launchItem(player, (ItemStack) object.getObject());
			else
				leastSlotsSylladex.addCard(index, object, player);
		}

		public void addCards(int cards, EntityPlayer player) // Sylladex will always be empty
		{
			for (int i = 0; i < cards; i++)
				addCard(0, null, player);
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
			for (ISylladex sylladex : sylladices)
				sylladex.ejectAll(player, asCards, onlyFull);
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

		public Modus[][] getModi()
		{
			ArrayList<Modus[]> modi = new ArrayList<>();
			getModi(modi);
			return modi.toArray(new Modus[0][]);
		}

		protected void getModi(ArrayList<Modus[]> modi)
		{
			modi.add(this.modi.toArray(new Modus[0]));
			((Sylladex) sylladices.get(0)).getModi(modi);
		}

		public int[] getLengths()
		{
			ArrayList<Integer> lengths = new ArrayList<>();
			getLengths(lengths);
			return lengths.stream().mapToInt(Integer::intValue).toArray();
		}

		protected void getLengths(ArrayList<Integer> lengths)
		{
			lengths.add(this.sylladices.size());
			((Sylladex) sylladices.get(0)).getLengths(lengths);
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
				this.sylladices.add(ISylladex.readFromNBT((NBTTagCompound) sylladexTagBase));
		}

		@Override
		@SideOnly(Side.CLIENT)
		public ArrayList<ModusGuiContainer> generateSubContainers(ArrayList<CardGuiContainer.CardTextureIndex[]> textureIndices)
		{
			guiContainers.clear();

			CardGuiContainer.CardTextureIndex[] thisIndices = new CardGuiContainer.CardTextureIndex[modi.size()];
			for (int i = 0; i < thisIndices.length; i++)
				thisIndices[i] = modi.get(i).getCardTextureIndex();
			textureIndices.add(thisIndices);

			for (ISylladex sylladex : sylladices)
				guiContainers.add(modi.get(0).getGuiContainer(textureIndices, sylladex));
			return guiContainers;
		}

		@SideOnly(Side.CLIENT)
		public String getName(boolean plural)
		{
			String name;
			if (modi.size() == 1)
				name = modi.get(0).getName(true, false, plural);
			else
			{
				StringBuilder nameBuilder = new StringBuilder();
				for (int i = 0; i < modi.size(); i++)
					nameBuilder.append(modi.get(i).getName(false, i == 0, plural && i == modi.size() - 1));
				name = nameBuilder.toString();
			}

			if (sylladices.getFirst() instanceof Sylladex)
				return I18n.format("modus.nameCombob", name, ((Sylladex)sylladices.getFirst()).getName(true));
			else
				return name;
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

	class BottomSylladex extends Sylladex
	{
		private BottomSylladex(Modus[] modi)
		{
			super(modi);
		}

		private BottomSylladex(NBTTagCompound nbt)
		{
			super(nbt);
		}

		@Override
		public ICaptchalogueable get(int[] slots, int i, boolean asCard)
		{
			for (Modus modus : modi)
				if (modus.canGet(sylladices, slots, i))
				{
					ICaptchalogueable rtn = modus.get(sylladices, slots, i, asCard);
					if (asCard)
						cleanUpMarkedCards(slots,  i);
					return rtn;
				}
			return null;
		}

		@Override
		public ICaptchalogueable tryGetEmptyCard(int[] slots, int i)
		{
			ICaptchalogueable rtn = sylladices.get(slots[i]).tryGetEmptyCard(slots, i + 1);
			cleanUpMarkedCards(slots, i);
			return rtn;
		}

		@Override
		public void ejectAll(EntityPlayer player, boolean asCards, boolean onlyFull)
		{
			for (int i = 0; i < sylladices.size(); i++)
			{
				sylladices.get(i).ejectAll(player, asCards, onlyFull);
				if (asCards)
					cleanUpMarkedCards(i);
			}
		}

		private void cleanUpMarkedCards(int[] slots, int i)
		{
			if (cleanUpMarkedCards(slots[i]))
				slots[i] = -1;
		}

		private boolean cleanUpMarkedCards(int i)
		{
			if (sylladices.get(i).getTotalSlots() == 0)
			{
				sylladices.remove(i);
				return true;
			}
			else
				return false;
		}

		@Override
		public void addCard(int index, ICaptchalogueable object, EntityPlayer player)
		{
			sylladices.add(index, new CardSylladex(this, object));
		}

		@Override
		protected void getModi(ArrayList<Modus[]> modi)
		{
			modi.add(this.modi.toArray(new Modus[0]));
		}

		@Override
		protected void getLengths(ArrayList<Integer> lengths) { }

		@Override
		public NBTTagCompound writeToNBT()
		{
			NBTTagCompound nbt = super.writeToNBT();
			nbt.setBoolean("isBottom", true);
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
				CardSylladex card = new CardSylladex(this);
				card.readFromNBT((NBTTagCompound) sylladexTagBase);
				this.sylladices.add(card);
			}
		}
	}

	class CardSylladex implements ISylladex // TODO: Rework this so BottomSylladex is a list of ICaptchaloguables
	{
		private final BottomSylladex owner;
		private ICaptchalogueable object;
		private final ArrayList<ModusGuiContainer> containers = new ArrayList<>();
		private boolean markedForDeletion = false;

		private CardSylladex(BottomSylladex owner, ICaptchalogueable object)
		{
			this.owner = owner;
			this.object = object;
		}

		private CardSylladex(BottomSylladex owner)
		{
			this(owner, null);
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
		public void addCard(int index, ICaptchalogueable object, EntityPlayer player)
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
			get(null, 0, false).eject(owner, owner.sylladices.indexOf(this), player);
		}

		@Override
		public void ejectAll(EntityPlayer player, boolean asCards, boolean onlyFull)
		{
			if (!onlyFull || object != null)
				get(null, -1, asCards).eject(null, -1, player);
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
