package com.swampmaster2160.swampmaster2160smod.block;

import java.util.HashSet;
import java.util.Set;

import com.swampmaster2160.swampmaster2160smod.Direction6Enum;
import com.swampmaster2160.swampmaster2160smod.SwampMaster2160sModClient;
import com.swampmaster2160.swampmaster2160smod.TriStateStateEnum;

import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.level.World;

public class BlockTriStateTrueClient extends BlockTriStateClient {
	public BlockTriStateTrueClient(int id) {
		super(id);
	}

	@Override
	public TriStateStateEnum getTriStateState(World world, int x, int y, int z, Direction6Enum directionTowards, Set<int[]> visited) {
		return TriStateStateEnum.TRUE;
	}

	@Override
	public TriStateStateEnum getTriStateStateFromSources(World world, int x, int y, int z, Direction6Enum directionTowards, Set<int[]> visited) {
		super.getTriStateStateFromSources(world, x, y, z, directionTowards, visited);
		// This block is a source for a true state signal
		return TriStateStateEnum.TRUE;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		// Inform all block neighbors that their state may need changing
		for (int i = 0; i < 6; i++) {
			Direction6Enum direction = Direction6Enum.fromInt(i);
			int neighborX = x + direction.xOffset;
			int neighborY = y + direction.yOffset;
			int neighborZ = z + direction.zOffset;
			int neighborId = world.getBlockId(neighborX, neighborY, neighborZ);
			if (SwampMaster2160sModClient.triStateBlocksList.contains(neighborId)) {
				BlockTriStateClient neighborBlock = (BlockTriStateClient)Block.blocksList[neighborId];
				neighborBlock.triStateStateMayNeedChanging(world, neighborX, neighborY, neighborZ, new HashSet<int[]>());
			}
		}
	}

	@Override
	public void onBlockRemoval(World world, int x, int y, int z) {
		// Inform all block neighbors that their state may need changing
		for (int i = 0; i < 6; i++) {
			Direction6Enum direction = Direction6Enum.fromInt(i);
			int neighborX = x + direction.xOffset;
			int neighborY = y + direction.yOffset;
			int neighborZ = z + direction.zOffset;
			int neighborId = world.getBlockId(neighborX, neighborY, neighborZ);
			if (SwampMaster2160sModClient.triStateBlocksList.contains(neighborId)) {
				BlockTriStateClient neighborBlock = (BlockTriStateClient)Block.blocksList[neighborId];
				neighborBlock.triStateStateMayNeedChanging(world, neighborX, neighborY, neighborZ, new HashSet<int[]>());
			}
		}
	}
}
