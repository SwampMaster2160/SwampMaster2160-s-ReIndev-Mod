package com.swampmaster2160.swampmaster2160smod;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.registry.BlockBuilder;
import com.fox2code.foxloader.registry.ItemBuilder;
import com.fox2code.foxloader.registry.RegisteredBlock;
import com.fox2code.foxloader.registry.RegisteredItem;
import com.swampmaster2160.swampmaster2160smod.block.BlockDeathServer;
import com.swampmaster2160.swampmaster2160smod.item.ItemTestWandServer;

public class SwampMaster2160sModServer extends SwampMaster2160sMod implements ServerMod {
	// All the blocks as static vars
	public static RegisteredBlock deathBlock;
	// Add the items as static vars
	public static RegisteredItem testWand;

	@Override
	public void onInit() {
		// Server specific code

		// Register blocks (clientside)
		deathBlock = registerNewBlock("death_block", new BlockBuilder()
			.setGameBlockSource(BlockDeathServer.class)
			.setBlockName("death_block")
		);
		// Register items (serverside)
		testWand = registerNewItem("test_wand", new ItemBuilder()
			.setGameItemSource(ItemTestWandServer.class)
			.setItemName("test_wand")
		);
	}
}
