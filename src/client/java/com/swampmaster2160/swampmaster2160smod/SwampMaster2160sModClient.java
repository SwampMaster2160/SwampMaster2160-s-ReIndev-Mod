package com.swampmaster2160.swampmaster2160smod;

import java.util.Vector;

import com.fox2code.foxloader.loader.ClientMod;
import com.fox2code.foxloader.registry.BlockBuilder;
import com.fox2code.foxloader.registry.GameRegistry;
import com.fox2code.foxloader.registry.GameRegistryClient;
import com.fox2code.foxloader.registry.ItemBuilder;
import com.fox2code.foxloader.registry.RegisteredBlock;
import com.fox2code.foxloader.registry.RegisteredItem;
import com.swampmaster2160.swampmaster2160smod.block.BlockDeathClient;
import com.swampmaster2160.swampmaster2160smod.block.tristate.BlockTriStateAndGateClient;
import com.swampmaster2160.swampmaster2160smod.block.tristate.BlockTriStateBufferClient;
import com.swampmaster2160.swampmaster2160smod.block.tristate.BlockTriStateFalseClient;
import com.swampmaster2160.swampmaster2160smod.block.tristate.BlockTriStateInput1Client;
import com.swampmaster2160.swampmaster2160smod.block.tristate.BlockTriStateNotGateClient;
import com.swampmaster2160.swampmaster2160smod.block.tristate.BlockTriStateOrGateClient;
import com.swampmaster2160.swampmaster2160smod.block.tristate.BlockTriStateSignalClient;
import com.swampmaster2160.swampmaster2160smod.block.tristate.BlockTriStateTrueClient;
import com.swampmaster2160.swampmaster2160smod.block.tristate.BlockTriStateXorGateClient;
import com.swampmaster2160.swampmaster2160smod.entity.EntityRaccoonClient;
import com.swampmaster2160.swampmaster2160smod.item.ItemSMMSpawnEggClient;
import com.swampmaster2160.swampmaster2160smod.item.ItemTestWandClient;
import com.swampmaster2160.swampmaster2160smod.mixininterfaces.IMixinEntityListClient;

import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.entity.EntityList;
import net.minecraft.src.game.item.Item;

public class SwampMaster2160sModClient extends SwampMaster2160sMod implements ClientMod {
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
	public static ItemSMMSpawnEggClient raccoonSpawnEgg;
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
		/*GameRegistry gameRegistry = GameRegistry.getInstance();
		String blockName = GameRegistry.validateAndFixRegistryName(getModContainer().id + ":" + "death_block");
		int id = gameRegistry.generateNewBlockId(blockName, GameRegistry.DEFAULT_FALLBACK_BLOCK_ID);
		Block block = new BlockDeathClient(id);*/
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
		triStateBuffer = registerNewBlock("tri_state_buffer", new BlockBuilder()
			.setGameBlockSource(BlockTriStateBufferClient.class)
			.setBlockName("tri_state_buffer")
		);
		triStateBlocksList.add(triStateBuffer.getRegisteredBlockId());
		triStateNotGate = registerNewBlock("tri_state_not_gate", new BlockBuilder()
			.setGameBlockSource(BlockTriStateNotGateClient.class)
			.setBlockName("tri_state_not_gate")
		);
		triStateBlocksList.add(triStateNotGate.getRegisteredBlockId());
		triStateInput1 = registerNewBlock("tri_state_input_1", new BlockBuilder()
			.setGameBlockSource(BlockTriStateInput1Client.class)
			.setBlockName("tri_state_input_1")
		);
		triStateBlocksList.add(triStateInput1.getRegisteredBlockId());
		triStateOrGate = registerNewBlock("tri_state_or_gate", new BlockBuilder()
			.setGameBlockSource(BlockTriStateOrGateClient.class)
			.setBlockName("tri_state_or_gate")
		);
		triStateBlocksList.add(triStateOrGate.getRegisteredBlockId());
		triStateAndGate = registerNewBlock("tri_state_and_gate", new BlockBuilder()
			.setGameBlockSource(BlockTriStateAndGateClient.class)
			.setBlockName("tri_state_and_gate")
		);
		triStateBlocksList.add(triStateAndGate.getRegisteredBlockId());
		triStateXorGate = registerNewBlock("tri_state_xor_gate", new BlockBuilder()
			.setGameBlockSource(BlockTriStateXorGateClient.class)
			.setBlockName("tri_state_xor_gate")
		);
		triStateBlocksList.add(triStateXorGate.getRegisteredBlockId());
		// Register items (Clientside)
		testWand = registerNewItem("test_wand", new ItemBuilder()
			.setGameItemSource(ItemTestWandClient.class)
			.setItemName("test_wand")
		);

		raccoonSpawnEggRegistered = registerNewItem("raccoon_spawn_egg", new ItemBuilder()
			.setGameItemSource(ItemSMMSpawnEggClient.class)
			.setItemName("spawnEgg.raccoon")
		);
		raccoonSpawnEgg = (ItemSMMSpawnEggClient)Item.itemsList[raccoonSpawnEggRegistered.getRegisteredItemId()];
		raccoonSpawnEgg.setFlavor("raccoon");
		// Register entities (Clientside)
		IMixinEntityListClient entityListDummyObject = (IMixinEntityListClient)(Object)new EntityList();
		entityListDummyObject.addMappingPublic(EntityRaccoonClient.class, "Raccoon", 300);
	}
}
