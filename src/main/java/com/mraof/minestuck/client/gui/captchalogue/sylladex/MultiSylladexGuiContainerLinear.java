package com.mraof.minestuck.client.gui.captchalogue.sylladex;

import com.mraof.minestuck.captchalogue.sylladex.Sylladex;
import com.mraof.minestuck.captchalogue.sylladex.MultiSylladex;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MultiSylladexGuiContainerLinear extends MultiSylladexGuiContainer
{
	public MultiSylladexGuiContainerLinear(MultiSylladex<? extends Sylladex> sylladex, CardGuiContainer.CardTextureIndex[] firstTextureIndices, CardGuiContainer.CardTextureIndex[] lowerTextureIndices)
	{
		super(sylladex, firstTextureIndices, lowerTextureIndices);
	}

	@Override
	public void update(int depth, float partialTicks)
	{
		if (getContainers().isEmpty())
			return;

		float theta = (-depth + 1) * (float) Math.PI / 3f;
		if (theta < 0)
			theta += (float) Math.PI;
		float moveX = MathHelper.cos(theta);
		float moveY = MathHelper.sin(theta);
		float slope = moveY / moveX;

		float distanceX = 0;
		float distanceY = 0;
		for (SylladexGuiContainer container : getContainers())
		{
			if (container.isEmpty())
				continue;
			container.update(depth + 1, partialTicks);
			container.x = distanceX;
			if (slope < 0) container.x -= container.width;
			container.y = distanceY;

			float containerSlope = container.height / container.width;
			float relaxedMetric = (Math.abs(slope) < containerSlope ? container.width : container.height) * 0.9f;
			distanceX += moveX * relaxedMetric;
			distanceY += moveY * relaxedMetric;
		}

		constrainBounds();
	}
}
