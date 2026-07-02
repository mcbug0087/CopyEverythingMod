package com.example.copyeverything.blocks;

import com.example.copyeverything.CopyEverything;
import com.example.copyeverything.tileentity.CopyChestTileEntity;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CopyChestBlock extends BlockContainer {
    public CopyChestBlock() {
        super(Material.wood);
        this.setBlockName("copyChest");
        this.setBlockTextureName(CopyEverything.MODID + ":copy_chest");
        this.setCreativeTab(net.minecraft.creativetab.CreativeTabs.tabDecorations);
        this.setHardness(2.5F);
        this.setResistance(5.0F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new CopyChestTileEntity();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            FMLNetworkHandler.openGui(player, CopyEverything.instance, 0, world, x, y, z);
        }
        return true;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
}