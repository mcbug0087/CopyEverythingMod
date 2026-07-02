package com.example.copyeverything;

import com.example.copyeverything.blocks.CopyChestBlock;
import com.example.copyeverything.blocks.CopySaplingBlock;
import com.example.copyeverything.tileentity.CopyChestTileEntity;
import com.example.copyeverything.gui.CopyChestGuiHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = CopyEverything.MODID, version = CopyEverything.VERSION)
public class CopyEverything {
    public static final String MODID = "copyeverything";
    public static final String VERSION = "1.0";

    public static Block copySapling;
    public static Block copyChest;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        copySapling = new CopySaplingBlock();
        GameRegistry.registerBlock(copySapling, "copySapling");

        copyChest = new CopyChestBlock();
        GameRegistry.registerBlock(copyChest, "copyChest");

        GameRegistry.registerTileEntity(CopyChestTileEntity.class, "copyChestTileEntity");
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        GameRegistry.addRecipe(new ItemStack(copySapling),
            "SS",
            "SS",
            'S', net.minecraft.init.Items.sapling
        );

        GameRegistry.registerGuiHandler(this, new CopyChestGuiHandler());
        
        MinecraftForge.EVENT_BUS.register(new CopySaplingBlock.SaplingGrowHandler());
    }
}