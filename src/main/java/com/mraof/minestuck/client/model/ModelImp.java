// Date: 31/12/2012 15:51:35
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX
package com.mraof.minestuck.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class ModelImp extends ModelBase
{
	//fields
	ModelRenderer Head;
	ModelRenderer Body;
	ModelRenderer Armright;
	ModelRenderer Armleft;
	ModelRenderer Legleft;
	ModelRenderer Legright;

	public ModelImp()
	{
		textureWidth = 32;
		textureHeight = 32;

		Head = new ModelRenderer(this, 0, 0);
		Head.addBox(-3F, -3F, -5F, 5, 5, 5);
		Head.setRotationPoint(0F, 15F, 0F);
		Head.mirror = true;
		setRotation(Head, 0F, 0F, 0F);
		Body = new ModelRenderer(this, 0, 10);
		Body.addBox(-3F, -4F, -2F, 5, 6, 4);
		Body.setRotationPoint(0F, 19F, 0F);
		Body.mirror = true;
		setRotation(Body, 0F, 0F, 0F);
		Armright = new ModelRenderer(this, 0, 20);
		Armright.addBox(-1F, 0F, -1F, 1, 5, 1);
		Armright.setRotationPoint(-3F, 16F, 0F);
		Armright.mirror = true;
		setRotation(Armright, 0F, 0.0371786F, 0.0371786F);
		Armleft = new ModelRenderer(this, 0, 20);
		Armleft.addBox(0F, 0F, -1F, 1, 5, 1);
		Armleft.setRotationPoint(2F, 16F, 0F);
		Armleft.mirror = true;
		setRotation(Armleft, 0F, 0F, 0F);
		Legleft = new ModelRenderer(this, 4, 20);
		Legleft.addBox(-1F, 0F, 0F, 1, 3, 1);
		Legleft.setRotationPoint(-1F, 21F, 0F);
		Legleft.mirror = true;
		setRotation(Legleft, 0F, 0F, 0F);
		Legright = new ModelRenderer(this, 4, 20);
		Legright.addBox(0F, 0F, 0F, 1, 3, 1);
		Legright.setRotationPoint(0F, 21F, 0F);
		Legright.mirror = true;
		setRotation(Legright, 0F, 0F, 0F);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		scale *= 1.5F;
		GL11.glTranslatef(0F, -0.8F, 0F);

		setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
		Head.render(scale);
		Body.render(scale);
		Armright.render(scale);
		Armleft.render(scale);
		Legleft.render(scale);
		Legright.render(scale);
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	{
		this.Legleft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.Legright.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
	}

}
