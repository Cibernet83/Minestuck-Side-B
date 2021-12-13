package com.mraof.minestuck.block;

import com.mraof.minestuck.tileentity.TileEntityEffectBeacon;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockEffectBeacon extends MSBlockBase
{
    protected final PotionEffect effect;

    public BlockEffectBeacon(MapColor blockMapColorIn, PotionEffect effect, String name)
    {
        super(Material.IRON, blockMapColorIn, name);
        this.effect = effect;
        setBlockUnbreakable();
        setResistance(6000000.0F);
        disableStats();
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityEffectBeacon();
    }

    public PotionEffect getEffect()
    {
        return new PotionEffect(effect.getPotion(), effect.getDuration(), effect.getAmplifier(), effect.getIsAmbient(), effect.doesShowParticles());
    }
}
