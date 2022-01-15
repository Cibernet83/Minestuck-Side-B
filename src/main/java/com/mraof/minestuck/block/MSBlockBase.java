package com.mraof.minestuck.block;

import com.mraof.minestuck.item.MinestuckTabs;
import com.mraof.minestuck.util.IRegistryObject;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
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

	public MSBlockBase(String name, MapColor mapColor)
	{
		this(name, Material.ROCK, mapColor);
	}

	public MSBlockBase(String name, Material material, MapColor mapColor)
	{
		this(name, MinestuckTabs.minestuck, material, mapColor);
	}

	public MSBlockBase(String name, CreativeTabs tab, Material material, MapColor mapColor)
	{
		super(material, mapColor);
		setUnlocalizedName(name);
		regName = IRegistryObject.unlocToReg(name);
		setCreativeTab(tab);
		MinestuckBlocks.blocks.add(this);
	}

	public MSBlockBase(String name)
	{
		this(name, Material.ROCK);
	}

	public MSBlockBase(String name, Material material)
	{
		this(name, material, material.getMaterialMapColor());
	}

	public Block setHarvestLevelChain(String toolClass, int harvestLevel)
	{
		setHarvestLevel(toolClass, harvestLevel);
		return this;
	}

	@Override
	public void register(IForgeRegistry<Block> registry)
	{
		setRegistryName(regName);
		registry.register(this);
	}

	@Override
	public Block setSoundType(SoundType sound)
	{
		return super.setSoundType(sound);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
	{
		String key = getUnlocalizedName() + ".tooltip";
		if (!I18n.translateToLocal(key).equals(key))
			tooltip.add(I18n.translateToLocal(key));
		super.addInformation(stack, player, tooltip, advanced);
	}
}
