package com.mraof.minestuck.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.resources.Language;

import java.util.Locale;

public final class Translator
{
	private Translator()
	{
	}

	/*public static String translateToLocal(String key)
	{
		return I18n.hasKey(key);
	}*/

	public static String translateToLocalFormatted(String key, Object... format)
	{
		return I18n.format(key, format);
	}

	public static String toLowercaseWithLocale(String string)
	{
		return string.toLowerCase(getLocale());
	}

	@SuppressWarnings("ConstantConditions")
	private static Locale getLocale()
	{
		Minecraft minecraft = Minecraft.getMinecraft();
		if (minecraft != null)
		{
			LanguageManager languageManager = minecraft.getLanguageManager();
			if (languageManager != null)
			{
				Language currentLanguage = languageManager.getCurrentLanguage();
				if (currentLanguage != null)
				{
					return currentLanguage.getJavaLocale();
				}
			}
		}
		return Locale.getDefault();
	}
}
