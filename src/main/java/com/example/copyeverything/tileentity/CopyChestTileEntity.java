package com.example.copyeverything.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class CopyChestTileEntity extends TileEntity implements IInventory {
    private static final int SLOT_COUNT = 1024;
    private static final int MAX_TOTAL_PER_ITEM = 768;
    
    private ItemStack[] inventory = new ItemStack[SLOT_COUNT];
    private int tickCounter = 0;

    public CopyChestTileEntity() {
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            tickCounter++;
            if (tickCounter >= 5) {
                tickCounter = 0;
                copyItems();
            }
        }
    }

    private void copyItems() {
        for (int i = 0; i < SLOT_COUNT; i++) {
            ItemStack stack = inventory[i];
            if (stack != null) {
                int totalCount = getTotalItemCount(stack);
                if (totalCount >= MAX_TOTAL_PER_ITEM) {
                    continue;
                }
                
                int stackSize = stack.stackSize;
                int maxStackSize = stack.getMaxStackSize();
                
                if (stackSize < maxStackSize) {
                    int newSize = Math.min(stackSize * 2, maxStackSize);
                    int actualNew = Math.min(newSize, maxStackSize);
                    stack.stackSize = actualNew;
                } else {
                    int targetSlot = findEmptySlot();
                    if (targetSlot != -1) {
                        ItemStack newStack = stack.copy();
                        newStack.stackSize = Math.min(stackSize, MAX_TOTAL_PER_ITEM - totalCount);
                        if (newStack.stackSize > 0) {
                            inventory[targetSlot] = newStack;
                        }
                    }
                }
                
                markDirty();
            }
        }
    }

    private int getTotalItemCount(ItemStack targetStack) {
        int count = 0;
        for (int i = 0; i < SLOT_COUNT; i++) {
            ItemStack stack = inventory[i];
            if (stack != null && areItemStacksEqual(stack, targetStack)) {
                count += stack.stackSize;
            }
        }
        return count;
    }
    
    private boolean areItemStacksEqual(ItemStack stack1, ItemStack stack2) {
        if (stack1 == stack2) return true;
        if (stack1 == null || stack2 == null) return false;
        if (stack1.getItem() != stack2.getItem()) return false;
        if (stack1.getItemDamage() != stack2.getItemDamage()) return false;
        if (stack1.stackTagCompound == stack2.stackTagCompound) return true;
        if (stack1.stackTagCompound == null || stack2.stackTagCompound == null) return false;
        return stack1.stackTagCompound.equals(stack2.stackTagCompound);
    }

    private int findEmptySlot() {
        for (int i = 0; i < SLOT_COUNT; i++) {
            if (inventory[i] == null) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        
        NBTTagList items = tag.getTagList("Items", 10);
        inventory = new ItemStack[SLOT_COUNT];
        
        for (int i = 0; i < items.tagCount(); i++) {
            NBTTagCompound itemTag = items.getCompoundTagAt(i);
            int slot = itemTag.getByte("Slot") & 255;
            
            if (slot >= 0 && slot < SLOT_COUNT) {
                inventory[slot] = ItemStack.loadItemStackFromNBT(itemTag);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        
        NBTTagList items = new NBTTagList();
        
        for (int i = 0; i < SLOT_COUNT; i++) {
            if (inventory[i] != null) {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setByte("Slot", (byte) i);
                inventory[i].writeToNBT(itemTag);
                items.appendTag(itemTag);
            }
        }
        
        tag.setTag("Items", items);
    }

    @Override
    public int getSizeInventory() {
        return SLOT_COUNT;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        ItemStack stack = inventory[slot];
        if (stack != null) {
            if (stack.stackSize <= amount) {
                inventory[slot] = null;
            } else {
                stack = stack.splitStack(amount);
                if (stack.stackSize == 0) {
                    inventory[slot] = null;
                }
            }
            markDirty();
        }
        return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        ItemStack stack = inventory[slot];
        if (stack != null) {
            inventory[slot] = null;
        }
        return stack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory[slot] = stack;
        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
            stack.stackSize = getInventoryStackLimit();
        }
        markDirty();
    }

    @Override
    public String getInventoryName() {
        return "container.copyChest";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this &&
               player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return true;
    }
}