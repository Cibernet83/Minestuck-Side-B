package com.mraof.minestuck.item;

import com.mraof.minestuck.util.IRegistryObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MSItemBase extends Item implements IRegistryItem
{
	public static final HashMap<UUID, String> DEDICATED_TOOLTIPS = new HashMap<UUID, String>()
	{{
		put(UUID.fromString("42941b8f-d0c3-45dd-8cbb-1f6f388de45e"), "Cibernet");
		put(UUID.fromString("fb31b473-92e8-47f2-9d18-ac7b9554671a"), "Ishumire");
		put(UUID.fromString("f0c08884-e1f7-495c-b2c2-23e9cc17c303"), "Badadamadaba");
		put(UUID.fromString("28a9176d-9720-4d44-8df7-fb5309aedf2f"), "ThatLameOverlord");
		put(UUID.fromString("d38ad1b5-76d1-4bd1-ad35-c1a2ea6cef32"), "Nhezak");
		put(UUID.fromString("991b9a67-28ae-4777-b046-4dfd16670921"), "Fishwreck");
		put(UUID.fromString("ab8782ba-9ada-4bb8-89a9-c686bb2f3e66"), "Akisephila");
		put(UUID.fromString("d11f4f2a-de7d-46d7-a70a-39953168c06d"), "ZeroCraftsman");
		put(UUID.fromString("37fd48f4-f059-4370-852d-942d3381a0cf"), "_draconix");
		put(UUID.fromString("5a47376e-ca28-499b-a6aa-8d7518b753b9"), "Owo_XxX_owO");
		put(UUID.fromString("817aaf17-5127-45d8-8efe-142c2b8d64d8"), "carefreeDesigner");
		put(UUID.fromString("dd82e2a0-f702-44ba-ad36-fb450cd38182"), "JDubSupreme");
	}};

	private final String regName;
	public final boolean hasCustomModel;
	protected boolean isSecret = false;

	public MSItemBase(String name, CreativeTabs tab, int stackSize, boolean hasCustomModel)
	{
		regName = IRegistryObject.unlocToReg(name);
		setUnlocalizedName(name);
		setCreativeTab(tab);
		setMaxStackSize(stackSize);
		MinestuckItems.items.add(this);
		this.hasCustomModel = hasCustomModel;
	}

	public MSItemBase(String name, boolean hasCustomModel)
	{
		this(name, MinestuckTabs.minestuck, 64, hasCustomModel);
	}

	public MSItemBase(String name)
	{
		this(name, MinestuckTabs.minestuck, 64, false);
	}

	public MSItemBase setSecret()
	{
		isSecret = true;
		return this;
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if(!isSecret)
			super.getSubItems(tab, items);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		String key = getUnlocalizedName()+".tooltip";
		UUID playerUUID = Minecraft.getMinecraft().player == null ? null : Minecraft.getMinecraft().player.getGameProfile().getId();
		String playerName = playerUUID != null && DEDICATED_TOOLTIPS.containsKey(playerUUID) ? DEDICATED_TOOLTIPS.get(playerUUID) : "";

		String str = "";

		if(!playerName.isEmpty() && I18n.hasKey(key+"." + playerName))
			str = (I18n.format(key + "." + playerName));
		else if(I18n.hasKey(key))
			str = I18n.format(key);

		if(!str.isEmpty())
			tooltip.add(str);

		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public void register(IForgeRegistry<Item> registry)
	{
		setRegistryName(regName);
		registry.register(this);
	}

	@Override
	public void registerModel()
	{
		if(hasCustomModel)
			return;
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
}
