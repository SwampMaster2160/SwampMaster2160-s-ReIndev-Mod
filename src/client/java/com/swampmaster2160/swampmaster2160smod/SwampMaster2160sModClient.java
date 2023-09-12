package com.swampmaster2160.swampmaster2160smod;

import java.util.Vector;

import com.fox2code.foxloader.loader.ClientMod;
import com.fox2code.foxloader.registry.BlockBuilder;
import com.fox2code.foxloader.registry.ItemBuilder;
import com.fox2code.foxloader.registry.RegisteredBlock;
import com.fox2code.foxloader.registry.RegisteredItem;
import com.swampmaster2160.swampmaster2160smod.block.BlockDeathClient;
import com.swampmaster2160.swampmaster2160smod.block.BlockTriStateFalseClient;
import com.swampmaster2160.swampmaster2160smod.block.BlockTriStateSignalClient;
import com.swampmaster2160.swampmaster2160smod.block.BlockTriStateTrueClient;
import com.swampmaster2160.swampmaster2160smod.item.ItemTestWandClient;

public class SwampMaster2160sModClient extends SwampMaster2160sMod implements ClientMod {
	// All the blocks as static vars
	public static RegisteredBlock deathBlock;
	public static RegisteredBlock triStateSignal;
	public static RegisteredBlock triStateTrue;
	public static RegisteredBlock triStateFalse;
	// Add the items as static vars
	public static RegisteredItem testWand;
	// A list of tri-state blocks
	public static Vector<Integer> triStateBlocksList;

	// Called when the game is in the initialization phase, when we can register blocks/items
	@Override
	public void onInit() {
		// Client specific code

		// Register blocks (Clientside)
		triStateBlocksList = new Vector<Integer>();

		deathBlock = registerNewBlock("death_block", new BlockBuilder()
			.setGameBlockSource(BlockDeathClient.class)
			.setBlockName("death_block")
		);
		triStateSignal = registerNewBlock("tri_state_signal", new BlockBuilder()
			.setGameBlockSource(BlockTriStateSignalClient.class)
			.setBlockName("tri_state_signal")
		);
		triStateBlocksList.add(triStateSignal.getRegisteredBlockId());
		triStateTrue = registerNewBlock("tri_state_true", new BlockBuilder()
			.setGameBlockSource(BlockTriStateTrueClient.class)
			.setBlockName("tri_state_true")
		);
		triStateBlocksList.add(triStateTrue.getRegisteredBlockId());
		triStateFalse = registerNewBlock("tri_state_false", new BlockBuilder()
			.setGameBlockSource(BlockTriStateFalseClient.class)
			.setBlockName("tri_state_false")
		);
		triStateBlocksList.add(triStateFalse.getRegisteredBlockId());
		// Register items (Clientside)
		testWand = registerNewItem("test_wand", new ItemBuilder()
			.setGameItemSource(ItemTestWandClient.class)
			.setItemName("test_wand")
		);
	}
}
