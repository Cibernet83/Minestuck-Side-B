package com.mraof.minestuck.item;

import com.mraof.minestuck.util.IRegistryObject;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemFood extends net.minecraft.item.ItemFood implements IRegistryItem
{
	protected final FoodItemConsumer consumer;
	private final String regName;

	public ItemFood(String name, int amount, float saturation, boolean isWolfFood)
	{
		this(name, amount, saturation, isWolfFood, null);
	}

	public ItemFood(String name, int amount, float saturation, boolean isWolfFood, FoodItemConsumer consumer)
	{
		super(amount, saturation, isWolfFood);
		this.consumer = consumer;

		setCreativeTab(MinestuckTabs.minestuck);

		setUnlocalizedName(name);
		regName = IRegistryObject.unlocToReg(name);
		MinestuckItems.items.add(this);
	}

	public static FoodItemConsumer getPopBallConsumer()
	{
		return (stack, worldIn, player) ->
		{
			if (!worldIn.isRemote)
			{
				int eightBallMessage = worldIn.rand.nextInt(20);
				ITextComponent msg = new TextComponentTranslation("status.eightBallMessage." + eightBallMessage).setStyle(new Style().setColor(TextFormatting.BLUE).setItalic(true));
				player.sendStatusMessage(msg, false);
			}
			return null;
		};
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
	{
		ItemStack oldStack = stack.copy();
		ItemStack result = super.onItemUseFinish(stack, worldIn, entityLiving);
		if (consumer != null && entityLiving instanceof EntityPlayer)
		{
			ItemStack consumerResult = consumer.accept(oldStack, worldIn, (EntityPlayer) entityLiving);
			if (consumerResult != null)
				return consumerResult;
		}
		return result;
	}

	public ItemFood setPotionEffect(PotionEffect effect, float probability)
	{
		//this.potionId = effect;
		//this.potionEffectProbability = probability;
		return this;
	}

	@Override
	public void register(IForgeRegistry<Item> registry)
	{
		setRegistryName(regName);
		registry.register(this);
	}

	public interface FoodItemConsumer
	{
		ItemStack accept(ItemStack stack, World worldIn, EntityPlayer player);
	}
}
