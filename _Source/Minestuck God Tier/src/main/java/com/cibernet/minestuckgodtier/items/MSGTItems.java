package com.cibernet.minestuckgodtier.items;

import com.cibernet.minestuckgodtier.MinestuckGodTier;
import com.cibernet.minestuckgodtier.blocks.MSGTBlocks;
import com.mraof.minestuck.alchemy.GristSet;
import com.mraof.minestuck.alchemy.GristType;
import com.mraof.minestuck.editmode.DeployList;
import com.mraof.minestuck.util.EnumAspect;
import com.mraof.minestuck.util.EnumClass;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class MSGTItems
{
	public static final ArrayList<Block> blocks = new ArrayList<>();

	public static ItemArmor.ArmorMaterial gtMaterial = EnumHelper.addArmorMaterial("GOD_TIER", MinestuckGodTier.MODID+":blank", -1, new int[] {0,0,0,0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);
	
	public static final CreativeTabs tab = new CreativeTabs("godTierArmor")
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return ItemKit.generateKit(EnumClass.HEIR, null);
		}
	};
	
	public static Item armorKit = new ItemKit();
	public static Item gtHood = new ItemGTArmor(gtMaterial, 0, EntityEquipmentSlot.HEAD).setUnlocalizedName("gtHood");
	public static Item gtShirt = new ItemGTArmor(gtMaterial, 0, EntityEquipmentSlot.CHEST).setUnlocalizedName("gtShirt");
	public static Item gtPants = new ItemGTArmor(gtMaterial, 0, EntityEquipmentSlot.LEGS).setUnlocalizedName("gtPants");
	public static Item gtShoes = new ItemGTArmor(gtMaterial, 0, EntityEquipmentSlot.FEET).setUnlocalizedName("gtShoes");
	public static final Map<EnumAspect, ItemHeroStoneShard> heroStoneShards = createHeroStoneShardMap();

	public static Item skillReseter = new ItemGodTierReseter("skillResetCharm");
	public static Item tomeOfTheAncients = new ItemDenizenTome();
	public static Item denizenEye = new ItemLocatorEye("denizenEye");
	public static Item sashKit = new ItemSashKit("sashKit");
	public static Item manipulatedMatter = new ItemManipulatedMatter("manipulatedMatter");

	@SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
		IForgeRegistry<Item> registry = event.getRegistry();

		MSGTItems.blocks.forEach(block -> registry.register(new ItemBlock(block).setRegistryName(block.getRegistryName().getResourcePath())));

		registry.register(new ItemSlab(MSGTBlocks.prospitStoneSlab, MSGTBlocks.prospitStoneSlab, MSGTBlocks.prospitStoneDoubleSlab).setCreativeTab(tab).setRegistryName(MSGTBlocks.prospitStoneSlab.getRegistryName().getResourcePath()));
		registry.register(new ItemSlab(MSGTBlocks.prospitSmoothstoneSlab, MSGTBlocks.prospitSmoothstoneSlab, MSGTBlocks.prospitSmoothstoneDoubleSlab).setCreativeTab(tab).setRegistryName(MSGTBlocks.prospitSmoothstoneSlab.getRegistryName().getResourcePath()));
		registry.register(new ItemSlab(MSGTBlocks.prospitBricksSlab, MSGTBlocks.prospitBricksSlab, MSGTBlocks.prospitBricksDoubleSlab).setCreativeTab(tab).setRegistryName(MSGTBlocks.prospitBricksSlab.getRegistryName().getResourcePath()));
		
		registry.register(new ItemSlab(MSGTBlocks.derseStoneSlab, MSGTBlocks.derseStoneSlab, MSGTBlocks.derseStoneDoubleSlab).setCreativeTab(tab).setRegistryName(MSGTBlocks.derseStoneSlab.getRegistryName().getResourcePath()));
		registry.register(new ItemSlab(MSGTBlocks.derseSmoothstoneSlab, MSGTBlocks.derseSmoothstoneSlab, MSGTBlocks.derseSmoothstoneDoubleSlab).setCreativeTab(tab).setRegistryName(MSGTBlocks.derseSmoothstoneSlab.getRegistryName().getResourcePath()));
		registry.register(new ItemSlab(MSGTBlocks.derseBricksSlab, MSGTBlocks.derseBricksSlab, MSGTBlocks.derseBricksDoubleSlab).setCreativeTab(tab).setRegistryName(MSGTBlocks.derseBricksSlab.getRegistryName().getResourcePath()));
		
		/*
		MSGTBlocks.heroStones.forEach((key, block) -> registry.registerBadgeEvents(new ItemBlock(block).setRegistryName(block.getRegistryName().getResourcePath())));
		registry.registerBadgeEvents(new ItemBlock(MSGTBlocks.wildcardHeroStone).setRegistryName(MSGTBlocks.wildcardHeroStone.getRegistryName().getResourcePath()));
		MSGTBlocks.chiseledHeroStones.forEach((key, block) -> registry.registerBadgeEvents(new ItemBlock(block).setRegistryName(block.getRegistryName().getResourcePath())));
		registry.registerBadgeEvents(new ItemBlock(MSGTBlocks.wildcardChiseledHeroStone).setRegistryName(MSGTBlocks.wildcardChiseledHeroStone.getRegistryName().getResourcePath()));
		MSGTBlocks.heroStoneWalls.forEach((key, block) -> registry.registerBadgeEvents(new ItemBlock(block).setRegistryName(block.getRegistryName().getResourcePath())));
		registry.registerBadgeEvents(new ItemBlock(MSGTBlocks.wildcardHeroStoneWall).setRegistryName(MSGTBlocks.wildcardHeroStoneWall.getRegistryName().getResourcePath()));
		registry.registerBadgeEvents(new ItemBlock(MSGTBlocks.glowingHeroStone).setRegistryName(MSGTBlocks.glowingHeroStone.getRegistryName().getResourcePath()));

		registry.registerBadgeEvents(new ItemBlock(MSGTBlocks.heroLockBeacon).setRegistryName(MSGTBlocks.heroLockBeacon.getRegistryName().getResourcePath()));

		*/

		registry.register(armorKit.setRegistryName("armor_kit"));
		registry.register(gtHood.setRegistryName("hood"));
		registry.register(gtShirt.setRegistryName("shirt"));
		registry.register(gtPants.setRegistryName("pants"));
		registry.register(gtShoes.setRegistryName("shoes"));
		heroStoneShards.forEach((key, item) -> registry.register(item));

		registry.register(skillReseter.setRegistryName("skill_reset_charm"));
		registry.register(tomeOfTheAncients.setRegistryName("tome_of_the_ancients"));
		registry.register(denizenEye.setRegistryName("denizen_eye"));
		registry.register(sashKit.setRegistryName("sash_kit"));
		registry.register(manipulatedMatter.setRegistryName("manipulated_matter"));
    }
    
    public static void registerDeployList()
	{
		DeployList.registerItem("gt_kit", new GristSet(GristType.Zillium, 50), 0, sburbConnection -> ItemKit.isAvailable(sburbConnection), sburbConnection -> ItemKit.generateKit(sburbConnection));
	}


	private static Map<EnumAspect, ItemHeroStoneShard> createHeroStoneShardMap()
	{
		Map<EnumAspect, ItemHeroStoneShard> result = new TreeMap<>();

		for(EnumAspect aspect : EnumAspect.values())
			result.put(aspect, new ItemHeroStoneShard(aspect));
		return result;
	}
}
