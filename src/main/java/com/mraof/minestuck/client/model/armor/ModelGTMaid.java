package com.mraof.minestuck.client.model.armor;// Made with Blockbench 3.7.2
// Exported for Minecraft version 1.12
// Paste this class into your mod and generate all required imports


import com.mraof.minestuck.util.EnumClass;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ModelGTMaid extends ModelGTAbstract
{
	private final ModelRenderer hood;

	public ModelGTMaid()
	{
		super(128, 128, EnumClass.MAID);
		addColorIgnores(6, 7);

		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.cubeList.add(new ModelBox(head, 0, 0, -5.0F, -1.0F + 0.2f, -5.0F, 10, 1, 10, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 22, 11, -5.0F, -9.0F + 0.2f, 4.0F, 10, 8, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 20, -5.0F, -9.0F + 0.2f, -4.0F, 1, 8, 8, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 11, -5.0F, -9.0F + 0.2f, -5.0F, 10, 8, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 18, 20, 4.0F, -9.0F + 0.2f, -4.0F, 1, 8, 8, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 40, 0, -5.0F, -10.0F + 0.2f, -4.0F, 10, 1, 8, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 18, 36, -5.0F, -10.0F + 0.2f, 4.0F, 10, 1, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 36, -4.0F, -10.0F + 0.2f, -5.0F, 8, 1, 1, 0.0F, false));

		hood = new ModelRenderer(this);
		hood.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.addChild(hood);
		hood.cubeList.add(new ModelBox(hood, 110, 10, -4.0F, -9.0F, 5.0F, 8, 8, 1, 0.5F, false));
		hood.cubeList.add(new ModelBox(hood, 108, 19, -4.0F, -8.5F, 5.5F, 8, 8, 2, 0.0F, false));
		hood.cubeList.add(new ModelBox(hood, 114, 29, -3.0F, -6.5F, 7.0F, 6, 6, 1, 0.5F, false));
		hood.cubeList.add(new ModelBox(hood, 112, 36, -3.0F, -6.0F, 7.5F, 6, 6, 2, 0.0F, false));
		hood.cubeList.add(new ModelBox(hood, 118, 44, -2.0F, -4.0F, 9.0F, 4, 4, 1, 0.5F, false));
		hood.cubeList.add(new ModelBox(hood, 116, 49, -2.0F, -3.5F, 9.5F, 4, 4, 2, 0.0F, false));
		hood.cubeList.add(new ModelBox(hood, 122, 55, -1.0F, -1.5F, 11.0F, 2, 2, 1, 0.5F, false));
		hood.cubeList.add(new ModelBox(hood, 120, 58, -1.0F, -1.0F, 11.5F, 2, 2, 2, 0.0F, false));
		hood.cubeList.add(new ModelBox(hood, 124, 62, -0.5F, 0.0F, 13.0F, 1, 1, 1, 0.0F, false));
		hood.cubeList.add(new ModelBox(hood, 122, 64, -0.5F, 0.5F, 12.5F, 1, 1, 2, 0.0F, false));

		neck.setRotationPoint(0.0F, 0.0F, 0.0F);
		neck.cubeList.add(new ModelBox(neck, 106, 0, -5.0F, 0.0F, 2.0F, 10, 1, 1, 0.0F, false));
		neck.cubeList.add(new ModelBox(neck, 84, 0, -5.0F, 0.0F, -3.0F, 10, 1, 1, 0.0F, false));
		neck.cubeList.add(new ModelBox(neck, 80, 112, -4.0F, 0.0F, -2.0F, 8, 12, 4, 0.2515F, false));

		skirtFront.setRotationPoint(0.0F, 12.0F, 0.0F);
		skirtFront.cubeList.add(new ModelBox(skirtFront, 35, 126, -4.5F, -2.0F, -2.252F, 9, 2, 0, 0.0F, false));
		skirtFront.cubeList.add(new ModelBox(skirtFront, 37, 125, -3.5F, 0.0F, -2.252F, 7, 1, 0, 0.0F, false));
		skirtFront.cubeList.add(new ModelBox(skirtFront, 39, 124, -2.5F, 1.0F, -2.252F, 5, 1, 0, 0.0F, false));
		skirtFront.cubeList.add(new ModelBox(skirtFront, 41, 122, -1.5F, 2.0F, -2.252F, 3, 2, 0, 0.0F, false));

		skirtBack.setRotationPoint(0.0F, 12.0F, 0.252F);
		skirtBack.cubeList.add(new ModelBox(skirtBack, 58, 123, -4.0F, -1.0F, 2.252F, 4, 5, 0, 0.0F, false));
		skirtBack.cubeList.add(new ModelBox(skirtBack, 59, 119, -3.25F, 4.0F, 2.252F, 3, 4, 0, 0.0F, false));
		skirtBack.cubeList.add(new ModelBox(skirtBack, 60, 117, -2.5F, 8.0F, 2.252F, 2, 2, 0, 0.0F, false));
		skirtBack.cubeList.add(new ModelBox(skirtBack, 61, 115, -1.75F, 9.5F, 2.252F, 1, 2, 0, 0.0F, false));
		skirtBack.cubeList.add(new ModelBox(skirtBack, 58, 123, 0.0F, -1.0F, 2.252F, 4, 5, 0, 0.0F, false));
		skirtBack.cubeList.add(new ModelBox(skirtBack, 59, 119, 0.25F, 4.0F, 2.252F, 3, 4, 0, 0.0F, false));
		skirtBack.cubeList.add(new ModelBox(skirtBack, 60, 117, 0.5F, 8.0F, 2.252F, 2, 2, 0, 0.0F, false));
		skirtBack.cubeList.add(new ModelBox(skirtBack, 61, 115, 0.75F, 9.5F, 2.252F, 1, 2, 0, 0.0F, false));

		torso.setRotationPoint(0.0F, 0.0F, 0.0F);
		torso.cubeList.add(new ModelBox(torso, 104, 112, -4.0F, 0.0F, -2.0F, 8, 12, 4, 0.251F, false));

		rightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		rightArm.cubeList.add(new ModelBox(rightArm, 0, 74, -3.0F, -2.0F, -2.0F, 4, 12, 4, 0.251F, false));

		leftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		leftArm.cubeList.add(new ModelBox(leftArm, 0, 74, -1.0F, -2.0F, -2.0F, 4, 12, 4, 0.251F, true));

		rightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
		rightLeg.cubeList.add(new ModelBox(rightLeg, 0, 96, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.251F, false));

		leftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
		leftLeg.cubeList.add(new ModelBox(leftLeg, 0, 96, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.251F, true));

		rightFoot.setRotationPoint(-2.0F, 12.0F, 0.0F);
		rightFoot.cubeList.add(new ModelBox(rightFoot, 0, 112, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.252F, false));

		leftFoot.setRotationPoint(2.0F, 12.0F, 0.0F);
		leftFoot.cubeList.add(new ModelBox(leftFoot, 0, 112, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.252F, true));
	}

	@Override
	public EntityEquipmentSlot getSkirtSlot()
	{
		return EntityEquipmentSlot.CHEST;
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}