package com.cibernet.fetchmodiplus.registries;

import com.cibernet.fetchmodiplus.blocks.HardStoneBlock;
import com.cibernet.fetchmodiplus.blocks.OperandiBlock;
import com.cibernet.fetchmodiplus.blocks.OperandiGlassBlock;
import com.cibernet.fetchmodiplus.blocks.OperandiLogBlock;
import com.mraof.minestuck.block.MinestuckBlocks;
import com.mraof.minestuck.item.TabMinestuck;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class FMPBlocks
{
	public static final Block hardStone = new HardStoneBlock().setRegistryName("hard_stone").setUnlocalizedName("hard_stone").setCreativeTab(TabMinestuck.instance);
	public static final Block operandiBlock = new OperandiBlock("operandi_block", 1.0f, 0, Material.GOURD, "");
	public static final Block operandiGlass = new OperandiGlassBlock("operandi_glass", 0.5f, 0, Material.GLASS, "");
	public static final Block operandiStone = new OperandiBlock("operandi_stone", 3.0f, 6.5f, Material.IRON, "pickaxe");
	public static final Block operandiLog = new OperandiLogBlock("operandi_log", 2.0f, 0, OperandiBlock.LOG, "axe");


	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		IForgeRegistry<Block> registry = event.getRegistry();
		registry.register(hardStone);
		registry.register(operandiBlock);
		registry.register(operandiStone);
		registry.register(operandiGlass);
		registry.register(operandiLog);
		
	}
}
