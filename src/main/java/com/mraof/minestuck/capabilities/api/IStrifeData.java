package com.mraof.minestuck.capabilities.api;

import com.mraof.minestuck.capabilities.IMinestuckCapabilityBase;
import com.mraof.minestuck.strife.KindAbstratus;
import com.mraof.minestuck.strife.StrifeSpecibus;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;

public interface IStrifeData extends IMinestuckCapabilityBase<EntityLivingBase>
{
	StrifeSpecibus[] getPortfolio();

	boolean isPortfolioFull();
	boolean isPortfolioEmpty();
	boolean portfolioHasAbstratus(KindAbstratus abstratus);

	boolean addSpecibus(StrifeSpecibus specibus);
	StrifeSpecibus removeSpecibus(int index);
	void setSpecibus(StrifeSpecibus specibus, int index);
	void clearPortfolio();

	boolean canStrife();
	void setStrifeEnabled(boolean canStrife);
	boolean abstrataSwitcherUnlocked();
	void unlockAbstrataSwitcher(boolean unlocked);
	int getDroppedCards();
	void setDroppedCards(int v);
	boolean canDropCards();

	int getSelectedSpecibusIndex();
	void setSelectedSpecibusIndex(int index);
	int getSelectedWeaponIndex();
	void setSelectedWeaponIndex(int index);
	boolean isArmed();
	void setArmed(boolean armed);

	int getPrevSelSlot();
	void setPrevSelSlot(int slot);

	StrifeSpecibus[] getNonEmptyPortfolio();
	int getSpecibusIndex(StrifeSpecibus specibus);

	void writePortfolio(NBTTagCompound nbt, int... indices);
	void writeSelectedIndices(NBTTagCompound nbt);
	void writeDroppedCards(NBTTagCompound nbt);
	void writeConfig(NBTTagCompound nbt);

	void toBytes(ByteBuf buf);
	void fromBytes(ByteBuf buf);
	void writePortfolio(ByteBuf buf, int... indices);
	void readPortfolio(ByteBuf buf);
	void writeSelectedIndices(ByteBuf buf);
	void readSelectedIndices(ByteBuf buf);
	void writeDroppedCards(ByteBuf buf);
	void readDroppedCards(ByteBuf buf);
	void writeConfig(ByteBuf buf);
	void readConfig(ByteBuf buf);
}
