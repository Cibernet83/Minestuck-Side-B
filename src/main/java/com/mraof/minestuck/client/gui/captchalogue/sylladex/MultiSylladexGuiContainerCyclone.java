package com.mraof.minestuck.client.gui.captchalogue.sylladex;

import com.mraof.minestuck.captchalogue.modus.ModusCyclone;
import com.mraof.minestuck.captchalogue.sylladex.MultiSylladex;
import com.mraof.minestuck.captchalogue.sylladex.Sylladex;
import com.mraof.minestuck.util.MinestuckUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

@SideOnly(Side.CLIENT)
public class MultiSylladexGuiContainerCyclone extends MultiSylladexGuiContainer
{
	static final float QUADRANT = (float)Math.PI / 2;

	public MultiSylladexGuiContainerCyclone(MultiSylladex<? extends Sylladex> sylladex, CardGuiContainer.CardTextureIndex[] firstTextureIndices, CardGuiContainer.CardTextureIndex[] lowerTextureIndices)
	{
		super(sylladex, firstTextureIndices, lowerTextureIndices);
	}

	@Override
	public void update(int depth, float directionAngle, float partialTicks)
	{
		ArrayList<SylladexGuiContainer> containers = getContainers();
		if (containers.isEmpty())
			return;

		int size = (int)containers.stream().filter((container) -> !container.isEmpty()).count();
		float theta = (Minecraft.getMinecraft().player.ticksExisted + partialTicks) * ModusCyclone.CYCLE_SPEED;

		float length = 0;
		for(int i = 0, ai = 0; i < containers.size(); i++)
		{
			SylladexGuiContainer container = containers.get(i);
			if (container.isEmpty())
				continue;

			float angle = MathHelper.positiveModulo(((theta + ai++ + 0.5f) / size - 0.25f) * ((float)Math.PI * 2f), (float)Math.PI * 2);
			container.update(depth + 1, angle, partialTicks);

			float newLengthMax = container.getMaxVertexDistance(angle);
			float newLengthMin = container.getMinVertexDistance(angle);
			float newLengthMid = (newLengthMax + newLengthMin) / 2f;
			float newLength = newLengthMax - newLengthMin;

			if (angle < QUADRANT)
			{
				if (newLengthMid >= 0)
				{
					container.x = 0;
					container.y = -newLengthMid / MathHelper.cos(angle);
				}
				else
				{
					container.x = newLengthMid / MathHelper.sin(angle);
					container.y = 0;
				}
			}
			else if (angle < QUADRANT * 2)
			{
				if (newLengthMid >= container.height * MathHelper.cos(angle))
				{
					container.x = -container.width;
					container.y = newLengthMid / MathHelper.cos(angle) - container.height;
				}
				else
				{
					container.x = newLengthMid / MathHelper.sin(angle);
					container.y = 0;
				}
			}
			else if (angle < QUADRANT * 3)
			{
				if (newLengthMid < 0)
				{
					container.x = -container.width;
					container.y = newLengthMid / MathHelper.cos(angle) - container.height;
				}
				else
				{
					container.x = -newLengthMid / MathHelper.sin(angle) - container.width;
					container.y = -container.height;
				}
			}
			else
			{
				if (newLengthMid < container.height * MathHelper.cos(angle))
				{
					container.x = 0;
					container.y = -newLengthMid / MathHelper.cos(angle);
				}
				else
				{
					container.x = -newLengthMid / MathHelper.sin(angle) - container.width;
					container.y = -container.height;
				}
			}

			if (newLength > length)
				length = newLength;
		}

		float radius = size == 1 ? 0 : size == 2 ? 6 : length / 2 / MinestuckUtils.tan((float)Math.PI / size);

		for(int i = 0, ai = 0; i < containers.size(); i++)
		{
			SylladexGuiContainer container = containers.get(i);
			if (container.isEmpty())
				continue;

			float angle = ((theta + ai++ + 0.5f) / size - 0.25f) * (2f * (float)Math.PI);
			container.x += MathHelper.cos(angle) * radius;
			container.y += MathHelper.sin(angle) * radius;
		}

		constrainBounds();
	}
}
