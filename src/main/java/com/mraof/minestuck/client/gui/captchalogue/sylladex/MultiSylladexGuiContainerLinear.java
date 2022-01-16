package com.mraof.minestuck.client.gui.captchalogue.sylladex;

import com.mraof.minestuck.captchalogue.sylladex.MultiSylladex;
import com.mraof.minestuck.captchalogue.sylladex.Sylladex;
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
	public void update(int depth, float directionAngle, float partialTicks)
	{
		if (getContainers().isEmpty())
			return;

		float moveX = MathHelper.cos(directionAngle);
		float moveY = MathHelper.sin(directionAngle);
		float slope = moveY / moveX;

		directionAngle -= (float)Math.PI / 3f;
		directionAngle = MathHelper.positiveModulo(directionAngle, (float)Math.PI);

		float distanceX = 0;
		float distanceY = 0;
		for (SylladexGuiContainer container : getContainers())
		{
			if (container.isEmpty())
				continue;
			container.update(depth + 1, directionAngle, partialTicks);
			container.x = distanceX;
			if (slope < 0) container.x -= container.width;
			container.y = distanceY;

			float containerSlope = container.height / container.width;
			float relaxedMetric = container.width;//(Math.abs(slope) < containerSlope ? container.width : container.height) * 0.9f; //Make this smoove instead of an if
			distanceX += moveX * relaxedMetric;
			distanceY += moveY * relaxedMetric;
		}

		//constrainBounds wrong make this the sum of the widths n heights
		constrainBounds();
	}
}
