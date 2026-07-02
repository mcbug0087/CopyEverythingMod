package com.example.copyeverything.gui;

import com.example.copyeverything.tileentity.CopyChestTileEntity;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class CopyChestGui extends GuiContainer {
    private CopyChestTileEntity tileEntity;
    private GuiButton prevButton;
    private GuiButton nextButton;
    private CopyChestContainer container;

    public CopyChestGui(InventoryPlayer playerInventory, CopyChestTileEntity tileEntity) {
        super(new CopyChestContainer(playerInventory, tileEntity));
        this.tileEntity = tileEntity;
        this.container = (CopyChestContainer) inventorySlots;
        this.xSize = 176;
        this.ySize = 190;
    }

    @Override
    public void initGui() {
        super.initGui();
        int centerX = (width - xSize) / 2;
        int centerY = (height - ySize) / 2;
        
        prevButton = new GuiButton(0, centerX - 20, centerY + 80, 15, 20, "<");
        nextButton = new GuiButton(1, centerX + xSize + 5, centerY + 80, 15, 20, ">");
        
        buttonList.add(prevButton);
        buttonList.add(nextButton);
        
        updateButtons();
    }

    private void updateButtons() {
        prevButton.enabled = container.getCurrentPage() > 0;
        nextButton.enabled = container.getCurrentPage() < container.getPageCount() - 1;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button == prevButton) {
            container.setPage(container.getCurrentPage() - 1);
        } else if (button == nextButton) {
            container.setPage(container.getCurrentPage() + 1);
        }
        updateButtons();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRendererObj.drawString(StatCollector.translateToLocal("container.copyChest"), 8, 6, 4210752);
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
        fontRendererObj.drawString("Page " + (container.getCurrentPage() + 1) + "/" + container.getPageCount(), xSize / 2 - fontRendererObj.getStringWidth("Page 1/1") / 2, 85, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(net.minecraft.util.ResourceLocation("textures/gui/container/generic_54.png"));
        int k = (width - xSize) / 2;
        int l = (height - ySize) / 2;
        drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(net.minecraft.util.ResourceLocation("textures/gui/widgets.png"));
        
        prevButton.drawButton(mc, mouseX, mouseY);
        nextButton.drawButton(mc, mouseX, mouseY);
    }
}