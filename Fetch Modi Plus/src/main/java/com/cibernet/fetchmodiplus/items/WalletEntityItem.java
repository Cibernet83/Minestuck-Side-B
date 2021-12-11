package com.cibernet.fetchmodiplus.items;

import com.mraof.minestuck.inventory.captchalouge.CaptchaDeckHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class WalletEntityItem extends BaseItem
{
    public WalletEntityItem(String name)
    {
        super(name);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        if(stack.hasTagCompound() && stack.getTagCompound().hasKey("EntityName"))
            return stack.getTagCompound().getString("EntityName");
        return super.getItemStackDisplayName(stack);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        spawnEntity(entityIn, worldIn, stack);
    }

    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem)
    {
        spawnEntity(entityItem, entityItem.world, entityItem.getItem());
        return super.onEntityItemUpdate(entityItem);
    }

    public static void spawnEntity(Entity source, World world, ItemStack stack)
    {
        if(world.isRemote || !stack.hasTagCompound() || !stack.getTagCompound().hasKey("Entity"))
            return;

        NBTTagCompound nbt = stack.getTagCompound().getCompoundTag("Entity");

        if(nbt.hasUniqueId("UUID") && world instanceof WorldServer && ((WorldServer) world).getEntityFromUuid(nbt.getUniqueId("UUID")) != null)
        {
            nbt.removeTag("UUIDMost");
            nbt.removeTag("UUIDLeast");
        }

        Entity entity = AnvilChunkLoader.readWorldEntityPos(nbt, world, source.posX, source.posY, source.posZ, true);
        if(entity != null) {
            entity.motionX = CaptchaDeckHandler.rand.nextDouble() - 0.5D;
            entity.motionZ = CaptchaDeckHandler.rand.nextDouble() - 0.5D;
            entity.setPosition(source.posX, source.posY + 1.0D, source.posZ);
        }
        stack.shrink(1);
    }

    public static void storeEntity(Entity entity, ItemStack stack)
    {
        if(entity instanceof EntityPlayer)
            return;

        NBTTagCompound nbt = entity.writeToNBT(new NBTTagCompound());
        nbt.setString("id", EntityRegistry.getEntry(entity.getClass()).getRegistryName().toString());

        if(!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setTag("Entity", nbt);
        stack.getTagCompound().setString("EntityName", entity.getName());

    }
}
