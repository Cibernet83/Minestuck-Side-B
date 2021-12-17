package com.mraof.minestuck.client.gui.captchalogue;

import com.mraof.minestuck.client.gui.GuiCipherDecode;
import com.google.common.collect.Lists;
import com.mraof.minestuck.inventory.captchalouge.Modus;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;
import java.util.List;

public class CipherGuiHandler extends BaseModusGuiHandler
{
	public CipherGuiHandler(Modus modus)
	{
		super(modus, 45);
	}
	
	@Override
	public void updateContent()
	{
		NonNullList<ItemStack> stacks = this.modus.getItems();
		this.cards.clear();
		this.maxWidth = Math.max(this.mapWidth, 10 + stacks.size() * 21 + (stacks.size() - 1) * 5);
		this.maxHeight = this.mapHeight;
		updateContentSuper();
		int start = Math.max(5, (this.mapWidth - (stacks.size() * 21 + (stacks.size() - 1) * 5)) / 2);
		
		for(int i = 0; i < stacks.size(); ++i)
			this.cards.add(new CipherCard(stacks.get(i), this, i, start + i * 26, (this.mapHeight - 26) / 2));
	}
	
	@Override
	public int getTextureIndex(GuiCard card)
	{
		return textureIndex + (card.item.isEmpty() ? 0 : 1);
	}
	
	@Override
	public List<String> getItemToolTip(ItemStack stack)
	{
		List<String> list = Lists.<String>newArrayList();
		
		list.add(encodeName(stack));
		
		return list;
	}
	
	public Modus getModus()
	{
		return modus;
	}
	public ArrayList<GuiCard> getCards()
	{
		return cards;
	}
	
	public static String encodeName(ItemStack stack)
	{
		String name = stack.getDisplayName();
		String encodedName = "";
		
		int shift = (Math.abs(name.charAt(0)) % 32) -16;
		if(shift == 0)
			shift = 1;
		
		for(int i = 0; i < name.length(); i++)
		{
			String cStr = ""+name.charAt(i);
			boolean isUpperCase = cStr.equals(cStr.toUpperCase());
			char c = name.toLowerCase().charAt(i);
			
			if(c >= 'a' && c <= 'z')
			{
				if(((c-'a'+shift) % 26) < 0)
					c += 26;
				c = (char) (((c-'a'+shift) % 26) + 'a');
			}
			else if(c >= '0' && c <= '9')
			{
				if(((c-'0'+shift) % 10) < 0)
					c += 10;
				c = (char) (((c-'0'+shift) % 10) + '0');
			}
			encodedName += isUpperCase ? ("" + c).toUpperCase() : c;
		}
		return encodedName;
	}
	
	public class CipherCard extends GuiCard
	{
		public CipherGuiHandler gui;
		
		public CipherCard(ItemStack stack, CipherGuiHandler gui, int index, int x, int y)
		{
			super(stack, gui, index, x, y);
		}
		
		@Override
		protected void drawItem()
		{
		}
		
		@Override
		protected void drawTooltip(int mouseX, int mouseY)
		{
			super.drawTooltip(mouseX, mouseY);
		}
		
		protected void renderToolTip(ItemStack stack, int x, int y)
		{
			FontRenderer font = stack.getItem().getFontRenderer(stack);
			net.minecraftforge.fml.client.config.GuiUtils.preItemToolTip(stack);
			gui.drawHoveringText(gui.getItemToolTip(stack), x, y, (font == null ? fontRenderer : font));
			net.minecraftforge.fml.client.config.GuiUtils.postItemToolTip();
		}
		
		@Override
		public void onClick(int mouseButton)
		{
			if(mouseButton == 0)
			{
				mc.currentScreen = new GuiCipherDecode(CipherGuiHandler.this, index);
				mc.currentScreen.setWorldAndResolution(mc, width, height);
			}
			else super.onClick(mouseButton);
		}
	}
}
