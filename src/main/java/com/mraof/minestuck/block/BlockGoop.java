package com.mraof.minestuck.block;

import com.mraof.minestuck.item.MinestuckTabs;

import com.mraof.minestuck.item.block.MSItemBlock;
import com.mraof.minestuck.util.IRegistryItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistry;

public class BlockGoop extends BlockBreakable implements IRegistryBlock
{
    private final String regName;

	public BlockGoop(String name) 
	{
		super(Material.CLAY, false);
		setCreativeTab(MinestuckTabs.minestuck);
		setSoundType(SoundType.SLIME);
		setUnlocalizedName(name);
        regName = IRegistryItem.unlocToReg(name);
        MinestuckBlocks.blocks.add(this);
		setHardness(0.1F);
		setHarvestLevel("Shovel", 0);
	}

	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
    {
        if (Math.abs(entityIn.motionY) < 0.1D && !entityIn.isSneaking())
        {
            double d0 = 0.4D + Math.abs(entityIn.motionY) * 0.2D;
            entityIn.motionX *= d0;
            entityIn.motionZ *= d0;
        }

        super.onEntityWalk(worldIn, pos, entityIn);
    }
	
	@Override
	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
    {
        if (entityIn.isSneaking())
        {
            super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
        }
        else
        {
            entityIn.fall(fallDistance, 0.0F);
        }
    }
	
	@Override
    public void onLanded(World worldIn, Entity entityIn)
    {
        if (entityIn.isSneaking())
        {
            super.onLanded(worldIn, entityIn);
        }
        else if (entityIn.motionY < 0.0D)
        {
            entityIn.motionY = -entityIn.motionY;

            if (!(entityIn instanceof EntityLivingBase))
            {
                entityIn.motionY *= 0.8D;
            }
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
		return new MSItemBlock(this);
	}
}