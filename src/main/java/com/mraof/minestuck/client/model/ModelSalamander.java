package com.mraof.minestuck.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class ModelSalamander extends ModelConsort
{
	ModelRenderer upperTail;
	ModelRenderer lowerTail;
	ModelRenderer upperJaw;
	ModelRenderer lowerJaw;
	ModelRenderer hood;


	public ModelSalamander()
	{
		this(false);
	}

	public ModelSalamander(boolean hasArms)
	{
		/*ModelRenderer(this, textureOffsetX, textureOffsetY)
		 * When figuring out the texture offset, the width will be 2 * sizeZ + 2 * sizeX, and the height will be sizeY + sizeZ
		 * addBox(offsetX,offsetY,offsetZ,sizeX,sizeY,sizeZ)
		 * setRotationPoint(x,y,z)
		 * x is width, y is height (lower numbers are higher), z is length
		 * 16 is 1 meter (width of one block)
		 * bottom is 24
		 */
		textureWidth = 64;
		textureHeight = 32;

		this.body = new ModelRenderer(this, 10, 18);
		this.body.setRotationPoint(0.0F, 12.0F, 0.0F);
		this.body.addBox(-3.0F, 0.0F, -3.0F, 6, 8, 6, 0.0F);
		this.rightLeg = new ModelRenderer(this, 0, 18);
		this.rightLeg.setRotationPoint(-1.0F, 20.0F, 0.0F);
		this.rightLeg.addBox(-2.0F, 0.0F, -2.0F, 2, 4, 3, 0.0F);
		this.leftLeg = new ModelRenderer(this, 0, 25);
		this.leftLeg.setRotationPoint(3.0F, 20.0F, 0.0F);
		this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 2, 4, 3, 0.0F);
		this.head = new ModelRenderer(this, 0, 0);
		this.head.setRotationPoint(0.0F, 12.0F, 0.0F);
		this.head.addBox(-3.0F, -4.0F, -3.5F, 6, 4, 7, 0.0F);
		this.upperJaw = new ModelRenderer(this, 0, 11);
		this.upperJaw.setRotationPoint(0.0F, 11.0F, 0.0F);
		this.upperJaw.addBox(-2.0F, -2.0F, -6.0F, 4, 2, 3, 0.0F);
		this.lowerJaw = new ModelRenderer(this, 14, 11);
		this.lowerJaw.setRotationPoint(0.0F, 13.0F, 0.0F);
		this.lowerJaw.addBox(-2.0F, -2.0F, -6.0F, 4, 1, 3, 0.0F);
		this.hood = new ModelRenderer(this, 26, 0);
		this.hood.setRotationPoint(0.0F, 12.0F, 0.0F);
		this.hood.addBox(-4.0F, -5.0F, -4.0F, 8, 5, 8, 0.0F);
		this.upperTail = new ModelRenderer(this, 34, 18);
		this.upperTail.setRotationPoint(0.0F, 18.0F, 3.0F);
		this.upperTail.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
		this.setRotation(upperTail, 0.22307169437408447F, 0.0F, 0.0F);
		this.lowerTail = new ModelRenderer(this, 34, 24);
		this.lowerTail.setRotationPoint(0.0F, 22.0F, 6.0F);
		this.lowerTail.addBox(-1.0F, 0.0F, -3.0F, 2, 2, 6, 0.0F);

	}

	/**
	 * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
	 * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
	 * "far" arms and legs can swing at most.
	 */
	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);

		if (this.isChild)
		{
			float var8 = 2.0F;
			GL11.glPushMatrix();
			GL11.glScalef(1.5F / var8, 1.5F / var8, 1.5F / var8);
			GL11.glTranslatef(0.0F, 16.0F * scale, 0.0F);
			head.render(scale);
			upperJaw.render(scale);
			lowerJaw.render(scale);
			hood.render(scale);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / var8, 1.0F / var8, 1.0F / var8);
			GL11.glTranslatef(0.0F, 24.0F * scale, 0.0F);
			//			if(hasArms)
			//			{
			//				this.rightArm.render(scale);
			//				this.leftArm.render(scale);
			//			}
			this.body.render(scale);
			this.rightLeg.render(scale);
			this.leftLeg.render(scale);
			this.upperTail.render(scale);
			this.lowerTail.render(scale);
			GL11.glPopMatrix();
		}
		else
		{
			head.render(scale);
			upperJaw.render(scale);
			lowerJaw.render(scale);
			hood.render(scale);
			//			if(hasArms)
			//			{
			//				this.rightArm.render(scale);
			//				this.leftArm.render(scale);
			//			}
			this.body.render(scale);
			this.rightLeg.render(scale);
			this.leftLeg.render(scale);
			this.upperTail.render(scale);
			this.lowerTail.render(scale);
		}
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	{
		float angleY = netHeadYaw / (180F / (float) Math.PI);
		float angleX = headPitch / (180F / (float) Math.PI);
		head.rotateAngleY = hood.rotateAngleY = upperJaw.rotateAngleY = lowerJaw.rotateAngleY = angleY;
		head.rotateAngleX = hood.rotateAngleX = upperJaw.rotateAngleX = lowerJaw.rotateAngleX = angleX;
		this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
	}

}
