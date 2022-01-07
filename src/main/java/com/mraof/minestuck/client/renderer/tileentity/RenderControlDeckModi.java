package com.mraof.minestuck.client.renderer.tileentity;

import com.mraof.minestuck.block.BlockModusControlDeck;
import com.mraof.minestuck.tileentity.TileEntityModusControlDeck;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;

public class RenderControlDeckModi extends TileEntitySpecialRenderer<TileEntityModusControlDeck>
{
	@Override
	public void render(TileEntityModusControlDeck te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);

		NonNullList<ItemStack> inv = te.getInventory();

		EnumFacing facing = te.getWorld().getBlockState(te.getPos()).getValue(BlockModusControlDeck.FACING);
		//GlStateManager.translate(5/16d + 6/16d*(i%2),  5.5/16d, 4.5/16d + 3/16d*(i/2));

		for (int i = 0; i < inv.size(); i++)
		{
			GlStateManager.pushMatrix();
			ItemStack stack = inv.get(i);

			GlStateManager.translate(x, y, z);
			GlStateManager.rotate(180 - facing.getHorizontalAngle(), 0.0F, 1.0F, 0.0F);
			GlStateManager.translate(11 / 16d - 6 / 16d * (i % 2) + -((3 - facing.getHorizontalIndex()) / 2), 5.5 / 16d, 11.5 / 16d - 3 / 16d * (i / 2) + (facing == EnumFacing.SOUTH || facing == EnumFacing.EAST ? -1 : 0));

			GlStateManager.scale(0.5, 0.5, 0.5);
			Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
			GlStateManager.popMatrix();
		}

	}
}
