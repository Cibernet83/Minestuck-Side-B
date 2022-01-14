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
	public void update(int depth, float partialTicks)
	{
		ArrayList<SylladexGuiContainer> containers = getContainers();
		if (getContainers().isEmpty())
			return;

		int size = (int)containers.stream().filter((container) -> !container.isEmpty()).count();

		float theta = (Minecraft.getMinecraft().player.ticksExisted + partialTicks) * ModusCyclone.CYCLE_SPEED;
		float radius = size == 1 ? 0 : size <= 4 ? 26 : 18 / MathHelper.sin((float)Math.PI/size);

		for(int i = 0, ai = 0; i < containers.size(); ++i)
		{
			SylladexGuiContainer container = containers.get(i);
			if (container.isEmpty())
				continue;

			container.update(depth + 1, partialTicks);

			float angle = ((theta + ai++ + 0.5f) / size - 0.25f) * (2f * (float)Math.PI);
			container.x = MathHelper.cos(angle) * radius + radius;
			container.y = MathHelper.sin(angle) * radius + radius;
		}

		constrainBounds();
	}
}
