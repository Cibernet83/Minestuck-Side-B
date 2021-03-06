package com.mraof.minestuck.capabilities.caps;

import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.capabilities.api.IStrifeData;
import com.mraof.minestuck.strife.KindAbstratus;
import com.mraof.minestuck.strife.StrifeSpecibus;
import com.mraof.minestuck.util.MinestuckPlayerData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;

public class StrifeData implements IStrifeData
{
	public static final int PORTFOLIO_SIZE = 10;
	protected final StrifeSpecibus[] portfolio = new StrifeSpecibus[PORTFOLIO_SIZE];
	protected EntityLivingBase owner;
	protected int selWeapon = -1;
	protected int selSpecibus = -1;
	protected boolean isArmed = false;

	protected int droppedCards = 0;
	protected int prevSelSlot = 0;

	protected boolean abstrataSwitcherUnlocked = false;
	protected boolean strifeEnabled = false;

	@Override
	public NBTTagCompound writeToNBT()
	{
		NBTTagCompound nbt = new NBTTagCompound();

		if (selWeapon < 0)
		{
			clearPortfolio();
			portfolio[0] = StrifeSpecibus.empty();
			selWeapon = 0;
		}

		writePortfolio(nbt);
		writeSelectedIndices(nbt);
		writeDroppedCards(nbt);
		nbt.setBoolean("AbstrataSwitcherUnlocked", abstrataSwitcherUnlocked);

		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		if (nbt.hasKey("Portfolio") && !nbt.getBoolean("KeepPortfolio"))
			clearPortfolio();

		if (nbt.hasKey("SelectedSpecibus"))
			setSelectedSpecibusIndex(nbt.getInteger("SelectedSpecibus"));
		if (nbt.hasKey("SelectedWeapon"))
			setSelectedWeaponIndex(nbt.getInteger("SelectedWeapon"));
		if (nbt.hasKey("Armed"))
			setArmed(nbt.getBoolean("Armed"));

		if (nbt.hasKey("CanStrife"))
			strifeEnabled = nbt.getBoolean("CanStrife");
		if (nbt.hasKey("AbstrataSwitcherUnlocked"))
			abstrataSwitcherUnlocked = nbt.getBoolean("AbstrataSwitcherUnlocked");
		if (nbt.hasKey("DroppedCards"))
			droppedCards = nbt.getInteger("DroppedCards");

		NBTTagList portfolioList = nbt.getTagList("Portfolio", 10);

		for (int i = 0; i < portfolioList.tagCount(); i++)
		{
			NBTTagCompound spNbt = portfolioList.getCompoundTagAt(i);
			int slot = spNbt.getInteger("Slot");
			if (slot >= 0 && slot < portfolio.length)
				portfolio[slot] = new StrifeSpecibus(spNbt.getCompoundTag("Specibus"));
		}
	}

	@Override
	public void setOwner(EntityLivingBase owner)
	{
		this.owner = owner;
	}

	@Override
	public StrifeSpecibus[] getPortfolio()
	{
		return portfolio;
	}

	@Override
	public boolean isPortfolioFull()
	{
		for (StrifeSpecibus sp : portfolio)
			if (sp == null)
				return false;
		return true;
	}

	@Override
	public boolean isPortfolioEmpty()
	{
		for (StrifeSpecibus sp : portfolio)
			if (sp != null)
				return false;
		return false;
	}

	@Override
	public boolean portfolioHasAbstratus(KindAbstratus abstratus)
	{
		for (StrifeSpecibus sp : portfolio)
			if (sp != null && sp.getKindAbstratus() == abstratus)
				return true;
		return false;
	}

	@Override
	public boolean addSpecibus(StrifeSpecibus specibus)
	{
		if (isPortfolioFull() || (specibus.isAssigned() && portfolioHasAbstratus(specibus.getKindAbstratus())))
			return false;

		for (int i = 0; i < portfolio.length; i++)
			if (portfolio[i] == null)
			{
				portfolio[i] = specibus;
				break;
			}
		return true;
	}

