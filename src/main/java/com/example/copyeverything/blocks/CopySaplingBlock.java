package com.example.copyeverything.blocks;

import com.example.copyeverything.CopyEverything;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.BonemealEvent;

import java.util.Random;

public class CopySaplingBlock extends BlockSapling {
    public CopySaplingBlock() {
        super();
        this.setBlockName("copySapling");
        this.setBlockTextureName(CopyEverything.MODID + ":copy_sapling");
        this.setCreativeTab(net.minecraft.creativetab.CreativeTabs.tabDecorations);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        if (!world.isRemote) {
            super.updateTick(world, x, y, z, random);
            if (world.getBlockLightValue(x, y + 1, z) >= 9 && random.nextInt(7) == 0) {
                this.growTree(world, x, y, z, random);
            }
        }
    }

    public void growTree(World world, int x, int y, int z, Random random) {
        Block below = world.getBlock(x, y - 1, z);
        
        if (below == Blocks.air || below.isAir(world, x, y - 1, z)) {
            below = Blocks.leaves;
        }

        world.setBlockToAir(x, y, z);
        
        for (int trunkY = y; trunkY < y + 4; trunkY++) {
            world.setBlock(x, trunkY, z, Blocks.log, 0, 3);
        }

        int crownBaseY = y + 3;
        
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                for (int dy = 0; dy <= 2; dy++) {
                    int posX = x + dx;
                    int posY = crownBaseY + dy;
                    int posZ = z + dz;
                    
                    boolean isInner = Math.abs(dx) + Math.abs(dz) + Math.abs(dy) <= 1;
                    
                    if (!isInner && world.isAirBlock(posX, posY, posZ)) {
                        int meta = world.getBlockMetadata(x, y - 1, z);
                        world.setBlock(posX, posY, posZ, below, meta, 3);
                    }
                }
            }
        }
        
        world.setBlock(x, crownBaseY + 1, z, Blocks.log, 0, 3);
    }

    public static class SaplingGrowHandler {
        @SubscribeEvent
        public void onBonemeal(BonemealEvent event) {
            if (event.block instanceof CopySaplingBlock) {
                CopySaplingBlock sapling = (CopySaplingBlock) event.block;
                if (!event.world.isRemote) {
                    sapling.growTree(event.world, event.x, event.y, event.z, event.world.rand);
                    event.setCanceled(true);
                }
            }
        }
    }
}