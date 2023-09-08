package com.swampmaster2160.swampmaster2160smod;

import com.fox2code.foxloader.loader.ClientMod;
import com.fox2code.foxloader.registry.BlockBuilder;

public class SwampMaster2160sModClient extends SwampMaster2160sMod implements ClientMod {
    @Override
    public void onInit() {
        // Client specific code

        // Register blocks (serverside)
        deathBlock = registerNewBlock("death_block", new BlockBuilder().setGameBlockSource(BlockDeathClient.class));
    }
}
