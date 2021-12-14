package com.mraof.minestuck.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * A class for creating blocks with a sound type in the constructor, because the sound setter is protected.
 */
public class BlockFlamable extends MSBlockBase
	{
	
	public int flammability, fireSpread;
	
	public BlockFlamable(String name, Material material, MapColor mapColor, SoundType sound)
	{
		super(name, material, mapColor);
		setSoundType(sound);
	}
	
	public BlockFlamable(String name, Material material, SoundType sound)
	{
		super(name, material);
		setSoundType(sound);
	}
	
	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return flammability;
	}
	
	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return fireSpread;
	}
	
	public BlockFlamable setFireInfo(int flammability, int fireSpread)
	{
		this.flammability = flammability;
		this.fireSpread = fireSpread;
		return this;
	}
}