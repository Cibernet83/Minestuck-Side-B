package com.mraof.minestuck.tileentity;


import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.MinestuckConfig;
import com.mraof.minestuck.alchemy.*;
import com.mraof.minestuck.block.BlockAlchemiter;
import com.mraof.minestuck.block.BlockAlchemiter.EnumParts;
import com.mraof.minestuck.block.MinestuckBlocks;
import com.mraof.minestuck.client.gui.MinestuckGuiHandler;
import com.mraof.minestuck.event.AlchemizeItemAlchemiterEvent;
import com.mraof.minestuck.event.AlchemizeItemEvent;
import com.mraof.minestuck.item.MinestuckItems;
import com.mraof.minestuck.tracker.MinestuckPlayerTracker;
import com.mraof.minestuck.util.AlchemyUtils;
import com.mraof.minestuck.util.Debug;
import com.mraof.minestuck.util.IdentifierHandler;
import com.mraof.minestuck.util.IdentifierHandler.PlayerIdentifier;
import com.mraof.minestuck.util.MinestuckPlayerData;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class TileEntityAlchemiter extends TileEntity
{
	//protected AlchemiterUpgrades upgrade[] = new AlchemiterUpgrades[7];
	public boolean upgraded = false;
	protected Grist selectedGrist = MinestuckGrist.build;
	protected boolean broken = false;
	protected ItemStack dowel = ItemStack.EMPTY;
	protected ItemStack[] upgradeItem = {ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY};
	protected TileEntity jbe = null;

	//tells the tile entity to not stop working
	public void unbreakMachine()
	{
		broken = false;
		if (world != null)
		{
			IBlockState state = world.getBlockState(pos);
			world.notifyBlockUpdate(pos, state, state, 2);
		}
	}

	public boolean isUpgraded()
	{
		return this.upgraded;
	}

	//TODO
	public void doTheBlenderThing()
	{
		if (!dowel.isEmpty())
		{
			InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(MinestuckItems.rawCruxite, 1));
			setDowel(ItemStack.EMPTY);
		}
	}

	public ItemStack[] getUpgradeItemsList()
	{
		return this.upgradeItem;
	}

	public ItemStack getUpgrade(int id)
	{
		return upgradeItem[id];
	}

	public EnumFacing getFacing()
	{
		return EnumFacing.getHorizontal(getBlockMetadata() / 4);
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);

		if (tagCompound.hasKey("gristType"))
			this.selectedGrist = Grist.getTypeFromString(tagCompound.getString("gristType"));
		if (this.selectedGrist == null)
		{
			this.selectedGrist = MinestuckGrist.build;
		}

		this.upgraded = tagCompound.getBoolean("upgraded");

		if (upgraded)
		{
			for (int i = 0; i < upgradeItem.length; i++)
			{
				setUpgrade(new ItemStack(tagCompound.getCompoundTag("upgrade" + i)), i);
			}
		}

		broken = tagCompound.getBoolean("broken");

		if (tagCompound.hasKey("dowel"))
			setDowel(new ItemStack(tagCompound.getCompoundTag("dowel")));
	}

	//JBE upgrades
	public void setUpgrade(ItemStack stack, int id)
	{
		if (!stack.isEmpty())
		{
			this.upgradeItem[id] = stack;
			if (world != null)
			{
				IBlockState state = world.getBlockState(pos);
				world.notifyBlockUpdate(pos, state, state, 2);
			}
		}
	}

	/* to upgrade alc
	public void setUpgraded(boolean bool, BlockPos pos)
	{
		
		TileEntity te = world.getTileEntity(pos);
		
		if(te instanceof TileEntityJumperBlock)
			jbe = te;
		else
		{
			Debug.warnf("%s is not a jbe tile entity", te);
			return;
		}

		//an alc upgrade
		TileEntityJumperBlock jbeTe = (TileEntityJumperBlock) te;
		this.upgraded = bool;
		
		if(bool == true)
		{
			for(int i = 0; i < upgradeItem.length; i++)
			{
				this.upgradeItem[i] = jbeTe.getUpgrade(i);
				this.upgradeItem[i].setCount(1);
			}
			
		}
		else
		{
			for(int i = 0; i < upgradeItem.length; i++)
			{
				this.upgradeItem[i] = ItemStack.EMPTY;
			}
		}
		
		this.upgrade = AlchemiterUpgrades.getUpgradesFromList(getUpgradeItemsList());
	}
	*/

	/* alc upgrade
	public boolean hasUpgrade(AlchemiterUpgrades upgrade)
	{
		return AlchemiterUpgrades.hasUpgrade(getUpgradeItemsList(), upgrade);
	}*/

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);

		tagCompound.setString("gristType", selectedGrist.getRegistryName().toString());
		tagCompound.setBoolean("upgraded", upgraded);
		tagCompound.setBoolean("broken", isBroken());

		for (int i = 0; i < upgradeItem.length; i++)
		{
			tagCompound.setTag("upgrade" + i, upgradeItem[i].writeToNBT(new NBTTagCompound()));
		}

		if (dowel != null)
			tagCompound.setTag("dowel", dowel.writeToNBT(new NBTTagCompound()));

		return tagCompound;
	}

	/**
	 * @return true if the machine is marked as broken
	 */
	public boolean isBroken()
	{
		return broken;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		SPacketUpdateTileEntity packet = new SPacketUpdateTileEntity(this.pos, 0, getUpdateTag());
		return packet;
	}

	/* alc upgrade
	public AlchemiterUpgrades[] getUpgradeList()
	{
		return upgrade;
	}
	*/

	@Override
	public NBTTagCompound getUpdateTag()
	{
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		handleUpdateTag(pkt.getNbtCompound());
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
	{
		return oldState.getBlock() != newSate.getBlock() || oldState.getValue(BlockAlchemiter.PART1) != newSate.getValue(BlockAlchemiter.PART1);
	}

	public void onRightClick(World worldIn, EntityPlayer playerIn, IBlockState state, EnumParts part)
	{
		if (worldIn.isRemote)
		{
			if (part == EnumParts.CENTER_PAD || part == EnumParts.CORNER || part == EnumParts.SIDE_LEFT || part == EnumParts.SIDE_RIGHT || part == EnumParts.TOTEM_CORNER)
			{
				BlockPos mainPos = pos;
				if (!isBroken())
				{
					{
						playerIn.openGui(Minestuck.instance, MinestuckGuiHandler.GuiId.ALCHEMITER.ordinal(), worldIn, mainPos.getX(), mainPos.getY(), mainPos.getZ());
					}
				}
			}
			return;
		}
		else
		{
			/*blender alc upgrade
			if(hasUpgrade(AlchemiterUpgrades.blender) && !dowel.isEmpty())
				doTheBlenderThing();
				*/
		}
		BlockPos mainPos = pos;
		TileEntity te = worldIn.getTileEntity(mainPos);

		if (te instanceof TileEntityAlchemiter && playerIn != null)
		{
			((TileEntityAlchemiter) te).onPadRightClick(playerIn, state);
		}
	}

	public void onPadRightClick(EntityPlayer player, IBlockState clickedState)
	{
		if (isUseable(clickedState))
		{
			BlockAlchemiter alchemiter = (BlockAlchemiter) clickedState.getBlock();
			EnumParts part = clickedState.getValue(alchemiter.PART);
			if (part.equals(EnumParts.TOTEM_PAD))
			{
				if (!dowel.isEmpty())
				{    //Remove dowel from pad
					if (player.getHeldItemMainhand().isEmpty())
						player.setHeldItem(EnumHand.MAIN_HAND, dowel);
					else if (!player.inventory.addItemStackToInventory(dowel))
						dropItem(false);
					else player.inventoryContainer.detectAndSendChanges();

					setDowel(ItemStack.EMPTY);
				}
				else
				{
					ItemStack heldStack = player.getHeldItemMainhand();
					if (!heldStack.isEmpty() && heldStack.getItem() == MinestuckItems.cruxiteDowel)
						setDowel(heldStack.splitStack(1));    //Put a dowel on the pad
				}
			}
		}
	}

	public void dropItem(boolean inBlock)
	{
		BlockPos dropPos;
		if (inBlock)
			dropPos = this.pos;
		else if (!world.getBlockState(this.pos).isBlockNormalCube())
			dropPos = this.pos;
		else if (!world.getBlockState(this.pos.up()).isBlockNormalCube())
			dropPos = this.pos.up();
		else dropPos = this.pos;

		InventoryHelper.spawnItemStack(world, dropPos.getX(), dropPos.getY(), dropPos.getZ(), dowel);
		setDowel(ItemStack.EMPTY);
	}

	private boolean isUseable(IBlockState state)
	{
		if (!broken)
		{
			checkStates();
			if (broken)
				Debug.warnf("Failed to notice a block being broken or misplaced at the alchemiter at %s", getPos());
		}
		return !broken;
	}

	public void checkStates()
	{
		if (this.broken)
			return;

		EnumFacing facing = getWorld().getBlockState(this.getPos()).getValue(BlockAlchemiter.DIRECTION);
		BlockPos pos = getPos().down();
		if (!world.getBlockState(pos.up(3)).equals(BlockAlchemiter.getBlockState(EnumParts.UPPER_ROD, facing)) ||
					!world.getBlockState(pos.up(2)).equals(BlockAlchemiter.getBlockState(EnumParts.LOWER_ROD, facing)) ||
					!world.getBlockState(pos.up()).equals(BlockAlchemiter.getBlockState(EnumParts.TOTEM_PAD, facing)) ||
					!world.getBlockState(pos).equals(BlockAlchemiter.getBlockState(EnumParts.TOTEM_CORNER, facing)) ||
					!world.getBlockState(pos.offset(facing.rotateY())).equals(BlockAlchemiter.getBlockState(EnumParts.SIDE_LEFT, facing)) ||
					!world.getBlockState(pos.offset(facing.rotateY(), 2)).equals(BlockAlchemiter.getBlockState(EnumParts.SIDE_RIGHT, facing)) ||
					!world.getBlockState(pos.offset(facing).offset(facing.rotateY())).equals(BlockAlchemiter.getBlockState(EnumParts.CENTER_PAD, facing)) ||
					!world.getBlockState(pos.offset(facing.rotateY(), 3)).equals(BlockAlchemiter.getBlockState(EnumParts.CORNER, facing)) ||
					!world.getBlockState(pos.offset(facing).offset(facing.rotateY(), 3)).equals(BlockAlchemiter.getBlockState(EnumParts.SIDE_LEFT, facing.rotateYCCW())) ||
					!world.getBlockState(pos.offset(facing, 2).offset(facing.rotateY(), 3)).equals(BlockAlchemiter.getBlockState(EnumParts.SIDE_RIGHT, facing.rotateYCCW())) ||
					!world.getBlockState(pos.offset(facing).offset(facing.rotateY(), 2)).equals(BlockAlchemiter.getBlockState(EnumParts.CENTER_PAD, facing.rotateYCCW())) ||
					!world.getBlockState(pos.offset(facing, 3).offset(facing.rotateY(), 3)).equals(BlockAlchemiter.getBlockState(EnumParts.CORNER, facing.rotateYCCW())) ||
					!world.getBlockState(pos.offset(facing, 3).offset(facing.rotateY(), 2)).equals(BlockAlchemiter.getBlockState(EnumParts.SIDE_LEFT, facing.getOpposite())) ||
					!world.getBlockState(pos.offset(facing, 3).offset(facing.rotateY(), 1)).equals(BlockAlchemiter.getBlockState(EnumParts.SIDE_RIGHT, facing.getOpposite())) ||
					!world.getBlockState(pos.offset(facing, 2).offset(facing.rotateY(), 2)).equals(BlockAlchemiter.getBlockState(EnumParts.CENTER_PAD, facing.getOpposite())) ||
					!world.getBlockState(pos.offset(facing, 3)).equals(BlockAlchemiter.getBlockState(EnumParts.CORNER, facing.getOpposite())) ||
					!world.getBlockState(pos.offset(facing, 2)).equals(BlockAlchemiter.getBlockState(EnumParts.SIDE_LEFT, facing.rotateY())) ||
					!world.getBlockState(pos.offset(facing)).equals(BlockAlchemiter.getBlockState(EnumParts.SIDE_RIGHT, facing.rotateY())) ||
					!world.getBlockState(pos.offset(facing, 2).offset(facing.rotateY(), 1)).equals(BlockAlchemiter.getBlockState(EnumParts.CENTER_PAD, facing.rotateY())))

		{
			breakMachine();
			return;
		}

		return;
	}

	//tells the tile entity to stop working
	public void breakMachine()
	{
		broken = true;
		if (world != null)
		{
			IBlockState state = world.getBlockState(pos);
			world.notifyBlockUpdate(pos, state, state, 2);
		}
	}

	public void processContents(int quantity, EntityPlayer player)
	{
		ItemStack newItem = getOutput();
		//Clamp quantity
		quantity = Math.min(newItem.getMaxStackSize() * MinestuckConfig.alchemiterMaxStacks, Math.max(1, quantity));

		EnumFacing facing = world.getBlockState(pos).getValue(BlockAlchemiter.DIRECTION);
		//get the position to spawn the item
		BlockPos spawnPos = this.getPos().offset(facing).offset(facing.rotateY());
		if (facing.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE)
			spawnPos = spawnPos.offset(facing);
		if (facing.rotateY().getAxisDirection() == EnumFacing.AxisDirection.POSITIVE)
			spawnPos = spawnPos.offset(facing.rotateY());
		//remove item damage
		if (newItem.isItemStackDamageable())
			newItem.setItemDamage(0);
		//get the grist cost
		GristSet cost = getGristCost(quantity);

		boolean canAfford = GristHelper.canAfford(MinestuckPlayerData.getGristSet(player), cost);
		AlchemizeItemEvent alchemizeItemEvent = new AlchemizeItemAlchemiterEvent(world, dowel, newItem, this, spawnPos);

		if (canAfford && !MinecraftForge.EVENT_BUS.post(alchemizeItemEvent))
		{
			newItem = alchemizeItemEvent.getResultItem();

			while (quantity > 0 && !newItem.isEmpty())
			{
				ItemStack stack = newItem.copy();
				stack.setCount(Math.min(stack.getMaxStackSize(), quantity));
				quantity -= stack.getCount();
				EntityItem item = new EntityItem(world, spawnPos.getX(), spawnPos.getY() + 0.5, spawnPos.getZ(), stack);
				world.spawnEntity(item);
			}

			AlchemyUtils.giveAlchemyExperience(newItem, player);

			PlayerIdentifier pid = IdentifierHandler.encode(player);
			GristHelper.decrease(pid, cost);
			MinestuckPlayerTracker.updateGristCache(pid);
		}
	}

	public ItemStack getOutput()
	{
		if (false)//hasUpgrade(AlchemiterUpgrades.captchaCard)) [asks for upgrade]
		{
			if (!AlchemyUtils.hasDecodedObject(dowel))
				return AlchemyUtils.createCard(new ItemStack(MinestuckBlocks.genericObject), false);
			else return AlchemyUtils.createCard(new ItemStack(AlchemyUtils.getDecodedItem(dowel).getItem(), 1), false);
		}
		else if (!AlchemyUtils.hasDecodedObject(dowel))
			return new ItemStack(MinestuckBlocks.genericObject);
		else return AlchemyUtils.getDecodedItem(dowel);
	}

	public GristSet getGristCost(int quantity)
	{
		ItemStack dowel = getDowel();
		GristSet set;
		ItemStack stack = getOutput();
		/* alc upgrade
		if(hasUpgrade(AlchemiterUpgrades.captchaCard))
			stack = AlchemyRecipes.getDecodedItem(getOutput());
			*/

		boolean useSelectedType;
		if (dowel.isEmpty())
			return null;

		//get the grist cost of stack
		set = GristRegistry.getGristConversion(stack);

		//if the item is a captcha card do other stuff
		useSelectedType = stack.getItem() == MinestuckItems.captchaCard;
		if (useSelectedType)
			set = new GristSet(getSelectedGrist(), !world.isRemote ? MinestuckConfig.cardCost : MinestuckConfig.clientCardCost);

		if (set != null)
		{
			//multiply cost by quantity
			set.scaleGrist(quantity);
		}

		return set;
	}

	public ItemStack getDowel()
	{
		return dowel;
	}

	public void setDowel(ItemStack newDowel)
	{
		if (newDowel.getItem() == MinestuckItems.cruxiteDowel || newDowel.isEmpty())
		{
			dowel = newDowel;
			if (world != null)
			{
				IBlockState state = world.getBlockState(pos);
				world.notifyBlockUpdate(pos, state, state, 2);
			}
		}
	}

	public Grist getSelectedGrist()
	{
		return selectedGrist;
	}

	public void setSelectedGrist(Grist selectedGrist)
	{
		this.selectedGrist = selectedGrist;
	}

}