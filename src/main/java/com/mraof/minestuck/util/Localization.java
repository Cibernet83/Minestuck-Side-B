package com.mraof.minestuck.util;

public class Localization
{
	public static void main(String[] args)
	{
		String unloc = "fooBarXYZThingA";
		String intendedReg = "foo_bar_xyz_thing_a";

		String actualReg = unlocToReg(unloc);
		System.out.println(actualReg);
		assert actualReg == intendedReg;
	}

	public static String unlocToReg(String unloc)
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
}