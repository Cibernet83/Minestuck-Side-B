package com.mraof.minestuck.block;

import com.mraof.minestuck.client.particles.MinestuckParticles;
import com.mraof.minestuck.item.block.MSItemBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockGlorb extends MSBlockBase
{
	public static final AxisAlignedBB EMPTY_AABB = new AxisAlignedBB(0, 0, 0, 0, 0, 0);
	public static final AxisAlignedBB AABB = new AxisAlignedBB(5 / 16D, 5 / 16D, 5 / 16D, 11 / 16D, 11 / 16D, 11 / 16D);

	public static final SoundType SOUND_TYPE = new SoundType(0.7F, 1.8F, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundEvents.BLOCK_FIRE_AMBIENT, SoundEvents.BLOCK_FIRE_AMBIENT, SoundEvents.BLOCK_FIRE_AMBIENT, SoundEvents.BLOCK_FIRE_AMBIENT);

	public BlockGlorb()
	{
		super("glowOrb", Material.FIRE);
		setSoundType(SOUND_TYPE);
		setLightLevel(1);
	}

	@Override
	public boolean isFullBlock(IBlockState state)
	{
		return false;
	}

	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return AABB;
	}

	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return NULL_AABB;
	}

	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		if (rand.nextInt(4) == 0)
			MinestuckParticles.spawnAuraParticles(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0xFFFDDE, Math.max(1, rand.nextInt(6) - 3));
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		super.breakBlock(worldIn, pos, state);
		worldIn.playEvent(1009, pos, 0);
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public boolean addLandingEffects(IBlockState state, WorldServer worldObj, BlockPos blockPosition, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles)
	{
		return true;
	}

	@Override
	public boolean addRunningEffects(IBlockState state, World world, BlockPos pos, Entity entity)
	{
		return true;
	}

	@Override
	public boolean addHitEffects(IBlockState state, World worldObj, RayTraceResult target, ParticleManager manager)
	{
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager)
	{
		MinestuckParticles.spawnAuraParticles(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0xFFFDDE, 8);
		return true;
	}

	@Override
	public MSItemBlock getItemBlock()
	{
		return null;
	}
}
