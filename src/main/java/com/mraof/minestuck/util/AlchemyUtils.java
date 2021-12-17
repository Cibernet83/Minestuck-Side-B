package com.mraof.minestuck.util;

import com.mraof.minestuck.alchemy.Grist;
import com.mraof.minestuck.alchemy.GristRegistry;
import com.mraof.minestuck.alchemy.GristSet;
import com.mraof.minestuck.alchemy.MinestuckGrists;
import com.mraof.minestuck.item.ItemCruxiteArtifact;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;

import static com.mraof.minestuck.item.MinestuckItems.captchaCard;

public class AlchemyUtils
{
	public static void giveAlchemyExperience(ItemStack stack, EntityPlayer player)
	{
		if(!(stack.getItem() instanceof ItemCruxiteArtifact))
		{
			Echeladder e = MinestuckPlayerData.getData(player).echeladder;
			e.checkBonus(Echeladder.ALCHEMY_BONUS_OFFSET);
		}

		GristSet set = GristRegistry.getGristConversion(stack);
		if(set != null) //The only time the grist set should be null here is if it was a captchalouge card that was alchemized
		{
			double value = 0;
			for(Grist type : Grist.values())
			{
				int v = set.getGrist(type);
				float f = type == MinestuckGrists.build || type == MinestuckGrists.artifact ? 0.5F : type == MinestuckGrists.zillium ? 20 : type.getPower();
				if(v > 0)
					value += f*v/2;
			}

			Echeladder e = MinestuckPlayerData.getData(player).echeladder;
			if(value >= 50)
				e.checkBonus((byte) (Echeladder.ALCHEMY_BONUS_OFFSET + 1));
			if(value >= 500)
				e.checkBonus((byte) (Echeladder.ALCHEMY_BONUS_OFFSET + 2));
		}
	}

	@Nonnull
	public static ItemStack getFirstOreItem(String name)
	{
		if(OreDictionary.getOres(name).isEmpty())
			return ItemStack.EMPTY;
		else return OreDictionary.getOres(name).get(0);
	}

	/**
	 * Given a punched card or a carved dowel, returns a new item that represents the encoded data.
	 *
	 * @param card - The dowel or card with encoded data
	 * @return An item, or null if the data was invalid.
	 */
	@Nonnull
	public static ItemStack getDecodedItem(ItemStack card)
	{
		return getDecodedItem(card, false);
	}

	@Nonnull
	public static ItemStack getDecodedItem(ItemStack card, boolean ignoreGhost)
	{
		if (!hasDecodedItem(card))
			return ItemStack.EMPTY;
		ItemStack stack = decodeFrom(card.getTagCompound());
		if (stack.getCount() <= 0 && !ignoreGhost)
			stack.setCount(1);
		return stack;
	}

	public static ItemStack decodeFrom(NBTTagCompound nbt)
	{
		Item item = Item.REGISTRY.getObject(new ResourceLocation(nbt.getString("contentID")));
		if (item == null)
			return ItemStack.EMPTY;
		ItemStack stack = new ItemStack(item, 1, nbt.getInteger("contentMeta"));

		if(nbt.hasKey("contentTags"))
		{
			stack.setTagCompound(nbt.getCompoundTag("contentTags"));
			stack.setCount(nbt.getInteger("contentSize"));
		}

		return stack;
	}

	/**
	 * Given a punched card, this method returns a new item that represents the encoded data,
	 * or it just returns the item directly if it's not a punched card.
	 */
	@Nonnull
	public static ItemStack getDecodedItemDesignix(ItemStack card)
	{

		if (card.isEmpty())
			return ItemStack.EMPTY;

		if (card.getItem().equals(captchaCard) && card.hasTagCompound() && card.getTagCompound().hasKey("contentID"))
			return getDecodedItem(card);
		else
			return card.copy();
	}

	/**
	 * Encodes the registry id and the meta of an itemstack onto a card or dowel, but not its nbt.
	 */
	@Nonnull
	public static ItemStack createEncodedItem(ItemStack item, ItemStack card)
	{
		card.setTagCompound(encodeTo(item, card.getTagCompound(), false));
		return card;
	}

	@Nonnull
	public static ItemStack createEncodedItem(ItemStack item, Item cardType)
	{
		return createEncodedItem(item, new ItemStack(cardType));
	}

	public static NBTTagCompound encodeTo(ItemStack item, NBTTagCompound nbt, boolean allData)
	{
		if (item.isEmpty())
			return nbt;

		if (nbt == null)
			nbt = new NBTTagCompound();

		nbt.setString("contentID", item.getItem().getRegistryName().toString());
		nbt.setInteger("contentMeta", item.getItemDamage());
		if (allData)
		{
			nbt.setInteger("contentSize", item.getCount());
			if (item.hasTagCompound())
				nbt.setTag("contentTags", item.getTagCompound());
		}
		return nbt;
	}

	@Nonnull
	public static ItemStack createCard(ItemStack item, boolean punched)
	{
		ItemStack card = new ItemStack(captchaCard);
		card.setTagCompound(encodeTo(item, null, !punched));
		card.getTagCompound().setBoolean("punched", punched);
		return card;
	}

	@Nonnull
	public static ItemStack createGhostCard(ItemStack item)
	{
		ItemStack stack = createCard(item, false);
		changeEncodeSize(stack, 0);
		return stack;
	}

	public static ItemStack changeEncodeSize(ItemStack stack, int size)
	{
		stack.getTagCompound().setInteger("contentSize", size);
		return stack;
	}

	public static boolean isPunchedCard(ItemStack item)
	{
		return item.getItem() == captchaCard && item.hasTagCompound() && item.getTagCompound().getBoolean("punched");
	}

	public static boolean isGhostCard(ItemStack item)
	{
		return item.getItem() == captchaCard && item.hasTagCompound() && item.getTagCompound().hasKey("contentSize") && item.getTagCompound().getInteger("contentSize") == 0;
	}

	public static boolean hasDecodedItem(ItemStack item)
	{
		return item.hasTagCompound() && item.getTagCompound().hasKey("contentID", 8);
	}

	public static boolean containsItem(ItemStack item)
	{
		return item.getItem() == captchaCard && item.hasTagCompound() && !item.getTagCompound().getBoolean("punched") && item.getTagCompound().getInteger("contentSize") > 0;
	}
}