	@Override
	public StrifeSpecibus removeSpecibus(int index)
	{
		if (index < 0 || index > portfolio.length || portfolio[index] == null)
			return null;
		StrifeSpecibus result = portfolio[index];
		portfolio[index] = null;

		if (getSelectedSpecibusIndex() == index)
		{
			setSelectedSpecibusIndex(-1);
			setArmed(false);
		}

		return result;
	}

	@Override
	public void setSpecibus(StrifeSpecibus specibus, int index)
	{
		portfolio[index] = specibus;
	}

	@Override
	public void clearPortfolio()
	{
		for (int i = 0; i < portfolio.length; i++)
			portfolio[i] = null;
	}

	@Override
	public boolean canStrife()
	{
		return strifeEnabled;
	}

	@Override
	public void setStrifeEnabled(boolean canStrife)
	{
		strifeEnabled = canStrife;
	}

	@Override
	public boolean abstrataSwitcherUnlocked()
	{
		return abstrataSwitcherUnlocked;
	}

	@Override
	public void unlockAbstrataSwitcher(boolean unlocked)
	{
		abstrataSwitcherUnlocked = unlocked;
	}

	@Override
	public int getDroppedCards()
	{
		return droppedCards;
	}

	@Override
	public void setDroppedCards(int v)
	{
		droppedCards = v;
	}

	@Override
	public boolean canDropCards()
	{
		if (!(owner instanceof EntityPlayer) || owner instanceof FakePlayer)
			return false;
		return droppedCards < Math.max(MinestuckConfig.strifeCardMobDrops, MinestuckPlayerData.getData((EntityPlayer) owner).echeladder.getRung() / 6);
	}

	@Override
	public int getSelectedSpecibusIndex()
	{
		return selSpecibus;
	}

	@Override
	public void setSelectedSpecibusIndex(int index)
	{
		if (selSpecibus != index)
		{
			selWeapon = 0;
			selSpecibus = index;
		}
	}

	@Override
	public int getSelectedWeaponIndex()
	{
		return selWeapon;
	}

	@Override
	public void setSelectedWeaponIndex(int index)
	{
		selWeapon = index;
	}

	@Override
	public boolean isArmed()
	{
		return isArmed;
	}

	@Override
	public void setArmed(boolean armed)
	{
		isArmed = armed;
	}

	@Override
	public int getPrevSelSlot()
	{
		return prevSelSlot;
	}

	@Override
	public void setPrevSelSlot(int slot)
	{
		prevSelSlot = slot;
	}

	@Override
	public StrifeSpecibus[] getNonEmptyPortfolio()
	{
		int size = 0;
		for (StrifeSpecibus specibus : portfolio)
			if (specibus != null && specibus.isAssigned() && (specibus.getKindAbstratus().isFist() || !specibus.getContents().isEmpty()))
				size++;
		StrifeSpecibus[] result = new StrifeSpecibus[size];
		int i = 0;
		for (StrifeSpecibus specibus : portfolio)
			if (specibus != null && specibus.isAssigned() && (specibus.getKindAbstratus().isFist() || !specibus.getContents().isEmpty()))
				result[i++] = specibus;

		return result;
	}

	@Override
	public int getSpecibusIndex(StrifeSpecibus specibus)
	{
		for (int i = 0; i < portfolio.length; i++)
			if (portfolio[i] == specibus)
				return i;
		return -1;
	}

