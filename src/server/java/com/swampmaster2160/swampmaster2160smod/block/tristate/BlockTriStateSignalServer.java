package com.swampmaster2160.swampmaster2160smod.block.tristate;

import java.util.Set;

import com.swampmaster2160.swampmaster2160smod.Direction6Enum;
import com.swampmaster2160.swampmaster2160smod.SwampMaster2160sModServer;
import com.swampmaster2160.swampmaster2160smod.TriStateStateEnum;
import com.swampmaster2160.swampmaster2160smod.block.BlockTriStateServer;

import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.level.World;
import java.util.HashSet;

public class BlockTriStateSignalServer extends BlockTriStateServer {
	public BlockTriStateSignalServer(int id) {
		super(id);
	}

	@Override
	public TriStateStateEnum getTriStateState(World world, int x, int y, int z, Direction6Enum directionTowards, Set<int[]> visited) {
		// Get the blocks state from it's metadata
		return TriStateStateEnum.fromInt(world.getBlockMetadata(x, y, z));
	}

	@Override
	public void setTriStateState(World world, int x, int y, int z, Direction6Enum directionTowards, TriStateStateEnum newState, Set<int[]> visited) {
		for (int[] pos : visited) {
			if (pos[0] == x && pos[1] == y && pos[2] == z) return;
		}
		super.setTriStateState(world, x, y, z, directionTowards, newState, visited);
		// Set the block's state if the state is diffrent and then tell neighbors to change their state
		world.setBlockMetadataWithNotify(x, y, z, newState.intValue);
		for (int i = 0; i < 6; i++) {
			Direction6Enum direction = Direction6Enum.fromInt(i);
			int neighborX = x + direction.xOffset;
			int neighborY = y + direction.yOffset;
			int neighborZ = z + direction.zOffset;
			int neighborId = world.getBlockId(neighborX, neighborY, neighborZ);
			if (SwampMaster2160sModServer.triStateBlocksList.contains(neighborId)) {
				BlockTriStateServer neighborBlock = (BlockTriStateServer)Block.blocksList[neighborId];
				neighborBlock.setTriStateState(world, neighborX, neighborY, neighborZ, direction, newState, visited);
			}
		}
	}

	@Override
	public TriStateStateEnum getTriStateStateFromSources(World world, int x, int y, int z, Direction6Enum directionTowards, Set<int[]> visited) {
		// Do not visit a block twice (that may cause an infinite loop and crash the game)
		for (int[] pos : visited) {
			if (pos[0] == x && pos[1] == y && pos[2] == z) return TriStateStateEnum.FLOATING;
		}
		super.getTriStateStateFromSources(world, x, y, z, directionTowards, visited);
		// Get the sources from the neighboring blocks and combine
		TriStateStateEnum out = TriStateStateEnum.FLOATING;
		for (int i = 0; i < 6; i++) {
			Direction6Enum direction = Direction6Enum.fromInt(i);
			int neighborX = x + direction.xOffset;
			int neighborY = y + direction.yOffset;
			int neighborZ = z + direction.zOffset;
			int neighborId = world.getBlockId(neighborX, neighborY, neighborZ);
			if (SwampMaster2160sModServer.triStateBlocksList.contains(neighborId)) {
				BlockTriStateServer neighborBlock = (BlockTriStateServer)Block.blocksList[neighborId];
				TriStateStateEnum neighborStateFromSource = neighborBlock.getTriStateStateFromSources(world, neighborX, neighborY, neighborZ, direction, visited);
				out = out.combine(neighborStateFromSource);
			}
		}
		return out;
	}

	@Override
	public void triStateStateMayNeedChanging(World world, int x, int y, int z, Set<int[]> visited) {
		// Do not visit a block twice (that may cause an infinite loop and crash the game)
		for (int[] pos : visited) {
			if (pos[0] == x && pos[1] == y && pos[2] == z) return;
		}
		super.triStateStateMayNeedChanging(world, x, y, z, new HashSet<int[]>());
		// Get the state from the sources that affect this block
		TriStateStateEnum stateFromSources = getTriStateStateFromSources(world, x, y, z, null, new HashSet<int[]>());
		setTriStateState(world, x, y, z, null, stateFromSources, visited);
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		// When we add a block we should see what state it should have and weather it affects other blocks
		triStateStateMayNeedChanging(world, x, y, z, new HashSet<int[]>());
	}

	@Override
	public void onBlockRemoval(World world, int x, int y, int z) {
		// Tell the neighbors that their state may have to change
		for (int i = 0; i < 6; i++) {
			Direction6Enum direction = Direction6Enum.fromInt(i);
			int neighborX = x + direction.xOffset;
			int neighborY = y + direction.yOffset;
			int neighborZ = z + direction.zOffset;
			int neighborId = world.getBlockId(neighborX, neighborY, neighborZ);
			if (SwampMaster2160sModServer.triStateBlocksList.contains(neighborId)) {
				BlockTriStateServer neighborBlock = (BlockTriStateServer)Block.blocksList[neighborId];
				neighborBlock.triStateStateMayNeedChanging(world, neighborX, neighborY, neighborZ, new HashSet<int[]>());
			}
		}
	}
}
