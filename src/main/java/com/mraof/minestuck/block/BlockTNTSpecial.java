package com.mraof.minestuck.block;

import com.mraof.minestuck.item.MinestuckTabs;
import com.mraof.minestuck.item.block.MSItemBlock;
import com.mraof.minestuck.util.IRegistryObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Random;

public class BlockTNTSpecial extends BlockTNT implements IRegistryBlock
{
	private final boolean primed, unstable, instant;
	private final String regName;

	public BlockTNTSpecial(String name, boolean primed, boolean unstable, boolean instant)
	{
		super();
		setCreativeTab(MinestuckTabs.minestuck);
		setHardness(0.0F);
		setSoundType(SoundType.PLANT);
		this.primed = primed;
		this.unstable = unstable;
		this.instant = instant;
		if (unstable)
			setTickRandomly(true);
		setUnlocalizedName(name);
		regName = IRegistryObject.unlocToReg(name);
		MinestuckBlocks.blocks.add(this);
	}

	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn)
	{
		if (!worldIn.isRemote)
		{
			EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(worldIn, (double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F), (double) ((float) pos.getZ() + 0.5F), explosionIn.getExplosivePlacedBy());
			entitytntprimed.setFuse(worldIn.rand.nextInt(entitytntprimed.getFuse() / 4) + entitytntprimed.getFuse() / 8);
			if (instant)
				entitytntprimed.setFuse(entitytntprimed.getFuse() / 2);
			worldIn.spawnEntity(entitytntprimed);
		}
	}

	@Override
	public void explode(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase igniter)
	{
		if (!worldIn.isRemote)
		{
			if (state.getValue(EXPLODE).booleanValue())
			{
				EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(worldIn, (double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F), (double) ((float) pos.getZ() + 0.5F), igniter);
				if (instant)
					entitytntprimed.setFuse(0);
				worldIn.spawnEntity(entitytntprimed);
				worldIn.playSound(null, entitytntprimed.posX, entitytntprimed.posY, entitytntprimed.posZ, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (unstable && rand.nextDouble() < 0.1)
		{
			this.explode(worldIn, pos, worldIn.getBlockState(pos).withProperty(EXPLODE, Boolean.valueOf(true)), null);
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn)
	{
		if (primed)
		{
			this.explode(worldIn, pos, worldIn.getBlockState(pos).withProperty(EXPLODE, Boolean.valueOf(true)), playerIn);
			worldIn.setBlockToAir(pos);
		}
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
		return new MSItemBlock(this)
		{
			@Override
			@SideOnly(Side.CLIENT)
			public void registerModel()
			{
				super.registerModel();
				ModelLoader.setCustomStateMapper(BlockTNTSpecial.this, (new StateMap.Builder()).ignore(BlockTNT.EXPLODE).build());
			}
		};
	}
}