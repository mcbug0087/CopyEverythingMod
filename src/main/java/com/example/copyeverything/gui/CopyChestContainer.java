package com.example.copyeverything.gui;

import com.example.copyeverything.tileentity.CopyChestTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class CopyChestContainer extends Container {
    private static final int SLOTS_PER_PAGE = 54;
    private static final int PAGE_COUNT = 1024 / SLOTS_PER_PAGE + 1;
    
    private CopyChestTileEntity tileEntity;
    private int currentPage = 0;
    private InventoryPlayer playerInventory;

    public CopyChestContainer(InventoryPlayer playerInventory, CopyChestTileEntity tileEntity) {
        this.tileEntity = tileEntity;
        this.playerInventory = playerInventory;
        
        for (int i = 0; i < SLOTS_PER_PAGE && i + currentPage * SLOTS_PER_PAGE < tileEntity.getSizeInventory(); i++) {
            int x = 8 + (i % 9) * 18;
            int y = 18 + (i / 9) * 18;
            addSlotToContainer(new Slot(tileEntity, i + currentPage * SLOTS_PER_PAGE, x, y));
        }
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 103 + i * 18));
            }
        }
        
        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 161));
        }
    }

    public void setPage(int page) {
        if (page >= 0 && page < PAGE_COUNT) {
            this.currentPage = page;
            this.inventorySlots.clear();
            this.inventoryItemStacks.clear();
            
            for (int i = 0; i < SLOTS_PER_PAGE && i + currentPage * SLOTS_PER_PAGE < tileEntity.getSizeInventory(); i++) {
                int x = 8 + (i % 9) * 18;
                int y = 18 + (i / 9) * 18;
                addSlotToContainer(new Slot(tileEntity, i + currentPage * SLOTS_PER_PAGE, x, y));
            }
            
            if (playerInventory != null) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 9; j++) {
                        addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 103 + i * 18));
                    }
                }
                
                for (int i = 0; i < 9; i++) {
                    addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 161));
                }
            }
        }
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageCount() {
        return PAGE_COUNT;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return tileEntity.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
        ItemStack itemStack = null;
        Slot slot = (Slot) inventorySlots.get(slotIndex);
        
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStackInSlot = slot.getStack();
            itemStack = itemStackInSlot.copy();
            
            if (slotIndex < SLOTS_PER_PAGE) {
                if (!mergeItemStack(itemStackInSlot, SLOTS_PER_PAGE, inventorySlots.size(), true)) {
                    return null;
                }
            } else {
                if (!mergeItemStack(itemStackInSlot, 0, SLOTS_PER_PAGE, false)) {
                    return null;
                }
            }
            
            if (itemStackInSlot.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
            
            if (itemStackInSlot.stackSize == itemStack.stackSize) {
                return null;
            }
            
            slot.onPickupFromSlot(player, itemStackInSlot);
        }
        
        return itemStack;
    }
}