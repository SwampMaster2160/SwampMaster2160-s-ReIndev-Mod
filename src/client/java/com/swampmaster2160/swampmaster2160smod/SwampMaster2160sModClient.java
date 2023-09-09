package com.swampmaster2160.swampmaster2160smod;

import com.fox2code.foxloader.loader.ClientMod;
import com.fox2code.foxloader.registry.BlockBuilder;
import com.fox2code.foxloader.registry.ItemBuilder;
import com.fox2code.foxloader.registry.RegisteredBlock;
import com.fox2code.foxloader.registry.RegisteredItem;
import com.swampmaster2160.swampmaster2160smod.block.BlockDeathClient;
import com.swampmaster2160.swampmaster2160smod.item.ItemTestWandClient;

public class SwampMaster2160sModClient extends SwampMaster2160sMod implements ClientMod {
    public static RegisteredBlock deathBlock;

    public static RegisteredItem testWand;

    @Override
    public void onInit() {
        // Client specific code

        // Register blocks (serverside)
        deathBlock = registerNewBlock("death_block", new BlockBuilder()
            .setGameBlockSource(BlockDeathClient.class)
            .setBlockName("death_block")
        );
        // Register items (serverside)
        testWand = registerNewItem("test_wand", new ItemBuilder()
            .setGameItemSource(ItemTestWandClient.class)
            .setItemName("test_wand")
        );
    }
}