	@Override
	public void writePortfolio(NBTTagCompound nbt, int... indices)
	{
		if (indices.length > 0)
			nbt.setBoolean("KeepPortfolio", true);

		NBTTagList portfolioList = new NBTTagList();
		for (int i = 0; i < portfolio.length; i++)
		{
			if ((indices.length <= 0 || ArrayUtils.contains(indices, i)) && portfolio[i] != null)
			{
				NBTTagCompound spNbt = new NBTTagCompound();
				spNbt.setInteger("Slot", i);
				spNbt.setTag("Specibus", portfolio[i].writeToNBT(new NBTTagCompound()));

				portfolioList.appendTag(spNbt);
			}
		}

		nbt.setTag("Portfolio", portfolioList);
	}

	@Override
	public void writeSelectedIndices(NBTTagCompound nbt)
	{
		nbt.setInteger("SelectedSpecibus", getSelectedSpecibusIndex());
		nbt.setInteger("SelectedWeapon", getSelectedWeaponIndex());
		nbt.setBoolean("Armed", isArmed());
	}

	@Override
	public void writeDroppedCards(NBTTagCompound nbt)
	{
		nbt.setInteger("DroppedCards", droppedCards);
	}

	@Override
	public void writeConfig(NBTTagCompound nbt)
	{
		nbt.setBoolean("AbstrataSwitcherUnlocked", abstrataSwitcherUnlocked);
		nbt.setBoolean("CanStrife", strifeEnabled);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		if (selWeapon < 0)
		{
			clearPortfolio();
			portfolio[0] = StrifeSpecibus.empty();
			selWeapon = 0;
		}

		writePortfolio(buf);
		writeSelectedIndices(buf);
		writeDroppedCards(buf);
		writeConfig(buf);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		readPortfolio(buf);
		readSelectedIndices(buf);
		readDroppedCards(buf);
		readConfig(buf);
	}

	@Override
	public void writePortfolio(ByteBuf buf, int... indices)
	{
		buf.writeBoolean(indices.length == 0);

		ArrayList<NBTTagCompound> tags = new ArrayList<>();
		for (int i = 0; i < portfolio.length; i++)
			if ((indices.length <= 0 || ArrayUtils.contains(indices, i)) && portfolio[i] != null)
			{
				NBTTagCompound tag = new NBTTagCompound();
				tag.setInteger("Slot", i);
				tag.setTag("Specibus", portfolio[i].writeToNBT(new NBTTagCompound()));

				tags.add(tag);
			}

		buf.writeInt(tags.size());
		for (NBTTagCompound tag : tags)
			ByteBufUtils.writeTag(buf, tag);
	}

	@Override
	public void readPortfolio(ByteBuf buf)
	{
		if (buf.readBoolean())
			clearPortfolio();

		int length = buf.readInt();
		for (int i = 0; i < length; i++)
		{
			NBTTagCompound tag = ByteBufUtils.readTag(buf);
			int slot = tag.getInteger("Slot");
			if (slot >= 0 && slot < portfolio.length)
				portfolio[slot] = new StrifeSpecibus(tag.getCompoundTag("Specibus"));
		}
	}

	@Override
	public void writeSelectedIndices(ByteBuf buf)
	{
		buf.writeInt(getSelectedSpecibusIndex());
		buf.writeInt(getSelectedWeaponIndex());
		buf.writeBoolean(isArmed());
	}

	@Override
	public void readSelectedIndices(ByteBuf buf)
	{
		setSelectedSpecibusIndex(buf.readInt());
		setSelectedWeaponIndex(buf.readInt());
		setArmed(buf.readBoolean());
	}

	@Override
	public void writeDroppedCards(ByteBuf buf)
	{
		buf.writeInt(droppedCards);
	}

	@Override
	public void readDroppedCards(ByteBuf buf)
	{
		droppedCards = buf.readInt();
	}

	@Override
	public void writeConfig(ByteBuf buf)
	{
		buf.writeBoolean(abstrataSwitcherUnlocked);
		buf.writeBoolean(strifeEnabled);
	}

	@Override
	public void readConfig(ByteBuf buf)
	{
		abstrataSwitcherUnlocked = buf.readBoolean();
		strifeEnabled = buf.readBoolean();
	}
}
