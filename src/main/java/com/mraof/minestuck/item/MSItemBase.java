package com.mraof.minestuck.item;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.util.IRegistryObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MSItemBase extends Item implements IRegistryItem
{
	public static final ArrayList<String> DEDICATED_TOOLTIPS = new ArrayList<String>()
	{{
		add("Cibernet");
		add("Ishumire");
		add("Badadamadaba");
		add("ThatLameOverlord");
		add("Nhezak");
		add("Fishwreck");
		add("Akisephila");
		add("ZeroCraftsman");
		add("_draconix");
		add("Owo_XxX_owO");
		add("carefreeDesigner");
		add("JDubSupreme");
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
		String playerName = Minecraft.getMinecraft().player == null ? "" : Minecraft.getMinecraft().player.getName();
		String str = "";

		if(DEDICATED_TOOLTIPS.contains(playerName) && net.minecraft.client.resources.I18n.hasKey(key+"."+playerName))
			str = (I18n.translateToLocal(key + "." + playerName));
		else if(net.minecraft.client.resources.I18n.hasKey(key) && !stack.getItem().getRegistryName().getResourceDomain().equals(Minestuck.MODID))
			str = (I18n.translateToLocal(key));

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
