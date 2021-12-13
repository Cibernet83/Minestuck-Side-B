package com.mraof.minestuck.entity;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.entity.underling.EntityUnderling;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class EntityMSUUnderling extends EntityUnderling
{
    public EntityMSUUnderling(World par1World) {super(par1World);  }

    @Override
    public ResourceLocation getTextureResource()
    {
        this.textureResource = new ResourceLocation(Minestuck.MODID, this.getTexture());

        if(this.textureResource == null) {
            this.textureResource = new ResourceLocation(Minestuck.MODID, this.getUnderlingName() + ".png");
        }
        return this.textureResource;
    }

    @Override
    public String getTexture() {
        return "textures/mobs/underlings/" + this.getGristType().getName() + '_' + this.getUnderlingName() + ".png";
    }

}
