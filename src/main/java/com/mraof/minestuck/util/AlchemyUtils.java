package com.mraof.minestuck.util;

import com.mraof.minestuck.alchemy.Grist;
import com.mraof.minestuck.alchemy.GristRegistry;
import com.mraof.minestuck.alchemy.GristSet;
import com.mraof.minestuck.alchemy.MinestuckGrists;
import com.mraof.minestuck.captchalogueable.CaptchalogueableItemStack;
import com.mraof.minestuck.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.modus.Modus;
import com.mraof.minestuck.item.ItemCruxiteArtifact;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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
		ItemStack stack = new ItemStack(card.getTagCompound().getCompoundTag("content"));
		if (isGhostCard(card) && !ignoreGhost)
			stack.setCount(1);
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
		if (card.getTagCompound() == null)
			card.setTagCompound(new NBTTagCompound());
		card.getTagCompound().setTag("content", item.writeToNBT(new NBTTagCompound())); // TODO: nbt alchemy lol
		return card;
	}

	@Nonnull
	public static ItemStack createEncodedItem(ItemStack item, Item cardType)
	{
		return createEncodedItem(item, new ItemStack(cardType));
	}

	@Nonnull
	public static ItemStack createCard(ItemStack item, boolean punched)
	{
		ItemStack card = new ItemStack(captchaCard);
		card.setTagCompound(new NBTTagCompound());
		card.getTagCompound().setTag("content", item.writeToNBT(new NBTTagCompound()));
		card.getTagCompound().setBoolean("punched", punched);
		return card;
	}

	@Nonnull
	public static ItemStack createGhostCard(ItemStack item)
	{
		ItemStack card = createCard(item, false);
		card.getTagCompound().setBoolean("ghost", true);
		return card;
	}

	public static ICaptchalogueable getCardContents(ItemStack card)
	{
		ItemStack decode = getDecodedItem(card);
		if(decode.isEmpty())
			return null;
		return new CaptchalogueableItemStack(decode);
	}

	public static boolean hasCardContents(ItemStack card)
	{
		return getCardContents(card) != null;
	}

	@Nullable
	public static List<Modus> getCardModi(ItemStack stack)
	{
		if(!stack.hasTagCompound() || !stack.getTagCompound().hasKey("Modus"))
			return Collections.EMPTY_LIST;

		ArrayList<Modus> modi = new ArrayList<>();
		Iterator<NBTBase> iter = stack.getTagCompound().getTagList("Modus", 8).iterator();

		while(iter.hasNext())
			modi.add(Modus.REGISTRY.getValue(new ResourceLocation(((NBTTagString)iter.next()).getString())));

		return modi;
	}


	public static ItemStack setCardModi(ItemStack card, List<Modus> modi)
	{
		if(!card.hasTagCompound())
			card.setTagCompound(new NBTTagCompound());

		NBTTagList nbt = new NBTTagList();

		for(Modus modus : modi)
			nbt.appendTag(new NBTTagString(modus.getRegistryName().toString()));

		card.getTagCompound().setTag("Modus", nbt);

		return card;
	}

	public static boolean cardBelongsToModus(ItemStack stack)
	{
		return !getCardModi(stack).isEmpty();
	}

	public static boolean isPunchedCard(ItemStack card)
	{
		return card.getItem() == captchaCard && card.hasTagCompound() && card.getTagCompound().getBoolean("punched");
	}

	public static boolean isGhostCard(ItemStack card)
	{
		return card.getItem() == captchaCard && card.hasTagCompound() && card.getTagCompound().getBoolean("ghost");
	}

	public static boolean hasDecodedItem(ItemStack card)
	{
		return card.hasTagCompound() && card.getTagCompound().hasKey("content", 10);
	}

	public static boolean containsItem(ItemStack card)
	{
		return card.getItem() == captchaCard && hasDecodedItem(card) && !card.getTagCompound().getBoolean("punched") && !card.getTagCompound().getBoolean("ghost");
	}

	public static boolean isEmptyCard(ItemStack card)
	{
		return card.getItem() == captchaCard && !hasDecodedItem(card);
	}

	public static boolean isAppendable(ItemStack card)
	{
		return isEmptyCard(card) || containsItem(card);
	}
}
