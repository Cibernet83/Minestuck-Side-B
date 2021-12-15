package com.mraof.minestuck.item.properties.beams;

import com.mraof.minestuck.capabilities.Beam;
import com.mraof.minestuck.item.properties.WeaponProperty;
import net.minecraft.item.ItemStack;

import java.awt.*;

public class PropertyRainbowBeam extends WeaponProperty implements IPropertyBeam
{
	@Override
	public void onBeamTick(ItemStack stack, Beam beam)
	{
		Color color = new Color(beam.color);
		float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), new float[3]);
		hsb[0] = (hsb[0]+0.05f) % 1f;
		hsb[1] = 0.6f;
		beam.color = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
	}
}
