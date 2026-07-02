package com.example.copyeverything.gui;

import com.example.copyeverything.tileentity.CopyChestTileEntity;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class CopyChestGuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        CopyChestTileEntity tileEntity = (CopyChestTileEntity) world.getTileEntity(x, y, z);
        if (tileEntity != null) {
            return new CopyChestContainer(player.inventory, tileEntity);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        CopyChestTileEntity tileEntity = (CopyChestTileEntity) world.getTileEntity(x, y, z);
        if (tileEntity != null) {
            return new CopyChestGui(player.inventory, tileEntity);
        }
        return null;
    }
}