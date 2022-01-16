package com.mraof.minestuck.item;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.captchalogue.modus.Modus;
import com.mraof.minestuck.client.gui.MinestuckGuiHandler;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.network.message.MessageModusSyncRequest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemModus extends MSItemBase
{
	private final Modus modus;

	public ItemModus(Modus modus)
	{
		super(modus.getUnlocalizedName() + "Modus", MinestuckTabs.fetchModi, 1, false);
		this.modus = modus;
		MinestuckItems.modi.put(modus, this);
	}

	public Modus getModus()
	{
		return modus;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) // FIXME: This runs twice on integrated servers
	{
		if (player.isSneaking())
		{
			MinestuckNetwork.sendToServer(new MessageModusSyncRequest());
			Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
		}
		else
		{
			BlockPos pos = player.getPosition();
			player.openGui(Minestuck.instance, MinestuckGuiHandler.GuiId.FETCH_MODUS.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
		}
		return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}

	@Override
	public void register(IForgeRegistry<Item> registry)
	{
		super.register(registry);
		OreDictionary.registerOre("modus", this); // OreDict gets *really* mad when you register the item as an ore before the item itself :flushed:
	}
}
