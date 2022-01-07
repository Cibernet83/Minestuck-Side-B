package com.mraof.minestuck.captchalogue.captchalogueable;

import com.mraof.minestuck.client.gui.captchalogue.sylladex.GuiSylladex;
import com.mraof.minestuck.util.SylladexUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class CaptchalogueableEntity implements ICaptchalogueable
{
	private NBTTagCompound entityNbt;

	public CaptchalogueableEntity(Entity entity)
	{
		entityNbt = new NBTTagCompound();
		//TODO players/e dragon crash the game :(
		entityNbt.setString("id", EntityRegistry.getEntry(entity.getClass()).getRegistryName().toString());
		entity.writeToNBT(entityNbt);

		if (entityNbt.hasUniqueId("UUID"))
		{
			entityNbt.removeTag("UUIDLeast");
			entityNbt.removeTag("UUIDMost");
		}
	}

	public CaptchalogueableEntity(NBTTagCompound nbtTagCompound)
	{
		entityNbt = nbtTagCompound;
	}

	@Override
	public NBTTagCompound writeData()
	{
		return entityNbt;
	}

	@Override
	public void grow(ICaptchalogueable other) { }

	@Override
	public boolean isEmpty()
	{
		return entityNbt.hasNoTags() && !ForgeRegistries.ENTITIES.containsKey(new ResourceLocation(entityNbt.getString("id")));
	}

	@Override
	public boolean isCompatibleWith(ICaptchalogueable other)
	{
		return false;
	}

	@Override
	public void fetch(EntityPlayer player)
	{
		eject(player);
	}

	@Override
	public void eject(EntityPlayer player)
	{
		Entity entity = AnvilChunkLoader.readWorldEntityPos(entityNbt, player.world, player.posX, player.posY + 1.0D, player.posZ, true);
		if (entity != null)
		{
			entity.motionX = SylladexUtils.rand.nextDouble() - 0.5D;
			entity.motionZ = SylladexUtils.rand.nextDouble() - 0.5D;
		}
	}

	@Override
	public void drop(World world, double posX, double posY, double posZ)
	{
		AnvilChunkLoader.readWorldEntityPos(entityNbt, world, posX, posY, posZ, true);
	}

	/**
	 * only used in debugging, use getDisplayName instead
	 */
	@Override
	@Deprecated
	public String getName()
	{
		return entityNbt.getString("id");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void draw(GuiSylladex gui, float mouseX, float mouseY, float partialTicks)
	{
		Entity entity = AnvilChunkLoader.readWorldEntity(entityNbt, Minecraft.getMinecraft().world, false);

		if (entity != null)
			drawEntityOnScreen(10, 20, 10, -mouseX, -mouseY, entity);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getDisplayName()
	{
		Entity entity = AnvilChunkLoader.readWorldEntity(entityNbt, Minecraft.getMinecraft().world, false);
		return entity.getName();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ITextComponent getTextComponent()
	{

		Entity entity = AnvilChunkLoader.readWorldEntity(entityNbt, Minecraft.getMinecraft().world, false);
		return entity.getDisplayName();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderTooltip(GuiSylladex gui, int x, int y)
	{
		List<String> tooltip = new ArrayList<>();
		tooltip.add(getDisplayName());

		gui.drawHoveringText(tooltip, x, y);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTextureKey()
	{
		return "entity";
	}

	@SideOnly(Side.CLIENT)
	public static void drawEntityOnScreen(int posX, int posY, float scale, float mouseX, float mouseY, Entity ent)
	{
		GlStateManager.enableColorMaterial();
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) posX, (float) posY, 50.0F);
		GlStateManager.scale(scale, -scale, scale);
		float f1 = ent.rotationYaw;
		float f2 = ent.rotationPitch;
		float f = (ent instanceof EntityLivingBase) ? ((EntityLivingBase) ent).renderYawOffset : 0;
		float f3 = (ent instanceof EntityLivingBase) ? ((EntityLivingBase) ent).prevRotationYawHead : 0;
		float f4 = (ent instanceof EntityLivingBase) ? ((EntityLivingBase) ent).rotationYawHead : 0;

		GlStateManager.enableDepth();
		GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);

		if (ent instanceof EntityLivingBase)
		{
			GlStateManager.rotate(-((float) Math.atan((double) (mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
			ent.rotationYaw = (float) Math.atan((double) (mouseX / 40.0F)) * 40.0F;
			ent.rotationPitch = -((float) Math.atan((double) (mouseY / 40.0F))) * 20.0F;

			((EntityLivingBase) ent).renderYawOffset = (float) Math.atan((double) (mouseX / 40.0F)) * 20.0F;
			((EntityLivingBase) ent).rotationYawHead = ent.rotationYaw;
			((EntityLivingBase) ent).prevRotationYawHead = ent.rotationYaw;
		}

		GlStateManager.translate(0.0F, 0.0F, 0.0F);
		RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
		rendermanager.setPlayerViewY(180.0F);
		rendermanager.setRenderShadow(false);
		rendermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
		rendermanager.setRenderShadow(true);
		ent.rotationYaw = f1;
		ent.rotationPitch = f2;

		if (ent instanceof EntityLivingBase)
		{
			((EntityLivingBase) ent).renderYawOffset = f;
			((EntityLivingBase) ent).prevRotationYawHead = f3;
			((EntityLivingBase) ent).rotationYawHead = f4;
		}

		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.disableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}
}
