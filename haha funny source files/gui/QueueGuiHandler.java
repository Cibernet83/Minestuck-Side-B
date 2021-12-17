package com.mraof.minestuck.client.gui.captchalogue;

public class QueueGuiHandler extends StackGuiHandler {

	public QueueGuiHandler()
	{
		super();
	}
	
	@Override
	public void updateContent()
	{
		super.updateContent();
		if(!cards.isEmpty())
		{
			cards.get(0).index = -1;
			cards.get(cards.size() - 1).index = 0;
		}
	}
	
}
