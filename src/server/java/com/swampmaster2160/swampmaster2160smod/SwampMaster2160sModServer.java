package com.swampmaster2160.swampmaster2160smod;

import java.util.Vector;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.registry.BlockBuilder;
import com.fox2code.foxloader.registry.ItemBuilder;
import com.fox2code.foxloader.registry.RegisteredBlock;
import com.fox2code.foxloader.registry.RegisteredItem;
import com.swampmaster2160.swampmaster2160smod.block.BlockDeathServer;
import com.swampmaster2160.swampmaster2160smod.block.BlockTriStateBufferServer;
import com.swampmaster2160.swampmaster2160smod.block.BlockTriStateFalseServer;
import com.swampmaster2160.swampmaster2160smod.block.BlockTriStateNotGateServer;
import com.swampmaster2160.swampmaster2160smod.block.BlockTriStateSignalServer;
import com.swampmaster2160.swampmaster2160smod.block.BlockTriStateTrueServer;
import com.swampmaster2160.swampmaster2160smod.item.ItemTestWandServer;

public class SwampMaster2160sModServer extends SwampMaster2160sMod implements ServerMod {
	// All the blocks as static vars
	public static RegisteredBlock deathBlock;
	public static RegisteredBlock triStateSignal;
	public static RegisteredBlock triStateTrue;
	public static RegisteredBlock triStateFalse;
	public static RegisteredBlock triStateBuffer;
	public static RegisteredBlock triStateNotGate;
	// Add the items as static vars
	public static RegisteredItem testWand;
	// A list of tri-state blocks
	public static Vector<Integer> triStateBlocksList;

	@Override
	public void onInit() {
		// Server specific code

		// Register blocks (Serverside)
		triStateBlocksList = new Vector<Integer>();

		deathBlock = registerNewBlock("death_block", new BlockBuilder()
			.setGameBlockSource(BlockDeathServer.class)
			.setBlockName("death_block")
		);
		triStateSignal = registerNewBlock("tri_state_signal", new BlockBuilder()
			.setGameBlockSource(BlockTriStateSignalServer.class)
			.setBlockName("tri_state_signal")
		);
		triStateBlocksList.add(triStateSignal.getRegisteredBlockId());
		triStateTrue = registerNewBlock("tri_state_true", new BlockBuilder()
			.setGameBlockSource(BlockTriStateTrueServer.class)
			.setBlockName("tri_state_true")
		);
		triStateBlocksList.add(triStateTrue.getRegisteredBlockId());
		triStateFalse = registerNewBlock("tri_state_false", new BlockBuilder()
			.setGameBlockSource(BlockTriStateFalseServer.class)
			.setBlockName("tri_state_false")
		);
		triStateBlocksList.add(triStateFalse.getRegisteredBlockId());
		triStateBuffer = registerNewBlock("tri_state_buffer", new BlockBuilder()
			.setGameBlockSource(BlockTriStateBufferServer.class)
			.setBlockName("tri_state_buffer")
		);
		triStateBlocksList.add(triStateBuffer.getRegisteredBlockId());
		triStateNotGate = registerNewBlock("tri_state_not_gate", new BlockBuilder()
			.setGameBlockSource(BlockTriStateNotGateServer.class)
			.setBlockName("tri_state_not_gate")
		);
		triStateBlocksList.add(triStateNotGate.getRegisteredBlockId());
		// Register items (serverside)
		testWand = registerNewItem("test_wand", new ItemBuilder()
			.setGameItemSource(ItemTestWandServer.class)
			.setItemName("test_wand")
		);
	}
}
