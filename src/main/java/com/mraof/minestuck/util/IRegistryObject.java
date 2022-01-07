package com.mraof.minestuck.util;

import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public interface IRegistryObject<T extends IForgeRegistryEntry<T>>
{
	static String unlocToReg(String unloc)
	{
		StringBuilder reg = new StringBuilder();
		for (int i = 0; i < unloc.length(); i++)
		{
			if (Character.isUpperCase(unloc.charAt(i)) && (
					(i > 0 && Character.isLowerCase(unloc.charAt(i - 1))) ||
							(i < unloc.length() - 1 && Character.isLowerCase(unloc.charAt(i + 1)))
			))
				reg.append('_');
			reg.append(unloc.charAt(i));
		}
		return reg.toString().toLowerCase();
	}
	void register(IForgeRegistry<T> registry);
}
