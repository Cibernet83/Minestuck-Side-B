package com.mraof.minestuck.block;

import com.mraof.minestuck.capabilities.MinestuckCapabilities;
import com.mraof.minestuck.item.MinestuckTabs;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.MinestuckPlayerData;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nullable;
import java.util.List;

public class BlockSpectralHeroStone extends MSBlockBase implements IGodTierBlock
{
	protected EnumAspect aspect;

	public BlockSpectralHeroStone(EnumAspect aspect)
	{
		super("heroStoneSpectral", Material.ROCK, getAspectMapColor(aspect));
		this.aspect = aspect;
		setHarvestLevel("pickaxe", 3);
		setHardness(20.0F);
		setResistance(2000.0F);
		setCreativeTab(MinestuckTabs.godTier);
	}

	protected static MapColor getAspectMapColor(EnumAspect aspect)
	{
		if (aspect == null)
			return MapColor.SILVER;
		switch (aspect)
		{
			case DOOM:
				return MapColor.GREEN_STAINED_HARDENED_CLAY;
			case HOPE:
				return MapColor.SAND;
			case LIFE:
				return MapColor.SILVER_STAINED_HARDENED_CLAY;
			case MIND:
				return MapColor.GRASS;
			case RAGE:
				return MapColor.PURPLE;
			case TIME:
				return MapColor.RED;
			case VOID:
				return MapColor.BLUE_STAINED_HARDENED_CLAY;
			case BLOOD:
				return MapColor.NETHERRACK;
			case HEART:
				return MapColor.MAGENTA_STAINED_HARDENED_CLAY;
			case LIGHT:
				return MapColor.ADOBE;
			case SPACE:
				return MapColor.BLACK;
			case BREATH:
				return MapColor.LAPIS;
			default:
				return MapColor.SILVER;
		}
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState)
	{
		if (entityIn instanceof EntityPlayer && entityIn.getCapability(MinestuckCapabilities.GOD_TIER_DATA, null).isGodTier()
					&& (aspect == null || aspect == MinestuckPlayerData.getData((EntityPlayer) entityIn).title.getHeroAspect()))
			return;
		super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, isActualState);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public void register(IForgeRegistry<Block> registry)
	{
		setRegistryName("hero_stone_spectral" + (aspect == null ? "" : "_" + aspect.toString()));
		registry.register(this);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, tooltip, flagIn);

		String heroAspect = TextFormatting.OBFUSCATED + "Thing" + TextFormatting.RESET;

		if (getAspect() != null)
			heroAspect = getAspect().getDisplayName();


		tooltip.add(heroAspect);
	}

	@Override
	public EnumAspect getAspect()
	{
		return aspect;
	}

	@Override
	public boolean canGodTier()
	{
		return false;
	}
}
