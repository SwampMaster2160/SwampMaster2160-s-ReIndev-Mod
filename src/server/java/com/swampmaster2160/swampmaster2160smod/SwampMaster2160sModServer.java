package com.swampmaster2160.swampmaster2160smod;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.registry.BlockBuilder;
import com.fox2code.foxloader.registry.RegisteredBlock;
import com.swampmaster2160.swampmaster2160smod.block.BlockDeathServer;

public class SwampMaster2160sModServer extends SwampMaster2160sMod implements ServerMod {
    public static RegisteredBlock deathBlock;

    @Override
    public void onInit() {
        // Server specific code

        // Register blocks (clientside)
        deathBlock = registerNewBlock("death_block", new BlockBuilder()
            .setGameBlockSource(BlockDeathServer.class)
            .setBlockName("death_block")
        );
    }
}
