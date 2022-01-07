package com.mraof.minestuck.block;

import com.mraof.minestuck.util.EnumAspect;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGlowingHeroStone extends MSBlockBase implements IGodTierBlock
{

	public BlockGlowingHeroStone()
	{
		super("glowingHeroStone", Material.ROCK, MapColor.QUARTZ);
		setHarvestLevel("pickaxe", 3);
		setHardness(20.0F);
		setBlockUnbreakable();
		setSoundType(SoundType.GLASS);
		setLightLevel(1.0F);
	}

	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

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
	public EnumAspect getAspect()
	{
		return null;
	}

	@Override
	public boolean canGodTier()
	{
		return false;
	}
}
