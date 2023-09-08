package com.swampmaster2160.swampmaster2160smod;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.registry.BlockBuilder;

public class SwampMaster2160sModServer extends SwampMaster2160sMod implements ServerMod {
    @Override
    public void onInit() {
        // Server specific code

        // Register blocks (clientside)
        deathBlock = registerNewBlock("death_block", new BlockBuilder().setGameBlockSource(BlockDeathServer.class));
    }
}
