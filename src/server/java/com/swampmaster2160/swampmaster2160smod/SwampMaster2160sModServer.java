package com.swampmaster2160.swampmaster2160smod;

import java.util.Vector;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.registry.BlockBuilder;
import com.fox2code.foxloader.registry.ItemBuilder;
import com.fox2code.foxloader.registry.RegisteredBlock;
import com.fox2code.foxloader.registry.RegisteredItem;
import com.swampmaster2160.swampmaster2160smod.block.BlockDeathServer;
import com.swampmaster2160.swampmaster2160smod.block.tristate.BlockTriStateAndGateServer;
import com.swampmaster2160.swampmaster2160smod.block.tristate.BlockTriStateBufferServer;
import com.swampmaster2160.swampmaster2160smod.block.tristate.BlockTriStateFalseServer;
import com.swampmaster2160.swampmaster2160smod.block.tristate.BlockTriStateInput1Server;
import com.swampmaster2160.swampmaster2160smod.block.tristate.BlockTriStateNotGateServer;
import com.swampmaster2160.swampmaster2160smod.block.tristate.BlockTriStateOrGateServer;
import com.swampmaster2160.swampmaster2160smod.block.tristate.BlockTriStateSignalServer;
import com.swampmaster2160.swampmaster2160smod.block.tristate.BlockTriStateTrueServer;
import com.swampmaster2160.swampmaster2160smod.block.tristate.BlockTriStateXorGateServer;
//import com.swampmaster2160.swampmaster2160smod.entity.EntityRaccoonServer;
import com.swampmaster2160.swampmaster2160smod.item.ItemSMMSpawnEggServer;
import com.swampmaster2160.swampmaster2160smod.item.ItemTestWandServer;
//import com.swampmaster2160.swampmaster2160smod.mixininterfaces.IMixinEntityListServer;

//import net.minecraft.src.game.entity.EntityList;
import net.minecraft.src.game.item.Item;

public class SwampMaster2160sModServer extends SwampMaster2160sMod implements ServerMod {
	// All the blocks as static vars
	public static RegisteredBlock deathBlock;
	public static RegisteredBlock triStateSignal;
	public static RegisteredBlock triStateTrue;
	public static RegisteredBlock triStateFalse;
	public static RegisteredBlock triStateBuffer;
	public static RegisteredBlock triStateNotGate;
	public static RegisteredBlock triStateInput1;
	public static RegisteredBlock triStateOrGate;
	public static RegisteredBlock triStateAndGate;
	public static RegisteredBlock triStateXorGate;
	// Add the items as static vars
	public static RegisteredItem testWand;
	public static RegisteredItem raccoonSpawnEggRegistered;
	public static ItemSMMSpawnEggServer raccoonSpawnEgg;
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
		triStateInput1 = registerNewBlock("tri_state_input_1", new BlockBuilder()
			.setGameBlockSource(BlockTriStateInput1Server.class)
			.setBlockName("tri_state_input_1")
		);
		triStateBlocksList.add(triStateInput1.getRegisteredBlockId());
		triStateOrGate = registerNewBlock("tri_state_or_gate", new BlockBuilder()
			.setGameBlockSource(BlockTriStateOrGateServer.class)
			.setBlockName("tri_state_or_gate")
		);
		triStateBlocksList.add(triStateOrGate.getRegisteredBlockId());
		triStateAndGate = registerNewBlock("tri_state_and_gate", new BlockBuilder()
			.setGameBlockSource(BlockTriStateAndGateServer.class)
			.setBlockName("tri_state_and_gate")
		);
		triStateBlocksList.add(triStateAndGate.getRegisteredBlockId());
		triStateXorGate = registerNewBlock("tri_state_xor_gate", new BlockBuilder()
			.setGameBlockSource(BlockTriStateXorGateServer.class)
			.setBlockName("tri_state_xor_gate")
		);
		triStateBlocksList.add(triStateXorGate.getRegisteredBlockId());
		// Register items (serverside)
		testWand = registerNewItem("test_wand", new ItemBuilder()
			.setGameItemSource(ItemTestWandServer.class)
			.setItemName("test_wand")
		);

		raccoonSpawnEggRegistered = registerNewItem("raccoon_spawn_egg", new ItemBuilder()
			.setGameItemSource(ItemSMMSpawnEggServer.class)
			.setItemName("spawnEgg.raccoon")
		);
		raccoonSpawnEgg = (ItemSMMSpawnEggServer)Item.itemsList[raccoonSpawnEggRegistered.getRegisteredItemId()];
		raccoonSpawnEgg.setFlavor("raccoon");
		// Register entities (Serverside)
		//IMixinEntityListServer entityListDummyObject = (IMixinEntityListServer)(Object)new EntityList();
		//entityListDummyObject.addMappingPublic(EntityRaccoonServer.class, "Raccoon", 300);
	}
}
