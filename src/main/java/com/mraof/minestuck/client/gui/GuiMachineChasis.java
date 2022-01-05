package com.mraof.minestuck.client.gui;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.inventory.ContainerMachineChasis;
import com.mraof.minestuck.network.MinestuckNetwork;
import com.mraof.minestuck.network.MinestuckMessage;
import com.mraof.minestuck.tileentity.TileEntityMachineChassis;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiMachineChasis extends GuiContainer
{
    private static final ResourceLocation TEXTURES = new ResourceLocation(Minestuck.MODID, "textures/gui/machine_chassis.png");
    private final InventoryPlayer player;
    private final TileEntityMachineChassis tileEntity;
    private GuiButton goButton;

    public GuiMachineChasis(InventoryPlayer player, TileEntityMachineChassis tileEntity)
    {
        super(new ContainerMachineChasis(player, tileEntity));
        this.player = player;
        this.tileEntity = tileEntity;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        goButton = new GuiButton(0,  (this.width-this.xSize)/2+ 20, (this.height-this.ySize)/2+ 60, fontRenderer.getStringWidth("ASSEMBLE")+5, 14, "ASSEMBLE");
        buttonList.add(goButton);

    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);
        if(button == goButton)
        {
            MinestuckNetwork.sendToServer(MinestuckMessage.makePacket(MinestuckMessage.Type.MACHINE_CHASSIS, new Object[] {tileEntity}));
        }

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(TEXTURES);
        drawTexturedModalRect(guiLeft, guiTop, 0 ,0, xSize, ySize);

    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        String displayName = tileEntity.getDisplayName().getUnformattedComponentText();
        fontRenderer.drawString(displayName, (xSize/2 - fontRenderer.getStringWidth(displayName)/2), 4, 4210752);
        fontRenderer.drawString(player.getDisplayName().getUnformattedText(), 122, ySize - 94, 4210752);
    }
}
