package com.mraof.minestuck.util;

import com.mraof.minestuck.client.model.armor.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;

@SideOnly(Side.CLIENT)
public class MSGTArmorModels
{
	public static final HashMap<EnumClass, ModelGTAbstract> models = new HashMap<EnumClass, ModelGTAbstract>()
	{{
		put(EnumClass.KNIGHT, new ModelGTKnight());
		put(EnumClass.HEIR, new ModelGTHeir());
		put(EnumClass.WITCH, new ModelGTWitch());
		put(EnumClass.SEER, new ModelGTSeer());
		put(EnumClass.PAGE, new ModelGTPage());
		put(EnumClass.MAGE, new ModelGTMage());
		put(EnumClass.BARD, new ModelGTBard());
		put(EnumClass.THIEF, new ModelGTThief());
		put(EnumClass.PRINCE, new ModelGTPrince());
		put(EnumClass.MAID, new ModelGTMaid());
		put(EnumClass.ROGUE, new ModelGTRogue());
		put(EnumClass.SYLPH, new ModelGTSylph());

		put(EnumClass.LORD, new ModelGTLord());
		put(EnumClass.MUSE, new ModelGTMuse());
	}};
}
