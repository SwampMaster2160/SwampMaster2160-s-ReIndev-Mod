package com.swampmaster2160.swampmaster2160smod.block.tristate;

import java.util.HashSet;
import java.util.Set;

import com.swampmaster2160.swampmaster2160smod.Direction6Enum;
import com.swampmaster2160.swampmaster2160smod.SwampMaster2160sModServer;
import com.swampmaster2160.swampmaster2160smod.TriStateStateEnum;
import com.swampmaster2160.swampmaster2160smod.block.BlockTriStateServer;

import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.level.World;

public class BlockTriStateOrGateServer extends BlockTriStateServer {
	public BlockTriStateOrGateServer(int id) {
		super(id);
	}

	@Override
	public TriStateStateEnum getTriStateState(World world, int x, int y, int z, Direction6Enum directionTowards, Set<int[]> visited) {
		// Don't bother checking if we've already checked this block
		for (int[] pos : visited) {
			if (pos[0] == x && pos[1] == y && pos[2] == z) return TriStateStateEnum.ERROR;
		}
		super.getTriStateState(world, x, y, z, directionTowards, visited);
		// Get state
		TriStateStateEnum out = TriStateStateEnum.FALSE;
		for (int i = 0; i < 6; i++) {
			Direction6Enum direction = Direction6Enum.fromInt(i);
			int neighborX = x + direction.xOffset;
			int neighborY = y + direction.yOffset;
			int neighborZ = z + direction.zOffset;
			int neighborId = world.getBlockId(neighborX, neighborY, neighborZ);
			if (SwampMaster2160sModServer.triStateBlocksList.contains(neighborId)) {
				BlockTriStateServer neighborBlock = (BlockTriStateServer)Block.blocksList[neighborId];
				if (neighborBlock.getInputType(world, neighborX, neighborY, neighborZ, direction) == 0) continue;
				out = out.or(neighborBlock.getTriStateInput(world, neighborX, neighborY, neighborZ, direction, visited));
			}
		}
		return out;
	}

	@Override
	public TriStateStateEnum getTriStateStateFromSources(World world, int x, int y, int z, Direction6Enum directionTowards, Set<int[]> visited) {
		// The buffer is a source so give the state of the block it's facing away from
		return getTriStateState(world, x, y, z, directionTowards, visited);
	}

	@Override
	public void triStateStateMayNeedChanging(World world, int x, int y, int z, Set<int[]> visited) {
		// Don't bother checking if we've already checked this block
		for (int[] pos : visited) {
			if (pos[0] == x && pos[1] == y && pos[2] == z) return;
		}
		super.triStateStateMayNeedChanging(world, x, y, z, visited);
		// Tell all the blocks except the block the buffer has it's back towards that they may need to change
		for (int i = 0; i < 6; i++) {
			Direction6Enum direction = Direction6Enum.fromInt(i);
			int neighborX = x + direction.xOffset;
			int neighborY = y + direction.yOffset;
			int neighborZ = z + direction.zOffset;
			int neighborId = world.getBlockId(neighborX, neighborY, neighborZ);
			if (SwampMaster2160sModServer.triStateBlocksList.contains(neighborId)) {
				BlockTriStateServer neighborBlock = (BlockTriStateServer)Block.blocksList[neighborId];
				neighborBlock.triStateStateMayNeedChanging(world, neighborX, neighborY, neighborZ, visited);
			}
		}
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
