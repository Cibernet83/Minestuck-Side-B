package com.mraof.minestuck.advancements;

import com.mraof.minestuck.Minestuck;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.util.ResourceLocation;

public class MinestuckCriteriaTriggers
{
	public static final EventTrigger SBURB_CONNECTION = new EventTrigger(new ResourceLocation(Minestuck.MODID, "sburb_connection"));
	public static final EventTrigger CRUXITE_ARTIFACT = new EventTrigger(new ResourceLocation(Minestuck.MODID, "cruxite_artifact"));
	public static final PunchDesignixTrigger PUNCH_DESIGNIX = new PunchDesignixTrigger();
	public static final CaptchalogueTrigger CAPTCHALOGUE = new CaptchalogueTrigger();
	public static final ChangeModusTrigger CHANGE_MODUS = new ChangeModusTrigger();
	public static final TreeModusRootTrigger TREE_MODUS_ROOT = new TreeModusRootTrigger();
	public static final ConsortItemTrigger CONSORT_ITEM = new ConsortItemTrigger();
	public static final ConsortTalkTrigger CONSORT_TALK = new ConsortTalkTrigger();
	
	public static void register()
	{
		CriteriaTriggers.register(SBURB_CONNECTION);
		CriteriaTriggers.register(CRUXITE_ARTIFACT);
		CriteriaTriggers.register(PUNCH_DESIGNIX);
		CriteriaTriggers.register(CAPTCHALOGUE);
		CriteriaTriggers.register(CHANGE_MODUS);
		CriteriaTriggers.register(TREE_MODUS_ROOT);
		CriteriaTriggers.register(CONSORT_ITEM);
		CriteriaTriggers.register(CONSORT_TALK);
	}
}