package com.swampmaster2160.swampmaster2160smod.block;

import java.util.Set;

import com.swampmaster2160.swampmaster2160smod.Direction6Enum;
import com.swampmaster2160.swampmaster2160smod.TriStateStateEnum;

import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.level.World;

public class BlockTriStateClient extends BlockSMMBaseClient {
	public BlockTriStateClient(int id) {
		super(id, Material.rock);
	}

	// Get the tri-state state that the block has on a side given a direction we are looking from.
	public TriStateStateEnum getTriStateState(World world, int x, int y, int z, Direction6Enum directionFrom) {
		return TriStateStateEnum.FLOATING;
	}

	// Set the tri-state state that the block has on a side given a direction we are looking from.
	public void setTriStateState(World world, int x, int y, int z, Direction6Enum directionFrom, TriStateStateEnum newState) {

	}

	// Called when the block may need to change its state (eg. a source block was placed next to it or removed).
	public void triStateStateMayNeedChanging(World world, int x, int y, int z) {

	}

	// Search for tri-state source blocks that affect the blocks state and return the state that the block should be in.
	public TriStateStateEnum getTriStateStateFromSources(World world, int x, int y, int z, Direction6Enum directionFrom, Set<int[]> visited) {
		visited.add(new int[] { x, y, z });
		return TriStateStateEnum.FLOATING;
	}
}
