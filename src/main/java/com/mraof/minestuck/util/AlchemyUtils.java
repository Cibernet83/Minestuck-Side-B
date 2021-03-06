package com.mraof.minestuck.util;

import com.mraof.minestuck.alchemy.Grist;
import com.mraof.minestuck.alchemy.GristRegistry;
import com.mraof.minestuck.alchemy.GristSet;
import com.mraof.minestuck.alchemy.MinestuckGrist;
import com.mraof.minestuck.captchalogue.captchalogueable.CaptchalogueableItemStack;
import com.mraof.minestuck.captchalogue.captchalogueable.ICaptchalogueable;
import com.mraof.minestuck.captchalogue.modus.Modus;
import com.mraof.minestuck.item.ItemCruxiteArtifact;
import net.minecraft.entity.player.EntityPlayer;
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
		if (!(stack.getItem() instanceof ItemCruxiteArtifact))
		{
			Echeladder e = MinestuckPlayerData.getData(player).echeladder;
			e.checkBonus(Echeladder.ALCHEMY_BONUS_OFFSET);
		}

		GristSet set = GristRegistry.getGristConversion(stack);
		if (set != null) //The only time the grist set should be null here is if it was a captchalouge card that was alchemized
		{
			double value = 0;
			for (Grist type : Grist.values())
			{
				int v = set.getGrist(type);
				float f = type == MinestuckGrist.build || type == MinestuckGrist.artifact ? 0.5F : type == MinestuckGrist.zillium ? 20 : type.getPower();
				if (v > 0)
					value += f * v / 2;
			}

			Echeladder e = MinestuckPlayerData.getData(player).echeladder;
			if (value >= 50)
				e.checkBonus((byte) (Echeladder.ALCHEMY_BONUS_OFFSET + 1));
			if (value >= 500)
				e.checkBonus((byte) (Echeladder.ALCHEMY_BONUS_OFFSET + 2));
		}
	}

	@Nonnull
	public static ItemStack getFirstOreItem(String name)
	{
		if (OreDictionary.getOres(name).isEmpty())
			return ItemStack.EMPTY;
		else return OreDictionary.getOres(name).get(0);
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

		if (card.getItem().equals(captchaCard) && card.hasTagCompound() && card.getTagCompound().hasKey("ContentID"))
			return getDecodedItem(card);
		else
			return card.copy();
	}

	/**
	 * Given a punched card or a carved dowel, returns a new item that represents the encoded data.
	 *
	 * @param card - The dowel or card with encoded data
	 * @return An item, or null if the data was invalid.
	 */
	@Nonnull
	@Deprecated //use getCardContents instead
	public static ItemStack getDecodedItem(ItemStack card)
	{
		return getDecodedItem(card, false);
	}

	@Nonnull
	@Deprecated //use getCardContents instead
	public static ItemStack getDecodedItem(ItemStack card, boolean ignoreGhost)
	{
		if (!hasDecodedObject(card))
			return ItemStack.EMPTY;
		ItemStack stack = new ItemStack(card.getTagCompound().getCompoundTag("Content"));
		if (isGhostCard(card) && !ignoreGhost)
			stack.setCount(1);
		return stack;
	}

	public static boolean isGhostCard(ItemStack card)
	{
		return card.getItem() == captchaCard && card.hasTagCompound() && card.getTagCompound().getBoolean("Ghost");
	}

	public static boolean hasDecodedObject(ItemStack card)
	{
		return card.hasTagCompound() && card.getTagCompound().hasKey("Content", 10);
	}

	/**
	 * Encodes the registry id and the meta of an itemstack onto a card or dowel, but not its nbt.
	 */
	@Nonnull
	@Deprecated //aaaaaaaaaaaaaaaa
	public static ItemStack createEncodedItem(ItemStack item, ItemStack card)
	{
		return createEncodedItem(new CaptchalogueableItemStack(item), card);
	}

	@Nonnull
	public static ItemStack createEncodedItem(ICaptchalogueable item, ItemStack card)
	{
		if (card.getTagCompound() == null)
			card.setTagCompound(new NBTTagCompound());
		card.getTagCompound().setTag("Content", ICaptchalogueable.writeToNBT(item)); // TODO: nbt alchemy lol
		return card;
	}

	@Nonnull
	@Deprecated
	//delete this once we rework alchemy to use captchalogueables, use createCard(ICaptchalogueable, boolean) instead
	public static ItemStack createCard(ItemStack stack, boolean punched)
	{
		return createCard(new CaptchalogueableItemStack(stack), punched);
	}

	@Nonnull
	public static ItemStack createCard(ICaptchalogueable item, boolean punched)
	{
		ItemStack card = new ItemStack(captchaCard);
		card.setTagCompound(new NBTTagCompound());
		createEncodedItem(item, card);
		card.getTagCompound().setBoolean("Punched", punched);
		return card;
	}

	public static ICaptchalogueable getCardContents(ItemStack card)
	{
		if (!card.hasTagCompound() || !card.getTagCompound().hasKey("Content"))
			return null;
		return ICaptchalogueable.readFromNBT(card.getTagCompound().getCompoundTag("Content"));
	}

	public static ItemStack setCardModi(ItemStack card, Modus[] textureModi)
	{
		if (!card.hasTagCompound())
			card.setTagCompound(new NBTTagCompound());

		NBTTagList nbt = new NBTTagList();

		for (Modus modus : textureModi)
			nbt.appendTag(new NBTTagString(modus.getRegistryName().toString()));

		card.getTagCompound().setTag("Modus", nbt);

		return card;
	}

	public static boolean cardBelongsToModus(ItemStack stack)
	{
		return !getCardModi(stack).isEmpty();
	}

	@Nullable
	public static List<Modus> getCardModi(ItemStack stack)
	{
		if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("Modus"))
			return Collections.EMPTY_LIST;

		ArrayList<Modus> modi = new ArrayList<>();
		Iterator<NBTBase> iter = stack.getTagCompound().getTagList("Modus", 8).iterator();

		while (iter.hasNext())
			modi.add(Modus.REGISTRY.getValue(new ResourceLocation(((NBTTagString) iter.next()).getString())));

		return modi;
	}

	public static boolean isPunchedCard(ItemStack card)
	{
		return card.getItem() == captchaCard && card.hasTagCompound() && card.getTagCompound().getBoolean("Punched");
	}

	public static boolean isAppendable(ItemStack card)
	{
		return isEmptyCard(card) || containsObject(card);
	}

	public static boolean containsObject(ItemStack card)
	{
		return card.getItem() == captchaCard && hasDecodedObject(card) && !card.getTagCompound().getBoolean("Punched") && !card.getTagCompound().getBoolean("Ghost");
	}

	public static boolean isEmptyCard(ItemStack card)
	{
		return card.getItem() == captchaCard && !hasDecodedObject(card);
	}
}
