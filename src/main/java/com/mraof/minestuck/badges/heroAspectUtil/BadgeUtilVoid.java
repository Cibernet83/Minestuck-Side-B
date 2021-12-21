package com.mraof.minestuck.badges.heroAspectUtil;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.capabilities.api.IBadgeEffects;
import com.mraof.minestuck.capabilities.caps.GameData;
import com.mraof.minestuck.capabilities.caps.GodKeyStates;
import com.mraof.minestuck.client.gui.MSGTGuiHandler;
import com.mraof.minestuck.client.particles.MinestuckParticles;
import com.mraof.minestuck.util.EnumAspect;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mod.EventBusSubscriber(modid = Minestuck.MODID)
public class BadgeUtilVoid extends BadgeHeroAspectUtil
{
	public BadgeUtilVoid() {
		super(EnumAspect.VOID, new ItemStack(Items.ENDER_PEARL, 200));
	}

	@Override
	public boolean onBadgeTick(World world, EntityPlayer player, IBadgeEffects badgeEffects, GodKeyStates.KeyState state, int time)
	{
		if(state != GodKeyStates.KeyState.PRESS)
			return false;

		player.openGui(Minestuck.instance, MSGTGuiHandler.ITEM_VOID, world, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());

		badgeEffects.startPowerParticles(getClass(), MinestuckParticles.ParticleType.AURA, EnumAspect.VOID, 20);

		return true;
	}

	@SubscribeEvent
	public static void onItemExpire(ItemExpireEvent event)
	{
		if (event.getEntity().world.isRemote)
			return;

		GameData.addItemToVoid(event.getEntityItem().getItem());
	}

	private static final HashMap<World, List<EntityItem>> prevItems = new HashMap<>();

	@SubscribeEvent
	public static void worldTick(TickEvent.WorldTickEvent event)
	{
		if(event.world.isRemote || event.phase == TickEvent.Phase.END)
			return;

		if(!prevItems.containsKey(event.world))
			prevItems.put(event.world, new ArrayList<>());

		List<EntityItem> items = prevItems.get(event.world);

		for(EntityItem item : items)
		{
			NBTTagCompound nbt = new NBTTagCompound();
			item.writeEntityToNBT(nbt);
			int health = nbt.getInteger("Health");

			if(health <= 0 || (item.posY <= -64 && !item.isDead))
			{
				GameData.addItemToVoid(item.getItem().copy());
				item.setDead();
			}
		}

		items.clear();
		items.addAll(event.world.getEntities(EntityItem.class, e -> true));
	}
}
