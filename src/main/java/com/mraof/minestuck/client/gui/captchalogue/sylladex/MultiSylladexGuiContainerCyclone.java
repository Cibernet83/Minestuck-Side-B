package com.mraof.minestuck.client.gui.captchalogue.sylladex;

import com.mraof.minestuck.captchalogue.modus.ModusCyclone;
import com.mraof.minestuck.captchalogue.sylladex.Sylladex;
import com.mraof.minestuck.captchalogue.sylladex.MultiSylladex;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

@SideOnly(Side.CLIENT)
public class MultiSylladexGuiContainerCyclone extends MultiSylladexGuiContainer
{
	public MultiSylladexGuiContainerCyclone(MultiSylladex<? extends Sylladex> sylladex, CardGuiContainer.CardTextureIndex[] firstTextureIndices, CardGuiContainer.CardTextureIndex[] lowerTextureIndices)
	{
		super(sylladex, firstTextureIndices, lowerTextureIndices);
	}

	@Override
	public void update(int depth, float directionAngle, float partialTicks)
	{
		ArrayList<SylladexGuiContainer> containers = getContainers();
		if (getContainers().isEmpty())
			return;

		int size = (int)containers.stream().filter((container) -> !container.isEmpty()).count();
		float theta = (Minecraft.getMinecraft().player.ticksExisted + partialTicks) * ModusCyclone.CYCLE_SPEED;

		float length = 0;
		for(int i = 0, ai = 0; i < containers.size(); i++)
		{
			SylladexGuiContainer container = containers.get(i);
			if (container.isEmpty())
				continue;

			float angle = ((theta + ai++ + 0.5f) / size - 0.25f) * (2f * (float)Math.PI);
			container.update(depth + 1, angle, partialTicks);
			length = Math.max(length, Math.abs(MathHelper.cos(angle)) * container.width + Math.abs(MathHelper.sin(angle)) * container.height);
		}

		float tan = MathHelper.sin((float)Math.PI / size) / MathHelper.cos((float)Math.PI / size);
		float radius = size == 1 ? 0 : size == 2 ? 6 : length / 2f / tan;

		for(int i = 0, ai = 0; i < containers.size(); i++)
		{
			SylladexGuiContainer container = containers.get(i);
			if (container.isEmpty())
				continue;

			float angle = ((theta + ai++ + 0.5f) / size - 0.25f) * (2f * (float)Math.PI);
			container.x = MathHelper.cos(angle) * radius + (MathHelper.cos(angle) - 1) / 2 * container.width;
			container.y = MathHelper.sin(angle) * radius + (MathHelper.sin(angle) - 1) / 2 * container.height;
		}

		//constrainBounds();
	}
}
