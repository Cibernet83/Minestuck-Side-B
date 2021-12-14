package com.mraof.minestuck.block;

import com.mraof.minestuck.item.block.MSItemBlock;
import com.mraof.minestuck.item.MinestuckTabs;
import com.mraof.minestuck.util.IRegistryItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nullable;
import java.util.List;


public class MSBlockBase extends Block implements IRegistryBlock
{
	private final String regName;

	public MSBlockBase(String name, CreativeTabs tab, Material material, MapColor mapColor)
	{
		super(material, mapColor);
		setUnlocalizedName(name);
		regName = IRegistryItem.unlocToReg(name);
		setCreativeTab(tab);
		MinestuckBlocks.blocks.add(this);
	}

	public MSBlockBase(String name, Material material, MapColor mapColor)
	{
		this(name, MinestuckTabs.minestuck, material, mapColor);
	}

	public MSBlockBase(String name, Material material)
	{
		this(name, material, material.getMaterialMapColor());
	}

	public MSBlockBase(String name, MapColor mapColor)
	{
		this(name, Material.ROCK, mapColor);
	}

	public Block setHarvestLevelChain(String toolClass, int harvestLevel)
	{
		setHarvestLevel(toolClass, harvestLevel);
		return this;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
	{
		String key = getUnlocalizedName()+".tooltip";
		if(!I18n.translateToLocal(key).equals(key))
			tooltip.add(I18n.translateToLocal(key));
		super.addInformation(stack, player, tooltip, advanced);
	}

	@Override
	public void register(IForgeRegistry<Block> registry)
	{
		setRegistryName(regName);
		registry.register(this);
	}

	@Override
	public MSItemBlock getItemBlock()
	{
		return new MSItemBlock(this);
	}
}
